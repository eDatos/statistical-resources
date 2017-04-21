package org.siemac.metamac.statistical.resources.web.server.stream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.common.protocol.ApiKeys;
import org.apache.kafka.common.protocol.Errors;
import org.apache.kafka.common.requests.CreateTopicsRequest;
import org.apache.kafka.common.requests.CreateTopicsResponse;
import org.apache.kafka.common.requests.RequestHeader;
import org.apache.kafka.common.requests.ResponseHeader;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class KafkaInitializeTopics implements ApplicationListener<ContextRefreshedEvent> {

    protected static Log                              LOGGER                              = LogFactory.getLog(KafkaInitializeTopics.class);

    // Create Topics Request
    private static final int                  NUM_OF_PARTITIONS  = 1;
    private static final short                NUM_OF_REPLICATION = (short) 1;
    private static final int                  TIMEOUT            = 1000;
    private static final short                APIKEY             = ApiKeys.CREATE_TOPICS.id;
    private static final short                VERSION            = 0;
    private static final short                CORRELATIONID      = -1;

    private static final String               RETENTION_MS       = "retention.ms";

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
        Map<String, String> configs = new HashMap<>();
        configs.put(RETENTION_MS, "-1");

        CreateTopicsRequest.TopicDetails topicDetails = new CreateTopicsRequest.TopicDetails(partitions, replication, configs);
        Map<String, CreateTopicsRequest.TopicDetails> topicConfig = new HashMap<String, CreateTopicsRequest.TopicDetails>();
        topicConfig.put(statisticalResourcesConfiguration.retrieveKafkaTopicQueryPublication(), topicDetails);
        topicConfig.put(statisticalResourcesConfiguration.retrieveKafkaTopicDatasetsPublication(), topicDetails);
        topicConfig.put(statisticalResourcesConfiguration.retrieveKafkaTopicCollectionPublication(), topicDetails);

        CreateTopicsRequest request = new CreateTopicsRequest(topicConfig, timeout);

        CreateTopicsResponse response = createTopic(request, statisticalResourcesConfiguration.retrieveKafkaBootStrapServers());

        checkErrors(response.errors());
    }

    private CreateTopicsResponse createTopic(CreateTopicsRequest request, String client) throws IllegalArgumentException, IOException {
        String[] comp = client.split(":");
        if (comp.length != 2) {
            throw new IllegalArgumentException("Wrong client directive");
        }
        String address = comp[0];
        int port = Integer.parseInt(comp[1]);

        RequestHeader header = new RequestHeader(APIKEY, VERSION, client, CORRELATIONID);
        ByteBuffer buffer = ByteBuffer.allocate(header.sizeOf() + request.sizeOf());
        header.writeTo(buffer);
        request.writeTo(buffer);

        byte byteBuf[] = buffer.array();

        byte[] resp = requestAndReceive(byteBuf, address, port);
        ByteBuffer respBuffer = ByteBuffer.wrap(resp);
        ResponseHeader.parse(respBuffer);

        return CreateTopicsResponse.parse(respBuffer);
    }

    private byte[] requestAndReceive(byte[] buffer, String address, int port) throws IOException {
        try (Socket socket = new Socket(address, port); DataOutputStream dos = new DataOutputStream(socket.getOutputStream()); DataInputStream dis = new DataInputStream(socket.getInputStream())) {
            dos.writeInt(buffer.length);
            dos.write(buffer);
            dos.flush();

            byte resp[] = new byte[dis.readInt()];
            dis.readFully(resp);

            return resp;
        }
    }
    
    private void checkErrors(Map<String, Errors> errors) {
        Iterator<Entry<String, Errors>> entryMapIterator = errors.entrySet().iterator();

        StringBuilder result = new StringBuilder();
        while (entryMapIterator.hasNext()) {
            Map.Entry<String, Errors> entry = entryMapIterator.next();

            Errors value = entry.getValue();

            if (!(value.equals(Errors.NONE) || value.equals(Errors.TOPIC_ALREADY_EXISTS))) {
                result.append(entry.getKey() + " " + value.message()).append("\n");
            }
        }

        if (result.length() > 0) {
            throw new RuntimeException("Imposible to create/check Topic in kafka: " + result.toString());
        }
    }

}
