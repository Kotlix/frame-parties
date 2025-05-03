package ru.kotlix.frame.parties.server.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.kotlix.frame.parties.server.repository.dto.MembershipRoleEntity
import java.time.OffsetDateTime

@Repository
class MembershipRoleEntityRepositoryImpl(
    private val npJdbc: NamedParameterJdbcTemplate,
) : MembershipRoleEntityRepository {
    companion object {
        private val ROW_MAPPER =
            RowMapper { rs, _ ->
                MembershipRoleEntity(
                    id = rs.getLong("id"),
                    assignedAt = rs.getObject("assigned_at", OffsetDateTime::class.java),
                    membershipId = rs.getLong("membership_id"),
                    roleId = rs.getLong("role_id"),
                )
            }
    }

    override fun findById(id: Long): MembershipRoleEntity? =
        npJdbc.query(
            """
            select * from membership_role
            where id = :id;
            """.trimIndent(),
            mapOf("id" to id),
            ROW_MAPPER,
        ).firstOrNull()

    override fun save(entity: MembershipRoleEntity): MembershipRoleEntity =
        npJdbc.queryForObject(
            """
            insert into membership_role
            (assigned_at, membership_id, role_id)
            values
            (:assigned_at, :membership_id, :role_id)
            returning *;
            """.trimIndent(),
            mapOf(
                "assigned_at" to entity.assignedAt,
                "membership_id" to entity.membershipId,
                "role_id" to entity.roleId,
            ),
            ROW_MAPPER,
        )!!

    override fun findAllByMembershipId(membershipId: Long): List<MembershipRoleEntity> =
        npJdbc.query(
            """
            select * from membership_role
                where membership_id = :membership_id;
            """.trimIndent(),
            mapOf(
                "membership_id" to membershipId,
            ),
            ROW_MAPPER,
        )

    override fun remove(entity: MembershipRoleEntity) {
        npJdbc.update(
            """
            delete from membership_role
                where id = :id;
            """.trimIndent(),
            mapOf(
                "id" to entity.id!!,
            ),
        )
    }

    override fun removeAllByMembershipId(membershipId: Long) {
        npJdbc.update(
            """
            delete from membership_role
                where membership_id = :membership_id;
            """.trimIndent(),
            mapOf(
                "membership_id" to membershipId,
            ),
        )
    }
}
