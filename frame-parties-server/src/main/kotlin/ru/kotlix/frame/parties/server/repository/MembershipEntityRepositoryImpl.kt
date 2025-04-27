package ru.kotlix.frame.parties.server.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.kotlix.frame.parties.server.repository.dto.MembershipEntity
import java.time.OffsetDateTime

@Repository
class MembershipEntityRepositoryImpl(
    private val npJdbc: NamedParameterJdbcTemplate
) : MembershipEntityRepository {

    companion object {
        private val ROW_MAPPER = RowMapper { rs, _ ->
            MembershipEntity(
                id = rs.getLong("id"),
                joinedAt = rs.getObject("joined_at", OffsetDateTime::class.java),
                userId = rs.getLong("user_id"),
                communityId = rs.getLong("community_id")
            )
        }
    }

    override fun findById(id: Long): MembershipEntity? =
        npJdbc.query(
            """
                select * from membership
                where id = :id;
            """.trimIndent(),
            mapOf("id" to id),
            ROW_MAPPER
        ).firstOrNull()

    override fun save(entity: MembershipEntity): MembershipEntity =
        npJdbc.queryForObject(
            """
                insert into membership
                (joined_at, user_id, community_id)
                values
                (:joined_at, :user_id, :community_id)
                returning *;
            """.trimIndent(),
            mapOf(
                "joined_at" to entity.joinedAt,
                "user_id" to entity.userId,
                "community_id" to entity.communityId
            ),
            ROW_MAPPER
        )!!

    override fun findAllByUserId(userId: Long): List<MembershipEntity> =
        npJdbc.query(
            """
                select * from membership
                where user_id = :user_id;
            """.trimIndent(),
            mapOf("user_id" to userId),
            ROW_MAPPER
        )
}