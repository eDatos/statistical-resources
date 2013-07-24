package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.NameableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.ElementLevelDS;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationStructureTabUiHandlers;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultilanguageRichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
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

        ViewMultiLanguageTextItem title = new ViewMultiLanguageTextItem(ElementLevelDS.TITLE, getConstants().publicationStructureElementTitle());

        ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(ElementLevelDS.DESCRIPTION, getConstants().publicationStructureElementDescription());

        ViewTextItem urn = new ViewTextItem(ElementLevelDS.URN, getConstants().publicationStructureElementURN());
        urn.setColSpan(2);

        form.setFields(title, description, urn);
        mainFormLayout.addViewCanvas(form);
    }

    private void createEditionForm() {
        editionForm = new GroupDynamicForm(getConstants().publicationStructureElement());

        MultiLanguageTextItem title = new MultiLanguageTextItem(ElementLevelDS.TITLE, getConstants().publicationStructureElementTitle());
        title.setRequired(true);

        MultilanguageRichTextEditorItem description = new MultilanguageRichTextEditorItem(ElementLevelDS.DESCRIPTION, getConstants().publicationStructureElementDescription());

        ViewTextItem urn = new ViewTextItem(ElementLevelDS.URN, getConstants().publicationStructureElementURN());
        urn.setColSpan(2);

        editionForm.setFields(title, description, urn);
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

        // TODO

        if (element instanceof ChapterDto) {

        } else if (element instanceof CubeDto) {

        }
    }

    private void setElementEditionMode(NameableStatisticalResourceDto element) {

        editionForm.setValue(ElementLevelDS.TITLE, RecordUtils.getInternationalStringRecord(element.getTitle()));
        editionForm.setValue(ElementLevelDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(element.getDescription()));
        editionForm.setValue(ElementLevelDS.URN, element.getUrn());

        // TODO

        if (element instanceof ChapterDto) {

        } else if (element instanceof CubeDto) {

        }
    }

    public NameableStatisticalResourceDto getSelectedElement() {

        element.setTitle((InternationalStringDto) editionForm.getValue(ElementLevelDS.TITLE));
        element.setDescription((InternationalStringDto) editionForm.getValue(ElementLevelDS.DESCRIPTION));

        // TODO
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
}
