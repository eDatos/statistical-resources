package org.siemac.metamac.statistical.resources.web.client.dataset.model.record;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.base.model.record.StatisticalResourceRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;

public class DatasetRecord extends StatisticalResourceRecord {

    public DatasetRecord() {
    }

    public void setDatasetDto(DatasetVersionDto datasetDto) {
        setAttribute(DatasetDS.DTO, datasetDto);
    }

    public ProcStatusEnum getProcStatus() {
        return ((DatasetVersionDto) getAttributeAsObject(DatasetDS.DTO)).getProcStatus();
    }
}
