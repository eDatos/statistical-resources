package org.siemac.metamac.statistical.resources.web.client.query.model.record;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;


public class QueryRecord extends ListGridRecord {
    
    public QueryRecord(Long id, String identifier, String name, String status, String type, String versionLogic, String urn, QueryDto queryDto) {
        setId(id);
        setIdentifier(identifier);
        setName(name);
        setProcStatus(status);
        setType(type);
        setVersionLogic(versionLogic);
        setUrn(urn);
        setQueryDto(queryDto);
    }

    public void setId(Long id) {
        setAttribute(QueryDS.ID, id);
    }

    public void setName(String name) {
        setAttribute(QueryDS.TITLE, name);
    }

    public void setIdentifier(String identifier) {
        setAttribute(QueryDS.CODE, identifier);
    }

    public void setProcStatus(String value) {
        setAttribute(QueryDS.PROC_STATUS, value);
    }
    
    public void setType(String value) {
        setAttribute(QueryDS.TYPE, value);
    }

    public void setVersionLogic(String value) {
        setAttribute(QueryDS.VERSION, value);
    }

    public void setUrn(String value) {
        setAttribute(QueryDS.URN, value);
    }

    public void setQueryDto(QueryDto queryDto) {
        setAttribute(QueryDS.DTO, queryDto);
    }

    public Long getId() {
        return getAttributeAsLong(QueryDS.ID);
    }

    public String getIdentifier() {
        return getAttribute(QueryDS.CODE);
    }

    public String getName() {
        return getAttribute(QueryDS.TITLE);
    }

    public StatisticalResourceProcStatusEnum getProcStatus() {
        return ((QueryDto) getAttributeAsObject(QueryDS.DTO)).getProcStatus();
    }

    public String getUrn() {
        return getAttributeAsString(QueryDS.URN);
    }
    
    public QueryDto getQueryDto() {
        return (QueryDto)getAttributeAsObject(QueryDS.DTO);
    }
}
