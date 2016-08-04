package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetAttributesTabPresenter.DatasetAttributesTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetAttributesTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.AttributePanel;
import org.siemac.metamac.statistical.resources.web.client.model.ds.DsdAttributeDS;
import org.siemac.metamac.statistical.resources.web.client.model.record.DsdAttributeRecord;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.BaseCustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomListGridField;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;

public class DatasetAttributesTabViewImpl extends ViewWithUiHandlers<DatasetAttributesTabUiHandlers> implements DatasetAttributesTabView {

    private VLayout            panel;
    private BaseCustomListGrid listGrid;
    private AttributePanel     attributePanel;

    public DatasetAttributesTabViewImpl() {

        // LIST

        listGrid = new BaseCustomListGrid();
        listGrid.setSelectionType(SelectionStyle.SINGLE);
        listGrid.setAutoFitMaxRecords(StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS);
        listGrid.setAutoFitData(Autofit.VERTICAL);
        listGrid.setMargin(15);
        CustomListGridField codeField = new CustomListGridField(DsdAttributeDS.IDENTIFIER, getConstants().datasetAttributeCode());
        CustomListGridField relationshipField = new CustomListGridField(DsdAttributeDS.RELATIONSHIP_TYPE, getConstants().datasetAttributeRelationship());
        listGrid.setFields(codeField, relationshipField);
        listGrid.addSelectionChangedHandler(new SelectionChangedHandler() {

            @Override
            public void onSelectionChanged(SelectionEvent event) {
                if (event.getSelectedRecord() instanceof DsdAttributeRecord) {
                    DsdAttributeDto dsdAttributeDto = ((DsdAttributeRecord) event.getSelectedRecord()).getDsdAttributeDto();
                    if (!CommonUtils.hasObservationRelationshipType(dsdAttributeDto)) {
                        getUiHandlers().retrieveAttributeInstances(dsdAttributeDto);
                    } else {
                        attributePanel.hide();
                    }
                }
            }
        });

        // MAIN FORM LAYOUT

        attributePanel = new AttributePanel();
        attributePanel.setVisible(false);

        // PANEL LAYOUT

        panel = new VLayout();
        panel.setAutoHeight();
        panel.addMember(listGrid);
        panel.addMember(attributePanel);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setAttributes(List<DsdAttributeDto> attributes) {
        listGrid.setData(StatisticalResourcesRecordUtils.getDsdAttributeRecords(attributes));
        attributePanel.hide();
    }

    @Override
    public void setAttributeInstances(DsdAttributeDto dsdAttributeDto, List<DsdAttributeInstanceDto> dsdAttributeInstanceDtos) {
        attributePanel.showAttributeInstances(dsdAttributeDto, dsdAttributeInstanceDtos);
    }

    @Override
    public void setUiHandlers(DatasetAttributesTabUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        attributePanel.setUiHandlers(uiHandlers);
    }

    @Override
    public void setDimensionsCoverageValues(Map<String, List<CodeItemDto>> dimensionsCoverage) {
        attributePanel.setDimensionsCoverageValues(dimensionsCoverage);
    }

    //
    // RELATED RESOURCES
    //

    @Override
    public void setItemsForDatasetLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults) {
        attributePanel.setItemsForDatasetLevelAttributeValueSelection(externalItemDtos, firstResult, totalResults);
    }

    @Override
    public void setItemsForDimensionOrGroupLevelAttributeValueSelection(List<ExternalItemDto> externalItemDtos, int firstResult, int totalResults) {
        attributePanel.setItemsForDimensionOrGroupLevelAttributeValueSelection(externalItemDtos, firstResult, totalResults);
    }
}
