package io.basquiat.response.global.model.response

import java.time.LocalDateTime

/**
 * 에러 공통 응답 객체
 * created by basquiat
 */
data class ApiErrorResponse(
    val status: Int,
    val error: String,
    val path: String,
    val timestamp: LocalDateTime,
)