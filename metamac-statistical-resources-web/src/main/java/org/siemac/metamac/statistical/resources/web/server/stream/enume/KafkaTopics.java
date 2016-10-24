package org.siemac.metamac.statistical.resources.web.server.stream.enume;


public enum KafkaTopics {
    NEW_PUBLISHED_DATASET_VERSION("NEW_PUBLISHED_DATASET_VERSION");

    private String topic;

    KafkaTopics(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
