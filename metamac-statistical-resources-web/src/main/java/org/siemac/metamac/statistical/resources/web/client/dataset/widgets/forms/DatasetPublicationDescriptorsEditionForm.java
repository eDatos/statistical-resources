package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcePublicationDescriptorsEditionForm;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class DatasetPublicationDescriptorsEditionForm extends StatisticalResourcePublicationDescriptorsEditionForm {

    public DatasetPublicationDescriptorsEditionForm() {
        super();

        ViewTextItem dateNextUpdate = new ViewTextItem(DatasetDS.DATE_NEXT_UPDATE, getConstants().datasetDateNextUpdate()); // TODO editable!
        ViewTextItem updateFrquency = new ViewTextItem(DatasetDS.UPDATE_FRECUENCY, getConstants().datasetUpdateFrequency()); // TODO editable!
        ViewTextItem statisticOfficiality = new ViewTextItem(DatasetDS.STATISTIC_OFFICIALITY, getConstants().datasetStatisticOfficiality()); // TODO editable!
        ViewMultiLanguageTextItem bibliographicCitation = new ViewMultiLanguageTextItem(DatasetDS.BIBLIOGRAPHIC_CITATION, getConstants().datasetBibliographicCitation());

        addFields(dateNextUpdate, updateFrquency, statisticOfficiality, bibliographicCitation);
    }

    public void setDatasetDto(DatasetDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        setValue(DatasetDS.DATE_NEXT_UPDATE, datasetDto.getDateNextUpdate()); // TODO editable!
        setValue(DatasetDS.UPDATE_FRECUENCY, ExternalItemUtils.getExternalItemName(datasetDto.getUpdateFrequency()));// TODO editable!
        setValue(DatasetDS.STATISTIC_OFFICIALITY, CommonUtils.getStatisticOfficialityName(datasetDto.getStatisticOfficiality())); // TODO editable!
        setValue(DatasetDS.BIBLIOGRAPHIC_CITATION, RecordUtils.getInternationalStringRecord(datasetDto.getBibliographicCitation()));
    }

    public DatasetDto getDatasetDto(DatasetDto datasetDto) {
        datasetDto = (DatasetDto) getSiemacMetadataStatisticalResourceDto(datasetDto);
        // TODO DATE_NEXT_UPDATE
        // TODO UPDATE_FRECUENCY
        // TODO STATISTIC_OFFICIALITY
        return datasetDto;
    }
}
