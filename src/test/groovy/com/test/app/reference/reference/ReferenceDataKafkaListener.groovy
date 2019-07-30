package com.test.app.reference.reference

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.OffsetReset
import io.micronaut.configuration.kafka.annotation.Topic
import org.apache.kafka.clients.consumer.ConsumerRecord

import java.util.concurrent.ConcurrentHashMap

@KafkaListener(
        clientId = 'test-client',
        groupId = 'test-group',
        threads = 1,
        offsetReset = OffsetReset.EARLIEST
        //If Earliest is not set Tests will randomly fail because the
        //listener will connect after the message was sent and not pick it up
)
class ReferenceDataKafkaListener {
    Map<String, byte[]> messages = new ConcurrentHashMap<>()

    @Topic('${application.kafka.reference-data.topic}')
    void onMessage(ConsumerRecord<String, byte[]> record) {
        messages[record.key()] = record.value()
    }
}
