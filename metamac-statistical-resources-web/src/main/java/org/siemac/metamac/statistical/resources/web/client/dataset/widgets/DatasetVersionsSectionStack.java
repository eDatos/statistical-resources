package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.VersionableResourceSectionStack;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.DeferredCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.SortDirection;

public class DatasetVersionsSectionStack extends VersionableResourceSectionStack {

    public DatasetVersionsSectionStack(String title) {
        super(title);
    }

    public void setDatasetVersions(List<DatasetVersionBaseDto> datasetVersionBaseDtos) {
        listGrid.selectAllRecords();
        listGrid.removeSelectedData();
        for (DatasetVersionBaseDto datasetDto : datasetVersionBaseDtos) {
            listGrid.addData(StatisticalResourcesRecordUtils.getDatasetRecord(datasetDto));
        }
        listGrid.sort(DatasetDS.VERSION, SortDirection.ASCENDING);
    }

    public void selectDatasetVersion(String currentDatasetVersionUrn) {
        selectRecord(DatasetDS.URN, currentDatasetVersionUrn);
    }
}
