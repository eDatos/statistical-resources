package org.siemac.metamac.statistical.resources.web.client.collection.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.CollectionStructureHierarchyDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.CollectionStructureHierarchyTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.collection.model.ds.CollectionDS;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;

import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class CollectionTreeGrid extends TreeGrid {

    // private static final String SCHEME_NODE_NAME = "scheme-node";

    private DeleteConfirmationWindow deleteConfirmationWindow;

    private Menu                     contextMenu;

    private MenuItem                 createElementMenuItem;
    private MenuItem                 deleteElementMenuItem;

    public CollectionTreeGrid() {

        setAutoFitMaxRecords(10);
        setAutoFitData(Autofit.VERTICAL);

        setShowOpenIcons(false);
        setShowDropIcons(false);
        setShowSelectedStyle(true);
        setShowPartialSelection(true);
        setCascadeSelection(false);
        setCanSort(false);
        setShowConnectors(true);
        setShowHeader(true);
        setLoadDataOnDemand(false);
        setSelectionType(SelectionStyle.SINGLE);
        setShowCellContextMenus(true);
        setLeaveScrollbarGap(Boolean.FALSE);

        setShowHeader(false);

        TreeGridField codeField = new TreeGridField(CollectionDS.TITLE, "identifier");

        setFields(codeField);

        // Menu

        createElementMenuItem = new MenuItem(getConstants().collectionStructureCreateElement());

        deleteElementMenuItem = new MenuItem(getConstants().collectionStructureDeleteElement());
        deleteConfirmationWindow = new DeleteConfirmationWindow(getConstants().collectionStructureDeleteElementTitle(), getConstants().collectionStructureDeleteElementConfirmation());
        deleteConfirmationWindow.getYesButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // TODO delete element
            }
        });
        deleteElementMenuItem.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                deleteConfirmationWindow.show();
            }
        });

        contextMenu = new Menu();
        contextMenu.addItem(createElementMenuItem);
        contextMenu.addItem(deleteElementMenuItem);
    }

    public void setCollectionStructure(CollectionStructureHierarchyDto structureHierarchyDto) {
        TreeNode collectionTreeNode = createCollectionTreeNode(structureHierarchyDto);

        Tree tree = new Tree();
        tree.setModelType(TreeModelType.CHILDREN);
        tree.setData(new TreeNode[]{collectionTreeNode});
        setData(tree);
        getData().openAll();
    }

    private TreeNode createCollectionTreeNode(CollectionStructureHierarchyDto structureHierarchyDto) {
        TreeNode node = new TreeNode();
        node.setAttribute(CollectionDS.TITLE, InternationalStringUtils.getLocalisedString(structureHierarchyDto.getLabel()));
        node.setAttribute(CollectionDS.TYPE, structureHierarchyDto.getType().name());

        TreeNode[] children = new TreeNode[structureHierarchyDto.getChildren().size()];
        for (int i = 0; i < structureHierarchyDto.getChildren().size(); i++) {
            children[i] = createCollectionTreeNode(structureHierarchyDto.getChildren().get(i));
        }
        // To show a file icon in text, URL, dataSet and query nodes
        if (!CollectionStructureHierarchyTypeEnum.TEXT.equals(structureHierarchyDto.getType()) && !CollectionStructureHierarchyTypeEnum.URL.equals(structureHierarchyDto.getType())
                && !CollectionStructureHierarchyTypeEnum.DATASET.equals(structureHierarchyDto.getType()) && !CollectionStructureHierarchyTypeEnum.QUERY.equals(structureHierarchyDto.getType())) {
            node.setChildren(children);
        }

        return node;
    }

    public MenuItem getCreateElementMenuItem() {
        return createElementMenuItem;
    }

    public MenuItem getDeleteElementMenuItem() {
        return deleteElementMenuItem;
    }

    public void showContextMenu() {
        contextMenu.markForRedraw();
        contextMenu.showContextMenu();
    }

}
