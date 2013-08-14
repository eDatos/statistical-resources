package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getDate;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getExternalItemValue;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setExternalItemValue;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetMetadataExternalField;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.SiemacMetadataPublicationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.SearchExternalItemSimpleItem;
import org.siemac.metamac.web.common.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.CustomRequiredValidator;
import org.siemac.metamac.web.common.client.utils.FormItemUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomDateItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.smartgwt.client.widgets.form.fields.FormItemIcon;

public class DatasetPublicationDescriptorsEditionForm extends SiemacMetadataPublicationDescriptorsEditionForm {

    private DatasetMetadataTabUiHandlers uiHandlers;
    private SearchExternalItemSimpleItem updateFrequency;

    public DatasetPublicationDescriptorsEditionForm() {
        super();

        final CustomDateItem dateNextUpdate = createDateNextUpdateItem();
        dateNextUpdate.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isResourceInProductionValidationOrGreaterProcStatus(procStatus) ? dateNextUpdate.getValueAsDate() != null : true;
            }
        });

        updateFrequency = createUpdateFrequencyItem();
        updateFrequency.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isResourceInProductionValidationOrGreaterProcStatus(procStatus) ? getExternalItemValue(getItem(DatasetDS.UPDATE_FRECUENCY)) != null : true;
            }
        });

        final CustomSelectItem statisticOfficiality = new CustomSelectItem(DatasetDS.STATISTIC_OFFICIALITY, getConstants().datasetStatisticOfficiality());
        statisticOfficiality.setValueMap(CommonUtils.getStatisticOfficialityHashMap());
        statisticOfficiality.addChangedHandler(FormItemUtils.getMarkForRedrawChangedHandler(this));
        statisticOfficiality.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                return CommonUtils.isResourceInProductionValidationOrGreaterProcStatus(procStatus) ? !StringUtils.isBlank(statisticOfficiality.getValueAsString()) : true;
            }
        });

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
        setValue(DatasetDS.BIBLIOGRAPHIC_CITATION, RecordUtils.getInternationalStringRecord(datasetDto.getBibliographicCitation()));

        if (datasetDto.getStatisticOfficiality() != null) {
            setValue(DatasetDS.STATISTIC_OFFICIALITY, datasetDto.getStatisticOfficiality().getIdentifier());
        } else {
            setValue(DatasetDS.STATISTIC_OFFICIALITY, (String)null);
        }
    }

    public DatasetVersionDto getDatasetVersionDto(DatasetVersionDto datasetDto) {
        datasetDto = (DatasetVersionDto) getSiemacMetadataStatisticalResourceDto(datasetDto);
        datasetDto.setDateNextUpdate(getDate(getItem(DatasetDS.DATE_NEXT_UPDATE)));
        datasetDto.setUpdateFrequency(getExternalItemValue(getItem(DatasetDS.UPDATE_FRECUENCY)));

        String statisticOfficialityIdentifier = getValueAsString(DatasetDS.STATISTIC_OFFICIALITY);
        if (!StringUtils.isEmpty(statisticOfficialityIdentifier)) {
            datasetDto.setStatisticOfficiality(CommonUtils.getStatisticOfficialityByIdentifier(statisticOfficialityIdentifier));
        } else {
            datasetDto.setStatisticOfficiality(null);
        }
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
