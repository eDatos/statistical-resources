package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.publication.ElementLevelDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.NavigableTreeGrid;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.ElementLevelDS;
import org.siemac.metamac.statistical.resources.web.client.publication.model.record.ElementLevelTreeNode;
import org.siemac.metamac.statistical.resources.web.client.utils.StatisticalResourcesRecordUtils;
import org.siemac.metamac.web.common.client.resources.StyleUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.utils.ListGridUtils;

import com.google.web.bindery.event.shared.HandlerRegistration;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitEvent;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;
import com.smartgwt.client.widgets.tree.events.FolderClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderClickHandler;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;
import com.smartgwt.client.widgets.viewer.DetailViewer;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class PublicationStructureTreeGrid extends NavigableTreeGrid {

    protected static final String   SCHEME_NODE_NAME = "scheme-node";

    protected Menu                  contextMenu;

    protected HandlerRegistration   folderContextHandlerRegistration;
    protected HandlerRegistration   leafContextHandlerRegistration;
    protected HandlerRegistration   folderClickHandlerRegistration;
    protected HandlerRegistration   leafClickHandlerRegistration;

    protected PublicationVersionDto publicationVersionDto;

    protected Tree                  tree;
    protected TreeGridField         titleField;
    protected TreeGridField         orderField;
    protected TreeGridField         infoField;

    protected HandlerRegistration   filterEditionHandler;

    public PublicationStructureTreeGrid() {
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

        titleField = new TreeGridField(ElementLevelDS.TITLE, getConstants().publicationStructureElementTitle());
        titleField.setShowHover(false); // only show hover in info field
        titleField.setCanFilter(true);

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

        setFields(titleField, orderField, infoField);

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

                if (StringUtils.isBlank(titleCriteria)) {
                    setData(tree);
                    return;
                } else {
                    List<TreeNode> matchingNodes = new ArrayList<TreeNode>();
                    for (TreeNode treeNode : treeNodes) {
                        if (!SCHEME_NODE_NAME.equals(treeNode.getName())) {
                            String name = treeNode.getAttributeAsString(ElementLevelDS.TITLE);

                            boolean matches = true;
                            if (titleCriteria != null && !StringUtils.containsIgnoreCase(name, titleCriteria)) {
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

        // Context menu

        contextMenu = new Menu();

        // Bind events

        folderClickHandlerRegistration = addFolderClickHandler(new FolderClickHandler() {

            @Override
            public void onFolderClick(FolderClickEvent event) {
                onNodeClick(event.getFolder().getName(), event.getFolder().getAttribute(ElementLevelDS.URN));
            }
        });
        leafClickHandlerRegistration = addLeafClickHandler(new LeafClickHandler() {

            @Override
            public void onLeafClick(LeafClickEvent event) {
                onNodeClick(event.getLeaf().getName(), event.getLeaf().getAttribute(ElementLevelDS.URN));
            }
        });
    }

    public void removeHandlerRegistrations() {
        folderContextHandlerRegistration.removeHandler();
        leafContextHandlerRegistration.removeHandler();
        folderClickHandlerRegistration.removeHandler();
        leafClickHandlerRegistration.removeHandler();
    }

    public void updatePublicationVersionRootNode(PublicationVersionDto publicationVersionDto) {
        this.publicationVersionDto = publicationVersionDto;
        // Update item scheme node
        TreeNode node = getTree().find(SCHEME_NODE_NAME);
        if (node != null) {
            node.setAttribute(ElementLevelDS.TITLE, InternationalStringUtils.getLocalisedString(publicationVersionDto.getTitle()));
            markForRedraw();
        }
    }

    public void setElements(PublicationVersionDto publicationVersionDto, List<ElementLevelDto> elementLevelDtos) {
        this.publicationVersionDto = publicationVersionDto;

        // Clear filter editor
        setFilterEditorCriteria(null);

        ElementLevelTreeNode publicationVersionRootNode = createPublicationVersionRootNode(publicationVersionDto);
        publicationVersionRootNode.setChildren(createElementLevelsTreeNodes(elementLevelDtos));

        addTreeNodesToTreeGrid(new ElementLevelTreeNode[]{publicationVersionRootNode});
    }

    public ElementLevelTreeNode[] createElementLevelsTreeNodes(List<ElementLevelDto> elementLevelDtos) {
        ElementLevelTreeNode[] treeNodes = new ElementLevelTreeNode[elementLevelDtos.size()];
        for (int i = 0; i < elementLevelDtos.size(); i++) {
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

    public void selectItem(String urn) {
        RecordList nodes = getDataAsRecordList();
        Record record = nodes.find(ElementLevelDS.URN, urn);
        selectRecord(record);
    }

    protected void showContextMenu() {
        contextMenu.markForRedraw();
        contextMenu.showContextMenu();
    }

    protected ElementLevelTreeNode createPublicationVersionRootNode(PublicationVersionDto publicationVersionDto) {
        return StatisticalResourcesRecordUtils.getPublicationVersionRootNode(SCHEME_NODE_NAME, publicationVersionDto);
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
        if (recordList != null && publicationVersionDto != null) {
            Record record = recordList.find(ElementLevelDS.URN, publicationVersionDto.getUrn());
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

    protected void onNodeClick(String nodeName, String elementUrn) {

    }

    protected DetailViewerField[] getDetailViewerFields() {
        DetailViewerField titleField = new DetailViewerField(ElementLevelDS.TITLE, getConstants().publicationStructureElementTitle());
        DetailViewerField descriptionField = new DetailViewerField(ElementLevelDS.DESCRIPTION, getConstants().publicationStructureElementDescription());
        // TODO
        return new DetailViewerField[]{titleField, descriptionField};
    }
}
