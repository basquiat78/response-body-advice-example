package io.basquiat.response.domain.member.service

import io.basquiat.response.domain.member.model.vo.QueryCondition
import io.basquiat.response.global.exceptions.NotFoundException
import io.basquiat.response.global.utils.logger
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberQueryServiceTest @Autowired constructor(
    private val query: MemberQueryService,
) {

    private val log = logger<MemberQueryServiceTest>()

    @Test
    @DisplayName("fetchByIdOrNull Test")
    fun fetchByIdOrNullTEST() {
        // given
        val id: Long = 1

        // when
        val result = query.fetchByIdOrNull(id)

        assertThat(result).isNull()
    }

    @Test
    @DisplayName("fetchByIdOrThrow Test")
    fun fetchByIdOrThrowTEST() {
        // given
        val id: Long = 1

        // "멤버 아이디 [${id}]로 조회된 사용자 정보가 없습니다."
        assertThatThrownBy { query.fetchByIdOrThrow(id) }
            .isInstanceOf(NotFoundException::class.java)
            .hasMessageContaining("멤버 아이디 [1]로 조회된 사용자 정보가 없습니다.")
    }

    @Test
    @DisplayName("existByNickName Test")
    fun existByNickNameTEST() {
        // given
        val nickName = "basquiat"

        val isDuplicated = query.existByNickName(nickName)

        assertThat(isDuplicated).isFalse()
    }

    @Test
    @DisplayName("member query by conditions")
    fun findByConditionsTEST() {
        // given
        val conditions = QueryCondition(
            name = "",
            nickName = null,
            size = 10,
            nextId = null,
        )

        // when
        val result = query.findByConditions(conditions)

        assertThat(result).isEmpty()
    }

}
