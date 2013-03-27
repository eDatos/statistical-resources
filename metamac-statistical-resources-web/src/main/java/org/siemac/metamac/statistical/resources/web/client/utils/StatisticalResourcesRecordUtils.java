package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.web.common.client.utils.InternationalStringUtils.getLocalisedString;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.client.collection.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasourceRecord;

public class StatisticalResourcesRecordUtils {

    public static DatasetRecord getDatasetRecord(DatasetDto datasetDto) {
        DatasetRecord record = new DatasetRecord(datasetDto.getId(), datasetDto.getCode(), getLocalisedString(datasetDto.getTitle()), getLocalisedString(datasetDto.getDescription()), CommonUtils.getProcStatusName(datasetDto), datasetDto.getVersionLogic(), datasetDto.getUrn(), datasetDto);
        return record;
    }
    
    public static DatasourceRecord getDatasourceRecord(DatasourceDto datasourceDto) {
        DatasourceRecord record = new DatasourceRecord(datasourceDto.getId(), datasourceDto.getCode(), datasourceDto.getUrn(), datasourceDto);
        return record;
    }

    public static PublicationRecord getCollectionRecord(PublicationDto collectionDto) {
        PublicationRecord record = new PublicationRecord(collectionDto.getId(), collectionDto.getCode(), getLocalisedString(collectionDto.getTitle()), getLocalisedString(collectionDto
                .getDescription()), CommonUtils.getProcStatusName(collectionDto), collectionDto.getVersionLogic(), collectionDto.getUrn(), collectionDto);
        return record;
    }

}
