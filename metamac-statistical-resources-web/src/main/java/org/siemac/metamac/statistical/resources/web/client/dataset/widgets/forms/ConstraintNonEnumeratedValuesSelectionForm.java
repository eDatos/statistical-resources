package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getCoreMessages;

import java.util.List;

import org.siemac.metamac.core.common.util.shared.BooleanUtils;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyValueDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.core.enume.constraint.domain.KeyPartTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DimensionConstraintsDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.types.Alignment;

public class ConstraintNonEnumeratedValuesSelectionForm extends GroupDynamicForm {

    public ConstraintNonEnumeratedValuesSelectionForm(String groupTitle) {
        super(groupTitle);

        ViewTextItem inclusionTypeField = new ViewTextItem(DimensionConstraintsDS.INCLUSION_TYPE, getConstants().datasetConstraintInclusionType());
        inclusionTypeField.setAlign(Alignment.LEFT);
        inclusionTypeField.setWidth(100);

        ViewTextItem keyPartTypeField = new ViewTextItem(DimensionConstraintsDS.KEY_PART_TYPE, getConstants().datasetConstraintKeyPartType());
        keyPartTypeField.setAlign(Alignment.LEFT);
        keyPartTypeField.setWidth(100);

        ViewTextItem valuesField = new ViewTextItem(DimensionConstraintsDS.VALUES, getConstants().datasetConstraintValues());

        setFields(inclusionTypeField, keyPartTypeField, valuesField);
    }

    public void setRegionValues(RegionValueDto regionValueDto, DsdDimensionDto dsdDimensionDto) {
        clearFormValues();
        KeyValueDto keyValueDto = CommonUtils.getKeyValueOfDimension(dsdDimensionDto, regionValueDto);
        if (keyValueDto != null) {
            KeyPartTypeEnum keyPartType = CommonUtils.getKeyPartTypeOfKeyValue(keyValueDto);
            setValue(DimensionConstraintsDS.INCLUSION_TYPE, BooleanUtils.isTrue(keyValueDto.getIncluded()) ? getCoreMessages().datasetConstraintInclusionTypeEnumINCLUSION() : getCoreMessages()
                    .datasetConstraintInclusionTypeEnumEXCLUSION());
            setValue(DimensionConstraintsDS.KEY_PART_TYPE, keyPartType == null ? StringUtils.EMPTY : keyPartType.name());
            if (KeyPartTypeEnum.NORMAL.equals(keyPartType)) {
                List<String> values = CommonUtils.getValuesOfKeyValue(keyValueDto);
                setValue(DimensionConstraintsDS.VALUES, CommonWebUtils.getStringListToString(values));
                getItem(DimensionConstraintsDS.VALUES).show();
            } else if (KeyPartTypeEnum.TIME_RANGE.equals(keyPartType)) {
                // TODO METAMAC-1985
            }
        }
    }

    private void clearFormValues() {
        setValue(DimensionConstraintsDS.INCLUSION_TYPE, StringUtils.EMPTY);
        setValue(DimensionConstraintsDS.KEY_PART_TYPE, StringUtils.EMPTY);
        setValue(DimensionConstraintsDS.VALUES, StringUtils.EMPTY);
        getItem(DimensionConstraintsDS.VALUES).hide();
        // TODO METAMAC-1985
    }
}
