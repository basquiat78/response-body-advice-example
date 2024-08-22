package io.basquiat.response.global.advice

import io.basquiat.response.global.exceptions.NotFoundException
import io.basquiat.response.global.model.response.ApiErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime.now


/**
 * rest api error advice
 * created by basquiat
 */
@RestControllerAdvice
class GlobalExceptionAdvice {

    /**
     * Inter server error 처리
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception, request: HttpServletRequest) = ApiErrorResponse(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        error = ex.message!!,
        path = request.requestURI,
        timestamp = now(),
    )

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    protected fun handleNotFoundException(ex: NotFoundException, request: HttpServletRequest) = ApiErrorResponse(
        status = HttpStatus.NOT_FOUND.value(),
        error = ex.message!!,
        path = request.requestURI,
        timestamp = now(),
    )
}