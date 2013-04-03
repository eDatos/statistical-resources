package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.SearchRelatedResourcePaginatedWindow;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceListItem;
import org.siemac.metamac.web.common.client.widgets.actions.PaginatedAction;
import org.siemac.metamac.web.common.client.widgets.actions.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchViewTextItem;
import org.siemac.metamac.web.common.shared.RelatedResourceBaseUtils;

import com.gwtplatform.mvp.client.UiHandlers;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public abstract class StatisticalResourceResourceRelationDescriptorsEditionForm extends GroupDynamicForm {

    private RelatedResourceDto                   replaces;
    private RelatedResourceDto                   isReplacedBy;

    private SearchRelatedResourcePaginatedWindow searchReplacesWindow;
    private SearchRelatedResourcePaginatedWindow searchIsReplacedByWindow;

    public StatisticalResourceResourceRelationDescriptorsEditionForm() {
        super(getConstants().formResourceRelationDescriptors());

        // TODO SOURCE

        SearchViewTextItem replaces = createReplacesItem(StatisticalResourceDS.REPLACES, getConstants().siemacMetadataStatisticalResourceReplaces());

        SearchViewTextItem isReplacedBy = createIsReplacedByItem(StatisticalResourceDS.IS_REPLACED_BY, getConstants().siemacMetadataStatisticalResourceIsReplacedBy());

        RelatedResourceListItem requires = new RelatedResourceListItem(StatisticalResourceDS.REQUIRES, getConstants().siemacMetadataStatisticalResourceRequires(), false);

        RelatedResourceListItem isRequiredBy = new RelatedResourceListItem(StatisticalResourceDS.IS_REQUIRED_BY, getConstants().siemacMetadataStatisticalResourceIsRequiredBy(), false);

        RelatedResourceListItem hasPart = new RelatedResourceListItem(StatisticalResourceDS.HAS_PART, getConstants().siemacMetadataStatisticalResourceHasPart(), false);

        RelatedResourceListItem isPartOf = new RelatedResourceListItem(StatisticalResourceDS.IS_PART_OF, getConstants().siemacMetadataStatisticalResourceIsPartOf(), false);

        // TODO IS_REFERENCE_BY

        // TODO REFERENCES

        // TODO IS_FORMAT_OF

        // TODO HAS_FORMAT

        setFields(replaces, isReplacedBy, requires, isRequiredBy, hasPart, isPartOf);
    }

    //
    // SETTERS
    //

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {

        // TODO SOURCE

        setReplaces(siemacMetadataStatisticalResourceDto.getReplaces());

        setIsReplacedBy(siemacMetadataStatisticalResourceDto.getIsReplacedBy());

        ((RelatedResourceListItem) getItem(StatisticalResourceDS.REQUIRES)).setRelatedResources(siemacMetadataStatisticalResourceDto.getRequires());

        ((RelatedResourceListItem) getItem(StatisticalResourceDS.IS_REQUIRED_BY)).setRelatedResources(siemacMetadataStatisticalResourceDto.getIsRequiredBy());

        ((RelatedResourceListItem) getItem(StatisticalResourceDS.HAS_PART)).setRelatedResources(siemacMetadataStatisticalResourceDto.getHasPart());

        ((RelatedResourceListItem) getItem(StatisticalResourceDS.IS_PART_OF)).setRelatedResources(siemacMetadataStatisticalResourceDto.getIsPartOf());

        // TODO IS_REFERENCE_BY

        // TODO REFERENCES

        // TODO IS_FORMAT_OF

        // TODO HAS_FORMAT
    }

    private void setReplaces(RelatedResourceDto relatedResourceDto) {
        setValue(StatisticalResourceDS.REPLACES, RelatedResourceBaseUtils.getRelatedResourceName(relatedResourceDto));
        replaces = relatedResourceDto;
    }

    private void setIsReplacedBy(RelatedResourceDto relatedResourceDto) {
        setValue(StatisticalResourceDS.IS_REPLACED_BY, RelatedResourceBaseUtils.getRelatedResourceName(relatedResourceDto));
        isReplacedBy = relatedResourceDto;
    }

    public void setRelatedResourcesForReplaces(List<RelatedResourceDto> relatedResourceDtos, int firstResult, int elementsInPage, int totalResults) {
        if (searchReplacesWindow != null) {
            searchReplacesWindow.setRelatedResources(relatedResourceDtos);
            searchReplacesWindow.refreshSourcePaginationInfo(firstResult, elementsInPage, totalResults);
        }
    }

    public void setRelatedResourcesForIsReplacedBy(List<RelatedResourceDto> relatedResourceDtos, int firstResult, int elementsInPage, int totalResults) {
        if (searchIsReplacedByWindow != null) {
            searchIsReplacedByWindow.setRelatedResources(relatedResourceDtos);
            searchIsReplacedByWindow.refreshSourcePaginationInfo(firstResult, elementsInPage, totalResults);
        }
    }

    //
    // GETTER
    //

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        siemacMetadataStatisticalResourceDto.setReplaces(replaces);
        siemacMetadataStatisticalResourceDto.setIsReplacedBy(isReplacedBy);
        return siemacMetadataStatisticalResourceDto;
    }

    //
    // FORM ITEM CREATION
    //

    private SearchViewTextItem createReplacesItem(String name, String title) {
        final int FIRST_RESULT = 0;
        final int MAX_RESULTS = 8;
        final SearchViewTextItem replacesItem = new SearchViewTextItem(name, title);
        replacesItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchReplacesWindow = new SearchRelatedResourcePaginatedWindow(getConstants().resourceSelection(), MAX_RESULTS, new PaginatedAction() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults) {
                        retrieveResourcesForReplaces(firstResult, maxResults, searchReplacesWindow.getRelatedResourceCriteria());
                    }
                });

                // Load resources (to populate the selection window)
                retrieveResourcesForReplaces(FIRST_RESULT, MAX_RESULTS, null);

                searchReplacesWindow.getListGridItem().getListGrid().setSelectionType(SelectionStyle.SINGLE);
                searchReplacesWindow.getListGridItem().setSearchAction(new SearchPaginatedAction() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults, String criteria) {
                        retrieveResourcesForReplaces(firstResult, maxResults, criteria);
                    }
                });

                searchReplacesWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        RelatedResourceDto selectedResource = searchReplacesWindow.getSelectedRelatedResource();
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

    private SearchViewTextItem createIsReplacedByItem(String name, String title) {
        final int FIRST_RESULT = 0;
        final int MAX_RESULTS = 8;
        final SearchViewTextItem isReplacedByItem = new SearchViewTextItem(name, title);
        isReplacedByItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchIsReplacedByWindow = new SearchRelatedResourcePaginatedWindow(getConstants().resourceSelection(), MAX_RESULTS, new PaginatedAction() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults) {
                        retrieveResourcesForIsReplacedBy(firstResult, maxResults, searchIsReplacedByWindow.getRelatedResourceCriteria());
                    }
                });

                // Load resources (to populate the selection window)
                retrieveResourcesForIsReplacedBy(FIRST_RESULT, MAX_RESULTS, null);

                searchIsReplacedByWindow.getListGridItem().getListGrid().setSelectionType(SelectionStyle.SINGLE);
                searchIsReplacedByWindow.getListGridItem().setSearchAction(new SearchPaginatedAction() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults, String criteria) {
                        retrieveResourcesForIsReplacedBy(firstResult, maxResults, criteria);
                    }
                });

                searchIsReplacedByWindow.getSave().addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

                    @Override
                    public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
                        RelatedResourceDto selectedResource = searchIsReplacedByWindow.getSelectedRelatedResource();
                        searchIsReplacedByWindow.markForDestroy();
                        // Set selected resource in form
                        setIsReplacedBy(selectedResource);
                        validate(false);
                    }
                });
            }
        });
        return isReplacedByItem;
    }

    public abstract void setUiHandlers(UiHandlers uiHandlers);
    public abstract void retrieveResourcesForReplaces(int firstResult, int maxResults, String criteria);
    public abstract void retrieveResourcesForIsReplacedBy(int firstResult, int maxResults, String criteria);
}
