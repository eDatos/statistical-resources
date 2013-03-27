package org.siemac.metamac.statistical.resources.web.client.dataset.model.record;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DatasetRecord extends ListGridRecord {

    public DatasetRecord(Long id, String identifier, String name, String description, String status, String versionLogic, String urn, DatasetDto datasetDto) {
        setId(id);
        setIdentifier(identifier);
        setName(name);
        setDescription(description);
        setProcStatus(status);
        setVersionLogic(versionLogic);
        setUrn(urn);
        setDataSetDto(datasetDto);
    }

    public void setId(Long id) {
        setAttribute(DatasetDS.ID, id);
    }

    public void setName(String name) {
        setAttribute(DatasetDS.TITLE, name);
    }

    public void setDescription(String desc) {
        setAttribute(DatasetDS.DESCRIPTION, desc);
    }

    public void setIdentifier(String identifier) {
        setAttribute(DatasetDS.CODE, identifier);
    }

    public void setProcStatus(String value) {
        setAttribute(DatasetDS.PROC_STATUS, value);
    }

    public void setVersionLogic(String value) {
        setAttribute(DatasetDS.VERSION_LOGIC, value);
    }

    public void setUrn(String value) {
        setAttribute(DatasetDS.URN, value);
    }

    public void setDataSetDto(DatasetDto datasetDto) {
        setAttribute(DatasetDS.DTO, datasetDto);
    }

    public Long getId() {
        return getAttributeAsLong(DatasetDS.ID);
    }

    public String getIdentifier() {
        return getAttribute(DatasetDS.CODE);
    }

    public String getName() {
        return getAttribute(DatasetDS.TITLE);
    }

    public StatisticalResourceProcStatusEnum getProcStatus() {
        return ((DatasetDto) getAttributeAsObject(DatasetDS.DTO)).getProcStatus();
    }

    public String getDescription() {
        return getAttribute(DatasetDS.DESCRIPTION);
    }

    public String getUrn() {
        return getAttributeAsString(DatasetDS.URN);
    }

}
