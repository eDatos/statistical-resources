package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyPartDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyValueDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.ItemDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DimensionConstraintsDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.ItemsSelectionTreeItem;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;

public abstract class ConstraintEnumeratedValuesSelectionBaseForm extends GroupDynamicForm {

    protected ItemsSelectionTreeItem treeItem;

    protected DsdDimensionDto        dsdDimensionDto;

    public ConstraintEnumeratedValuesSelectionBaseForm(String groupTitle) {
        super(groupTitle);
    }

    public void setValues(RegionValueDto regionValueDto, DsdDimensionDto dsdDimensionDto, ExternalItemDto itemScheme, List<ItemDto> items) {
        this.dsdDimensionDto = dsdDimensionDto;
        treeItem.setItems(itemScheme, items);

        setSavedValues(regionValueDto);
    }

    public void setSavedValues(RegionValueDto regionValueDto) {
        KeyValueDto selectedDimensionKeyValue = getKeyValueOfSelectedDimension(regionValueDto);

        if (selectedDimensionKeyValue == null) {
            setValue(DimensionConstraintsDS.INCLUSION_TYPE, StringUtils.EMPTY);
        } else {
            setInclusionTypeValue(selectedDimensionKeyValue.getIncluded());
            treeItem.selectItems(buildMap(selectedDimensionKeyValue.getParts()));
        }
    }

    public DsdDimensionDto getSelectedDimension() {
        return dsdDimensionDto;
    }

    /**
     * Returns the {@link KeyValueDto} of the selected dimension (all the {@link KeyPartDto} in a {@link KeyValueDto} belongs to the same dimension).
     * 
     * @param keyValueDto
     * @return
     */
    protected KeyValueDto getKeyValueOfSelectedDimension(RegionValueDto regionValueDto) {
        if (regionValueDto != null) {
            for (KeyValueDto keyValueDto : regionValueDto.getKeys()) {
                for (KeyPartDto keyPartDto : keyValueDto.getParts()) {
                    if (StringUtils.equals(dsdDimensionDto.getDimensionId(), keyPartDto.getIdentifier())) {
                        return keyValueDto;
                    }
                }
            }
        }
        return null;
    }

    private Map<String, KeyPartDto> buildMap(List<KeyPartDto> keyPartDtos) {
        Map<String, KeyPartDto> keyPartsMap = new HashMap<String, KeyPartDto>();
        for (KeyPartDto keyPart : keyPartDtos) {
            keyPartsMap.put(keyPart.getValue(), keyPart);
        }
        return keyPartsMap;
    }

    protected abstract void setInclusionTypeValue(Boolean isIncluded);
}
