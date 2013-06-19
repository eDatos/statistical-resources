package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceContentDescriptorsEditionForm;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemListItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class DatasetContentDescriptorsEditionForm extends StatisticalResourceContentDescriptorsEditionForm {

    public DatasetContentDescriptorsEditionForm() {
        super();

        ExternalItemListItem geographicCoverage = new ExternalItemListItem(DatasetDS.GEOGRAPHIC_COVERAGE, getConstants().datasetGeographicCoverage(), false);
        ViewTextItem temporalCoverage = new ViewTextItem(DatasetDS.DATE_START, getConstants().datasetTemporalCoverage()); 
        ExternalItemListItem geographicGranularities = new ExternalItemListItem(DatasetDS.GEOGRAPHIC_GRANULARITY, getConstants().datasetGeographicGranularities(), false); // TODO editable
        ExternalItemListItem temporalGranularities = new ExternalItemListItem(DatasetDS.TEMPORAL_GRANULARITY, getConstants().datasetTemporalGranularities(), false); // TODO editable
        ViewTextItem dateStart = new ViewTextItem(DatasetDS.DATE_START, getConstants().datasetDateStart());
        ViewTextItem dateEnd = new ViewTextItem(DatasetDS.DATE_END, getConstants().datasetDateEnd());
        ExternalItemListItem statisticalUnit = new ExternalItemListItem(DatasetDS.STATISTICAL_UNIT, getConstants().datasetStatisticalUnit(), false); // TODO editable
        ExternalItemListItem measures = new ExternalItemListItem(DatasetDS.MEASURES, getConstants().datasetMeasures(), false);

        addFields(geographicCoverage, temporalCoverage, geographicGranularities, temporalGranularities, dateStart, dateEnd, statisticalUnit, measures);
    }

    public void setDatasetDto(DatasetDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        ((ExternalItemListItem) getItem(DatasetDS.GEOGRAPHIC_COVERAGE)).setExternalItems(datasetDto.getGeographicCoverage());
        setValue(DatasetDS.TEMPORAL_COVERAGE, CommonWebUtils.getStringListToString(datasetDto.getTemporalCoverage()));
        ((ExternalItemListItem) getItem(DatasetDS.GEOGRAPHIC_GRANULARITY)).setExternalItems(datasetDto.getGeographicGranularities());
        ((ExternalItemListItem) getItem(DatasetDS.TEMPORAL_GRANULARITY)).setExternalItems(datasetDto.getTemporalGranularities());
        setValue(DatasetDS.DATE_START, datasetDto.getDateStart());
        setValue(DatasetDS.DATE_END, datasetDto.getDateEnd());
        ((ExternalItemListItem) getItem(DatasetDS.STATISTICAL_UNIT)).setExternalItems(datasetDto.getStatisticalUnit());
        ((ExternalItemListItem) getItem(DatasetDS.MEASURES)).setExternalItems(datasetDto.getMeasures());
    }

    public DatasetDto getDatasetDto(DatasetDto datasetDto) {
        datasetDto = (DatasetDto) getSiemacMetadataStatisticalResourceDto(datasetDto);
        // TODO GEOGRAPHIC_GRANULARITY
        // TODO TEMPORAL_GRANULARITY
        // TODO STATISTICAL_UNIT
        return datasetDto;
    }
}
