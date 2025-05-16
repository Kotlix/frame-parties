package ru.kotlix.frame.parties.server.producer

import ru.kotlix.frame.session.api.kafka.MessageNotification

interface MessageProducer {
    fun produceMessage(msg: MessageNotification)
}
