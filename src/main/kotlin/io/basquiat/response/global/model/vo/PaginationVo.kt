package io.basquiat.response.global.model.vo

/**
 * base search conditon vo
 * 이 부분만 사용될 수 있기에 abstract가 아닌 open class로 생성한다.
 * created by basquiat
 */
open class PaginationVo(
    open val size: Long?,
    open val nextId: Long?,
)
