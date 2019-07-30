package com.test.app.reference.reference


import groovy.transform.CompileStatic
import io.micronaut.configuration.kafka.annotation.*
import io.micronaut.configuration.kafka.config.AbstractKafkaConfiguration
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Property
import io.micronaut.core.util.CollectionUtils
import io.micronaut.runtime.server.EmbeddedServer
import org.apache.kafka.clients.consumer.ConsumerRecord
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import java.util.concurrent.ConcurrentHashMap

class KafkaAsPerDocSpec extends Specification {
    @Shared
    @AutoCleanup
    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer,
            CollectionUtils.mapOf(
                    "kafka.bootstrap.servers", 'localhost:${random.port}',
                    AbstractKafkaConfiguration.EMBEDDED, true,
                    AbstractKafkaConfiguration.EMBEDDED_TOPICS, []
            )
    )

    void "I can produce and consume a message on the same topic"() {
        given:
        def context = embeddedServer.getApplicationContext()
        PollingConditions conditions = new PollingConditions(timeout: 10, delay: 1)

        TestConsumer testConsumer = context.getBean(TestConsumer)
        TestClient testClient = context.getBean(TestClient)

        when: "I push a message to the reference topic"
        testClient.send("1", "test this")

        then: "My consumer will receive this message"

        conditions.eventually {
            testConsumer.messages.size() == 1
            testConsumer.messages.get("1") == [31, -117, 8, 0, 0, 0, 0, 0, 0, 0, 43, 73, 45, 46, 81, 40, -55, -56, 44, 6, 0, -10, -33, 21, -128, 9, 0, 0, 0]
        }
    }

    @KafkaListener(offsetReset = OffsetReset.EARLIEST, offsetStrategy = OffsetStrategy.SYNC)
    static class TestConsumer {
        Map<String, byte[]> messages = new ConcurrentHashMap<>()

        @Topic('embedded-server-reference-data')
        void onMessage(ConsumerRecord<String, byte[]> record) {
            messages[record.key()] = record.value()
        }
    }

    @CompileStatic
    @KafkaClient(value = "embedded-reference-data", properties = [
            @Property(name = "key.serializer", value = "org.apache.kafka.common.serialization.StringSerializer"),
            @Property(name = "value.serializer", value = "com.test.app.reference.reference.KafkaCustomGzipSerializer")
    ])

    static interface TestClient {

        @Topic('embedded-server-reference-data')
        void send(@KafkaKey String id, String object)
    }

}