package io.basquiat.response.global.exceptions

/**
 * AlreadyExistException
 * 이미 존재하는 데이터에 대한 익셉션
 * created by basquiat
 */
class AlreadyExistException(message: String? = "Already Exist") : RuntimeException(message)