package com.mera.book.AWS.web.dto;

import org.junit.Test;

// assertj라는 테스트 검증 라이브러리 검증 메소드
// 검증하고 싶은 대상을 메소드 인자로 받음
// 또한 메소드 체이닝이 되어서 isEqualTo와 같이 메소드 이어서 사용 가능
import static org.assertj.core.api.Assertions.assertThat;

public class HelloResponseDtoTest {

    @Test
    public void 롬복_기능_테스트() {
        //given
        String name = "test";
        int amount = 1000;

        //when
        HelloResponseDto dto = new HelloResponseDto(name, amount);

        // isEqualTo는 동등 비교메소드. 값이 같을 때만 성공
        //then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
