package org.siemac.metamac.statistical.resources.web.client.query.view.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.query.model.record.QueryRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.ResourceFieldUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;

public class QueryListGrid extends BaseCustomListGrid {

    public QueryListGrid() {
        super();
        this.setShowAllRecords(true);
        this.setFields(ResourceFieldUtils.getQueryListGridFields());
    }

    public void setQueries(List<QueryVersionDto> queries) {
        removeAllData();
        if (queries != null) {
            QueryRecord[] records = new QueryRecord[queries.size()];
            for (int i = 0; i < queries.size(); i++) {
                records[i] = StatisticalResourcesRecordUtils.getQueryRecord(queries.get(i));
            }
            setData(records);
        }
    }
}
