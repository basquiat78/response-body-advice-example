package io.basquiat.response.domain.member.service

import io.basquiat.response.domain.member.model.entity.Member
import io.basquiat.response.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service

/**
 * mutation member service
 * created by basquiat
 */
@Service
class MemberMutationService(
    private val repository: MemberRepository,
) {

    /**
     * create member
     * @param entity
     * @return Member
     */
    fun createMember(entity: Member): Member {
        return repository.save(entity)
    }

    /**
     * update member
     * @param entity
     * @return Member
     */
    fun updateMember(entity: Member): Member {
        return repository.save(entity)
    }

}