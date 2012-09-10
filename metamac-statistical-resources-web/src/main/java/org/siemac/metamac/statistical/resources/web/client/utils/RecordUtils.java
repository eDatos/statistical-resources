package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.web.common.client.utils.InternationalStringUtils.getLocalisedString;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.collection.model.record.CollectionRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;

public class RecordUtils {

    public static DatasetRecord getDatasetRecord(DatasetDto datasetDto) {
        DatasetRecord record = new DatasetRecord(datasetDto.getId(), datasetDto.getIdentifiersMetadata().getIdentifier(), getLocalisedString(datasetDto.getIdentifiersMetadata().getTitle()),
                getLocalisedString(datasetDto.getContentMetadata().getDescription()), CommonUtils.getDatasetProcStatus(datasetDto), datasetDto.getVersionLogic(), datasetDto.getIdentifiersMetadata()
                        .getUrn(), datasetDto);
        return record;
    }

    public static CollectionRecord getCollectionRecord(CollectionDto collectionDto) {
        CollectionRecord record = new CollectionRecord(collectionDto.getId(), collectionDto.getIdentifiersMetadata().getIdentifier(), getLocalisedString(collectionDto.getIdentifiersMetadata()
                .getTitle()), getLocalisedString(collectionDto.getContentMetadata().getDescription()), CommonUtils.getDatasetProcStatus(null), collectionDto.getVersionLogic(), collectionDto
                .getIdentifiersMetadata().getUrn(), collectionDto);
        return record;
    }

}
