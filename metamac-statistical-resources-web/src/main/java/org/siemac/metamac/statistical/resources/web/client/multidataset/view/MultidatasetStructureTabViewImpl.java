package org.siemac.metamac.statistical.resources.web.client.multidataset.view;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetCubeDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.multidataset.presenter.MultidatasetStructureTabPresenter.MultidatasetStructureTabView;
import org.siemac.metamac.statistical.resources.web.client.multidataset.view.handlers.MultidatasetStructureTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.multidataset.widgets.MultidatasetStructureCubePanel;
import org.siemac.metamac.statistical.resources.web.client.multidataset.widgets.MultidatasetStructureNodeClickAction;
import org.siemac.metamac.statistical.resources.web.client.multidataset.widgets.MultidatasetStructureTreeGrid;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueriesResult;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class MultidatasetStructureTabViewImpl extends ViewWithUiHandlers<MultidatasetStructureTabUiHandlers> implements MultidatasetStructureTabView {

    public static Long                     FIST_TREE_ELEMENT_POSITION = 1L;

    private VLayout                        panel;
    private MultidatasetStructureTreeGrid  multidatasetStructureTreeGrid;
    private MultidatasetStructureCubePanel multidatasetStructureCubePanel;

    @Inject
    public MultidatasetStructureTabViewImpl() {
        panel = new VLayout();
        panel.setMargin(15);

        createMultidatasetStructureTree();
        createMultidatasetStructureElementPanel();

        HLayout subPanel = new HLayout(10);

        // Tree

        VLayout treePanel = new VLayout();
        treePanel.addMember(multidatasetStructureTreeGrid);
        subPanel.addMember(treePanel);

        // Element

        subPanel.addMember(multidatasetStructureCubePanel);

        panel.addMember(subPanel);
    }

    private void createMultidatasetStructureElementPanel() {
        multidatasetStructureCubePanel = new MultidatasetStructureCubePanel();
        multidatasetStructureCubePanel.setVisible(false);
    }

    private void createMultidatasetStructureTree() {
        multidatasetStructureTreeGrid = new MultidatasetStructureTreeGrid(new MultidatasetStructureNodeClickAction() {

            @Override
            public void onNodeClick(MultidatasetCubeDto multidatasetCubeDto) {
                if (multidatasetCubeDto != null) {
                    setCubeInPanel(multidatasetCubeDto);
                }
            }
        });

        multidatasetStructureTreeGrid.addCreateCubeMenuItemClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                MultidatasetCubeDto selectedContextClickElement = multidatasetStructureTreeGrid.getSelectedContextClickElement();

                MultidatasetCubeDto cubeDto = new MultidatasetCubeDto();
                if (selectedContextClickElement != null) {
                    cubeDto.setOrderInMultidataset(selectedContextClickElement.getOrderInMultidataset() + 1);
                } else {
                    cubeDto.setOrderInMultidataset(FIST_TREE_ELEMENT_POSITION);
                }

                setNewCubeInPanel(cubeDto);
            }
        });
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(MultidatasetStructureTabUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        multidatasetStructureTreeGrid.setUiHandlers(uiHandlers);
        multidatasetStructureCubePanel.setUiHandlers(uiHandlers);
    }

    @Override
    public void setMultidatasetVersion(MultidatasetVersionDto multidatasetVersionDto) {
        multidatasetStructureTreeGrid.setMultidatasetVersion(multidatasetVersionDto);
        multidatasetStructureCubePanel.setMultidatasetVersion(multidatasetVersionDto);
        multidatasetStructureCubePanel.hide();
    }

    @Override
    public void setMultidatasetVersion(MultidatasetVersionDto multidatasetStructureDto, MultidatasetCubeDto selectedCube) {
        setMultidatasetVersion(multidatasetStructureDto);
        selectCube(selectedCube);
    };

    private void selectCube(MultidatasetCubeDto selectedCube) {
        multidatasetStructureTreeGrid.selectCube(selectedCube);
        setCubeInPanel(selectedCube);
    }

    private void setCubeInPanel(MultidatasetCubeDto cube) {
        multidatasetStructureCubePanel.setCube(cube);
        multidatasetStructureCubePanel.show();
    }

    private void setNewCubeInPanel(MultidatasetCubeDto cube) {
        setCubeInPanel(cube);
        multidatasetStructureCubePanel.setMainFormLayoutEditionMode();
    }

    //
    // RELATED RESOURCES
    //

    @Override
    public void setStatisticalOperationsForDatasetSelection(GetStatisticalOperationsPaginatedListResult result) {
        multidatasetStructureCubePanel.setStatisticalOperationsForDatasetSelection(result);
    }

    @Override
    public void setDatasetsForCubes(GetDatasetsResult result) {
        multidatasetStructureCubePanel.setDatasetsForCubes(result);
    }

    @Override
    public void setStatisticalOperationsForQuerySelection(GetStatisticalOperationsPaginatedListResult result) {
        multidatasetStructureCubePanel.setStatisticalOperationsForQuerySelection(result);
    }

    @Override
    public void setQueriesForCubes(GetQueriesResult result) {
        multidatasetStructureCubePanel.setQueriesForCubes(result);
    }

}
