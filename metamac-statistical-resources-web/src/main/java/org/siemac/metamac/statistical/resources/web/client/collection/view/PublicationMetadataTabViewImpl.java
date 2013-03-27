package org.siemac.metamac.statistical.resources.web.client.collection.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.core.dto.ContentMetadataDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.collection.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.PublicationMetadataTabPresenter.CollectionMetadataTabView;
import org.siemac.metamac.statistical.resources.web.client.collection.utils.PublicationClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.collection.view.handlers.PublicationMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.collection.widgets.PublicationMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.ProgramPublicationWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.VersionWindow;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListResult;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.DateUtils;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.SearchExternalItemWindow;
import org.siemac.metamac.web.common.client.widgets.SearchMultipleExternalItemWindow;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;
import org.siemac.metamac.web.common.client.widgets.actions.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextAreaItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalListItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.VLayout;


public class PublicationMetadataTabViewImpl extends ViewImpl implements CollectionMetadataTabView {

    private PublicationMetadataTabUiHandlers uiHandlers;
    private VLayout                         panel;

    private PublicationMainFormLayout        mainFormLayout;

    private GroupDynamicForm                identifiersForm;
    private GroupDynamicForm                versionForm;
    private GroupDynamicForm                lifeCycleForm;
    private GroupDynamicForm                contentMetadataForm;

    private GroupDynamicForm                identifiersEditionForm;
    private GroupDynamicForm                versionEditionForm;
    private GroupDynamicForm                lifeCycleEditionForm;
    private GroupDynamicForm                contentMetadataEditionForm;
    
    private SearchExternalItemWindow         searchAgencyWindow;
    private SearchMultipleExternalItemWindow searchMultiAgencyWindow;

    private CollectionDto                   collectionDto;
    
    @Inject
    public PublicationMetadataTabViewImpl() {
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.SCROLL);
        
        mainFormLayout = new PublicationMainFormLayout(PublicationClientSecurityUtils.canUpdateCollection());
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

                versionForm.setTranslationsShowed(translationsShowed);
                versionEditionForm.setTranslationsShowed(translationsShowed);

                lifeCycleForm.setTranslationsShowed(translationsShowed);
                lifeCycleEditionForm.setTranslationsShowed(translationsShowed);

