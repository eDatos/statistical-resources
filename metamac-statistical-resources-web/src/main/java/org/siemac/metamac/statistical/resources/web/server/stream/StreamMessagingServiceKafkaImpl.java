package org.siemac.metamac.statistical.resources.web.server.stream;

import java.util.Properties;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

@Component(StreamMessagingService.BEAN_ID)
public class StreamMessagingServiceKafkaImpl<K, V extends SpecificRecordBase> extends StreamMessagingService<K, V> {

    public static final String                  SCHEMA_REGISTRY_URL_CONFIG = "metamac.statistical.resources.kafka." + KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG;
    public static final String                  BOOTSTRAP_SERVERS_CONFIG   = "metamac.statistical.resources.kafka." + ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

    @Autowired
    protected StatisticalResourcesConfiguration statisticalResourcesConfig;

    protected Properties                        props;

    protected StreamMessagingServiceKafkaImpl() {
        super();
    }

    public StreamMessagingServiceKafkaImpl(Properties props) {
        setProps(props);
    }

    protected void setProps(Properties props) {
        if (null == props) {
            props = getPropsFromExternalSource();
        }
        this.props = props;
    }

    public void setStatisticalResourcesConfig(StatisticalResourcesConfiguration statisticalResourcesConfig) {
        this.statisticalResourcesConfig = statisticalResourcesConfig;
    }

    protected ProducerBase<K, V> createProducer() {
        if (null == producer) {
            setProps(null);
            try {
                producer = new KafkaCustomProducer<>(props);
            } catch (MetamacException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return producer;
    }

    private Properties getPropsFromExternalSource() {

        Properties props;
        props = new Properties();
        try {
            props.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, statisticalResourcesConfig.retrieveProperty(StreamMessagingServiceKafkaImpl.SCHEMA_REGISTRY_URL_CONFIG));
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, statisticalResourcesConfig.retrieveProperty(StreamMessagingServiceKafkaImpl.BOOTSTRAP_SERVERS_CONFIG));

        } catch (MetamacException e) {
            e.printStackTrace();
        }
        return props;
    }

    @Override
    public void sendMessage(V message, String topic) throws MetamacException {
        MessageBase<V> m = new AvroMessage<V>(message);

        // Lazy initialitation
        createProducer();

        try {
            producer.sendMessage(m, topic);
        } catch (MetamacException e) {
            throw new MetamacException(e, CommonServiceExceptionType.METADATA_INCORRECT);
        }

    }

}
