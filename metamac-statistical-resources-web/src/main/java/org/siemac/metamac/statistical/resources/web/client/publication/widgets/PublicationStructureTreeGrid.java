package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ElementLevelDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.NavigableTreeGrid;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.ElementLevelDS;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.ElementLevelTreeNode;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationStructureTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.resources.StyleUtils;
import org.siemac.metamac.web.common.client.utils.ListGridUtils;
import org.siemac.metamac.web.common.client.widgets.DeleteConfirmationWindow;

import com.google.web.bindery.event.shared.HandlerRegistration;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.DragDataAction;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitEvent;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.FolderClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderClickHandler;
import com.smartgwt.client.widgets.tree.events.FolderContextClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderContextClickHandler;
import com.smartgwt.client.widgets.tree.events.FolderDropEvent;
import com.smartgwt.client.widgets.tree.events.FolderDropHandler;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;
import com.smartgwt.client.widgets.tree.events.LeafContextClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafContextClickHandler;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class PublicationStructureTreeGrid extends NavigableTreeGrid {

    protected static final String               SCHEME_NODE_NAME = "scheme-node";

    protected TreeNodeClickAction               treeNodeClickAction;

    protected Menu                              contextMenu;
    private MenuItem                            createChapterMenuItem;
    private MenuItem                            createCubeMenuItem;
    private MenuItem                            deleteElementMenuItem;

    protected HandlerRegistration               folderContextHandlerRegistration;
    protected HandlerRegistration               leafContextHandlerRegistration;
    protected HandlerRegistration               folderClickHandlerRegistration;
    protected HandlerRegistration               leafClickHandlerRegistration;
    protected HandlerRegistration               folderDropHandlerRegistration;

    protected RelatedResourceDto                publicationVersion;

    protected Tree                              tree;
    protected TreeGridField                     titleField;
    protected TreeGridField                     urnField;
    protected TreeGridField                     orderField;
    protected TreeGridField                     infoField;

    protected HandlerRegistration               filterEditionHandler;

    protected PublicationStructureTabUiHandlers uiHandlers;

    protected ElementLevelDto                   selectedContextClickElement;

    protected int                               numberOfElementsInTheTree;

    public PublicationStructureTreeGrid(TreeNodeClickAction treeNodeClickAction) {
        this.treeNodeClickAction = treeNodeClickAction;

        setHeight(175);
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
        setRollUnderCanvasProperties(StyleUtils.getRollUnderCanvasProperties());
        setCanHover(true);
        setShowHover(true);
        setShowHoverComponents(true);
        setShowHeaderContextMenu(false); // do not show context menu in trees (avoid to show columns that should not be shown)
        setCanDragSelectText(false); // do not allow text selection! (strange behavior when dragging nodes)

        setCanReorderRecords(true);
        setCanAcceptDroppedRecords(true);
        setCanDragRecordsOut(false);
        setDragDataAction(DragDataAction.MOVE);
        setShowOpenIcons(true);
        setShowDropIcons(true);

        titleField = new TreeGridField(ElementLevelDS.TITLE, getConstants().publicationStructureElementTitle());
        titleField.setShowHover(false); // only show hover in info field
        titleField.setCanFilter(true);

        urnField = new TreeGridField(ElementLevelDS.URN, getConstants().publicationStructureElementURN());
        urnField.setShowHover(false); // only show hover in info field
        urnField.setCanFilter(true);

        orderField = new TreeGridField(ElementLevelDS.ORDER_IN_LEVEL, getConstants().publicationStructureElementOrderInLevel());
        orderField.setShowIfCondition(ListGridUtils.getFalseListGridFieldIfFunction());
        orderField.setCanSort(true);
        orderField.setShowHover(false);

        infoField = new TreeGridField(ElementLevelDS.INFO, " ");
        infoField.setType(ListGridFieldType.IMAGE);
        infoField.setWidth(50);
        infoField.setAlign(Alignment.CENTER);
        infoField.setCanFilter(false);
        infoField.setShowHover(true);

        setFields(titleField, urnField, orderField, infoField);

        // Order by ORDER field
        setCanSort(true);
        setSortField(ElementLevelDS.ORDER_IN_LEVEL);

        setShowFilterEditor(true);

        filterEditionHandler = addFilterEditorSubmitHandler(new FilterEditorSubmitHandler() {

            @Override
            public void onFilterEditorSubmit(FilterEditorSubmitEvent event) {
                event.cancel();
                TreeNode[] treeNodes = tree.getAllNodes();

                String titleCriteria = event.getCriteria().getAttribute(ElementLevelDS.TITLE);
                String urnCriteria = event.getCriteria().getAttribute(ElementLevelDS.URN);

                if (StringUtils.isBlank(titleCriteria) && StringUtils.isBlank(urnCriteria)) {
                    setData(tree);
                    return;
                } else {
                    List<TreeNode> matchingNodes = new ArrayList<TreeNode>();
                    for (TreeNode treeNode : treeNodes) {
                        if (!SCHEME_NODE_NAME.equals(treeNode.getName())) {
                            String title = treeNode.getAttributeAsString(ElementLevelDS.TITLE);
                            String urn = treeNode.getAttributeAsString(ElementLevelDS.URN);

                            boolean matches = true;
                            if (titleCriteria != null && !StringUtils.containsIgnoreCase(title, titleCriteria)) {
                                matches = false;
                            }
                            if (urnCriteria != null && !StringUtils.containsIgnoreCase(urn, urnCriteria)) {
                                matches = false;
                            }
                            if (matches) {
                                matchingNodes.add(treeNode);
                            }
                        }
                    }
                    Tree resultTree = new Tree();
                    resultTree.setData(matchingNodes.toArray(new TreeNode[0]));
                    setData(resultTree);
                }
            }
        });

        //
        // CONTEXT MENU
        //

        contextMenu = new Menu();

        createChapterMenuItem = new MenuItem(getConstants().publicationStructureCreateChapter());
        contextMenu.addItem(createChapterMenuItem);

        createCubeMenuItem = new MenuItem(getConstants().publicationStructureCreateCube());
        contextMenu.addItem(createCubeMenuItem);

        deleteElementMenuItem = new MenuItem(getConstants().publicationStructureDeleteElement());
        deleteElementMenuItem.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                DeleteConfirmationWindow deleteConfirmationWindow = new DeleteConfirmationWindow(getMessages().publicationStructureElementDeleteConfirmationTitle(), getMessages()
                        .publicationStructureElementDeleteConfirmation());
                deleteConfirmationWindow.show();
                deleteConfirmationWindow.getYesButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        getUiHandlers().deleteElement(publicationVersion.getUrn(), getElementLevelUrn(selectedContextClickElement));
                    }
                });
            }
        });
        contextMenu.addItem(deleteElementMenuItem);

        //
        // Bind events
        //

        folderClickHandlerRegistration = addFolderClickHandler(new FolderClickHandler() {

            @Override
            public void onFolderClick(FolderClickEvent event) {
                if (event.getFolder() instanceof ElementLevelTreeNode) {
                    onNodeClick(((ElementLevelTreeNode) event.getFolder()).getElementLevelDto());
                }
            }
        });
        leafClickHandlerRegistration = addLeafClickHandler(new LeafClickHandler() {

            @Override
            public void onLeafClick(LeafClickEvent event) {
                if (event.getLeaf() instanceof ElementLevelTreeNode) {
                    onNodeClick(((ElementLevelTreeNode) event.getLeaf()).getElementLevelDto());
                }
            }
        });
        folderContextHandlerRegistration = addFolderContextClickHandler(new FolderContextClickHandler() {

            @Override
            public void onFolderContextClick(final FolderContextClickEvent event) {
                onNodeContextClick(event.getFolder().getName(), (ElementLevelDto) event.getFolder().getAttributeAsObject(ElementLevelDS.DTO));
            }
        });
        leafContextHandlerRegistration = addLeafContextClickHandler(new LeafContextClickHandler() {

            @Override
            public void onLeafContextClick(LeafContextClickEvent event) {
                onNodeContextClick(event.getLeaf().getName(), (ElementLevelDto) event.getLeaf().getAttributeAsObject(ElementLevelDS.DTO));
            }
        });

        folderDropHandlerRegistration = addFolderDropHandler(new FolderDropHandler() {

            @Override
            public void onFolderDrop(FolderDropEvent event) {
                TreeNode dropFolder = event.getFolder();
                TreeNode droppedNode = event.getNodes().length > 0 ? event.getNodes()[0] : null;
                int position = event.getIndex(); // Absolute position
                if (isDroppable(dropFolder)) {
                    TreeNode[] siblings = getData().getChildren(dropFolder);

                    // We find out the position of the node under the dropFolder
                    int relativePosition = position; // Used to update position
                    int pos = -1;
                    for (int i = 0; i < siblings.length; i++) {
                        if (siblings[i] == droppedNode) {
                            pos = i;
                        }
                    }
                    if (pos >= 0 && pos < position) { // If moved node is before final position, the position must be updated
                        relativePosition--;
                    }

                    relativePosition++;

                    String newItemParent = SCHEME_NODE_NAME.equals(dropFolder.getName()) ? SCHEME_NODE_NAME : dropFolder.getAttribute(ElementLevelDS.URN);

                    if (SCHEME_NODE_NAME.equals(newItemParent)) {
                        // The code will be moved to the first level. The parent is null.
                        newItemParent = null;
                    }

                    if (droppedNode instanceof ElementLevelTreeNode) {
                        getUiHandlers().updateElementLocation(publicationVersion.getUrn(), ((ElementLevelTreeNode) droppedNode).getUrn(), newItemParent, Long.valueOf(relativePosition));
                    }
                }
                event.cancel();
            }
        });
    }

    public void removeHandlerRegistrations() {
        folderContextHandlerRegistration.removeHandler();
        leafContextHandlerRegistration.removeHandler();
        folderClickHandlerRegistration.removeHandler();
        leafClickHandlerRegistration.removeHandler();
    }

    public void setPublicationStructure(PublicationStructureDto publicationStructureDto) {
        this.numberOfElementsInTheTree = 1;
        this.publicationVersion = publicationStructureDto.getPublicationVersion();

        // Clear filter editor
        setFilterEditorCriteria(null);

        ElementLevelTreeNode publicationVersionRootNode = createPublicationVersionRootNode(publicationVersion);
        publicationVersionRootNode.setChildren(createElementLevelsTreeNodes(publicationStructureDto.getElements()));

        addTreeNodesToTreeGrid(new ElementLevelTreeNode[]{publicationVersionRootNode});
        setAutoFitMaxRecords(numberOfElementsInTheTree);
    }

    public ElementLevelTreeNode[] createElementLevelsTreeNodes(List<ElementLevelDto> elementLevelDtos) {
        ElementLevelTreeNode[] treeNodes = new ElementLevelTreeNode[elementLevelDtos.size()];
        for (int i = 0; i < elementLevelDtos.size(); i++) {
            numberOfElementsInTheTree++;
            treeNodes[i] = StatisticalResourcesRecordUtils.getElementLevelNode(elementLevelDtos.get(i));
            treeNodes[i].setChildren(createElementLevelsTreeNodes(elementLevelDtos.get(i).getSubelements()));
        }
        return treeNodes;
    }

    public void addTreeNodesToTreeGrid(ElementLevelTreeNode[] treeNodes) {
        tree = new Tree();
        tree.setModelType(TreeModelType.CHILDREN);
        tree.linkNodes(treeNodes);
        setData(tree);
        getData().openAll();
    }

    public void selectElement(NameableStatisticalResourceDto element) {
        String urn = element.getUrn();
        RecordList nodes = getDataAsRecordList();
        Record record = nodes.find(ElementLevelDS.URN, urn);
        if (record != null) {
            selectRecord(record);
        }
    }

    protected void showContextMenu() {
        contextMenu.markForRedraw();
        contextMenu.showContextMenu();
    }

    protected ElementLevelTreeNode createPublicationVersionRootNode(RelatedResourceDto publicationVersion) {
        return StatisticalResourcesRecordUtils.getPublicationVersionRootNode(SCHEME_NODE_NAME, publicationVersion);
    }

    protected void addItemsToContextMenu(MenuItem... menuItems) {
        for (MenuItem item : menuItems) {
            contextMenu.addItem(item);
        }
    }

    protected void removeFilterEditionHandler() {
        filterEditionHandler.removeHandler();
    }

    protected void disableItemSchemeNode() {
        RecordList recordList = getRecordList();
        if (recordList != null && publicationVersion != null) {
            Record record = recordList.find(ElementLevelDS.URN, publicationVersion.getUrn());
            if (record != null) {
                int index = getRecordIndex(record);
                if (index != -1) {
                    getRecord(index).setEnabled(false);
                }
            }
        }
    }

    @Override
    protected Canvas getCellHoverComponent(Record record, Integer rowNum, Integer colNum) {
        if (rowNum != 0) { // do not show details if it is the root node (item scheme node)
            DetailViewer detailViewer = new DetailViewer();
            detailViewer.setFields(getDetailViewerFields());
            detailViewer.setData(new Record[]{record});
            return detailViewer;
        }
        return super.getCellHoverComponent(record, rowNum, colNum);
    }

    protected boolean isDroppable(TreeNode dropFolder) {
        return !("/".equals(getDropFolder().getName()));
    }

    protected void onNodeClick(ElementLevelDto elementLevelDto) {
        this.treeNodeClickAction.onNodeClick(elementLevelDto);
    }

    protected void onNodeContextClick(String nodeName, ElementLevelDto elementLevelDto) {
        this.selectedContextClickElement = elementLevelDto;
        this.createChapterMenuItem.setEnabled(canCreateChapter());
        this.createCubeMenuItem.setEnabled(canCreateCube());
        this.deleteElementMenuItem.setEnabled(canDeleteElement(nodeName));
        showContextMenu();

    }
    protected DetailViewerField[] getDetailViewerFields() {
        DetailViewerField titleField = new DetailViewerField(ElementLevelDS.TITLE, getConstants().publicationStructureElementTitle());
        DetailViewerField descriptionField = new DetailViewerField(ElementLevelDS.DESCRIPTION, getConstants().publicationStructureElementDescription());
        DetailViewerField urnField = new DetailViewerField(ElementLevelDS.URN, getConstants().publicationStructureElementURN());
        return new DetailViewerField[]{titleField, descriptionField, urnField};
    }

    public void addCreateChapterMenuItemClickHandler(ClickHandler clickHandler) {
        createChapterMenuItem.addClickHandler(clickHandler);
    }

    public void addCreateCubeMenuItemClickHandler(ClickHandler clickHandler) {
        createCubeMenuItem.addClickHandler(clickHandler);
    }

    public PublicationStructureTabUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(PublicationStructureTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    private String getElementLevelUrn(ElementLevelDto elementLevelDto) {
        if (elementLevelDto.getChapter() != null) {
            return elementLevelDto.getChapter().getUrn();
        } else {
            return elementLevelDto.getCube().getUrn();
        }
    }

    public ElementLevelDto getSelectedContextClickElement() {
        return selectedContextClickElement;
    }

    private boolean canCreateChapter() {
        // TODO Security (METAMAC-1845)

        // Chapters cannot be created under a cube
        if (selectedContextClickElement != null && selectedContextClickElement.getCube() != null) {
            return false;
        }

        return true;
    }

    private boolean canCreateCube() {
        // TODO Security (METAMAC-1845)

        // Cubes cannot be created under a cube
        if (selectedContextClickElement != null && selectedContextClickElement.getCube() != null) {
            return false;
        }

        return true;
    }

    private boolean canDeleteElement(String nodeName) {
        // TODO Security (METAMAC-1845)
        if (SCHEME_NODE_NAME.equals(nodeName)) {
            return false;
        }
        return true;
    }
}
