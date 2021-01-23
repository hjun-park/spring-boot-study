/*
    HelloController이 잘 동작하는지 확인하는 테스트코드
 */
package com.mera.book.AWS.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // 테스트진행 시 Junit에 내장된 실행자 외 다른 실행자 실행
                            // 여기서는 SpringRunner이라는 스프링 실행자 사용
                            // 스프링부트 테스트와 Junit 사이에 연결자 역할
@WebMvcTest(controllers = HelloController.class) // 여러 스프링 어노테이션 중 Web에 집중할 수 있는 어노테이션

public class HelloControllerTest {

    @Autowired      // 스프링이 관리하는 Bean을 주입받음
    
    /*
        웹 API 테스트할 때 사용
        스프링 MVC 테스트 시작점
        HTTP GET POST 에 대한 api 테스트 가능
     */
    private MockMvc mvc;    

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))  // MockMVC를 통해 /hello 주소로 GET 요청
                .andExpect(status().isOk()) // 200OK인지 검사
                .andExpect(content().string(hello)); // 응답본문의 내용 검사 Controller에서 hello 리턴 검사
    }
}
