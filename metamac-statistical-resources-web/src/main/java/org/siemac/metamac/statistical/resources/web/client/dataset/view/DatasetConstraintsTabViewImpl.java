package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.constraint.ContentConstraintDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetConstraintsTabPresenter.DatasetConstraintsTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetConstraintsTabUiHandlers;
import org.siemac.metamac.web.common.client.widgets.CustomListGrid;
import org.siemac.metamac.web.common.client.widgets.CustomToolStripButton;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class DatasetConstraintsTabViewImpl extends ViewWithUiHandlers<DatasetConstraintsTabUiHandlers> implements DatasetConstraintsTabView {

    private VLayout panel;

    public DatasetConstraintsTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(5);
        panel.setHeight100();

        ConstraintsPanel constraintsPanel = new ConstraintsPanel();
        panel.addMember(constraintsPanel);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setConstraint(ContentConstraintDto contentConstraintDto) {
        // TODO METAMAC-1985

    }

    private class ConstraintsPanel extends VLayout {

        private CustomToolStripButton enableConstraintsButton;
        private CustomToolStripButton disableConstraintsButton;
        private CustomListGrid        constraintsList;

        public ConstraintsPanel() {

            addMember(createToolStrip());
        }

        private ToolStrip createToolStrip() {
            ToolStrip toolStrip = new ToolStrip();
            toolStrip.setWidth100();

            enableConstraintsButton = createEnableConstraintsButton();
            toolStrip.addButton(enableConstraintsButton);

            disableConstraintsButton = createDisableConstraintsButton();
            toolStrip.addButton(disableConstraintsButton);

            return toolStrip;

        }

        private CustomToolStripButton createEnableConstraintsButton() {
            CustomToolStripButton deleteDatasourceButton = new CustomToolStripButton(getConstants().actionEnableDatasetConstraints());
            deleteDatasourceButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    // TODO METAMAC-1985
                }
            });
            return deleteDatasourceButton;
        }

        private CustomToolStripButton createDisableConstraintsButton() {
            CustomToolStripButton importDatasourcesButton = new CustomToolStripButton(getConstants().actionDisableDatasetConstraints());
            importDatasourcesButton.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    // TODO METAMAC-1985
                }
            });
            return importDatasourcesButton;
        }
    }
}
