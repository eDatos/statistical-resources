package org.siemac.metamac.statistical.resources.web.client.multidataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetCubeDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.multidataset.model.ds.MultidatasetCubeDS;
import org.siemac.metamac.statistical.resources.web.client.multidataset.utils.MultidatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.multidataset.view.handlers.MultidatasetStructureTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleStatisticalRelatedResourcePaginatedWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.StatisticalResourceWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueriesResult;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageRichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchCustomLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.google.gwt.core.client.Scheduler;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.VLayout;

public class MultidatasetStructureCubePanel extends VLayout {

    private InternationalMainFormLayout                           mainFormLayout;
    private GroupDynamicForm                                      form;
    private GroupDynamicForm                                      editionForm;

    private SearchSingleStatisticalRelatedResourcePaginatedWindow searchDatasetsWindow;
    private SearchSingleStatisticalRelatedResourcePaginatedWindow searchQueriesWindow;

    private MultidatasetVersionDto                                multidatasetVersion;
    private MultidatasetCubeDto                                   cube;

    private MultidatasetStructureTabUiHandlers                    uiHandlers;

    public MultidatasetStructureCubePanel() {

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
                    // See: METAMAC-2516
                    // Two invokes to getXXXDto() is needed for Chrome, please don't remove this two call fix.
                    getSelectedCube();
                    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

                        @Override
                        public void execute() {
                            getUiHandlers().saveMultidatasetCube(multidatasetVersion.getUrn(), getSelectedCube());
                        }
                    });

                }
            }
        });

        mainFormLayout.getCancelToolStripButton().addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                // Hide panel if the creation of a new Cube is canceled
                if (cube != null && cube.getId() == null) {
                    hide();
                }
            }
        });
    }

    private void createViewForm() {
        form = new GroupDynamicForm(getConstants().multidatasetStructureCube());
        form.setNumCols(2);
        form.setColWidths("30%", "70%");

        ViewMultiLanguageTextItem title = new ViewMultiLanguageTextItem(MultidatasetCubeDS.TITLE, getConstants().multidatasetStructureCubeTitle());

        ViewMultiLanguageTextItem description = new ViewMultiLanguageTextItem(MultidatasetCubeDS.DESCRIPTION, getConstants().multidatasetStructureCubeDescription());

        ViewTextItem urn = new ViewTextItem(MultidatasetCubeDS.URN, getConstants().multidatasetStructureCubeURN());

        CustomLinkItem dataset = new CustomLinkItem(MultidatasetCubeDS.DATASET, getConstants().dataset());
        dataset.setShowIfCondition(getIsNotEmptyFormItemIfFunction());
        dataset.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                String datasetUrn = editionForm.getValueAsString(MultidatasetCubeDS.DATASET);
                getUiHandlers().goToLastVersion(datasetUrn);
            }
        });

        CustomLinkItem query = new CustomLinkItem(MultidatasetCubeDS.QUERY, getConstants().query());
        query.setShowIfCondition(getIsNotEmptyFormItemIfFunction());
        query.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                String queryUrn = editionForm.getValueAsString(MultidatasetCubeDS.QUERY);
                getUiHandlers().goToLastVersion(queryUrn);
            }
        });

        form.setFields(title, description, urn, dataset, query);
        mainFormLayout.addViewCanvas(form);
    }

    private void createEditionForm() {
        editionForm = new GroupDynamicForm(getConstants().multidatasetStructureCube());
        editionForm.setNumCols(2);
        editionForm.setColWidths("30%", "70%");

        MultiLanguageTextItem title = new MultiLanguageTextItem(MultidatasetCubeDS.TITLE, getConstants().multidatasetStructureCubeTitle());
        title.setRequired(true);

        MultiLanguageRichTextEditorItem description = new MultiLanguageRichTextEditorItem(MultidatasetCubeDS.DESCRIPTION, getConstants().multidatasetStructureCubeDescription());

        ViewTextItem urn = new ViewTextItem(MultidatasetCubeDS.URN, getConstants().multidatasetStructureCubeURN());

        CustomSelectItem resourceTypeToLink = new CustomSelectItem(MultidatasetCubeDS.RESOURCE_TYPE_TO_LINK, getConstants().multidatasetStructureCubeResourceTypeToLink());
        resourceTypeToLink.setValueMap(CommonUtils.getStatisticalResourceTypeThatCanBeAddIntoACubeHashMap());
        resourceTypeToLink.setRequired(true);
        resourceTypeToLink.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                editionForm.markForRedraw();
            }
        });
        resourceTypeToLink.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                // Only show the the form containts a cube
                return cube != null && cube instanceof MultidatasetCubeDto;
            }
        });

        // Dataset

        SearchCustomLinkItem dataset = createSearchDatasetItem(MultidatasetCubeDS.DATASET, getConstants().dataset());
        dataset.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                String datasetUrn = editionForm.getValueAsString(MultidatasetCubeDS.DATASET);
                getUiHandlers().goToLastVersion(datasetUrn);
            }
        });
        dataset.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return StringUtils.equals(StatisticalResourceTypeEnum.DATASET.name(), editionForm.getValueAsString(MultidatasetCubeDS.RESOURCE_TYPE_TO_LINK));
            }
        });

        // Query

        SearchCustomLinkItem query = createSearchQueryItem(MultidatasetCubeDS.QUERY, getConstants().query());
        query.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

            @Override
            public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                String queryUrn = editionForm.getValueAsString(MultidatasetCubeDS.QUERY);
                getUiHandlers().goToLastVersion(queryUrn);
            }
        });
        query.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return StringUtils.equals(StatisticalResourceTypeEnum.QUERY.name(), editionForm.getValueAsString(MultidatasetCubeDS.RESOURCE_TYPE_TO_LINK));
            }
        });

        editionForm.setFields(title, description, urn, resourceTypeToLink, dataset, query);
        mainFormLayout.addEditionCanvas(editionForm);
    }

    public void setCube(MultidatasetCubeDto cube) {
        this.cube = cube;

        mainFormLayout.setTitleLabelContents(InternationalStringUtils.getLocalisedString(cube.getTitle()));
        mainFormLayout.setViewMode();

        setCubeViewMode(cube);

        mainFormLayout.setCanEdit(MultidatasetClientSecurityUtils.canUpdateCube(multidatasetVersion.getProcStatus()));
        setCubeEditionMode(cube);
    }

    private void setCubeViewMode(MultidatasetCubeDto cube) {

        form.setValue(MultidatasetCubeDS.TITLE, cube.getTitle());
        form.setValue(MultidatasetCubeDS.DESCRIPTION, cube.getDescription());
        form.setValue(MultidatasetCubeDS.URN, cube.getUrn());

        if (cube instanceof MultidatasetCubeDto) {
            MultidatasetCubeDto MultidatasetCubeDto = cube;
            form.setValue(MultidatasetCubeDS.DATASET, MultidatasetCubeDto.getDatasetUrn());
            form.setValue(MultidatasetCubeDS.QUERY, MultidatasetCubeDto.getQueryUrn());
        } else {
            form.setValue(MultidatasetCubeDS.DATASET, StringUtils.EMPTY);
            form.setValue(MultidatasetCubeDS.QUERY, StringUtils.EMPTY);
        }

        form.markForRedraw();
    }

    private void setCubeEditionMode(MultidatasetCubeDto cube) {

        editionForm.setValue(MultidatasetCubeDS.TITLE, cube.getTitle());
        editionForm.setValue(MultidatasetCubeDS.DESCRIPTION, cube.getDescription());
        editionForm.setValue(MultidatasetCubeDS.URN, cube.getUrn());

        if (cube instanceof MultidatasetCubeDto) {
            MultidatasetCubeDto MultidatasetCubeDto = cube;

            String resourceTypeToLink = StringUtils.EMPTY;
            if (!StringUtils.isBlank(MultidatasetCubeDto.getDatasetUrn())) {
                resourceTypeToLink = StatisticalResourceTypeEnum.DATASET.name();
            } else if (!StringUtils.isBlank(MultidatasetCubeDto.getQueryUrn())) {
                resourceTypeToLink = StatisticalResourceTypeEnum.QUERY.name();
            }
            editionForm.setValue(MultidatasetCubeDS.RESOURCE_TYPE_TO_LINK, resourceTypeToLink);
            setDatasetInEditionForm(MultidatasetCubeDto.getDatasetUrn());
            setQueryInEditionForm(MultidatasetCubeDto.getQueryUrn());
        } else {
            editionForm.setValue(MultidatasetCubeDS.RESOURCE_TYPE_TO_LINK, StringUtils.EMPTY);
            ((CustomLinkItem) editionForm.getItem(MultidatasetCubeDS.DATASET)).clearValue();
            ((CustomLinkItem) editionForm.getItem(MultidatasetCubeDS.QUERY)).clearValue();
        }

        editionForm.markForRedraw();

        if (cube.getId() == null) {
            editionForm.clearErrors(true);
        } else {
            editionForm.validate(false);
        }
    }

    private void setDatasetInEditionForm(String datasetUrn) {
        ((CustomLinkItem) editionForm.getItem(MultidatasetCubeDS.DATASET)).setValue(datasetUrn, null);
    }

    private void setQueryInEditionForm(String queryUrn) {
        ((CustomLinkItem) editionForm.getItem(MultidatasetCubeDS.QUERY)).setValue(queryUrn, null);
    }

    public MultidatasetCubeDto getSelectedCube() {

        cube.setTitle(editionForm.getValueAsInternationalStringDto(MultidatasetCubeDS.TITLE));
        cube.setDescription(editionForm.getValueAsInternationalStringDto(MultidatasetCubeDS.DESCRIPTION));

        if (StatisticalResourceTypeEnum.DATASET.name().equals(editionForm.getValueAsString(MultidatasetCubeDS.RESOURCE_TYPE_TO_LINK))) {
            cube.setDatasetUrn(editionForm.getValueAsString(MultidatasetCubeDS.DATASET));
            cube.setQueryUrn(null);
        } else if (StatisticalResourceTypeEnum.QUERY.name().equals(editionForm.getValueAsString(MultidatasetCubeDS.RESOURCE_TYPE_TO_LINK))) {
            cube.setQueryUrn(editionForm.getValueAsString(MultidatasetCubeDS.QUERY));
            cube.setDatasetUrn(null);
        }

        return cube;
    }

    public void setMultidatasetVersion(MultidatasetVersionDto multidatasetVersion) {
        this.multidatasetVersion = multidatasetVersion;
    }

    public MultidatasetStructureTabUiHandlers getUiHandlers() {
        return uiHandlers;
    }

    public void setUiHandlers(MultidatasetStructureTabUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    private FormItemIfFunction getIsNotEmptyFormItemIfFunction() {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                return value != null && !StringUtils.isBlank(value.toString());
            }
        };
    }

    public void setMainFormLayoutEditionMode() {
        mainFormLayout.setEditionMode();
    }

    //
    // FORM ITEMS
    //

    private SearchCustomLinkItem createSearchDatasetItem(String name, String title) {

        final SearchCustomLinkItem datasetItem = new SearchCustomLinkItem(name, title);
        datasetItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchDatasetsWindow = new SearchSingleStatisticalRelatedResourcePaginatedWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<StatisticalResourceWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, StatisticalResourceWebCriteria criteria) {
                                getUiHandlers().retrieveDatasetsForCubes(firstResult, maxResults, criteria);
                            }
                        });

                // Load statistical operations to filter datasets
                getUiHandlers().retrieveStatisticalOperationsForDatasetSelection();

                searchDatasetsWindow.setSaveAction(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        RelatedResourceDto selectedResource = searchDatasetsWindow.getSelectedResource();
                        searchDatasetsWindow.markForDestroy();
                        // Set selected resource in form
                        setDatasetInEditionForm(selectedResource != null ? selectedResource.getUrn() : StringUtils.EMPTY);
                        editionForm.validate(false);
                    }
                });
            }
        });
        datasetItem.setRequired(true);
        return datasetItem;
    }

    private SearchCustomLinkItem createSearchQueryItem(String name, String title) {

        final SearchCustomLinkItem queryItem = new SearchCustomLinkItem(name, title);
        queryItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchQueriesWindow = new SearchSingleStatisticalRelatedResourcePaginatedWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<StatisticalResourceWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, StatisticalResourceWebCriteria criteria) {
                                getUiHandlers().retrieveQueriesForCubes(firstResult, maxResults, criteria);
                            }
                        });

                // Load statistical operations to filter queries
                getUiHandlers().retrieveStatisticalOperationsForQuerySelection();

                searchQueriesWindow.setSaveAction(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        RelatedResourceDto selectedResource = searchQueriesWindow.getSelectedResource();
                        searchQueriesWindow.markForDestroy();
                        // Set selected resource in form
                        setQueryInEditionForm(selectedResource != null ? selectedResource.getUrn() : StringUtils.EMPTY);
                        editionForm.validate(false);
                    }
                });
            }
        });
        queryItem.setRequired(true);
        return queryItem;
    }

    //
    // RELATED RESOURCES
    //

    public void setStatisticalOperationsForDatasetSelection(GetStatisticalOperationsPaginatedListResult result) {
        if (searchDatasetsWindow != null) {
            searchDatasetsWindow.setStatisticalOperations(result.getOperationsList());
            getUiHandlers().retrieveDatasetsForCubes(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, searchDatasetsWindow.getSearchCriteria());
        }
    }

    public void setDatasetsForCubes(GetDatasetsResult result) {
        if (searchDatasetsWindow != null) {
            searchDatasetsWindow.setResources(result.getDatasets());
            searchDatasetsWindow.refreshSourcePaginationInfo(result.getFirstResultOut(), result.getDatasets().size(), result.getTotalResults());
        }
    }

    public void setStatisticalOperationsForQuerySelection(GetStatisticalOperationsPaginatedListResult result) {
        if (searchQueriesWindow != null) {
            searchQueriesWindow.setStatisticalOperations(result.getOperationsList());
            getUiHandlers().retrieveQueriesForCubes(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, searchQueriesWindow.getSearchCriteria());
        }
    }

    public void setQueriesForCubes(GetQueriesResult result) {
        if (searchQueriesWindow != null) {
            searchQueriesWindow.setResources(result.getQueries());
            searchQueriesWindow.refreshSourcePaginationInfo(result.getFirstResultOut(), result.getQueries().size(), result.getTotalResults());
        }
    }
}
