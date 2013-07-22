package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getDate;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetMetadataExternalField;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcePublicationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchExternalItemSimpleItem;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.fields.FormItemIcon;

public class DatasetPublicationDescriptorsEditionForm extends StatisticalResourcePublicationDescriptorsEditionForm {

    private DatasetMetadataTabUiHandlers uiHandlers;
    private SearchExternalItemSimpleItem updateFrequency;

    public DatasetPublicationDescriptorsEditionForm() {
        super();
        CustomDateItem dateNextUpdate = createDateNextUpdateItem();
        updateFrequency = createUpdateFrequencyItem();
        ViewTextItem statisticOfficiality = new ViewTextItem(DatasetDS.STATISTIC_OFFICIALITY, getConstants().datasetStatisticOfficiality()); // TODO editable!
        ViewMultiLanguageTextItem bibliographicCitation = new ViewMultiLanguageTextItem(DatasetDS.BIBLIOGRAPHIC_CITATION, getConstants().datasetBibliographicCitation());

        addFields(dateNextUpdate, updateFrequency, statisticOfficiality, bibliographicCitation);
    }

    private CustomDateItem createDateNextUpdateItem() {
        FormItemIcon infoIcon = new FormItemIcon();
        infoIcon.setSrc(GlobalResources.RESOURCE.info().getURL());
        infoIcon.setPrompt(StatisticalResourcesWeb.getMessages().dateNextUpdateInfo());
        CustomDateItem item = new CustomDateItem(DatasetDS.DATE_NEXT_UPDATE, getConstants().datasetDateNextUpdate());
        item.setIcons(infoIcon);
        return item;
    }

    public void setDatasetVersionDto(DatasetVersionDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        setValue(DatasetDS.DATE_NEXT_UPDATE, datasetDto.getDateNextUpdate());
        setExternalItemValue(getItem(DatasetDS.UPDATE_FRECUENCY), datasetDto.getUpdateFrequency());
        setValue(DatasetDS.STATISTIC_OFFICIALITY, CommonUtils.getStatisticOfficialityName(datasetDto.getStatisticOfficiality())); // TODO editable!
        setValue(DatasetDS.BIBLIOGRAPHIC_CITATION, RecordUtils.getInternationalStringRecord(datasetDto.getBibliographicCitation()));
    }

    public DatasetVersionDto getDatasetVersionDto(DatasetVersionDto datasetDto) {
        datasetDto = (DatasetVersionDto) getSiemacMetadataStatisticalResourceDto(datasetDto);
        datasetDto.setDateNextUpdate(getDate(getItem(DatasetDS.DATE_NEXT_UPDATE)));
        datasetDto.setUpdateFrequency(getExternalItemValue(getItem(DatasetDS.UPDATE_FRECUENCY)));
        // TODO STATISTIC_OFFICIALITY
        return datasetDto;
    }

    public void setCodesForUpdateFrequency(List<ExternalItemDto> items, int firstResult, int totalResults) {
        updateFrequency.setResources(items, firstResult, totalResults);
    }

    private SearchExternalItemSimpleItem createUpdateFrequencyItem() {
        return new SearchExternalItemSimpleItem(DatasetDS.UPDATE_FRECUENCY, getConstants().datasetUpdateFrequency(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS) {

            @Override
            protected void retrieveResources(int firstResult, int maxResults, MetamacWebCriteria webCriteria) {
                uiHandlers.retrieveTemporalCodesForField(firstResult, maxResults, webCriteria, DatasetMetadataExternalField.UPDATE_FREQUENCY);
            }
        };
    }

    public void setUiHandlers(DatasetMetadataTabUiHandlers uiHandlers) {
        super.setUiHandlers(uiHandlers);
        this.uiHandlers = uiHandlers;
    }
}
