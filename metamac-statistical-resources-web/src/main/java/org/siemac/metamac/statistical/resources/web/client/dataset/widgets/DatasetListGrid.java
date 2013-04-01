package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DatasetRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;

import com.smartgwt.client.widgets.grid.HoverCustomizer;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DatasetListGrid extends BaseCustomListGrid {

    public DatasetListGrid() {
        super();

        this.setShowAllRecords(true);

        ListGridField identifierDatasetField = new ListGridField(DatasetDS.CODE, getConstants().identifiableStatisticalResourceCode());
        ListGridField titleDatasetField = new ListGridField(DatasetDS.TITLE, getConstants().nameableStatisticalResourceTitle());

        // ToolTip
        identifierDatasetField.setShowHover(true);
        identifierDatasetField.setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                DatasetRecord datasetRecord = (DatasetRecord) record;
                return datasetRecord.getIdentifier();
            }
        });
        titleDatasetField.setShowHover(true);
        titleDatasetField.setHoverCustomizer(new HoverCustomizer() {

            @Override
            public String hoverHTML(Object value, ListGridRecord record, int rowNum, int colNum) {
                DatasetRecord dsdRecord = (DatasetRecord) record;
                return dsdRecord.getName();
            }
        });
        this.setFields(identifierDatasetField, titleDatasetField);
    }

    public void setDatasets(List<DatasetDto> datasets) {
        removeAllData();
        if (datasets != null) {
            DatasetRecord[] datasetRecords = new DatasetRecord[datasets.size()];
            for (int i = 0; i < datasets.size(); i++) {
                datasetRecords[i] = StatisticalResourcesRecordUtils.getDatasetRecord(datasets.get(i));
            }
            setData(datasetRecords);
        }
    }

}
