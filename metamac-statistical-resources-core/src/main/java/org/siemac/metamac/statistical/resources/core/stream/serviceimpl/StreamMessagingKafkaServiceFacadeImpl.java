package org.siemac.metamac.statistical.resources.core.stream.serviceimpl;

import org.apache.avro.specific.SpecificRecord;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.enume.domain.StreamMessageStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.stream.serviceapi.StreamMessagingService;
import org.siemac.metamac.statistical.resources.core.stream.serviceapi.StreamMessagingServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamMessagingKafkaServiceFacadeImpl implements StreamMessagingServiceFacade {

    @Autowired
    protected StreamMessagingService<String, SpecificRecord> messagingService;

    @Autowired
    protected StatisticalResourcesConfiguration              statisticalResourcesConfig;

    @Autowired
    protected DatasetDto2DoMapper                            datasetDto2DoMapper;

    public StreamMessagingKafkaServiceFacadeImpl() {
    }

    @Override
    public void sendNewVersionPublished(HasSiemacMetadata version) throws MetamacException {
        try {
            updateMessageStatus(version, StreamMessageStatusEnum.PENDING);
            String topic = getTopicByType(version);
            messagingService.sendMessage(version, topic);
            updateMessageStatus(version, StreamMessageStatusEnum.SENT);
        } catch (MetamacException e) {
            updateMessageStatus(version, StreamMessageStatusEnum.FAILED);
            throw new MetamacException(e, ServiceExceptionType.UNABLE_TO_SEND_STREAM_MESSAGING_TO_STREAM_MESSAGING_SERVER);
        }
    }

    protected String getTopicByType(HasSiemacMetadata version) throws MetamacException {
        switch (version.getSiemacMetadataStatisticalResource().getType()) {
            case DATASET:
                return statisticalResourcesConfig.retrieveKafkaTopicDatasetsPublication();
            case COLLECTION:
                return statisticalResourcesConfig.retrieveKafkaTopicCollectionPublication();
            default:
                return null;
        }
    }

    protected String generateUniqueMessageKeyForMessage(DatasetVersion datasetVersion) {
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
