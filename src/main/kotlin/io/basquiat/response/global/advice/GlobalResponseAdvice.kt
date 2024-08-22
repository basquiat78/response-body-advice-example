package io.basquiat.response.global.advice

import io.basquiat.response.global.model.response.ApiErrorResponse
import io.basquiat.response.global.model.response.ApiResponse
import io.basquiat.response.global.model.response.Pagination
import io.basquiat.response.global.utils.toJson
import org.springframework.core.MethodParameter
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

/**
 * rest api response body advice
 * created by basquiat
 */
@RestControllerAdvice
class GlobalResponseAdvice: ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        // 각 요청 메소드에 따라 httpStatus를 세분화해서 보내주도록 하자.
        // update, create, delete 시 변경 또는 생성된 데이터를 보내주는 경우도 있을테니
        val httpStatus = when (request.method) {
            HttpMethod.GET -> HttpStatus.OK.value()
            HttpMethod.POST, HttpMethod.PATCH, HttpMethod.PUT -> HttpStatus.CREATED.value()
            else -> HttpStatus.NO_CONTENT.value()
        }
        return when (body) {
            is ApiErrorResponse -> {
                body
            }
            is Pair<*, *> -> {
                val pagination = body.second as Pagination?
                ApiResponse.create(httpStatus, body.first, request.uri.path, pagination)
            }
            is String -> {
                val apiResponse = ApiResponse.create(httpStatus, body, request.uri.path)
                response.headers.contentType = MediaType.APPLICATION_JSON
                toJson(apiResponse)
            }
            else -> ApiResponse.create(httpStatus, body, request.uri.path)
        }
    }
}