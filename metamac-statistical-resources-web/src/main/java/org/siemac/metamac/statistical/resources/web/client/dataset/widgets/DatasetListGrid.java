package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.ResourceFieldUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.NavigableListGrid;

public class DatasetListGrid extends NavigableListGrid {

    public DatasetListGrid() {
        super();
        this.setShowAllRecords(true);
        this.setFields(ResourceFieldUtils.getDatasetListGridFields());
    }

    public void setDatasets(List<DatasetVersionDto> datasets) {
        removeAllData();
        if (datasets != null) {
            DatasetRecord[] datasetRecords = new DatasetRecord[datasets.size()];
            for (int i = 0; i < datasets.size(); i++) {
                datasetRecords[i] = StatisticalResourcesRecordUtils.getDatasetRecord(datasets.get(i));
            }
            setData(datasetRecords);
        }
    }
}
