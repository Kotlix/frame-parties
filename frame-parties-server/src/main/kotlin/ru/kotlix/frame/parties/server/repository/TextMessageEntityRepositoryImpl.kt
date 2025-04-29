package ru.kotlix.frame.parties.server.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.kotlix.frame.parties.server.repository.dto.TextMessageEntity
import java.time.OffsetDateTime

@Repository
class TextMessageEntityRepositoryImpl(
    private val npJdbc: NamedParameterJdbcTemplate
) : TextMessageEntityRepository {

    companion object {
        private val ROW_MAPPER = RowMapper { rs, _ ->
            TextMessageEntity(
                id = rs.getLong("id"),
                createdAt = rs.getObject("created_at", OffsetDateTime::class.java),
                chatId = rs.getLong("chat_id"),
                userId = rs.getLong("user_id"),
                message = rs.getString("message")
            )
        }
    }

    override fun findById(id: Long): TextMessageEntity? =
        npJdbc.query(
            """
                select * from text_message
                where id = :id;
            """.trimIndent(),
            mapOf("id" to id),
            ROW_MAPPER
        ).firstOrNull()

    override fun save(entity: TextMessageEntity): TextMessageEntity =
        npJdbc.queryForObject(
            """
                insert into text_message
                (created_at, chat_id, user_id, message)
                values
                (:created_at, :chat_id, :user_id, :message)
                returning *;
            """.trimIndent(),
            mapOf(
                "created_at" to entity.createdAt,
                "chat_id" to entity.chatId,
                "user_id" to entity.userId,
                "message" to entity.message
            ),
            ROW_MAPPER
        )!!

    override fun findAllByChatId(chatId: Long): List<TextMessageEntity> =
        npJdbc.query(
            """
                select * from text_message
                where chat_id = :chat_id;
            """.trimIndent(),
            mapOf("chat_id" to chatId),
            ROW_MAPPER
        )
}