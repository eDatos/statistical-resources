package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.PublicationStructureHierarchyTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationStructureDS;
import org.siemac.metamac.statistical.resources.web.client.publication.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.FormItemUtils;
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

public class PublicationStructurePanel extends HLayout {

    private static final int             FORM_ITEM_CUSTOM_WIDTH = 250;

    private PublicationStructureTreeGrid publicationStructureTreeGrid;

    private InternationalMainFormLayout  mainFormLayout;
    private GroupDynamicForm             form;
    private GroupDynamicForm             editionForm;

    private PublicationStructureDto      selectedNode;

    public PublicationStructurePanel() {

        // TreeGrid

        publicationStructureTreeGrid = new PublicationStructureTreeGrid();
        publicationStructureTreeGrid.setWidth("40%");

        /*
         * publicationStructureTreeGrid.getCreateElementMenuItem().addClickHandler(new ClickHandler() {
         * @Override
         * public void onClick(MenuItemClickEvent event) {
         * setElementInForm(new PublicationStructureHierarchyDto());
         * mainFormLayout.setEditionMode();
         * }
         * });
         * publicationStructureTreeGrid.addFolderContextClickHandler(new FolderContextClickHandler() {
         * @Override
         * public void onFolderContextClick(final FolderContextClickEvent event) {
         * selectedNode = (PublicationStructureHierarchyDto) event.getFolder().getAttributeAsObject(PublicationStructureDS.DTO);
         * publicationStructureTreeGrid.showContextMenu(selectedNode.getType());
         * }
         * });
         * publicationStructureTreeGrid.addLeafContextClickHandler(new LeafContextClickHandler() {
         * @Override
         * public void onLeafContextClick(LeafContextClickEvent event) {
         * selectedNode = (PublicationStructureHierarchyDto) event.getLeaf().getAttributeAsObject(PublicationStructureDS.DTO);
         * publicationStructureTreeGrid.showContextMenu(selectedNode.getType());
         * }
         * });
         * publicationStructureTreeGrid.addFolderClickHandler(new FolderClickHandler() {
         * @Override
         * public void onFolderClick(FolderClickEvent event) {
         * selectedNode = (PublicationStructureHierarchyDto) event.getFolder().getAttributeAsObject(PublicationStructureDS.DTO);
         * PublicationStructureHierarchyDto publicationStructureHierarchyDto = (PublicationStructureHierarchyDto) event.getFolder().getAttributeAsObject(PublicationStructureDS.DTO);
         * setElementInForm(publicationStructureHierarchyDto);
         * }
         * });
         * publicationStructureTreeGrid.addLeafClickHandler(new LeafClickHandler() {
         * @Override
         * public void onLeafClick(LeafClickEvent event) {
         * selectedNode = (PublicationStructureHierarchyDto) event.getLeaf().getAttributeAsObject(PublicationStructureDS.DTO);
         * PublicationStructureHierarchyDto publicationStructureHierarchyDto = (PublicationStructureHierarchyDto) event.getLeaf().getAttributeAsObject(PublicationStructureDS.DTO);
         * setElementInForm(publicationStructureHierarchyDto);
         * }
         * });
         */

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

        addMember(publicationStructureTreeGrid);
        addMember(mainFormLayout);
    }

    // TREE GRID

    /*
     * public void setPublicationStructure(PublicationStructureHierarchyDto structureHierarchyDto) {
     * mainFormLayout.hide();
     * publicationStructureTreeGrid.setPublicationStructure(structureHierarchyDto);
     * }
     * private void setElementInForm(PublicationStructureHierarchyDto publicationStructureHierarchyDto) {
     * mainFormLayout.show();
     * if (selectedNode != null) {
     * updateFormElementTypeValueMap(selectedNode.getType());
     * }
     * setElement(publicationStructureHierarchyDto);
     * }
     */

    // FORM

