package org.siemac.metamac.statistical.resources.web.client.query.model.record;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.model.record.LifeCycleResourceRecord;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;

public class QueryRecord extends LifeCycleResourceRecord {

    public QueryRecord() {
    }

    public void setType(String value) {
        setAttribute(QueryDS.TYPE, value);
    }

    public void setQueryDto(QueryVersionDto queryDto) {
        setAttribute(QueryDS.DTO, queryDto);
    }

    public ProcStatusEnum getProcStatus() {
        return ((QueryVersionDto) getAttributeAsObject(QueryDS.DTO)).getProcStatus();
    }

    public QueryVersionDto getQueryDto() {
        return (QueryVersionDto) getAttributeAsObject(QueryDS.DTO);
    }
}
