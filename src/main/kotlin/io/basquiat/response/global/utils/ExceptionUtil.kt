package io.basquiat.response.global.utils

import io.basquiat.response.global.exceptions.AlreadyExistException
import io.basquiat.response.global.exceptions.NotFoundException

/**
 * 메세지가 없는 경우
 */
fun already(): Nothing {
    throw AlreadyExistException()
}

/**
 * 메세지가 있는 경우
 * @param message
 */
fun already(message: String?): Nothing {
    if(message == null) {
        already()
    } else {
        throw AlreadyExistException(message)
    }
}

/**
 * 메세지가 없는 경우
 */
fun entityEmpty(): Nothing {
    throw NotFoundException()
}

/**
 * 메세지가 있는 경우
 * @param message
 */
fun entityEmpty(message: String?): Nothing {
    if(message == null) {
        entityEmpty()
    } else {
        throw NotFoundException(message)
    }
}