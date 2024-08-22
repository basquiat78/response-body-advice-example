package io.basquiat.response.domain.member.repository.custom.impl

import com.querydsl.jpa.impl.JPAQueryFactory
import io.basquiat.response.domain.member.model.entity.Member
import io.basquiat.response.domain.member.model.entity.QMember.member
import io.basquiat.response.domain.member.model.vo.QueryCondition
import io.basquiat.response.domain.member.repository.custom.CustomMemberRepository

/**
 * CustomMemberRepository 구현체
 * created by basquiat
 */
class CustomMemberRepositoryImpl(
    private val dsl: JPAQueryFactory,
): CustomMemberRepository {

    /**
     * @param conditions
     * return List<Wallet>
     */
    override fun findByConditions(conditions: QueryCondition): List<Member> {
        return dsl.selectFrom(member)
                  .where(
                    member.eqName(conditions.name),
                    member.eqNickName(conditions.nickName),
                    member.nextId(conditions.nextId)
                  )
                  .limit(conditions.limit())
                  .orderBy(member.id.desc())
                  .fetch()
    }

    /**
     * nick name은 Unique로 nick name 중복 체크가 필요하다.
     * @param nickName
     * @return Boolean
     */
    override fun existNickName(nickName: String): Boolean {
        val fetch = dsl.selectOne()
                       .from(member)
                       .where(
                           member.nickName.eq(nickName)
                       ).fetchFirst()
        return fetch != null
    }
}