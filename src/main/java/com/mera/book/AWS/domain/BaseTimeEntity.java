package com.mera.book.AWS.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
    BaseTimeEntity 클래스는 JPA Auditing을 사용하는 클래스
    이를 이용하면 DB에 날짜 데이터 등록/수정을 DB에 기록한다.

    해당 클래스는 모든 Entity의 상위 클래스가 되어서
    Entity들의 createdDate, modifiedDate를 자동으로 관리하는 역할
 */

@Getter
@MappedSuperclass   // JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우
                    // 필드(createdDate, modifiedDate)도 컬럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class) // BaseTimeEntity 클래스에 Auditing 기능 포함
public class BaseTimeEntity {

    @CreatedDate    // Entity가 생성되어 저장될 때 시간 자동 저장
    private LocalDateTime createdDate;

    @LastModifiedDate   // 조회한 Entity의 값을 변경할 때 시간이 자동 저장
    private LocalDateTime modifiedDate;

}
