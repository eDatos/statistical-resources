package org.siemac.metamac.statistical.resources.web.client.multidataset.model.record;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.model.record.SiemacMetadataRecord;
import org.siemac.metamac.statistical.resources.web.client.multidataset.model.ds.MultidatasetDS;

public class MultidatasetRecord extends SiemacMetadataRecord {

    public MultidatasetRecord() {
    }

    public void setMultidatasetBaseDto(MultidatasetVersionBaseDto publicationVersionBaseDto) {
        setAttribute(MultidatasetDS.DTO, publicationVersionBaseDto);
    }

    public MultidatasetVersionBaseDto getMultidatasetVersionBaseDto() {
        return (MultidatasetVersionBaseDto) getAttributeAsObject(MultidatasetDS.DTO);
    }

    @Override
    public ProcStatusEnum getProcStatusEnum() {
        return getMultidatasetVersionBaseDto().getProcStatus();
    }
}
