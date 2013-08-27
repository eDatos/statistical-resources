package org.siemac.metamac.statistical.resources.web.client.publication.model.record;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.model.record.SiemacMetadataRecord;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationDS;

public class PublicationRecord extends SiemacMetadataRecord {

    public PublicationRecord() {
    }

    public void setPublicationBaseDto(PublicationVersionBaseDto publicationVersionBaseDto) {
        setAttribute(PublicationDS.DTO, publicationVersionBaseDto);
    }

    public PublicationVersionBaseDto getPublicationVersionBaseDto() {
        return (PublicationVersionBaseDto) getAttributeAsObject(PublicationDS.DTO);
    }

    @Override
    public ProcStatusEnum getProcStatusEnum() {
        return getPublicationVersionBaseDto().getProcStatus();
    }
}
