package ru.kotlix.frame.parties.api.dto

data class EditVoiceChatRequest(
    val communityId: Int,
    val elementId: Int,
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
