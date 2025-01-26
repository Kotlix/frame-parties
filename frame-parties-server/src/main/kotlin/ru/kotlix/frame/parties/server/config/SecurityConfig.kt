package ru.kotlix.frame.parties.server.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import ru.kotlix.frame.auth.api.token.TokenDecoder
import ru.kotlix.frame.auth.api.token.dto.UserInfo
import ru.kotlix.frame.parties.server.security.TokenAuthenticationExceptionHandler
import ru.kotlix.frame.parties.server.security.TokenAuthenticationFilter
import ru.kotlix.frame.parties.server.security.TokenAuthenticationProvider
import ru.kotlix.frame.parties.server.service.AuthenticationService

@Configuration
class SecurityConfig {
    @Bean
    fun authenticationProvider(
        authenticationService: AuthenticationService,
        tokenDecoder: TokenDecoder<UserInfo>,
    ): AuthenticationProvider = TokenAuthenticationProvider(authenticationService, tokenDecoder)

    @Bean
    fun authenticationManager(authenticationProvider: AuthenticationProvider): AuthenticationManager =
        ProviderManager(authenticationProvider)

    @Bean
    fun filter(authenticationManager: AuthenticationManager) =
        TokenAuthenticationFilter(AntPathRequestMatcher("/api/v1/**"), authenticationManager)

    @Bean
    fun filterChain(
        http: HttpSecurity,
        authenticationProvider: TokenAuthenticationProvider,
        authenticationFilter: TokenAuthenticationFilter,
    ): SecurityFilterChain =
        http
            .cors { it.disable() }
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(authenticationFilter, AnonymousAuthenticationFilter::class.java).authorizeHttpRequests {
                it
                    .requestMatchers("/api/v1/**").authenticated()
            }
            .exceptionHandling {
                it
                    .authenticationEntryPoint(TokenAuthenticationExceptionHandler())
            }
            .build()
}
