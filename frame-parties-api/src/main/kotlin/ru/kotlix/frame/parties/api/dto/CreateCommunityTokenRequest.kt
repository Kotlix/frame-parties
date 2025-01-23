package ru.kotlix.frame.parties.api.dto

data class CreateCommunityTokenRequest(
    val id: String,
    val lifetime: String,
    val singleTime: Boolean
)
