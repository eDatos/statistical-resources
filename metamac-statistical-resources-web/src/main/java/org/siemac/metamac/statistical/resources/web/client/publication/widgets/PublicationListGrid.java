package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.ResourceFieldUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.NavigableListGrid;

public class PublicationListGrid extends NavigableListGrid {

    public PublicationListGrid() {
        super();
        this.setShowAllRecords(true);
        this.setFields(ResourceFieldUtils.getPublicationListGridFields());
    }

    public void setPublications(List<PublicationVersionBaseDto> publications) {
        removeAllData();
        if (publications != null) {
            PublicationRecord[] records = StatisticalResourcesRecordUtils.getPublicationRecords(publications);
            setData(records);
        }
    }
}
