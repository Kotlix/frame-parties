package ru.kotlix.frame.parties.server.repository

import com.fasterxml.jackson.databind.ObjectMapper
import org.postgresql.util.PGobject
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.kotlix.frame.parties.server.repository.dto.RoleEntity
import java.time.OffsetDateTime

@Repository
class RoleEntityRepositoryImpl(
    private val npJdbc: NamedParameterJdbcTemplate,
    private val objectMapper: ObjectMapper,
) : RoleEntityRepository {
    private val rowMapper =
        RowMapper { rs, _ ->
            RoleEntity(
                id = rs.getLong("id"),
                createdAt = rs.getObject("created_at", OffsetDateTime::class.java),
                updatedAt = rs.getObject("updated_at", OffsetDateTime::class.java),
                communityId = rs.getLong("community_id"),
                name = rs.getString("name"),
                priority = rs.getInt("priority"),
                protected = rs.getBoolean("protected"),
                permissionSet = readRights(rs.getObject("rights", PGobject::class.java)),
            )
        }

    private fun writeRights(obj: RoleEntity.PermissionSet): PGobject =
        PGobject().apply {
            type = "json"
            value = objectMapper.writeValueAsString(obj)
        }

    private fun readRights(obj: PGobject): RoleEntity.PermissionSet =
        objectMapper.readValue(obj.value, RoleEntity.PermissionSet::class.java)

    override fun findById(id: Long): RoleEntity? =
        npJdbc.query(
            """
            select * from role
            where id = :id;
            """.trimIndent(),
            mapOf("id" to id),
            rowMapper,
        ).firstOrNull()

    override fun save(entity: RoleEntity): RoleEntity =
        npJdbc.queryForObject(
            """
            insert into role
            (created_at, updated_at, community_id, name, priority, protected, rights)
            values
            (:created_at, :updated_at, :community_id, :name, :priority, :protected, :rights)
            returning *;
            """.trimIndent(),
            mapOf(
                "created_at" to entity.createdAt,
                "updated_at" to entity.updatedAt,
                "community_id" to entity.communityId,
                "name" to entity.name,
                "priority" to entity.priority,
                "protected" to entity.protected,
                "rights" to writeRights(entity.permissionSet),
            ),
            rowMapper,
        )!!

    override fun remove(entity: RoleEntity) {
        npJdbc.update(
            """
            delete from role
                where id = :id;
            """.trimIndent(),
            mapOf(
                "id" to entity.id!!,
            ),
        )
    }

    override fun update(entity: RoleEntity): RoleEntity =
        npJdbc.queryForObject(
            """
            update role
            set updated_at = :updated_at,
                community_id = :community_id,
                name = :name,
                priority = :priority,
                protected = :protected,
                rights = :rights
            where id = :id
            returning *;
            """.trimIndent(),
            mapOf(
                "updated_at" to entity.updatedAt,
                "community_id" to entity.communityId,
                "name" to entity.name,
                "priority" to entity.priority,
                "protected" to entity.protected,
                "rights" to writeRights(entity.permissionSet),
            ),
            rowMapper,
        )!!

    override fun findAllByCommunityId(communityId: Long): List<RoleEntity> =
        npJdbc.query(
            """
            select * from role
            where community_id = :community_id;
            """.trimIndent(),
            mapOf("community_id" to communityId),
            rowMapper,
        )

    override fun findAllByMembershipId(membershipId: Long): List<RoleEntity> =
        npJdbc.query(
            """
            select * from role
            where id in (
                select role_id from membership_role
                where membership_id = :membership_id
            )
            order by priority desc;
            """.trimIndent(),
            mapOf(
                "membership_id" to membershipId,
            ),
            rowMapper,
        )
}
