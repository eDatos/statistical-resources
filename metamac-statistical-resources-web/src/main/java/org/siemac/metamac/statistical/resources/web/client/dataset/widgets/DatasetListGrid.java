package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.ResourceFieldUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.NavigableListGrid;

public class DatasetListGrid extends NavigableListGrid {

    public DatasetListGrid() {
        super();
        this.setShowAllRecords(true);
        this.setFields(ResourceFieldUtils.getDatasetListGridFields());
        this.setCanMultiSort(Boolean.FALSE);
    }

    public void setDatasets(List<DatasetVersionBaseDto> datasets) {
        removeAllData();
        if (datasets != null) {
            DatasetRecord[] datasetRecords = StatisticalResourcesRecordUtils.getDatasetRecords(datasets);
            setData(datasetRecords);
        }
    }
}
