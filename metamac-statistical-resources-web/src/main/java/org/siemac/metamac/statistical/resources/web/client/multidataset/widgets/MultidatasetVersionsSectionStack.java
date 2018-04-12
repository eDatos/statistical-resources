package org.siemac.metamac.statistical.resources.web.client.multidataset.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.SiemacMetadataResourceSectionStack;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.VersionableResourceSectionStack;
import org.siemac.metamac.statistical.resources.web.client.multidataset.model.ds.MultidatasetDS;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;

import com.smartgwt.client.types.SortDirection;

public class MultidatasetVersionsSectionStack extends SiemacMetadataResourceSectionStack {

    public MultidatasetVersionsSectionStack(String title) {
        super(title);
        setListGridFields();
    }

    public void setMultidatasetVersions(List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos) {
        listGrid.selectAllRecords();
        listGrid.removeSelectedData();
        for (MultidatasetVersionBaseDto multidatasetVersionDto : multidatasetVersionBaseDtos) {
            listGrid.addData(StatisticalResourcesRecordUtils.getMultidatasetRecord(multidatasetVersionDto));
        }
        listGrid.sort(MultidatasetDS.VERSION, SortDirection.DESCENDING);
    }

    public void selectMultidatasetVersion(String urn) {
        selectRecord(MultidatasetDS.URN, urn);
    }
}
