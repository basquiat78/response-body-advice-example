package io.basquiat.response.domain.member.model.vo

/**
 * 검색 조건 vo
 * created by basquiat
 */
data class QueryCondition(
    val name: String?,
    val nickName: String?,
    val nextId: Long?,
    val size: Long?,
) {
    fun limit(): Long {
        return (size ?: 10) + 1
    }

}
