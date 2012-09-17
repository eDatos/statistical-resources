package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.ContentMetadataDto;
import org.siemac.metamac.statistical.resources.core.dto.DataSetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetMetadataTabPresenter.DatasetMetadataTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.DatasetMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.ProgramPublicationWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.VersionWindow;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.DateUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextAreaItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;


public class DatasetMetadataTabViewImpl extends ViewImpl implements DatasetMetadataTabView {

    private DatasetMetadataTabUiHandlers uiHandlers;
    private VLayout panel;
    private DatasetMainFormLayout mainFormLayout;

    private GroupDynamicForm      identifiersForm;
    private GroupDynamicForm      contentMetadataForm;
    private GroupDynamicForm      versioningForm;
    private GroupDynamicForm      lifeCycleForm;
    
    private GroupDynamicForm      identifiersEditionForm;
    private GroupDynamicForm      contentMetadataEditionForm;
    private GroupDynamicForm      versioningEditionForm;
    private GroupDynamicForm      lifeCycleEditionForm;

    private DataSetDto            datasetDto;
    
    public DatasetMetadataTabViewImpl() {
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.SCROLL);

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
                versioningForm.setTranslationsShowed(translationsShowed);
                contentMetadataForm.setTranslationsShowed(translationsShowed);
                lifeCycleForm.setTranslationsShowed(translationsShowed);
            }
        });
        
        // Save
        mainFormLayout.getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (identifiersEditionForm.validate(false) && versioningEditionForm.validate(false) && lifeCycleEditionForm.validate(false) && contentMetadataEditionForm.validate(false)) {
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
        mainFormLayout.getPendingPublicationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.sendToPendingPublication(datasetDto.getUrn(), datasetDto.getProcStatus());
            }
        });
        mainFormLayout.getProgramPublicationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                final ProgramPublicationWindow window = new ProgramPublicationWindow(getConstants().lifeCycleProgramPublication());
                window.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        if (window.validateForm()) {
                            // TODO Send to date and hour selected to service
                            uiHandlers.programPublication(datasetDto.getUrn(), datasetDto.getProcStatus());
                            window.destroy();
                        }
                    }
                });
            }
        });
        mainFormLayout.getCancelProgrammedPublication().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.cancelProgrammedPublication(datasetDto.getUrn(), datasetDto.getProcStatus());
            }
        });
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
        mainFormLayout.getArchiveButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.archive(datasetDto.getUrn(), datasetDto.getProcStatus());
            }
        });
    }
    
    private void createViewForm() {
        // Identifiers Form
        identifiersForm = new GroupDynamicForm(getConstants().datasetIdentifiers());
        ViewTextItem identifier = new ViewTextItem(DatasetDS.IDENTIFIER, getConstants().datasetIdentifier());
        ViewMultiLanguageTextItem title = new ViewMultiLanguageTextItem(DatasetDS.TITLE, getConstants().datasetTitle());
        ViewTextItem uri = new ViewTextItem(DatasetDS.URI, getConstants().datasetUri());
        ViewTextItem urn = new ViewTextItem(DatasetDS.URN, getConstants().datasetUrn());
        identifiersForm.setFields(identifier, title, uri, urn);

        // version
        versioningForm = new GroupDynamicForm(getConstants().datasetVersioning());
        ViewTextItem version = new ViewTextItem(DatasetDS.VERSION_LOGIC, getConstants().datasetVersion());
        ViewTextItem dateVersion = new ViewTextItem(DatasetDS.VERSION_DATE, getConstants().datasetVersionDate());
        ViewTextItem nextVersion = new ViewTextItem(DatasetDS.NEXT_VERSION_DATE, getConstants().datasetNextVersion());
        ViewTextItem rationale = new ViewTextItem(DatasetDS.RATIONALE, getConstants().datasetRationale());
        ViewTextItem rationaleType = new ViewTextItem(DatasetDS.RATIONALE_TYPE, getConstants().datasetRationaleType());
        versioningForm.setFields(version, dateVersion, nextVersion, rationaleType, rationale);
        
        // Life cycle form
        lifeCycleForm = new GroupDynamicForm(getConstants().datasetLifeCycle());
        ViewTextItem procStatus = new ViewTextItem(DatasetDS.PROC_STATUS, getConstants().lifeCycleProcStatus());
        ViewTextItem responsabilityContributor = new ViewTextItem(DatasetDS.RESPONSABILITY_CONTRIBUTOR, getConstants().lifeCycleResponsabilityContributor());
        ViewTextItem responsabilitySubmitted = new ViewTextItem(DatasetDS.RESPONSABILITY_SUBMITTED, getConstants().lifeCycleResponsabilitySubmitted());
        ViewTextItem responsabilityAccepted = new ViewTextItem(DatasetDS.RESPONSABILITY_ACCEPTED, getConstants().lifeCycleResponsabilityAccepted());
        ViewTextItem responsabilityIssued = new ViewTextItem(DatasetDS.RESPONSABILITY_ISSUED, getConstants().lifeCycleResponsabilityIssued());
        ViewTextItem responsabilityOutOfPrint = new ViewTextItem(DatasetDS.RESPONSABILITY_OUT_OF_PRINT, getConstants().lifeCycleResponsabilityOutOfPrint());
        ViewTextItem creator = new ViewTextItem(DatasetDS.CREATOR, getConstants().lifeCycleCreator());
        ViewTextItem contributor = new ViewTextItem(DatasetDS.CONTRIBUTOR, getConstants().lifeCycleContributor());
        ViewTextItem publisher = new ViewTextItem(DatasetDS.PUBLISHER, getConstants().lifeCyclePublisher());
        ViewTextItem mediator = new ViewTextItem(DatasetDS.MEDIATOR, getConstants().lifeCycleMediator());
        ViewTextItem submittedDate = new ViewTextItem(DatasetDS.SUBMITTED_DATE, getConstants().lifeCycleSubmittedDate());
        ViewTextItem acceptedDate = new ViewTextItem(DatasetDS.ACCEPTED_DATE, getConstants().lifeCycleAcceptedDate());
        ViewTextItem issuedDate = new ViewTextItem(DatasetDS.ISSUED_DATE, getConstants().lifeCycleIssuedDate());
        lifeCycleForm.setFields(procStatus, responsabilityContributor, responsabilitySubmitted, responsabilityAccepted, responsabilityIssued, responsabilityOutOfPrint, creator, contributor,
                publisher, mediator, submittedDate, acceptedDate, issuedDate);
        
     // Content metadata form
        contentMetadataForm = new GroupDynamicForm(getConstants().datasetContentMetadata());
        ViewTextItem language = new ViewTextItem(DatasetDS.LANGUAGE, getConstants().contentMetadataLanguage());
        ViewTextItem languages = new ViewTextItem(DatasetDS.LANGUAGES, getConstants().contentMetadataLanguages());
        ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(DatasetDS.DESCRIPTION, getConstants().contentMetadataDescription());
        ViewTextItem keywords = new ViewTextItem(DatasetDS.KEYWORDS, getConstants().contentMetadataKeywords());
        ViewTextItem spatialCoverage = new ViewTextItem(DatasetDS.SPATIAL_COVERAGE, getConstants().contentMetadataSpatialCoverage());
        ViewTextItem spatialCoverageCodes = new ViewTextItem(DatasetDS.SPATIAL_COVERAGE_CODES, getConstants().contentMetadataSpatialCoverageCodes());
        ViewTextItem temporalCoverage = new ViewTextItem(DatasetDS.TEMPORAL_COVERAGE, getConstants().contentMetadataTemporalCoverage());
        ViewTextItem temporalCoverageCodes = new ViewTextItem(DatasetDS.TEMPORAL_COVERAGE_CODES, getConstants().contentMetadataTemporalCoverageCodes());
        ViewTextItem type = new ViewTextItem(DatasetDS.TYPE, getConstants().contentMetadataType());
        ViewTextItem format = new ViewTextItem(DatasetDS.FORMAT, getConstants().contentMetadataFormat());
        ViewTextItem nextUpdate = new ViewTextItem(DatasetDS.NEXT_UPDATE_DATE, getConstants().contentMetadataNextUpdate());
        ViewTextItem updateFrequency = new ViewTextItem(DatasetDS.UPDATE_FREQUENCY, getConstants().contentMetadataUpdateFrequency());
        ViewTextItem rightsHolder = new ViewTextItem(DatasetDS.RIGHTS_HOLDER, getConstants().contentMetadataRightsHolder());
        ViewTextItem copyrightedDate = new ViewTextItem(DatasetDS.COPYRIGHTED_DATE, getConstants().contentMetadataCopyrightedDate());
        ViewTextItem license = new ViewTextItem(DatasetDS.LICENSE, getConstants().contentMetadataLicense());
        contentMetadataForm.setFields(language, languages, description, keywords, spatialCoverage, spatialCoverageCodes, temporalCoverage, temporalCoverageCodes, type, format, nextUpdate,
                updateFrequency, rightsHolder, copyrightedDate, license);

        mainFormLayout.addViewCanvas(identifiersForm);
        mainFormLayout.addViewCanvas(versioningForm);
        mainFormLayout.addViewCanvas(lifeCycleForm);
        mainFormLayout.addViewCanvas(contentMetadataForm);
    }
    
    private void createEditionForm() {
        identifiersEditionForm = new GroupDynamicForm(getConstants().datasetIdentifiers());
        RequiredTextItem identifier = new RequiredTextItem(DatasetDS.IDENTIFIER, getConstants().datasetIdentifier());
        identifier.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        MultiLanguageTextItem title = new MultiLanguageTextItem(DatasetDS.TITLE, getConstants().datasetTitle());
        title.setRequired(true);
        ViewTextItem uri = new ViewTextItem(DatasetDS.URI, getConstants().datasetUri());
        ViewTextItem urn = new ViewTextItem(DatasetDS.URN, getConstants().datasetUrn());
        identifiersEditionForm.setFields(identifier, title, uri, urn);

        // Version form
        versioningEditionForm = new GroupDynamicForm(getConstants().versionableVersion());
        ViewTextItem version = new ViewTextItem(DatasetDS.VERSION_LOGIC, getConstants().versionableVersion());
        ViewTextItem versionDate = new ViewTextItem(DatasetDS.VERSION_DATE, getConstants().versionableVersionDate());
        ViewTextItem nextVerstionDate = new ViewTextItem(DatasetDS.NEXT_VERSION_DATE, getConstants().versionableNextVersionDate()); // TODO what formItem should be used?
        ViewTextItem rationaleType = new ViewTextItem(DatasetDS.RATIONALE_TYPE, getConstants().versionableRationaleType());
        CustomTextItem rationale = new CustomTextItem(DatasetDS.RATIONALE, getConstants().versionableRationale());
        versioningEditionForm.setFields(version, versionDate, nextVerstionDate, rationaleType, rationale);

        // Life cycle form
        lifeCycleEditionForm = new GroupDynamicForm(getConstants().datasetLifeCycle());
        ViewTextItem procStatus = new ViewTextItem(DatasetDS.PROC_STATUS, getConstants().lifeCycleProcStatus());
        ViewTextItem responsabilityContributor = new ViewTextItem(DatasetDS.RESPONSABILITY_CONTRIBUTOR, getConstants().lifeCycleResponsabilityContributor());
        ViewTextItem responsabilitySubmitted = new ViewTextItem(DatasetDS.RESPONSABILITY_SUBMITTED, getConstants().lifeCycleResponsabilitySubmitted());
        ViewTextItem responsabilityAccepted = new ViewTextItem(DatasetDS.RESPONSABILITY_ACCEPTED, getConstants().lifeCycleResponsabilityAccepted());
        ViewTextItem responsabilityIssued = new ViewTextItem(DatasetDS.RESPONSABILITY_ISSUED, getConstants().lifeCycleResponsabilityIssued());
        ViewTextItem responsabilityOutOfPrint = new ViewTextItem(DatasetDS.RESPONSABILITY_OUT_OF_PRINT, getConstants().lifeCycleResponsabilityOutOfPrint());
        ViewTextItem creator = new ViewTextItem(DatasetDS.CREATOR, getConstants().lifeCycleCreator());
        ViewTextItem contributor = new ViewTextItem(DatasetDS.CONTRIBUTOR, getConstants().lifeCycleContributor());
        ViewTextItem publisher = new ViewTextItem(DatasetDS.PUBLISHER, getConstants().lifeCyclePublisher());
        ViewTextItem mediator = new ViewTextItem(DatasetDS.MEDIATOR, getConstants().lifeCycleMediator());
        ViewTextItem submittedDate = new ViewTextItem(DatasetDS.SUBMITTED_DATE, getConstants().lifeCycleSubmittedDate());
        ViewTextItem acceptedDate = new ViewTextItem(DatasetDS.ACCEPTED_DATE, getConstants().lifeCycleAcceptedDate());
        ViewTextItem issuedDate = new ViewTextItem(DatasetDS.ISSUED_DATE, getConstants().lifeCycleIssuedDate());
        lifeCycleEditionForm.setFields(procStatus, responsabilityContributor, responsabilitySubmitted, responsabilityAccepted, responsabilityIssued, responsabilityOutOfPrint, creator, contributor,
                publisher, mediator, submittedDate, acceptedDate, issuedDate);

        // Content metadata form
        contentMetadataEditionForm = new GroupDynamicForm(getConstants().datasetContentMetadata());
        ViewTextItem language = new ViewTextItem(DatasetDS.LANGUAGE, getConstants().contentMetadataLanguage()); // TODO edit?
        ViewTextItem languages = new ViewTextItem(DatasetDS.LANGUAGES, getConstants().contentMetadataLanguages()); // TODO if can be edited, what formItem should be used?
        MultiLanguageTextAreaItem description = new MultiLanguageTextAreaItem(DatasetDS.DESCRIPTION, getConstants().contentMetadataDescription());
        ViewTextItem keywords = new ViewTextItem(DatasetDS.KEYWORDS, getConstants().contentMetadataKeywords()); // TODO what formItem should be used?
        ViewTextItem spatialCoverage = new ViewTextItem(DatasetDS.SPATIAL_COVERAGE, getConstants().contentMetadataSpatialCoverage()); // TODO what formItem should be used?
        ViewTextItem spatialCoverageCodes = new ViewTextItem(DatasetDS.SPATIAL_COVERAGE_CODES, getConstants().contentMetadataSpatialCoverageCodes()); // TODO what formItem should be used?
        ViewTextItem temporalCoverage = new ViewTextItem(DatasetDS.TEMPORAL_COVERAGE, getConstants().contentMetadataTemporalCoverage()); // TODO what formItem should be used?
        ViewTextItem temporalCoverageCodes = new ViewTextItem(DatasetDS.TEMPORAL_COVERAGE_CODES, getConstants().contentMetadataTemporalCoverageCodes()); // TODO what formItem should be used?
        ViewTextItem type = new ViewTextItem(DatasetDS.TYPE, getConstants().contentMetadataType());// TODO what formItem should be used?
        ViewTextItem format = new ViewTextItem(DatasetDS.FORMAT, getConstants().contentMetadataFormat());// TODO what formItem should be used?
        ViewTextItem nextUpdate = new ViewTextItem(DatasetDS.NEXT_UPDATE_DATE, getConstants().contentMetadataNextUpdate());// TODO what formItem should be used?
        ViewTextItem updateFrequency = new ViewTextItem(DatasetDS.UPDATE_FREQUENCY, getConstants().contentMetadataUpdateFrequency());// TODO what formItem should be used?
        ViewTextItem rightsHolder = new ViewTextItem(DatasetDS.RIGHTS_HOLDER, getConstants().contentMetadataRightsHolder());// TODO what formItem should be used?
        ViewTextItem copyrightedDate = new ViewTextItem(DatasetDS.COPYRIGHTED_DATE, getConstants().contentMetadataCopyrightedDate());// TODO what formItem should be used?
        ViewTextItem license = new ViewTextItem(DatasetDS.LICENSE, getConstants().contentMetadataLicense());// TODO what formItem should be used?
        contentMetadataEditionForm.setFields(language, languages, description, keywords, spatialCoverage, spatialCoverageCodes, temporalCoverage, temporalCoverageCodes, type, format, nextUpdate,
                updateFrequency, rightsHolder, copyrightedDate, license);

        mainFormLayout.addEditionCanvas(identifiersEditionForm);
        mainFormLayout.addEditionCanvas(versioningEditionForm);
        mainFormLayout.addEditionCanvas(lifeCycleEditionForm);
        mainFormLayout.addEditionCanvas(contentMetadataEditionForm);
    }
    
    @Override
    public void setDataset(DataSetDto datasetDto) {
        this.datasetDto = datasetDto;

        mainFormLayout.updatePublishSection(datasetDto.getProcStatus());

        mainFormLayout.setViewMode();

        setDatasetViewMode(datasetDto);
        setDatasetEditionMode(datasetDto);
    }

    private void setDatasetViewMode(DataSetDto datasetDto) {
        // Identifiers
        identifiersForm.setValue(DatasetDS.IDENTIFIER, datasetDto.getIdentifier());
        identifiersForm.setValue(DatasetDS.URI, datasetDto.getUri());
        identifiersForm.setValue(DatasetDS.URN, datasetDto.getUrn());
        identifiersForm.setValue(DatasetDS.TITLE, RecordUtils.getInternationalStringRecord(datasetDto.getTitle()));

        versioningForm.setValue(DatasetDS.VERSION_LOGIC, datasetDto.getVersionLogic());
        versioningForm.setValue(DatasetDS.VERSION_DATE, DateUtils.getFormattedDate(datasetDto.getVersionDate()));
        // TODO change based on values taken from gpe
        versioningForm.setValue(DatasetDS.RATIONALE_TYPE, datasetDto.getRationaleType() != null ? datasetDto.getRationaleType().name() : StringUtils.EMPTY);
        versioningForm.setValue(DatasetDS.RATIONALE, datasetDto.getRationale() != null ? datasetDto.getRationale() : StringUtils.EMPTY);
        versioningForm.setValue(DatasetDS.VERSION_DATE, DateUtils.getFormattedDate(datasetDto.getVersionDate()));
        versioningForm.setValue(DatasetDS.NEXT_VERSION_DATE, DateUtils.getFormattedDate(datasetDto.getNextVersionDate()));
        
        lifeCycleForm.setValue(DatasetDS.PROC_STATUS, CommonUtils.getProcStatusName(datasetDto));
        lifeCycleForm.setValue(DatasetDS.RESPONSABILITY_CONTRIBUTOR, datasetDto.getResponsabilityContributor());
        lifeCycleForm.setValue(DatasetDS.RESPONSABILITY_SUBMITTED, datasetDto.getResponsabilitySubmitted());
        lifeCycleForm.setValue(DatasetDS.RESPONSABILITY_ACCEPTED, datasetDto.getResponsabilityAccepted());
        lifeCycleForm.setValue(DatasetDS.RESPONSABILITY_ISSUED, datasetDto.getResponsabilityIssued());
        lifeCycleForm.setValue(DatasetDS.RESPONSABILITY_OUT_OF_PRINT, datasetDto.getResponsabilityOutOfPrint());
        lifeCycleForm.setValue(DatasetDS.CREATOR, datasetDto.getCreator());
        lifeCycleForm.setValue(DatasetDS.CONTRIBUTOR, datasetDto.getContributor());
        lifeCycleForm.setValue(DatasetDS.PUBLISHER, datasetDto.getPublisher());
        lifeCycleForm.setValue(DatasetDS.MEDIATOR, datasetDto.getMediator());
        lifeCycleForm.setValue(DatasetDS.SUBMITTED_DATE, DateUtils.getFormattedDate(datasetDto.getSubmittedDate()));
        lifeCycleForm.setValue(DatasetDS.ACCEPTED_DATE, DateUtils.getFormattedDate(datasetDto.getAcceptedDate()));
        lifeCycleForm.setValue(DatasetDS.ISSUED_DATE, DateUtils.getFormattedDate(datasetDto.getIssuedDate()));
        
        ContentMetadataDto contentMetadataDto = datasetDto.getContentMetadata() != null ? datasetDto.getContentMetadata() : new ContentMetadataDto();
        contentMetadataForm.setValue(DatasetDS.LANGUAGE, contentMetadataDto.getLanguage());
        contentMetadataForm.setValue(DatasetDS.LANGUAGES, contentMetadataDto.getLanguages() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getLanguages()) : null);
        contentMetadataForm.setValue(DatasetDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(contentMetadataDto.getDescription()));
        contentMetadataForm.setValue(DatasetDS.KEYWORDS, contentMetadataDto.getKeywords() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getKeywords()) : null);
        contentMetadataForm.setValue(DatasetDS.SPATIAL_COVERAGE, contentMetadataDto.getSpatialCoverage() != null
                ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverage())
                : null);
        contentMetadataForm.setValue(DatasetDS.SPATIAL_COVERAGE_CODES,
                contentMetadataDto.getSpatialCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverageCodes()) : null);
        contentMetadataForm.setValue(DatasetDS.TEMPORAL_COVERAGE, contentMetadataDto.getTemporalCoverage() != null
                ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverage())
                : null);
        contentMetadataForm.setValue(DatasetDS.TEMPORAL_COVERAGE_CODES,
                contentMetadataDto.getTemporalCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverageCodes()) : null);
        contentMetadataForm.setValue(DatasetDS.TYPE, CommonUtils.getStatisticalResourceTypeName(contentMetadataDto.getType()));
        contentMetadataForm.setValue(DatasetDS.FORMAT, CommonUtils.getStatisticalResourceFormatName(contentMetadataDto.getFormat()));
        contentMetadataForm.setValue(DatasetDS.NEXT_UPDATE_DATE, DateUtils.getFormattedDate(contentMetadataDto.getNextUpdateDate()));
        contentMetadataForm.setValue(DatasetDS.UPDATE_FREQUENCY, contentMetadataDto.getUpdateFrequency());
        contentMetadataForm.setValue(DatasetDS.RIGHTS_HOLDER, contentMetadataDto.getRightsHolder());
        contentMetadataForm.setValue(DatasetDS.COPYRIGHTED_DATE, DateUtils.getFormattedDate(contentMetadataDto.getCopyrightedDate()));
        contentMetadataForm.setValue(DatasetDS.LICENSE, contentMetadataDto.getLicense());
    }
    
    private void setDatasetEditionMode(DataSetDto datasetDto) {
        // Identifiers form
        identifiersEditionForm.setValue(DatasetDS.IDENTIFIER, datasetDto.getIdentifier());
        identifiersEditionForm.setValue(DatasetDS.TITLE, RecordUtils.getInternationalStringRecord(datasetDto.getTitle()));
        identifiersEditionForm.setValue(DatasetDS.URI, datasetDto.getUri());
        identifiersEditionForm.setValue(DatasetDS.URN, datasetDto.getUrn());

        // Version form
        versioningEditionForm.setValue(DatasetDS.VERSION_LOGIC, datasetDto.getVersionLogic());
        versioningEditionForm.setValue(DatasetDS.VERSION_DATE, DateUtils.getFormattedDate(datasetDto.getVersionDate()));
        versioningEditionForm.setValue(DatasetDS.NEXT_VERSION_DATE, DateUtils.getFormattedDate(datasetDto.getNextVersionDate()));
        versioningEditionForm.setValue(DatasetDS.RATIONALE_TYPE, CommonUtils.getStatisticalResourceVersionRationaleTypeName(datasetDto.getRationaleType()));
        versioningEditionForm.setValue(DatasetDS.RATIONALE, datasetDto.getRationale());

        // Life cycle form
        lifeCycleEditionForm.setValue(DatasetDS.PROC_STATUS, CommonUtils.getProcStatusName(datasetDto));
        lifeCycleEditionForm.setValue(DatasetDS.RESPONSABILITY_CONTRIBUTOR, datasetDto.getResponsabilityContributor());
        lifeCycleEditionForm.setValue(DatasetDS.RESPONSABILITY_SUBMITTED, datasetDto.getResponsabilitySubmitted());
        lifeCycleEditionForm.setValue(DatasetDS.RESPONSABILITY_ACCEPTED, datasetDto.getResponsabilityAccepted());
        lifeCycleEditionForm.setValue(DatasetDS.RESPONSABILITY_ISSUED, datasetDto.getResponsabilityIssued());
        lifeCycleEditionForm.setValue(DatasetDS.RESPONSABILITY_OUT_OF_PRINT, datasetDto.getResponsabilityOutOfPrint());
        lifeCycleEditionForm.setValue(DatasetDS.CREATOR, datasetDto.getCreator());
        lifeCycleEditionForm.setValue(DatasetDS.CONTRIBUTOR, datasetDto.getContributor());
        lifeCycleEditionForm.setValue(DatasetDS.PUBLISHER, datasetDto.getPublisher());
        lifeCycleEditionForm.setValue(DatasetDS.MEDIATOR, datasetDto.getMediator());
        lifeCycleEditionForm.setValue(DatasetDS.SUBMITTED_DATE, DateUtils.getFormattedDate(datasetDto.getSubmittedDate()));
        lifeCycleEditionForm.setValue(DatasetDS.ACCEPTED_DATE, DateUtils.getFormattedDate(datasetDto.getAcceptedDate()));
        lifeCycleEditionForm.setValue(DatasetDS.ISSUED_DATE, DateUtils.getFormattedDate(datasetDto.getIssuedDate()));

        // Content metadata form
        ContentMetadataDto contentMetadataDto = datasetDto.getContentMetadata() != null ? datasetDto.getContentMetadata() : new ContentMetadataDto();
        contentMetadataEditionForm.setValue(DatasetDS.LANGUAGE, contentMetadataDto.getLanguage());
        contentMetadataEditionForm.setValue(DatasetDS.LANGUAGES, contentMetadataDto.getLanguages() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getLanguages()) : null);
        contentMetadataEditionForm.setValue(DatasetDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(contentMetadataDto.getDescription()));
        contentMetadataEditionForm.setValue(DatasetDS.KEYWORDS, contentMetadataDto.getKeywords() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getKeywords()) : null);
        contentMetadataEditionForm.setValue(DatasetDS.SPATIAL_COVERAGE,
                contentMetadataDto.getSpatialCoverage() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverage()) : null);
        contentMetadataEditionForm.setValue(DatasetDS.SPATIAL_COVERAGE_CODES,
                contentMetadataDto.getSpatialCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverageCodes()) : null);
        contentMetadataEditionForm.setValue(DatasetDS.TEMPORAL_COVERAGE,
                contentMetadataDto.getTemporalCoverage() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverage()) : null);
        contentMetadataEditionForm.setValue(DatasetDS.TEMPORAL_COVERAGE_CODES,
                contentMetadataDto.getTemporalCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverageCodes()) : null);
        contentMetadataEditionForm.setValue(DatasetDS.TYPE, CommonUtils.getStatisticalResourceTypeName(contentMetadataDto.getType()));
        contentMetadataEditionForm.setValue(DatasetDS.FORMAT, CommonUtils.getStatisticalResourceFormatName(contentMetadataDto.getFormat()));
        contentMetadataEditionForm.setValue(DatasetDS.NEXT_UPDATE_DATE, DateUtils.getFormattedDate(contentMetadataDto.getNextUpdateDate()));
        contentMetadataEditionForm.setValue(DatasetDS.UPDATE_FREQUENCY, contentMetadataDto.getUpdateFrequency());
        contentMetadataEditionForm.setValue(DatasetDS.RIGHTS_HOLDER, contentMetadataDto.getRightsHolder());
        contentMetadataEditionForm.setValue(DatasetDS.COPYRIGHTED_DATE, DateUtils.getFormattedDate(contentMetadataDto.getCopyrightedDate()));
        contentMetadataEditionForm.setValue(DatasetDS.LICENSE, contentMetadataDto.getLicense());
    }

    
    public DataSetDto getDatasetDto() {
     // Identifiers form
        datasetDto.setIdentifier(identifiersEditionForm.getValueAsString(DatasetDS.IDENTIFIER));
        datasetDto.setTitle((InternationalStringDto) identifiersEditionForm.getValue(DatasetDS.TITLE));

        // Version form
        datasetDto.setRationale(versioningEditionForm.getValueAsString(DatasetDS.RATIONALE));

        // Life cycle form

        // Content metadata form
        if (datasetDto.getContentMetadata() == null) {
            datasetDto.setContentMetadata(new ContentMetadataDto());
        }
        datasetDto.getContentMetadata().setDescription((InternationalStringDto) contentMetadataEditionForm.getValue(DatasetDS.DESCRIPTION));

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
}
