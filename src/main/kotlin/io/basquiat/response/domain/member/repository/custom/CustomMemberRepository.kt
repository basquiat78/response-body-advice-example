package io.basquiat.response.domain.member.repository.custom

import io.basquiat.response.domain.member.model.entity.Member
import io.basquiat.response.domain.member.model.vo.QueryCondition

/**
 * CustomMemberRepository interface
 * created by basquiat
 */
interface CustomMemberRepository {
    fun findByConditions(conditions: QueryCondition): List<Member>
    fun existNickName(nickName: String): Boolean
}