package ru.kotlix.frame.parties.api.dto

data class DeleteCommunityElementRequest(
    val communityId: Int,
    val elementId: Int
)
