package io.basquiat.response.api.member.usecase

import io.basquiat.response.api.member.request.UpdateMember
import io.basquiat.response.domain.member.service.MemberQueryService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

/**
 * create member usecase
 * created by basquiat
 */
@Service
class UpdateMemberUseCase(
    private val query: MemberQueryService,
) {

    @Transactional
    fun execute(id: Long, request: UpdateMember): String {
        val targetMember = query.fetchByIdOrThrow(id)
        targetMember.changeNickName(request.nickName)
        return "업데이트가 성공했습니다."
    }

}