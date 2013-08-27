package org.siemac.metamac.statistical.resources.web.client.query.view.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.query.model.record.QueryRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.ResourceFieldUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.NavigableListGrid;

public class QueryListGrid extends NavigableListGrid {

    public QueryListGrid() {
        super();
        this.setShowAllRecords(true);
        this.setFields(ResourceFieldUtils.getQueryListGridFields());
    }

    public void setQueries(List<QueryVersionBaseDto> queries) {
        removeAllData();
        if (queries != null) {
            QueryRecord[] records = StatisticalResourcesRecordUtils.getQueryRecords(queries);
            for (int i = 0; i < queries.size(); i++) {
                records[i] = StatisticalResourcesRecordUtils.getQueryRecord(queries.get(i));
            }
            setData(records);
        }
    }
}
