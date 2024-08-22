package io.basquiat.response.global.model.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime
import java.time.LocalDateTime.now

/**
 * Rest API response 정보를 담은 객체
 */
@JsonPropertyOrder("status", "data", "path", "timestamp", "pagination")
data class ApiResponse<T>(
    val status: Int,

    val data: T,

    val path: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    val timestamp: LocalDateTime,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val pagination: Pagination?
) {
    companion object {
        /**
         * ApiResponse를 생성하는 정적 메소드
         * @param status
         * @param data
         * @param path
         * @param pagination
         * @return ResponseResult<T>
         */
        fun <T> create(
            status: Int,
            data: T,
            path: String,
            pagination: Pagination? = null
        ) = ApiResponse(
            status, data, path, now(), pagination
        )
    }

}