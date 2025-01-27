package ru.kotlix.frame.parties.api.dto

data class CreateChatRequest(
    val communityId: Int,
    val element: Chat
) {
    data class Chat(
        val name: String,
        val type: String,
        val number: Int,
        val parentCatalogId: Int,
        val rolesIds: List<Int>
    )
}
