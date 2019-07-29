package com.test.app.reference.reference

import groovy.transform.CompileStatic
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.context.annotation.Property

@CompileStatic
@KafkaClient(value = "reference-data")
interface ReferenceDataKafkaClient {

    @Topic('${application.kafka.reference-data.topic}')
    void send(@KafkaKey String id, String object)
}
