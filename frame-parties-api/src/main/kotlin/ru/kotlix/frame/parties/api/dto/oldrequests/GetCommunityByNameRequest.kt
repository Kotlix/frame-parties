package ru.kotlix.frame.parties.api.dto.oldrequests

data class GetCommunityByNameRequest(
    val promt: String,
    val from: Int, //beginning
    val limit: Int, // number of results
    val searchByName: Boolean,
    val searchByDesc: Boolean,
    val searchFromBeginning: Boolean
)
