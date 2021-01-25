package com.mera.book.AWS.config.auth;

import com.mera.book.AWS.config.auth.dto.OAuthAttributes;
import com.mera.book.AWS.config.auth.dto.SessionUser;
import com.mera.book.AWS.domain.user.User;
import com.mera.book.AWS.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
                                throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User>
                delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registerationID : 현재 로그인 진행 중인 서비스 구분하는 코드
        // 지금은 구글만 사용하는 불필요한 값이지만 이후 네이버 로그인 연동 시에
        // 네이버 로그인인지 구글 로그인인지 구분하기 위해 사용
        String registerationId = userRequest.getClientRegistration().getRegistrationId();

        // userNameAttributeName : OAuth2 로그인 진행 시 키가 되는 필드값을 이야기. ( PK와 같은 의미 )
        // 구글의 경우 sub라는 기본 코드를 지원하지만 네이버나 카카오는 지원하지 않음
        // 이후 네이버 로그인과 구글 로그인 동시 지원 시 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuthAttributes : OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        // 이후 네이버나 다른 소셜 로그인도 OAuthAttributes 클래스 사용
        OAuthAttributes attributes = OAuthAttributes.
                of(registerationId, userNameAttributeName,
                        oAuth2User.getAttributes());


        /*
            SessionUser
                - 세션에 사용자 정보를 저장하기 위한 Dto 클래스
                - 왜 User 클래스를 쓰지 않고 새로 만들어서 쓰는지는 이후 설명
         */
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                    attributes.getAttributes(),
                    attributes.getNameAttributeKey());
    }

    // 구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능도 같이 구현.
    // 사용자의 이름이나 프로필 사진이 변경되면 User 엔티티에도 반영
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);

    }
}
