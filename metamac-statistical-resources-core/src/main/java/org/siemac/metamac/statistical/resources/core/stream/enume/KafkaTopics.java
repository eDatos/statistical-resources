package org.siemac.metamac.statistical.resources.core.stream.enume;


public enum KafkaTopics {
    DATASET_PUBLICATIONS("DATASET_PUBLICATIONS");

    private String topic;

    KafkaTopics(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }
}
