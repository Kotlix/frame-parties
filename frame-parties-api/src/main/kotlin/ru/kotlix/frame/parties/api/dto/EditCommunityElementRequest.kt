package ru.kotlix.frame.parties.api.dto

data class EditCommunityElementRequest(
    val communityId: String,
    val elementId: String,
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
