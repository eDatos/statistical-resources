package org.siemac.metamac.statistical.resources.web.client.publication.model.record;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.base.model.record.SiemacMetadataRecord;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationDS;

public class PublicationRecord extends SiemacMetadataRecord {

    public PublicationRecord() {
    }

    public void setPublicationDto(PublicationVersionDto publicationDto) {
        setAttribute(PublicationDS.DTO, publicationDto);
    }

    public ProcStatusEnum getProcStatus() {
        return ((PublicationVersionDto) getAttributeAsObject(PublicationDS.DTO)).getProcStatus();
    }
}
