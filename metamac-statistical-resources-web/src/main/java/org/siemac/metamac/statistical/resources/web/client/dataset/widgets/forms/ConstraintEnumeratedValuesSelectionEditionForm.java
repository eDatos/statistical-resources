package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.Map;

import org.siemac.metamac.core.common.util.shared.BooleanUtils;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyPartDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyValueDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.enume.constraint.domain.KeyPartTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DimensionConstraintsDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.ItemsSelectionTreeItem;
import org.siemac.metamac.statistical.resources.web.client.enums.DatasetConstraintInclusionTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;

import com.smartgwt.client.types.Alignment;

public class ConstraintEnumeratedValuesSelectionEditionForm extends ConstraintEnumeratedValuesSelectionBaseForm {

    private CustomSelectItem inclusionTypeField;

    public ConstraintEnumeratedValuesSelectionEditionForm(String groupTitle) {
        super(groupTitle);

        inclusionTypeField = new CustomSelectItem(DimensionConstraintsDS.INCLUSION_TYPE, getConstants().datasetConstraintInclusionType());
        inclusionTypeField.setRequired(true);
        inclusionTypeField.setValueMap(CommonUtils.getConstraintInclusionTypeHashMap());
        inclusionTypeField.setAlign(Alignment.LEFT);
        inclusionTypeField.setWidth(100);

        treeItem = new ItemsSelectionTreeItem(DimensionConstraintsDS.VALUES, "tree-values-selection", true);
        treeItem.setShowTitle(false);
        treeItem.setStartRow(true);
        treeItem.setColSpan(4);

        setFields(inclusionTypeField, treeItem);
    }

    /**
     * Updates the region with the specified values. Each {@link RegionValueDto} has a list of {@link KeyValueDto}. All the {@link KeyPartDto} of a {@link KeyValueDto} belongs to the same dimesion.
     * 
     * @param regionValueDto
     */
    public RegionValueDto updateRegionDto(RegionValueDto regionValueDto) {
        Boolean included = DatasetConstraintInclusionTypeEnum.INCLUSION.equals(CommonUtils.getDatasetConstraintInclusionTypeEnum(inclusionTypeField.getValueAsString()));
        Map<String, Boolean> selectedItems = treeItem.getSelectedItems();

        KeyValueDto keyValueDto = getKeyValueOfSelectedDimension(regionValueDto);
        if (keyValueDto == null) {
            keyValueDto = new KeyValueDto();
            keyValueDto.setRegion(regionValueDto);
            regionValueDto.addKey(keyValueDto);
        }
        keyValueDto.setIncluded(included);
        keyValueDto.removeAllParts();
        for (String itemCode : selectedItems.keySet()) {
            KeyPartDto keyPartDto = new KeyPartDto();
            keyPartDto.setType(KeyPartTypeEnum.NORMAL);
            keyPartDto.setIdentifier(dsdDimensionDto.getDimensionId());
            keyPartDto.setValue(itemCode);
            keyPartDto.setCascadeValues(selectedItems.get(itemCode));
            keyPartDto.setPosition(dsdDimensionDto.getPosition());
            keyValueDto.addPart(keyPartDto);
        }
        if (keyValueDto.getParts().isEmpty()) {
            regionValueDto.removeKey(keyValueDto);
        }
        return regionValueDto;
    }

    @Override
    protected void setInclusionTypeValue(Boolean isIncluded) {
        setValue(DimensionConstraintsDS.INCLUSION_TYPE, BooleanUtils.isTrue(isIncluded) ? DatasetConstraintInclusionTypeEnum.INCLUSION.name() : DatasetConstraintInclusionTypeEnum.EXCLUSION.name());
    }
}
