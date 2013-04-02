package org.siemac.metamac.statistical.resources.web.client.publication.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationMetadataTabPresenter.PublicationMetadataTabView;
import org.siemac.metamac.statistical.resources.web.client.publication.utils.PublicationClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.publication.widgets.PublicationMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.widgets.VersionWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceLifeCycleForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceVersionEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceVersionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.PublicationClassDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.PublicationClassDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceContentDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceContentDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceProductionDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceProductionDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceResourceRelationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceResourceRelationDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListResult;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class PublicationMetadataTabViewImpl extends ViewImpl implements PublicationMetadataTabView {

    private PublicationMetadataTabUiHandlers                          uiHandlers;
    private VLayout                                                   panel;

    private PublicationMainFormLayout                                 mainFormLayout;

    private NameableResourceIdentifiersForm                           identifiersForm;
    private StatisticalResourceContentDescriptorsForm                 contentDescriptorsForm;
    private StatisticalResourceProductionDescriptorsForm              productionDescriptorsForm;
    private PublicationClassDescriptorsForm                           classDescriptorsForm;
    private StatisticalResourceResourceRelationDescriptorsForm        resourceRelationDescriptorsForm;
    private LifeCycleResourceLifeCycleForm                            lifeCycleForm;
    private LifeCycleResourceVersionForm                              versionForm;
    // private GroupDynamicForm versionForm;
    // private GroupDynamicForm lifeCycleForm;
    // private GroupDynamicForm contentMetadataForm;

    private NameableResourceIdentifiersEditionForm                    identifiersEditionForm;
    private StatisticalResourceContentDescriptorsEditionForm          contentDescriptorsEditionForm;
    private StatisticalResourceProductionDescriptorsEditionForm       productionDescriptorsEditionForm;
    private PublicationClassDescriptorsEditionForm                    classDescriptorsEditionForm;
    private StatisticalResourceResourceRelationDescriptorsEditionForm resourceRelationDescriptorsEditionForm;
    private LifeCycleResourceLifeCycleForm                            lifeCycleEditionForm;
    private LifeCycleResourceVersionEditionForm                       versionEditionForm;
    // private GroupDynamicForm versionEditionForm;
    // private GroupDynamicForm lifeCycleEditionForm;
    // private GroupDynamicForm contentMetadataEditionForm;

    // private SearchExternalItemWindow searchAgencyWindow;
    // private SearchMultipleExternalItemWindow searchMultiAgencyWindow;

    private PublicationDto                                            publicationDto;

    @Inject
    public PublicationMetadataTabViewImpl() {
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.SCROLL);

        mainFormLayout = new PublicationMainFormLayout(PublicationClientSecurityUtils.canUpdatePublication());
        bindMainFormLayoutEvents();
        createViewForm();
        createEditionForm();

        panel.addMember(mainFormLayout);
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

                productionDescriptorsForm.setTranslationsShowed(translationsShowed);
                productionDescriptorsEditionForm.setTranslationsShowed(translationsShowed);

                classDescriptorsForm.setTranslationsShowed(translationsShowed);
                classDescriptorsEditionForm.setTranslationsShowed(translationsShowed);

                resourceRelationDescriptorsForm.setTranslationsShowed(translationsShowed);
                resourceRelationDescriptorsEditionForm.setTranslationsShowed(translationsShowed);

                lifeCycleForm.setTranslationsShowed(translationsShowed);
                lifeCycleEditionForm.setTranslationsShowed(translationsShowed);

                versionForm.setTranslationsShowed(translationsShowed);
                versionEditionForm.setTranslationsShowed(translationsShowed);

                // versionForm.setTranslationsShowed(translationsShowed);
                // versionEditionForm.setTranslationsShowed(translationsShowed);
                //
                // lifeCycleForm.setTranslationsShowed(translationsShowed);
                // lifeCycleEditionForm.setTranslationsShowed(translationsShowed);
                //
                // contentMetadataForm.setTranslationsShowed(translationsShowed);
                // contentMetadataEditionForm.setTranslationsShowed(translationsShowed);
            }
        });

        // Save

        mainFormLayout.getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (identifiersEditionForm.validate(false) && contentDescriptorsEditionForm.validate(false) && productionDescriptorsEditionForm.validate(false)
                        && classDescriptorsEditionForm.validate(false) && versionEditionForm.validate(false) && resourceRelationDescriptorsEditionForm.validate(false)) {
                    uiHandlers.savePublication(getPublicationDto());
                }
            }
        });

        // Life cycle
        mainFormLayout.getProductionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.sendToProductionValidation(publicationDto.getUrn(), publicationDto.getProcStatus());
            }
        });
        mainFormLayout.getDiffusionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.sendToDiffusionValidation(publicationDto.getUrn(), publicationDto.getProcStatus());
            }
        });
        mainFormLayout.getRejectValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.rejectValidation(publicationDto.getUrn(), publicationDto.getProcStatus());
            }
        });
        mainFormLayout.getPublishButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.publish(publicationDto.getUrn(), publicationDto.getProcStatus());
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
                            uiHandlers.version(publicationDto.getUrn(), versionWindow.getSelectedVersion());
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

        // Production descriptors
        productionDescriptorsForm = new StatisticalResourceProductionDescriptorsForm();
        mainFormLayout.addViewCanvas(productionDescriptorsForm);

        // Class descriptors
        classDescriptorsForm = new PublicationClassDescriptorsForm();
        mainFormLayout.addViewCanvas(classDescriptorsForm);

        // Resource relation descriptors
        resourceRelationDescriptorsForm = new StatisticalResourceResourceRelationDescriptorsForm();
        mainFormLayout.addViewCanvas(resourceRelationDescriptorsForm);

        // Life cycle
        lifeCycleForm = new LifeCycleResourceLifeCycleForm();
        mainFormLayout.addViewCanvas(lifeCycleForm);

        // Version
        versionForm = new LifeCycleResourceVersionForm();
        mainFormLayout.addViewCanvas(versionForm);
    }

    private void createEditionForm() {
        // Identifiers form
        identifiersEditionForm = new NameableResourceIdentifiersEditionForm();
        mainFormLayout.addEditionCanvas(identifiersEditionForm);

        // Content descriptors form
        contentDescriptorsEditionForm = new StatisticalResourceContentDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(contentDescriptorsEditionForm);

        // Production descriptors
        productionDescriptorsEditionForm = new StatisticalResourceProductionDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(productionDescriptorsEditionForm);

        // Class descriptors
        classDescriptorsEditionForm = new PublicationClassDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(classDescriptorsEditionForm);

        // Resource relation descriptors
        resourceRelationDescriptorsEditionForm = new StatisticalResourceResourceRelationDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(resourceRelationDescriptorsEditionForm);

        // Life cycle
        lifeCycleEditionForm = new LifeCycleResourceLifeCycleForm();
        mainFormLayout.addEditionCanvas(lifeCycleEditionForm);

        // Version
        versionEditionForm = new LifeCycleResourceVersionEditionForm();
        mainFormLayout.addEditionCanvas(versionEditionForm);
    }

    // private SearchExternalViewTextItem createRelatedAgencyItem(String name, String title, AgencyField agencyField) {
    // SearchExternalViewTextItem agencyItem = new SearchExternalViewTextItem(name, title);
    // agencyItem.setRequired(true);
    // agencyItem.getSearchIcon().addFormItemClickHandler(new SearchAgencyFormItemClickHandler(agencyField));
    // return agencyItem;
    // }
    //
    // private SearchExternalListItem createRelatedMultiAgencyItem(String name, String title, AgencyField agencyField, boolean editionMode) {
    // SearchExternalListItem agencyItem = new SearchExternalListItem(name, title, editionMode);
    // if (editionMode) {
    // agencyItem.setRequired(false);
    // agencyItem.getSearchIcon().addFormItemClickHandler(new SearchMultiAgencyFormItemClickHandler(agencyField));
    // }
    // return agencyItem;
    // }

    private void setPublicationViewMode(PublicationDto publicationDto) {
        // Identifiers form
        identifiersForm.setNameableStatisticalResourceDto(publicationDto);

        // Content descriptors form
        contentDescriptorsForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Life cycle
        lifeCycleForm.setLifeCycleStatisticalResourceDto(publicationDto);

        // Class descriptors
        classDescriptorsForm.setPublicationDto(publicationDto);

        // Resource relation descriptors
        resourceRelationDescriptorsForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Production descriptors
        productionDescriptorsForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Version
        versionForm.setLifeCycleStatisticalResourceDto(publicationDto);
    }

    private void setPublicationEditionMode(PublicationDto publicationDto) {
        // Identifiers form
        identifiersEditionForm.setNameableStatisticalResourceDto(publicationDto);

        // Content descriptors form
        contentDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Production descriptors
        productionDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Class descriptors
        classDescriptorsEditionForm.setPublicationDto(publicationDto);

        // Resource relation descriptors
        resourceRelationDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(publicationDto);

        // Life cycle
        lifeCycleEditionForm.setLifeCycleStatisticalResourceDto(publicationDto);

        // Version
        versionEditionForm.setLifeCycleStatisticalResourceDto(publicationDto);
    }

    @Override
    public void setPublication(PublicationDto publicationDto) {
        this.publicationDto = publicationDto;

        mainFormLayout.updatePublishSection(publicationDto.getProcStatus());
        mainFormLayout.setViewMode();

        setPublicationViewMode(publicationDto);
        setPublicationEditionMode(publicationDto);
    }

    @Override
    public void setAgenciesPaginatedList(GetAgenciesPaginatedListResult result) {
        // if (searchAgencyWindow != null) {
        // searchAgencyWindow.setExternalItems(result.getAgenciesList());
        // }
        // if (searchMultiAgencyWindow != null) {
        // searchMultiAgencyWindow.setSourceExternalItems(result.getAgenciesList());
        // }
    }

    private PublicationDto getPublicationDto() {
        // Identifiers
        publicationDto = (PublicationDto) identifiersEditionForm.getNameableStatisticalResourceDto(publicationDto);

        // Content descriptors
        publicationDto = (PublicationDto) contentDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(publicationDto);

        // Production descriptors
        publicationDto = (PublicationDto) productionDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(publicationDto);

        // Class descriptors
        publicationDto = classDescriptorsEditionForm.getPublicationDto(publicationDto);

        // Resource relation descriptors
        publicationDto = (PublicationDto) resourceRelationDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(publicationDto);

        // Version
        publicationDto = (PublicationDto) versionEditionForm.getLifeCycleStatisticalResourceDto(publicationDto);

        // // Version form
        // collectionDto.setRationale(versionEditionForm.getValueAsString(PublicationDS.RATIONALE));
        // String rationaleType = versionEditionForm.getValueAsString(PublicationDS.RATIONALE_TYPE);
        // if (!StringUtils.isEmpty(rationaleType)) {
        // collectionDto.setRationaleType(StatisticalResourceVersionRationaleTypeEnum.valueOf(rationaleType));
        // }
        //
        // collectionDto.setNextVersionDate((Date) versionEditionForm.getValue(PublicationDS.NEXT_VERSION_DATE));
        //
        // // Life cycle form
        //
        // // Content metadata form
        // if (collectionDto.getContentMetadata() == null) {
        // collectionDto.setContentMetadata(new ContentMetadataDto());
        // }
        //
        // ExternalItemDto creatorAgency = ((SearchExternalViewTextItem) lifeCycleEditionForm.getField(PublicationDS.CREATOR)).getExternalItem();
        // collectionDto.setCreator(creatorAgency);
        //
        // // Life cycle form
        // List<ExternalItemDto> contributorAgencies = ((SearchExternalListItem) lifeCycleEditionForm.getField(PublicationDS.CONTRIBUTOR)).getSelectedExternalItems();
        // if (contributorAgencies != null) {
        // collectionDto.getContributor().clear();
        // collectionDto.getContributor().addAll(contributorAgencies);
        // }
        // List<ExternalItemDto> publisherAgencies = ((SearchExternalListItem) lifeCycleEditionForm.getField(PublicationDS.PUBLISHER)).getSelectedExternalItems();
        // if (publisherAgencies != null) {
        // collectionDto.getPublisher().clear();
        // collectionDto.getPublisher().addAll(publisherAgencies);
        // }
        //
        // List<ExternalItemDto> mediatorAgencies = ((SearchExternalListItem) lifeCycleEditionForm.getField(PublicationDS.MEDIATOR)).getSelectedExternalItems();
        // if (mediatorAgencies != null) {
        // collectionDto.getMediator().clear();
        // collectionDto.getMediator().addAll(mediatorAgencies);
        // }
        //
        // collectionDto.getContentMetadata().setDescription((InternationalStringDto) contentMetadataEditionForm.getValue(PublicationDS.DESCRIPTION));
        // collectionDto.getContentMetadata().setNextUpdateDate((Date) contentMetadataEditionForm.getValue(PublicationDS.NEXT_UPDATE_DATE));

        return publicationDto;
    }

    @Override
    public void setUiHandlers(PublicationMetadataTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    // private enum AgencyField {
    // AGENCY_CREATOR(PublicationDS.CREATOR), AGENCY_CONTRIBUTOR(PublicationDS.CONTRIBUTOR), AGENCY_PUBLISHER(PublicationDS.PUBLISHER), AGENCY_MEDIATOR(PublicationDS.MEDIATOR);
    //
    // private String formFieldId;
    //
    // private AgencyField(String formFieldId) {
    // this.formFieldId = formFieldId;
    // }
    //
    // public String getFormFieldId() {
    // return formFieldId;
    // }
    // }

    // private class SearchAgencyFormItemClickHandler implements FormItemClickHandler {
    //
    // private AgencyField agencyField;
    //
    // public SearchAgencyFormItemClickHandler(AgencyField agencyField) {
    // this.agencyField = agencyField;
    // }
    //
    // @Override
    // public void onFormItemClick(FormItemIconClickEvent event) {
    // final int AGENCY_FIRST_RESULT = 0;
    // final int AGENCY_MAX_RESULTS = 16;
    //
    // searchAgencyWindow = new SearchExternalItemWindow(getConstants().agencySearch(), AGENCY_MAX_RESULTS, new PaginatedAction() {
    //
    // @Override
    // public void retrieveResultSet(int firstResult, int maxResults) {
    // uiHandlers.retrieveAgencies(firstResult, maxResults, null);
    // }
    // });
    // uiHandlers.retrieveAgencies(AGENCY_FIRST_RESULT, AGENCY_MAX_RESULTS, null);
    // searchAgencyWindow.setSearchAction(new SearchPaginatedAction() {
    //
    // @Override
    // public void retrieveResultSet(int firstResult, int maxResults, String code) {
    // uiHandlers.retrieveAgencies(firstResult, maxResults, code);
    // }
    // });
    // searchAgencyWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
    //
    // @Override
    // public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
    // ExternalItemDto selectedAgency = searchAgencyWindow.getSelectedExternalItem();
    //
    // ((SearchExternalViewTextItem) lifeCycleEditionForm.getField(agencyField.getFormFieldId())).setExternalItem(selectedAgency);
    // searchAgencyWindow.destroy();
    // }
    // });
    // }
    // }
    //
    // private class SearchMultiAgencyFormItemClickHandler implements FormItemClickHandler {
    //
    // private AgencyField agencyField;
    //
    // public SearchMultiAgencyFormItemClickHandler(AgencyField agencyField) {
    // this.agencyField = agencyField;
    // }
    //
    // @Override
    // public void onFormItemClick(FormItemIconClickEvent event) {
    // final int AGENCY_FIRST_RESULT = 0;
    // final int AGENCY_MAX_RESULTS = 16;
    //
    // searchMultiAgencyWindow = new SearchMultipleExternalItemWindow(getConstants().agencySearch(), AGENCY_MAX_RESULTS, new PaginatedAction() {
    //
    // @Override
    // public void retrieveResultSet(int firstResult, int maxResults) {
    // uiHandlers.retrieveAgencies(firstResult, maxResults, null);
    // }
    // });
    //
    // List<ExternalItemDto> selectedAgencies = ((SearchExternalListItem) lifeCycleEditionForm.getField(agencyField.getFormFieldId())).getSelectedExternalItems();
    // searchMultiAgencyWindow.setSelectedExternalItems(selectedAgencies);
    //
    // uiHandlers.retrieveAgencies(AGENCY_FIRST_RESULT, AGENCY_MAX_RESULTS, null);
    // searchMultiAgencyWindow.setSearchAction(new SearchPaginatedAction() {
    //
    // @Override
    // public void retrieveResultSet(int firstResult, int maxResults, String code) {
    // uiHandlers.retrieveAgencies(firstResult, maxResults, code);
    // }
    // });
    // searchMultiAgencyWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
    //
    // @Override
    // public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
    // List<ExternalItemDto> selectedAgencies = searchMultiAgencyWindow.getSelectedExternalItems();
    // ((SearchExternalListItem) lifeCycleEditionForm.getField(agencyField.getFormFieldId())).setExternalItems(selectedAgencies);
    // searchMultiAgencyWindow.hide();
    // searchMultiAgencyWindow.destroy();
    // }
    // });
    // }
    // }
}
