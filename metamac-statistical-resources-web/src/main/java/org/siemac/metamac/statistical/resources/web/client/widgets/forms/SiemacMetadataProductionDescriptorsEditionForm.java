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
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.CustomRequiredValidator;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiLanguageRichTextEditorItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmItemLinkItemWithSchemeFilterItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSrmListItemWithSchemeFilterItem;
import org.siemac.metamac.web.common.shared.criteria.SrmExternalResourceRestCriteria;
import org.siemac.metamac.web.common.shared.criteria.SrmItemRestCriteria;

public class SiemacMetadataProductionDescriptorsEditionForm extends GroupDynamicForm {

    protected ProcStatusEnum                          procStatus;

    private StatisticalResourceUiHandlers             uiHandlers;

    private SearchSrmListItemWithSchemeFilterItem     contributorItem;

    private SearchSrmItemLinkItemWithSchemeFilterItem creatorItem;

    public SiemacMetadataProductionDescriptorsEditionForm() {
        super(getConstants().formProductionDescriptors());

        ExternalItemLinkItem maintainer = new ExternalItemLinkItem(SiemacMetadataDS.MAINTAINER, getConstants().siemacMetadataStatisticalResourceMaintainer());

        creatorItem = createCreatorItem();
        creatorItem.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                ExternalItemDto externalItem = getValueAsExternalItemDto(SiemacMetadataDS.CREATOR);
                return CommonUtils.isResourceInProductionValidationOrGreaterProcStatus(procStatus) ? externalItem != null : true;
            }
        });

        contributorItem = createContributorItem();

        ViewTextItem dateCreated = new ViewTextItem(SiemacMetadataDS.DATE_CREATED, getConstants().siemacMetadataStatisticalResourceDateCreated());
        ViewTextItem lastUpdate = new ViewTextItem(SiemacMetadataDS.LAST_UPDATE, getConstants().siemacMetadataStatisticalResourceLastUpdate());
        MultiLanguageRichTextEditorItem conformsTo = new MultiLanguageRichTextEditorItem(SiemacMetadataDS.CONFORMS_TO, getConstants().siemacMetadataStatisticalResourceConformsTo());
        MultiLanguageRichTextEditorItem conformsToInternal = new MultiLanguageRichTextEditorItem(SiemacMetadataDS.CONFORMS_TO_INTERNAL, getConstants()
                .siemacMetadataStatisticalResourceConformsToInternal());

        setFields(dateCreated, lastUpdate, maintainer, creatorItem, contributorItem, conformsTo, conformsToInternal);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        this.procStatus = siemacMetadataStatisticalResourceDto.getProcStatus();

        setValue(SiemacMetadataDS.MAINTAINER, siemacMetadataStatisticalResourceDto.getMaintainer());
        setValue(SiemacMetadataDS.CREATOR, siemacMetadataStatisticalResourceDto.getCreator());
        setExternalItemsValue(getItem(SiemacMetadataDS.CONTRIBUTOR), siemacMetadataStatisticalResourceDto.getContributor());

        setValue(SiemacMetadataDS.DATE_CREATED, siemacMetadataStatisticalResourceDto.getResourceCreatedDate());
        setValue(SiemacMetadataDS.LAST_UPDATE, siemacMetadataStatisticalResourceDto.getLastUpdate());
        setValue(SiemacMetadataDS.CONFORMS_TO, siemacMetadataStatisticalResourceDto.getConformsTo());
        setValue(SiemacMetadataDS.CONFORMS_TO_INTERNAL, siemacMetadataStatisticalResourceDto.getConformsToInternal());
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        siemacMetadataStatisticalResourceDto.setCreator(getValueAsExternalItemDto(SiemacMetadataDS.CREATOR));
        siemacMetadataStatisticalResourceDto.getContributor().clear();
        siemacMetadataStatisticalResourceDto.getContributor().addAll(getExternalItemsValue(getItem(SiemacMetadataDS.CONTRIBUTOR)));

        siemacMetadataStatisticalResourceDto.setConformsTo(getValueAsInternationalStringDto(SiemacMetadataDS.CONFORMS_TO));
        siemacMetadataStatisticalResourceDto.setConformsToInternal(getValueAsInternationalStringDto(SiemacMetadataDS.CONFORMS_TO_INTERNAL));
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

    private SearchSrmItemLinkItemWithSchemeFilterItem createCreatorItem() {
        return new SearchSrmItemLinkItemWithSchemeFilterItem(SiemacMetadataDS.CREATOR, getConstants().siemacMetadataStatisticalResourceCreator(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnitSchemes(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.CREATOR);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
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

    private SearchSrmListItemWithSchemeFilterItem createContributorItem() {
        final SearchSrmListItemWithSchemeFilterItem item = new SearchSrmListItemWithSchemeFilterItem(SiemacMetadataDS.CONTRIBUTOR, getConstants().siemacMetadataStatisticalResourceContributor(),
                StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveItemSchemes(int firstResult, int maxResults, SrmExternalResourceRestCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnitSchemes(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.CONTRIBUTOR);
            }

            @Override
            protected void retrieveItems(int firstResult, int maxResults, SrmItemRestCriteria webCriteria) {
                uiHandlers.retrieveOrganisationUnits(firstResult, maxResults, webCriteria, SiemacMetadataExternalField.CONTRIBUTOR);
            }
        };
        return item;
    }

    public void setUiHandlers(StatisticalResourceUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }
}
