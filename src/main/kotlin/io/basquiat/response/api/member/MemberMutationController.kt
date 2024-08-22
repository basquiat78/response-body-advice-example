package io.basquiat.response.api.member

import io.basquiat.response.api.member.request.CreateMember
import io.basquiat.response.api.member.request.UpdateMember
import io.basquiat.response.api.member.usecase.CreateMemberUseCase
import io.basquiat.response.api.member.usecase.UpdateMemberUseCase
import org.springframework.web.bind.annotation.*

/**
 * member mutation controller
 * created by basquiat
 */
@RestController
@RequestMapping("/members")
class MemberMutationController(
    private val createMemberUseCase: CreateMemberUseCase,
    private val updateMemberUseCase: UpdateMemberUseCase,
) {

    @PostMapping("")
    fun createMember(@RequestBody request: CreateMember): String {
        return createMemberUseCase.execute(request)
    }

    @PatchMapping("/{id}")
    fun updateMember(@PathVariable("id") id: Long, @RequestBody request: UpdateMember): String {
        return updateMemberUseCase.execute(id, request)
    }

}