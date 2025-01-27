package ru.kotlix.frame.parties.api.dto

data class CreateVoiceChatRequest(
    val communityId: Int,
    val element: VoiceChat
) {
    data class VoiceChat(
        val name: String,
        val type: String,
        val number: Int,
        val parentCatalogId: Int,
        val rolesIds: List<Int>
    )
}
