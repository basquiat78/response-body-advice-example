package io.basquiat.response.global.configuraion

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * JPAQueryFactory @Bean으로 등록하기
 * created by basquiat
 *
 */
@Configuration
class QueryDSLConfiguration {

    @Bean
    fun jpaQueryFactory(em: EntityManager) = JPAQueryFactory(em)

}