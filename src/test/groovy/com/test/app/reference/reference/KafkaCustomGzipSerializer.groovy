package com.test.app.reference.reference

import org.apache.kafka.common.serialization.Serializer

import java.util.zip.GZIPOutputStream

class KafkaCustomGzipSerializer implements Serializer<String> {

    @Override
    void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    byte[] serialize(String topic, String data) {
        data != null ? writeData(data) : null
    }

    private static byte[] writeData(String data) {
        new ByteArrayOutputStream().withCloseable { byteArrayCompressedStream ->
            new GZIPOutputStream(byteArrayCompressedStream).withCloseable { os -> os.write(data.getBytes("UTF-8")) }
            return byteArrayCompressedStream.toByteArray()
        }
    }

    @Override
    void close() {}
}
