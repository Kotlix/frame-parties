package ru.kotlix.frame.parties.server.config

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import ru.kotlix.frame.parties.server.config.props.KafkaAuthProperties

@EnableKafka
@Configuration
class KafkaConfig {
    @Bean
    fun producerFactory(
        kafkaProperties: KafkaProperties,
        kafkaAuth: KafkaAuthProperties,
    ): ProducerFactory<Unit, String> =
        DefaultKafkaProducerFactory(
            HashMap<String, Any>()
                .withEntry(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers)
                .withEntry(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java)
                .withEntry(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java)
                .withEntry(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG to "SASL_PLAINTEXT")
                .withEntry(SaslConfigs.SASL_MECHANISM to "PLAIN")
                .withEntry(SaslConfigs.SASL_JAAS_CONFIG to saslJaasConfig(kafkaAuth)),
        )

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<Unit, String>): KafkaTemplate<Unit, String> = KafkaTemplate(producerFactory)

    private fun saslJaasConfig(kafkaAuth: KafkaAuthProperties) =
        "org.apache.kafka.common.security.plain.PlainLoginModule required " +
            "username=\"${kafkaAuth.username}\" " +
            "password=\"${kafkaAuth.password}\" " +
            "user_${kafkaAuth.username}=\"${kafkaAuth.password}\";"

    private fun <K, V> MutableMap<K, V>.withEntry(entry: Pair<K, V>) =
        this.apply {
            this[entry.first] = entry.second
        }
}
