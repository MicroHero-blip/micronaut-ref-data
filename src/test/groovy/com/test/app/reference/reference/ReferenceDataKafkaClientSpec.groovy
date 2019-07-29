package com.test.app.reference.reference

import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import javax.inject.Inject

@MicronautTest
class ReferenceDataKafkaClientSpec extends Specification {

    PollingConditions conditions = new PollingConditions(timeout: 10)

    @Inject
    ReferenceDataKafkaClient refData
    @Inject
    ReferenceDataKafkaListener refDataListener

    def 'Publishing to reference-data topic'() {
        given:
        def content = "Text"

        when: "Message is send to reference data"
        refData.send("1", content)

        then: "Then the price is not send to reference data topic"
        conditions.eventually {
            refDataListener.messages.containsKey("1")
            refDataListener.messages["1"] == content
        }
    }
}
