package io.basquiat.response.api.member

import io.basquiat.response.api.member.request.QueryMember
import io.basquiat.response.api.member.usecase.QueryAllMembersUseCase
import io.basquiat.response.api.member.usecase.QueryMemberUseCase
import io.basquiat.response.domain.member.model.dto.MemberDto
import io.basquiat.response.global.model.response.Pagination
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * member controller
 * created by basquiat
 */
@RestController
@RequestMapping("/members")
class MemberQueryController(
    private val queryMemberUseCase: QueryMemberUseCase,
    private val queryAllMembersUseCase: QueryAllMembersUseCase,
) {

    @GetMapping("")
    fun memberById(request: QueryMember): Pair<List<MemberDto>, Pagination?> {
        return queryAllMembersUseCase.execute(request)
    }

    @GetMapping("/{id}")
    fun memberById(@PathVariable("id") id: Long): MemberDto {
        return queryMemberUseCase.execute(id)
    }
}