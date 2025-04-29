package ru.kotlix.frame.parties.api.dto.oldrequests

data class CreateCommunityRequest(
    val name: String,
    val desc: String,
    val public: Boolean
)
