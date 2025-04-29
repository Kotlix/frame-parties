package ru.kotlix.frame.parties.api.dto.oldrequests

data class EditChatRequest(
    val communityId: String,
    val elementId: String,
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
