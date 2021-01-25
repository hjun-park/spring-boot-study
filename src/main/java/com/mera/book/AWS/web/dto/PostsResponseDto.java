package com.mera.book.AWS.web.dto;

import com.mera.book.AWS.domain.posts.Posts;
import lombok.Getter;

/*
    생성자로 Entity를 받아서 필드에 값을 집어넣음.
    굳이 모든 필드를 가진 생성자가 필요하지 않으므로 Dto는 Entity를 받아서 처리
 */

@Getter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();

    }

}
