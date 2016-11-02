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
            KafkaTopics topic = KafkaTopics.NEW_PUBLISHED_DATASET_VERSION;
            String key = generateUniqueMessageKeyForMessage(datasetVersion, topic);
            messagingService.sendMessage(key, datasetVersion, topic.getTopic());
            updateMessageStatus(datasetVersion, StreamMessageStatusEnum.SENT);
        } catch (MetamacException e) {
            throw new MetamacException(e, CommonServiceExceptionType.METADATA_UNEXPECTED);
        }
    }

    protected String generateUniqueMessageKeyForMessage(DatasetVersion datasetVersion, KafkaTopics topic) {
        // TODO añadir clave única para los mensajes
        return "TODO UNIQUE KEY";
    }

    @Override
    public void updateMessageStatus(DatasetVersion datasetVersion, StreamMessageStatusEnum status) {
        datasetVersion.getLifeCycleStatisticalResource().setStreamMsgStatus(status);
    }
}
