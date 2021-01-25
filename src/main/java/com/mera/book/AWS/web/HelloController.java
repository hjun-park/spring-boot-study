package com.mera.book.AWS.web;

import com.mera.book.AWS.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // 컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 줍니다.
public class HelloController {
/*
    @GetMapping("/hello") // Get Method를 받을 수 있는 API
    public String hello() {
        return "hello";
    }
*/

    // 새로 만든 dto가 적용되도록 설정
    @GetMapping("/hello/dto")

    // @RequestParam : 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
    //                 여기서는 name(@RequestParam("name"))이란 이름으로 넘긴 파라미터를
    //                 메소드 파라미터 name(String name)에 저장하게 된다.
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
