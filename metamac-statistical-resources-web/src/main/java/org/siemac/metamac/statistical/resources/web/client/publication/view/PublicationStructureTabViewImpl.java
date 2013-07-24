package org.siemac.metamac.statistical.resources.web.client.publication.view;

import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ElementLevelDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationStructureTabPresenter.PublicationStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationStructureTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.PublicationStructureElementPanel;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.PublicationStructureTreeGrid;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.TreeNodeClickAction;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PublicationStructureTabViewImpl extends ViewWithUiHandlers<PublicationStructureTabUiHandlers> implements PublicationStructureTabView {

    private VLayout                          panel;
    private PublicationStructureTreeGrid     publicationStructureTreeGrid;
    private PublicationStructureElementPanel publicationStructureElementPanel;

    @Inject
    public PublicationStructureTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(15);

        publicationStructureTreeGrid = new PublicationStructureTreeGrid(new TreeNodeClickAction() {

            @Override
            public void onNodeClick(ElementLevelDto elementLevelDto) {
                if (elementLevelDto != null) {
                    setElementInPanel(elementLevelDto.getChapter() != null ? elementLevelDto.getChapter() : elementLevelDto.getCube());
                }
            }
        });

        publicationStructureElementPanel = new PublicationStructureElementPanel();
        publicationStructureElementPanel.setVisible(false);

        HLayout subPanel = new HLayout(10);
        subPanel.addMember(publicationStructureTreeGrid);
        subPanel.addMember(publicationStructureElementPanel);

        panel.addMember(subPanel);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(PublicationStructureTabUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        publicationStructureTreeGrid.setUiHandlers(uiHandlers);
        publicationStructureElementPanel.setUiHandlers(uiHandlers);
    }

    @Override
    public void setPublicationStructure(PublicationStructureDto publicationStructureDto) {
        publicationStructureTreeGrid.setPublicationStructure(publicationStructureDto);
        publicationStructureElementPanel.setPublicationVersion(publicationStructureDto.getPublicationVersion());
        publicationStructureElementPanel.hide();
    }

    @Override
    public void setPublicationStructure(PublicationStructureDto publicationStructureDto, NameableStatisticalResourceDto selectedElement) {
        setPublicationStructure(publicationStructureDto);
        selectElement(selectedElement);
    };

    private void selectElement(NameableStatisticalResourceDto element) {
        publicationStructureTreeGrid.selectElement(element);
        setElementInPanel(element);
    }

    private void setElementInPanel(NameableStatisticalResourceDto element) {
        publicationStructureElementPanel.setElement(element);
        publicationStructureElementPanel.show();
    }
}
