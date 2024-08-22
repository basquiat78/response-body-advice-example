package io.basquiat.response.global.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

/**
 * kotlin jackson object mapper
 */
val mapper: ObjectMapper = jacksonObjectMapper().apply {
    registerModule(JavaTimeModule())
}

/**
 * 객체를 받아서 json 스트링으로 반환한다.
 */
fun toJson(any: Any): String = mapper.writeValueAsString(any)

/**
 * json 스트링을 해당 객체로 매핑해서 반환한다.
 */
fun <T> convertToObject(json: String, valueType: Class<T>): T = mapper.readValue(json, valueType)