package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.web.common.client.utils.InternationalStringUtils.getLocalisedString;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasourceRecord;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.query.model.record.QueryRecord;

public class StatisticalResourcesRecordUtils {

    public static DatasetRecord getDatasetRecord(DatasetDto datasetDto) {
        DatasetRecord record = new DatasetRecord(datasetDto.getId(), datasetDto.getCode(), getLocalisedString(datasetDto.getTitle()), getLocalisedString(datasetDto.getDescription()),
                CommonUtils.getProcStatusName(datasetDto), datasetDto.getVersionLogic(), datasetDto.getUrn(), datasetDto);
        return record;
    }

    public static DatasourceRecord getDatasourceRecord(DatasourceDto datasourceDto) {
        DatasourceRecord record = new DatasourceRecord(datasourceDto.getId(), datasourceDto.getCode(), datasourceDto.getUrn(), datasourceDto);
        return record;
    }

    public static PublicationRecord getPublicationRecord(PublicationDto publicationDto) {
        PublicationRecord record = new PublicationRecord(publicationDto.getId(), publicationDto.getCode(), getLocalisedString(publicationDto.getTitle()),
                getLocalisedString(publicationDto.getDescription()), CommonUtils.getProcStatusName(publicationDto), publicationDto.getVersionLogic(), publicationDto.getUrn(), publicationDto);
        return record;
    }
    
    public static QueryRecord getQueryRecord(QueryDto queryDto) {
        QueryRecord record = new QueryRecord(queryDto.getId(), queryDto.getCode(), getLocalisedString(queryDto.getTitle()), 
                CommonUtils.getProcStatusName(queryDto), CommonUtils.getQueryTypeName(queryDto), queryDto.getVersionLogic(), queryDto.getUrn(), queryDto);
        return record;
    }
}
