package ru.kotlix.frame.parties.api.dto

data class CreateVoiceChatRequest(
    val communityId: String,
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
