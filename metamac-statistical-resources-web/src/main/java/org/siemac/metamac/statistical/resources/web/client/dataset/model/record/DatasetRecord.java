package org.siemac.metamac.statistical.resources.web.client.dataset.model.record;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.model.record.SiemacMetadataRecord;

public class DatasetRecord extends SiemacMetadataRecord {

    public DatasetRecord() {
    }

    public void setRelatedDSD(ExternalItemDto value) {
        setExternalItem(DatasetDS.RELATED_DSD, value);
    }

    public void setStatisticOfficiality(String value) {
        setAttribute(DatasetDS.STATISTIC_OFFICIALITY, value);
    }

    public void setDatasetDto(DatasetVersionDto datasetDto) {
        setAttribute(DatasetDS.DTO, datasetDto);
    }

    public ProcStatusEnum getProcStatus() {
        return ((DatasetVersionDto) getAttributeAsObject(DatasetDS.DTO)).getProcStatus();
    }
}
