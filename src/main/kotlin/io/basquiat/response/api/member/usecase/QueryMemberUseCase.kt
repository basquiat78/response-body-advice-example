package io.basquiat.response.api.member.usecase

import io.basquiat.response.domain.member.model.dto.MemberDto
import io.basquiat.response.domain.member.service.MemberQueryService
import org.springframework.stereotype.Service

/**
 * query member usecase
 * created by basquiat
 */
@Service
class QueryMemberUseCase(
    private val query: MemberQueryService,
) {

    fun execute(id: Long): MemberDto {
        return query.fetchByIdOrThrow(id).let(MemberDto::toDto)
    }

}