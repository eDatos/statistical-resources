package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.web.common.client.utils.InternationalStringUtils.getLocalisedString;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.DatasourceDto;
import org.siemac.metamac.statistical.resources.web.client.collection.model.record.CollectionRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasourceRecord;

public class StatisticalResourcesRecordUtils {

    public static DatasetRecord getDatasetRecord(DatasetDto datasetDto) {
        DatasetRecord record = new DatasetRecord(datasetDto.getId(), datasetDto.getIdentifier(), getLocalisedString(datasetDto.getTitle()), getLocalisedString(datasetDto.getContentMetadata()
                .getDescription()), CommonUtils.getProcStatusName(datasetDto), datasetDto.getVersionLogic(), datasetDto.getUrn(), datasetDto);
        return record;
    }
    
    public static DatasourceRecord getDatasourceRecord(DatasourceDto datasourceDto) {
        DatasourceRecord record = new DatasourceRecord(datasourceDto.getId(), datasourceDto.getIdentifier(), getLocalisedString(datasourceDto.getTitle()), datasourceDto.getUrn(), datasourceDto);
        return record;
    }

    public static CollectionRecord getCollectionRecord(CollectionDto collectionDto) {
        CollectionRecord record = new CollectionRecord(collectionDto.getId(), collectionDto.getIdentifier(), getLocalisedString(collectionDto.getTitle()), getLocalisedString(collectionDto
                .getContentMetadata().getDescription()), CommonUtils.getProcStatusName(collectionDto), collectionDto.getVersionLogic(), collectionDto.getUrn(), collectionDto);
        return record;
    }

}
