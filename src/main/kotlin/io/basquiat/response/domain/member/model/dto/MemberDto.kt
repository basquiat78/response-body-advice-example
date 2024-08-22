package io.basquiat.response.domain.member.model.dto

import io.basquiat.response.domain.member.model.entity.Member
import io.basquiat.response.global.type.LongIdentifiable
import java.time.LocalDateTime

/**
 * member dto
 * created by basquiat
 */
data class MemberDto(
    override val id: Long,
    val name: String,
    val nickName: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
): LongIdentifiable {
    companion object {
        /**
         * entity to dto convert
         * @param entity
         * @return MemberDto
         */
        fun toDto(entity: Member) = with(entity) {
            MemberDto(
                id = id!!,
                name = name,
                nickName = nickName,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}
