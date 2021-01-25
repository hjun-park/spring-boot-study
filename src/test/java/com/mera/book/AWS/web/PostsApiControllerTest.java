package com.mera.book.AWS.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mera.book.AWS.domain.posts.Posts;
import com.mera.book.AWS.domain.posts.PostsRepository;
import com.mera.book.AWS.web.dto.PostsSaveRequestDto;
import com.mera.book.AWS.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
    @WebMvcTest를 사용하지 않은 이유는 JPA 기능이 작동하지 않음
    따라서 Controller와 ControllerAdvice 등 외부 연동 부분 + JPA까지 테스트 위해
    @SpringBootTest를 사용함.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    // @SpringBootTest에서 MockMvc를 사용하기 위해 추가
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;


    // @SpringBootTest에서 MockMvc를 사용하기 위해 추가
    // 매번 테스트 시작 전에 MockMvc 인스턴스 생성
    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    // 등록 기능 테스트 ( insert 쿼리 )
    @Test
    @WithMockUser(roles="USER")
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";



        /*
            mvc.perform
             - 생성된 MockMvc를 통해 API를 테스트
             - Body 영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환
         */

        // @SpringBootTest에서 MockMvc를 사용하기 위해 추가
        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // @SpringBootTest에서 MockMvc를 사용하기 위해 추가
        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);

//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.
//                postForEntity(url, requestDto, Long.class);

//        //then
//        assertThat(responseEntity.getStatusCode()).
//                isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).
//                isGreaterThan(0L);

//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(title);
//        assertThat(all.get(0).getContent()).isEqualTo(content);

    }

    /*
        WithMockUser
         - 인증된 모의 사용자를 만들어서 사용
         - roles에 권한 추가 가능
         - 즉, 해당 어노테이션으로 인해 ROLE_USER 권한을 가진 사용자가 API 요청하는 것과 동일한 효과
     */

    // update 기능에서 쿼리 날리는 부분이 없어도 update 쿼리를 날리는지 확인하는 테스트
    @Test
    @WithMockUser(roles="USER")
    public void Posts_수정된다() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new
                HttpEntity<>(requestDto);

        // @SpringBootTest에서 MockMvc를 사용하기 위해 추가
        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);

//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(
//                url, HttpMethod.PUT, requestEntity, Long.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
//        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
