package io.basquiat.response.domain.member.repository

import io.basquiat.response.domain.member.model.entity.Member
import io.basquiat.response.domain.member.repository.custom.CustomMemberRepository
import io.basquiat.response.global.repository.BaseRepository

interface MemberRepository: BaseRepository<Member, Long>, CustomMemberRepository