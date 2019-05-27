package org.siemac.metamac.statistical.resources.web.client.multidataset.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.multidataset.model.record.MultidatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.ResourceFieldUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.NavigableListGrid;

public class MultidatasetListGrid extends NavigableListGrid {

    public MultidatasetListGrid() {
        super();
        this.setShowAllRecords(true);
        this.setFields(ResourceFieldUtils.getMultidatasetListGridFields());
        this.setCanMultiSort(Boolean.FALSE);
    }

    public void setMultidatasets(List<MultidatasetVersionBaseDto> multidatasets) {
        removeAllData();
        if (multidatasets != null) {
            MultidatasetRecord[] records = StatisticalResourcesRecordUtils.getMultidatasetRecords(multidatasets);
            setData(records);
        }
    }
}
