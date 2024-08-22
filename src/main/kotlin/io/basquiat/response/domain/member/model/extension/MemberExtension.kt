package io.basquiat.response.domain.member.model.extension

import com.querydsl.core.annotations.QueryDelegate
import com.querydsl.core.annotations.QueryEntity
import com.querydsl.core.types.Predicate
import io.basquiat.response.domain.member.model.entity.Member
import io.basquiat.response.domain.member.model.entity.QMember

/**
 * QMember 위임 객체
 * created by basquiat
 */
@QueryEntity
class MemberExtension {
    companion object {
        @JvmStatic
        @QueryDelegate(Member::class)
        fun eqName(member: QMember, name: String?): Predicate? = if(!name.isNullOrBlank()) {
            member.name.eq(name)
        } else {
            null
        }

        @JvmStatic
        @QueryDelegate(Member::class)
        fun eqNickName(member: QMember, nickName: String?): Predicate? = if(!nickName.isNullOrBlank()) {
            member.nickName.eq(nickName)
        } else {
            null
        }

        @JvmStatic
        @QueryDelegate(Member::class)
        fun nextId(member: QMember, nextId: Long?): Predicate? = nextId?.let { member.id.lt(it) }

    }
}
