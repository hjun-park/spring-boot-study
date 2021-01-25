package com.mera.book.AWS.domain.posts;

import com.mera.book.AWS.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
    1. Entity : 테이블과 링크 될 클래스 ( 테이블이라고 봐도 무방 )
                기본값으로 클래스의 카멜 이름을 언더스코어 네이밍으로 테이블 이름 매칭
                ex) WebDev.java -> web_dev table
    2. NoArgsConstructor : 롬복라이브러리 (기본 생성자 자동 추가)
    3. Getter : 롬복 라이브러리 ( 해당 클래스 내 모든 필드의 Getter 자동 생성 )
    4. Builder : 롬복 라이브러리 ( 해당 클래스 빌더 패턴 클래스 생성 )
 */
@Getter
@NoArgsConstructor
@Entity

// Posts 클래스는 실제로 DB 테이블과 매칭될 클래스.
// JPA를 사용하면 DB 데이터에 작업할 경우 실제 쿼리를 날리기보다는 이 Entity 클래스 수정 통해 작업

// DB에 날짜 반영할 JPA Auditing 기능 수행하는 BaseTimeEntity를 상속받음
public class Posts extends BaseTimeEntity {

    /*
        @Id : 해당 테이블의 Primary Key 필드
        @GeneratedValue : PK의 생성 규칙 나타냄
                GenerationType.IDENTITY : auto_increment되는 옵션
        @Column : 테이블의 컬럼을 나타내며 선언하지 않아도 해당 클래스는 모두 컬럼
                  사용한걸 보면 문자열 크기를 500으로 늘리거나 타입을 TEXT로 변환 사용
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

