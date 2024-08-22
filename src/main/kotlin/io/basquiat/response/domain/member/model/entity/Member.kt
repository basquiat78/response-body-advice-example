package io.basquiat.response.domain.member.model.entity

import io.basquiat.response.global.model.entity.BaseTimeOnlyPreUpdateEntity
import jakarta.persistence.*
import org.hibernate.annotations.DynamicUpdate

/**
 * member entity
 * created by basquiat
 */
@Entity
@DynamicUpdate
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    val name: String,

    nickName: String,

): BaseTimeOnlyPreUpdateEntity() {

    @Column(name = "nick_name", nullable = false, length = 100, unique = true)
    var nickName: String = nickName
        private set

    fun changeNickName(nickName: String) {
        this.nickName = nickName
    }

}