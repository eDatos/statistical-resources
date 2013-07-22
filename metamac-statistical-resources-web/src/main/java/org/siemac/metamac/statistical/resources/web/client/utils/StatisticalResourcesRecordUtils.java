package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.web.common.client.utils.InternationalStringUtils.getLocalisedString;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.TemporalCodeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasourceRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.CodeItemRecord;
import org.siemac.metamac.statistical.resources.web.client.model.record.TemporalCodeRecord;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.query.model.record.QueryRecord;

public class StatisticalResourcesRecordUtils extends org.siemac.metamac.web.common.client.utils.RecordUtils {

    public static DatasetRecord getDatasetRecord(DatasetVersionDto datasetDto) {
        DatasetRecord record = new DatasetRecord(datasetDto.getId(), datasetDto.getCode(), getLocalisedString(datasetDto.getTitle()), getLocalisedString(datasetDto.getDescription()),
                CommonUtils.getProcStatusName(datasetDto), datasetDto.getVersionLogic(), datasetDto.getUrn(), datasetDto);
        return record;
    }

    public static DatasourceRecord getDatasourceRecord(DatasourceDto datasourceDto) {
        DatasourceRecord record = new DatasourceRecord(datasourceDto.getId(), datasourceDto.getCode(), datasourceDto.getUrn(), datasourceDto);
        return record;
    }

    public static PublicationRecord getPublicationRecord(PublicationVersionDto publicationDto) {
        PublicationRecord record = new PublicationRecord(publicationDto.getId(), publicationDto.getCode(), getLocalisedString(publicationDto.getTitle()),
                getLocalisedString(publicationDto.getDescription()), CommonUtils.getProcStatusName(publicationDto), publicationDto.getVersionLogic(), publicationDto.getUrn(), publicationDto);
        return record;
    }

    public static QueryRecord getQueryRecord(QueryVersionDto queryDto) {
        QueryRecord record = new QueryRecord(queryDto.getId(), queryDto.getCode(), getLocalisedString(queryDto.getTitle()), CommonUtils.getProcStatusName(queryDto),
                CommonUtils.getQueryTypeName(queryDto), queryDto.getVersionLogic(), queryDto.getUrn(), queryDto);
        return record;
    }

    public static CodeItemRecord getCodeItemRecord(CodeItemDto codeItemDto) {
        CodeItemRecord record = new CodeItemRecord();
        record.setCode(codeItemDto.getCode());
        record.setTitle(codeItemDto.getTitle());
        record.setCodeItemDto(codeItemDto);
        return record;
    }

    public static TemporalCodeRecord getTemporalCodeRecord(TemporalCodeDto temporalCodeDto) {
        TemporalCodeRecord record = new TemporalCodeRecord();
        record.setCode(temporalCodeDto.getIdentifier());
        record.setTitle(temporalCodeDto.getTitle());
        record.setTemporalCodeDto(temporalCodeDto);
        return record;
    }

    public static List<TemporalCodeRecord> getTemporalCodeRecords(List<TemporalCodeDto> dtos) {
        List<TemporalCodeRecord> records = new ArrayList<TemporalCodeRecord>();
        for (TemporalCodeDto dto : dtos) {
            records.add(getTemporalCodeRecord(dto));
        }
        return records;
    }
}