    private void createViewForm() {
        form = new GroupDynamicForm(getConstants().publicationStructureElement());

        ViewTextItem type = new ViewTextItem(PublicationStructureDS.TYPE, getConstants().publicationStructureElementType());
        ViewTextItem typeView = new ViewTextItem(PublicationStructureDS.TYPE_VIEW, getConstants().publicationStructureElementType());
        typeView.setShowIfCondition(FormItemUtils.getFalseFormItemIfFunction());

        ViewMultiLanguageTextItem text = new ViewMultiLanguageTextItem(PublicationStructureDS.TEXT, getConstants().publicationStructureElementText());

        ViewTextItem url = new ViewTextItem(PublicationStructureDS.URL, getConstants().publicationStructureElementURL());
        url.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return PublicationStructureHierarchyTypeEnum.URL.name().equals(form.getValueAsString(PublicationStructureDS.TYPE));
            }
        });

        ViewTextItem urn = new ViewTextItem(PublicationStructureDS.URN, getConstants().publicationStructureElementURN());
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
        editionForm = new GroupDynamicForm(getConstants().publicationStructureElement());

        RequiredSelectItem type = new RequiredSelectItem(PublicationStructureDS.TYPE, getConstants().publicationStructureElementType());
        type.setWidth(FORM_ITEM_CUSTOM_WIDTH);
        ViewTextItem typeView = new ViewTextItem(PublicationStructureDS.TYPE_VIEW, getConstants().publicationStructureElementType());
        typeView.setShowIfCondition(FormItemUtils.getFalseFormItemIfFunction());
        ViewTextItem typeViewName = new ViewTextItem(PublicationStructureDS.TYPE_VIEW_NAME, getConstants().publicationStructureElementType());

        MultiLanguageTextAreaItem text = new MultiLanguageTextAreaItem(PublicationStructureDS.TEXT, getConstants().publicationStructureElementText(), String.valueOf(FORM_ITEM_CUSTOM_WIDTH));
        text.setRequired(true);
        text.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return !PublicationStructureHierarchyTypeEnum.TEXT.name().equals(form.getValueAsString(PublicationStructureDS.TYPE_VIEW));
            }
        });

        MultilanguageRichTextEditorItem textHtml = new MultilanguageRichTextEditorItem(PublicationStructureDS.TEXT_HTML, getConstants().publicationStructureElementText());
        textHtml.setRequired(true);
        textHtml.setWidth(FORM_ITEM_CUSTOM_WIDTH);
        textHtml.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return PublicationStructureHierarchyTypeEnum.TEXT.name().equals(form.getValueAsString(PublicationStructureDS.TYPE_VIEW));
            }
        });

        RequiredTextItem url = new RequiredTextItem(PublicationStructureDS.URL, getConstants().publicationStructureElementURL());
        url.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return PublicationStructureHierarchyTypeEnum.URL.name().equals(form.getValueAsString(PublicationStructureDS.TYPE));
            }
        });
        url.setWidth(FORM_ITEM_CUSTOM_WIDTH);

        RequiredTextItem urn = new RequiredTextItem(PublicationStructureDS.URN, getConstants().publicationStructureElementURN());
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

    /*
     * private void setElement(PublicationStructureHierarchyDto publicationStructureHierarchyDto) {
     * setElementViewMode(publicationStructureHierarchyDto);
     * setElementEditionMode(publicationStructureHierarchyDto);
     * }
     * private void setElementViewMode(PublicationStructureHierarchyDto publicationStructureHierarchyDto) {
     * form.setValue(
     * PublicationStructureDS.TYPE,
     * publicationStructureHierarchyDto.getType() != null ? getCoreMessages().getString(
     * getCoreMessages().publicationStructureHierarchyTypeEnum() + publicationStructureHierarchyDto.getType().name()) : StringUtils.EMPTY);
     * form.setValue(PublicationStructureDS.TYPE_VIEW, publicationStructureHierarchyDto.getType() != null ? publicationStructureHierarchyDto.getType().name() : StringUtils.EMPTY);
     * form.setValue(PublicationStructureDS.TEXT, RecordUtils.getInternationalStringRecord(publicationStructureHierarchyDto.getText()));
     * form.setValue(PublicationStructureDS.URL, publicationStructureHierarchyDto.getUrl());
     * form.setValue(PublicationStructureDS.URN, publicationStructureHierarchyDto.getUrn());
     * form.markForRedraw();
     * }
     * private void setElementEditionMode(PublicationStructureHierarchyDto publicationStructureHierarchyDto) {
     * editionForm.setValue(PublicationStructureDS.TYPE, publicationStructureHierarchyDto.getType() != null
     * ? CommonUtils.getStructureHierarchyTypeName(publicationStructureHierarchyDto.getType())
     * : StringUtils.EMPTY);
     * editionForm.setValue(PublicationStructureDS.TYPE_VIEW, publicationStructureHierarchyDto.getType() != null ? publicationStructureHierarchyDto.getType().name() : StringUtils.EMPTY);
     * editionForm.setValue(PublicationStructureDS.TYPE_VIEW_NAME,
     * publicationStructureHierarchyDto.getType() != null ? CommonUtils.getStructureHierarchyTypeName(publicationStructureHierarchyDto.getType()) : StringUtils.EMPTY);
     * updateFormTypeItemVisibility(publicationStructureHierarchyDto);
     * editionForm.setValue(PublicationStructureDS.TEXT, RecordUtils.getInternationalStringRecord(publicationStructureHierarchyDto.getText()));
     * editionForm.setValue(PublicationStructureDS.URL, publicationStructureHierarchyDto.getUrl());
     * editionForm.setValue(PublicationStructureDS.URN, publicationStructureHierarchyDto.getUrn());
     * editionForm.markForRedraw();
     * }
     */

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

    /*
     * private void updateFormTypeItemVisibility(PublicationStructureHierarchyDto publicationStructureHierarchyDto) {
     * if (publicationStructureHierarchyDto.getId() == null) {
     * editionForm.getItem(PublicationStructureDS.TYPE).show();
     * editionForm.getItem(PublicationStructureDS.TYPE_VIEW_NAME).hide();
     * } else {
     * editionForm.getItem(PublicationStructureDS.TYPE).hide();
     * editionForm.getItem(PublicationStructureDS.TYPE_VIEW_NAME).show();
     * }
     * }
     */

}
