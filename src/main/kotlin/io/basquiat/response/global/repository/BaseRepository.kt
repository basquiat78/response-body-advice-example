package io.basquiat.response.global.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.io.Serializable

/**
 * jpaRepository Base -> 도메인별 레파지토리 생성시 해당 인터페이스를 상속해서 사용한다.
 * @param M entity
 * @param ID entity ID
 * created by basquiat
 */
@NoRepositoryBean
interface BaseRepository<M, ID : Serializable?> : JpaRepository<M, ID>, JpaSpecificationExecutor<M>