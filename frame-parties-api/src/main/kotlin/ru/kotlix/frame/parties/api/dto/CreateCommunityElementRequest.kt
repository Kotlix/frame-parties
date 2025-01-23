package ru.kotlix.frame.parties.api.dto

data class CreateCommunityElementRequest(
    val communityId: String,
    val element: Element
) {
    data class Element(
        val name: String,
        val type: String,
        val number: Int,
        val parentCatalogId: String,
        val rolesIds: List<String>
    )
}