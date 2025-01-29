package ru.kotlix.frame.parties.api.dto

data class EditChannelRequest(
    val communityId: Int,
    val elementId: Int,
    val element: Channel
) {
    data class Channel(
        val name: String,
        val type: String,
        val number: Int,
        val parentCatalogId: Int,
        val rolesIds: List<Int>
    )
}
