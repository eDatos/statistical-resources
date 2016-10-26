package org.siemac.metamac.statistical.resources.web.server.stream;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

public class KafkaCustomProducer<K, V extends SpecificRecordBase> extends ProducerBase<K, V> {

    protected final static String[]            MANDATORY_SETTINGS = {KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, ProducerConfig.BOOTSTRAP_SERVERS_CONFIG};
    protected final static Map<String, Object> DEFAULT_SETTINGS   = new HashMap<String, Object>() {

                                                                      {
                                                                          put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
                                                                          put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
                                                                      }
                                                                  };

    protected Properties                       props;
    protected KafkaProducer<K, V>              producer;

    public KafkaCustomProducer(Properties props) throws MetamacException {
        super();
        setProperties(props);
        this.producer = new KafkaProducer<K, V>(props);
    }

    public void setProperties(Properties props) throws MetamacException {
        // TODO properties must be injected from config file/db ?
        props = checkForMissingProperties(props);

        this.props = props;
    }

    @Override
    public void sendMessage(MessageBase<V> m, String topic) throws MetamacException {

        checkTopicIsValid(topic);
        // TODO ADD A KEY TO THE MESSAGE
        ProducerRecord<K, V> record = new ProducerRecord<K, V>(topic, m.getContent());
        try {
            producer.send(record);
        } catch (SerializationException e) {
            throw new MetamacException(e.getCause(), null, null);
        } finally {
        }
    }

    protected void checkTopicIsValid(String topic) throws MetamacException {
        if (topic == null || topic.isEmpty()) {
            throw new MetamacExceptionBuilder().withMessageParameters("Kafka topic is not valid").build();
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
                throw new MetamacExceptionBuilder().withMessageParameters("Missing mandatory settings to initialize Kafka Producer").build();
            }
        }
        return props;
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
