package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.VersionableResourceSectionStack;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;

import com.smartgwt.client.types.SortDirection;

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
        listGrid.sort(PublicationDS.VERSION, SortDirection.ASCENDING);
    }

    public void selectPublicationVersion(String urn) {
        selectRecord(PublicationDS.URN, urn);
    }
}
