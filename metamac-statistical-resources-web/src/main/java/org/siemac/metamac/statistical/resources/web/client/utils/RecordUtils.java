package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.web.common.client.utils.InternationalStringUtils.getLocalisedString;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.core.dto.DataSetDto;
import org.siemac.metamac.statistical.resources.web.client.collection.model.record.CollectionRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;

public class RecordUtils {

    public static DatasetRecord getDatasetRecord(DataSetDto datasetDto) {
        DatasetRecord record = new DatasetRecord(datasetDto.getId(), datasetDto.getIdentifier(), getLocalisedString(datasetDto.getTitle()), getLocalisedString(datasetDto.getContentMetadata()
                .getDescription()), CommonUtils.getProcStatusName(datasetDto), datasetDto.getVersionLogic(), datasetDto.getUrn(), datasetDto);
        return record;
    }

    public static CollectionRecord getCollectionRecord(CollectionDto collectionDto) {
        CollectionRecord record = new CollectionRecord(collectionDto.getId(), collectionDto.getIdentifier(), getLocalisedString(collectionDto.getTitle()), getLocalisedString(collectionDto
                .getContentMetadata().getDescription()), CommonUtils.getProcStatusName(collectionDto), collectionDto.getVersionLogic(), collectionDto.getUrn(), collectionDto);
        return record;
    }

}
