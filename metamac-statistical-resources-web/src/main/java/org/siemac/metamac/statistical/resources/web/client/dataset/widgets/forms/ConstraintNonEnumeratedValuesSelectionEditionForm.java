package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.BooleanUtils;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyPartDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyValueDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.core.enume.constraint.domain.KeyPartTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DimensionConstraintsDS;
import org.siemac.metamac.statistical.resources.web.client.enums.DatasetConstraintInclusionTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.MultiTextItem;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.CustomValidator;

public class ConstraintNonEnumeratedValuesSelectionEditionForm extends GroupDynamicForm {

    private DsdDimensionDto dsdDimensionDto;

    public ConstraintNonEnumeratedValuesSelectionEditionForm(String groupTitle) {
        super(groupTitle);

        CustomSelectItem inclusionTypeField = new CustomSelectItem(DimensionConstraintsDS.INCLUSION_TYPE, getConstants().datasetConstraintInclusionType());
        inclusionTypeField.setRequired(true);
        inclusionTypeField.setValueMap(CommonUtils.getConstraintInclusionTypeHashMap());
        inclusionTypeField.setAlign(Alignment.LEFT);
        inclusionTypeField.setWidth(100);

        CustomSelectItem keyPartTypeField = new CustomSelectItem(DimensionConstraintsDS.KEY_PART_TYPE, getConstants().datasetConstraintKeyPartType());
        keyPartTypeField.setRequired(true);
        keyPartTypeField.setValueMap(CommonUtils.getConstraintKeyPartTypeHashMap());
        keyPartTypeField.setAlign(Alignment.LEFT);
        keyPartTypeField.setWidth(150);
        keyPartTypeField.addChangedHandler(new ChangedHandler() {

            @Override
            public void onChanged(ChangedEvent event) {
                markForRedraw();
            }
        });

        MultiTextItem valuesField = new MultiTextItem(DimensionConstraintsDS.VALUES, getConstants().datasetConstraintValues(), new CustomValidator() {

            @Override
            protected boolean condition(Object value) {
                // Set required with a custom validator
                return value != null && !StringUtils.isBlank((String) value);
            }
        });
        valuesField.setShowIfCondition(new FormItemIfFunction() {

            @Override
            public boolean execute(FormItem item, Object value, DynamicForm form) {
                String keyPartType = form.getValueAsString(DimensionConstraintsDS.KEY_PART_TYPE);
                return KeyPartTypeEnum.NORMAL.name().equals(keyPartType);
            }
        });
        valuesField.setTitleStyle("staticFormItemTitle");
        valuesField.setVisible(false);

        setFields(inclusionTypeField, keyPartTypeField, valuesField);
    }

    public void setRegionValues(RegionValueDto regionValueDto, DsdDimensionDto dsdDimensionDto) {
        clearFormValues();
        this.dsdDimensionDto = dsdDimensionDto;
        KeyValueDto keyValueDto = CommonUtils.getKeyValueOfDimension(dsdDimensionDto, regionValueDto);
        if (keyValueDto != null) {
            KeyPartTypeEnum keyPartType = CommonUtils.getKeyPartTypeOfKeyValue(keyValueDto);
            setValue(DimensionConstraintsDS.INCLUSION_TYPE, BooleanUtils.isTrue(keyValueDto.getIncluded())
                    ? DatasetConstraintInclusionTypeEnum.INCLUSION.name()
                    : DatasetConstraintInclusionTypeEnum.EXCLUSION.name());
            setValue(DimensionConstraintsDS.KEY_PART_TYPE, keyPartType == null ? StringUtils.EMPTY : keyPartType.name());
            if (KeyPartTypeEnum.NORMAL.equals(keyPartType)) {
                List<String> values = CommonUtils.getValuesOfKeyValue(keyValueDto);
                ((MultiTextItem) getItem(DimensionConstraintsDS.VALUES)).setValues(values);
            } else if (KeyPartTypeEnum.TIME_RANGE.equals(keyPartType)) {
                // TODO METAMAC-1985
            }
        }
        markForRedraw();
    }

    /**
     * Updates the region with the specified values. Each {@link RegionValueDto} has a list of {@link KeyValueDto}. All the {@link KeyPartDto} of a {@link KeyValueDto} belongs to the same dimension.
     * 
     * @param regionValueDto
     */
    public RegionValueDto updateRegionDto(RegionValueDto regionValueDto) {
        Boolean included = DatasetConstraintInclusionTypeEnum.INCLUSION.equals(CommonUtils.getDatasetConstraintInclusionTypeEnum(getValueAsString(DimensionConstraintsDS.INCLUSION_TYPE)));
        KeyPartTypeEnum keyPartType = CommonUtils.getDatasetConstraintKeyPartTypeEnum(getValueAsString(DimensionConstraintsDS.KEY_PART_TYPE));

        KeyValueDto keyValueDto = CommonUtils.getKeyValueOfDimension(dsdDimensionDto, regionValueDto);
        if (keyValueDto == null) {
            keyValueDto = new KeyValueDto();
            keyValueDto.setRegion(regionValueDto);
            regionValueDto.addKey(keyValueDto);
        }

        keyValueDto.setIncluded(included);
        keyValueDto.removeAllParts();

        if (KeyPartTypeEnum.NORMAL.equals(keyPartType)) {
            List<String> values = ((MultiTextItem) getItem(DimensionConstraintsDS.VALUES)).getValues();
            for (String value : values) {
                KeyPartDto keyPartDto = new KeyPartDto();
                keyPartDto.setType(keyPartType);
                keyPartDto.setIdentifier(dsdDimensionDto.getDimensionId());
                keyPartDto.setPosition(dsdDimensionDto.getPosition());
                keyPartDto.setValue(value);
                keyValueDto.addPart(keyPartDto);
            }
        } else {
            // TODO METAMAC-1985
        }

        if (keyValueDto.getParts().isEmpty()) {
            regionValueDto.removeKey(keyValueDto);
        }
        return regionValueDto;
    }

    public DsdDimensionDto getSelectedDimension() {
        return dsdDimensionDto;
    }

    private void clearFormValues() {
        setValue(DimensionConstraintsDS.INCLUSION_TYPE, StringUtils.EMPTY);
        setValue(DimensionConstraintsDS.KEY_PART_TYPE, StringUtils.EMPTY);
        ((MultiTextItem) getItem(DimensionConstraintsDS.VALUES)).setValues(new ArrayList<String>());
        // TODO METAMAC-1985
    }
}
