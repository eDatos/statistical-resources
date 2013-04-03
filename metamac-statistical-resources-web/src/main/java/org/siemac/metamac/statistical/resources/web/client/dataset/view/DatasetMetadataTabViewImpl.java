package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetMetadataTabPresenter.DatasetMetadataTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DatasetMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.DatasetClassDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.DatasetClassDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.DatasetContentDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.DatasetContentDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.DatasetProductionDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.DatasetProductionDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.DatasetPublicationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.DatasetPublicationDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms.DatasetResourceRelationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.VersionWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceLifeCycleForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceVersionEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceVersionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceLanguageEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceLanguageForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceResourceRelationDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceThematicContentClassifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceThematicContentClassifiersForm;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsResult;
import org.siemac.metamac.statistical.resources.web.shared.utils.RelatedResourceUtils;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class DatasetMetadataTabViewImpl extends ViewWithUiHandlers<DatasetMetadataTabUiHandlers> implements DatasetMetadataTabView {

    // private final int AGENCIES_MAX_RESULTS = 15;

    private VLayout                                                  panel;
    private DatasetMainFormLayout                                    mainFormLayout;

    private NameableResourceIdentifiersForm                          identifiersForm;
    private DatasetContentDescriptorsForm                            contentDescriptorsForm;
    private StatisticalResourceThematicContentClassifiersForm        thematicContentClassifiersForm;
    private StatisticalResourceLanguageForm                          languageForm;
    private DatasetProductionDescriptorsForm                         productionDescriptorsForm;
    private DatasetClassDescriptorsForm                              classDescriptorsForm;
    private StatisticalResourceResourceRelationDescriptorsForm       resourceRelationDescriptorsForm;
    private DatasetPublicationDescriptorsForm                        publicationDescriptorsForm;
    private LifeCycleResourceLifeCycleForm                           lifeCycleForm;
    private LifeCycleResourceVersionForm                             versionForm;

    private NameableResourceIdentifiersEditionForm                   identifiersEditionForm;
    private DatasetContentDescriptorsEditionForm                     contentDescriptorsEditionForm;
    private StatisticalResourceThematicContentClassifiersEditionForm thematicContentClassifiersEditionForm;
    private StatisticalResourceLanguageEditionForm                   languageEditionForm;
    private DatasetProductionDescriptorsEditionForm                  productionDescriptorsEditionForm;
    private DatasetClassDescriptorsEditionForm                       classDescriptorsEditionForm;
    private DatasetResourceRelationDescriptorsEditionForm            resourceRelationDescriptorsEditionForm;
    private DatasetPublicationDescriptorsEditionForm                 publicationDescriptorsEditionForm;
    private LifeCycleResourceLifeCycleForm                           lifeCycleEditionForm;
    private LifeCycleResourceVersionEditionForm                      versionEditionForm;

    // private SearchExternalItemWindow searchAgencyWindow;
    // private SearchMultipleExternalItemWindow searchMultiAgencyWindow;

    private DatasetDto                                               datasetDto;

    public DatasetMetadataTabViewImpl() {
        panel = new VLayout();

        mainFormLayout = new DatasetMainFormLayout(DatasetClientSecurityUtils.canUpdateDataset());

        bindMainFormLayoutEvents();
        createViewForm();
        createEditionForm();

        panel.addMember(mainFormLayout);

    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(DatasetMetadataTabUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        resourceRelationDescriptorsEditionForm.setUiHandlers(uiHandlers);
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

                // versioningForm.setTranslationsShowed(translationsShowed);
                // contentMetadataForm.setTranslationsShowed(translationsShowed);
                // lifeCycleForm.setTranslationsShowed(translationsShowed);
            }
        });

        // Save
        mainFormLayout.getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (identifiersEditionForm.validate(false) && contentDescriptorsEditionForm.validate(false) && productionDescriptorsEditionForm.validate(false)
                        && classDescriptorsEditionForm.validate(false) && versionEditionForm.validate(false) && resourceRelationDescriptorsEditionForm.validate(false)
                        && publicationDescriptorsEditionForm.validate(false) && thematicContentClassifiersEditionForm.validate(false) && languageEditionForm.validate(false)) {
                    getUiHandlers().saveDataset(getDatasetDto());
                }
            }
        });

        // Life cycle
        mainFormLayout.getProductionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().sendToProductionValidation(datasetDto.getUrn(), datasetDto.getProcStatus());
            }
        });
        mainFormLayout.getDiffusionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().sendToDiffusionValidation(datasetDto.getUrn(), datasetDto.getProcStatus());
            }
        });
        mainFormLayout.getRejectValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().rejectValidation(datasetDto.getUrn(), datasetDto.getProcStatus());
            }
        });
        // FIXME: add clickhandler
        // mainFormLayout.getPendingPublicationButton().addClickHandler(new ClickHandler() {
        //
        // @Override
        // public void onClick(ClickEvent event) {
        // uiHandlers.sendToPendingPublication(datasetDto.getUrn(), datasetDto.getProcStatus());
        // }
        // });
        /*
         * mainFormLayout.getProgramPublicationButton().addClickHandler(new ClickHandler() {
         * @Override
         * public void onClick(ClickEvent event) {
         * final ProgramPublicationWindow window = new ProgramPublicationWindow(getConstants().lifeCycleProgramPublication());
         * window.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
         * @Override
         * public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
         * if (window.validateForm()) {
         * // TODO Send to date and hour selected to service
         * uiHandlers.programPublication(datasetDto.getUrn(), datasetDto.getProcStatus());
         * window.destroy();
         * }
         * }
         * });
         * }
         * });
         * mainFormLayout.getCancelProgrammedPublication().addClickHandler(new ClickHandler() {
         * @Override
         * public void onClick(ClickEvent event) {
         * uiHandlers.cancelProgrammedPublication(datasetDto.getUrn(), datasetDto.getProcStatus());
         * }
         * });
         */
        mainFormLayout.getPublishButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().publish(datasetDto.getUrn(), datasetDto.getProcStatus());
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
                            getUiHandlers().version(datasetDto.getUrn(), versionWindow.getSelectedVersion());
                            versionWindow.destroy();
                        }
                    }
                });
            }
        });
        // FIXME: ADD CLICK HANDLER
        // mainFormLayout.getArchiveButton().addClickHandler(new ClickHandler() {
        //
        // @Override
        // public void onClick(ClickEvent event) {
        // uiHandlers.archive(datasetDto.getUrn(), datasetDto.getProcStatus());
        // }
        // });
    }

    private void createViewForm() {
        // Identifiers Form
        identifiersForm = new NameableResourceIdentifiersForm();
        mainFormLayout.addViewCanvas(identifiersForm);

        // Content descriptors form
        contentDescriptorsForm = new DatasetContentDescriptorsForm();
        mainFormLayout.addViewCanvas(contentDescriptorsForm);

        // Thematic content classifiers
        thematicContentClassifiersForm = new StatisticalResourceThematicContentClassifiersForm();
        mainFormLayout.addViewCanvas(thematicContentClassifiersForm);

        // Languages
        languageForm = new StatisticalResourceLanguageForm();
        mainFormLayout.addViewCanvas(languageForm);

        // Production descriptors
        productionDescriptorsForm = new DatasetProductionDescriptorsForm();
        mainFormLayout.addViewCanvas(productionDescriptorsForm);

        // Class descriptors
        classDescriptorsForm = new DatasetClassDescriptorsForm();
        mainFormLayout.addViewCanvas(classDescriptorsForm);

        // Resource relation descriptors
        resourceRelationDescriptorsForm = new StatisticalResourceResourceRelationDescriptorsForm();
        mainFormLayout.addViewCanvas(resourceRelationDescriptorsForm);

        // Publication descriptors
        publicationDescriptorsForm = new DatasetPublicationDescriptorsForm();
        mainFormLayout.addViewCanvas(publicationDescriptorsForm);

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
        contentDescriptorsEditionForm = new DatasetContentDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(contentDescriptorsEditionForm);

        // Thematic content classifiers
        thematicContentClassifiersEditionForm = new StatisticalResourceThematicContentClassifiersEditionForm();
        mainFormLayout.addEditionCanvas(thematicContentClassifiersEditionForm);

        // Languages
        languageEditionForm = new StatisticalResourceLanguageEditionForm();
        mainFormLayout.addEditionCanvas(languageEditionForm);

        // Production descriptors
        productionDescriptorsEditionForm = new DatasetProductionDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(productionDescriptorsEditionForm);

        // Class descriptors
        classDescriptorsEditionForm = new DatasetClassDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(classDescriptorsEditionForm);

        // Resource relation descriptors
        resourceRelationDescriptorsEditionForm = new DatasetResourceRelationDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(resourceRelationDescriptorsEditionForm);

        // Publication descriptors
        publicationDescriptorsEditionForm = new DatasetPublicationDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(publicationDescriptorsEditionForm);

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

    @Override
    public void setDataset(DatasetDto datasetDto) {
        this.datasetDto = datasetDto;

        mainFormLayout.updatePublishSection(datasetDto.getProcStatus());

        mainFormLayout.setViewMode();

        setDatasetViewMode(datasetDto);
        setDatasetEditionMode(datasetDto);
    }

    private void setDatasetViewMode(DatasetDto datasetDto) {
        // Identifiers
        identifiersForm.setNameableStatisticalResourceDto(datasetDto);

        // Content descriptors
        contentDescriptorsForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Thematic content classifiers
        thematicContentClassifiersForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Languages
        languageForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Production descriptors
        productionDescriptorsForm.setDatasetDto(datasetDto);

        // Class descriptors
        classDescriptorsForm.setDatasetDto(datasetDto);

        // Resource relation descriptors
        resourceRelationDescriptorsForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Publication descriptors
        publicationDescriptorsForm.setDatasetDto(datasetDto);

        // Life cycle
        lifeCycleForm.setLifeCycleStatisticalResourceDto(datasetDto);

        // Version
        versionForm.setLifeCycleStatisticalResourceDto(datasetDto);

    }

    private void setDatasetEditionMode(DatasetDto datasetDto) {
        // Identifiers form
        identifiersEditionForm.setNameableStatisticalResourceDto(datasetDto);

        // Content Descriptors
        contentDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Thematic content classifiers
        thematicContentClassifiersEditionForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Languages
        languageEditionForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Production descriptors
        productionDescriptorsEditionForm.setDatasetDto(datasetDto);

        // Class descriptors
        classDescriptorsEditionForm.setDatasetDto(datasetDto);

        // Resource relation descriptors
        resourceRelationDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Publication descriptors
        publicationDescriptorsEditionForm.setDatasetDto(datasetDto);

        // Life cycle
        lifeCycleEditionForm.setLifeCycleStatisticalResourceDto(datasetDto);

        // Version
        versionEditionForm.setLifeCycleStatisticalResourceDto(datasetDto);

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

    public DatasetDto getDatasetDto() {
        // Identifiers form
        datasetDto = (DatasetDto) identifiersEditionForm.getNameableStatisticalResourceDto(datasetDto);

        // Content descriptors form
        datasetDto = contentDescriptorsEditionForm.getDatasetDto(datasetDto);

        // Thematic content classifiers
        datasetDto = (DatasetDto) thematicContentClassifiersEditionForm.getSiemacMetadataStatisticalResourceDto(datasetDto);

        // Language
        datasetDto = (DatasetDto) languageEditionForm.getSiemacMetadataStatisticalResourceDto(datasetDto);

        // Production descriptors
        datasetDto = productionDescriptorsEditionForm.getDatasetDto(datasetDto);

        // Class descriptors
        datasetDto = classDescriptorsEditionForm.getDatasetDto(datasetDto);

        // Resource relation descriptors
        datasetDto = (DatasetDto) resourceRelationDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(datasetDto);

        // Publication descriptors
        datasetDto = publicationDescriptorsEditionForm.getDatasetDto(datasetDto);

        // Version
        datasetDto = (DatasetDto) versionEditionForm.getLifeCycleStatisticalResourceDto(datasetDto);

        // FIXME: add metadata

        // Version form
        // String rationaleType = versioningEditionForm.getValueAsString(DatasetDS.RATIONALE_TYPE);
        // if (!StringUtils.isEmpty(rationaleType)) {
        // datasetDto.setRationaleType(StatisticalResourceVersionRationaleTypeEnum.valueOf(rationaleType));
        // }
        // datasetDto.setRationale(versioningEditionForm.getValueAsString(DatasetDS.RATIONALE));
        // datasetDto.setNextVersionDate((Date) versioningEditionForm.getValue(DatasetDS.NEXT_VERSION_DATE));
        //
        // // Life cycle form
        // ExternalItemDto creatorAgency = ((SearchExternalViewTextItem)lifeCycleEditionForm.getField(DatasetDS.CREATOR)).getExternalItem();
        // datasetDto.setCreator(creatorAgency);
        //
        // List<ExternalItemDto> contributorAgencies = ((SearchExternalListItem)lifeCycleEditionForm.getField(DatasetDS.CONTRIBUTOR)).getSelectedExternalItems();
        // if (contributorAgencies != null) {
        // datasetDto.getContributor().clear();
        // datasetDto.getContributor().addAll(contributorAgencies);
        // }
        // List<ExternalItemDto> publisherAgencies = ((SearchExternalListItem)lifeCycleEditionForm.getField(DatasetDS.PUBLISHER)).getSelectedExternalItems();
        // if (publisherAgencies != null) {
        // datasetDto.getPublisher().clear();
        // datasetDto.getPublisher().addAll(publisherAgencies);
        // }
        //
        // List<ExternalItemDto> mediatorAgencies = ((SearchExternalListItem)lifeCycleEditionForm.getField(DatasetDS.MEDIATOR)).getSelectedExternalItems();
        // if (mediatorAgencies != null) {
        // datasetDto.getMediator().clear();
        // datasetDto.getMediator().addAll(mediatorAgencies);
        // }
        //
        // // Content metadata form
        // if (datasetDto.getContentMetadata() == null) {
        // datasetDto.setContentMetadata(new ContentMetadataDto());
        // }
        // datasetDto.getContentMetadata().setDescription((InternationalStringDto) contentMetadataEditionForm.getValue(DatasetDS.DESCRIPTION));
        // datasetDto.getContentMetadata().setNextUpdateDate((Date) contentMetadataEditionForm.getValue(DatasetDS.NEXT_UPDATE_DATE));

        return datasetDto;
    }

    @Override
    public void setDatasetsForReplaces(GetDatasetsResult result) {
        List<RelatedResourceDto> relatedResourceDtos = RelatedResourceUtils.getDatasetDtosAsRelatedResourceDtos(result.getDatasetDtos());
        resourceRelationDescriptorsEditionForm.setRelatedResourcesForReplaces(relatedResourceDtos, result.getFirstResultOut(), relatedResourceDtos.size(), result.getTotalResults());
    }

    @Override
    public void setDatasetsForIsReplacedBy(GetDatasetsResult result) {
        List<RelatedResourceDto> relatedResourceDtos = RelatedResourceUtils.getDatasetDtosAsRelatedResourceDtos(result.getDatasetDtos());
        resourceRelationDescriptorsEditionForm.setRelatedResourcesForIsReplacedBy(relatedResourceDtos, result.getFirstResultOut(), relatedResourceDtos.size(), result.getTotalResults());
    }

    // private enum AgencyField {
    // AGENCY_CREATOR(DatasetDS.CREATOR), AGENCY_CONTRIBUTOR(DatasetDS.CONTRIBUTOR), AGENCY_PUBLISHER(DatasetDS.PUBLISHER), AGENCY_MEDIATOR(DatasetDS.MEDIATOR);
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
