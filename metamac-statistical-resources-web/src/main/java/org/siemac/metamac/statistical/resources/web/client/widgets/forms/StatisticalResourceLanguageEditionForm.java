package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getExternalItemsValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemsValue;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchMultiExternalItemSimpleItem;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public class StatisticalResourceLanguageEditionForm extends GroupDynamicForm {

    private StatisticalResourceUiHandlers     uiHandlers;

    private SearchMultiExternalItemSimpleItem languagesItem;

    public StatisticalResourceLanguageEditionForm() {
        super(getConstants().formLanguages());

        ExternalItemLinkItem language = new ExternalItemLinkItem(StatisticalResourceDS.LANGUAGE, getConstants().siemacMetadataStatisticalResourceLanguage());
        languagesItem = createLanguagesItem();

        setFields(language, languagesItem);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        setExternalItemValue(getItem(StatisticalResourceDS.LANGUAGE), dto.getLanguage());
        setExternalItemsValue(getItem(StatisticalResourceDS.LANGUAGES), dto.getLanguages());
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto dto) {
        dto.getLanguages().clear();
        dto.getLanguages().addAll(getExternalItemsValue(getItem(StatisticalResourceDS.LANGUAGES)));
        // FIXME: always include language in languages
        return dto;
    }

    public void setCodesForLanguages(List<ExternalItemDto> items, int firstResult, int totalResults) {
        languagesItem.setResources(items, firstResult, totalResults);
    }

    private SearchMultiExternalItemSimpleItem createLanguagesItem() {
        return new SearchMultiExternalItemSimpleItem(StatisticalResourceDS.LANGUAGES, getConstants().siemacMetadataStatisticalResourceLanguages(),
                StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveLanguagesCodes(firstResult, maxResults, webCriteria);
            }
        };
    }

    public void setUiHandlers(StatisticalResourceUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

}
