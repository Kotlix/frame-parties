package ru.kotlix.frame.parties.server.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
open class NotFoundException(source: String) : RuntimeException("$source not found.") {
    class CommunityById(id: Long) :
        NotFoundException("Community by id=$id")

    class Token(token: String) :
        NotFoundException("Token=$token")

    class RoleById(id: Long) :
        NotFoundException("Role by id=$id")

    class ServerByRegionAndName(region: String, name: String) :
        NotFoundException("Server by region=$region and name=$name")

    class DirectoryById(id: Long) :
        NotFoundException("Directory by id=$id")

    class ChatById(id: Long) :
        NotFoundException("Chat by id=$id")
}
