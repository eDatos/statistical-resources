package org.siemac.metamac.statistical.resources.web.client.query.model.record;

import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.model.record.LifeCycleResourceRecord;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;

public class QueryRecord extends LifeCycleResourceRecord {

    public QueryRecord() {
    }

    public void setRelatedDataset(RelatedResourceDto value) {
        setRelatedResource(QueryDS.RELATED_DATASET_VERSION, value);
    }

    public void setStatus(String value) {
        setAttribute(QueryDS.STATUS, value);
    }

    public void setType(String value) {
        setAttribute(QueryDS.TYPE, value);
    }

    public void setQueryVersionBaseDto(QueryVersionBaseDto queryDto) {
        setAttribute(QueryDS.DTO, queryDto);
    }

    @Override
    public ProcStatusEnum getProcStatusEnum() {
        return getQueryVersionBaseDto().getProcStatus();
    }

    public QueryVersionBaseDto getQueryVersionBaseDto() {
        return (QueryVersionBaseDto) getAttributeAsObject(QueryDS.DTO);
    }
}
