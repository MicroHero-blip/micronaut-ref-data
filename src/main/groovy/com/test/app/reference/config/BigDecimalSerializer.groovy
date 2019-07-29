package com.test.app.reference.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import groovy.transform.CompileStatic

import javax.inject.Singleton

// Note: Micronaut will automatically add any beans it creates which extend
// JsonSerializer or JsonDeserializer to the ObjectMapper
@CompileStatic
@Singleton
class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString())
    }
}
