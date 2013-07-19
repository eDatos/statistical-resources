package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getRelatedResourceValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setRelatedResourceValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setRelatedResourcesValue;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceLinkItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceListItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchRelatedResourceLinkItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleRelatedResourcePaginatedWindow;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.shared.RelatedResourceBaseUtils;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.gwtplatform.mvp.client.UiHandlers;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public abstract class StatisticalResourceResourceRelationDescriptorsEditionForm extends NavigationEnabledDynamicForm {

    private SearchSingleRelatedResourcePaginatedWindow searchReplacesWindow;
    private SearchSingleRelatedResourcePaginatedWindow searchIsReplacedByWindow;

    public StatisticalResourceResourceRelationDescriptorsEditionForm() {
        super(getConstants().formResourceRelationDescriptors());

        SearchRelatedResourceLinkItem replaces = createReplacesItem(StatisticalResourceDS.REPLACES, getConstants().siemacMetadataStatisticalResourceReplaces());
        RelatedResourceLinkItem isReplacedBy = new RelatedResourceLinkItem(StatisticalResourceDS.IS_REPLACED_BY, getConstants().siemacMetadataStatisticalResourceIsReplacedBy(), getCustomLinkItemNavigationClickHandler()); 
        RelatedResourceListItem requires = new RelatedResourceListItem(StatisticalResourceDS.REQUIRES, getConstants().siemacMetadataStatisticalResourceRequires(), false, getRecordNavigationHandler());
        RelatedResourceListItem isRequiredBy = new RelatedResourceListItem(StatisticalResourceDS.IS_REQUIRED_BY, getConstants().siemacMetadataStatisticalResourceIsRequiredBy(), false,
                getRecordNavigationHandler());
        RelatedResourceListItem hasPart = new RelatedResourceListItem(StatisticalResourceDS.HAS_PART, getConstants().siemacMetadataStatisticalResourceHasPart(), false, getRecordNavigationHandler());
        RelatedResourceListItem isPartOf = new RelatedResourceListItem(StatisticalResourceDS.IS_PART_OF, getConstants().siemacMetadataStatisticalResourceIsPartOf(), false,
                getRecordNavigationHandler());

        setFields(replaces, isReplacedBy, requires, isRequiredBy, hasPart, isPartOf);
    }

    //
    // SETTERS
    //

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        setRelatedResourceValue(getItem(StatisticalResourceDS.REPLACES), dto.getReplaces());
        setRelatedResourceValue(getItem(StatisticalResourceDS.IS_REPLACED_BY), dto.getIsReplacedBy());
        
        setRelatedResourcesValue(getItem(StatisticalResourceDS.REQUIRES), dto.getRequires());
        setRelatedResourcesValue(getItem(StatisticalResourceDS.IS_REQUIRED_BY), dto.getIsRequiredBy());
        
        setRelatedResourcesValue(getItem(StatisticalResourceDS.HAS_PART), dto.getHasPart());
        setRelatedResourcesValue(getItem(StatisticalResourceDS.IS_PART_OF), dto.getIsPartOf());
    }

    private void setReplaces(RelatedResourceDto relatedResourceDto) {
        setValue(StatisticalResourceDS.REPLACES, RelatedResourceBaseUtils.getRelatedResourceName(relatedResourceDto));
    }


    public void setRelatedResourcesForReplaces(List<RelatedResourceDto> relatedResourceDtos, int firstResult, int elementsInPage, int totalResults) {
        if (searchReplacesWindow != null) {
            searchReplacesWindow.setResources(relatedResourceDtos);
            searchReplacesWindow.refreshSourcePaginationInfo(firstResult, elementsInPage, totalResults);
        }
    }

    public void setRelatedResourcesForIsReplacedBy(List<RelatedResourceDto> relatedResourceDtos, int firstResult, int elementsInPage, int totalResults) {
        if (searchIsReplacedByWindow != null) {
            searchIsReplacedByWindow.setResources(relatedResourceDtos);
            searchIsReplacedByWindow.refreshSourcePaginationInfo(firstResult, elementsInPage, totalResults);
        }
    }

    //
    // GETTER
    //
    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        
        dto.setReplaces(getRelatedResourceValue(getItem(StatisticalResourceDS.REPLACES)));
        
        return dto;
    }

    //
    // FORM ITEM CREATION
    //
    private SearchRelatedResourceLinkItem createReplacesItem(String name, String title) {
        final SearchRelatedResourceLinkItem replacesItem = new SearchRelatedResourceLinkItem(name, title, getCustomLinkItemNavigationClickHandler());
        
        replacesItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchReplacesWindow = new SearchSingleRelatedResourcePaginatedWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<MetamacWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                                retrieveResourcesForReplaces(firstResult, maxResults, webCriteria);
                            }

                        });

                // Load resources (to populate the selection window)
                retrieveResourcesForReplaces(0, StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS, null);

                searchReplacesWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        RelatedResourceDto selectedResource = searchReplacesWindow.getSelectedResource();
                        searchReplacesWindow.markForDestroy();
                        // Set selected resource in form
                        setReplaces(selectedResource);
                        validate(false);
                    }
                });
            }
        });
        return replacesItem;
    }


    public abstract void setUiHandlers(UiHandlers uiHandlers);
    public abstract void retrieveResourcesForReplaces(int firstResult, int maxResults, MetamacWebCriteria criteria);
}
