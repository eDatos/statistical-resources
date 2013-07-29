package org.siemac.metamac.statistical.resources.web.client.publication.view;

import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ElementLevelDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationStructureTabPresenter.PublicationStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationStructureTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.PublicationStructureElementPanel;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.PublicationStructureTreeGrid;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.TreeNodeClickAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class PublicationStructureTabViewImpl extends ViewWithUiHandlers<PublicationStructureTabUiHandlers> implements PublicationStructureTabView {

    public static Long                       FIST_TREE_ELEMENT_POSITION = 1L;

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

        publicationStructureTreeGrid.addCreateChapterMenuItemClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                ElementLevelDto selectedContextClickElement = publicationStructureTreeGrid.getSelectedContextClickElement();

                ChapterDto chapterDto = new ChapterDto();
                chapterDto.setOrderInLevel(FIST_TREE_ELEMENT_POSITION);
                if (selectedContextClickElement != null && selectedContextClickElement.getChapter() != null) {
                    chapterDto.setParentChapterUrn(selectedContextClickElement.getChapter().getUrn());
                }

                setNewElementInPanel(chapterDto);
            }
        });

        publicationStructureTreeGrid.addCreateCubeMenuItemClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                ElementLevelDto selectedContextClickElement = publicationStructureTreeGrid.getSelectedContextClickElement();

                CubeDto cubeDto = new CubeDto();
                cubeDto.setOrderInLevel(FIST_TREE_ELEMENT_POSITION);
                if (selectedContextClickElement != null && selectedContextClickElement.getChapter() != null) {
                    cubeDto.setParentChapterUrn(selectedContextClickElement.getChapter().getUrn());
                }

                setNewElementInPanel(cubeDto);
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

    private void setNewElementInPanel(NameableStatisticalResourceDto element) {
        setElementInPanel(element);
        publicationStructureElementPanel.setMainFormLayoutEditionMode();
    }

    //
    // RELATED RESOURCES
    //

    @Override
    public void setStatisticalOperationsForDatasetSelection(GetStatisticalOperationsPaginatedListResult result) {
        publicationStructureElementPanel.setStatisticalOperationsForDatasetSelection(result);
    }

    @Override
    public void setDatasetsForCubes(GetDatasetsResult result) {
        publicationStructureElementPanel.setDatasetsForCubes(result);
    }
}
