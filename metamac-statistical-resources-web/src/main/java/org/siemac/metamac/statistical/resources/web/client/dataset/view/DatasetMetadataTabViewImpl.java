package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetMetadataTabPresenter.DatasetMetadataTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DatasetMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.widgets.VersionWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasetClassDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasetClassDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasetContentDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasetContentDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasetProductionDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.DatasetProductionDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceLifeCycleForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceVersionEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceVersionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceResourceRelationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceResourceRelationDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListResult;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class DatasetMetadataTabViewImpl extends ViewImpl implements DatasetMetadataTabView {

    // private final int AGENCIES_MAX_RESULTS = 15;

    private DatasetMetadataTabUiHandlers                              uiHandlers;
    private VLayout                                                   panel;
    private DatasetMainFormLayout                                     mainFormLayout;

    private NameableResourceIdentifiersForm                           identifiersForm;
    private DatasetContentDescriptorsForm                             contentDescriptorsForm;
    private DatasetProductionDescriptorsForm                          productionDescriptorsForm;
    private DatasetClassDescriptorsForm                               classDescriptorsForm;
    private StatisticalResourceResourceRelationDescriptorsForm        resourceRelationDescriptorsForm;
    private LifeCycleResourceLifeCycleForm                            lifeCycleForm;
    private LifeCycleResourceVersionForm                              versionForm;
    // private GroupDynamicForm contentMetadataForm;
    // private GroupDynamicForm versioningForm;
    // private GroupDynamicForm lifeCycleForm;

    private NameableResourceIdentifiersEditionForm                    identifiersEditionForm;
    private DatasetContentDescriptorsEditionForm                      contentDescriptorsEditionForm;
    private DatasetProductionDescriptorsEditionForm                   productionDescriptorsEditionForm;
    private DatasetClassDescriptorsEditionForm                        classDescriptorsEditionForm;
    private StatisticalResourceResourceRelationDescriptorsEditionForm resourceRelationDescriptorsEditionForm;
    private LifeCycleResourceLifeCycleForm                            lifeCycleEditionForm;
    private LifeCycleResourceVersionEditionForm                       versionEditionForm;
    // private GroupDynamicForm contentMetadataEditionForm;
    // private GroupDynamicForm versioningEditionForm;
    // private GroupDynamicForm lifeCycleEditionForm;

    // private SearchExternalItemWindow searchAgencyWindow;
    // private SearchMultipleExternalItemWindow searchMultiAgencyWindow;

    private DatasetDto                                                datasetDto;

    public DatasetMetadataTabViewImpl() {
        panel = new VLayout();

        mainFormLayout = new DatasetMainFormLayout(DatasetClientSecurityUtils.canUpdateDataset());

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
                        && classDescriptorsEditionForm.validate(false) && versionEditionForm.validate(false) && resourceRelationDescriptorsEditionForm.validate(false)) {
                    uiHandlers.saveDataset(getDatasetDto());
                }
            }
        });

        // Life cycle
        mainFormLayout.getProductionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.sendToProductionValidation(datasetDto.getUrn(), datasetDto.getProcStatus());
            }
        });
        mainFormLayout.getDiffusionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.sendToDiffusionValidation(datasetDto.getUrn(), datasetDto.getProcStatus());
            }
        });
        mainFormLayout.getRejectValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.rejectValidation(datasetDto.getUrn(), datasetDto.getProcStatus());
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
                uiHandlers.publish(datasetDto.getUrn(), datasetDto.getProcStatus());
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
                            uiHandlers.version(datasetDto.getUrn(), versionWindow.getSelectedVersion());
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

        // Production descriptors
        productionDescriptorsForm = new DatasetProductionDescriptorsForm();
        mainFormLayout.addViewCanvas(productionDescriptorsForm);

        // Class descriptors
        classDescriptorsForm = new DatasetClassDescriptorsForm();
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

        // version
        // versioningForm = new GroupDynamicForm(getConstants().datasetVersioning());
        // ViewTextItem version = new ViewTextItem(DatasetDS.VERSION_LOGIC, getConstants().datasetVersion());
        // ViewTextItem dateVersion = new ViewTextItem(DatasetDS.VERSION_DATE, getConstants().datasetVersionDate());
        // ViewTextItem nextVersion = new ViewTextItem(DatasetDS.NEXT_VERSION_DATE, getConstants().datasetNextVersion());
        // ViewTextItem rationale = new ViewTextItem(DatasetDS.RATIONALE, getConstants().datasetRationale());
        // ViewTextItem rationaleType = new ViewTextItem(DatasetDS.RATIONALE_TYPE, getConstants().datasetRationaleType());
        // versioningForm.setFields(version, dateVersion, nextVersion, rationaleType, rationale);

        // Life cycle form
        // lifeCycleForm = new GroupDynamicForm(getConstants().datasetLifeCycle());
        // ViewTextItem procStatus = new ViewTextItem(DatasetDS.PROC_STATUS, getConstants().lifeCycleProcStatus());
        // ViewTextItem responsabilityCreator = new ViewTextItem(DatasetDS.RESPONSABILITY_CREATOR, getConstants().lifeCycleResponsabilityCreator());
        // ViewTextItem responsabilityContributor = new ViewTextItem(DatasetDS.RESPONSABILITY_CONTRIBUTOR, getConstants().lifeCycleResponsabilityContributor());
        // ViewTextItem responsabilitySubmitted = new ViewTextItem(DatasetDS.RESPONSABILITY_SUBMITTED, getConstants().lifeCycleResponsabilitySubmitted());
        // ViewTextItem responsabilityAccepted = new ViewTextItem(DatasetDS.RESPONSABILITY_ACCEPTED, getConstants().lifeCycleResponsabilityAccepted());
        // ViewTextItem responsabilityIssued = new ViewTextItem(DatasetDS.RESPONSABILITY_ISSUED, getConstants().lifeCycleResponsabilityIssued());
        // ViewTextItem responsabilityOutOfPrint = new ViewTextItem(DatasetDS.RESPONSABILITY_OUT_OF_PRINT, getConstants().lifeCycleResponsabilityOutOfPrint());
        // ViewTextItem creator = new ViewTextItem(DatasetDS.CREATOR, getConstants().lifeCycleCreator());

        // SearchExternalListItem contributor = createRelatedMultiAgencyItem(DatasetDS.CONTRIBUTOR, getConstants().lifeCycleContributor(), AgencyField.AGENCY_CONTRIBUTOR, false);
        // SearchExternalListItem publisher = createRelatedMultiAgencyItem(DatasetDS.PUBLISHER, getConstants().lifeCyclePublisher(), AgencyField.AGENCY_PUBLISHER, false);
        // SearchExternalListItem mediator = createRelatedMultiAgencyItem(DatasetDS.MEDIATOR, getConstants().lifeCycleMediator(), AgencyField.AGENCY_MEDIATOR, false);
        //
        // ViewTextItem submittedDate = new ViewTextItem(DatasetDS.SUBMITTED_DATE, getConstants().lifeCycleSubmittedDate());
        // ViewTextItem acceptedDate = new ViewTextItem(DatasetDS.ACCEPTED_DATE, getConstants().lifeCycleAcceptedDate());
        // ViewTextItem issuedDate = new ViewTextItem(DatasetDS.ISSUED_DATE, getConstants().lifeCycleIssuedDate());
        // lifeCycleForm.setFields(procStatus, responsabilityCreator, responsabilityContributor, responsabilitySubmitted, responsabilityAccepted, responsabilityIssued, responsabilityOutOfPrint,
        // creator,
        // contributor, publisher, mediator, submittedDate, acceptedDate, issuedDate);

        // Content metadata form
        // contentMetadataForm = new GroupDynamicForm(getConstants().datasetContentMetadata());
        // ViewTextItem language = new ViewTextItem(DatasetDS.LANGUAGE, getConstants().contentMetadataLanguage());
        // ViewTextItem languages = new ViewTextItem(DatasetDS.LANGUAGES, getConstants().contentMetadataLanguages());
        // ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(DatasetDS.DESCRIPTION, getConstants().contentMetadataDescription());
        // ViewTextItem keywords = new ViewTextItem(DatasetDS.KEYWORDS, getConstants().contentMetadataKeywords());
        // ViewTextItem spatialCoverage = new ViewTextItem(DatasetDS.SPATIAL_COVERAGE, getConstants().contentMetadataSpatialCoverage());
        // ViewTextItem spatialCoverageCodes = new ViewTextItem(DatasetDS.SPATIAL_COVERAGE_CODES, getConstants().contentMetadataSpatialCoverageCodes());
        // ViewTextItem temporalCoverage = new ViewTextItem(DatasetDS.TEMPORAL_COVERAGE, getConstants().contentMetadataTemporalCoverage());
        // ViewTextItem temporalCoverageCodes = new ViewTextItem(DatasetDS.TEMPORAL_COVERAGE_CODES, getConstants().contentMetadataTemporalCoverageCodes());
        // ViewTextItem type = new ViewTextItem(DatasetDS.TYPE, getConstants().contentMetadataType());
        // ViewTextItem format = new ViewTextItem(DatasetDS.FORMAT, getConstants().contentMetadataFormat());
        // ViewTextItem nextUpdate = new ViewTextItem(DatasetDS.NEXT_UPDATE_DATE, getConstants().contentMetadataNextUpdate());
        // ViewTextItem updateFrequency = new ViewTextItem(DatasetDS.UPDATE_FREQUENCY, getConstants().contentMetadataUpdateFrequency());
        // ViewTextItem rightsHolder = new ViewTextItem(DatasetDS.RIGHTS_HOLDER, getConstants().contentMetadataRightsHolder());
        // ViewTextItem copyrightedDate = new ViewTextItem(DatasetDS.COPYRIGHTED_DATE, getConstants().contentMetadataCopyrightedDate());
        // ViewTextItem license = new ViewTextItem(DatasetDS.LICENSE, getConstants().contentMetadataLicense());
        // contentMetadataForm.setFields(language, languages, description, keywords, spatialCoverage, spatialCoverageCodes, temporalCoverage, temporalCoverageCodes, type, format, nextUpdate,
        // updateFrequency, rightsHolder, copyrightedDate, license);

        // mainFormLayout.addViewCanvas(versioningForm);
        // mainFormLayout.addViewCanvas(lifeCycleForm);
        // mainFormLayout.addViewCanvas(contentMetadataForm);
    }

    private void createEditionForm() {
        // Identifiers form
        identifiersEditionForm = new NameableResourceIdentifiersEditionForm();
        mainFormLayout.addEditionCanvas(identifiersEditionForm);

        // Content descriptors form
        contentDescriptorsEditionForm = new DatasetContentDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(contentDescriptorsEditionForm);

        // Production descriptors
        productionDescriptorsEditionForm = new DatasetProductionDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(productionDescriptorsEditionForm);

        // Class descriptors
        classDescriptorsEditionForm = new DatasetClassDescriptorsEditionForm();
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

        // Version form
        // versioningEditionForm = new GroupDynamicForm(getConstants().versionableVersion());
        // ViewTextItem version = new ViewTextItem(DatasetDS.VERSION_LOGIC, getConstants().versionableVersion());
        // ViewTextItem versionDate = new ViewTextItem(DatasetDS.VERSION_DATE, getConstants().versionableVersionDate());
        // CustomDateItem nextVerstionDate = new CustomDateItem(DatasetDS.NEXT_VERSION_DATE, getConstants().versionableNextVersionDate()); // TODO what formItem should be used?
        // CustomSelectItem rationaleType = new CustomSelectItem(DatasetDS.RATIONALE_TYPE, getConstants().versionableRationaleType());
        // rationaleType.setValueMap(CommonUtils.getStatisticalResourceVersionRationaleTypeHashMap());
        // CustomTextItem rationale = new CustomTextItem(DatasetDS.RATIONALE, getConstants().versionableRationale());
        // versioningEditionForm.setFields(version, versionDate, nextVerstionDate, rationaleType, rationale);

        // Life cycle form
        // lifeCycleEditionForm = new GroupDynamicForm(getConstants().datasetLifeCycle());
        // ViewTextItem procStatus = new ViewTextItem(DatasetDS.PROC_STATUS, getConstants().lifeCycleProcStatus());
        // ViewTextItem responsabilityCreator = new ViewTextItem(DatasetDS.RESPONSABILITY_CONTRIBUTOR, getConstants().lifeCycleResponsabilityCreator());
        // ViewTextItem responsabilityContributor = new ViewTextItem(DatasetDS.RESPONSABILITY_CONTRIBUTOR, getConstants().lifeCycleResponsabilityContributor());
        // ViewTextItem responsabilitySubmitted = new ViewTextItem(DatasetDS.RESPONSABILITY_SUBMITTED, getConstants().lifeCycleResponsabilitySubmitted());
        // ViewTextItem responsabilityAccepted = new ViewTextItem(DatasetDS.RESPONSABILITY_ACCEPTED, getConstants().lifeCycleResponsabilityAccepted());
        // ViewTextItem responsabilityIssued = new ViewTextItem(DatasetDS.RESPONSABILITY_ISSUED, getConstants().lifeCycleResponsabilityIssued());
        // ViewTextItem responsabilityOutOfPrint = new ViewTextItem(DatasetDS.RESPONSABILITY_OUT_OF_PRINT, getConstants().lifeCycleResponsabilityOutOfPrint());
        //
        // SearchViewTextItem creator = createRelatedAgencyItem(DatasetDS.CREATOR, getConstants().lifeCycleCreator(), AgencyField.AGENCY_CREATOR);
        // SearchExternalListItem contributor = createRelatedMultiAgencyItem(DatasetDS.CONTRIBUTOR, getConstants().lifeCycleContributor(), AgencyField.AGENCY_CONTRIBUTOR, true);
        // SearchExternalListItem publisher = createRelatedMultiAgencyItem(DatasetDS.PUBLISHER, getConstants().lifeCyclePublisher(), AgencyField.AGENCY_PUBLISHER, true);
        // SearchExternalListItem mediator = createRelatedMultiAgencyItem(DatasetDS.MEDIATOR, getConstants().lifeCycleMediator(), AgencyField.AGENCY_MEDIATOR, true);
        //
        // ViewTextItem submittedDate = new ViewTextItem(DatasetDS.SUBMITTED_DATE, getConstants().lifeCycleSubmittedDate());
        // ViewTextItem acceptedDate = new ViewTextItem(DatasetDS.ACCEPTED_DATE, getConstants().lifeCycleAcceptedDate());
        // ViewTextItem issuedDate = new ViewTextItem(DatasetDS.ISSUED_DATE, getConstants().lifeCycleIssuedDate());
        // lifeCycleEditionForm.setFields(procStatus, responsabilityCreator, responsabilityContributor, responsabilitySubmitted, responsabilityAccepted, responsabilityIssued, responsabilityOutOfPrint,
        // creator, contributor, publisher, mediator, submittedDate, acceptedDate, issuedDate);

        // Content metadata form
        // contentMetadataEditionForm = new GroupDynamicForm(getConstants().datasetContentMetadata());
        // ViewTextItem language = new ViewTextItem(DatasetDS.LANGUAGE, getConstants().contentMetadataLanguage()); // TODO edit?
        // ViewTextItem languages = new ViewTextItem(DatasetDS.LANGUAGES, getConstants().contentMetadataLanguages()); // TODO if can be edited, what formItem should be used?
        // MultiLanguageTextAreaItem description = new MultiLanguageTextAreaItem(DatasetDS.DESCRIPTION, getConstants().contentMetadataDescription());
        // ViewTextItem keywords = new ViewTextItem(DatasetDS.KEYWORDS, getConstants().contentMetadataKeywords()); // TODO what formItem should be used?
        // ViewTextItem spatialCoverage = new ViewTextItem(DatasetDS.SPATIAL_COVERAGE, getConstants().contentMetadataSpatialCoverage()); // TODO what formItem should be used?
        // ViewTextItem spatialCoverageCodes = new ViewTextItem(DatasetDS.SPATIAL_COVERAGE_CODES, getConstants().contentMetadataSpatialCoverageCodes()); // TODO what formItem should be used?
        // ViewTextItem temporalCoverage = new ViewTextItem(DatasetDS.TEMPORAL_COVERAGE, getConstants().contentMetadataTemporalCoverage()); // TODO what formItem should be used?
        // ViewTextItem temporalCoverageCodes = new ViewTextItem(DatasetDS.TEMPORAL_COVERAGE_CODES, getConstants().contentMetadataTemporalCoverageCodes()); // TODO what formItem should be used?
        // ViewTextItem type = new ViewTextItem(DatasetDS.TYPE, getConstants().contentMetadataType());// TODO what formItem should be used?
        // ViewTextItem format = new ViewTextItem(DatasetDS.FORMAT, getConstants().contentMetadataFormat());// TODO what formItem should be used?
        // CustomDateItem nextUpdate = new CustomDateItem(DatasetDS.NEXT_UPDATE_DATE, getConstants().contentMetadataNextUpdate());// TODO what formItem should be used?
        // ViewTextItem updateFrequency = new ViewTextItem(DatasetDS.UPDATE_FREQUENCY, getConstants().contentMetadataUpdateFrequency());// TODO what formItem should be used?
        // ViewTextItem rightsHolder = new ViewTextItem(DatasetDS.RIGHTS_HOLDER, getConstants().contentMetadataRightsHolder());// TODO what formItem should be used?
        // ViewTextItem copyrightedDate = new ViewTextItem(DatasetDS.COPYRIGHTED_DATE, getConstants().contentMetadataCopyrightedDate());// TODO what formItem should be used?
        // ViewTextItem license = new ViewTextItem(DatasetDS.LICENSE, getConstants().contentMetadataLicense());// TODO what formItem should be used?
        // contentMetadataEditionForm.setFields(language, languages, description, keywords, spatialCoverage, spatialCoverageCodes, temporalCoverage, temporalCoverageCodes, type, format, nextUpdate,
        // updateFrequency, rightsHolder, copyrightedDate, license);

        // mainFormLayout.addEditionCanvas(versioningEditionForm);
        // mainFormLayout.addEditionCanvas(lifeCycleEditionForm);
        // mainFormLayout.addEditionCanvas(contentMetadataEditionForm);
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

        // Production descriptors
        productionDescriptorsForm.setDatasetDto(datasetDto);

        // Class descriptors
        classDescriptorsForm.setDatasetDto(datasetDto);

        // Resource relation descriptors
        resourceRelationDescriptorsForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Life cycle
        lifeCycleForm.setLifeCycleStatisticalResourceDto(datasetDto);

        // Version
        versionForm.setLifeCycleStatisticalResourceDto(datasetDto);

        // FIXME: add rest of metadata
        // versioningForm.setValue(DatasetDS.VERSION_LOGIC, datasetDto.getVersionLogic());
        // versioningForm.setValue(DatasetDS.VERSION_DATE, DateUtils.getFormattedDate(datasetDto.getVersionDate()));
        // // TODO change based on values taken from gpe
        // versioningForm.setValue(DatasetDS.RATIONALE_TYPE, CommonUtils.getStatisticalResourceVersionRationaleTypeName(datasetDto.getRationaleType()));
        // versioningForm.setValue(DatasetDS.RATIONALE, datasetDto.getRationale() != null ? datasetDto.getRationale() : StringUtils.EMPTY);
        // versioningForm.setValue(DatasetDS.NEXT_VERSION_DATE, datasetDto.getNextVersionDate());
        //
        // lifeCycleForm.setValue(DatasetDS.PROC_STATUS, CommonUtils.getProcStatusName(datasetDto));
        // lifeCycleForm.setValue(DatasetDS.RESPONSABILITY_CONTRIBUTOR, datasetDto.getResponsabilityContributor());
        // lifeCycleForm.setValue(DatasetDS.RESPONSABILITY_SUBMITTED, datasetDto.getResponsabilitySubmitted());
        // lifeCycleForm.setValue(DatasetDS.RESPONSABILITY_ACCEPTED, datasetDto.getResponsabilityAccepted());
        // lifeCycleForm.setValue(DatasetDS.RESPONSABILITY_ISSUED, datasetDto.getResponsabilityIssued());
        // lifeCycleForm.setValue(DatasetDS.RESPONSABILITY_OUT_OF_PRINT, datasetDto.getResponsabilityOutOfPrint());
        // lifeCycleForm.setValue(DatasetDS.CREATOR, datasetDto.getCreator() != null ? ExternalItemUtils.getExternalItemName(datasetDto.getCreator()) : null);
        //
        // ((SearchExternalListItem)lifeCycleForm.getField(DatasetDS.CONTRIBUTOR)).setExternalItems(datasetDto.getContributor());
        // ((SearchExternalListItem)lifeCycleForm.getField(DatasetDS.PUBLISHER)).setExternalItems(datasetDto.getPublisher());
        // ((SearchExternalListItem)lifeCycleForm.getField(DatasetDS.MEDIATOR)).setExternalItems(datasetDto.getMediator());
        //
        // lifeCycleForm.setValue(DatasetDS.SUBMITTED_DATE, DateUtils.getFormattedDate(datasetDto.getSubmittedDate()));
        // lifeCycleForm.setValue(DatasetDS.ACCEPTED_DATE, DateUtils.getFormattedDate(datasetDto.getAcceptedDate()));
        // lifeCycleForm.setValue(DatasetDS.ISSUED_DATE, DateUtils.getFormattedDate(datasetDto.getIssuedDate()));
        //
        // ContentMetadataDto contentMetadataDto = datasetDto.getContentMetadata() != null ? datasetDto.getContentMetadata() : new ContentMetadataDto();
        // contentMetadataForm.setValue(DatasetDS.LANGUAGE, contentMetadataDto.getLanguage());
        // contentMetadataForm.setValue(DatasetDS.LANGUAGES, contentMetadataDto.getLanguages() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getLanguages()) : null);
        // contentMetadataForm.setValue(DatasetDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(contentMetadataDto.getDescription()));
        // contentMetadataForm.setValue(DatasetDS.KEYWORDS, contentMetadataDto.getKeywords() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getKeywords()) : null);
        // contentMetadataForm
        // .setValue(DatasetDS.SPATIAL_COVERAGE, contentMetadataDto.getSpatialCoverage() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverage()) : null);
        // contentMetadataForm.setValue(DatasetDS.SPATIAL_COVERAGE_CODES,
        // contentMetadataDto.getSpatialCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverageCodes()) : null);
        // contentMetadataForm.setValue(DatasetDS.TEMPORAL_COVERAGE, contentMetadataDto.getTemporalCoverage() != null
        // ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverage())
        // : null);
        // contentMetadataForm.setValue(DatasetDS.TEMPORAL_COVERAGE_CODES,
        // contentMetadataDto.getTemporalCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverageCodes()) : null);
        // contentMetadataForm.setValue(DatasetDS.TYPE, CommonUtils.getStatisticalResourceTypeName(contentMetadataDto.getType()));
        // contentMetadataForm.setValue(DatasetDS.FORMAT, CommonUtils.getStatisticalResourceFormatName(contentMetadataDto.getFormat()));
        // contentMetadataForm.setValue(DatasetDS.NEXT_UPDATE_DATE, contentMetadataDto.getNextUpdateDate());
        // contentMetadataForm.setValue(DatasetDS.UPDATE_FREQUENCY, contentMetadataDto.getUpdateFrequency());
        // contentMetadataForm.setValue(DatasetDS.RIGHTS_HOLDER, contentMetadataDto.getRightsHolder());
        // contentMetadataForm.setValue(DatasetDS.COPYRIGHTED_DATE, DateUtils.getFormattedDate(contentMetadataDto.getCopyrightedDate()));
        // contentMetadataForm.setValue(DatasetDS.LICENSE, contentMetadataDto.getLicense());
    }

    private void setDatasetEditionMode(DatasetDto datasetDto) {
        // Identifiers form
        identifiersEditionForm.setNameableStatisticalResourceDto(datasetDto);

        // Production descriptors
        productionDescriptorsEditionForm.setDatasetDto(datasetDto);

        // Class descriptors
        classDescriptorsEditionForm.setDatasetDto(datasetDto);

        // Resource relation descriptors
        resourceRelationDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Life cycle
        lifeCycleEditionForm.setLifeCycleStatisticalResourceDto(datasetDto);

        // Version
        versionEditionForm.setLifeCycleStatisticalResourceDto(datasetDto);

        // FIXME: add more metadatas
        // // Version form
        // versioningEditionForm.setValue(DatasetDS.VERSION_LOGIC, datasetDto.getVersionLogic());
        // versioningEditionForm.setValue(DatasetDS.VERSION_DATE, DateUtils.getFormattedDate(datasetDto.getVersionDate()));
        // versioningEditionForm.setValue(DatasetDS.NEXT_VERSION_DATE, datasetDto.getNextVersionDate());
        // versioningEditionForm.setValue(DatasetDS.RATIONALE_TYPE, datasetDto.getRationaleType() != null ? datasetDto.getRationaleType().name() : StringUtils.EMPTY);
        // versioningEditionForm.setValue(DatasetDS.RATIONALE, datasetDto.getRationale());
        //
        // // Life cycle form
        // lifeCycleEditionForm.setValue(DatasetDS.PROC_STATUS, CommonUtils.getProcStatusName(datasetDto));
        // lifeCycleEditionForm.setValue(DatasetDS.RESPONSABILITY_CONTRIBUTOR, datasetDto.getResponsabilityContributor());
        // lifeCycleEditionForm.setValue(DatasetDS.RESPONSABILITY_SUBMITTED, datasetDto.getResponsabilitySubmitted());
        // lifeCycleEditionForm.setValue(DatasetDS.RESPONSABILITY_ACCEPTED, datasetDto.getResponsabilityAccepted());
        // lifeCycleEditionForm.setValue(DatasetDS.RESPONSABILITY_ISSUED, datasetDto.getResponsabilityIssued());
        // lifeCycleEditionForm.setValue(DatasetDS.RESPONSABILITY_OUT_OF_PRINT, datasetDto.getResponsabilityOutOfPrint());
        //
        // ((SearchExternalViewTextItem)lifeCycleEditionForm.getField(DatasetDS.CREATOR)).setExternalItem(datasetDto.getCreator());
        // ((SearchExternalListItem)lifeCycleEditionForm.getField(DatasetDS.CONTRIBUTOR)).setExternalItems(new ArrayList<ExternalItemDto>(datasetDto.getContributor()));
        // ((SearchExternalListItem)lifeCycleEditionForm.getField(DatasetDS.PUBLISHER)).setExternalItems(new ArrayList<ExternalItemDto>(datasetDto.getPublisher()));
        // ((SearchExternalListItem)lifeCycleEditionForm.getField(DatasetDS.MEDIATOR)).setExternalItems(new ArrayList<ExternalItemDto>(datasetDto.getMediator()));
        //
        // lifeCycleEditionForm.setValue(DatasetDS.SUBMITTED_DATE, DateUtils.getFormattedDate(datasetDto.getSubmittedDate()));
        // lifeCycleEditionForm.setValue(DatasetDS.ACCEPTED_DATE, DateUtils.getFormattedDate(datasetDto.getAcceptedDate()));
        // lifeCycleEditionForm.setValue(DatasetDS.ISSUED_DATE, DateUtils.getFormattedDate(datasetDto.getIssuedDate()));
        //
        // // Content metadata form
        // ContentMetadataDto contentMetadataDto = datasetDto.getContentMetadata() != null ? datasetDto.getContentMetadata() : new ContentMetadataDto();
        // contentMetadataEditionForm.setValue(DatasetDS.LANGUAGE, contentMetadataDto.getLanguage());
        // contentMetadataEditionForm.setValue(DatasetDS.LANGUAGES, contentMetadataDto.getLanguages() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getLanguages()) : null);
        // contentMetadataEditionForm.setValue(DatasetDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(contentMetadataDto.getDescription()));
        // contentMetadataEditionForm.setValue(DatasetDS.KEYWORDS, contentMetadataDto.getKeywords() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getKeywords()) : null);
        // contentMetadataEditionForm.setValue(DatasetDS.SPATIAL_COVERAGE, contentMetadataDto.getSpatialCoverage() != null
        // ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverage())
        // : null);
        // contentMetadataEditionForm.setValue(DatasetDS.SPATIAL_COVERAGE_CODES,
        // contentMetadataDto.getSpatialCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverageCodes()) : null);
        // contentMetadataEditionForm.setValue(DatasetDS.TEMPORAL_COVERAGE,
        // contentMetadataDto.getTemporalCoverage() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverage()) : null);
        // contentMetadataEditionForm.setValue(DatasetDS.TEMPORAL_COVERAGE_CODES,
        // contentMetadataDto.getTemporalCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverageCodes()) : null);
        // contentMetadataEditionForm.setValue(DatasetDS.TYPE, CommonUtils.getStatisticalResourceTypeName(contentMetadataDto.getType()));
        // contentMetadataEditionForm.setValue(DatasetDS.FORMAT, CommonUtils.getStatisticalResourceFormatName(contentMetadataDto.getFormat()));
        // contentMetadataEditionForm.setValue(DatasetDS.NEXT_UPDATE_DATE, DateUtils.getFormattedDate(contentMetadataDto.getNextUpdateDate()));
        // contentMetadataEditionForm.setValue(DatasetDS.UPDATE_FREQUENCY, contentMetadataDto.getUpdateFrequency());
        // contentMetadataEditionForm.setValue(DatasetDS.RIGHTS_HOLDER, contentMetadataDto.getRightsHolder());
        // contentMetadataEditionForm.setValue(DatasetDS.COPYRIGHTED_DATE, DateUtils.getFormattedDate(contentMetadataDto.getCopyrightedDate()));
        // contentMetadataEditionForm.setValue(DatasetDS.LICENSE, contentMetadataDto.getLicense());
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

        // Production descriptors
        datasetDto = productionDescriptorsEditionForm.getDatasetDto(datasetDto);

        // Class descriptors
        datasetDto = classDescriptorsEditionForm.getDatasetDto(datasetDto);

        // Resource relation descriptors
        datasetDto = (DatasetDto) resourceRelationDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(datasetDto);

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
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(DatasetMetadataTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
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
