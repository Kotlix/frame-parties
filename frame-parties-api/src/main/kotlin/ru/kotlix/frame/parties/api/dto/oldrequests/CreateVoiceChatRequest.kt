package ru.kotlix.frame.parties.api.dto.oldrequests

data class CreateVoiceChatRequest(
    val communityId: Int,
    val element: VoiceChat
) {
    data class VoiceChat(
        val name: String,
        val type: String,
        val number: Int?, // if it is assigned by server
        val parentCatalogId: Int,
        val rolesIds: List<Int>
    )
}
