package ru.kotlix.frame.parties.server.service

interface AuthenticationService {
    fun authenticateByToken(token: String): Boolean
}
