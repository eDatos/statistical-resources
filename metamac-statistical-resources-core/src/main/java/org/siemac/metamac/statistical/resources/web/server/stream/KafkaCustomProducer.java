package org.siemac.metamac.statistical.resources.web.server.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.SerializationException;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class KafkaCustomProducer<K, V extends SpecificRecordBase> extends ProducerBase<K, V> {

    private static final int                   KAFKA_TIMEOUT      = 500;
    protected final static String[]            MANDATORY_SETTINGS = {KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, ProducerConfig.BOOTSTRAP_SERVERS_CONFIG};
    protected final static Map<String, Object> DEFAULT_SETTINGS   = new HashMap<String, Object>() {

                                                                      {
                                                                          put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
                                                                          put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
                                                                          put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);                                                          // Time to wait if
                                                                                                                                                                                  // Kafka cluster is
                                                                                                                                                                                  // down or buffer is
                                                                                                                                                                                  // full
                                                                      }
                                                                  };

    protected Properties                       props;
    protected KafkaProducer<K, V>              producer;

    public KafkaCustomProducer(Properties props) throws MetamacException {
        super();
        setProperties(props);
        producer = new KafkaProducer<K, V>(props);
    }

    void setProperties(Properties props) throws MetamacException {
        props = checkForMissingProperties(props);
        this.props = props;
    }

    @Override
    public void sendMessage(MessageBase<K, V> message, String topic) throws MetamacException {
        ProducerRecord<K, V> record = new ProducerRecord<K, V>(topic, message.getKey(), message.getContent());
        RecordMetadata result = null;
        try {
            Future<RecordMetadata> sendResult = producer.send(record);
            result = sendResult.get(KAFKA_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (SerializationException | InterruptedException | ExecutionException | TimeoutException e) {
            throw MetamacExceptionBuilder.builder().withCause(e).build();
        }
    }

    protected Properties checkForMissingProperties(Properties props) throws MetamacException {
        props = checkMissingMandatoryProperties(props);
        props = fillMissingDefaultProperties(props);
        return props;
    }

    protected Properties checkMissingMandatoryProperties(Properties props) throws MetamacException {
        for (String prop : KafkaCustomProducer.MANDATORY_SETTINGS) {
            if (!props.containsKey(prop)) {
                throw new MetamacExceptionBuilder().withMessageParameters(ServiceExceptionType.STREAM_MESSAGING_MISSING_MANDATORY_SETTINGS.getMessageForReasonType()).build();
            }
        }
        return props;
    }

    public static List<String> getRequiredProperties() {
        List<String> properties = new ArrayList<String>();
        properties.addAll(Arrays.asList(KafkaCustomProducer.MANDATORY_SETTINGS));
        return properties;
    }

    public static List<String> getOptionalProperties() {
        List<String> properties = new ArrayList<String>();
        properties.addAll(KafkaCustomProducer.DEFAULT_SETTINGS.keySet());
        return properties;
    }

    protected Properties fillMissingDefaultProperties(Properties props) {
        for (Object key : DEFAULT_SETTINGS.keySet()) {
            if (!props.containsKey(key)) {
                props.put(key, DEFAULT_SETTINGS.get(key));
            }
        }
        return props;
    }

    @Override
    public void close() {
        if (null != producer) {
            producer.close();
        }
    }

}
