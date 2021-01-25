/*
    Posts 클래스로 Database를 접근하게 해 줄 인터페이스
 */
package com.mera.book.AWS.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
    SQL 매핑방식인 ibatis나 Mybatis 등에서는 이거를 Dao라고 부르는 DB Layer 접근자
    JPA에서는 Repository라고 부르며 Interface로 생성

    인터페이스 생성 후 JpaRepository<Entity 클래스, PK타입>을 상속시키면
    기본적인 CRUD 메소드가 자동으로 생성

    Entity 클래스와 기본 Entity Repository는 함께 위치해야 한다.
    추후에 프로젝트가 커져서 도메인별로 프로젝트 분리해야한다면
    Entity 클래스(Posts)와 기본 Repository(PostsRepository)는 같이 움직여야 함으로
    도메인 패키지에서 함께 관리
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
