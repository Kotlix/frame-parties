package ru.kotlix.frame.parties.server.repository

import ru.kotlix.frame.parties.server.repository.dto.TextMessageEntity

interface TextMessageEntityRepository {
    fun findById(id: Long): TextMessageEntity?

    fun save(entity: TextMessageEntity): TextMessageEntity

    fun findAllByChatId(chatId: Long): List<TextMessageEntity>
}