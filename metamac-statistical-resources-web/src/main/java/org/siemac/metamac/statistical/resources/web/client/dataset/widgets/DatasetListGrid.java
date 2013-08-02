package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;

public class DatasetListGrid extends BaseCustomListGrid {

    public DatasetListGrid() {
        super();

        this.setShowAllRecords(true);

        CustomListGridField identifierDatasetField = new CustomListGridField(DatasetDS.CODE, getConstants().identifiableStatisticalResourceCode());
        CustomListGridField titleDatasetField = new CustomListGridField(DatasetDS.TITLE, getConstants().nameableStatisticalResourceTitle());

        this.setFields(identifierDatasetField, titleDatasetField);
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
