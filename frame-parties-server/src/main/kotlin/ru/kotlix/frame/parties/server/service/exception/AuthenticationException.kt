package ru.kotlix.frame.parties.server.service.exception

open class AuthenticationException(msg: String, base: Exception?) : RuntimeException(msg, base)
