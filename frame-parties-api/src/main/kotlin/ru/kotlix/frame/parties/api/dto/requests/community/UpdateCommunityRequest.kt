package ru.kotlix.frame.parties.api.dto.requests.community

data class UpdateCommunityRequest(
    val name: String,
    val desc: String?,
    val is_public: Boolean,
    val created_at: String,
    val updated_at: String
)