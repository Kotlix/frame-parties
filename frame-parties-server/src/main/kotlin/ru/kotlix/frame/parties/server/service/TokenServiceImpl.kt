package ru.kotlix.frame.parties.server.service

import org.springframework.stereotype.Service
import ru.kotlix.frame.parties.server.repository.InvitationTokenEntityRepository
import java.security.SecureRandom
import java.time.LocalDateTime
import java.util.Base64

@Service
class TokenServiceImpl(
    private val tokenEntityRepository: InvitationTokenEntityRepository,
) : TokenService {
    private val secureRandom = SecureRandom(LocalDateTime.now().toString().toByteArray())

    override fun generateToken(): String {
        var token: String
        do {
            token = randomTokenGeneration()
        } while (tokenEntityRepository.findByToken(token) != null)
        return token
    }

    private fun randomTokenGeneration(): String =
        ByteArray(32).let {
            secureRandom.nextBytes(it)
            Base64.getUrlEncoder().withoutPadding().encodeToString(it)
        }
}
