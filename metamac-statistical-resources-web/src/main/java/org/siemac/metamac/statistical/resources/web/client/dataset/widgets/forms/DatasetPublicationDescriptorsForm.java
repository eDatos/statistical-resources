package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.*;  

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcePublicationDescriptorsForm;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemLinkItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewMultiLanguageTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class DatasetPublicationDescriptorsForm extends StatisticalResourcePublicationDescriptorsForm {

    public DatasetPublicationDescriptorsForm() {
        super();

        ViewTextItem dateNextUpdate = new ViewTextItem(DatasetDS.DATE_NEXT_UPDATE, getConstants().datasetDateNextUpdate());
        ExternalItemLinkItem updateFrequency = new ExternalItemLinkItem(DatasetDS.UPDATE_FRECUENCY, getConstants().datasetUpdateFrequency());
        ViewTextItem statisticOfficiality = new ViewTextItem(DatasetDS.STATISTIC_OFFICIALITY, getConstants().datasetStatisticOfficiality());
        ViewMultiLanguageTextItem bibliographicCitation = new ViewMultiLanguageTextItem(DatasetDS.BIBLIOGRAPHIC_CITATION, getConstants().datasetBibliographicCitation());

        addFields(dateNextUpdate, updateFrequency, statisticOfficiality, bibliographicCitation);
    }

    public void setDatasetVersionDto(DatasetVersionDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        setValue(DatasetDS.DATE_NEXT_UPDATE, datasetDto.getDateNextUpdate());
        setValue(DatasetDS.UPDATE_FRECUENCY, ExternalItemUtils.getExternalItemName(datasetDto.getUpdateFrequency()));
        setExternalItemValue(getItem(DatasetDS.UPDATE_FRECUENCY), datasetDto.getUpdateFrequency());
        setValue(DatasetDS.STATISTIC_OFFICIALITY, CommonUtils.getStatisticOfficialityName(datasetDto.getStatisticOfficiality()));
        setValue(DatasetDS.BIBLIOGRAPHIC_CITATION, RecordUtils.getInternationalStringRecord(datasetDto.getBibliographicCitation()));
    }
}
