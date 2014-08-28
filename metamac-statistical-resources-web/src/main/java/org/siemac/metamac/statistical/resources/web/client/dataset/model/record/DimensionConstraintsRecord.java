package org.siemac.metamac.statistical.resources.web.client.dataset.model.record;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DimensionConstraintsDS;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DimensionConstraintsRecord extends ListGridRecord {

    public DimensionConstraintsRecord() {
    }

    public void setDimensionId(String value) {
        setAttribute(DimensionConstraintsDS.DIMENSION_ID, value);
    }

    public void setInclusionType(boolean isIncluded) {
        // TODO METAMAC-1985
    }

    public void setValues() {
        // TODO METAMAC-1985
    }

    public void setDsdDimensionDto(DsdDimensionDto dsdDimensionDto) {
        setAttribute(DimensionConstraintsDS.DSD_DIMENSION_DTO, dsdDimensionDto);
    }

    public DsdDimensionDto getDimensionDto() {
        return (org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto) getAttributeAsObject(DimensionConstraintsDS.DSD_DIMENSION_DTO);
    }
}
