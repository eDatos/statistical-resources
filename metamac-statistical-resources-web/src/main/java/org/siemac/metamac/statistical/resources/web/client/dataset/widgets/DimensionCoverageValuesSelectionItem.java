package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.CodeItemDS;
import org.siemac.metamac.statistical.resources.web.client.model.record.CodeItemRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;
import org.siemac.metamac.web.common.client.widgets.form.CustomDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomCanvasItem;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.HeaderSpan;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DimensionCoverageValuesSelectionItem extends CustomCanvasItem {

    private BaseCustomListGrid selectedDimensionValuesListGrid;

    private CustomDynamicForm  dimensionsForm;

    private boolean            editionMode;

    public DimensionCoverageValuesSelectionItem(String name, String title, Set<String> dimensionIds, boolean editionMode) {
        super(name, title);
        setCellStyle("dragAndDropCellStyle");

        this.editionMode = editionMode;

        // Dimensions layout (form)

        dimensionsForm = new CustomDynamicForm();
        dimensionsForm.setNumCols(3);
        dimensionsForm.setColWidths("33%", "33%", "33%");
        List<DimensionsListGridItem> dimensionItems = new ArrayList<DimensionsListGridItem>();
        for (String dimensionId : dimensionIds) {
            DimensionsListGridItem item = createDimensionListGridItem(dimensionId);
            dimensionItems.add(item);
        }
        dimensionsForm.setFields(dimensionItems.toArray(new DimensionsListGridItem[dimensionItems.size()]));

        // Selected dimensions list

        selectedDimensionValuesListGrid = new BaseCustomListGrid();
        selectedDimensionValuesListGrid.setAutoFitData(Autofit.VERTICAL);
        selectedDimensionValuesListGrid.setAutoFitMaxRecords(8);
        CustomListGridField dimensionField = new CustomListGridField(CodeItemDS.DIMENSION_ID, getConstants().codeItemDimension());
        CustomListGridField codeField = new CustomListGridField(CodeItemDS.CODE, getConstants().codeItemCode());
        CustomListGridField titleField = new CustomListGridField(CodeItemDS.TITLE, getConstants().codeItemTitle());
        selectedDimensionValuesListGrid.setFields(dimensionField, codeField, titleField);

        VLayout mainPanel = new VLayout(10);
        mainPanel.setAutoHeight();
        mainPanel.setOverflow(Overflow.VISIBLE);
        mainPanel.addMember(dimensionsForm);
        mainPanel.addMember(selectedDimensionValuesListGrid);
        mainPanel.setPadding(10);
        setCanvas(mainPanel);
    }

    private DimensionsListGridItem createDimensionListGridItem(final String dimensionId) {
        DimensionsListGridItem dimensionsListGridItem = new DimensionsListGridItem(dimensionId);
        if (editionMode) {
            dimensionsListGridItem.getListGrid().addSelectionChangedHandler(new SelectionChangedHandler() {

                @Override
                public void onSelectionChanged(SelectionEvent event) {
                    if (event.getRecord() instanceof CodeItemRecord) {
                        CodeItemRecord selectedCodeItemRecord = (CodeItemRecord) event.getRecord();
                        if (event.getState()) {
                            // The record is being selected, so it is added to the selected values list
                            CodeItemRecord record = StatisticalResourcesRecordUtils.getCodeItemRecord(dimensionId, selectedCodeItemRecord.getCodeItemDto());
                            selectedDimensionValuesListGrid.addData(record);
                        } else {
                            // The record is being unselected, so it is removed from the selected values list
                            if (selectedDimensionValuesListGrid.getRecordList() != null) {
                                Map<String, String> criteriaMap = new HashMap<String, String>();
                                criteriaMap.put(CodeItemDS.DIMENSION_ID, dimensionId);
                                criteriaMap.put(CodeItemDS.CODE, selectedCodeItemRecord.getCode());
                                Record[] records = selectedDimensionValuesListGrid.getRecordList().findAll(criteriaMap);
                                if (records != null && records.length > 0) {
                                    selectedDimensionValuesListGrid.removeData(records[0]); // the result should be only one
                                }
                            }
                        }
                    }
                }
            });
        }
        return dimensionsListGridItem;
    }

    public void setDimensionCoverageValues(String dimensionId, List<CodeItemDto> codeItemDtos) {
        if (dimensionsForm.getItem(dimensionId) != null) {
            CodeItemRecord[] records = StatisticalResourcesRecordUtils.getCodeItemRecords(codeItemDtos);
            ((DimensionsListGridItem) dimensionsForm.getItem(dimensionId)).getListGrid().setData(records);
        }
    }

    private class DimensionsListGridItem extends CustomCanvasItem {

        protected BaseCustomListGrid customListGrid;

        public DimensionsListGridItem(String name) {
            super(name, StringUtils.EMPTY);
            setCellStyle("dragAndDropCellStyle");
            setShowTitle(false);

            customListGrid = new BaseCustomListGrid();

            if (editionMode) {
                customListGrid.setSelectionType(SelectionStyle.SIMPLE);
                customListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
                customListGrid.setCanSelectAll(true);
            }

            customListGrid.setAutoFitMaxRecords(6);
            customListGrid.setAutoFitData(Autofit.VERTICAL);
            customListGrid.setHeaderHeight(40);
            CustomListGridField codeField = new CustomListGridField(CodeItemDS.CODE, getConstants().codeItemCode());
            CustomListGridField titleField = new CustomListGridField(CodeItemDS.TITLE, getConstants().codeItemTitle());
            customListGrid.setFields(codeField, titleField);
            customListGrid.setHeaderSpans(new HeaderSpan(name, new String[]{CodeItemDS.CODE, CodeItemDS.TITLE}));

            HLayout hLayout = new HLayout();
            hLayout.addMember(customListGrid);
            hLayout.setStyleName("canvasCellStyle");

            setCanvas(hLayout);
        }

        public BaseCustomListGrid getListGrid() {
            return customListGrid;
        }
    }
}
