package io.basquiat.response.global.model.response

/**
 * cursor pagination dto
 * 다음 조회할 아이디 정보와 현재 데이터가 마지막페이지인지를 담는 정보로 구성
 * created by basquiat
 */
data class Pagination(
    val nextId: Long?,
    val last: Boolean,
)
