package org.siemac.metamac.statistical.resources.web.client.dataset.model.record;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getCoreMessages;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.BooleanUtils;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyPartDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DimensionConstraintsDS;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DimensionConstraintsRecord extends ListGridRecord {

    public DimensionConstraintsRecord() {
    }

    public void setDimensionId(String value) {
        setAttribute(DimensionConstraintsDS.DIMENSION_ID, value);
    }

    public void setInclusionType(Boolean isIncluded) {
        setAttribute(DimensionConstraintsDS.INCLUSION_TYPE, BooleanUtils.isTrue(isIncluded) ? getCoreMessages().datasetConstraintInclusionTypeEnumINCLUSION() : getCoreMessages()
                .datasetConstraintInclusionTypeEnumEXCLUSION());
    }

    public void setValues(List<KeyPartDto> keyParts) {
        // TODO METAMAC-1985 how to set values when the dimension associated does not have a enumerated representation?
        List<String> values = new ArrayList<String>(keyParts.size());
        for (KeyPartDto keyPart : keyParts) {
            values.add(keyPart.getValue());
        }
        setAttribute(DimensionConstraintsDS.VALUES, CommonWebUtils.getStringListToString(values));
    }

    public void setDsdDimensionDto(DsdDimensionDto dsdDimensionDto) {
        setAttribute(DimensionConstraintsDS.DSD_DIMENSION_DTO, dsdDimensionDto);
    }

    public DsdDimensionDto getDimensionDto() {
        return (org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto) getAttributeAsObject(DimensionConstraintsDS.DSD_DIMENSION_DTO);
    }
}
