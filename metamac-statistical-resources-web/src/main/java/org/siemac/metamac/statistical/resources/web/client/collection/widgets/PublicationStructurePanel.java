package org.siemac.metamac.statistical.resources.web.client.collection.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getCoreMessages;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureHierarchyDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.PublicationStructureHierarchyTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.collection.model.ds.PublicationStructureDS;
import org.siemac.metamac.statistical.resources.web.client.collection.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.FormItemUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextAreaItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultilanguageRichTextEditorItem;
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

public class PublicationStructurePanel extends HLayout {

    private static final int                 FORM_ITEM_CUSTOM_WIDTH = 250;

    private PublicationStructureTreeGrid     collectionStructureTreeGrid;

    private InternationalMainFormLayout      mainFormLayout;
    private GroupDynamicForm                 form;
    private GroupDynamicForm                 editionForm;

    private PublicationStructureHierarchyDto selectedNode;

    public PublicationStructurePanel() {

        // TreeGrid

        collectionStructureTreeGrid = new PublicationStructureTreeGrid();
        collectionStructureTreeGrid.setWidth("40%");

        collectionStructureTreeGrid.getCreateElementMenuItem().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(MenuItemClickEvent event) {
                setElementInForm(new PublicationStructureHierarchyDto());
                mainFormLayout.setEditionMode();
            }
        });

        collectionStructureTreeGrid.addFolderContextClickHandler(new FolderContextClickHandler() {

            @Override
            public void onFolderContextClick(final FolderContextClickEvent event) {
                selectedNode = (PublicationStructureHierarchyDto) event.getFolder().getAttributeAsObject(PublicationStructureDS.DTO);
                collectionStructureTreeGrid.showContextMenu(selectedNode.getType());
            }
        });

        collectionStructureTreeGrid.addLeafContextClickHandler(new LeafContextClickHandler() {

            @Override
            public void onLeafContextClick(LeafContextClickEvent event) {
                selectedNode = (PublicationStructureHierarchyDto) event.getLeaf().getAttributeAsObject(PublicationStructureDS.DTO);
                collectionStructureTreeGrid.showContextMenu(selectedNode.getType());

            }
        });

        collectionStructureTreeGrid.addFolderClickHandler(new FolderClickHandler() {

            @Override
            public void onFolderClick(FolderClickEvent event) {
                selectedNode = (PublicationStructureHierarchyDto) event.getFolder().getAttributeAsObject(PublicationStructureDS.DTO);
                PublicationStructureHierarchyDto collectionStructureHierarchyDto = (PublicationStructureHierarchyDto) event.getFolder().getAttributeAsObject(PublicationStructureDS.DTO);
                setElementInForm(collectionStructureHierarchyDto);
            }
        });

        collectionStructureTreeGrid.addLeafClickHandler(new LeafClickHandler() {

            @Override
            public void onLeafClick(LeafClickEvent event) {
                selectedNode = (PublicationStructureHierarchyDto) event.getLeaf().getAttributeAsObject(PublicationStructureDS.DTO);
                PublicationStructureHierarchyDto collectionStructureHierarchyDto = (PublicationStructureHierarchyDto) event.getLeaf().getAttributeAsObject(PublicationStructureDS.DTO);
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

    public void setPublicationStructure(PublicationStructureHierarchyDto structureHierarchyDto) {
        mainFormLayout.hide();
        collectionStructureTreeGrid.setCollectionStructure(structureHierarchyDto);
    }

    private void setElementInForm(PublicationStructureHierarchyDto collectionStructureHierarchyDto) {
        mainFormLayout.show();
        if (selectedNode != null) {
            updateFormElementTypeValueMap(selectedNode.getType());
        }
        setElement(collectionStructureHierarchyDto);
    }

    // FORM

    private void createViewForm() {
        form = new GroupDynamicForm(getConstants().collectionStructureElement());

        ViewTextItem type = new ViewTextItem(PublicationStructureDS.TYPE, getConstants().collectionStructureElementType());
        ViewTextItem typeView = new ViewTextItem(PublicationStructureDS.TYPE_VIEW, getConstants().collectionStructureElementType());
        typeView.setShowIfCondition(FormItemUtils.getFalseFormItemIfFunction());

        ViewMultiLanguageTextItem text = new ViewMultiLanguageTextItem(PublicationStructureDS.TEXT, getConstants().collectionStructureElementText());

        ViewTextItem url = new ViewTextItem(PublicationStructureDS.URL, getConstants().collectionStructureElementURL());
        url.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return PublicationStructureHierarchyTypeEnum.URL.name().equals(form.getValueAsString(PublicationStructureDS.TYPE));
            }
        });

        ViewTextItem urn = new ViewTextItem(PublicationStructureDS.URN, getConstants().collectionStructureElementURN());
        urn.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return PublicationStructureHierarchyTypeEnum.DATASET.name().equals(form.getValueAsString(PublicationStructureDS.TYPE_VIEW))
                        || PublicationStructureHierarchyTypeEnum.QUERY.name().equals(form.getValueAsString(PublicationStructureDS.TYPE_VIEW));
            }
        });

        form.setFields(type, typeView, text, url, urn);

        mainFormLayout.addViewCanvas(form);
    }

    private void createEditionForm() {
        editionForm = new GroupDynamicForm(getConstants().collectionStructureElement());

        RequiredSelectItem type = new RequiredSelectItem(PublicationStructureDS.TYPE, getConstants().collectionStructureElementType());
        type.setWidth(FORM_ITEM_CUSTOM_WIDTH);
        ViewTextItem typeView = new ViewTextItem(PublicationStructureDS.TYPE_VIEW, getConstants().collectionStructureElementType());
        typeView.setShowIfCondition(FormItemUtils.getFalseFormItemIfFunction());
        ViewTextItem typeViewName = new ViewTextItem(PublicationStructureDS.TYPE_VIEW_NAME, getConstants().collectionStructureElementType());

        MultiLanguageTextAreaItem text = new MultiLanguageTextAreaItem(PublicationStructureDS.TEXT, getConstants().collectionStructureElementText(), FORM_ITEM_CUSTOM_WIDTH);
        text.setRequired(true);
        text.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return !PublicationStructureHierarchyTypeEnum.TEXT.name().equals(form.getValueAsString(PublicationStructureDS.TYPE_VIEW));
            }
        });

        MultilanguageRichTextEditorItem textHtml = new MultilanguageRichTextEditorItem(PublicationStructureDS.TEXT_HTML, getConstants().collectionStructureElementText());
        textHtml.setRequired(true);
        textHtml.setWidth(FORM_ITEM_CUSTOM_WIDTH);
        textHtml.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return PublicationStructureHierarchyTypeEnum.TEXT.name().equals(form.getValueAsString(PublicationStructureDS.TYPE_VIEW));
            }
        });

        RequiredTextItem url = new RequiredTextItem(PublicationStructureDS.URL, getConstants().collectionStructureElementURL());
        url.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return PublicationStructureHierarchyTypeEnum.URL.name().equals(form.getValueAsString(PublicationStructureDS.TYPE));
            }
        });
        url.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        RequiredTextItem urn = new RequiredTextItem(PublicationStructureDS.URN, getConstants().collectionStructureElementURN());
        urn.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return PublicationStructureHierarchyTypeEnum.DATASET.name().equals(form.getValueAsString(PublicationStructureDS.TYPE_VIEW))
                        || PublicationStructureHierarchyTypeEnum.QUERY.name().equals(form.getValueAsString(PublicationStructureDS.TYPE_VIEW));
            }
        });
        urn.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        editionForm.setFields(type, typeView, typeViewName, text, textHtml, url, urn);

        mainFormLayout.addEditionCanvas(editionForm);
    }

    private void setElement(PublicationStructureHierarchyDto collectionStructureHierarchyDto) {
        setElementViewMode(collectionStructureHierarchyDto);
        setElementEditionMode(collectionStructureHierarchyDto);
    }

    private void setElementViewMode(PublicationStructureHierarchyDto collectionStructureHierarchyDto) {
        form.setValue(
                PublicationStructureDS.TYPE,
                collectionStructureHierarchyDto.getType() != null ? getCoreMessages().getString(
                        getCoreMessages().collectionStructureHierarchyTypeEnum() + collectionStructureHierarchyDto.getType().name()) : StringUtils.EMPTY);
        form.setValue(PublicationStructureDS.TYPE_VIEW, collectionStructureHierarchyDto.getType() != null ? collectionStructureHierarchyDto.getType().name() : StringUtils.EMPTY);
        form.setValue(PublicationStructureDS.TEXT, RecordUtils.getInternationalStringRecord(collectionStructureHierarchyDto.getText()));
        form.setValue(PublicationStructureDS.URL, collectionStructureHierarchyDto.getUrl());
        form.setValue(PublicationStructureDS.URN, collectionStructureHierarchyDto.getUrn());

        form.markForRedraw();
    }

    private void setElementEditionMode(PublicationStructureHierarchyDto collectionStructureHierarchyDto) {
        editionForm.setValue(PublicationStructureDS.TYPE, collectionStructureHierarchyDto.getType() != null
                ? CommonUtils.getStructureHierarchyTypeName(collectionStructureHierarchyDto.getType())
                : StringUtils.EMPTY);
        editionForm.setValue(PublicationStructureDS.TYPE_VIEW, collectionStructureHierarchyDto.getType() != null ? collectionStructureHierarchyDto.getType().name() : StringUtils.EMPTY);
        editionForm.setValue(PublicationStructureDS.TYPE_VIEW_NAME,
                collectionStructureHierarchyDto.getType() != null ? CommonUtils.getStructureHierarchyTypeName(collectionStructureHierarchyDto.getType()) : StringUtils.EMPTY);
        updateFormTypeItemVisibility(collectionStructureHierarchyDto);
        editionForm.setValue(PublicationStructureDS.TEXT, RecordUtils.getInternationalStringRecord(collectionStructureHierarchyDto.getText()));
        editionForm.setValue(PublicationStructureDS.URL, collectionStructureHierarchyDto.getUrl());
        editionForm.setValue(PublicationStructureDS.URN, collectionStructureHierarchyDto.getUrn());

        editionForm.markForRedraw();
    }

    private void updateFormElementTypeValueMap(PublicationStructureHierarchyTypeEnum type) {
        if (PublicationStructureHierarchyTypeEnum.TITLE.equals(type)) {
            editionForm.getItem(PublicationStructureDS.TYPE).setValueMap(CommonUtils.getStructureHierarchyTitleValidTypesHashMap());
        } else if (PublicationStructureHierarchyTypeEnum.CHAPTER.equals(type)) {
            editionForm.getItem(PublicationStructureDS.TYPE).setValueMap(CommonUtils.getStructureHierarchyChapterValidTypesHashMap());
        } else if (PublicationStructureHierarchyTypeEnum.SUBCHAPTER1.equals(type)) {
            editionForm.getItem(PublicationStructureDS.TYPE).setValueMap(CommonUtils.getStructureHierarchySubChapter1ValidTypesHashMap());
        } else if (PublicationStructureHierarchyTypeEnum.SUBCHAPTER2.equals(type)) {
            editionForm.getItem(PublicationStructureDS.TYPE).setValueMap(CommonUtils.getStructureHierarchySubChapter2ValidTypesHashMap());
        } else {
            editionForm.getItem(PublicationStructureDS.TYPE).setValueMap(CommonUtils.getStructureHierarchyTypeHashMap());
        }
    }

    private void updateFormTypeItemVisibility(PublicationStructureHierarchyDto collectionStructureHierarchyDto) {
        if (collectionStructureHierarchyDto.getId() == null) {
            editionForm.getItem(PublicationStructureDS.TYPE).show();
            editionForm.getItem(PublicationStructureDS.TYPE_VIEW_NAME).hide();
        } else {
            editionForm.getItem(PublicationStructureDS.TYPE).hide();
            editionForm.getItem(PublicationStructureDS.TYPE_VIEW_NAME).show();
        }
    }

}
