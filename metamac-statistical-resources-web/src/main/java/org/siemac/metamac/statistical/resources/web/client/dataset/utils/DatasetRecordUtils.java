package org.siemac.metamac.statistical.resources.web.client.dataset.utils;

import static org.siemac.metamac.web.common.client.utils.InternationalStringUtils.getLocalisedString;

import org.siemac.metamac.statistical.resources.core.dto.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;

public class DatasetRecordUtils {

    public static DatasetRecord getDatasetRecord(DatasetDto datasetDto) {
        DatasetRecord record = new DatasetRecord(datasetDto.getId(), datasetDto.getIdentifiersMetadata().getIdentifier(), getLocalisedString(datasetDto.getIdentifiersMetadata().getTitle()),
                getLocalisedString(datasetDto.getContentMetadata().getDescription()), CommonUtils.getDatasetProcStatus(datasetDto), datasetDto.getVersionLogic(), datasetDto.getIdentifiersMetadata()
                        .getUrn(), datasetDto);
        return record;
    }

}
