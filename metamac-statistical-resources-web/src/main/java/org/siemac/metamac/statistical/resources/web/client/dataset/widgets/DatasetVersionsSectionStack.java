package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.VersionableResourceSectionStack;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

public class DatasetVersionsSectionStack extends VersionableResourceSectionStack {

    public DatasetVersionsSectionStack(String title) {
        super(title);
    }

    public void setDatasetVersions(List<DatasetVersionDto> datasetVersionDtos) {
        listGrid.selectAllRecords();
        listGrid.removeSelectedData();
        for (DatasetVersionDto datasetDto : datasetVersionDtos) {
            listGrid.addData(StatisticalResourcesRecordUtils.getDatasetRecord(datasetDto));
        }
    }

    public void selectDatasetVersion(DatasetVersionDto datasetVersionDto) {
        RecordList recordList = listGrid.getRecordList();
        Record record = recordList.find(DatasetDS.URN, datasetVersionDto.getUrn());
        if (record != null) {
            listGrid.selectRecord(record);
        }
    }
}
