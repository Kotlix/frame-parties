package ru.kotlix.frame.parties.server.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.kotlix.frame.parties.server.repository.dto.ChatEntity
import java.time.OffsetDateTime

@Repository
class ChatEntityRepositoryImpl(
    private val npJdbc: NamedParameterJdbcTemplate
) : ChatEntityRepository {

    companion object {
        private val ROW_MAPPER = RowMapper { rs, _ ->
            ChatEntity(
                id = rs.getLong("id"),
                createdAt = rs.getObject("created_at", OffsetDateTime::class.java),
                updatedAt = rs.getObject("created_at", OffsetDateTime::class.java),
                communityId = rs.getLong("community_id"),
                name = rs.getString("name"),
                parentDirectoryId = rs.getLong("parent_directory_id"),
                pos = rs.getInt("pos")
            )
        }
    }

    override fun findById(id: Long): ChatEntity? =
        npJdbc.query(
            """
                select * from chat
                where id = :id;
            """.trimIndent(),
            mapOf("id" to id),
            ROW_MAPPER
        ).firstOrNull()

    override fun save(entity: ChatEntity): ChatEntity =
        npJdbc.queryForObject(
            """
                insert into chat
                (created_at, updated_at, community_id, name, parent_directory_id, pos)
                values
                (:created_at, :updated_at, :community_id, :name, :parent_directory_id, :pos)
                returning *;
            """.trimIndent(),
            mapOf(
                "created_at" to entity.createdAt,
                "updated_at" to entity.updatedAt,
                "community_id" to entity.communityId,
                "name" to entity.name,
                "parent_directory_id" to entity.parentDirectoryId,
                "pos" to entity.pos
            ),
            ROW_MAPPER
        )!!

    override fun findAllByCommunityId(communityId: Long): List<ChatEntity> =
        npJdbc.query(
            """
                select * from chat
                where community_id = :community_id;
            """.trimIndent(),
            mapOf("community_id" to communityId),
            ROW_MAPPER
        )
}