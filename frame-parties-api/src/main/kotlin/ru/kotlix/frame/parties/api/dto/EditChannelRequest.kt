package ru.kotlix.frame.parties.api.dto

data class EditChannelRequest(
    val communityId: String,
    val elementId: String,
    val element: Channel
) {
    data class Channel(
        val name: String,
        val type: String,
        val number: Int,
        val parentCatalogId: String,
        val rolesIds: List<String>
    )
}
