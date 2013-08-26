package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.CodeItemDS;
import org.siemac.metamac.statistical.resources.web.client.model.record.CodeItemRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomCanvasItem;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.grid.HeaderSpan;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DimensionCoverageValuesSelectionItem extends CustomCanvasItem {

    private Map<String, ListGrid> dimensionListGrids;
    private BaseCustomListGrid    selectedDimensionValuesListGrid;

    public DimensionCoverageValuesSelectionItem(String name, String title, Set<String> dimensionIds) {
        super(name, title);
        setCellStyle("dragAndDropCellStyle");

        // Dimensions layout

        HLayout dimensionsLayout = new HLayout(2);

        dimensionListGrids = new HashMap<String, ListGrid>();
        for (String dimensionId : dimensionIds) {
            CustomListGrid dimemsionListGrid = createDimensionListGrid(dimensionId);
            dimensionListGrids.put(dimensionId, dimemsionListGrid);
            dimensionsLayout.addMember(dimemsionListGrid);
        }

        // Selected dimensions list

        selectedDimensionValuesListGrid = new BaseCustomListGrid();
        selectedDimensionValuesListGrid.setAutoFitData(Autofit.VERTICAL);
        selectedDimensionValuesListGrid.setAutoFitMaxRecords(5);
        CustomListGridField dimensionField = new CustomListGridField(CodeItemDS.DIMENSION_ID, getConstants().codeItemDimension());
        CustomListGridField codeField = new CustomListGridField(CodeItemDS.CODE, getConstants().codeItemCode());
        CustomListGridField titleField = new CustomListGridField(CodeItemDS.TITLE, getConstants().codeItemTitle());
        selectedDimensionValuesListGrid.setFields(dimensionField, codeField, titleField);

        VLayout mainPanel = new VLayout(10);
        mainPanel.setAutoHeight();
        mainPanel.setOverflow(Overflow.VISIBLE);
        mainPanel.addMember(dimensionsLayout);
        mainPanel.addMember(selectedDimensionValuesListGrid);
        setCanvas(mainPanel);
    }

    private CustomListGrid createDimensionListGrid(final String dimensionId) {
        CustomListGrid customListGrid = new CustomListGrid();
        customListGrid.setAutoFitMaxRecords(6);
        customListGrid.setAutoFitData(Autofit.VERTICAL);
        customListGrid.setHeaderHeight(40);
        CustomListGridField codeField = new CustomListGridField(CodeItemDS.CODE, getConstants().codeItemCode());
        CustomListGridField titleField = new CustomListGridField(CodeItemDS.TITLE, getConstants().codeItemTitle());
        customListGrid.setFields(codeField, titleField);
        customListGrid.setHeaderSpans(new HeaderSpan(dimensionId, new String[]{CodeItemDS.CODE, CodeItemDS.TITLE}));
        customListGrid.addRecordClickHandler(new RecordClickHandler() {

            @Override
            public void onRecordClick(RecordClickEvent event) {
                if (event.getRecord() instanceof CodeItemRecord) {
                    CodeItemRecord selectedCodeItemRecord = (CodeItemRecord) event.getRecord();
                    if (event.getViewer().isSelected(selectedCodeItemRecord)) {
                        // The record is being selected, so it is added to the selected values list
                        CodeItemRecord record = StatisticalResourcesRecordUtils.getCodeItemRecord(dimensionId, selectedCodeItemRecord.getCodeItemDto());
                        selectedDimensionValuesListGrid.addData(record);
                    } else {
                        // The record is being unselected, so it is removed from the selected values list
                        if (dimensionListGrids.containsKey(dimensionId)) {
                            if (selectedDimensionValuesListGrid.getRecordList() != null) {
                                Map<String, String> criteriaMap = new HashMap<String, String>();
                                criteriaMap.put(CodeItemDS.DIMENSION_ID, dimensionId);
                                criteriaMap.put(CodeItemDS.CODE, selectedCodeItemRecord.getCode());
                                Record[] records = selectedDimensionValuesListGrid.getRecordList().findAll(criteriaMap);
                                if (records != null && records.length > 0) {
                                    // TODO
                                }
                            }
                        }
                    }
                }
            }
        });
        return customListGrid;
    }

    public void setDimensionCoverageValues(String dimensionId, List<CodeItemDto> codeItemDtos) {
        if (dimensionListGrids.containsKey(dimensionId)) {
            CodeItemRecord[] records = StatisticalResourcesRecordUtils.getCodeItemRecords(codeItemDtos);
            dimensionListGrids.get(dimensionId).setData(records);
        }
    }
}
