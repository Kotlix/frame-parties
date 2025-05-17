package ru.kotlix.frame.parties.server.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.kotlix.frame.parties.server.repository.dto.CommunityEntity
import java.time.OffsetDateTime

@Repository
class CommunityEntityRepositoryImpl(
    private val npJdbc: NamedParameterJdbcTemplate,
) : CommunityEntityRepository {
    companion object {
        private val ROW_MAPPER =
            RowMapper { rs, _ ->
                CommunityEntity(
                    id = rs.getLong("id"),
                    createdAt = rs.getObject("created_at", OffsetDateTime::class.java),
                    updatedAt = rs.getObject("updated_at", OffsetDateTime::class.java),
                    name = rs.getString("name"),
                    isPublic = rs.getBoolean("is_public"),
                    description = rs.getString("description"),
                    voiceName = rs.getString("voice_name"),
                    voiceRegion = rs.getString("voice_region"),
                    creatorId = rs.getLong("creator_id"),
                    deleted = rs.getBoolean("deleted"),
                )
            }
    }

    override fun findById(id: Long): CommunityEntity? =
        npJdbc.query(
            """
            select * from community
            where id = :id
                and deleted = false;
            """.trimIndent(),
            mapOf("id" to id),
            ROW_MAPPER,
        ).firstOrNull()

    override fun save(entity: CommunityEntity): CommunityEntity =
        npJdbc.queryForObject(
            """
            insert into community
            (created_at, updated_at, name, is_public, description, voice_name, voice_region, deleted, creator_id)
            values
            (:created_at, :updated_at, :name, :is_public, :description, :voice_name, :voice_region, :deleted, :creator_id)
            returning *;
            """.trimIndent(),
            mapOf(
                "created_at" to entity.createdAt,
                "updated_at" to entity.updatedAt,
                "name" to entity.name,
                "is_public" to entity.isPublic,
                "description" to entity.description,
                "voice_name" to entity.voiceName,
                "voice_region" to entity.voiceRegion,
                "creator_id" to entity.creatorId,
                "deleted" to entity.deleted,
            ),
            ROW_MAPPER,
        )!!

    override fun update(entity: CommunityEntity): CommunityEntity =
        npJdbc.queryForObject(
            """
            update community
            set updated_at = :updated_at,
                name = :name,
                is_public = :is_public,
                description = :description,
                voice_name = :voice_name,
                voice_region = :voice_region,
                deleted = :deleted
            where id = :id
            returning *;
            """.trimIndent(),
            mapOf(
                "id" to entity.id,
                "updated_at" to entity.updatedAt,
                "name" to entity.name,
                "is_public" to entity.isPublic,
                "description" to entity.description,
                "voice_name" to entity.voiceName,
                "voice_region" to entity.voiceRegion,
                "deleted" to entity.deleted,
            ),
            ROW_MAPPER,
        )!!

    override fun findAllPublic(
        pageNumber: Long,
        pageSize: Long,
    ): List<CommunityEntity> =
        npJdbc.query(
            """
            select * from community
            where is_public = true
                and deleted = false
            order by created_at
            limit :limit
            offset :offset;
            """.trimIndent(),
            mapOf(
                "limit" to pageSize,
                "offset" to pageNumber * pageSize,
            ),
            ROW_MAPPER,
        )

    override fun findAllPublicByName(
        name: String,
        pageNumber: Long,
        pageSize: Long,
    ): List<CommunityEntity> =
        npJdbc.query(
            """
            select * from community
            where is_public = true
                and deleted = false
                and name ~ :name
            order by created_at
            limit :limit
            offset :offset;
            """.trimIndent(),
            mapOf(
                "name" to name,
                "limit" to pageSize,
                "offset" to pageNumber * pageSize,
            ),
            ROW_MAPPER,
        )

    override fun findAllByCreatorId(creatorId: Long): List<CommunityEntity> =
        npJdbc.query(
            """
            select * from community
            where creator_id = :creator_id
                and deleted = false
            order by created_at;
            """.trimIndent(),
            mapOf(
                "creator_id" to creatorId,
            ),
            ROW_MAPPER,
        )

    override fun findAllByUserId(userId: Long): List<CommunityEntity> =
        npJdbc.query(
            """
            select c.* from
            community c join membership m on c.id = m.community_id
            where m.user_id = :user_id
                and c.deleted = false
            order by m.joined_at;
            """.trimIndent(),
            mapOf(
                "user_id" to userId,
            ),
            ROW_MAPPER,
        )
}
