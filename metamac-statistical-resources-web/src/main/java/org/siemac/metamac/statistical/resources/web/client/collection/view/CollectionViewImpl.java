package org.siemac.metamac.statistical.resources.web.client.collection.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.CollectionDto;
import org.siemac.metamac.statistical.resources.core.dto.ContentMetadataDto;
import org.siemac.metamac.statistical.resources.web.client.collection.model.ds.CollectionDS;
import org.siemac.metamac.statistical.resources.web.client.collection.presenter.CollectionPresenter;
import org.siemac.metamac.statistical.resources.web.client.collection.utils.CollectionClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.collection.view.handlers.CollectionUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.collection.widgets.CollectionMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.collection.widgets.CollectionStructurePanel;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.utils.DateUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.TitleLabel;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextAreaItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.RequiredTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class CollectionViewImpl extends ViewImpl implements CollectionPresenter.CollectionView {

    private CollectionUiHandlers     uiHandlers;

    private VLayout                  panel;
    private CollectionMainFormLayout mainFormLayout;

    private GroupDynamicForm         identifiersForm;
    private GroupDynamicForm         versionForm;
    private GroupDynamicForm         lifeCycleForm;
    private GroupDynamicForm         contentMetadataForm;

    private GroupDynamicForm         identifiersEditionForm;
    private GroupDynamicForm         versionEditionForm;
    private GroupDynamicForm         lifeCycleEditionForm;
    private GroupDynamicForm         contentMetadataEditionForm;

    private CollectionStructurePanel structurePanel;

    private CollectionDto            collectionDto;

    @Inject
    public CollectionViewImpl() {
        super();
        panel = new VLayout();
        panel.setHeight100();
        panel.setOverflow(Overflow.SCROLL);

        // COLLECTION METADATA

        mainFormLayout = new CollectionMainFormLayout(CollectionClientSecurityUtils.canUpdateCollection());
        bindMainFormLayoutEvents();
        createViewForm();
        createEditionForm();

        // STRUCTURE

        structurePanel = new CollectionStructurePanel();

        VLayout collectionStructureLayout = new VLayout();
        collectionStructureLayout.setMargin(15);
        collectionStructureLayout.addMember(new TitleLabel(getConstants().collectionStructure()));
        collectionStructureLayout.addMember(structurePanel);

        // TABS

        TabSet tabSet = new TabSet();
        tabSet.setMargin(10);

        Tab collectionMetadataTab = new Tab(getConstants().collectionMetadata());
        collectionMetadataTab.setPane(mainFormLayout);

        Tab collectionStructureTab = new Tab(getConstants().collectionStructure());
        collectionStructureTab.setPane(structurePanel);

        tabSet.setTabs(collectionMetadataTab, collectionStructureTab);

        panel.addMember(tabSet);
    }

    @Override
    public void setUiHandlers(CollectionUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == CollectionPresenter.TYPE_SetContextAreaContentOperationResourcesToolBar) {
            if (content != null) {
                Canvas[] canvas = ((ToolStrip) content).getMembers();
                for (int i = 0; i < canvas.length; i++) {
                    if (canvas[i] instanceof ToolStripButton) {
                        if (StatisticalResourcesToolStripButtonEnum.COLLECTIONS.getValue().equals(((ToolStripButton) canvas[i]).getID())) {
                            ((ToolStripButton) canvas[i]).select();
                        }
                    }
                }
                panel.addMember(content, 0);
            }
        } else {
            // To support inheritance in your views it is good practice to call super.setInSlot when you can't handle the call.
            // Who knows, maybe the parent class knows what to do with this slot.
            super.setInSlot(slot, content);
        }
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setCollection(CollectionDto collectionDto) {
        this.collectionDto = collectionDto;

        mainFormLayout.setTitleLabelContents(InternationalStringUtils.getLocalisedString(collectionDto.getTitle()));
        mainFormLayout.updatePublishSection(collectionDto.getProcStatus());
        mainFormLayout.setViewMode();

        setCollectionViewMode(collectionDto);
        setCollectionEditionMode(collectionDto);

        // Structure
        structurePanel.setCollectionStructure(collectionDto.getStructure());
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
                uiHandlers.programPublication(collectionDto.getUrn(), collectionDto.getProcStatus());
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
                // TODO Auto-generated method stub

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
        ViewTextItem identifier = new ViewTextItem(CollectionDS.IDENTIFIER, getConstants().collectionIdentifier());
        ViewMultiLanguageTextItem title = new ViewMultiLanguageTextItem(CollectionDS.TITLE, getConstants().collectionTitle());
        ViewTextItem uri = new ViewTextItem(CollectionDS.URI, getConstants().collectionUri());
        ViewTextItem urn = new ViewTextItem(CollectionDS.URN, getConstants().collectionUrn());
        identifiersForm.setFields(identifier, title, uri, urn);

        // Version form
        versionForm = new GroupDynamicForm(getConstants().versionableVersion());
        ViewTextItem version = new ViewTextItem(CollectionDS.VERSION_LOGIC, getConstants().versionableVersion());
        ViewTextItem versionDate = new ViewTextItem(CollectionDS.VERSION_DATE, getConstants().versionableVersionDate());
        ViewTextItem nextVerstionDate = new ViewTextItem(CollectionDS.NEXT_VERSION_DATE, getConstants().versionableNextVersionDate());
        ViewTextItem rationaleType = new ViewTextItem(CollectionDS.RATIONALE_TYPE, getConstants().versionableRationaleType());
        ViewTextItem rationale = new ViewTextItem(CollectionDS.RATIONALE, getConstants().versionableRationale());
        versionForm.setFields(version, versionDate, nextVerstionDate, rationaleType, rationale);

        // Life cycle form
        lifeCycleForm = new GroupDynamicForm(getConstants().collectionLifeCycle());
        ViewTextItem procStatus = new ViewTextItem(CollectionDS.PROC_STATUS, getConstants().lifeCycleProcStatus());
        ViewTextItem responsabilityContributor = new ViewTextItem(CollectionDS.RESPONSABILITY_CONTRIBUTOR, getConstants().lifeCycleResponsabilityContributor());
        ViewTextItem responsabilitySubmitted = new ViewTextItem(CollectionDS.RESPONSABILITY_SUBMITTED, getConstants().lifeCycleResponsabilitySubmitted());
        ViewTextItem responsabilityAccepted = new ViewTextItem(CollectionDS.RESPONSABILITY_ACCEPTED, getConstants().lifeCycleResponsabilityAccepted());
        ViewTextItem responsabilityIssued = new ViewTextItem(CollectionDS.RESPONSABILITY_ISSUED, getConstants().lifeCycleResponsabilityIssued());
        ViewTextItem responsabilityOutOfPrint = new ViewTextItem(CollectionDS.RESPONSABILITY_OUT_OF_PRINT, getConstants().lifeCycleResponsabilityOutOfPrint());
        ViewTextItem creator = new ViewTextItem(CollectionDS.CREATOR, getConstants().lifeCycleCreator());
        ViewTextItem contributor = new ViewTextItem(CollectionDS.CONTRIBUTOR, getConstants().lifeCycleContributor());
        ViewTextItem publisher = new ViewTextItem(CollectionDS.PUBLISHER, getConstants().lifeCyclePublisher());
        ViewTextItem mediator = new ViewTextItem(CollectionDS.MEDIATOR, getConstants().lifeCycleMediator());
        ViewTextItem submittedDate = new ViewTextItem(CollectionDS.SUBMITTED_DATE, getConstants().lifeCycleSubmittedDate());
        ViewTextItem acceptedDate = new ViewTextItem(CollectionDS.ACCEPTED_DATE, getConstants().lifeCycleAcceptedDate());
        ViewTextItem issuedDate = new ViewTextItem(CollectionDS.ISSUED_DATE, getConstants().lifeCycleIssuedDate());
        lifeCycleForm.setFields(procStatus, responsabilityContributor, responsabilitySubmitted, responsabilityAccepted, responsabilityIssued, responsabilityOutOfPrint, creator, contributor,
                publisher, mediator, submittedDate, acceptedDate, issuedDate);

        // Content metadata form
        contentMetadataForm = new GroupDynamicForm(getConstants().collectionContentMetadata());
        ViewTextItem language = new ViewTextItem(CollectionDS.LANGUAGE, getConstants().contentMetadataLanguage());
        ViewTextItem languages = new ViewTextItem(CollectionDS.LANGUAGES, getConstants().contentMetadataLanguages());
        ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(CollectionDS.DESCRIPTION, getConstants().contentMetadataDescription());
        ViewTextItem keywords = new ViewTextItem(CollectionDS.KEYWORDS, getConstants().contentMetadataKeywords());
        ViewTextItem spatialCoverage = new ViewTextItem(CollectionDS.SPATIAL_COVERAGE, getConstants().contentMetadataSpatialCoverage());
        ViewTextItem spatialCoverageCodes = new ViewTextItem(CollectionDS.SPATIAL_COVERAGE_CODES, getConstants().contentMetadataSpatialCoverageCodes());
        ViewTextItem temporalCoverage = new ViewTextItem(CollectionDS.TEMPORAL_COVERAGE, getConstants().contentMetadataTemporalCoverage());
        ViewTextItem temporalCoverageCodes = new ViewTextItem(CollectionDS.TEMPORAL_COVERAGE_CODES, getConstants().contentMetadataTemporalCoverageCodes());
        ViewTextItem type = new ViewTextItem(CollectionDS.TYPE, getConstants().contentMetadataType());
        ViewTextItem format = new ViewTextItem(CollectionDS.FORMAT, getConstants().contentMetadataFormat());
        ViewTextItem nextUpdate = new ViewTextItem(CollectionDS.NEXT_UPDATE_DATE, getConstants().contentMetadataNextUpdate());
        ViewTextItem updateFrequency = new ViewTextItem(CollectionDS.UPDATE_FREQUENCY, getConstants().contentMetadataUpdateFrequency());
        ViewTextItem rightsHolder = new ViewTextItem(CollectionDS.RIGHTS_HOLDER, getConstants().contentMetadataRightsHolder());
        ViewTextItem copyrightedDate = new ViewTextItem(CollectionDS.COPYRIGHTED_DATE, getConstants().contentMetadataCopyrightedDate());
        ViewTextItem license = new ViewTextItem(CollectionDS.LICENSE, getConstants().contentMetadataLicense());
        contentMetadataForm.setFields(language, languages, description, keywords, spatialCoverage, spatialCoverageCodes, temporalCoverage, temporalCoverageCodes, type, format, nextUpdate,
                updateFrequency, rightsHolder, copyrightedDate, license);

        mainFormLayout.addViewCanvas(identifiersForm);
        mainFormLayout.addViewCanvas(versionForm);
        mainFormLayout.addViewCanvas(lifeCycleForm);
        mainFormLayout.addViewCanvas(contentMetadataForm);
    }

    private void createEditionForm() {
        identifiersEditionForm = new GroupDynamicForm(getConstants().collectionIdentifiers());
        RequiredTextItem identifier = new RequiredTextItem(CollectionDS.IDENTIFIER, getConstants().collectionIdentifier());
        identifier.setValidators(CommonWebUtils.getSemanticIdentifierCustomValidator());
        MultiLanguageTextItem title = new MultiLanguageTextItem(CollectionDS.TITLE, getConstants().collectionTitle());
        title.setRequired(true);
        ViewTextItem uri = new ViewTextItem(CollectionDS.URI, getConstants().collectionUri());
        ViewTextItem urn = new ViewTextItem(CollectionDS.URN, getConstants().collectionUrn());
        identifiersEditionForm.setFields(identifier, title, uri, urn);

        // Version form
        versionEditionForm = new GroupDynamicForm(getConstants().versionableVersion());
        ViewTextItem version = new ViewTextItem(CollectionDS.VERSION_LOGIC, getConstants().versionableVersion());
        ViewTextItem versionDate = new ViewTextItem(CollectionDS.VERSION_DATE, getConstants().versionableVersionDate());
        ViewTextItem nextVerstionDate = new ViewTextItem(CollectionDS.NEXT_VERSION_DATE, getConstants().versionableNextVersionDate()); // TODO what formItem should be used?
        ViewTextItem rationaleType = new ViewTextItem(CollectionDS.RATIONALE_TYPE, getConstants().versionableRationaleType());
        CustomTextItem rationale = new CustomTextItem(CollectionDS.RATIONALE, getConstants().versionableRationale());
        versionEditionForm.setFields(version, versionDate, nextVerstionDate, rationaleType, rationale);

        // Life cycle form
        lifeCycleEditionForm = new GroupDynamicForm(getConstants().collectionLifeCycle());
        ViewTextItem procStatus = new ViewTextItem(CollectionDS.PROC_STATUS, getConstants().lifeCycleProcStatus());
        ViewTextItem responsabilityContributor = new ViewTextItem(CollectionDS.RESPONSABILITY_CONTRIBUTOR, getConstants().lifeCycleResponsabilityContributor());
        ViewTextItem responsabilitySubmitted = new ViewTextItem(CollectionDS.RESPONSABILITY_SUBMITTED, getConstants().lifeCycleResponsabilitySubmitted());
        ViewTextItem responsabilityAccepted = new ViewTextItem(CollectionDS.RESPONSABILITY_ACCEPTED, getConstants().lifeCycleResponsabilityAccepted());
        ViewTextItem responsabilityIssued = new ViewTextItem(CollectionDS.RESPONSABILITY_ISSUED, getConstants().lifeCycleResponsabilityIssued());
        ViewTextItem responsabilityOutOfPrint = new ViewTextItem(CollectionDS.RESPONSABILITY_OUT_OF_PRINT, getConstants().lifeCycleResponsabilityOutOfPrint());
        ViewTextItem creator = new ViewTextItem(CollectionDS.CREATOR, getConstants().lifeCycleCreator());
        ViewTextItem contributor = new ViewTextItem(CollectionDS.CONTRIBUTOR, getConstants().lifeCycleContributor());
        ViewTextItem publisher = new ViewTextItem(CollectionDS.PUBLISHER, getConstants().lifeCyclePublisher());
        ViewTextItem mediator = new ViewTextItem(CollectionDS.MEDIATOR, getConstants().lifeCycleMediator());
        ViewTextItem submittedDate = new ViewTextItem(CollectionDS.SUBMITTED_DATE, getConstants().lifeCycleSubmittedDate());
        ViewTextItem acceptedDate = new ViewTextItem(CollectionDS.ACCEPTED_DATE, getConstants().lifeCycleAcceptedDate());
        ViewTextItem issuedDate = new ViewTextItem(CollectionDS.ISSUED_DATE, getConstants().lifeCycleIssuedDate());
        lifeCycleEditionForm.setFields(procStatus, responsabilityContributor, responsabilitySubmitted, responsabilityAccepted, responsabilityIssued, responsabilityOutOfPrint, creator, contributor,
                publisher, mediator, submittedDate, acceptedDate, issuedDate);

        // Content metadata form
        contentMetadataEditionForm = new GroupDynamicForm(getConstants().collectionContentMetadata());
        ViewTextItem language = new ViewTextItem(CollectionDS.LANGUAGE, getConstants().contentMetadataLanguage()); // TODO edit?
        ViewTextItem languages = new ViewTextItem(CollectionDS.LANGUAGES, getConstants().contentMetadataLanguages()); // TODO if can be edited, what formItem should be used?
        MultiLanguageTextAreaItem description = new MultiLanguageTextAreaItem(CollectionDS.DESCRIPTION, getConstants().contentMetadataDescription());
        ViewTextItem keywords = new ViewTextItem(CollectionDS.KEYWORDS, getConstants().contentMetadataKeywords()); // TODO what formItem should be used?
        ViewTextItem spatialCoverage = new ViewTextItem(CollectionDS.SPATIAL_COVERAGE, getConstants().contentMetadataSpatialCoverage()); // TODO what formItem should be used?
        ViewTextItem spatialCoverageCodes = new ViewTextItem(CollectionDS.SPATIAL_COVERAGE_CODES, getConstants().contentMetadataSpatialCoverageCodes()); // TODO what formItem should be used?
        ViewTextItem temporalCoverage = new ViewTextItem(CollectionDS.TEMPORAL_COVERAGE, getConstants().contentMetadataTemporalCoverage()); // TODO what formItem should be used?
        ViewTextItem temporalCoverageCodes = new ViewTextItem(CollectionDS.TEMPORAL_COVERAGE_CODES, getConstants().contentMetadataTemporalCoverageCodes()); // TODO what formItem should be used?
        ViewTextItem type = new ViewTextItem(CollectionDS.TYPE, getConstants().contentMetadataType());// TODO what formItem should be used?
        ViewTextItem format = new ViewTextItem(CollectionDS.FORMAT, getConstants().contentMetadataFormat());// TODO what formItem should be used?
        ViewTextItem nextUpdate = new ViewTextItem(CollectionDS.NEXT_UPDATE_DATE, getConstants().contentMetadataNextUpdate());// TODO what formItem should be used?
        ViewTextItem updateFrequency = new ViewTextItem(CollectionDS.UPDATE_FREQUENCY, getConstants().contentMetadataUpdateFrequency());// TODO what formItem should be used?
        ViewTextItem rightsHolder = new ViewTextItem(CollectionDS.RIGHTS_HOLDER, getConstants().contentMetadataRightsHolder());// TODO what formItem should be used?
        ViewTextItem copyrightedDate = new ViewTextItem(CollectionDS.COPYRIGHTED_DATE, getConstants().contentMetadataCopyrightedDate());// TODO what formItem should be used?
        ViewTextItem license = new ViewTextItem(CollectionDS.LICENSE, getConstants().contentMetadataLicense());// TODO what formItem should be used?
        contentMetadataEditionForm.setFields(language, languages, description, keywords, spatialCoverage, spatialCoverageCodes, temporalCoverage, temporalCoverageCodes, type, format, nextUpdate,
                updateFrequency, rightsHolder, copyrightedDate, license);

        mainFormLayout.addEditionCanvas(identifiersEditionForm);
        mainFormLayout.addEditionCanvas(versionEditionForm);
        mainFormLayout.addEditionCanvas(lifeCycleEditionForm);
        mainFormLayout.addEditionCanvas(contentMetadataEditionForm);
    }

    private void setCollectionViewMode(CollectionDto collectionDto) {
        // Identifiers form
        identifiersForm.setValue(CollectionDS.IDENTIFIER, collectionDto.getIdentifier());
        identifiersForm.setValue(CollectionDS.TITLE, RecordUtils.getInternationalStringRecord(collectionDto.getTitle()));
        identifiersForm.setValue(CollectionDS.URI, collectionDto.getUri());
        identifiersForm.setValue(CollectionDS.URN, collectionDto.getUrn());

        // Version form
        versionForm.setValue(CollectionDS.VERSION_LOGIC, collectionDto.getVersionLogic());
        versionForm.setValue(CollectionDS.VERSION_DATE, DateUtils.getFormattedDate(collectionDto.getVersionDate()));
        versionForm.setValue(CollectionDS.NEXT_VERSION_DATE, DateUtils.getFormattedDate(collectionDto.getNextVersionDate()));
        versionForm.setValue(CollectionDS.RATIONALE_TYPE, CommonUtils.getStatisticalResourceVersionRationaleTypeName(collectionDto.getRationaleType()));
        versionForm.setValue(CollectionDS.RATIONALE, collectionDto.getRationale());

        // Life cycle form
        lifeCycleForm.setValue(CollectionDS.PROC_STATUS, CommonUtils.getProcStatusName(collectionDto));
        lifeCycleForm.setValue(CollectionDS.RESPONSABILITY_CONTRIBUTOR, collectionDto.getResponsabilityContributor());
        lifeCycleForm.setValue(CollectionDS.RESPONSABILITY_SUBMITTED, collectionDto.getResponsabilitySubmitted());
        lifeCycleForm.setValue(CollectionDS.RESPONSABILITY_ACCEPTED, collectionDto.getResponsabilityAccepted());
        lifeCycleForm.setValue(CollectionDS.RESPONSABILITY_ISSUED, collectionDto.getResponsabilityIssued());
        lifeCycleForm.setValue(CollectionDS.RESPONSABILITY_OUT_OF_PRINT, collectionDto.getResponsabilityOutOfPrint());
        lifeCycleForm.setValue(CollectionDS.CREATOR, collectionDto.getCreator());
        lifeCycleForm.setValue(CollectionDS.CONTRIBUTOR, collectionDto.getContributor());
        lifeCycleForm.setValue(CollectionDS.PUBLISHER, collectionDto.getPublisher());
        lifeCycleForm.setValue(CollectionDS.MEDIATOR, collectionDto.getMediator());
        lifeCycleForm.setValue(CollectionDS.SUBMITTED_DATE, DateUtils.getFormattedDate(collectionDto.getSubmittedDate()));
        lifeCycleForm.setValue(CollectionDS.ACCEPTED_DATE, DateUtils.getFormattedDate(collectionDto.getAcceptedDate()));
        lifeCycleForm.setValue(CollectionDS.ISSUED_DATE, DateUtils.getFormattedDate(collectionDto.getIssuedDate()));

        // Content metadata form
        ContentMetadataDto contentMetadataDto = collectionDto.getContentMetadata() != null ? collectionDto.getContentMetadata() : new ContentMetadataDto();
        contentMetadataForm.setValue(CollectionDS.LANGUAGE, contentMetadataDto.getLanguage());
        contentMetadataForm.setValue(CollectionDS.LANGUAGES, contentMetadataDto.getLanguages() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getLanguages()) : null);
        contentMetadataForm.setValue(CollectionDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(contentMetadataDto.getDescription()));
        contentMetadataForm.setValue(CollectionDS.KEYWORDS, contentMetadataDto.getKeywords() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getKeywords()) : null);
        contentMetadataForm.setValue(CollectionDS.SPATIAL_COVERAGE, contentMetadataDto.getSpatialCoverage() != null
                ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverage())
                : null);
        contentMetadataForm.setValue(CollectionDS.SPATIAL_COVERAGE_CODES,
                contentMetadataDto.getSpatialCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverageCodes()) : null);
        contentMetadataForm.setValue(CollectionDS.TEMPORAL_COVERAGE, contentMetadataDto.getTemporalCoverage() != null
                ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverage())
                : null);
        contentMetadataForm.setValue(CollectionDS.TEMPORAL_COVERAGE_CODES,
                contentMetadataDto.getTemporalCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverageCodes()) : null);
        contentMetadataForm.setValue(CollectionDS.TYPE, CommonUtils.getStatisticalResourceTypeName(contentMetadataDto.getType()));
        contentMetadataForm.setValue(CollectionDS.FORMAT, CommonUtils.getStatisticalResourceFormatName(contentMetadataDto.getFormat()));
        contentMetadataForm.setValue(CollectionDS.NEXT_UPDATE_DATE, DateUtils.getFormattedDate(contentMetadataDto.getNextUpdateDate()));
        contentMetadataForm.setValue(CollectionDS.UPDATE_FREQUENCY, contentMetadataDto.getUpdateFrequency());
        contentMetadataForm.setValue(CollectionDS.RIGHTS_HOLDER, contentMetadataDto.getRightsHolder());
        contentMetadataForm.setValue(CollectionDS.COPYRIGHTED_DATE, DateUtils.getFormattedDate(contentMetadataDto.getCopyrightedDate()));
        contentMetadataForm.setValue(CollectionDS.LICENSE, contentMetadataDto.getLicense());
    }

    private void setCollectionEditionMode(CollectionDto collectionDto) {
        // Identifiers form
        identifiersEditionForm.setValue(CollectionDS.IDENTIFIER, collectionDto.getIdentifier());
        identifiersEditionForm.setValue(CollectionDS.TITLE, RecordUtils.getInternationalStringRecord(collectionDto.getTitle()));
        identifiersEditionForm.setValue(CollectionDS.URI, collectionDto.getUri());
        identifiersEditionForm.setValue(CollectionDS.URN, collectionDto.getUrn());

        // Version form
        versionEditionForm.setValue(CollectionDS.VERSION_LOGIC, collectionDto.getVersionLogic());
        versionEditionForm.setValue(CollectionDS.VERSION_DATE, DateUtils.getFormattedDate(collectionDto.getVersionDate()));
        versionEditionForm.setValue(CollectionDS.NEXT_VERSION_DATE, DateUtils.getFormattedDate(collectionDto.getNextVersionDate()));
        versionEditionForm.setValue(CollectionDS.RATIONALE_TYPE, CommonUtils.getStatisticalResourceVersionRationaleTypeName(collectionDto.getRationaleType()));
        versionEditionForm.setValue(CollectionDS.RATIONALE, collectionDto.getRationale());

        // Life cycle form
        lifeCycleEditionForm.setValue(CollectionDS.PROC_STATUS, CommonUtils.getProcStatusName(collectionDto));
        lifeCycleEditionForm.setValue(CollectionDS.RESPONSABILITY_CONTRIBUTOR, collectionDto.getResponsabilityContributor());
        lifeCycleEditionForm.setValue(CollectionDS.RESPONSABILITY_SUBMITTED, collectionDto.getResponsabilitySubmitted());
        lifeCycleEditionForm.setValue(CollectionDS.RESPONSABILITY_ACCEPTED, collectionDto.getResponsabilityAccepted());
        lifeCycleEditionForm.setValue(CollectionDS.RESPONSABILITY_ISSUED, collectionDto.getResponsabilityIssued());
        lifeCycleEditionForm.setValue(CollectionDS.RESPONSABILITY_OUT_OF_PRINT, collectionDto.getResponsabilityOutOfPrint());
        lifeCycleEditionForm.setValue(CollectionDS.CREATOR, collectionDto.getCreator());
        lifeCycleEditionForm.setValue(CollectionDS.CONTRIBUTOR, collectionDto.getContributor());
        lifeCycleEditionForm.setValue(CollectionDS.PUBLISHER, collectionDto.getPublisher());
        lifeCycleEditionForm.setValue(CollectionDS.MEDIATOR, collectionDto.getMediator());
        lifeCycleEditionForm.setValue(CollectionDS.SUBMITTED_DATE, DateUtils.getFormattedDate(collectionDto.getSubmittedDate()));
        lifeCycleEditionForm.setValue(CollectionDS.ACCEPTED_DATE, DateUtils.getFormattedDate(collectionDto.getAcceptedDate()));
        lifeCycleEditionForm.setValue(CollectionDS.ISSUED_DATE, DateUtils.getFormattedDate(collectionDto.getIssuedDate()));

        // Content metadata form
        ContentMetadataDto contentMetadataDto = collectionDto.getContentMetadata() != null ? collectionDto.getContentMetadata() : new ContentMetadataDto();
        contentMetadataEditionForm.setValue(CollectionDS.LANGUAGE, contentMetadataDto.getLanguage());
        contentMetadataEditionForm.setValue(CollectionDS.LANGUAGES, contentMetadataDto.getLanguages() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getLanguages()) : null);
        contentMetadataEditionForm.setValue(CollectionDS.DESCRIPTION, RecordUtils.getInternationalStringRecord(contentMetadataDto.getDescription()));
        contentMetadataEditionForm.setValue(CollectionDS.KEYWORDS, contentMetadataDto.getKeywords() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getKeywords()) : null);
        contentMetadataEditionForm.setValue(CollectionDS.SPATIAL_COVERAGE,
                contentMetadataDto.getSpatialCoverage() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverage()) : null);
        contentMetadataEditionForm.setValue(CollectionDS.SPATIAL_COVERAGE_CODES,
                contentMetadataDto.getSpatialCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getSpatialCoverageCodes()) : null);
        contentMetadataEditionForm.setValue(CollectionDS.TEMPORAL_COVERAGE,
                contentMetadataDto.getTemporalCoverage() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverage()) : null);
        contentMetadataEditionForm.setValue(CollectionDS.TEMPORAL_COVERAGE_CODES,
                contentMetadataDto.getTemporalCoverageCodes() != null ? CommonWebUtils.getStringListToString(contentMetadataDto.getTemporalCoverageCodes()) : null);
        contentMetadataEditionForm.setValue(CollectionDS.TYPE, CommonUtils.getStatisticalResourceTypeName(contentMetadataDto.getType()));
        contentMetadataEditionForm.setValue(CollectionDS.FORMAT, CommonUtils.getStatisticalResourceFormatName(contentMetadataDto.getFormat()));
        contentMetadataEditionForm.setValue(CollectionDS.NEXT_UPDATE_DATE, DateUtils.getFormattedDate(contentMetadataDto.getNextUpdateDate()));
        contentMetadataEditionForm.setValue(CollectionDS.UPDATE_FREQUENCY, contentMetadataDto.getUpdateFrequency());
        contentMetadataEditionForm.setValue(CollectionDS.RIGHTS_HOLDER, contentMetadataDto.getRightsHolder());
        contentMetadataEditionForm.setValue(CollectionDS.COPYRIGHTED_DATE, DateUtils.getFormattedDate(contentMetadataDto.getCopyrightedDate()));
        contentMetadataEditionForm.setValue(CollectionDS.LICENSE, contentMetadataDto.getLicense());
    }

    private CollectionDto getCollectionDto() {
        // Identifiers form
        collectionDto.setIdentifier(identifiersEditionForm.getValueAsString(CollectionDS.IDENTIFIER));
        collectionDto.setTitle((InternationalStringDto) identifiersEditionForm.getValue(CollectionDS.TITLE));

        // Version form
        collectionDto.setRationale(versionEditionForm.getValueAsString(CollectionDS.RATIONALE));

        // Life cycle form

        // Content metadata form
        if (collectionDto.getContentMetadata() == null) {
            collectionDto.setContentMetadata(new ContentMetadataDto());
        }
        collectionDto.getContentMetadata().setDescription((InternationalStringDto) contentMetadataEditionForm.getValue(CollectionDS.DESCRIPTION));

        return collectionDto;
    }

}
