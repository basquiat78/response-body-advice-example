package io.basquiat.response.global.model.entity

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PreUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.time.LocalDateTime.now

/**
 * 최초 row 생성시 updated_at 컬럼을 null로 세팅하고 싶다면 이 추상 엔티티를 상속한다.
 * created by basquiat
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeOnlyPreUpdateEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = now()
        protected set

    @Column(name = "updated_at", nullable = true, updatable = true)
    var updatedAt: LocalDateTime? = null
        protected set

    @PreUpdate
    fun updated() {
        updatedAt = now()
    }

}