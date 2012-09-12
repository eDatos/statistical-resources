package org.siemac.metamac.statistical.resources.web.client.collection.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getCoreMessages;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.CollectionStructureHierarchyDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.CollectionStructureHierarchyTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.collection.model.ds.CollectionStructureDS;
import org.siemac.metamac.statistical.resources.web.client.collection.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.FormItemUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextAreaItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderClickHandler;
import com.smartgwt.client.widgets.tree.events.FolderContextClickEvent;
import com.smartgwt.client.widgets.tree.events.FolderContextClickHandler;
import com.smartgwt.client.widgets.tree.events.LeafClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafClickHandler;
import com.smartgwt.client.widgets.tree.events.LeafContextClickEvent;
import com.smartgwt.client.widgets.tree.events.LeafContextClickHandler;

public class CollectionStructurePanel extends HLayout {

    private static final int                FORM_ITEM_CUSTOM_WIDTH = 250;

    private CollectionStructureTreeGrid     collectionStructureTreeGrid;

    private InternationalMainFormLayout     mainFormLayout;
    private GroupDynamicForm                form;
    private GroupDynamicForm                editionForm;

    private CollectionStructureHierarchyDto selectedNode;

    public CollectionStructurePanel() {

        // TreeGrid

        collectionStructureTreeGrid = new CollectionStructureTreeGrid();
        collectionStructureTreeGrid.setWidth("40%");

        collectionStructureTreeGrid.getCreateElementMenuItem().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                setElementInForm(new CollectionStructureHierarchyDto());
                mainFormLayout.setEditionMode();
            }
        });

        collectionStructureTreeGrid.addFolderContextClickHandler(new FolderContextClickHandler() {

            @Override
            public void onFolderContextClick(final FolderContextClickEvent event) {
                selectedNode = (CollectionStructureHierarchyDto) event.getFolder().getAttributeAsObject(CollectionStructureDS.DTO);
                collectionStructureTreeGrid.showContextMenu();
            }
        });

        collectionStructureTreeGrid.addLeafContextClickHandler(new LeafContextClickHandler() {

            @Override
            public void onLeafContextClick(LeafContextClickEvent event) {
                selectedNode = (CollectionStructureHierarchyDto) event.getLeaf().getAttributeAsObject(CollectionStructureDS.DTO);
                collectionStructureTreeGrid.showContextMenu();

            }
        });

        collectionStructureTreeGrid.addFolderClickHandler(new FolderClickHandler() {

            @Override
            public void onFolderClick(FolderClickEvent event) {
                CollectionStructureHierarchyDto collectionStructureHierarchyDto = (CollectionStructureHierarchyDto) event.getFolder().getAttributeAsObject(CollectionStructureDS.DTO);
                setElementInForm(collectionStructureHierarchyDto);
            }
        });

        collectionStructureTreeGrid.addLeafClickHandler(new LeafClickHandler() {

            @Override
            public void onLeafClick(LeafClickEvent event) {
                CollectionStructureHierarchyDto collectionStructureHierarchyDto = (CollectionStructureHierarchyDto) event.getLeaf().getAttributeAsObject(CollectionStructureDS.DTO);
                setElementInForm(collectionStructureHierarchyDto);
            }
        });

        // MainFormLayout

        mainFormLayout = new InternationalMainFormLayout();
        mainFormLayout.getTranslateToolStripButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                boolean translationsShowed = mainFormLayout.getTranslateToolStripButton().isSelected();
                form.setTranslationsShowed(translationsShowed);
                editionForm.setTranslationsShowed(translationsShowed);
            }
        });
        mainFormLayout.setVisibility(Visibility.HIDDEN);
        createViewForm();
        createEditionForm();

        addMember(collectionStructureTreeGrid);
        addMember(mainFormLayout);
    }

    // TREE GRID

    public void setCollectionStructure(CollectionStructureHierarchyDto structureHierarchyDto) {
        collectionStructureTreeGrid.setCollectionStructure(structureHierarchyDto);
    }

    private void setElementInForm(CollectionStructureHierarchyDto collectionStructureHierarchyDto) {
        mainFormLayout.show();
        if (selectedNode != null) {
            updateFormElementTypeValueMap(selectedNode.getType());
        }
        setElement(collectionStructureHierarchyDto);
    }

    // FORM

    private void createViewForm() {
        form = new GroupDynamicForm(getConstants().collectionStructureElement());

        ViewTextItem type = new ViewTextItem(CollectionStructureDS.TYPE, getConstants().collectionStructureElementType());
        ViewTextItem typeView = new ViewTextItem(CollectionStructureDS.TYPE_VIEW, getConstants().collectionStructureElementType());
        typeView.setShowIfCondition(FormItemUtils.getFalseFormItemIfFunction());

        ViewMultiLanguageTextItem text = new ViewMultiLanguageTextItem(CollectionStructureDS.TEXT, getConstants().collectionStructureElementText());

        ViewTextItem url = new ViewTextItem(CollectionStructureDS.URL, getConstants().collectionStructureElementURL());
        url.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return CollectionStructureHierarchyTypeEnum.URL.name().equals(form.getValueAsString(CollectionStructureDS.TYPE));
            }
        });

        ViewTextItem urn = new ViewTextItem(CollectionStructureDS.URN, getConstants().collectionStructureElementURN());
        urn.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return CollectionStructureHierarchyTypeEnum.DATASET.name().equals(form.getValueAsString(CollectionStructureDS.TYPE))
                        || CollectionStructureHierarchyTypeEnum.QUERY.name().equals(form.getValueAsString(CollectionStructureDS.TYPE));
            }
        });

        form.setFields(type, typeView, text, url, urn);

        mainFormLayout.addViewCanvas(form);
    }

    private void createEditionForm() {
        editionForm = new GroupDynamicForm(getConstants().collectionStructureElement());

        RequiredSelectItem type = new RequiredSelectItem(CollectionStructureDS.TYPE, getConstants().collectionStructureElementType());
        type.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        MultiLanguageTextAreaItem text = new MultiLanguageTextAreaItem(CollectionStructureDS.TEXT, getConstants().collectionStructureElementText());
        text.setRequired(true);
        text.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        RequiredTextItem url = new RequiredTextItem(CollectionStructureDS.URL, getConstants().collectionStructureElementURL());
        url.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return CollectionStructureHierarchyTypeEnum.URL.name().equals(form.getValueAsString(CollectionStructureDS.TYPE));
            }
        });
        url.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        RequiredTextItem urn = new RequiredTextItem(CollectionStructureDS.URN, getConstants().collectionStructureElementURN());
        urn.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return CollectionStructureHierarchyTypeEnum.DATASET.name().equals(form.getValueAsString(CollectionStructureDS.TYPE))
                        || CollectionStructureHierarchyTypeEnum.QUERY.name().equals(form.getValueAsString(CollectionStructureDS.TYPE));
            }
        });
        urn.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        editionForm.setFields(type, text, url, urn);

        mainFormLayout.addEditionCanvas(editionForm);
    }

    private void setElement(CollectionStructureHierarchyDto collectionStructureHierarchyDto) {
        setElementViewMode(collectionStructureHierarchyDto);
        setElementEditionMode(collectionStructureHierarchyDto);
    }

    private void setElementViewMode(CollectionStructureHierarchyDto collectionStructureHierarchyDto) {
        form.setValue(
                CollectionStructureDS.TYPE,
                collectionStructureHierarchyDto.getType() != null ? getCoreMessages().getString(
                        getCoreMessages().collectionStructureHierarchyTypeEnum() + collectionStructureHierarchyDto.getType().name()) : StringUtils.EMPTY);
        form.setValue(CollectionStructureDS.TYPE_VIEW, collectionStructureHierarchyDto.getType().name());
        form.setValue(CollectionStructureDS.TEXT, RecordUtils.getInternationalStringRecord(collectionStructureHierarchyDto.getText()));
        form.setValue(CollectionStructureDS.URL, collectionStructureHierarchyDto.getUrl());
        form.setValue(CollectionStructureDS.URN, collectionStructureHierarchyDto.getUrn());

        form.markForRedraw();
    }

    private void setElementEditionMode(CollectionStructureHierarchyDto collectionStructureHierarchyDto) {
        editionForm.setValue(
                CollectionStructureDS.TYPE,
                collectionStructureHierarchyDto.getType() != null ? getCoreMessages().getString(
                        getCoreMessages().collectionStructureHierarchyTypeEnum() + collectionStructureHierarchyDto.getType().name()) : StringUtils.EMPTY);
        editionForm.setValue(CollectionStructureDS.TEXT, RecordUtils.getInternationalStringRecord(collectionStructureHierarchyDto.getText()));
        editionForm.setValue(CollectionStructureDS.URL, collectionStructureHierarchyDto.getUrl());
        editionForm.setValue(CollectionStructureDS.URN, collectionStructureHierarchyDto.getUrn());

        editionForm.markForRedraw();
    }

    private void updateFormElementTypeValueMap(CollectionStructureHierarchyTypeEnum type) {
        if (CollectionStructureHierarchyTypeEnum.TITLE.equals(type)) {
            editionForm.getItem(CollectionStructureDS.TYPE).setValueMap(CommonUtils.getStructureHierarchyTitleValidTypesHashMap());
        } else if (CollectionStructureHierarchyTypeEnum.CHAPTER.equals(type)) {
            editionForm.getItem(CollectionStructureDS.TYPE).setValueMap(CommonUtils.getStructureHierarchyChapterValidTypesHashMap());
        } else if (CollectionStructureHierarchyTypeEnum.SUBCHAPTER1.equals(type)) {
            editionForm.getItem(CollectionStructureDS.TYPE).setValueMap(CommonUtils.getStructureHierarchySubChapter1ValidTypesHashMap());
        } else if (CollectionStructureHierarchyTypeEnum.SUBCHAPTER2.equals(type)) {
            editionForm.getItem(CollectionStructureDS.TYPE).setValueMap(CommonUtils.getStructureHierarchySubChapter2ValidTypesHashMap());
        } else {
            editionForm.getItem(CollectionStructureDS.TYPE).setValueMap(CommonUtils.getStructureHierarchyTypeHashMap());
        }
    }

}
