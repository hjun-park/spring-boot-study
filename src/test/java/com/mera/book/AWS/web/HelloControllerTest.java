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

// API를 테스트하는 코드를 추가
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class) // 테스트진행 시 Junit에 내장된 실행자 외 다른 실행자 실행
                            // 여기서는 SpringRunner이라는 스프링 실행자 사용
                            // 스프링부트 테스트와 Junit 사이에 연결자 역할
@WebMvcTest(controllers = HelloController.class) // 여러 스프링 어노테이션 중 Web에 집중할 수 있는 어노테이션

public class HelloControllerTest {

//    @Autowired      // 스프링이 관리하는 Bean을 주입받음
//
//    /*
//        웹 API 테스트할 때 사용
//        스프링 MVC 테스트 시작점
//        HTTP GET POST 에 대한 api 테스트 가능
//     */
//    private MockMvc mvc;
//
//    @Test
//    public void hello가_리턴된다() throws Exception {
//        String hello = "hello";
//
//        mvc.perform(get("/hello"))  // MockMVC를 통해 /hello 주소로 GET 요청
//                .andExpect(status().isOk()) // 200OK인지 검사
//                .andExpect(content().string(hello)); // 응답본문의 내용 검사 Controller에서 hello 리턴 검사
//    }

    // dto 테스트 코드를 추가
    @Autowired
    private MockMvc mvc;

    // 기존 테스트
    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    // 추가된 테스트
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        /*
            param : API 테스트할 때 사용될 요청 파라미터 설정
                    String만 허용되기 때문에 숫자 날짜는 문자열로 변경해야 함

            jsonPath : JSON 응답값을 필드별로 검증할 수 있는 메소드
                       $를 기준으로 필드명 명시. name과 amount를 검증하니
                       $.name, $.amount로 검증한다.
         */
        mvc.perform(
                get("/hello/dto")
                    .param("name", name)
                    .param("amount", String.valueOf(amount)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(name)))
            .andExpect(jsonPath("$.amount", is(amount)));
    }
}
