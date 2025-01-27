package ru.kotlix.frame.parties.api.dto.entities

data class Role(
    val id: Int,
    val name: String,
    val priority: Int,
    val rightsToChangeRights: Boolean,
    val changeableRights: List<String>?,
    val inheritedRights: List<String>?
)
