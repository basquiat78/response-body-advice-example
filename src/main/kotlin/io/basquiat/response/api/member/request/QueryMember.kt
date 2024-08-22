package io.basquiat.response.api.member.request

import io.basquiat.response.domain.member.model.vo.QueryCondition
import io.basquiat.response.global.model.vo.PaginationVo

/**
 * member 검색 조건 요청 객체
 * created by basquiat
 */
data class QueryMember(
    val name: String?,
    val nickName: String?,
    override val size: Long?,
    override val nextId: Long?,
): PaginationVo(size, nextId) {
    fun toQueryCondition(): QueryCondition {
        return QueryCondition(
            name = name,
            nickName = nickName,
            nextId = nextId,
            size = size,
        )
    }
}
