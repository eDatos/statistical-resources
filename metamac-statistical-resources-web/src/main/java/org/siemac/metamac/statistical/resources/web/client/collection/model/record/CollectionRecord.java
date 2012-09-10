package org.siemac.metamac.statistical.resources.web.client.collection.model.record;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.collection.model.ds.CollectionDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CollectionRecord extends ListGridRecord {

    public CollectionRecord(Long id, String identifier, String name, String description, String status, String versionLogic, String urn, CollectionDto collectionDto) {
        setId(id);
        setIdentifier(identifier);
        setName(name);
        setDescription(description);
        setProcStatus(status);
        setVersionLogic(versionLogic);
        setUrn(urn);
        setCollectionDto(collectionDto);
    }

    public void setId(Long id) {
        setAttribute(CollectionDS.ID, id);
    }

    public void setName(String name) {
        setAttribute(CollectionDS.TITLE, name);
    }

    public void setDescription(String desc) {
        setAttribute(CollectionDS.DESCRIPTION, desc);
    }

    public void setIdentifier(String identifier) {
        setAttribute(CollectionDS.IDENTIFIER, identifier);
    }

    public void setProcStatus(String value) {
        setAttribute(CollectionDS.PROC_STATUS, value);
    }

    public void setVersionLogic(String value) {
        setAttribute(CollectionDS.VERSION_LOGIC, value);
    }

    public void setUrn(String value) {
        setAttribute(CollectionDS.URN, value);
    }

    public void setCollectionDto(CollectionDto collectionDto) {
        setAttribute(CollectionDS.DTO, collectionDto);
    }

    public Long getId() {
        return getAttributeAsLong(CollectionDS.ID);
    }

    public String getIdentifier() {
        return getAttribute(CollectionDS.IDENTIFIER);
    }

    public String getName() {
        return getAttribute(CollectionDS.TITLE);
    }

    public StatisticalResourceProcStatusEnum getProcStatus() {
        return ((CollectionDto) getAttributeAsObject(CollectionDS.DTO)).getProcStatus();
    }

    public String getDescription() {
        return getAttribute(CollectionDS.DESCRIPTION);
    }

    public String getUrn() {
        return getAttributeAsString(CollectionDS.URN);
    }

}
