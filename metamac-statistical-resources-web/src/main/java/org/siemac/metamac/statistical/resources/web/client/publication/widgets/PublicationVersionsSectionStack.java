package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.SiemacMetadataResourceSectionStack;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.VersionableResourceSectionStack;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;

import com.smartgwt.client.types.SortDirection;

public class PublicationVersionsSectionStack extends SiemacMetadataResourceSectionStack {

    public PublicationVersionsSectionStack(String title) {
        super(title);
        setListGridFields();
    }

    public void setPublicationVersions(List<PublicationVersionBaseDto> publicationVersionBaseDtos) {
        listGrid.selectAllRecords();
        listGrid.removeSelectedData();
        for (PublicationVersionBaseDto publicationVersionDto : publicationVersionBaseDtos) {
            listGrid.addData(StatisticalResourcesRecordUtils.getPublicationRecord(publicationVersionDto));
        }
        listGrid.sort(PublicationDS.VERSION, SortDirection.DESCENDING);
    }

    public void selectPublicationVersion(String urn) {
        selectRecord(PublicationDS.URN, urn);
    }
}
