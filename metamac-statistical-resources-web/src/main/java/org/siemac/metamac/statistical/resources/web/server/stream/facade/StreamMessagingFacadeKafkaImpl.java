package org.siemac.metamac.statistical.resources.web.server.stream.facade;

import org.apache.avro.specific.SpecificRecord;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.mapper.DatasetDto2DoMapper;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mappers.AvroMapperUtils;
import org.siemac.metamac.statistical.resources.web.server.stream.StreamMessagingService;
import org.siemac.metamac.statistical.resources.web.server.stream.enume.KafkaTopics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreamMessagingFacadeKafkaImpl implements StreamMessagingFacade {

    @Autowired
    protected StreamMessagingService<String, SpecificRecord> messagingService;

    @Autowired
    protected DatasetDto2DoMapper                            datasetDto2DoMapper;

    public StreamMessagingFacadeKafkaImpl() {
    }

    @Override
    public void sendNewDatasetVersionPublished(DatasetVersionDto datasetVersionDto) throws MetamacException {
        DatasetVersion dv;
        try {
            dv = datasetDto2DoMapper.datasetVersionDtoToDo(datasetVersionDto);
            DatasetVersionAvro message = (DatasetVersionAvro) AvroMapperUtils.do2Avro(dv);
            messagingService.sendMessage(message, KafkaTopics.NEW_PUBLISHED_DATASET_VERSION.getTopic());
        } catch (MetamacException e) {
            throw new MetamacException(e, CommonServiceExceptionType.METADATA_UNEXPECTED);
        }
    }
}
