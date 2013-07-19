package org.siemac.metamac.statistical.resources.web.client.dataset.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.StatisticalResourceMetadataBaseViewImpl;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetMetadataTabPresenter.DatasetMetadataTabView;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetMetadataExternalField;
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
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceCommonMetadataEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceCommonMetadataForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceIntellectualPropertyDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceIntellectualPropertyDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceLanguageEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceLanguageForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceProductionDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcePublicationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceResourceRelationDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceThematicContentClassifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceThematicContentClassifiersForm;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptSchemesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetGeographicalGranularitiesListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetTemporalGranularitiesListResult;
import org.siemac.metamac.statistical.resources.web.shared.utils.RelatedResourceUtils;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;

public class DatasetMetadataTabViewImpl extends StatisticalResourceMetadataBaseViewImpl<DatasetMetadataTabUiHandlers> implements DatasetMetadataTabView {

    private VLayout                                                       panel;
    private DatasetMainFormLayout                                         mainFormLayout;

    private NameableResourceIdentifiersForm                               identifiersForm;
    private DatasetContentDescriptorsForm                                 contentDescriptorsForm;
    private StatisticalResourceCommonMetadataForm                         commonMetadataForm;
    private StatisticalResourceThematicContentClassifiersForm             thematicContentClassifiersForm;
    private StatisticalResourceLanguageForm                               languageForm;
    private DatasetProductionDescriptorsForm                              productionDescriptorsForm;
    private DatasetClassDescriptorsForm                                   classDescriptorsForm;
    private StatisticalResourceResourceRelationDescriptorsForm            resourceRelationDescriptorsForm;
    private DatasetPublicationDescriptorsForm                             publicationDescriptorsForm;
    private LifeCycleResourceLifeCycleForm                                lifeCycleForm;
    private LifeCycleResourceVersionForm                                  versionForm;
    private StatisticalResourceIntellectualPropertyDescriptorsForm        intellectualPropertyDescriptorsForm;

    private NameableResourceIdentifiersEditionForm                        identifiersEditionForm;
    private DatasetContentDescriptorsEditionForm                          contentDescriptorsEditionForm;
    private StatisticalResourceCommonMetadataEditionForm                  commonMetadataEditionForm;
    private StatisticalResourceThematicContentClassifiersEditionForm      thematicContentClassifiersEditionForm;
    private StatisticalResourceLanguageEditionForm                        languageEditionForm;
    private DatasetProductionDescriptorsEditionForm                       productionDescriptorsEditionForm;
    private DatasetClassDescriptorsEditionForm                            classDescriptorsEditionForm;
    private DatasetResourceRelationDescriptorsEditionForm                 resourceRelationDescriptorsEditionForm;
    private DatasetPublicationDescriptorsEditionForm                      publicationDescriptorsEditionForm;
    private LifeCycleResourceLifeCycleForm                                lifeCycleEditionForm;
    private LifeCycleResourceVersionEditionForm                           versionEditionForm;
    private StatisticalResourceIntellectualPropertyDescriptorsEditionForm intellectualPropertyDescriptorsEditionForm;

    // private SearchExternalItemWindow searchAgencyWindow;
    // private SearchMultipleExternalItemWindow searchMultiAgencyWindow;

