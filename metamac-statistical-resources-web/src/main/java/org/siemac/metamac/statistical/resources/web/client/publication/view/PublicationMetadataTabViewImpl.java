package org.siemac.metamac.statistical.resources.web.client.publication.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.StatisticalResourceMetadataBaseViewImpl;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationMetadataTabPresenter.PublicationMetadataTabView;
import org.siemac.metamac.statistical.resources.web.client.publication.utils.PublicationClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.PublicationMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.forms.PublicationClassDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.forms.PublicationClassDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.forms.PublicationResourceRelationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.VersionWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceLifeCycleForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceVersionEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceVersionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceCommonMetadataEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceCommonMetadataForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceContentDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceContentDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceIntellectualPropertyDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceIntellectualPropertyDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceLanguageEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceLanguageForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceProductionDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceProductionDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcePublicationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcePublicationDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceResourceRelationDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceThematicContentClassifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceThematicContentClassifiersForm;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.utils.RelatedResourceUtils;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class PublicationMetadataTabViewImpl extends StatisticalResourceMetadataBaseViewImpl<PublicationMetadataTabUiHandlers> implements PublicationMetadataTabView {

    private VLayout                                                       panel;

    private PublicationMainFormLayout                                     mainFormLayout;

    private NameableResourceIdentifiersForm                               identifiersForm;
    private StatisticalResourceContentDescriptorsForm                     contentDescriptorsForm;
    private StatisticalResourceCommonMetadataForm                         commonMetadataForm;
    private StatisticalResourceThematicContentClassifiersForm             thematicContentClassifiersForm;
    private StatisticalResourceLanguageForm                               languageForm;
    private StatisticalResourceProductionDescriptorsForm                  productionDescriptorsForm;
    private PublicationClassDescriptorsForm                               classDescriptorsForm;
    private StatisticalResourceResourceRelationDescriptorsForm            resourceRelationDescriptorsForm;
    private StatisticalResourcePublicationDescriptorsForm                 publicationDescriptorsForm;
    private LifeCycleResourceLifeCycleForm                                lifeCycleForm;
    private LifeCycleResourceVersionForm                                  versionForm;
    private StatisticalResourceIntellectualPropertyDescriptorsForm        intellectualPropertyDescriptorsForm;

    private NameableResourceIdentifiersEditionForm                        identifiersEditionForm;
    private StatisticalResourceContentDescriptorsEditionForm              contentDescriptorsEditionForm;
    private StatisticalResourceCommonMetadataEditionForm                  commonMetadataEditionForm;
    private StatisticalResourceThematicContentClassifiersEditionForm      thematicContentClassifiersEditionForm;
    private StatisticalResourceLanguageEditionForm                        languageEditionForm;
    private StatisticalResourceProductionDescriptorsEditionForm           productionDescriptorsEditionForm;
    private PublicationClassDescriptorsEditionForm                        classDescriptorsEditionForm;
    private PublicationResourceRelationDescriptorsEditionForm             resourceRelationDescriptorsEditionForm;
    private StatisticalResourcePublicationDescriptorsEditionForm          publicationDescriptorsEditionForm;
    private LifeCycleResourceLifeCycleForm                                lifeCycleEditionForm;
    private LifeCycleResourceVersionEditionForm                           versionEditionForm;
    private StatisticalResourceIntellectualPropertyDescriptorsEditionForm intellectualPropertyDescriptorsEditionForm;

    private PublicationVersionDto                                         publicationDto;

    @Inject
    public PublicationMetadataTabViewImpl() {
        panel = new VLayout();
        // panel.setAutoHeight();
        // panel.setOverflow(Overflow.SCROLL);

        mainFormLayout = new PublicationMainFormLayout(PublicationClientSecurityUtils.canUpdatePublication());
        bindMainFormLayoutEvents();
        createViewForm();
        createEditionForm();

        VLayout subPanel = new VLayout();
        subPanel.addMember(mainFormLayout);

        panel.addMember(subPanel);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(PublicationMetadataTabUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        resourceRelationDescriptorsEditionForm.setUiHandlers(uiHandlers);
        resourceRelationDescriptorsForm.setUiHandlers(uiHandlers);
        commonMetadataForm.setBaseUiHandlers(uiHandlers);
        commonMetadataEditionForm.setUiHandlers(uiHandlers);
        thematicContentClassifiersEditionForm.setUiHandlers(uiHandlers);
        productionDescriptorsEditionForm.setUiHandlers(uiHandlers);
        publicationDescriptorsEditionForm.setUiHandlers(uiHandlers);
        languageEditionForm.setUiHandlers(uiHandlers);
    }

    private void bindMainFormLayoutEvents() {
        mainFormLayout.getTranslateToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                boolean translationsShowed = mainFormLayout.getTranslateToolStripButton().isSelected();
                identifiersForm.setTranslationsShowed(translationsShowed);
                identifiersEditionForm.setTranslationsShowed(translationsShowed);

                contentDescriptorsForm.setTranslationsShowed(translationsShowed);
                contentDescriptorsEditionForm.setTranslationsShowed(translationsShowed);

                commonMetadataForm.setTranslationsShowed(translationsShowed);
                commonMetadataEditionForm.setTranslationsShowed(translationsShowed);

                thematicContentClassifiersForm.setTranslationsShowed(translationsShowed);
                thematicContentClassifiersEditionForm.setTranslationsShowed(translationsShowed);

                languageForm.setTranslationsShowed(translationsShowed);
                languageEditionForm.setTranslationsShowed(translationsShowed);

                productionDescriptorsForm.setTranslationsShowed(translationsShowed);
                productionDescriptorsEditionForm.setTranslationsShowed(translationsShowed);

                classDescriptorsForm.setTranslationsShowed(translationsShowed);
                classDescriptorsEditionForm.setTranslationsShowed(translationsShowed);

                resourceRelationDescriptorsForm.setTranslationsShowed(translationsShowed);
                resourceRelationDescriptorsEditionForm.setTranslationsShowed(translationsShowed);

                publicationDescriptorsForm.setTranslationsShowed(translationsShowed);
                publicationDescriptorsEditionForm.setTranslationsShowed(translationsShowed);

                lifeCycleForm.setTranslationsShowed(translationsShowed);
                lifeCycleEditionForm.setTranslationsShowed(translationsShowed);

                versionForm.setTranslationsShowed(translationsShowed);
                versionEditionForm.setTranslationsShowed(translationsShowed);

                intellectualPropertyDescriptorsForm.setTranslationsShowed(translationsShowed);
                intellectualPropertyDescriptorsEditionForm.setTranslationsShowed(translationsShowed);
            }
        });

        // Save

        mainFormLayout.getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (identifiersEditionForm.validate(false) && contentDescriptorsEditionForm.validate(false) && commonMetadataEditionForm.validate(false)
                        && productionDescriptorsEditionForm.validate(false) && classDescriptorsEditionForm.validate(false) && versionEditionForm.validate(false)
                        && resourceRelationDescriptorsEditionForm.validate(false) && publicationDescriptorsEditionForm.validate(false) && thematicContentClassifiersEditionForm.validate(false)
                        && languageEditionForm.validate(false) && intellectualPropertyDescriptorsEditionForm.validate(false)) {
                    getUiHandlers().savePublication(getPublicationDto());
                }
            }
        });

        // Life cycle
        mainFormLayout.getProductionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().sendToProductionValidation(publicationDto);
            }
        });
        mainFormLayout.getDiffusionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().sendToDiffusionValidation(publicationDto);
            }
        });
        mainFormLayout.getRejectValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().rejectValidation(publicationDto);
            }
        });
        mainFormLayout.getPublishButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().publish(publicationDto);
            }
        });
        mainFormLayout.getVersioningButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final VersionWindow versionWindow = new VersionWindow(getConstants().lifeCycleVersioning());
                versionWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        if (versionWindow.validateForm()) {
                            getUiHandlers().version(publicationDto.getUrn(), versionWindow.getSelectedVersion());
                            versionWindow.destroy();
                        }
                    }
                });
            }
        });
    }

    private void createViewForm() {
        // Identifiers form
        identifiersForm = new NameableResourceIdentifiersForm();
        mainFormLayout.addViewCanvas(identifiersForm);

        // Content descriptors form
        contentDescriptorsForm = new StatisticalResourceContentDescriptorsForm();
        mainFormLayout.addViewCanvas(contentDescriptorsForm);

        // Common metadata form
        commonMetadataForm = new StatisticalResourceCommonMetadataForm();
        mainFormLayout.addViewCanvas(commonMetadataForm);

        // Thematic content classifiers
        thematicContentClassifiersForm = new StatisticalResourceThematicContentClassifiersForm();
        mainFormLayout.addViewCanvas(thematicContentClassifiersForm);

        // Languages
        languageForm = new StatisticalResourceLanguageForm();
        mainFormLayout.addViewCanvas(languageForm);

        // Production descriptors
        productionDescriptorsForm = new StatisticalResourceProductionDescriptorsForm();
        mainFormLayout.addViewCanvas(productionDescriptorsForm);

        // Class descriptors
        classDescriptorsForm = new PublicationClassDescriptorsForm();
        mainFormLayout.addViewCanvas(classDescriptorsForm);

        // Resource relation descriptors
        resourceRelationDescriptorsForm = new StatisticalResourceResourceRelationDescriptorsForm();
        mainFormLayout.addViewCanvas(resourceRelationDescriptorsForm);

        // Publication descriptors
        publicationDescriptorsForm = new StatisticalResourcePublicationDescriptorsForm();
        mainFormLayout.addViewCanvas(publicationDescriptorsForm);

        // Life cycle
        lifeCycleForm = new LifeCycleResourceLifeCycleForm();
        mainFormLayout.addViewCanvas(lifeCycleForm);

        // Version
        versionForm = new LifeCycleResourceVersionForm();
        mainFormLayout.addViewCanvas(versionForm);

        // Intellectual property descriptors
        intellectualPropertyDescriptorsForm = new StatisticalResourceIntellectualPropertyDescriptorsForm();
        mainFormLayout.addViewCanvas(intellectualPropertyDescriptorsForm);
    }

    private void createEditionForm() {
        // Identifiers form
        identifiersEditionForm = new NameableResourceIdentifiersEditionForm();
        mainFormLayout.addEditionCanvas(identifiersEditionForm);

        // Content descriptors form
        contentDescriptorsEditionForm = new StatisticalResourceContentDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(contentDescriptorsEditionForm);

        // Common metadata
        commonMetadataEditionForm = new StatisticalResourceCommonMetadataEditionForm();
        mainFormLayout.addEditionCanvas(commonMetadataEditionForm);

        // Thematic content classifiers
        thematicContentClassifiersEditionForm = new StatisticalResourceThematicContentClassifiersEditionForm();
        mainFormLayout.addEditionCanvas(thematicContentClassifiersEditionForm);

        // Languages
        languageEditionForm = new StatisticalResourceLanguageEditionForm();
        mainFormLayout.addEditionCanvas(languageEditionForm);

        // Production descriptors
        productionDescriptorsEditionForm = new StatisticalResourceProductionDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(productionDescriptorsEditionForm);

        // Class descriptors
        classDescriptorsEditionForm = new PublicationClassDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(classDescriptorsEditionForm);

        // Resource relation descriptors
        resourceRelationDescriptorsEditionForm = new PublicationResourceRelationDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(resourceRelationDescriptorsEditionForm);

        // Publication descriptors
        publicationDescriptorsEditionForm = new StatisticalResourcePublicationDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(publicationDescriptorsEditionForm);

        // Life cycle
        lifeCycleEditionForm = new LifeCycleResourceLifeCycleForm();
        mainFormLayout.addEditionCanvas(lifeCycleEditionForm);

        // Version
        versionEditionForm = new LifeCycleResourceVersionEditionForm();
        mainFormLayout.addEditionCanvas(versionEditionForm);

        // Intellectual property descriptors
        intellectualPropertyDescriptorsEditionForm = new StatisticalResourceIntellectualPropertyDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(intellectualPropertyDescriptorsEditionForm);
    }

    private void setPublicationViewMode(PublicationVersionDto publicationDto) {
        // Identifiers form
        identifiersForm.setNameableStatisticalResourceDto(publicationDto);

        // Content descriptors form
        contentDescriptorsForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Common metadata form
        commonMetadataForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Thematic content classifiers
        thematicContentClassifiersForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Languages
        languageForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Class descriptors
        classDescriptorsForm.setPublicationDto(publicationDto);

        // Resource relation descriptors
        resourceRelationDescriptorsForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Production descriptors
        productionDescriptorsForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Publication descriptors
        publicationDescriptorsForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Life cycle
        lifeCycleForm.setLifeCycleStatisticalResourceDto(publicationDto);

        // Version
        versionForm.setLifeCycleStatisticalResourceDto(publicationDto);

        // Intellectual property descriptors
        intellectualPropertyDescriptorsForm.setSiemacMetadataStatisticalResourceDto(publicationDto);
    }

    private void setPublicationEditionMode(PublicationVersionDto publicationDto) {
        // Identifiers form
        identifiersEditionForm.setNameableStatisticalResourceDto(publicationDto);

        // Content descriptors form
        contentDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Common metadata
        commonMetadataEditionForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Thematic content classifiers
        thematicContentClassifiersEditionForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Languages
        languageEditionForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Production descriptors
        productionDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Class descriptors
        classDescriptorsEditionForm.setPublicationDto(publicationDto);

        // Resource relation descriptors
        resourceRelationDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Publication descriptors
        publicationDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Life cycle
        lifeCycleEditionForm.setLifeCycleStatisticalResourceDto(publicationDto);

        // Version
        versionEditionForm.setLifeCycleStatisticalResourceDto(publicationDto);

        // Intellectual property descriptors
        intellectualPropertyDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(publicationDto);
    }

    @Override
    public void setPublication(PublicationVersionDto publicationDto) {
        this.publicationDto = publicationDto;

        mainFormLayout.updatePublishSection(publicationDto.getProcStatus());
        mainFormLayout.setViewMode();

        setPublicationViewMode(publicationDto);
        setPublicationEditionMode(publicationDto);
    }

    private PublicationVersionDto getPublicationDto() {
        // Identifiers
        publicationDto = (PublicationVersionDto) identifiersEditionForm.getNameableStatisticalResourceDto(publicationDto);

        // Content descriptors
        publicationDto = (PublicationVersionDto) contentDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(publicationDto);

        // Content descriptors
        publicationDto = (PublicationVersionDto) commonMetadataEditionForm.getSiemacMetadataStatisticalResourceDto(publicationDto);

        // Thematic content classifiers
        publicationDto = (PublicationVersionDto) thematicContentClassifiersEditionForm.getSiemacMetadataStatisticalResourceDto(publicationDto);

        // Language
        publicationDto = (PublicationVersionDto) languageEditionForm.getSiemacMetadataStatisticalResourceDto(publicationDto);

        // Production descriptors
        publicationDto = (PublicationVersionDto) productionDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(publicationDto);

        // Class descriptors
        publicationDto = classDescriptorsEditionForm.getPublicationDto(publicationDto);

        // Resource relation descriptors
        publicationDto = (PublicationVersionDto) resourceRelationDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(publicationDto);

        // Publication descriptors
        publicationDto = (PublicationVersionDto) publicationDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(publicationDto);

        // Version
        publicationDto = (PublicationVersionDto) versionEditionForm.getLifeCycleStatisticalResourceDto(publicationDto);

        // Intellectual property descriptors
        publicationDto = (PublicationVersionDto) intellectualPropertyDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(publicationDto);

        return publicationDto;
    }

    @Override
    public void setPublicationsForReplaces(GetPublicationVersionsResult result) {
        List<RelatedResourceDto> relatedResourceDtos = RelatedResourceUtils.getPublicationDtosAsRelatedResourceDtos(result.getPublicationDtos());
        resourceRelationDescriptorsEditionForm.setRelatedResourcesForReplaces(relatedResourceDtos, result.getFirstResultOut(), relatedResourceDtos.size(), result.getTotalResults());
    }

    @Override
    public void setPublicationsForIsReplacedBy(GetPublicationVersionsResult result) {
        List<RelatedResourceDto> relatedResourceDtos = RelatedResourceUtils.getPublicationDtosAsRelatedResourceDtos(result.getPublicationDtos());
        resourceRelationDescriptorsEditionForm.setRelatedResourcesForIsReplacedBy(relatedResourceDtos, result.getFirstResultOut(), relatedResourceDtos.size(), result.getTotalResults());
    }

    @Override
    protected StatisticalResourceCommonMetadataEditionForm getCommonMetadataEditionForm() {
        return commonMetadataEditionForm;
    }

    @Override
    protected StatisticalResourceProductionDescriptorsEditionForm getProductionDescriptorsEditionForm() {
        return productionDescriptorsEditionForm;
    }

    @Override
    protected StatisticalResourcePublicationDescriptorsEditionForm getPublicationDescriptorsEditionForm() {
        return publicationDescriptorsEditionForm;
    }

    @Override
    protected StatisticalResourceThematicContentClassifiersEditionForm getThematicContentClassifiersEditionForm() {
        return thematicContentClassifiersEditionForm;
    }

    @Override
    protected StatisticalResourceLanguageEditionForm getLanguageEditionForm() {
        return languageEditionForm;
    }
}
