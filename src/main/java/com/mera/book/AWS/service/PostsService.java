package com.mera.book.AWS.service;

import com.mera.book.AWS.domain.posts.Posts;
import com.mera.book.AWS.domain.posts.PostsRepository;
import com.mera.book.AWS.web.dto.PostsListResponseDto;
import com.mera.book.AWS.web.dto.PostsResponseDto;
import com.mera.book.AWS.web.dto.PostsSaveRequestDto;
import com.mera.book.AWS.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/*
    # JPA 영속성 컨텍스트
    update 기능에 DB에 쿼리 날리는 부분이 없음
    영속성 컨텍스트 : 엔티티를 영구 저장하는 환경
    1. 트랜잭션 안에서 데이터베이스에서 데이터 가져오면 이 데이터는 영속성 컨텍스트 유지된 상태
    2. 이 상태에서 데이터 값 변경 시 트랜잭션 끝나는 시점에 해당 테이블 변경분 반영
    3. 즉, Entity 값 변경만으로도 테이블 내용이 변경됨. Update 쿼리가 별도로 필요 없음
    4. 이 개념이 더티 체킹이라고 한다. ( https://jojoldu.tistory.com/415 )
 */

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    // 저장하는 API 기능
    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    // 수정하는 API 기능
    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow( () -> new
                 IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

                posts.update(requestDto.getTitle(), requestDto.getContent());

                return id;

    }

    // 삭제하는 API 기능
    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }


    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow( () -> new
                        IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    // readonly true : 트랜잭션 범위 유지하되, 조회 기능만 남겨둠
    // 그러면 등록 수정 삭제는 없지만 조회 속도는 개선된 환경
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                // .map(posts -> new PostsListResponseDto(posts)) 람다로 표현
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }


}