                contentMetadataForm.setTranslationsShowed(translationsShowed);
                contentMetadataEditionForm.setTranslationsShowed(translationsShowed);
            }
        });

        // Save

        mainFormLayout.getSave().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (identifiersEditionForm.validate(false) && versionEditionForm.validate(false) && lifeCycleEditionForm.validate(false) && contentMetadataEditionForm.validate(false)) {
                    uiHandlers.saveCollection(getCollectionDto());
                }
            }
        });

        // Life cycle
        mainFormLayout.getProductionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.sendToProductionValidation(collectionDto.getUrn(), collectionDto.getProcStatus());
            }
        });
        mainFormLayout.getDiffusionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.sendToDiffusionValidation(collectionDto.getUrn(), collectionDto.getProcStatus());
            }
        });
        mainFormLayout.getRejectValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.rejectValidation(collectionDto.getUrn(), collectionDto.getProcStatus());
            }
        });
        mainFormLayout.getPendingPublicationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.sendToPendingPublication(collectionDto.getUrn(), collectionDto.getProcStatus());
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
                            uiHandlers.programPublication(collectionDto.getUrn(), collectionDto.getProcStatus());
                            window.destroy();
                        }
                    }
                });
            }
        });
        mainFormLayout.getCancelProgrammedPublication().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.cancelProgrammedPublication(collectionDto.getUrn(), collectionDto.getProcStatus());
            }
        });
        mainFormLayout.getPublishButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.publish(collectionDto.getUrn(), collectionDto.getProcStatus());
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
                            uiHandlers.version(collectionDto.getUrn(), versionWindow.getSelectedVersion());
                            versionWindow.destroy();
                        }
                    }
                });
            }
        });
        mainFormLayout.getArchiveButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                uiHandlers.archive(collectionDto.getUrn(), collectionDto.getProcStatus());
            }
        });
    }
    
    private void createViewForm() {
        // Identifiers form
        identifiersForm = new GroupDynamicForm(getConstants().collectionIdentifiers());
        ViewTextItem identifier = new ViewTextItem(PublicationDS.IDENTIFIER, getConstants().collectionIdentifier());
        ViewMultiLanguageTextItem title = new ViewMultiLanguageTextItem(PublicationDS.TITLE, getConstants().collectionTitle());
        ViewTextItem uri = new ViewTextItem(PublicationDS.URI, getConstants().collectionUri());
        ViewTextItem urn = new ViewTextItem(PublicationDS.URN, getConstants().collectionUrn());
        identifiersForm.setFields(identifier, title, uri, urn);

        // Version form
        versionForm = new GroupDynamicForm(getConstants().versionableVersion());
        ViewTextItem version = new ViewTextItem(PublicationDS.VERSION_LOGIC, getConstants().versionableVersion());
        ViewTextItem versionDate = new ViewTextItem(PublicationDS.VERSION_DATE, getConstants().versionableVersionDate());
        ViewTextItem nextVerstionDate = new ViewTextItem(PublicationDS.NEXT_VERSION_DATE, getConstants().versionableNextVersionDate());
        ViewTextItem rationaleType = new ViewTextItem(PublicationDS.RATIONALE_TYPE, getConstants().versionableRationaleType());
        ViewTextItem rationale = new ViewTextItem(PublicationDS.RATIONALE, getConstants().versionableRationale());
        versionForm.setFields(version, versionDate, nextVerstionDate, rationaleType, rationale);

        // Life cycle form
        lifeCycleForm = new GroupDynamicForm(getConstants().collectionLifeCycle());
        ViewTextItem procStatus = new ViewTextItem(PublicationDS.PROC_STATUS, getConstants().lifeCycleProcStatus());
        ViewTextItem responsabilityCreator = new ViewTextItem(PublicationDS.RESPONSABILITY_CREATOR, getConstants().lifeCycleResponsabilityCreator());
        ViewTextItem responsabilityContributor = new ViewTextItem(PublicationDS.RESPONSABILITY_CONTRIBUTOR, getConstants().lifeCycleResponsabilityContributor());
        ViewTextItem responsabilitySubmitted = new ViewTextItem(PublicationDS.RESPONSABILITY_SUBMITTED, getConstants().lifeCycleResponsabilitySubmitted());
        ViewTextItem responsabilityAccepted = new ViewTextItem(PublicationDS.RESPONSABILITY_ACCEPTED, getConstants().lifeCycleResponsabilityAccepted());
        ViewTextItem responsabilityIssued = new ViewTextItem(PublicationDS.RESPONSABILITY_ISSUED, getConstants().lifeCycleResponsabilityIssued());
        ViewTextItem responsabilityOutOfPrint = new ViewTextItem(PublicationDS.RESPONSABILITY_OUT_OF_PRINT, getConstants().lifeCycleResponsabilityOutOfPrint());
        ViewTextItem creator = new ViewTextItem(PublicationDS.CREATOR, getConstants().lifeCycleCreator());
        SearchExternalListItem contributor = createRelatedMultiAgencyItem(PublicationDS.CONTRIBUTOR, getConstants().lifeCycleContributor(),AgencyField.AGENCY_CONTRIBUTOR,false);
        SearchExternalListItem publisher = createRelatedMultiAgencyItem(PublicationDS.PUBLISHER, getConstants().lifeCyclePublisher(),AgencyField.AGENCY_PUBLISHER,false);
        SearchExternalListItem mediator = createRelatedMultiAgencyItem(PublicationDS.MEDIATOR, getConstants().lifeCycleMediator(),AgencyField.AGENCY_MEDIATOR,false);
        ViewTextItem submittedDate = new ViewTextItem(PublicationDS.SUBMITTED_DATE, getConstants().lifeCycleSubmittedDate());
        ViewTextItem acceptedDate = new ViewTextItem(PublicationDS.ACCEPTED_DATE, getConstants().lifeCycleAcceptedDate());
        ViewTextItem issuedDate = new ViewTextItem(PublicationDS.ISSUED_DATE, getConstants().lifeCycleIssuedDate());
        lifeCycleForm.setFields(procStatus, responsabilityCreator, responsabilityContributor, responsabilitySubmitted, responsabilityAccepted, responsabilityIssued, responsabilityOutOfPrint, creator, contributor,
                publisher, mediator, submittedDate, acceptedDate, issuedDate);

        // Content metadata form
        contentMetadataForm = new GroupDynamicForm(getConstants().collectionContentMetadata());
        ViewTextItem language = new ViewTextItem(PublicationDS.LANGUAGE, getConstants().contentMetadataLanguage());
        ViewTextItem languages = new ViewTextItem(PublicationDS.LANGUAGES, getConstants().contentMetadataLanguages());
        ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(PublicationDS.DESCRIPTION, getConstants().contentMetadataDescription());
        ViewTextItem keywords = new ViewTextItem(PublicationDS.KEYWORDS, getConstants().contentMetadataKeywords());
        ViewTextItem spatialCoverage = new ViewTextItem(PublicationDS.SPATIAL_COVERAGE, getConstants().contentMetadataSpatialCoverage());
        ViewTextItem spatialCoverageCodes = new ViewTextItem(PublicationDS.SPATIAL_COVERAGE_CODES, getConstants().contentMetadataSpatialCoverageCodes());
        ViewTextItem temporalCoverage = new ViewTextItem(PublicationDS.TEMPORAL_COVERAGE, getConstants().contentMetadataTemporalCoverage());
        ViewTextItem temporalCoverageCodes = new ViewTextItem(PublicationDS.TEMPORAL_COVERAGE_CODES, getConstants().contentMetadataTemporalCoverageCodes());
        ViewTextItem type = new ViewTextItem(PublicationDS.TYPE, getConstants().contentMetadataType());
        ViewTextItem format = new ViewTextItem(PublicationDS.FORMAT, getConstants().contentMetadataFormat());
        ViewTextItem nextUpdate = new ViewTextItem(PublicationDS.NEXT_UPDATE_DATE, getConstants().contentMetadataNextUpdate());
        ViewTextItem updateFrequency = new ViewTextItem(PublicationDS.UPDATE_FREQUENCY, getConstants().contentMetadataUpdateFrequency());
        ViewTextItem rightsHolder = new ViewTextItem(PublicationDS.RIGHTS_HOLDER, getConstants().contentMetadataRightsHolder());
        ViewTextItem copyrightedDate = new ViewTextItem(PublicationDS.COPYRIGHTED_DATE, getConstants().contentMetadataCopyrightedDate());
        ViewTextItem license = new ViewTextItem(PublicationDS.LICENSE, getConstants().contentMetadataLicense());
        contentMetadataForm.setFields(language, languages, description, keywords, spatialCoverage, spatialCoverageCodes, temporalCoverage, temporalCoverageCodes, type, format, nextUpdate,
                updateFrequency, rightsHolder, copyrightedDate, license);

        mainFormLayout.addViewCanvas(identifiersForm);
        mainFormLayout.addViewCanvas(versionForm);
        mainFormLayout.addViewCanvas(lifeCycleForm);
        mainFormLayout.addViewCanvas(contentMetadataForm);
    }

    private void createEditionForm() {
        identifiersEditionForm = new GroupDynamicForm(getConstants().collectionIdentifiers());
        RequiredTextItem identifier = new RequiredTextItem(PublicationDS.IDENTIFIER, getConstants().collectionIdentifier());
        identifier.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        MultiLanguageTextItem title = new MultiLanguageTextItem(PublicationDS.TITLE, getConstants().collectionTitle());
        title.setRequired(true);
        ViewTextItem uri = new ViewTextItem(PublicationDS.URI, getConstants().collectionUri());
        ViewTextItem urn = new ViewTextItem(PublicationDS.URN, getConstants().collectionUrn());
        identifiersEditionForm.setFields(identifier, title, uri, urn);

        // Version form
        versionEditionForm = new GroupDynamicForm(getConstants().versionableVersion());
        ViewTextItem version = new ViewTextItem(PublicationDS.VERSION_LOGIC, getConstants().versionableVersion());
        ViewTextItem versionDate = new ViewTextItem(PublicationDS.VERSION_DATE, getConstants().versionableVersionDate());
        CustomDateItem nextVerstionDate = new CustomDateItem(PublicationDS.NEXT_VERSION_DATE, getConstants().versionableNextVersionDate());
        CustomSelectItem rationaleType = new CustomSelectItem(PublicationDS.RATIONALE_TYPE, getConstants().versionableRationaleType());
        rationaleType.setValueMap(CommonUtils.getStatisticalResourceVersionRationaleTypeHashMap());
        CustomTextItem rationale = new CustomTextItem(PublicationDS.RATIONALE, getConstants().versionableRationale());
        versionEditionForm.setFields(version, versionDate, nextVerstionDate, rationaleType, rationale);

        // Life cycle form
        lifeCycleEditionForm = new GroupDynamicForm(getConstants().collectionLifeCycle());
        ViewTextItem procStatus = new ViewTextItem(PublicationDS.PROC_STATUS, getConstants().lifeCycleProcStatus());
        ViewTextItem responsabilityCreator = new ViewTextItem(PublicationDS.RESPONSABILITY_CONTRIBUTOR, getConstants().lifeCycleResponsabilityCreator());
        ViewTextItem responsabilityContributor = new ViewTextItem(PublicationDS.RESPONSABILITY_CONTRIBUTOR, getConstants().lifeCycleResponsabilityContributor());
        ViewTextItem responsabilitySubmitted = new ViewTextItem(PublicationDS.RESPONSABILITY_SUBMITTED, getConstants().lifeCycleResponsabilitySubmitted());
        ViewTextItem responsabilityAccepted = new ViewTextItem(PublicationDS.RESPONSABILITY_ACCEPTED, getConstants().lifeCycleResponsabilityAccepted());
        ViewTextItem responsabilityIssued = new ViewTextItem(PublicationDS.RESPONSABILITY_ISSUED, getConstants().lifeCycleResponsabilityIssued());
        ViewTextItem responsabilityOutOfPrint = new ViewTextItem(PublicationDS.RESPONSABILITY_OUT_OF_PRINT, getConstants().lifeCycleResponsabilityOutOfPrint());
        SearchExternalViewTextItem creator = createRelatedAgencyItem(PublicationDS.CREATOR, getConstants().lifeCycleCreator(), AgencyField.AGENCY_CREATOR);
        SearchExternalListItem contributor = createRelatedMultiAgencyItem(PublicationDS.CONTRIBUTOR, getConstants().lifeCycleContributor(),AgencyField.AGENCY_CONTRIBUTOR,true);
        SearchExternalListItem publisher = createRelatedMultiAgencyItem(PublicationDS.PUBLISHER, getConstants().lifeCyclePublisher(),AgencyField.AGENCY_PUBLISHER,true);
        SearchExternalListItem mediator = createRelatedMultiAgencyItem(PublicationDS.MEDIATOR, getConstants().lifeCycleMediator(),AgencyField.AGENCY_MEDIATOR, true);
        ViewTextItem submittedDate = new ViewTextItem(PublicationDS.SUBMITTED_DATE, getConstants().lifeCycleSubmittedDate());
        ViewTextItem acceptedDate = new ViewTextItem(PublicationDS.ACCEPTED_DATE, getConstants().lifeCycleAcceptedDate());
        ViewTextItem issuedDate = new ViewTextItem(PublicationDS.ISSUED_DATE, getConstants().lifeCycleIssuedDate());
        lifeCycleEditionForm.setFields(procStatus, responsabilityCreator, responsabilityContributor, responsabilitySubmitted, responsabilityAccepted, responsabilityIssued, responsabilityOutOfPrint, creator, contributor,
                publisher, mediator, submittedDate, acceptedDate, issuedDate);

        // Content metadata form
        contentMetadataEditionForm = new GroupDynamicForm(getConstants().collectionContentMetadata());
        ViewTextItem language = new ViewTextItem(PublicationDS.LANGUAGE, getConstants().contentMetadataLanguage()); // TODO edit?
        ViewTextItem languages = new ViewTextItem(PublicationDS.LANGUAGES, getConstants().contentMetadataLanguages()); // TODO if can be edited, what formItem should be used?
        MultiLanguageTextAreaItem description = new MultiLanguageTextAreaItem(PublicationDS.DESCRIPTION, getConstants().contentMetadataDescription());
        ViewTextItem keywords = new ViewTextItem(PublicationDS.KEYWORDS, getConstants().contentMetadataKeywords()); // TODO what formItem should be used?
        ViewTextItem spatialCoverage = new ViewTextItem(PublicationDS.SPATIAL_COVERAGE, getConstants().contentMetadataSpatialCoverage()); // TODO what formItem should be used?
        ViewTextItem spatialCoverageCodes = new ViewTextItem(PublicationDS.SPATIAL_COVERAGE_CODES, getConstants().contentMetadataSpatialCoverageCodes()); // TODO what formItem should be used?
        ViewTextItem temporalCoverage = new ViewTextItem(PublicationDS.TEMPORAL_COVERAGE, getConstants().contentMetadataTemporalCoverage()); // TODO what formItem should be used?
        ViewTextItem temporalCoverageCodes = new ViewTextItem(PublicationDS.TEMPORAL_COVERAGE_CODES, getConstants().contentMetadataTemporalCoverageCodes()); // TODO what formItem should be used?
        ViewTextItem type = new ViewTextItem(PublicationDS.TYPE, getConstants().contentMetadataType());// TODO what formItem should be used?
        ViewTextItem format = new ViewTextItem(PublicationDS.FORMAT, getConstants().contentMetadataFormat());// TODO what formItem should be used?
        CustomDateItem nextUpdate = new CustomDateItem(PublicationDS.NEXT_UPDATE_DATE, getConstants().contentMetadataNextUpdate());
        ViewTextItem updateFrequency = new ViewTextItem(PublicationDS.UPDATE_FREQUENCY, getConstants().contentMetadataUpdateFrequency());// TODO what formItem should be used?
        ViewTextItem rightsHolder = new ViewTextItem(PublicationDS.RIGHTS_HOLDER, getConstants().contentMetadataRightsHolder());// TODO what formItem should be used?
        ViewTextItem copyrightedDate = new ViewTextItem(PublicationDS.COPYRIGHTED_DATE, getConstants().contentMetadataCopyrightedDate());// TODO what formItem should be used?
        ViewTextItem license = new ViewTextItem(PublicationDS.LICENSE, getConstants().contentMetadataLicense());// TODO what formItem should be used?
        contentMetadataEditionForm.setFields(language, languages, description, keywords, spatialCoverage, spatialCoverageCodes, temporalCoverage, temporalCoverageCodes, type, format, nextUpdate,
                updateFrequency, rightsHolder, copyrightedDate, license);

        mainFormLayout.addEditionCanvas(identifiersEditionForm);
        mainFormLayout.addEditionCanvas(versionEditionForm);
        mainFormLayout.addEditionCanvas(lifeCycleEditionForm);
        mainFormLayout.addEditionCanvas(contentMetadataEditionForm);
    }
    
    private SearchExternalViewTextItem createRelatedAgencyItem(String name, String title, AgencyField agencyField) {
        SearchExternalViewTextItem agencyItem = new SearchExternalViewTextItem(name, title);
        agencyItem.setRequired(true);
        agencyItem.getSearchIcon().addFormItemClickHandler(new SearchAgencyFormItemClickHandler(agencyField));
        return agencyItem;
    }
    
    private SearchExternalListItem createRelatedMultiAgencyItem(String name, String title, AgencyField agencyField, boolean editionMode) {
        SearchExternalListItem agencyItem = new SearchExternalListItem(name, title,editionMode);
        if (editionMode) {
            agencyItem.setRequired(false);
            agencyItem.getSearchIcon().addFormItemClickHandler(new SearchMultiAgencyFormItemClickHandler(agencyField));
        }
        return agencyItem;
    }
    
    private void setCollectionViewMode(CollectionDto collectionDto) {
        // Identifiers form
        identifiersForm.setValue(PublicationDS.IDENTIFIER, collectionDto.getIdentifier());
        identifiersForm.setValue(PublicationDS.TITLE, RecordUtils.getInternationalStringRecord(collectionDto.getTitle()));
        identifiersForm.setValue(PublicationDS.URI, collectionDto.getUri());
        identifiersForm.setValue(PublicationDS.URN, collectionDto.getUrn());

        // Version form
        versionForm.setValue(PublicationDS.VERSION_LOGIC, collectionDto.getVersionLogic());
        versionForm.setValue(PublicationDS.VERSION_DATE, DateUtils.getFormattedDate(collectionDto.getVersionDate()));
        versionForm.setValue(PublicationDS.NEXT_VERSION_DATE, DateUtils.getFormattedDate(collectionDto.getNextVersionDate()));
        versionForm.setValue(PublicationDS.RATIONALE_TYPE, CommonUtils.getStatisticalResourceVersionRationaleTypeName(collectionDto.getRationaleType()));
        versionForm.setValue(PublicationDS.RATIONALE, collectionDto.getRationale());

        // Life cycle form
        lifeCycleForm.setValue(PublicationDS.PROC_STATUS, CommonUtils.getProcStatusName(collectionDto));
        lifeCycleForm.setValue(PublicationDS.RESPONSABILITY_CREATOR, collectionDto.getResponsabilityCreator());
        lifeCycleForm.setValue(PublicationDS.RESPONSABILITY_CONTRIBUTOR, collectionDto.getResponsabilityContributor());
        lifeCycleForm.setValue(PublicationDS.RESPONSABILITY_SUBMITTED, collectionDto.getResponsabilitySubmitted());
        lifeCycleForm.setValue(PublicationDS.RESPONSABILITY_ACCEPTED, collectionDto.getResponsabilityAccepted());
        lifeCycleForm.setValue(PublicationDS.RESPONSABILITY_ISSUED, collectionDto.getResponsabilityIssued());
        lifeCycleForm.setValue(PublicationDS.RESPONSABILITY_OUT_OF_PRINT, collectionDto.getResponsabilityOutOfPrint());
        lifeCycleForm.setValue(PublicationDS.CREATOR, collectionDto.getCreator() != null ? ExternalItemUtils.getExternalItemName(collectionDto.getCreator()) : null);
        
        ((SearchExternalListItem)lifeCycleForm.getField(PublicationDS.CONTRIBUTOR)).setExternalItems(collectionDto.getContributor());
        ((SearchExternalListItem)lifeCycleForm.getField(PublicationDS.PUBLISHER)).setExternalItems(collectionDto.getPublisher());
        ((SearchExternalListItem)lifeCycleForm.getField(PublicationDS.MEDIATOR)).setExternalItems(collectionDto.getMediator());
        
        lifeCycleForm.setValue(PublicationDS.SUBMITTED_DATE, DateUtils.getFormattedDate(collectionDto.getSubmittedDate()));
        lifeCycleForm.setValue(PublicationDS.ACCEPTED_DATE, DateUtils.getFormattedDate(collectionDto.getAcceptedDate()));
        lifeCycleForm.setValue(PublicationDS.ISSUED_DATE, DateUtils.getFormattedDate(collectionDto.getIssuedDate()));

        // Content metadata form
        ContentMetadataDto contentMetadataDto = collectionDto.getContentMetadata() != null ? collectionDto.getContentMetadata() : new ContentMetadataDto();
        contentMetadataForm.setValue(PublicationDS.LANGUAGE, contentMetadataDto.getLanguage());
        contentMetadataForm.setValue(PublicationDS.LANGUAGES, contentMetadataDto.getLanguages() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getLanguages()) : null);
        contentMetadataForm.setValue(PublicationDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(contentMetadataDto.getDescription()));
        contentMetadataForm.setValue(PublicationDS.KEYWORDS, contentMetadataDto.getKeywords() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getKeywords()) : null);
        contentMetadataForm.setValue(PublicationDS.SPATIAL_COVERAGE, contentMetadataDto.getSpatialCoverage() != null
                ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverage())
                : null);
        contentMetadataForm.setValue(PublicationDS.SPATIAL_COVERAGE_CODES,
                contentMetadataDto.getSpatialCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverageCodes()) : null);
        contentMetadataForm.setValue(PublicationDS.TEMPORAL_COVERAGE, contentMetadataDto.getTemporalCoverage() != null
                ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverage())
                : null);
        contentMetadataForm.setValue(PublicationDS.TEMPORAL_COVERAGE_CODES,
                contentMetadataDto.getTemporalCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverageCodes()) : null);
        contentMetadataForm.setValue(PublicationDS.TYPE, CommonUtils.getStatisticalResourceTypeName(contentMetadataDto.getType()));
        contentMetadataForm.setValue(PublicationDS.FORMAT, CommonUtils.getStatisticalResourceFormatName(contentMetadataDto.getFormat()));
        contentMetadataForm.setValue(PublicationDS.NEXT_UPDATE_DATE, DateUtils.getFormattedDate(contentMetadataDto.getNextUpdateDate()));
        contentMetadataForm.setValue(PublicationDS.UPDATE_FREQUENCY, contentMetadataDto.getUpdateFrequency());
        contentMetadataForm.setValue(PublicationDS.RIGHTS_HOLDER, contentMetadataDto.getRightsHolder());
        contentMetadataForm.setValue(PublicationDS.COPYRIGHTED_DATE, DateUtils.getFormattedDate(contentMetadataDto.getCopyrightedDate()));
        contentMetadataForm.setValue(PublicationDS.LICENSE, contentMetadataDto.getLicense());
    }

    private void setCollectionEditionMode(CollectionDto collectionDto) {
        // Identifiers form
        identifiersEditionForm.setValue(PublicationDS.IDENTIFIER, collectionDto.getIdentifier());
        identifiersEditionForm.setValue(PublicationDS.TITLE, RecordUtils.getInternationalStringRecord(collectionDto.getTitle()));
        identifiersEditionForm.setValue(PublicationDS.URI, collectionDto.getUri());
        identifiersEditionForm.setValue(PublicationDS.URN, collectionDto.getUrn());

        // Version form
        versionEditionForm.setValue(PublicationDS.VERSION_LOGIC, collectionDto.getVersionLogic());
        versionEditionForm.setValue(PublicationDS.VERSION_DATE, DateUtils.getFormattedDate(collectionDto.getVersionDate()));
        versionEditionForm.setValue(PublicationDS.NEXT_VERSION_DATE, DateUtils.getFormattedDate(collectionDto.getNextVersionDate()));
        versionEditionForm.setValue(PublicationDS.RATIONALE_TYPE, collectionDto.getRationaleType() != null ? collectionDto.getRationaleType().name(): StringUtils.EMPTY);
        versionEditionForm.setValue(PublicationDS.RATIONALE, collectionDto.getRationale());

        // Life cycle form
        lifeCycleEditionForm.setValue(PublicationDS.PROC_STATUS, CommonUtils.getProcStatusName(collectionDto));
        lifeCycleEditionForm.setValue(PublicationDS.RESPONSABILITY_CONTRIBUTOR, collectionDto.getResponsabilityContributor());
        lifeCycleEditionForm.setValue(PublicationDS.RESPONSABILITY_SUBMITTED, collectionDto.getResponsabilitySubmitted());
        lifeCycleEditionForm.setValue(PublicationDS.RESPONSABILITY_ACCEPTED, collectionDto.getResponsabilityAccepted());
        lifeCycleEditionForm.setValue(PublicationDS.RESPONSABILITY_ISSUED, collectionDto.getResponsabilityIssued());
        lifeCycleEditionForm.setValue(PublicationDS.RESPONSABILITY_OUT_OF_PRINT, collectionDto.getResponsabilityOutOfPrint());
        
        ((SearchExternalViewTextItem)lifeCycleEditionForm.getField(PublicationDS.CREATOR)).setExternalItem(collectionDto.getCreator());
        ((SearchExternalListItem)lifeCycleEditionForm.getField(PublicationDS.CONTRIBUTOR)).setExternalItems(new ArrayList<ExternalItemDto>(collectionDto.getContributor()));
        ((SearchExternalListItem)lifeCycleEditionForm.getField(PublicationDS.PUBLISHER)).setExternalItems(new ArrayList<ExternalItemDto>(collectionDto.getPublisher()));
        ((SearchExternalListItem)lifeCycleEditionForm.getField(PublicationDS.MEDIATOR)).setExternalItems(new ArrayList<ExternalItemDto>(collectionDto.getMediator()));
        
        lifeCycleEditionForm.setValue(PublicationDS.SUBMITTED_DATE, DateUtils.getFormattedDate(collectionDto.getSubmittedDate()));
        lifeCycleEditionForm.setValue(PublicationDS.ACCEPTED_DATE, DateUtils.getFormattedDate(collectionDto.getAcceptedDate()));
        lifeCycleEditionForm.setValue(PublicationDS.ISSUED_DATE, DateUtils.getFormattedDate(collectionDto.getIssuedDate()));

        // Content metadata form
        ContentMetadataDto contentMetadataDto = collectionDto.getContentMetadata() != null ? collectionDto.getContentMetadata() : new ContentMetadataDto();
        contentMetadataEditionForm.setValue(PublicationDS.LANGUAGE, contentMetadataDto.getLanguage());
        contentMetadataEditionForm.setValue(PublicationDS.LANGUAGES, contentMetadataDto.getLanguages() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getLanguages()) : null);
        contentMetadataEditionForm.setValue(PublicationDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(contentMetadataDto.getDescription()));
        contentMetadataEditionForm.setValue(PublicationDS.KEYWORDS, contentMetadataDto.getKeywords() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getKeywords()) : null);
        contentMetadataEditionForm.setValue(PublicationDS.SPATIAL_COVERAGE,
                contentMetadataDto.getSpatialCoverage() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverage()) : null);
        contentMetadataEditionForm.setValue(PublicationDS.SPATIAL_COVERAGE_CODES,
                contentMetadataDto.getSpatialCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverageCodes()) : null);
        contentMetadataEditionForm.setValue(PublicationDS.TEMPORAL_COVERAGE,
                contentMetadataDto.getTemporalCoverage() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverage()) : null);
        contentMetadataEditionForm.setValue(PublicationDS.TEMPORAL_COVERAGE_CODES,
                contentMetadataDto.getTemporalCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverageCodes()) : null);
        contentMetadataEditionForm.setValue(PublicationDS.TYPE, CommonUtils.getStatisticalResourceTypeName(contentMetadataDto.getType()));
        contentMetadataEditionForm.setValue(PublicationDS.FORMAT, CommonUtils.getStatisticalResourceFormatName(contentMetadataDto.getFormat()));
        contentMetadataEditionForm.setValue(PublicationDS.NEXT_UPDATE_DATE, DateUtils.getFormattedDate(contentMetadataDto.getNextUpdateDate()));
        contentMetadataEditionForm.setValue(PublicationDS.UPDATE_FREQUENCY, contentMetadataDto.getUpdateFrequency());
        contentMetadataEditionForm.setValue(PublicationDS.RIGHTS_HOLDER, contentMetadataDto.getRightsHolder());
        contentMetadataEditionForm.setValue(PublicationDS.COPYRIGHTED_DATE, DateUtils.getFormattedDate(contentMetadataDto.getCopyrightedDate()));
        contentMetadataEditionForm.setValue(PublicationDS.LICENSE, contentMetadataDto.getLicense());
    }
    
    @Override
    public void setCollection(CollectionDto collectionDto) {
        this.collectionDto = collectionDto;
        
        mainFormLayout.updatePublishSection(collectionDto.getProcStatus());
        mainFormLayout.setViewMode();

        setCollectionViewMode(collectionDto);
        setCollectionEditionMode(collectionDto);
    }
    
    @Override
    public void setAgenciesPaginatedList(GetAgenciesPaginatedListResult result) {
        if (searchAgencyWindow != null) {
            searchAgencyWindow.setExternalItems(result.getAgenciesList());
        }
        if (searchMultiAgencyWindow != null)  {
            searchMultiAgencyWindow.setSourceExternalItems(result.getAgenciesList());
        }
    }

    private CollectionDto getCollectionDto() {
        // Identifiers form
        collectionDto.setIdentifier(identifiersEditionForm.getValueAsString(PublicationDS.IDENTIFIER));
        collectionDto.setTitle((InternationalStringDto) identifiersEditionForm.getValue(PublicationDS.TITLE));

        // Version form
        collectionDto.setRationale(versionEditionForm.getValueAsString(PublicationDS.RATIONALE));
        String rationaleType = versionEditionForm.getValueAsString(PublicationDS.RATIONALE_TYPE);
        if (!StringUtils.isEmpty(rationaleType)) {
            collectionDto.setRationaleType(StatisticalResourceVersionRationaleTypeEnum.valueOf(rationaleType));
        }
        
        collectionDto.setNextVersionDate((Date)versionEditionForm.getValue(PublicationDS.NEXT_VERSION_DATE));

        // Life cycle form

        // Content metadata form
        if (collectionDto.getContentMetadata() == null) {
            collectionDto.setContentMetadata(new ContentMetadataDto());
        }
        
        ExternalItemDto creatorAgency = ((SearchExternalViewTextItem)lifeCycleEditionForm.getField(PublicationDS.CREATOR)).getExternalItem();
        collectionDto.setCreator(creatorAgency);
        
     // Life cycle form
        List<ExternalItemDto> contributorAgencies = ((SearchExternalListItem)lifeCycleEditionForm.getField(PublicationDS.CONTRIBUTOR)).getSelectedExternalItems();
        if (contributorAgencies != null) {
            collectionDto.getContributor().clear();
            collectionDto.getContributor().addAll(contributorAgencies);
        }
        List<ExternalItemDto> publisherAgencies = ((SearchExternalListItem)lifeCycleEditionForm.getField(PublicationDS.PUBLISHER)).getSelectedExternalItems();
        if (publisherAgencies != null) {
            collectionDto.getPublisher().clear();
            collectionDto.getPublisher().addAll(publisherAgencies);
        }
        
        List<ExternalItemDto> mediatorAgencies = ((SearchExternalListItem)lifeCycleEditionForm.getField(PublicationDS.MEDIATOR)).getSelectedExternalItems();
        if (mediatorAgencies != null) {
            collectionDto.getMediator().clear();
            collectionDto.getMediator().addAll(mediatorAgencies);
        }

        
        collectionDto.getContentMetadata().setDescription((InternationalStringDto) contentMetadataEditionForm.getValue(PublicationDS.DESCRIPTION));
        collectionDto.getContentMetadata().setNextUpdateDate((Date) contentMetadataEditionForm.getValue(PublicationDS.NEXT_UPDATE_DATE));
        
        return collectionDto;
    }
    
    @Override
    public void setUiHandlers(PublicationMetadataTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
    
    @Override
    public Widget asWidget() {
        return panel;
    }
    
    private enum AgencyField {
        AGENCY_CREATOR(PublicationDS.CREATOR),
        AGENCY_CONTRIBUTOR(PublicationDS.CONTRIBUTOR),
        AGENCY_PUBLISHER(PublicationDS.PUBLISHER),
        AGENCY_MEDIATOR(PublicationDS.MEDIATOR);
        
        private String formFieldId;
        private AgencyField(String formFieldId) {
            this.formFieldId = formFieldId;
        }
        
        public String getFormFieldId() {
            return formFieldId;
        }
    }

    private class SearchAgencyFormItemClickHandler implements FormItemClickHandler {
        private AgencyField agencyField;
        
        public SearchAgencyFormItemClickHandler(AgencyField agencyField) {
            this.agencyField = agencyField;
        }
        
        @Override
        public void onFormItemClick(FormItemIconClickEvent event) {
            final int AGENCY_FIRST_RESULT = 0;
            final int AGENCY_MAX_RESULTS = 16;
            
            searchAgencyWindow = new SearchExternalItemWindow(getConstants().agencySearch(), AGENCY_MAX_RESULTS, new PaginatedAction() {
                @Override
                public void retrieveResultSet(int firstResult, int maxResults) {
                    uiHandlers.retrieveAgencies(firstResult, maxResults, null);
                }
            });
            uiHandlers.retrieveAgencies(AGENCY_FIRST_RESULT, AGENCY_MAX_RESULTS, null);
            searchAgencyWindow.setSearchAction(new SearchPaginatedAction() {
                @Override
                public void retrieveResultSet(int firstResult, int maxResults, String code) {
                    uiHandlers.retrieveAgencies(firstResult, maxResults, code);
                }
            });
            searchAgencyWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
                @Override
                public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                    ExternalItemDto selectedAgency = searchAgencyWindow.getSelectedExternalItem();
                    
                    ((SearchExternalViewTextItem)lifeCycleEditionForm.getField(agencyField.getFormFieldId())).setExternalItem(selectedAgency);
                    searchAgencyWindow.destroy();
                }
            });
        }
    }
    private class SearchMultiAgencyFormItemClickHandler implements FormItemClickHandler {
        private AgencyField agencyField;
        
        public SearchMultiAgencyFormItemClickHandler(AgencyField agencyField) {
            this.agencyField = agencyField;
        }
        
        @Override
        public void onFormItemClick(FormItemIconClickEvent event) {
            final int AGENCY_FIRST_RESULT = 0;
            final int AGENCY_MAX_RESULTS = 16;
            
            searchMultiAgencyWindow = new SearchMultipleExternalItemWindow(getConstants().agencySearch(), AGENCY_MAX_RESULTS, new PaginatedAction() {
                @Override
                public void retrieveResultSet(int firstResult, int maxResults) {
                    uiHandlers.retrieveAgencies(firstResult, maxResults, null);
                }
            });
            
            List<ExternalItemDto> selectedAgencies =((SearchExternalListItem)lifeCycleEditionForm.getField(agencyField.getFormFieldId())).getSelectedExternalItems();
            searchMultiAgencyWindow.setSelectedExternalItems(selectedAgencies);
            
            uiHandlers.retrieveAgencies(AGENCY_FIRST_RESULT, AGENCY_MAX_RESULTS, null);
            searchMultiAgencyWindow.setSearchAction(new SearchPaginatedAction() {
                @Override
                public void retrieveResultSet(int firstResult, int maxResults, String code) {
                    uiHandlers.retrieveAgencies(firstResult, maxResults, code);
                }
            });
            searchMultiAgencyWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
                @Override
                public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                    List<ExternalItemDto> selectedAgencies = searchMultiAgencyWindow.getSelectedExternalItems();
                    ((SearchExternalListItem)lifeCycleEditionForm.getField(agencyField.getFormFieldId())).setExternalItems(selectedAgencies);
                    searchMultiAgencyWindow.hide();
                    searchMultiAgencyWindow.destroy();
                }
            });
        }
    }
}
