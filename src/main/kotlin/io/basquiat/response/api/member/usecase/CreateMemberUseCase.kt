package io.basquiat.response.api.member.usecase

import io.basquiat.response.api.member.request.CreateMember
import io.basquiat.response.domain.member.service.MemberMutationService
import io.basquiat.response.domain.member.service.MemberQueryService
import io.basquiat.response.global.utils.already
import org.springframework.stereotype.Service

/**
 * create member usecase
 * created by basquiat
 */
@Service
class CreateMemberUseCase(
    private val mutation: MemberMutationService,
    private val query: MemberQueryService,
) {

    fun execute(request: CreateMember): String {
        val isDuplicated = query.existByNickName(request.nickName)
        if(isDuplicated) already("[${request.nickName}]라는 nickName이 이미 존재합니다.")
        mutation.createMember(request.toEntity())
        return "멤버가 생성되었습니다."
    }

}