    private DatasetVersionDto                                                    datasetDto;

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
        resourceRelationDescriptorsForm.setUiHandlers(uiHandlers);
        commonMetadataForm.setBaseUiHandlers(uiHandlers);
        contentDescriptorsEditionForm.setUiHandlers(uiHandlers);
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
                    getUiHandlers().saveDataset(getDatasetVersionDto());
                }
            }
        });

        // Life cycle
        mainFormLayout.getProductionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().sendToProductionValidation(datasetDto);
            }
        });
        mainFormLayout.getDiffusionValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().sendToDiffusionValidation(datasetDto);
            }
        });
        mainFormLayout.getRejectValidationButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().rejectValidation(datasetDto);
            }
        });

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
                getUiHandlers().publish(datasetDto);
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
                            getUiHandlers().version(datasetDto, versionWindow.getSelectedVersion());
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

        // Intellectual property descriptors
        intellectualPropertyDescriptorsForm = new StatisticalResourceIntellectualPropertyDescriptorsForm();
        mainFormLayout.addViewCanvas(intellectualPropertyDescriptorsForm);
    }

    private void createEditionForm() {
        // Identifiers form
        identifiersEditionForm = new NameableResourceIdentifiersEditionForm();
        mainFormLayout.addEditionCanvas(identifiersEditionForm);

        // Content descriptors form
        contentDescriptorsEditionForm = new DatasetContentDescriptorsEditionForm();
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

        // Intellectual property descriptors
        intellectualPropertyDescriptorsEditionForm = new StatisticalResourceIntellectualPropertyDescriptorsEditionForm();
        mainFormLayout.addEditionCanvas(intellectualPropertyDescriptorsEditionForm);

    }

    @Override
    public void setDataset(DatasetVersionDto datasetDto) {
        this.datasetDto = datasetDto;

        mainFormLayout.updatePublishSection(datasetDto.getProcStatus());

        mainFormLayout.setViewMode();

        setDatasetViewMode(datasetDto);
        setDatasetEditionMode(datasetDto);
    }

    private void setDatasetViewMode(DatasetVersionDto datasetDto) {
        // Identifiers
        identifiersForm.setNameableStatisticalResourceDto(datasetDto);

        // Content descriptors
        contentDescriptorsForm.setDatasetVersionDto(datasetDto);

        // Common metadata
        commonMetadataForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Thematic content classifiers
        thematicContentClassifiersForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Languages
        languageForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Production descriptors
        productionDescriptorsForm.setDatasetVersionDto(datasetDto);

        // Class descriptors
        classDescriptorsForm.setDatasetVersionDto(datasetDto);

        // Resource relation descriptors
        resourceRelationDescriptorsForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Publication descriptors
        publicationDescriptorsForm.setDatasetVersionDto(datasetDto);

        // Life cycle
        lifeCycleForm.setLifeCycleStatisticalResourceDto(datasetDto);

        // Version
        versionForm.setLifeCycleStatisticalResourceDto(datasetDto);

        // Intellectual property descriptors
        intellectualPropertyDescriptorsForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

    }

    private void setDatasetEditionMode(DatasetVersionDto datasetDto) {
        // Identifiers form
        identifiersEditionForm.setNameableStatisticalResourceDto(datasetDto);

        // Content Descriptors
        contentDescriptorsEditionForm.setDatasetVersionDto(datasetDto);

        // Common metadata
        commonMetadataEditionForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Thematic content classifiers
        thematicContentClassifiersEditionForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Languages
        languageEditionForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Production descriptors
        productionDescriptorsEditionForm.setDatasetVersionDto(datasetDto);

        // Class descriptors
        classDescriptorsEditionForm.setDatasetVersionDto(datasetDto);

        // Resource relation descriptors
        resourceRelationDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

        // Publication descriptors
        publicationDescriptorsEditionForm.setDatasetVersionDto(datasetDto);

        // Life cycle
        lifeCycleEditionForm.setLifeCycleStatisticalResourceDto(datasetDto);

        // Version
        versionEditionForm.setLifeCycleStatisticalResourceDto(datasetDto);

        // Intellectual property descriptors
        intellectualPropertyDescriptorsEditionForm.setSiemacMetadataStatisticalResourceDto(datasetDto);

    }

    public DatasetVersionDto getDatasetVersionDto() {
        // Identifiers form
        datasetDto = (DatasetVersionDto) identifiersEditionForm.getNameableStatisticalResourceDto(datasetDto);

        // Content descriptors form
        datasetDto = contentDescriptorsEditionForm.getDatasetVersionDto(datasetDto);

        // Common metadata
        datasetDto = (DatasetVersionDto) commonMetadataEditionForm.getSiemacMetadataStatisticalResourceDto(datasetDto);

        // Thematic content classifiers
        datasetDto = (DatasetVersionDto) thematicContentClassifiersEditionForm.getSiemacMetadataStatisticalResourceDto(datasetDto);

        // Language
        datasetDto = (DatasetVersionDto) languageEditionForm.getSiemacMetadataStatisticalResourceDto(datasetDto);

        // Production descriptors
        datasetDto = productionDescriptorsEditionForm.getDatasetVersionDto(datasetDto);

        // Class descriptors
        datasetDto = classDescriptorsEditionForm.getDatasetVersionDto(datasetDto);

        // Resource relation descriptors
        datasetDto = (DatasetVersionDto) resourceRelationDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(datasetDto);

        // Publication descriptors
        datasetDto = publicationDescriptorsEditionForm.getDatasetVersionDto(datasetDto);

        // Version
        datasetDto = (DatasetVersionDto) versionEditionForm.getLifeCycleStatisticalResourceDto(datasetDto);

        // Intellectual property descriptors
        datasetDto = (DatasetVersionDto) intellectualPropertyDescriptorsEditionForm.getSiemacMetadataStatisticalResourceDto(datasetDto);

        return datasetDto;
    }

    @Override
    public void setDatasetsForReplaces(GetDatasetsResult result) {
        List<RelatedResourceDto> relatedResourceDtos = RelatedResourceUtils.getDatasetVersionDtosAsRelatedResourceDtos(result.getDatasetVersionDtos());
        resourceRelationDescriptorsEditionForm.setRelatedResourcesForReplaces(relatedResourceDtos, result.getFirstResultOut(), relatedResourceDtos.size(), result.getTotalResults());
    }

    @Override
    public void setDatasetsForIsReplacedBy(GetDatasetsResult result) {
        List<RelatedResourceDto> relatedResourceDtos = RelatedResourceUtils.getDatasetVersionDtosAsRelatedResourceDtos(result.getDatasetVersionDtos());
        resourceRelationDescriptorsEditionForm.setRelatedResourcesForIsReplacedBy(relatedResourceDtos, result.getFirstResultOut(), relatedResourceDtos.size(), result.getTotalResults());
    }

    @Override
    public void setStatisticalOperationsForDsdSelection(List<ExternalItemDto> results, ExternalItemDto defaultSelected) {
        productionDescriptorsEditionForm.setStatisticalOperationsForRelatedDsd(results, defaultSelected, null);
    }

    @Override
    public void setDsdsForRelatedDsd(GetDsdsPaginatedListResult result) {
        List<ExternalItemDto> externalItemsDtos = result.getDsdsList();
        productionDescriptorsEditionForm.setExternalItemsForRelatedDsd(externalItemsDtos, result.getFirstResultOut(), result.getTotalResults());
    }

    @Override
    public void setCodesForGeographicalGranularities(GetGeographicalGranularitiesListResult result) {
        List<ExternalItemDto> externalItemsDtos = result.getGeographicalGranularities();
        contentDescriptorsEditionForm.setCodesForGeographicalGranularities(externalItemsDtos, result.getFirstResultOut(), result.getTotalResults());
    }

    @Override
    public void setTemporalCodesForField(GetTemporalGranularitiesListResult result, DatasetMetadataExternalField field) {
        List<ExternalItemDto> externalItemsDtos = result.getTemporalGranularities();
        switch (field) {
            case TEMPORAL_GRANULARITY:
                contentDescriptorsEditionForm.setCodesForTemporalGranularities(externalItemsDtos, result.getFirstResultOut(), result.getTotalResults());
                break;
            case UPDATE_FREQUENCY:
                publicationDescriptorsEditionForm.setCodesForUpdateFrequency(externalItemsDtos, result.getFirstResultOut(), result.getTotalResults());
                break;
        }
    }

    @Override
    public void setConceptSchemesForStatisticalUnit(GetConceptSchemesPaginatedListResult result) {
        List<ExternalItemDto> externalItemsDtos = result.getConceptSchemes();
        contentDescriptorsEditionForm.setConceptSchemesForStatisticalUnit(externalItemsDtos, result.getFirstResultOut(), result.getTotalResults());
    }

    @Override
    public void setConceptsForStatisticalUnit(GetConceptsPaginatedListResult result) {
        List<ExternalItemDto> externalItemsDtos = result.getConcepts();
        contentDescriptorsEditionForm.setConceptsForStatisticalUnit(externalItemsDtos, result.getFirstResultOut(), result.getTotalResults());
    }

    // Generic forms for parent
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
