package ru.kotlix.frame.parties.api.dto.oldrequests

data class CreateChannelRequest(
    val communityId: Int,
    val element: Channel
) {
    data class Channel(
        val name: String,
        val type: String,
        val number: Int?, // if it is assigned by server
        val parentCatalogId: String,
        val rolesIds: List<Int>
    )
}
