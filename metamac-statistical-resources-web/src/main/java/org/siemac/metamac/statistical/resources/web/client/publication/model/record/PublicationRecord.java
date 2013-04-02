package org.siemac.metamac.statistical.resources.web.client.publication.model.record;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.model.ds.PublicationDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PublicationRecord extends ListGridRecord {

    public PublicationRecord(Long id, String identifier, String name, String description, String status, String versionLogic, String urn, PublicationDto publicationDto) {
        setId(id);
        setIdentifier(identifier);
        setName(name);
        setDescription(description);
        setProcStatus(status);
        setVersionLogic(versionLogic);
        setUrn(urn);
        setPublicationDto(publicationDto);
    }

    public void setId(Long id) {
        setAttribute(PublicationDS.ID, id);
    }

    public void setName(String name) {
        setAttribute(PublicationDS.TITLE, name);
    }

    public void setDescription(String desc) {
        setAttribute(PublicationDS.DESCRIPTION, desc);
    }

    public void setIdentifier(String identifier) {
        setAttribute(PublicationDS.CODE, identifier);
    }

    public void setProcStatus(String value) {
        setAttribute(PublicationDS.PROC_STATUS, value);
    }

    public void setVersionLogic(String value) {
        setAttribute(PublicationDS.VERSION, value);
    }

    public void setUrn(String value) {
        setAttribute(PublicationDS.URN, value);
    }

    public void setPublicationDto(PublicationDto publicationDto) {
        setAttribute(PublicationDS.DTO, publicationDto);
    }

    public Long getId() {
        return getAttributeAsLong(PublicationDS.ID);
    }

    public String getIdentifier() {
        return getAttribute(PublicationDS.CODE);
    }

    public String getName() {
        return getAttribute(PublicationDS.TITLE);
    }

    public StatisticalResourceProcStatusEnum getProcStatus() {
        return ((PublicationDto) getAttributeAsObject(PublicationDS.DTO)).getProcStatus();
    }

    public String getDescription() {
        return getAttribute(PublicationDS.DESCRIPTION);
    }

    public String getUrn() {
        return getAttributeAsString(PublicationDS.URN);
    }
}