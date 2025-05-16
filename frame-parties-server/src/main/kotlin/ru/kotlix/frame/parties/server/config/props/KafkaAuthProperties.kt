package ru.kotlix.frame.parties.server.config.props

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "parties.kafka.authentication")
data class KafkaAuthProperties(
    var username: String,
    var password: String,
)
