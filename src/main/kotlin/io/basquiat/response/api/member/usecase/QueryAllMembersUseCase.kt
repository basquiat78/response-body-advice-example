package io.basquiat.response.api.member.usecase

import io.basquiat.response.api.member.request.QueryMember
import io.basquiat.response.domain.member.model.dto.MemberDto
import io.basquiat.response.domain.member.service.MemberQueryService
import io.basquiat.response.global.model.response.Pagination
import io.basquiat.response.global.utils.make
import org.springframework.stereotype.Service

/**
 * query all member usecase
 * created by basquiat
 */
@Service
class QueryAllMembersUseCase(
    private val query: MemberQueryService,
) {

    fun execute(request: QueryMember): Pair<List<MemberDto>, Pagination?> {
        val data = query.findByConditions(request.toQueryCondition())
                        .map(MemberDto::toDto)
        // 비어 있다면
        if(data.isEmpty()) {
            return emptyList<MemberDto>() to null
        }
        val size = request.size ?: 10
        return make(data, size)
    }

}