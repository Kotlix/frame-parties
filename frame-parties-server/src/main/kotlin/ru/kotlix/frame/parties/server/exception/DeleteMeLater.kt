package ru.kotlix.frame.parties.server.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

// TODO: DELETE THIS

fun <ANY> todoex(): ANY = throw NotImplementedException()

fun todoex1(): Unit = throw NotImplementedException()

@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
class NotImplementedException : RuntimeException()
