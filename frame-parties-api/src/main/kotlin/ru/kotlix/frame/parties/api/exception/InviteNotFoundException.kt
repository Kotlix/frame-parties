package ru.kotlix.frame.parties.api.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class InviteNotFoundException(token: String)
    : RuntimeException("Invite with token='$token' not found or expired")