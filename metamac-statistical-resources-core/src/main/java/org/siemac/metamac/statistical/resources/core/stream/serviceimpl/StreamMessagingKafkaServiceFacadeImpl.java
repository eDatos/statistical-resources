package org.siemac.metamac.statistical.resources.core.stream.serviceimpl;

import org.apache.avro.specific.SpecificRecord;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.enume.domain.StreamMessageStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.stream.enume.KafkaTopics;
import org.siemac.metamac.statistical.resources.core.stream.serviceapi.StreamMessagingService;
import org.siemac.metamac.statistical.resources.core.stream.serviceapi.StreamMessagingServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamMessagingKafkaServiceFacadeImpl implements StreamMessagingServiceFacade {

    @Autowired
    protected StreamMessagingService<String, SpecificRecord> messagingService;

    @Autowired
    protected DatasetDto2DoMapper                            datasetDto2DoMapper;

    public StreamMessagingKafkaServiceFacadeImpl() {
    }

    @Override
    public void sendNewVersionPublished(HasSiemacMetadata version) throws MetamacException {
        try {
            updateMessageStatus(version, StreamMessageStatusEnum.PENDING);
            KafkaTopics topic = getTopicByType(version);
            messagingService.sendMessage(version, topic.getTopic());
            updateMessageStatus(version, StreamMessageStatusEnum.SENT);
        } catch (MetamacException e) {
            updateMessageStatus(version, StreamMessageStatusEnum.FAILED);
            throw new MetamacException(e, ServiceExceptionType.UNABLE_TO_SEND_STREAM_MESSAGING_TO_STREAM_MESSAGING_SERVER);
        }
    }

    protected KafkaTopics getTopicByType(HasSiemacMetadata version) {
        switch (version.getSiemacMetadataStatisticalResource().getType()) {
            case DATASET:
                return KafkaTopics.DATASET_PUBLICATIONS;
            case COLLECTION:
                return KafkaTopics.COLLECTION_PUBLICATIONS;
            default:
                return null;
        }
    }


    protected String generateUniqueMessageKeyForMessage(DatasetVersion datasetVersion, KafkaTopics topic) {
        String uniqueKey = datasetVersion.getLifeCycleStatisticalResource().getUrn();
        return uniqueKey;
    }

    @Override
    public void updateMessageStatus(HasSiemacMetadata version, StreamMessageStatusEnum status) {
        if (version != null) {
            version.getLifeCycleStatisticalResource().setPublicationStreamStatus(status);
        }
    }
}
