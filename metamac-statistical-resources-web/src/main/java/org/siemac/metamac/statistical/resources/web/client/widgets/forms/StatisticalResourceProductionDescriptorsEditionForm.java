package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getExternalItemsValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemsValue;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.base.utils.SiemacMetadataExternalField;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchSrmItemListWithSchemeFilterItem;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchSrmLinkItemWithSchemeFilterItem;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultilanguageRichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class StatisticalResourceProductionDescriptorsEditionForm extends GroupDynamicForm {

    private StatisticalResourceUiHandlers         uiHandlers;

    private SearchSrmItemListWithSchemeFilterItem contributorItem;

    private SearchSrmLinkItemWithSchemeFilterItem creatorItem;

    public StatisticalResourceProductionDescriptorsEditionForm() {
        super(getConstants().formProductionDescriptors());

        ExternalItemLinkItem maintainer = new ExternalItemLinkItem(StatisticalResourceDS.MAINTAINER, getConstants().siemacMetadataStatisticalResourceMaintainer());

        creatorItem = createCreatorItem();

        contributorItem = createContributorItem();

        ViewTextItem dateCreated = new ViewTextItem(StatisticalResourceDS.DATE_CREATED, getConstants().siemacMetadataStatisticalResourceDateCreated());
        ViewTextItem lastUpdate = new ViewTextItem(StatisticalResourceDS.LAST_UPDATE, getConstants().siemacMetadataStatisticalResourceLastUpdate());
        MultilanguageRichTextEditorItem conformsTo = new MultilanguageRichTextEditorItem(StatisticalResourceDS.CONFORMS_TO, getConstants().siemacMetadataStatisticalResourceConformsTo());
        MultilanguageRichTextEditorItem conformsToInternal = new MultilanguageRichTextEditorItem(StatisticalResourceDS.CONFORMS_TO_INTERNAL, getConstants()
                .siemacMetadataStatisticalResourceConformsToInternal());

        setFields(dateCreated, lastUpdate, maintainer, creatorItem, contributorItem, conformsTo, conformsToInternal);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setExternalItemValue(getItem(StatisticalResourceDS.MAINTAINER), siemacMetadataStatisticalResourceDto.getMaintainer());
        setExternalItemValue(getItem(StatisticalResourceDS.CREATOR), siemacMetadataStatisticalResourceDto.getCreator());
        setExternalItemsValue(getItem(StatisticalResourceDS.CONTRIBUTOR), siemacMetadataStatisticalResourceDto.getContributor());

        setValue(StatisticalResourceDS.DATE_CREATED, siemacMetadataStatisticalResourceDto.getResourceCreatedDate());
        setValue(StatisticalResourceDS.LAST_UPDATE, siemacMetadataStatisticalResourceDto.getLastUpdate());
        setValue(StatisticalResourceDS.CONFORMS_TO, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getConformsTo()));
        setValue(StatisticalResourceDS.CONFORMS_TO_INTERNAL, RecordUtils.getInternationalStringRecord(siemacMetadataStatisticalResourceDto.getConformsToInternal()));
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        siemacMetadataStatisticalResourceDto.setCreator(getExternalItemValue(getItem(StatisticalResourceDS.CREATOR)));
        siemacMetadataStatisticalResourceDto.getContributor().clear();
        siemacMetadataStatisticalResourceDto.getContributor().addAll(getExternalItemsValue(getItem(StatisticalResourceDS.CONTRIBUTOR)));

        siemacMetadataStatisticalResourceDto.setConformsTo((InternationalStringDto) getValue(StatisticalResourceDS.CONFORMS_TO));
        siemacMetadataStatisticalResourceDto.setConformsToInternal((InternationalStringDto) getValue(StatisticalResourceDS.CONFORMS_TO_INTERNAL));
        return siemacMetadataStatisticalResourceDto;
    }

    // ***************************************************************************************
    // CREATOR
    // ***************************************************************************************

    public void setOrganisationUnitsForCreator(List<ExternalItemDto> items, int firstResult, int totalResults) {
        creatorItem.setResources(items, firstResult, items.size(), totalResults);
    }

    public void setOrganisationUnitSchemesForCreator(List<ExternalItemDto> items, int firstResult, int totalResults) {
        creatorItem.setFilterResources(items, firstResult, items.size(), totalResults);
    }

    private SearchSrmLinkItemWithSchemeFilterItem createCreatorItem() {
        return new SearchSrmLinkItemWithSchemeFilterItem(StatisticalResourceDS.CREATOR, getConstants().siemacMetadataStatisticalResourceCreator(),
                StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnitSchemes(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.CREATOR);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnits(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.CREATOR);
            }
        };
    }

    // ***************************************************************************************
    // CONTRIBUTOR
    // ***************************************************************************************

    public void setOrganisationUnitsForContributor(List<ExternalItemDto> items, int firstResult, int totalResults) {
        contributorItem.setResources(items, firstResult, totalResults);
    }

    public void setOrganisationUnitSchemesForContributor(List<ExternalItemDto> items, int firstResult, int totalResults) {
        contributorItem.setFilterResources(items, firstResult, totalResults);
    }

    private SearchSrmItemListWithSchemeFilterItem createContributorItem() {
        final SearchSrmItemListWithSchemeFilterItem item = new SearchSrmItemListWithSchemeFilterItem(StatisticalResourceDS.CONTRIBUTOR, getConstants().siemacMetadataStatisticalResourceContributor(),
                StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnitSchemes(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.CONTRIBUTOR);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnits(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.CONTRIBUTOR);
            }
        };
        return item;
    }

    public void setUiHandlers(StatisticalResourceUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
