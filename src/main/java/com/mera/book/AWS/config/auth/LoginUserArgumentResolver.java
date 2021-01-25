package com.mera.book.AWS.config.auth;

import com.mera.book.AWS.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;


// 해당 Resolver는 HandlerMethodArgumentResolver 인터페이스를 구현한 클래스
// HandlerMethodArgumentResolver는 한 가지 기능을 지원
// :: 조건에 맞는 메소드가 있다면 해당 리졸버의 구현체가 지정한 값으로 메소드 파라미터 넘길 수 있음
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    // supportsParameter()
    // 컨트롤러 메소드의 특정 팔미터를 지원하는지 판단
    // 여기서는 @LoginUser 어노테이션이 붙어 있고, 파라미터 클래스 타입이 SessionUser.class인 경우 true 반환
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }


    @Override
    // resolveArgument()
    // 파라미터에 전달할 객체 생성
    // 여기서는 세션에서 객체를 가져옴
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return httpSession.getAttribute("user");
    }
}

/*
    생성한 리졸버는 스프링에서 인식될 수 있도록 WebMvcConfigure에 추가 . => WebConfig 클래스 생성
 */