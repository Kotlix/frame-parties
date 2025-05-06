package ru.kotlix.frame.parties.config

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration
import ru.kotlix.frame.parties.client.PartiesChatClient
import ru.kotlix.frame.parties.client.PartiesCommunityClient
import ru.kotlix.frame.parties.client.PartiesDirectoryClient
import ru.kotlix.frame.parties.client.PartiesMessageClient
import ru.kotlix.frame.parties.client.PartiesRoleClient
import ru.kotlix.frame.parties.client.PartiesVoiceClient

@Configuration
@EnableFeignClients(
    basePackageClasses = [
        PartiesChatClient::class,
        PartiesCommunityClient::class,
        PartiesDirectoryClient::class,
        PartiesMessageClient::class,
        PartiesRoleClient::class,
        PartiesVoiceClient::class,
    ],
)
@ConditionalOnProperty(
    prefix = "frame.parties.clients.auto-configure",
    value = ["true"],
    matchIfMissing = true,
)
class ClientsConfig
