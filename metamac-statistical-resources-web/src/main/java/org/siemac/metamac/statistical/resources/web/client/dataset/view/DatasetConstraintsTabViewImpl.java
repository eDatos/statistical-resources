package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DimensionConstraintsDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.record.DimensionConstraintsRecord;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetConstraintsTabPresenter.DatasetConstraintsTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.ConstraintsClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetConstraintsTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class DatasetConstraintsTabViewImpl extends ViewWithUiHandlers<DatasetConstraintsTabUiHandlers> implements DatasetConstraintsTabView {

    private VLayout              panel;
    private ConstraintsPanel     constraintsPanel;

    private DatasetVersionDto    datasetVersionDto;
    private ContentConstraintDto contentConstraintDto;
    private RegionValueDto       regionValueDto;

    public DatasetConstraintsTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(5);
        panel.setHeight100();

        constraintsPanel = new ConstraintsPanel();
        panel.addMember(constraintsPanel);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setConstraint(DatasetVersionDto datasetVersionDto, ContentConstraintDto contentConstraintDto, RegionValueDto regionValueDto) {
        this.datasetVersionDto = datasetVersionDto;
        this.contentConstraintDto = contentConstraintDto;
        constraintsPanel.updateVisibility(datasetVersionDto, contentConstraintDto);

        // TODO METAMAC-1985
    }

    @Override
    public void setRelatedDsdDimensions(List<DsdDimensionDto> dimensions) {
        constraintsPanel.setDimensions(dimensions, regionValueDto);
    }

    private class ConstraintsPanel extends VLayout {

        private ToolStrip             toolStrip;
        private CustomToolStripButton enableConstraintsButton;
        private CustomToolStripButton disableConstraintsButton;
        private CustomListGrid        constraintsList;

        public ConstraintsPanel() {

            createToolStrip();
            createConstraintsList();

            addMember(toolStrip);
            addMember(constraintsList);
        }

        public void setDimensions(List<DsdDimensionDto> dimensions, RegionValueDto regionValueDto) {
            DimensionConstraintsRecord[] records = StatisticalResourcesRecordUtils.getDimensionConstraintsRecords(dimensions, regionValueDto);
            constraintsList.setData(records);
        }

        private void createToolStrip() {
            toolStrip = new ToolStrip();
            toolStrip.setWidth100();

            enableConstraintsButton = createEnableConstraintsButton();
            toolStrip.addButton(enableConstraintsButton);

            disableConstraintsButton = createDisableConstraintsButton();
            toolStrip.addButton(disableConstraintsButton);
        }

        private void createConstraintsList() {
            constraintsList = new CustomListGrid();
            constraintsList.setVisible(true);
            constraintsList.setAutoFitMaxRecords(StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS);
            constraintsList.setAutoFitData(Autofit.VERTICAL);
            constraintsList.setDataSource(new DimensionConstraintsDS());
            constraintsList.setUseAllDataSourceFields(false);

            ListGridField dimensionIdField = new ListGridField(DimensionConstraintsDS.DIMENSION_ID, getConstants().datasetConstraintDimensionId());
            dimensionIdField.setAlign(Alignment.LEFT);
            ListGridField inclusionTypeField = new ListGridField(DimensionConstraintsDS.INCLUSION_TYPE, getConstants().datasetConstraintInclusionType());
            ListGridField valuesField = new ListGridField(DimensionConstraintsDS.VALUES, getConstants().datasetConstraintValues());

            constraintsList.setFields(dimensionIdField, inclusionTypeField, valuesField);

            constraintsList.addSelectionChangedHandler(new SelectionChangedHandler() {

                @Override
                public void onSelectionChanged(SelectionEvent event) {
                    // TODO METAMAC-1985
                }
            });
        }

        private CustomToolStripButton createEnableConstraintsButton() {
            CustomToolStripButton enableConstraintButton = new CustomToolStripButton(getConstants().actionEnableDatasetConstraints());
            enableConstraintButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    getUiHandlers().createConstraint();
                }
            });
            return enableConstraintButton;
        }

        private CustomToolStripButton createDisableConstraintsButton() {
            CustomToolStripButton disableConstraintButton = new CustomToolStripButton(getConstants().actionDisableDatasetConstraints());
            disableConstraintButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    DeleteConfirmationWindow deleteConfirmationWindow = new DeleteConfirmationWindow(getMessages().datasetConstraintDisableConfirmationTitle(), getMessages()
                            .datasetConstraintDisableConfirmation());
                    deleteConfirmationWindow.show();
                    deleteConfirmationWindow.getYesButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

                        @Override
                        public void onClick(ClickEvent event) {
                            getUiHandlers().deleteConstraint(contentConstraintDto, regionValueDto);
                        }
                    });
                }
            });
            return disableConstraintButton;
        }

        private void updateVisibility(DatasetVersionDto datasetVersionDto, ContentConstraintDto contentConstraintDto) {
            constraintsList.setVisible(false);
            if (contentConstraintDto == null) {
                enableConstraintsButton.setVisible(ConstraintsClientSecurityUtils.canCreateContentConstraint(datasetVersionDto.getStatisticalOperation().getCode()));
                disableConstraintsButton.setVisible(false);
            } else {
                enableConstraintsButton.setVisible(false);
                disableConstraintsButton
                        .setVisible(ConstraintsClientSecurityUtils.canDeleteContentConstraint(datasetVersionDto.getStatisticalOperation().getCode(), datasetVersionDto.getProcStatus()));
                constraintsList.setVisible(true);
            }
        }
    }
}
