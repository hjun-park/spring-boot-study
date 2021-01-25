package com.mera.book.AWS;

// 패키지 가져오기 단축키 : [Alt + Enter]
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing      // JPA Auditing 기능 모두 활성화
@SpringBootApplication      // 스프링부트 자동 설정, 스프링 Bean 읽기와 생성 모두 자동 설정 ( 항상 프로젝트 최상단 위치 )
public class Application {
    public static void main(String[] args) {
        // 스프링 부트에서는 언제 어디에서나 같은 환경에서의 스프링부트를 배포하기 위해 내장 AWS 사용
        SpringApplication.run(Application.class, args); // 내장 AWS 실행 성능문제도 그리 없다.
    }
}
