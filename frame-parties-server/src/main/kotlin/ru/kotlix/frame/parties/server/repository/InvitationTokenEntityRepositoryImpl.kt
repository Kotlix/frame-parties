package ru.kotlix.frame.parties.server.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.kotlix.frame.parties.server.repository.dto.InvitationTokenEntity
import java.time.OffsetDateTime

@Repository
class InvitationTokenEntityRepositoryImpl(
    private val npJdbc: NamedParameterJdbcTemplate
) : InvitationTokenEntityRepository {

    companion object {
        private val ROW_MAPPER = RowMapper { rs, _ ->
            InvitationTokenEntity(
                id = rs.getLong("id"),
                createdAt = rs.getObject("created_at", OffsetDateTime::class.java),
                createdBy = rs.getLong("created_by"),
                token = rs.getString("token"),
                communityId = rs.getLong("community_id"),
                isOneTime = rs.getBoolean("is_one_time"),
                expiresAt = rs.getObject("expires_at", OffsetDateTime::class.java)
            )
        }
    }

    override fun findById(id: Long): InvitationTokenEntity? =
        npJdbc.query(
            """
                select * from invitation_token
                where id = :id
            """.trimIndent(),
            mapOf("id" to id),
            ROW_MAPPER
        ).firstOrNull()

    override fun save(entity: InvitationTokenEntity): InvitationTokenEntity =
        npJdbc.queryForObject(
            """
                insert into invitation_token
                (created_at, created_by, token, community_id, is_one_time, expires_at)
                values
                (:created_at, :created_by, :token, :community_id, :is_one_time, :expires_at)
                returning *;
            """.trimIndent(),
            mapOf(
                "created_at" to entity.createdAt,
                "created_by" to entity.createdBy,
                "token" to entity.token,
                "community_id" to entity.communityId,
                "is_one_time" to entity.isOneTime,
                "expires_at" to entity.expiresAt
            ),
            ROW_MAPPER
        )!!

    override fun findByToken(token: String): InvitationTokenEntity? =
        npJdbc.query(
            """
                select * from invitation_token
                where token = :token
            """.trimIndent(),
            mapOf("token" to token),
            ROW_MAPPER
        ).firstOrNull()
}