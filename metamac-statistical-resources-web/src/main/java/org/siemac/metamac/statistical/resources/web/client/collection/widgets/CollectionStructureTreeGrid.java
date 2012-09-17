package org.siemac.metamac.statistical.resources.web.client.collection.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.CollectionStructureHierarchyDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.CollectionStructureHierarchyTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.collection.model.ds.CollectionStructureDS;
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

public class CollectionStructureTreeGrid extends TreeGrid {

    private DeleteConfirmationWindow deleteConfirmationWindow;

    private Menu                     contextMenu;

    private MenuItem                 createElementMenuItem;
    private MenuItem                 deleteElementMenuItem;

    public CollectionStructureTreeGrid() {

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

        TreeGridField nameField = new TreeGridField(CollectionStructureDS.TEXT, getConstants().collectionStructureElementText());

        setFields(nameField);

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
        if (structureHierarchyDto != null) {
            TreeNode collectionTreeNode = createCollectionTreeNode(structureHierarchyDto);
            
            Tree tree = new Tree();
            tree.setModelType(TreeModelType.CHILDREN);
            tree.setData(new TreeNode[]{collectionTreeNode});
            setData(tree);
            getData().openAll();
        }
    }

    private TreeNode createCollectionTreeNode(CollectionStructureHierarchyDto structureHierarchyDto) {
        TreeNode node = new TreeNode();
        // If node type is URL, show the URL in the tree, not the text
        if (CollectionStructureHierarchyTypeEnum.URL.equals(structureHierarchyDto.getType())) {
            node.setAttribute(CollectionStructureDS.TEXT, structureHierarchyDto.getUrl());
        } else {
            node.setAttribute(CollectionStructureDS.TEXT, InternationalStringUtils.getLocalisedString(structureHierarchyDto.getText()));
        }
        node.setAttribute(CollectionStructureDS.DTO, structureHierarchyDto);

        TreeNode[] children = new TreeNode[structureHierarchyDto.getChildren().size()];
        for (int i = 0; i < structureHierarchyDto.getChildren().size(); i++) {
            children[i] = createCollectionTreeNode(structureHierarchyDto.getChildren().get(i));
        }
        // To show a file icon in TEXT, URL, DATASET and QUERY nodes
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

    public void showContextMenu(CollectionStructureHierarchyTypeEnum type) {
        contextMenu.markForRedraw();
        contextMenu.showContextMenu();
        updateNodeMenuItems(type);
    }

    private void updateNodeMenuItems(CollectionStructureHierarchyTypeEnum type) {
        boolean isFinalNode = CollectionStructureHierarchyTypeEnum.TEXT.equals(type) || CollectionStructureHierarchyTypeEnum.URL.equals(type)
                || CollectionStructureHierarchyTypeEnum.DATASET.equals(type) || CollectionStructureHierarchyTypeEnum.QUERY.equals(type);
        createElementMenuItem.setEnabled(!isFinalNode);
        deleteElementMenuItem.setEnabled(!CollectionStructureHierarchyTypeEnum.TITLE.equals(type));
    }

}
