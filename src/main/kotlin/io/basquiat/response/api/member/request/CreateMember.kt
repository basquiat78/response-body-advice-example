package io.basquiat.response.api.member.request

import io.basquiat.response.domain.member.model.entity.Member

/**
 * member 생성 요청 객체
 * created by basquiat
 */
data class CreateMember(
    val name: String,
    val nickName: String,
) {
    fun toEntity(): Member {
        return Member(
            name = name,
            nickName = nickName
        )
    }
}
