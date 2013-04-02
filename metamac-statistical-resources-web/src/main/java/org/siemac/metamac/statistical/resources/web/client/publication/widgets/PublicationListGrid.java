package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.PublicationRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;

import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PublicationListGrid extends BaseCustomListGrid {

    public PublicationListGrid() {
        super();

        this.setShowAllRecords(true);

        ListGridField identifierField = new ListGridField(PublicationDS.CODE, getConstants().identifiableStatisticalResourceCode());
        ListGridField titleField = new ListGridField(PublicationDS.TITLE, getConstants().nameableStatisticalResourceTitle());

        // ToolTip
        identifierField.setShowHover(true);
        identifierField.setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                PublicationRecord collectionRecord = (PublicationRecord) record;
                return collectionRecord.getIdentifier();
            }
        });
        titleField.setShowHover(true);
        titleField.setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                PublicationRecord collectionRecord = (PublicationRecord) record;
                return collectionRecord.getName();
            }
        });
        this.setFields(identifierField, titleField);
    }

    public void setPublications(List<PublicationDto> collections) {
        removeAllData();
        if (collections != null) {
            PublicationRecord[] records = new PublicationRecord[collections.size()];
            for (int i = 0; i < collections.size(); i++) {
                records[i] = StatisticalResourcesRecordUtils.getPublicationRecord(collections.get(i));
            }
            setData(records);
        }
    }
}
