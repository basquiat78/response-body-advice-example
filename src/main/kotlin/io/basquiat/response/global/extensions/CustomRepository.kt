package io.basquiat.response.global.extensions

import io.basquiat.response.global.utils.entityEmpty
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

/**
 * null을 반환하지 않고 EntityEmptyException에러를 날리자.
 * created by basquiat
 */
fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID, message: String? = null): T = this.findByIdOrNull(id) ?: entityEmpty(message)