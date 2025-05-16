package ru.kotlix.frame.parties.server.producer

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import ru.kotlix.frame.session.api.kafka.MessageNotification

@Component
class MessageProducerImpl(
    @Value("\${parties.kafka.producer.topic}")
    private val topicName: String,
    private val kafkaTemplate: KafkaTemplate<Unit, String>,
    private val objectMapper: ObjectMapper,
) : MessageProducer {
    override fun produceMessage(msg: MessageNotification) {
        val str = objectMapper.writeValueAsString(msg)
        kafkaTemplate.send(topicName, str)
    }
}
