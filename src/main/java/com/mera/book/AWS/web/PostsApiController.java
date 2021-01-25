package com.mera.book.AWS.web;

import com.mera.book.AWS.service.PostsService;
import com.mera.book.AWS.web.dto.PostsResponseDto;
import com.mera.book.AWS.web.dto.PostsSaveRequestDto;
import com.mera.book.AWS.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    // 등록 기능 (insert)
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto
                                 requestDto) {
        return postsService.save(requestDto);
    }

    // 게시글 수정 (update)
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody
            PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }


    // 게시글 삭제 컨트롤러 (delete)
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }



    // 게시글 선택
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById (@PathVariable Long id) {
        return postsService.findById(id);
    }
}
