package org.siemac.metamac.statistical.resources.web.shared.criteria;

import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;

public class QueryVersionWebCriteria extends LifeCycleStatisticalResourceWebCriteria {

    private static final long serialVersionUID = 1L;

    private String            datasetVersionUrn;
    private QueryStatusEnum   queryStatus;
    private QueryTypeEnum     queryType;

    public QueryVersionWebCriteria() {
        super();
    }

    public QueryVersionWebCriteria(String criteria) {
        super(criteria);
    }

    public String getDatasetVersionUrn() {
        return datasetVersionUrn;
    }

    public QueryStatusEnum getQueryStatus() {
        return queryStatus;
    }

    public QueryTypeEnum getQueryType() {
        return queryType;
    }

    public void setDatasetVersionUrn(String datasetVersionUrn) {
        this.datasetVersionUrn = datasetVersionUrn;
    }

    public void setQueryStatus(QueryStatusEnum queryStatus) {
        this.queryStatus = queryStatus;
    }

    public void setQueryType(QueryTypeEnum queryType) {
        this.queryType = queryType;
    }
}
