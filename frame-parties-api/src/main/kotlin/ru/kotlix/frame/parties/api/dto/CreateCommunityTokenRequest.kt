package ru.kotlix.frame.parties.api.dto

data class CreateCommunityTokenRequest(
    val id: Int,
    val lifetime: String,
    val singleTime: Boolean
)
