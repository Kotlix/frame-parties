package ru.kotlix.frame.parties.server.security

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import ru.kotlix.frame.auth.api.token.TokenDecoder
import ru.kotlix.frame.auth.api.token.dto.UserInfo
import ru.kotlix.frame.parties.server.service.AuthenticationService
import ru.kotlix.frame.parties.server.service.dto.toServerUserInfo

class TokenAuthenticationProvider(
    private val authenticationService: AuthenticationService,
    private val tokenDecoder: TokenDecoder<UserInfo>,
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        val token = authentication?.principal as String? ?: throw AccessDeniedException("Authentication required")
        if (!authenticationService.authenticateByToken(token)) {
            throw AccessDeniedException("Authentication failed")
        }
        val user = tokenDecoder.getPayload(token).toServerUserInfo()
        return PreAuthenticatedAuthenticationToken(user, "ROLE_USER").apply {
            isAuthenticated = true
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.let { PreAuthenticatedAuthenticationToken::class.java.isAssignableFrom(it) } ?: false
    }
}
