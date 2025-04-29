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
        private val ROW_MAPPER = RowMapper { rs, _ ->
            MembershipRoleEntity(
                id = rs.getLong("id"),
                assignedAt = rs.getObject("assigned_at", OffsetDateTime::class.java),
                userId = rs.getLong("user_id"),
                communityId = rs.getLong("community_id"),
                roleId = rs.getLong("role_id")
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
            ROW_MAPPER
        ).firstOrNull()

    override fun save(entity: MembershipRoleEntity): MembershipRoleEntity =
        npJdbc.queryForObject(
            """
                insert into membership_role
                (assigned_at, user_id, community_id, role_id)
                values
                (:assigned_at, :user_id, :community_id, :role_id)
                returning *;
            """.trimIndent(),
            mapOf(
                "assigned_at" to entity.assignedAt,
                "user_id" to entity.userId,
                "community_id" to entity.communityId,
                "role_id" to entity.roleId
            ),
            ROW_MAPPER
        )!!

    override fun findAllByUserIdAndCommunityId(userId: Long, communityId: Long): List<MembershipRoleEntity> =
        npJdbc.query(
            """
                select * from membership_role
                where user_id = :user_id
                    and community_id = :community_id;
            """.trimIndent(),
            mapOf(
                "user_id" to userId,
                "community_id" to communityId
            ),
            ROW_MAPPER
        )
}