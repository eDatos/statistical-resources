package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.getDate;

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

    public DatasetPublicationDescriptorsEditionForm() {
        super();

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

        addFields(statisticOfficiality, bibliographicCitation);
    }

    public void setDatasetVersionDto(DatasetVersionDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        setValue(DatasetDS.BIBLIOGRAPHIC_CITATION, datasetDto.getBibliographicCitation());

        if (datasetDto.getStatisticOfficiality() != null) {
            setValue(DatasetDS.STATISTIC_OFFICIALITY, datasetDto.getStatisticOfficiality().getIdentifier());
        } else {
            setValue(DatasetDS.STATISTIC_OFFICIALITY, (String) null);
        }
    }

    public DatasetVersionDto getDatasetVersionDto(DatasetVersionDto datasetDto) {
        datasetDto = (DatasetVersionDto) getSiemacMetadataStatisticalResourceDto(datasetDto);

        String statisticOfficialityIdentifier = getValueAsString(DatasetDS.STATISTIC_OFFICIALITY);
        if (!StringUtils.isEmpty(statisticOfficialityIdentifier)) {
            datasetDto.setStatisticOfficiality(CommonUtils.getStatisticOfficialityByIdentifier(statisticOfficialityIdentifier));
        } else {
            datasetDto.setStatisticOfficiality(null);
        }
        return datasetDto;
    }

}
