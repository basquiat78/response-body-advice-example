package io.basquiat.response.global.model.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.LocalDateTime.now

/**
 * 최초 row 생성시 created_at과 updated_at의 생성 시기를 AuditingEntityListener에 위임하고 싶다면
 * 해당 엔티티를 상속한다.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = now()
        protected set

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false, updatable = false)
    var updatedAt: LocalDateTime = now()
        protected set

}