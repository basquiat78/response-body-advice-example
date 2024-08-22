package io.basquiat.response.domain.member.service

import io.basquiat.response.domain.member.model.entity.Member
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MemberMutationServiceTest @Autowired constructor(
    private val mutation: MemberMutationService,
    private val query: MemberQueryService,
) {

    @Test
    @DisplayName("create member test")
    fun createMemberTEST() {
        // given
        val entity = Member(
            name = "funnyjazz",
            nickName = "basquiat"
        )
        val created = mutation.createMember(entity)

        assertThat(created.nickName).isEqualTo("basquiat")
    }

    @Test
    @DisplayName("create member loop test")
    fun createMemberLoopTEST() {
        for(index in 1..20) {
            // given
            val entity = Member(
                name = "funnyjazz_$index",
                nickName = "basquiat_$index"
            )
            mutation.createMember(entity)
        }
    }

    @Test
    @DisplayName("update member test")
    fun updateMemberTEST() {
        // given
        val id: Long = 2
        val nickName = "changed Basquiat"

        val target = query.fetchByIdOrThrow(id)

        target.changeNickName(nickName)

        val updated = mutation.updateMember(target)

        assertThat(updated.nickName).isEqualTo(nickName)
    }

}
