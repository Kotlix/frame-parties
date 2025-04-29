package ru.kotlix.frame.parties.api.dto.oldrequests

data class CreateCommunityTokenRequest(
    val id: Int,
    val lifetime: String,
    val singleTime: Boolean
)
