package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceContentDescriptorsForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ExternalItemListItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

public class DatasetContentDescriptorsForm extends StatisticalResourceContentDescriptorsForm {

    public DatasetContentDescriptorsForm() {
        super();

        ExternalItemListItem geographicCoverage = new ExternalItemListItem(DatasetDS.GEOGRAPHIC_COVERAGE, getConstants().datasetGeographicCoverage(), false);
        ExternalItemListItem temporalCoverage = new ExternalItemListItem(DatasetDS.TEMPORAL_COVERAGE, getConstants().datasetTemporalCoverage(), false);
        ExternalItemListItem geographicGranularities = new ExternalItemListItem(DatasetDS.GEOGRAPHIC_GRANULARITY, getConstants().datasetGeographicGranularities(), false);
        ExternalItemListItem temporalGranularities = new ExternalItemListItem(DatasetDS.TEMPORAL_GRANULARITY, getConstants().datasetTemporalGranularities(), false);
        ViewTextItem dateStart = new ViewTextItem(DatasetDS.DATE_START, getConstants().datasetDateStart());
        ViewTextItem dateEnd = new ViewTextItem(DatasetDS.DATE_END, getConstants().datasetDateEnd());
        ExternalItemListItem statisticalUnit = new ExternalItemListItem(DatasetDS.STATISTICAL_UNIT, getConstants().datasetStatisticalUnit(), false);
        ExternalItemListItem measures = new ExternalItemListItem(DatasetDS.MEASURES, getConstants().datasetMeasures(), false);

        addFields(geographicCoverage, temporalCoverage, geographicGranularities, temporalGranularities, dateStart, dateEnd, statisticalUnit, measures);
    }

    public void setDatasetDto(DatasetDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);
        ((ExternalItemListItem) getItem(DatasetDS.GEOGRAPHIC_COVERAGE)).setExternalItems(datasetDto.getGeographicCoverage());
        ((ExternalItemListItem) getItem(DatasetDS.TEMPORAL_COVERAGE)).setExternalItems(datasetDto.getTemporalCoverage());
        ((ExternalItemListItem) getItem(DatasetDS.GEOGRAPHIC_GRANULARITY)).setExternalItems(datasetDto.getGeographicGranularities());
        ((ExternalItemListItem) getItem(DatasetDS.TEMPORAL_GRANULARITY)).setExternalItems(datasetDto.getTemporalGranularities());
        setValue(DatasetDS.DATE_START, getConstants().datasetDateStart());
        setValue(DatasetDS.DATE_END, getConstants().datasetDateEnd());
        ((ExternalItemListItem) getItem(DatasetDS.STATISTICAL_UNIT)).setExternalItems(datasetDto.getStatisticalUnit());
        ((ExternalItemListItem) getItem(DatasetDS.MEASURES)).setExternalItems(datasetDto.getMeasures());
    }
}
