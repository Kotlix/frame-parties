package ru.kotlix.frame.parties.api.dto

data class CreateCatalogRequest(
    val communityId: Int,
    val element: Catalog
) {
    data class Catalog(
        val name: String,
        val type: String,
        val number: Int?, // if it is assigned by server
        val parentCatalogId: Int, // -1 if root?
        val rolesIds: List<Int>
    )
}
