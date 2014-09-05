package org.siemac.metamac.statistical.resources.web.client.dataset.model.record;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getCoreMessages;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.BooleanUtils;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyPartDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.core.enume.constraint.domain.KeyPartTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DimensionConstraintsDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.statistical.resources.web.shared.dtos.RangeDto;
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
        List<String> values = new ArrayList<String>(keyParts.size());
        for (KeyPartDto keyPart : keyParts) {
            if (KeyPartTypeEnum.NORMAL.equals(keyPart.getType())) {
                values.add(keyPart.getValue());
            } else if (KeyPartTypeEnum.TIME_RANGE.equals(keyPart.getType())) {
                values.add(getKeyPartRangeValue(keyPart));
            }
        }
        setAttribute(DimensionConstraintsDS.VALUES, CommonWebUtils.getStringListToString(values));
    }

    public void setDsdDimensionDto(DsdDimensionDto dsdDimensionDto) {
        setAttribute(DimensionConstraintsDS.DSD_DIMENSION_DTO, dsdDimensionDto);
    }

    public DsdDimensionDto getDimensionDto() {
        return (org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto) getAttributeAsObject(DimensionConstraintsDS.DSD_DIMENSION_DTO);
    }

    private String getKeyPartRangeValue(KeyPartDto keyPartDto) {
        RangeDto rangeDto = CommonUtils.buildRangeDto(keyPartDto);
        StringBuilder value = new StringBuilder();
        if (!StringUtils.isBlank(rangeDto.getFromValue())) {
            value.append(getConstants().datasetConstraintRangeFrom()).append(" ").append(rangeDto.getFromValue()).append(" ");
        }
        if (!StringUtils.isBlank(rangeDto.getToValue())) {
            value.append(getConstants().datasetConstraintRangeTo()).append(" ").append(rangeDto.getToValue()).append(" ");
        }
        return value.toString();
    }
}
