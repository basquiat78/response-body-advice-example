package io.basquiat.response.global.utils

import io.basquiat.response.global.model.response.Pagination
import io.basquiat.response.global.type.LongIdentifiable

fun <T> isLast(data: List<T>, size: Long): Boolean {
    val dataSize = data.size
    return dataSize <= size.toInt()
}

fun <T: LongIdentifiable> getNextId(data: List<T>): Long? {
    if(data.isEmpty()) {
        return null
    }
    return data.last().id
}

fun <T: LongIdentifiable> make(data: List<T>, size: Long): Pair<List<T>, Pagination> {
    val isLast = isLast(data, size)
    // 마지막 페이지라면 조회한 리스트를 그대로
    val result = if(isLast) data else data.dropLast(1)
    // 마지막 페이지라면 nextId는 null로 세팅
    val nextId = if(isLast) null else getNextId(result)
    val pagination = Pagination(
        nextId = nextId,
        last = isLast
    )
    return result to pagination
}