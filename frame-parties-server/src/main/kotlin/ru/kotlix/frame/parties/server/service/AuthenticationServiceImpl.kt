package ru.kotlix.frame.parties.server.service

import feign.FeignException
import org.springframework.stereotype.Service
import ru.kotlix.frame.auth.client.AuthClient

@Service
class AuthenticationServiceImpl(
    private val authClient: AuthClient,
) : AuthenticationService {
    override fun authenticateByToken(token: String): Boolean =
        try {
            authClient.checkAuth(token)
            true
        } catch (ex: FeignException) {
            false
        }
}
