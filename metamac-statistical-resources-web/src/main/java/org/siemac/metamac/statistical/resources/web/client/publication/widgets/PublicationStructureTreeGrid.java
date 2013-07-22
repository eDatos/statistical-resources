package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationStructureDS;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;

import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;

public class PublicationStructureTreeGrid extends TreeGrid {

    private DeleteConfirmationWindow deleteConfirmationWindow;

    private Menu                     contextMenu;

    private MenuItem                 createElementMenuItem;
    private MenuItem                 deleteElementMenuItem;

    public PublicationStructureTreeGrid() {

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

        TreeGridField nameField = new TreeGridField(PublicationStructureDS.TEXT, getConstants().publicationStructureElementText());

        setFields(nameField);

        // Menu

        createElementMenuItem = new MenuItem(getConstants().publicationStructureCreateElement());

        deleteElementMenuItem = new MenuItem(getConstants().publicationStructureDeleteElement());
        deleteConfirmationWindow = new DeleteConfirmationWindow(getConstants().publicationStructureDeleteElementTitle(), getConstants().publicationStructureDeleteElementConfirmation());
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

    public void setPublicationStructure(PublicationStructureDto structureHierarchyDto) {
        /*
         * if (structureHierarchyDto != null) {
         * TreeNode collectionTreeNode = createCollectionTreeNode(structureHierarchyDto);
         * Tree tree = new Tree();
         * tree.setModelType(TreeModelType.CHILDREN);
         * tree.setData(new TreeNode[]{collectionTreeNode});
         * setData(tree);
         * getData().openAll();
         * }
         */
    }

    /*
     * private TreeNode createCollectionTreeNode(PublicationStructureHierarchyDto structureHierarchyDto) {
     * TreeNode node = new TreeNode();
     * // If node type is URL, show the URL in the tree, not the text
     * if (PublicationStructureHierarchyTypeEnum.URL.equals(structureHierarchyDto.getType())) {
     * node.setAttribute(PublicationStructureDS.TEXT, structureHierarchyDto.getUrl());
     * } else {
     * node.setAttribute(PublicationStructureDS.TEXT, InternationalStringUtils.getLocalisedString(structureHierarchyDto.getText()));
     * }
     * node.setAttribute(PublicationStructureDS.DTO, structureHierarchyDto);
     * TreeNode[] children = new TreeNode[structureHierarchyDto.getChildren().size()];
     * for (int i = 0; i < structureHierarchyDto.getChildren().size(); i++) {
     * children[i] = createCollectionTreeNode(structureHierarchyDto.getChildren().get(i));
     * }
     * // To show a file icon in TEXT, URL, DATASET and QUERY nodes
     * if (!PublicationStructureHierarchyTypeEnum.TEXT.equals(structureHierarchyDto.getType()) && !PublicationStructureHierarchyTypeEnum.URL.equals(structureHierarchyDto.getType())
     * && !PublicationStructureHierarchyTypeEnum.DATASET.equals(structureHierarchyDto.getType()) && !PublicationStructureHierarchyTypeEnum.QUERY.equals(structureHierarchyDto.getType())) {
     * node.setChildren(children);
     * }
     * return node;
     * }
     */

    public MenuItem getCreateElementMenuItem() {
        return createElementMenuItem;
    }

    public MenuItem getDeleteElementMenuItem() {
        return deleteElementMenuItem;
    }

    // public void showContextMenu(PublicationStructureHierarchyTypeEnum type) {
    // contextMenu.markForRedraw();
    // contextMenu.showContextMenu();
    // updateNodeMenuItems(type);
    // }
    //
    // private void updateNodeMenuItems(PublicationStructureHierarchyTypeEnum type) {
    // boolean isFinalNode = PublicationStructureHierarchyTypeEnum.TEXT.equals(type) || PublicationStructureHierarchyTypeEnum.URL.equals(type)
    // || PublicationStructureHierarchyTypeEnum.DATASET.equals(type) || PublicationStructureHierarchyTypeEnum.QUERY.equals(type);
    // createElementMenuItem.setEnabled(!isFinalNode);
    // deleteElementMenuItem.setEnabled(!PublicationStructureHierarchyTypeEnum.TITLE.equals(type));
    // }
}
