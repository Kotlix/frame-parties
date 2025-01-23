package ru.kotlix.frame.parties.api.dto

data class EditChatRequest(
    val communityId: String,
    val elementId: String,
    val element: Chat
) {
    data class Chat(
        val name: String,
        val type: String,
        val number: Int,
        val parentCatalogId: String,
        val rolesIds: List<String>
    )
}
