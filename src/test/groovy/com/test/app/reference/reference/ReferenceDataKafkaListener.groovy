package com.test.app.reference.reference

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import org.apache.kafka.clients.consumer.ConsumerRecord

import java.util.concurrent.ConcurrentHashMap

@KafkaListener(
        clientId = 'test-client',
        groupId = 'test-group',
        threads = 1
)
class ReferenceDataKafkaListener {
    Map<String, String> messages = new ConcurrentHashMap<>()

    @Topic('${application.kafka.reference-data.topic}')
    void onMessage(ConsumerRecord<String, String> record) {
        messages[record.key()] = record.value()
    }
}
