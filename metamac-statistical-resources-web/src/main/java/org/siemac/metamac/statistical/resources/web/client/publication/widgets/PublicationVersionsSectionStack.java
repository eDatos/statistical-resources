package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.VersionableResourceSectionStack;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;

public class PublicationVersionsSectionStack extends VersionableResourceSectionStack {

    public PublicationVersionsSectionStack(String title) {
        super(title);
    }

    public void setPublicationVersions(List<PublicationVersionDto> publicationVersionDtos) {
        listGrid.selectAllRecords();
        listGrid.removeSelectedData();
        for (PublicationVersionDto publicationVersionDto : publicationVersionDtos) {
            listGrid.addData(StatisticalResourcesRecordUtils.getPublicationRecord(publicationVersionDto));
        }
    }

    public void selectPublicationVersion(PublicationVersionDto publicationVersionDto) {
        RecordList recordList = listGrid.getRecordList();
        Record record = recordList.find(PublicationDS.URN, publicationVersionDto.getUrn());
        if (record != null) {
            listGrid.selectRecord(record);
        }
    }
}
