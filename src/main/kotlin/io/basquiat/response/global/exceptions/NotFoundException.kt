package io.basquiat.response.global.exceptions

/**
 * NotFoundException
 * 조회시 entity 정보가 null일때 사용한다.
 * created by basquiat
 */
class NotFoundException(message: String? = "Not found.") : RuntimeException(message)