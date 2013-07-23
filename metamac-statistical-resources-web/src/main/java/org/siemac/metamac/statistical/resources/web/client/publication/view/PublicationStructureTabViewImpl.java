package org.siemac.metamac.statistical.resources.web.client.publication.view;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationStructureTabPresenter.PublicationStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationStructureTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.PublicationStructureTreeGrid;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.layout.VLayout;

public class PublicationStructureTabViewImpl extends ViewWithUiHandlers<PublicationStructureTabUiHandlers> implements PublicationStructureTabView {

    private VLayout                      panel;
    private PublicationStructureTreeGrid publicationStructureTreeGrid;

    @Inject
    public PublicationStructureTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(15);

        publicationStructureTreeGrid = new PublicationStructureTreeGrid();

        panel.addMember(publicationStructureTreeGrid);
    }

    @Override
    public void setPublicationStructure(PublicationVersionDto publicationVersionDto, PublicationStructureDto publicationStructureDto) {
        publicationStructureTreeGrid.setElements(publicationVersionDto, publicationStructureDto.getElements());
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
