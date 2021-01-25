package com.mera.book.AWS.config.auth;

import com.mera.book.AWS.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity  // Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // h2-console 화면을 사용하기 위해 해당 옵션들을 disable
                .csrf().disable().headers().frameOptions().disable()
                .and()
                    // URL별 권한 관리 설정 옵션 시작 ( antMatchers를 사용하기 위함 )
                    .authorizeRequests()

                    // antMatchers : 권한 관리 대상 지정 옵션 ( URL, HTTP 메소드별 관리 가능 )
                    // "/" 등 지정된 URL들은 permitAll() 옵션 통해 전체 열람 권한
                    // "/api/v1/**" 주소를 가진 API는 USER 권한 가진 사람만 가능
                    .antMatchers("/", "/css/**", "/images/**", "/js/**",
                            "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())

                    // anyRequest :: 설정된 값들 이외 나머지 URL 나타냄
                    // 여기서는 authenticated()를 추가하여 나머지 URL은 모두 인증된 사용자에게만 허가
                    // 인증된 사용자란 즉, 로그인한 사용자들을 말함
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    // OAuth2 로그인 기능에 대한 여러 설정 진입점
                    .oauth2Login()
                        // OAuth 2 로그인 성공 이후 사용자 정보 가져오는 설정 담당
                        .userInfoEndpoint()
                            // 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
                            // 리소스 서버 (소셜서비스)에서 사용자 정보를 가져온 상태에서
                            // 추가로 진행하고자 하는 기능을 명시할 수 있음
                            .userService(customOAuth2UserService);
    }
}
