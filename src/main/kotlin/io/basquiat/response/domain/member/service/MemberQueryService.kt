package io.basquiat.response.domain.member.service

import io.basquiat.response.domain.member.model.entity.Member
import io.basquiat.response.domain.member.model.vo.QueryCondition
import io.basquiat.response.domain.member.repository.MemberRepository
import io.basquiat.response.global.exceptions.NotFoundException
import io.basquiat.response.global.extensions.findByIdOrThrow
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * query member service
 * created by basquiat
 */
@Service
class MemberQueryService(
    private val repository: MemberRepository,
) {

    /**
     * member by id or null
     * @param id
     * @return Member?
     */
    fun fetchByIdOrNull(id: Long): Member? {
        return repository.findByIdOrNull(id)
    }

    /**
     * member by id or throw
     * @param id
     * @return Member
     * @throws NotFoundException
     */
    fun fetchByIdOrThrow(id: Long): Member {
        return repository.findByIdOrThrow(id, "멤버 아이디 [${id}]로 조회된 사용자 정보가 없습니다.")
    }

    /**
     * @param nickName
     * @return Boolean
     */
    fun existByNickName(nickName: String): Boolean {
        return repository.existNickName(nickName)
    }

    /**
     * @param conditions
     * @return List<Member>
     */
    fun findByConditions(conditions: QueryCondition): List<Member> {
        return repository.findByConditions(conditions)
    }
}