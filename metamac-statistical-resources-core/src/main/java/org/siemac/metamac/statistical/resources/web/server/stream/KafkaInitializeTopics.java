package org.siemac.metamac.statistical.resources.web.server.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.errors.TopicExistsException;
import org.apache.kafka.common.protocol.ApiKeys;
import org.apache.kafka.common.serialization.StringSerializer;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

@Component
public class KafkaInitializeTopics implements ApplicationListener<ContextRefreshedEvent> {

    protected static final Log               LOGGER             = LogFactory.getLog(KafkaInitializeTopics.class);

    // Create Topics Request
    private static final int                 NUM_OF_PARTITIONS  = 1;
    private static final short               NUM_OF_REPLICATION = (short) 1;
    private static final int                 TIMEOUT            = 1000;
    private static final short               APIKEY             = ApiKeys.CREATE_TOPICS.id;
    private static final short               VERSION            = 0;
    private static final short               CORRELATIONID      = -1;

    private static final String              RETENTION_MS       = "retention.ms";

    private static final Map<String, String> TOPIC_DEFAULT_SETTINGS;

    static {
        TOPIC_DEFAULT_SETTINGS = new HashMap<>();
        TOPIC_DEFAULT_SETTINGS.put(RETENTION_MS, "-1");
    };

    @Autowired
    private StatisticalResourcesConfiguration statisticalResourcesConfiguration;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext ac = event.getApplicationContext();
        if (ac.getParent() == null) {
            try {
                propagateCreationOfTopics(NUM_OF_PARTITIONS, NUM_OF_REPLICATION, TIMEOUT);
            } catch (IOException | IllegalArgumentException | MetamacException e) {
                LOGGER.error(e);
            }
        }
    }

    private void propagateCreationOfTopics(int partitions, short replication, int timeout) throws IOException, IllegalArgumentException, MetamacException {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, statisticalResourcesConfiguration.retrieveKafkaBootStrapServers());
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);

        List<NewTopic> topics = new ArrayList<>();
        topics.add(createTopic(statisticalResourcesConfiguration.retrieveKafkaTopicQueryPublication(), partitions, replication));
        topics.add(createTopic(statisticalResourcesConfiguration.retrieveKafkaTopicDatasetsPublication(), partitions, replication));
        topics.add(createTopic(statisticalResourcesConfiguration.retrieveKafkaTopicCollectionPublication(), partitions, replication));

        try (final AdminClient adminClient = AdminClient.create(properties)) {
            adminClient.createTopics(topics).all().get();
        } catch (final InterruptedException | ExecutionException e) {
            // Ignore if TopicExistsException, which may be valid if topic exists
            if (!(e.getCause() instanceof TopicExistsException)) {
                throw new RuntimeException("Imposible to create/check Topic in kafka", e);
            }
        }
    }

    private NewTopic createTopic(String topic, int partitions, short replication) {
        return new NewTopic(topic, partitions, replication).configs(TOPIC_DEFAULT_SETTINGS);
    }

    // private void propagateCreationOfTopics(int partitions, short replication, int timeout) throws IOException, IllegalArgumentException, MetamacException {
    // Map<String, String> configs = new HashMap<>();
    // configs.put(RETENTION_MS, "-1");
    //
    // CreateTopicsRequest.TopicDetails topicDetails = new CreateTopicsRequest.TopicDetails(partitions, replication, configs);
    // Map<String, CreateTopicsRequest.TopicDetails> topicConfig = new HashMap<String, CreateTopicsRequest.TopicDetails>();
    // topicConfig.put(statisticalResourcesConfiguration.retrieveKafkaTopicQueryPublication(), topicDetails);
    // topicConfig.put(statisticalResourcesConfiguration.retrieveKafkaTopicDatasetsPublication(), topicDetails);
    // topicConfig.put(statisticalResourcesConfiguration.retrieveKafkaTopicCollectionPublication(), topicDetails);
    //
    // CreateTopicsRequest request = new CreateTopicsRequest(topicConfig, timeout);
    //
    // CreateTopicsResponse response = createTopic(request, statisticalResourcesConfiguration.retrieveKafkaBootStrapServers());
    //
    // checkErrors(response.errors());
    // }
    //
    // private CreateTopicsResponse createTopic(CreateTopicsRequest request, String client) throws IllegalArgumentException, IOException {
    // String[] comp = client.split(":");
    // if (comp.length != 2) {
    // throw new IllegalArgumentException("Wrong client directive");
    // }
    // String address = comp[0];
    // int port = Integer.parseInt(comp[1]);
    //
    // RequestHeader header = new RequestHeader(APIKEY, VERSION, client, CORRELATIONID);
    // ByteBuffer buffer = ByteBuffer.allocate(header.sizeOf() + request.sizeOf());
    // header.writeTo(buffer);
    // request.writeTo(buffer);
    //
    // byte byteBuf[] = buffer.array();
    //
    // byte[] resp = requestAndReceive(byteBuf, address, port);
    // ByteBuffer respBuffer = ByteBuffer.wrap(resp);
    // ResponseHeader.parse(respBuffer);
    //
    // return CreateTopicsResponse.parse(respBuffer);
    // }

    // private byte[] requestAndReceive(byte[] buffer, String address, int port) throws IOException {
    // try (Socket socket = new Socket(address, port); DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); DataInputStream dis = new DataInputStream(socket.getInputStream())) {
    // dos.writeInt(buffer.length);
    // dos.write(buffer);
    // dos.flush();
    //
    // byte resp[] = new byte[dis.readInt()];
    // dis.readFully(resp);
    //
    // return resp;
    // }
    // }
    //
    // private void checkErrors(Map<String, Errors> errors) {
    // Iterator<Entry<String, Errors>> entryMapIterator = errors.entrySet().iterator();
    //
    // StringBuilder result = new StringBuilder();
    // while (entryMapIterator.hasNext()) {
    // Map.Entry<String, Errors> entry = entryMapIterator.next();
    //
    // Errors value = entry.getValue();
    //
    // if (!(value.equals(Errors.NONE) || value.equals(Errors.TOPIC_ALREADY_EXISTS))) {
    // result.append(entry.getKey() + " " + value.message()).append("\n");
    // }
    // }
    //
    // if (result.length() > 0) {
    // throw new RuntimeException("Imposible to create/check Topic in kafka: " + result.toString());
    // }
    // }

}
