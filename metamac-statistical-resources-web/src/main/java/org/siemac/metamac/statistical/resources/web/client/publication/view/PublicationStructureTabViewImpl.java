package org.siemac.metamac.statistical.resources.web.client.publication.view;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationStructureTabPresenter.PublicationStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationStructureTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.PublicationStructureTreeGrid;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PublicationStructureTabViewImpl extends ViewWithUiHandlers<PublicationStructureTabUiHandlers> implements PublicationStructureTabView {

    private VLayout                      panel;
    private PublicationStructureTreeGrid publicationStructureTreeGrid;
    private InternationalMainFormLayout  mainFormLayout;

    @Inject
    public PublicationStructureTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(15);

        publicationStructureTreeGrid = new PublicationStructureTreeGrid();

        mainFormLayout = new InternationalMainFormLayout();

        HLayout subPanel = new HLayout(10);
        subPanel.addMember(publicationStructureTreeGrid);
        subPanel.addMember(mainFormLayout);

        panel.addMember(subPanel);
    }

    @Override
    public void setPublicationStructure(PublicationStructureDto publicationStructureDto) {
        publicationStructureTreeGrid.setPublicationStructure(publicationStructureDto);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(PublicationStructureTabUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        publicationStructureTreeGrid.setUiHandlers(uiHandlers);
    }
}
