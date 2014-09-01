package org.siemac.metamac.statistical.resources.web.client.dataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyPartDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.KeyValueDto;
import org.siemac.metamac.statistical.resources.core.dto.constraint.RegionValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdDimensionDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.ItemDto;
import org.siemac.metamac.statistical.resources.web.client.dataset.model.ds.DimensionConstraintsDS;
import org.siemac.metamac.statistical.resources.web.client.dataset.widgets.ItemsSelectionTreeItem;
import org.siemac.metamac.statistical.resources.web.client.enums.DatasetConstraintInclusionTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.widgets.form.GroupDynamicForm;
import org.siemac.metamac.web.common.client.widgets.form.fields.CustomSelectItem;

import com.smartgwt.client.types.Alignment;

public class ConstraintEnumeratedValuesSelectionEditionForm extends GroupDynamicForm {

    private CustomSelectItem       inclusionTypeField;
    private ItemsSelectionTreeItem treeItem;

    private DsdDimensionDto        dsdDimensionDto;

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

    public void setValues(DsdDimensionDto dsdDimensionDto, ExternalItemDto itemScheme, List<ItemDto> items) {
        this.dsdDimensionDto = dsdDimensionDto;
        treeItem.setItems(itemScheme, items);
    }

    /**
     * Updates the region with the specified values. Each {@link RegionValueDto} has a list of {@link KeyValueDto}. All the {@link KeyPartDto} of a {@link KeyValueDto} belongs to the same dimesion.
     * 
     * @param regionValueDto
     */
    public RegionValueDto updateRegionDto(RegionValueDto regionValueDto) {
        Boolean included = DatasetConstraintInclusionTypeEnum.INCLUSION.equals(CommonUtils.getDatasetConstraintInclusionTypeEnum(inclusionTypeField.getValueAsString()));
        List<String> selectedItemaCodes = treeItem.getSelectedItemsCodes();

        KeyValueDto keyValueDto = getKeyValueOfSelectedDimension(regionValueDto);
        if (keyValueDto == null) {
            keyValueDto = new KeyValueDto();
            keyValueDto.setRegion(regionValueDto);
            regionValueDto.addKey(keyValueDto);
        }
        keyValueDto.setIncluded(included);
        keyValueDto.removeAllParts();
        for (String itemCode : selectedItemaCodes) {
            KeyPartDto keyPartDto = new KeyPartDto();
            keyPartDto.setIdentifier(dsdDimensionDto.getDimensionId());
            keyPartDto.setValue(itemCode);
            keyPartDto.setCascadeValues(false); // FIXME METAMAC-1985
            keyPartDto.setPosition(dsdDimensionDto.getPosition());
            keyValueDto.addPart(keyPartDto);
        }
        return regionValueDto;
    }

    /**
     * Returns the {@link KeyValueDto} of the selected dimension (all the {@link KeyPartDto} in a {@link KeyValueDto} belongs to the same dimension).
     * 
     * @param keyValueDto
     * @return
     */
    private KeyValueDto getKeyValueOfSelectedDimension(RegionValueDto regionValueDto) {
        for (KeyValueDto keyValueDto : regionValueDto.getKeys()) {
            for (KeyPartDto keyPartDto : keyValueDto.getParts()) {
                if (StringUtils.equals(dsdDimensionDto.getDimensionId(), keyPartDto.getIdentifier())) {
                    return keyValueDto;
                }
            }
        }
        return null;
    }
}
