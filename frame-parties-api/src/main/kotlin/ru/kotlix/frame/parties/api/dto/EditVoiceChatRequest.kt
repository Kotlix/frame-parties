package ru.kotlix.frame.parties.api.dto

data class EditVoiceChatRequest(
    val communityId: String,
    val elementId: String,
    val element: VoiceChat
) {
    data class VoiceChat(
        val name: String,
        val type: String,
        val number: Int,
        val parentCatalogId: String,
        val rolesIds: List<String>
    )
}
