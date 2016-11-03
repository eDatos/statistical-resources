package org.siemac.metamac.statistical.resources.core.stream.serviceimpl;

import org.apache.avro.specific.SpecificRecord;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.enume.domain.StreamMessageStatusEnum;
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
    public void sendNewDatasetVersionPublished(DatasetVersion datasetVersion) throws MetamacException {
        try {
            updateMessageStatus(datasetVersion, StreamMessageStatusEnum.PENDING);
            KafkaTopics topic = KafkaTopics.DATASET_PUBLICATIONS;
            String key = generateUniqueMessageKeyForMessage(datasetVersion, topic);
            messagingService.sendMessage(key, datasetVersion, topic.getTopic());
            updateMessageStatus(datasetVersion, StreamMessageStatusEnum.SENT);
        } catch (MetamacException e) {
            throw new MetamacException(e, CommonServiceExceptionType.METADATA_UNEXPECTED);
        }
    }


    protected String generateUniqueMessageKeyForMessage(DatasetVersion datasetVersion, KafkaTopics topic) {
        String uniqueKey = datasetVersion.getLifeCycleStatisticalResource().getUrn();
        return uniqueKey;
    }

    @Override
    public void updateMessageStatus(DatasetVersion datasetVersion, StreamMessageStatusEnum status) {
        if (datasetVersion != null) {
            datasetVersion.getLifeCycleStatisticalResource().setPublicationStreamStatus(status);
        }
    }
}
