package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.web.common.client.utils.InternationalStringUtils.getLocalisedString;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.ItemDto;
import org.siemac.metamac.web.common.client.resources.StyleUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomCheckboxItem;

import com.google.web.bindery.event.shared.HandlerRegistration;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitEvent;
import com.smartgwt.client.widgets.grid.events.FilterEditorSubmitHandler;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ItemsTreeGrid extends TreeGrid {

    protected static final String SCHEME_NODE_NAME = "scheme-node";

    protected Tree                tree;
    protected TreeGridField       codeField;
    protected TreeGridField       nameField;
    protected TreeGridField       cascadeField;

    protected HandlerRegistration filterEditionHandler;

    public ItemsTreeGrid(boolean editionMode) {
        setCanFocus(false); // To avoid scrolling when a record is clicked
        setCanDragSelectText(true); // To allow text selection
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
        setShowFilterEditor(true);
        setSelectionType(SelectionStyle.SIMPLE);
        setSelectionAppearance(SelectionAppearance.CHECKBOX);
        createTreeFields(editionMode);
        createFilterEditionHandlers();
    }

    public void setItems(ExternalItemDto itemScheme, List<ItemDto> items) {
        clearFilterEditor();

        TreeNode[] treeNodes = new TreeNode[items.size() + 1];
        treeNodes[0] = createItemSchemeTreeNode(itemScheme);

        for (int i = 0; i < items.size(); i++) {
            treeNodes[i + 1] = createItemTreeNode(items.get(i));
        }

        tree = new Tree();
        tree.setModelType(TreeModelType.PARENT);
        tree.linkNodes(treeNodes);
        setData(tree);
        getData().openAll();
    }

    private void clearFilterEditor() {
        setFilterEditorCriteria(null);
    }

    private void createTreeFields(boolean editionMode) {
        codeField = new TreeGridField(ItemDS.CODE, getConstants().identifiableStatisticalResourceCode());
        codeField.setWidth("30%");
        codeField.setCanFilter(true);

        nameField = new TreeGridField(ItemDS.NAME, getConstants().nameableStatisticalResourceTitle());
        nameField.setCanFilter(true);

        cascadeField = new TreeGridField(ItemDS.CASCADE, getConstants().datasetConstraintInCascade());
        cascadeField.setWidth("20%");
        cascadeField.setCanEdit(editionMode);
        cascadeField.setCanFilter(false);
        cascadeField.setEditorType(new CustomCheckboxItem(ItemDS.CASCADE, getConstants().datasetConstraintInCascade()));
        cascadeField.setAlign(Alignment.CENTER);

        setFields(codeField, nameField, cascadeField);
    }

    private void createFilterEditionHandlers() {
        filterEditionHandler = addFilterEditorSubmitHandler(new FilterEditorSubmitHandler() {

            @Override
            public void onFilterEditorSubmit(FilterEditorSubmitEvent event) {
                event.cancel();
                TreeNode[] treeNodes = tree.getAllNodes();

                String codeCriteria = event.getCriteria().getAttribute(ItemDS.CODE);
                String nameCriteria = event.getCriteria().getAttribute(ItemDS.NAME);

                if (StringUtils.isBlank(codeCriteria) && StringUtils.isBlank(nameCriteria)) {
                    setData(tree);
                    return;
                } else {
                    List<TreeNode> matchingNodes = new ArrayList<TreeNode>();
                    for (TreeNode treeNode : treeNodes) {
                        if (!SCHEME_NODE_NAME.equals(treeNode.getName())) {
                            String code = treeNode.getAttributeAsString(ItemDS.CODE);
                            String name = treeNode.getAttributeAsString(ItemDS.NAME);

                            boolean matches = true;
                            if (codeCriteria != null && !StringUtils.containsIgnoreCase(code, codeCriteria)) {
                                matches = false;
                            }
                            if (nameCriteria != null && !StringUtils.containsIgnoreCase(name, nameCriteria)) {
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
    }

    private static TreeNode createItemSchemeTreeNode(ExternalItemDto itemScheme) {
        TreeNode node = new TreeNode(SCHEME_NODE_NAME);
        node.setID(SCHEME_NODE_NAME);
        node.setAttribute(ItemDS.URN, itemScheme.getUrn());
        node.setAttribute(ItemDS.CODE, itemScheme.getCode());
        node.setAttribute(ItemDS.NAME, getLocalisedString(itemScheme.getTitle()));
        return node;
    }

    private static TreeNode createItemTreeNode(ItemDto itemDto) {
        String parentUrn = !StringUtils.isBlank(itemDto.getItemParentUrn()) ? itemDto.getItemParentUrn() : SCHEME_NODE_NAME;
        TreeNode node = new TreeNode();
        node.setID(itemDto.getUrn());
        node.setParentID(parentUrn);
        node.setAttribute(ItemDS.CODE, itemDto.getCode());
        node.setAttribute(ItemDS.NAME, getLocalisedString(itemDto.getTitle()));
        node.setAttribute(ItemDS.URN, itemDto.getUrn());
        return node;
    }

    private class ItemDS extends DataSource {

        public static final String CODE    = "item-code";
        public static final String NAME    = "item-name";
        public static final String URN     = "item-urn";
        public static final String CASCADE = "item-cascade";
    }
}
