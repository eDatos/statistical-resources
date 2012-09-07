package org.siemac.metamac.statistical.resources.web.client.operation.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DatasetListGrid;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationResourcesPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.view.handlers.OperationResourcesUiHandlers;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class OperationResourcesViewImpl extends ViewImpl implements OperationResourcesPresenter.OperationResourcesView {

    private OperationResourcesUiHandlers uiHandlers;

    private DatasetListGrid              datasetsListGrid;

    private VLayout                      panel;
    private SectionStack                 sections;

    @Inject
    public OperationResourcesViewImpl() {
        sections = new SectionStack();
        sections.setWidth100();
        sections.setVisibilityMode(VisibilityMode.MULTIPLE);
        sections.setAnimateSections(true);
        sections.setOverflow(Overflow.HIDDEN);

        datasetsListGrid = new DatasetListGrid();

        SectionStackSection lastDatasetsModifiedSection = new SectionStackSection();
        lastDatasetsModifiedSection.setTitle(getConstants().datasetsLastModified());
        lastDatasetsModifiedSection.setExpanded(false);
        lastDatasetsModifiedSection.setItems(datasetsListGrid);
        sections.addSection(lastDatasetsModifiedSection);

        panel = new VLayout();
        panel.addMember(sections);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(OperationResourcesUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
