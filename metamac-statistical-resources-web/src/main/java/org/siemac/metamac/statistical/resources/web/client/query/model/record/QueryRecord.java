package org.siemac.metamac.statistical.resources.web.client.query.model.record;

import org.siemac.metamac.statistical.resources.core.dto.VersionableRelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.model.record.LifeCycleResourceRecord;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.web.common.client.utils.NavigationUtils;
import org.siemac.metamac.web.common.shared.RelatedResourceBaseUtils;

public class QueryRecord extends LifeCycleResourceRecord {

    public QueryRecord() {
    }

    public void setRelatedDataset(VersionableRelatedResourceDto value) {
        setAttribute(QueryDS.RELATED_DATASET_VERSION,
                value.getLastVersion() ? RelatedResourceBaseUtils.getRelatedResourceName(value) : RelatedResourceBaseUtils.getRelatedResourceNameWithVersion(value, value.getVersionLogic()));
        setAttribute(NavigationUtils.getDtoPropertyName(QueryDS.RELATED_DATASET_VERSION), value);
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
