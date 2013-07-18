package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetDto;
import org.siemac.metamac.statistical.resources.web.client.base.checks.DatasetMetadataShowChecks;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DatasetDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceContentDescriptorsForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.ExternalItemListItem;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.HiddenItem;

public class DatasetContentDescriptorsForm extends StatisticalResourceContentDescriptorsForm {

    public DatasetContentDescriptorsForm() {
        super();

        HiddenItem procStatusHidden = new HiddenItem(DatasetDS.PROC_STATUS);

        ExternalItemListItem geographicCoverage = new ExternalItemListItem(DatasetDS.GEOGRAPHIC_COVERAGE, getConstants().datasetGeographicCoverage(), false);
        geographicCoverage.setShowIfCondition(getCanShowCoveragesFunction());
        ExternalItemListItem temporalCoverage = new ExternalItemListItem(DatasetDS.TEMPORAL_COVERAGE, getConstants().datasetTemporalCoverage(), false);
        temporalCoverage.setShowIfCondition(getCanShowCoveragesFunction());
        ExternalItemListItem measures = new ExternalItemListItem(DatasetDS.MEASURES, getConstants().datasetMeasures(), false);
        measures.setShowIfCondition(getCanShowCoveragesFunction());

        ExternalItemListItem geographicGranularities = new ExternalItemListItem(DatasetDS.GEOGRAPHIC_GRANULARITY, getConstants().datasetGeographicGranularities(), false);
        ExternalItemListItem temporalGranularities = new ExternalItemListItem(DatasetDS.TEMPORAL_GRANULARITY, getConstants().datasetTemporalGranularities(), false);

        ViewTextItem dateStart = new ViewTextItem(DatasetDS.DATE_START, getConstants().datasetDateStart());
        ViewTextItem dateEnd = new ViewTextItem(DatasetDS.DATE_END, getConstants().datasetDateEnd());

        ExternalItemListItem statisticalUnit = new ExternalItemListItem(DatasetDS.STATISTICAL_UNIT, getConstants().datasetStatisticalUnit(), false);

        addFields(procStatusHidden, geographicCoverage, temporalCoverage, measures, geographicGranularities, temporalGranularities, dateStart, dateEnd, statisticalUnit);
    }

    public void setDatasetDto(DatasetDto datasetDto) {
        setSiemacMetadataStatisticalResourceDto(datasetDto);

        setValue(DatasetDS.PROC_STATUS, datasetDto.getProcStatus().name());

        ((ExternalItemListItem) getItem(DatasetDS.GEOGRAPHIC_GRANULARITY)).setExternalItems(datasetDto.getGeographicGranularities());
        ((ExternalItemListItem) getItem(DatasetDS.TEMPORAL_GRANULARITY)).setExternalItems(datasetDto.getTemporalGranularities());

        setValue(DatasetDS.DATE_START, datasetDto.getDateStart());
        setValue(DatasetDS.DATE_END, datasetDto.getDateEnd());

        ((ExternalItemListItem) getItem(DatasetDS.STATISTICAL_UNIT)).setExternalItems(datasetDto.getStatisticalUnit());
    }

    public void setCoverages(List<ExternalItemDto> geoItems, List<ExternalItemDto> temporalItems, List<ExternalItemDto> measureItems) {
        ((ExternalItemListItem) getItem(DatasetDS.GEOGRAPHIC_COVERAGE)).setExternalItems(geoItems);
        ((ExternalItemListItem) getItem(DatasetDS.TEMPORAL_COVERAGE)).setExternalItems(temporalItems);
        ((ExternalItemListItem) getItem(DatasetDS.MEASURES)).setExternalItems(measureItems);
    }

    private FormItemIfFunction getCanShowCoveragesFunction() {
        return new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                Object valueObject = form.getItem(DatasetDS.PROC_STATUS).getValue();
                return DatasetMetadataShowChecks.canCoveragesBeShown(valueObject);
            }
        };
    }
}
