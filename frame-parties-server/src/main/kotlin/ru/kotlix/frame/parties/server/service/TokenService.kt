package ru.kotlix.frame.parties.server.service

interface TokenService {
    fun generateToken(): String
}
