package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;

public class PublicationListGrid extends BaseCustomListGrid {

    public PublicationListGrid() {
        super();

        this.setShowAllRecords(true);

        CustomListGridField identifierField = new CustomListGridField(PublicationDS.CODE, getConstants().identifiableStatisticalResourceCode());
        CustomListGridField titleField = new CustomListGridField(PublicationDS.TITLE, getConstants().nameableStatisticalResourceTitle());

        this.setFields(identifierField, titleField);
    }

    public void setPublications(List<PublicationVersionDto> publications) {
        removeAllData();
        if (publications != null) {
            PublicationRecord[] records = new PublicationRecord[publications.size()];
            for (int i = 0; i < publications.size(); i++) {
                records[i] = StatisticalResourcesRecordUtils.getPublicationRecord(publications.get(i));
            }
            setData(records);
        }
    }
}
