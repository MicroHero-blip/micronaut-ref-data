package com.test.app.reference.reference

import groovy.transform.CompileStatic
import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import io.micronaut.context.annotation.Property

@CompileStatic
@KafkaClient(value = "reference-data", properties = [
        @Property(name = "key.serializer", value = "org.apache.kafka.common.serialization.StringSerializer"),
        @Property(name = "value.serializer", value = "com.test.app.reference.reference.KafkaCustomGzipSerializer")
])
interface ReferenceDataKafkaClient {

    @Topic('${application.kafka.reference-data.topic}')
    void send(@KafkaKey String id, String object)
}
