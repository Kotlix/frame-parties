package ru.kotlix.frame.parties.server.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.kotlix.frame.parties.server.repository.dto.CommunityEntity
import java.time.OffsetDateTime

@Repository
class CommunityEntityRepositoryImpl(
    private val npJdbc: NamedParameterJdbcTemplate
) : CommunityEntityRepository {

    companion object {
        private val ROW_MAPPER = RowMapper { rs, _ ->
            CommunityEntity(
                id = rs.getLong("id"),
                createdAt = rs.getObject("created_at", OffsetDateTime::class.java),
                updatedAt = rs.getObject("updated_at", OffsetDateTime::class.java),
                name = rs.getString("name"),
                isPublic = rs.getBoolean("is_public"),
                description = rs.getString("description")
            )
        }
    }

    override fun findById(id: Long): CommunityEntity? =
        npJdbc.query(
            """
                select * from community
                where id = :id;
            """.trimIndent(),
            mapOf("id" to id),
            ROW_MAPPER
        ).firstOrNull()

    override fun save(entity: CommunityEntity): CommunityEntity =
        npJdbc.queryForObject(
            """
                insert into community
                (created_at, updated_at, name, is_public, description)
                values
                (:created_at, :updated_at, :name, :is_public, :description)
                returning *;
            """.trimIndent(),
            mapOf(
                "created_at" to entity.createdAt,
                "updated_at" to entity.updatedAt,
                "name" to entity.name,
                "is_public" to entity.isPublic,
                "description" to entity.description
            ),
            ROW_MAPPER
        )!!

    override fun findAllPublic(pageNumber: Int, pageSize: Int): List<CommunityEntity> =
        npJdbc.query(
            """
                select * from community
                where is_public = true
                order by created_at
                limit :limit
                offset :offset;
            """.trimIndent(),
            mapOf(
                "limit" to pageSize,
                "offset" to pageNumber * pageSize
            ),
            ROW_MAPPER
        )

    override fun findAllPublicByName(name: String, pageNumber: Int, pageSize: Int): List<CommunityEntity> =
        npJdbc.query(
            """
                select * from community
                where is_public = true and
                    name ~ :name
                order by created_at
                limit :limit
                offset :offset;
            """.trimIndent(),
            mapOf(
                "name" to name,
                "limit" to pageSize,
                "offset" to pageNumber * pageSize
            ),
            ROW_MAPPER
        )
}