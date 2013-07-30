package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getExternalItemsValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemsValue;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.base.utils.SiemacMetadataExternalField;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchSrmItemListWithSchemeFilterItem;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.utils.CustomRequiredValidator;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class StatisticalResourcePublicationDescriptorsEditionForm extends GroupDynamicForm {

    protected ProcStatusEnum                      procStatus;

    private StatisticalResourceUiHandlers         uiHandlers;

    private SearchSrmItemListWithSchemeFilterItem publisherItem;
    private SearchSrmItemListWithSchemeFilterItem publisherContributorItem;
    private SearchSrmItemListWithSchemeFilterItem mediatorItem;

    public StatisticalResourcePublicationDescriptorsEditionForm() {
        super(getConstants().formPublicationDescriptors());

        publisherItem = createPublisherItem();
        publisherItem.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                List<ExternalItemDto> values = getExternalItemsValue(getItem(StatisticalResourceDS.PUBLISHER));
                return CommonUtils.isResourceInProductionValidationOrGreaterProcStatus(procStatus) ? (values != null && values.size() > 0) : true;
            }
        });

        publisherContributorItem = createPublisherContributorItem();
        mediatorItem = createMediatorItem();
        CustomDateItem newnessUntilDate = new CustomDateItem(StatisticalResourceDS.NEWNESS_UNTIL_DATE, getConstants().siemacMetadataStatisticalResourceNewnessUntilDate());

        setFields(publisherItem, publisherContributorItem, mediatorItem, newnessUntilDate);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        this.procStatus = dto.getProcStatus();

        setExternalItemsValue(getItem(StatisticalResourceDS.PUBLISHER), dto.getPublisher());
        setExternalItemsValue(getItem(StatisticalResourceDS.PUBLISHER_CONTRIBUTOR), dto.getPublisherContributor());
        setExternalItemsValue(getItem(StatisticalResourceDS.MEDIATOR), dto.getMediator());
        setValue(StatisticalResourceDS.NEWNESS_UNTIL_DATE, dto.getNewnessUntilDate());
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        dto.getPublisher().clear();
        dto.getPublisher().addAll(getExternalItemsValue(getItem(StatisticalResourceDS.PUBLISHER)));

        dto.getPublisherContributor().clear();
        dto.getPublisherContributor().addAll(getExternalItemsValue(getItem(StatisticalResourceDS.PUBLISHER_CONTRIBUTOR)));

        dto.getMediator().clear();
        dto.getMediator().addAll(getExternalItemsValue(getItem(StatisticalResourceDS.PUBLISHER_CONTRIBUTOR)));

        dto.setNewnessUntilDate(((CustomDateItem) getItem(StatisticalResourceDS.NEWNESS_UNTIL_DATE)).getValueAsDate());
        return dto;
    }

    // *************************************************
    // PUBLISHER
    // *************************************************

    public void setOrganisationUnitSchemesForPublisher(List<ExternalItemDto> externalItemsDtos, int firstResult, int totalResults) {
        publisherItem.setFilterResources(externalItemsDtos, firstResult, totalResults);
    }

    public void setOrganisationUnitsForPublisher(List<ExternalItemDto> externalItemsDtos, int firstResult, int totalResults) {
        publisherItem.setResources(externalItemsDtos, firstResult, totalResults);
    }

    private SearchSrmItemListWithSchemeFilterItem createPublisherItem() {
        return new SearchSrmItemListWithSchemeFilterItem(StatisticalResourceDS.PUBLISHER, getConstants().siemacMetadataStatisticalResourcePublisher(),
                StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItems(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnits(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.PUBLISHER);
            }

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnitSchemes(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.PUBLISHER);
            }
        };
    }

    // *************************************************
    // PUBLISHER CONTRIBUTOR
    // *************************************************

    public void setOrganisationUnitSchemesForPublisherContributor(List<ExternalItemDto> externalItemsDtos, int firstResult, int totalResults) {
        publisherContributorItem.setFilterResources(externalItemsDtos, firstResult, totalResults);
    }

    public void setOrganisationUnitsForPublisherContributor(List<ExternalItemDto> externalItemsDtos, int firstResult, int totalResults) {
        publisherContributorItem.setResources(externalItemsDtos, firstResult, totalResults);
    }

    private SearchSrmItemListWithSchemeFilterItem createPublisherContributorItem() {
        return new SearchSrmItemListWithSchemeFilterItem(StatisticalResourceDS.PUBLISHER_CONTRIBUTOR, getConstants().siemacMetadataStatisticalResourcePublisherContributor(),
                StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItems(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnits(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.PUBLISHER_CONTRIBUTOR);
            }

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnitSchemes(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.PUBLISHER_CONTRIBUTOR);
            }
        };
    }

    // *************************************************
    // MEDIATOR
    // *************************************************

    public void setOrganisationUnitSchemesForMediator(List<ExternalItemDto> externalItemsDtos, int firstResult, int totalResults) {
        mediatorItem.setFilterResources(externalItemsDtos, firstResult, totalResults);
    }

    public void setOrganisationUnitsForMediator(List<ExternalItemDto> externalItemsDtos, int firstResult, int totalResults) {
        mediatorItem.setResources(externalItemsDtos, firstResult, totalResults);
    }

    private SearchSrmItemListWithSchemeFilterItem createMediatorItem() {
        return new SearchSrmItemListWithSchemeFilterItem(StatisticalResourceDS.MEDIATOR, getConstants().siemacMetadataStatisticalResourceMediator(),
                StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItems(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnits(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.MEDIATOR);
            }

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnitSchemes(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.MEDIATOR);
            }
        };
    }

    public void setUiHandlers(StatisticalResourceUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
