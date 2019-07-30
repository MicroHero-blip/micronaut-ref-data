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
        when: "Message is send to reference data"
        refData.send("1", "test this")

        then: "Then the price is not send to reference data topic"
        conditions.eventually {
            refDataListener.messages.containsKey("1")
            refDataListener.messages["1"] == [31, -117, 8, 0, 0, 0, 0, 0, 0, 0, 43, 73, 45, 46, 81, 40, -55, -56, 44, 6, 0, -10, -33, 21, -128, 9, 0, 0, 0]
        }
    }
}
