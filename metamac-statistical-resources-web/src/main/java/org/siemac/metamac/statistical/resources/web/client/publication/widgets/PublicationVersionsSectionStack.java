package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.VersionableResourceSectionStack;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

public class PublicationVersionsSectionStack extends VersionableResourceSectionStack {

    public PublicationVersionsSectionStack(String title) {
        super(title);
    }

    public void setPublicationVersions(List<PublicationVersionBaseDto> publicationVersionBaseDtos) {
        listGrid.selectAllRecords();
        listGrid.removeSelectedData();
        for (PublicationVersionBaseDto publicationVersionDto : publicationVersionBaseDtos) {
            listGrid.addData(StatisticalResourcesRecordUtils.getPublicationRecord(publicationVersionDto));
        }
    }

    public void selectPublicationVersion(String urn) {
        RecordList recordList = listGrid.getRecordList();
        Record record = recordList.find(PublicationDS.URN, urn);
        if (record != null) {
            listGrid.selectRecord(record);
        }
    }
}
