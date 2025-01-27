package ru.kotlix.frame.parties.api.dto

data class CreateCommunityElementRequest(
    val communityId: Int,
    val element: Element
) {
    data class Element(
        val name: String,
        val type: String,
        val number: Int,
        val parentCatalogId: Int,
        val rolesIds: List<Int>
    )
}