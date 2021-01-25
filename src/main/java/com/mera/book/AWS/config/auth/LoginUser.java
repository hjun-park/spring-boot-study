package com.mera.book.AWS.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 해당 LoginUser이라는 어노테이션이 생성될 수 있는 위치를 지정
// PARAMETER로 지젛앴으니 메소드의 파라미터로 선언된 객체에서만 사용 가능
// 이 외에도 클래스 선언문에 쓸 수 있는 TYPE 등이 있음
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {   // @interface // LoginUser이라는 이름을 가진 어노테이션이 생성되었음을 알려줌 ( 어노테이션 클래스 )
}
