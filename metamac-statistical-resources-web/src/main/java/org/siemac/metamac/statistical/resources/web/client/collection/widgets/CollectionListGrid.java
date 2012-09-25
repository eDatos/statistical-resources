package org.siemac.metamac.statistical.resources.web.client.collection.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.web.client.collection.model.ds.CollectionDS;
import org.siemac.metamac.statistical.resources.web.client.collection.model.record.CollectionRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;

import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CollectionListGrid extends BaseCustomListGrid {

    public CollectionListGrid() {
        super();

        this.setShowAllRecords(true);

        ListGridField identifierField = new ListGridField(CollectionDS.IDENTIFIER, getConstants().collectionIdentifier());
        ListGridField titleField = new ListGridField(CollectionDS.TITLE, getConstants().collectionTitle());

        // ToolTip
        identifierField.setShowHover(true);
        identifierField.setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                CollectionRecord collectionRecord = (CollectionRecord) record;
                return collectionRecord.getIdentifier();
            }
        });
        titleField.setShowHover(true);
        titleField.setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                CollectionRecord collectionRecord = (CollectionRecord) record;
                return collectionRecord.getName();
            }
        });
        this.setFields(identifierField, titleField);
    }

    public void setCollections(List<CollectionDto> collections) {
        removeAllData();
        if (collections != null) {
            CollectionRecord[] records = new CollectionRecord[collections.size()];
            for (int i = 0; i < collections.size(); i++) {
                records[i] = StatisticalResourcesRecordUtils.getCollectionRecord(collections.get(i));
            }
            setData(records);
        }
    }

}
