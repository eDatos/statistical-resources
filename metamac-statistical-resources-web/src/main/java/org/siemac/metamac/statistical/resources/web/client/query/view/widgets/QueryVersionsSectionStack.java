package org.siemac.metamac.statistical.resources.web.client.query.view.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.VersionableResourceSectionStack;
import org.siemac.metamac.statistical.resources.web.client.query.model.ds.QueryDS;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;

import com.smartgwt.client.types.SortDirection;

public class QueryVersionsSectionStack extends VersionableResourceSectionStack {

    public QueryVersionsSectionStack(String title) {
        super(title);
    }

    public void setQueryVersions(List<QueryVersionBaseDto> queryVersionBaseDtos) {
        listGrid.selectAllRecords();
        listGrid.removeSelectedData();
        for (QueryVersionBaseDto queryDto : queryVersionBaseDtos) {
            listGrid.addData(StatisticalResourcesRecordUtils.getQueryRecord(queryDto));
        }
        listGrid.sort(QueryDS.VERSION, SortDirection.DESCENDING);
    }

    public void selectQueryVersion(String currentQueryVersionUrn) {
        selectRecord(QueryDS.URN, currentQueryVersionUrn);
    }
}
