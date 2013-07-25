package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.ElementLevelDS;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationStructureTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultilanguageRichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class PublicationStructureElementPanel extends VLayout {

    private InternationalMainFormLayout       mainFormLayout;
    private GroupDynamicForm                  form;
    private GroupDynamicForm                  editionForm;

    private RelatedResourceDto                publicationVersion;
    private NameableStatisticalResourceDto    element;

    private PublicationStructureTabUiHandlers uiHandlers;

    public PublicationStructureElementPanel() {

        mainFormLayout = new InternationalMainFormLayout();
        bindMainFormLayoutEvents();
        createViewForm();
        createEditionForm();

        addMember(mainFormLayout);
    }

    private void bindMainFormLayoutEvents() {
        mainFormLayout.getTranslateToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                boolean translationsShowed = mainFormLayout.getTranslateToolStripButton().isSelected();

                form.setTranslationsShowed(translationsShowed);
                editionForm.setTranslationsShowed(translationsShowed);
            }
        });

        mainFormLayout.getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (editionForm.validate(false)) {
                    getUiHandlers().saveElement(publicationVersion.getUrn(), getSelectedElement());
                }
            }
        });
    }

    private void createViewForm() {
        form = new GroupDynamicForm(getConstants().publicationStructureElement());
        form.setNumCols(2);
        form.setColWidths("30%", "70%");

        ViewMultiLanguageTextItem title = new ViewMultiLanguageTextItem(ElementLevelDS.TITLE, getConstants().publicationStructureElementTitle());

        ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(ElementLevelDS.DESCRIPTION, getConstants().publicationStructureElementDescription());

        ViewTextItem urn = new ViewTextItem(ElementLevelDS.URN, getConstants().publicationStructureElementURN());

        CustomLinkItem dataset = new CustomLinkItem(ElementLevelDS.DATASET, getConstants().dataset());
        dataset.setShowIfCondition(getIsNotEmptyFormItemIfFunction());
        dataset.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                String datasetUrn = editionForm.getValueAsString(ElementLevelDS.DATASET);
                getUiHandlers().goToLastVersion(datasetUrn);
            }
        });

        CustomLinkItem query = new CustomLinkItem(ElementLevelDS.QUERY, getConstants().query());
        query.setShowIfCondition(getIsNotEmptyFormItemIfFunction());
        query.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                String queryUrn = editionForm.getValueAsString(ElementLevelDS.QUERY);
                getUiHandlers().goToLastVersion(queryUrn);
            }
        });

        form.setFields(title, description, urn, dataset, query);
        mainFormLayout.addViewCanvas(form);
    }

    private void createEditionForm() {
        editionForm = new GroupDynamicForm(getConstants().publicationStructureElement());
        editionForm.setNumCols(2);
        editionForm.setColWidths("30%", "70%");

        MultiLanguageTextItem title = new MultiLanguageTextItem(ElementLevelDS.TITLE, getConstants().publicationStructureElementTitle());
        title.setRequired(true);

        MultilanguageRichTextEditorItem description = new MultilanguageRichTextEditorItem(ElementLevelDS.DESCRIPTION, getConstants().publicationStructureElementDescription());

        ViewTextItem urn = new ViewTextItem(ElementLevelDS.URN, getConstants().publicationStructureElementURN());

        CustomSelectItem resourceTypeToLink = new CustomSelectItem(ElementLevelDS.RESOURCE_TYPE_TO_LINK, getConstants().publicationStructureElementResourceTypeToLink());
        resourceTypeToLink.setValueMap(CommonUtils.getStatisticalResourceTypeThatCanBeAddIntoACubeHashMap());
        resourceTypeToLink.setRequired(true);
        resourceTypeToLink.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                editionForm.markForRedraw();
            }
        });
        resourceTypeToLink.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                // Only show the the form containts a cube
                return element != null && element instanceof CubeDto;
            }
        });

        CustomLinkItem dataset = new CustomLinkItem(ElementLevelDS.DATASET, getConstants().dataset());
        dataset.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                String datasetUrn = editionForm.getValueAsString(ElementLevelDS.DATASET);
                getUiHandlers().goToLastVersion(datasetUrn);
            }
        });
        dataset.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return StringUtils.equals(StatisticalResourceTypeEnum.DATASET.name(), editionForm.getValueAsString(ElementLevelDS.RESOURCE_TYPE_TO_LINK));
            }
        });

        CustomLinkItem query = new CustomLinkItem(ElementLevelDS.QUERY, getConstants().query());
        query.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                String queryUrn = editionForm.getValueAsString(ElementLevelDS.QUERY);
                getUiHandlers().goToLastVersion(queryUrn);
            }
        });
        query.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return StringUtils.equals(StatisticalResourceTypeEnum.QUERY.name(), editionForm.getValueAsString(ElementLevelDS.RESOURCE_TYPE_TO_LINK));
            }
        });

        editionForm.setFields(title, description, urn, resourceTypeToLink, dataset, query);
        mainFormLayout.addEditionCanvas(editionForm);
    }

    public void setElement(NameableStatisticalResourceDto element) {
        this.element = element;

        mainFormLayout.setTitleLabelContents(InternationalStringUtils.getLocalisedString(element.getTitle()));
        mainFormLayout.setViewMode();

        setElementViewMode(element);
        setElementEditionMode(element);
    }

    private void setElementViewMode(NameableStatisticalResourceDto element) {

        form.setValue(ElementLevelDS.TITLE, RecordUtils.getInternationalStringRecord(element.getTitle()));
        form.setValue(ElementLevelDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(element.getDescription()));
        form.setValue(ElementLevelDS.URN, element.getUrn());

        if (element instanceof CubeDto) {
            CubeDto cubeDto = (CubeDto) element;
            form.setValue(ElementLevelDS.DATASET, cubeDto.getDatasetUrn());
            form.setValue(ElementLevelDS.QUERY, cubeDto.getQueryUrn());
        } else {
            form.setValue(ElementLevelDS.DATASET, StringUtils.EMPTY);
            form.setValue(ElementLevelDS.QUERY, StringUtils.EMPTY);
        }

        form.markForRedraw();
    }

    private void setElementEditionMode(NameableStatisticalResourceDto element) {

        editionForm.setValue(ElementLevelDS.TITLE, RecordUtils.getInternationalStringRecord(element.getTitle()));
        editionForm.setValue(ElementLevelDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(element.getDescription()));
        editionForm.setValue(ElementLevelDS.URN, element.getUrn());

        if (element instanceof CubeDto) {
            CubeDto cubeDto = (CubeDto) element;

            String resourceTypeToLink = StringUtils.EMPTY;
            if (!StringUtils.isBlank(cubeDto.getDatasetUrn())) {
                resourceTypeToLink = StatisticalResourceTypeEnum.DATASET.name();
            } else if (!StringUtils.isBlank(cubeDto.getQueryUrn())) {
                resourceTypeToLink = StatisticalResourceTypeEnum.QUERY.name();
            }
            editionForm.setValue(ElementLevelDS.RESOURCE_TYPE_TO_LINK, resourceTypeToLink);
            ((CustomLinkItem) editionForm.getItem(ElementLevelDS.DATASET)).setValue(cubeDto.getDatasetUrn(), null);
            ((CustomLinkItem) editionForm.getItem(ElementLevelDS.QUERY)).setValue(cubeDto.getQueryUrn(), null);
        } else {
            editionForm.setValue(ElementLevelDS.RESOURCE_TYPE_TO_LINK, StringUtils.EMPTY);
            ((CustomLinkItem) editionForm.getItem(ElementLevelDS.DATASET)).clearValue();
            ((CustomLinkItem) editionForm.getItem(ElementLevelDS.QUERY)).clearValue();
        }

        editionForm.markForRedraw();
    }

    public NameableStatisticalResourceDto getSelectedElement() {

        element.setTitle((InternationalStringDto) editionForm.getValue(ElementLevelDS.TITLE));
        element.setDescription((InternationalStringDto) editionForm.getValue(ElementLevelDS.DESCRIPTION));

        if (element instanceof CubeDto) {
            ((CubeDto) element).setDatasetUrn(editionForm.getValueAsString(ElementLevelDS.DATASET));
            ((CubeDto) element).setQueryUrn(editionForm.getValueAsString(ElementLevelDS.QUERY));
        }

        return element;
    }

    public void setPublicationVersion(RelatedResourceDto publicationVersion) {
        this.publicationVersion = publicationVersion;
    }

    public PublicationStructureTabUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(PublicationStructureTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    private FormItemIfFunction getIsNotEmptyFormItemIfFunction() {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return value != null && !StringUtils.isBlank(value.toString());
            }
        };
    }
}