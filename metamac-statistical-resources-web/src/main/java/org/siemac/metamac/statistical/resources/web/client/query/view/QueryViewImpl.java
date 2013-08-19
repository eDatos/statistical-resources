package org.siemac.metamac.statistical.resources.web.client.query.view;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.Date;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesDefaults;
import org.siemac.metamac.statistical.resources.web.client.enums.StatisticalResourcesToolStripButtonEnum;
import org.siemac.metamac.statistical.resources.web.client.query.presenter.QueryListPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.presenter.QueryPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.view.handlers.QueryUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.query.view.widgets.QueryMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms.QueryIdentifiersCreationForm;
import org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms.QueryProductionDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.query.view.widgets.forms.QueryProductionDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.ProgramPublicationWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.VersionWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceContentDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceContentDescriptorsForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceLifeCycleForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceVersionEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.LifeCycleResourceVersionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.NameableResourceIdentifiersForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceThematicContentClassifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceThematicContentClassifiersForm;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.utils.RelatedResourceUtils;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class QueryViewImpl extends ViewWithUiHandlers<QueryUiHandlers> implements QueryPresenter.QueryView {

    private VLayout        panel;

    private QueryFormPanel queryFormPanel;

    @Inject
    public QueryViewImpl() {
        super();
        panel = new VLayout();
        panel.setHeight100();

        VLayout subPanel = new VLayout();
        subPanel.setOverflow(Overflow.SCROLL);
        subPanel.setMembersMargin(5);
        subPanel.setMargin(15);

        queryFormPanel = new QueryFormPanel();
        queryFormPanel.setWidth("99%");

        subPanel.addMember(queryFormPanel);
        panel.addMember(subPanel);
    }

    @Override
    public Widget asWidget() {
        return panel;
    }

    @Override
    public void setUiHandlers(QueryUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        queryFormPanel.productionDescriptorsEditionForm.setUiHandlers(uiHandlers);
        queryFormPanel.productionDescriptorsForm.setUiHandlers(uiHandlers);
    }

    @Override
    public void setQueryDto(QueryVersionDto queryDto) {
        queryFormPanel.setQuery(queryDto);
    }

    @Override
    public void newQueryDto() {
        queryFormPanel.createQuery();
    }

    @Override
    public void setDatasetsForQuery(GetDatasetVersionsResult result) {
        List<RelatedResourceDto> relatedResourceDtos = RelatedResourceUtils.getDatasetVersionDtosAsRelatedResourceDtos(result.getDatasetVersionDtos());
        queryFormPanel.productionDescriptorsEditionForm.setDatasetsForQuery(relatedResourceDtos, result.getFirstResultOut(), relatedResourceDtos.size(), result.getTotalResults());
    }

    @Override
    public void setStatisticalOperationsForDatasetSelection(GetStatisticalOperationsPaginatedListResult result) {
        queryFormPanel.productionDescriptorsEditionForm.setStatisticalOperationsForDatasetSelection(result.getOperationsList());
    }

    @Override
    public void setDatasetDimensionsIds(List<String> datasetDimensionsIds) {
        queryFormPanel.productionDescriptorsEditionForm.setDatasetDimensionsIds(datasetDimensionsIds);
    }

    @Override
    public void setDatasetDimensionCodes(String dimensionId, List<CodeItemDto> codesDimension) {
        queryFormPanel.productionDescriptorsEditionForm.setDatasetDimensionCodes(dimensionId, codesDimension);
    }

    @Override
    public void setAgencySchemesForMaintainer(GetAgencySchemesPaginatedListResult result) {
        queryFormPanel.productionDescriptorsEditionForm
                .setAgencySchemesForMaintainer(result.getAgencySchemes(), result.getFirstResultOut(), result.getAgencySchemes().size(), result.getTotalResults());
    }

    @Override
    public void setAgenciesForMaintainer(GetAgenciesPaginatedListResult result) {
        queryFormPanel.productionDescriptorsEditionForm.setExternalItemsForMaintainer(result.getAgencies(), result.getFirstResultOut(), result.getAgencies().size(), result.getTotalResults());
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == QueryListPresenter.TYPE_SetContextAreaContentOperationResourcesToolBar) {
            if (content != null) {
                Canvas[] canvas = ((ToolStrip) content).getMembers();
                for (int i = 0; i < canvas.length; i++) {
                    if (canvas[i] instanceof ToolStripButton) {
                        if (StatisticalResourcesToolStripButtonEnum.QUERIES.getValue().equals(((ToolStripButton) canvas[i]).getID())) {
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

    private class QueryFormPanel extends VLayout {

        private QueryMainFormLayout                                      mainFormLayout;

        private NameableResourceIdentifiersForm                          identifiersForm;
        private LifeCycleResourceContentDescriptorsForm                  contentDescriptorsForm;
        private StatisticalResourceThematicContentClassifiersForm        thematicContentClassifiersForm;
        private QueryProductionDescriptorsForm                           productionDescriptorsForm;
        private LifeCycleResourceLifeCycleForm                           lifeCycleForm;
        private LifeCycleResourceVersionForm                             versionForm;

        // only creation
        private QueryIdentifiersCreationForm                             identifiersCreationForm;
        // Only edition
        private NameableResourceIdentifiersEditionForm                   identifiersEditionForm;

        private LifeCycleResourceContentDescriptorsEditionForm           contentDescriptorsEditionForm;
        private StatisticalResourceThematicContentClassifiersEditionForm thematicContentClassifiersEditionForm;
        private QueryProductionDescriptorsEditionForm                    productionDescriptorsEditionForm;
        private LifeCycleResourceLifeCycleForm                           lifeCycleEditionForm;
        private LifeCycleResourceVersionEditionForm                      versionEditionForm;

        private QueryVersionDto                                          queryVersionDto;

        public QueryFormPanel() {
            super();
            setWidth("99%");

            mainFormLayout = new QueryMainFormLayout();
            mainFormLayout.setMargin(0);

            this.addMember(mainFormLayout);
            createViewForm();
            createEditionForm();

            bindMainFormLayoutEvents();
            this.hide();
        }

        private void bindMainFormLayoutEvents() {
            mainFormLayout.getTranslateToolStripButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    boolean translationsShowed = mainFormLayout.getTranslateToolStripButton().isSelected();
                    identifiersForm.setTranslationsShowed(translationsShowed);
                    identifiersCreationForm.setTranslationsShowed(translationsShowed);
                    identifiersEditionForm.setTranslationsShowed(translationsShowed);

                    contentDescriptorsForm.setTranslationsShowed(translationsShowed);
                    contentDescriptorsEditionForm.setTranslationsShowed(translationsShowed);

                    thematicContentClassifiersForm.setTranslationsShowed(translationsShowed);
                    thematicContentClassifiersEditionForm.setTranslationsShowed(translationsShowed);

                    productionDescriptorsForm.setTranslationsShowed(translationsShowed);
                    productionDescriptorsEditionForm.setTranslationsShowed(translationsShowed);

                    lifeCycleForm.setTranslationsShowed(translationsShowed);
                    lifeCycleEditionForm.setTranslationsShowed(translationsShowed);

                    versionForm.setTranslationsShowed(translationsShowed);
                    versionEditionForm.setTranslationsShowed(translationsShowed);
                }
            });

            // Save

            mainFormLayout.getSave().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    if (isCreationMode()) {
                        if (identifiersCreationForm.validate(false) && productionDescriptorsEditionForm.validate(false) && versionEditionForm.validate(false)
                                && contentDescriptorsEditionForm.validate(false)) {
                            getUiHandlers().saveQuery(getQuery());
                        }
                    } else {
                        if (identifiersEditionForm.validate(false) && productionDescriptorsEditionForm.validate(false) && versionEditionForm.validate(false)
                                && contentDescriptorsEditionForm.validate(false)) {
                            getUiHandlers().saveQuery(getQuery());
                        }
                    }
                }
            });

            // Delete

            mainFormLayout.getDeleteConfirmationWindow().getYesButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    getUiHandlers().deleteQuery(queryVersionDto.getUrn());
                }
            });

            // Cancel

            mainFormLayout.getCancelToolStripButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    if (isCreationMode()) {
                        QueryFormPanel.this.hide();
                        QueryViewImpl.this.getUiHandlers().goToQueries();
                    }
                }
            });

            // Life cycle

            mainFormLayout.getProductionValidationButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    getUiHandlers().sendToProductionValidation(queryVersionDto);
                }
            });
            mainFormLayout.getDiffusionValidationButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    getUiHandlers().sendToDiffusionValidation(queryVersionDto);
                }
            });
            mainFormLayout.getRejectValidationButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    getUiHandlers().rejectValidation(queryVersionDto);
                }
            });
            mainFormLayout.getPublishButton().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    getUiHandlers().publish(queryVersionDto);
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
                                Date selectedDate = window.getSelectedDate();
                                // TODO Send to date and hour selected to service
                                getUiHandlers().programPublication(queryVersionDto);
                                window.destroy();
                            }
                        }
                    });
                }
            });
            mainFormLayout.getCancelProgrammedPublication().addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    // TODO Auto-generated method stub

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
                                getUiHandlers().version(queryVersionDto, versionWindow.getSelectedVersion());
                                versionWindow.destroy();
                            }
                        }
                    });
                }
            });
        }

        private void createViewForm() {
            identifiersForm = new NameableResourceIdentifiersForm();
            thematicContentClassifiersForm = new StatisticalResourceThematicContentClassifiersForm();
            contentDescriptorsForm = new LifeCycleResourceContentDescriptorsForm();
            productionDescriptorsForm = new QueryProductionDescriptorsForm();
            lifeCycleForm = new LifeCycleResourceLifeCycleForm();
            versionForm = new LifeCycleResourceVersionForm();
            mainFormLayout.addViewCanvas(identifiersForm);
            mainFormLayout.addViewCanvas(contentDescriptorsForm);
            mainFormLayout.addViewCanvas(thematicContentClassifiersForm);
            mainFormLayout.addViewCanvas(productionDescriptorsForm);
            mainFormLayout.addViewCanvas(lifeCycleForm);
            mainFormLayout.addViewCanvas(versionForm);
        }

        private void createEditionForm() {
            identifiersEditionForm = new NameableResourceIdentifiersEditionForm();
            identifiersCreationForm = new QueryIdentifiersCreationForm();
            contentDescriptorsEditionForm = new LifeCycleResourceContentDescriptorsEditionForm();
            thematicContentClassifiersEditionForm = new StatisticalResourceThematicContentClassifiersEditionForm();
            productionDescriptorsEditionForm = new QueryProductionDescriptorsEditionForm();
            lifeCycleEditionForm = new LifeCycleResourceLifeCycleForm();
            versionEditionForm = new LifeCycleResourceVersionEditionForm();

            mainFormLayout.addEditionCanvas(identifiersEditionForm);
            mainFormLayout.addEditionCanvas(identifiersCreationForm);
            mainFormLayout.addEditionCanvas(contentDescriptorsEditionForm);
            mainFormLayout.addEditionCanvas(thematicContentClassifiersEditionForm);
            mainFormLayout.addEditionCanvas(productionDescriptorsEditionForm);
            mainFormLayout.addEditionCanvas(lifeCycleEditionForm);
            mainFormLayout.addEditionCanvas(versionEditionForm);
        }

        private void createQuery() {
            this.queryVersionDto = new QueryVersionDto();
            this.queryVersionDto.setMaintainer(StatisticalResourcesDefaults.defaultAgency);

            mainFormLayout.setTitleLabelContents(getConstants().queryNew());
            mainFormLayout.setEditionMode();
            fillViewForm(queryVersionDto);
            fillEditionForm(queryVersionDto);

            mainFormLayout.redraw();
            this.show();
        }

        private void setQuery(QueryVersionDto queryVersionDto) {
            this.queryVersionDto = queryVersionDto;

            mainFormLayout.setQueryVersion(queryVersionDto);
            mainFormLayout.setViewMode();

            fillViewForm(queryVersionDto);
            fillEditionForm(queryVersionDto);
            mainFormLayout.redraw();
            this.show();
        }

        private void fillViewForm(QueryVersionDto queryDto) {
            identifiersForm.setNameableStatisticalResourceDto(queryDto);
            contentDescriptorsForm.setLifeCycleResource(queryDto);
            thematicContentClassifiersForm.setStatisticalResourceDto(queryDto);
            productionDescriptorsForm.setQueryDto(queryDto);
            lifeCycleForm.setLifeCycleStatisticalResourceDto(queryDto);
            versionForm.setLifeCycleStatisticalResourceDto(queryDto);
        }

        private void fillEditionForm(QueryVersionDto queryDto) {
            identifiersEditionForm.setNameableStatisticalResourceDto(queryDto);
            identifiersCreationForm.setNameableStatisticalResourceDto(queryDto);
            contentDescriptorsEditionForm.setLifeCycleStatisticalResourceDto(queryDto);
            thematicContentClassifiersEditionForm.setStatisticalResourceDto(queryDto);

            // WORKAROUND, this form is continuously rebuilt
            mainFormLayout.removeEditionCanvas(productionDescriptorsEditionForm);
            productionDescriptorsEditionForm = new QueryProductionDescriptorsEditionForm();
            productionDescriptorsEditionForm.setUiHandlers(getUiHandlers());
            productionDescriptorsEditionForm.setQueryDto(queryDto);
            mainFormLayout.addEditionCanvas(productionDescriptorsEditionForm, 4);

            // WORKAROUND

            lifeCycleEditionForm.setLifeCycleStatisticalResourceDto(queryDto);
            if (isCreationMode()) {
                identifiersEditionForm.hide();
                lifeCycleEditionForm.hide();
                identifiersCreationForm.show();
            } else {
                identifiersEditionForm.show();
                lifeCycleEditionForm.show();
                identifiersCreationForm.hide();
            }
            versionEditionForm.setLifeCycleStatisticalResourceDto(queryDto);
        }

        private QueryVersionDto getQuery() {
            if (isCreationMode()) {
                queryVersionDto = (QueryVersionDto) identifiersCreationForm.getNameableStatisticalResourceDto(queryVersionDto);
            } else {
                queryVersionDto = (QueryVersionDto) identifiersEditionForm.getNameableStatisticalResourceDto(queryVersionDto);
            }
            queryVersionDto = (QueryVersionDto) contentDescriptorsEditionForm.getLifeCycleStatisticalResourceDto(queryVersionDto);
            queryVersionDto = productionDescriptorsEditionForm.getQueryDto(queryVersionDto);
            queryVersionDto = (QueryVersionDto) versionEditionForm.getLifeCycleStatisticalResourceDto(queryVersionDto);
            return queryVersionDto;
        }

        private boolean isCreationMode() {
            return StringUtils.isEmpty(this.queryVersionDto.getUrn());
        }
    }
}
