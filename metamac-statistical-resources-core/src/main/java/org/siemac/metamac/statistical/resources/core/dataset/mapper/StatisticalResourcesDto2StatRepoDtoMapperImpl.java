package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dto.datasets.AttributeValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.InternationalStringDto;
import com.arte.statistic.dataset.repository.dto.LocalisedStringDto;

@Component(StatisticalResourcesDto2StatRepoDtoMapper.BEAN_ID)
public class StatisticalResourcesDto2StatRepoDtoMapperImpl implements StatisticalResourcesDto2StatRepoDtoMapper {

    @Override
    public AttributeDto dsdAttributeInstaceDtoToAttributeDto(DsdAttributeInstanceDto source) throws MetamacException {
        if (source == null) {
            return null;
        }
        AttributeDto target = new AttributeDto();
        target.setAttributeId(source.getAttributeId());
        target.setValue(attributeValueDtoToInternationalStringValue(source.getValue()));
        target.setCodesByDimension(codeItemMapToStringMap(source.getCodeDimensions()));
        return target;
    }

    private InternationalStringDto attributeValueDtoToInternationalStringValue(AttributeValueDto attributeValueDto) {
        if (attributeValueDto == null) {
            return null;
        }
        LocalisedStringDto localisedValue = new LocalisedStringDto();
        localisedValue.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
        if (StringUtils.isNotBlank(attributeValueDto.getStringValue())) {
            localisedValue.setLabel(attributeValueDto.getStringValue());
        } else {
            localisedValue.setLabel(attributeValueDto.getExternalItemValue() != null ? attributeValueDto.getExternalItemValue().getCode() : null);
        }
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        internationalStringDto.addText(localisedValue);
        return internationalStringDto;
    }

    private Map<String, List<String>> codeItemMapToStringMap(Map<String, List<CodeItemDto>> codeItemMap) {
        if (codeItemMap == null) {
            return null;
        }
        Map<String, List<String>> stringMap = new HashMap<String, List<String>>();
        for (String dimensionId : codeItemMap.keySet()) {
            stringMap.put(dimensionId, codeItemListToStringList(codeItemMap.get(dimensionId)));
        }
        return stringMap;
    }

    private List<String> codeItemListToStringList(List<CodeItemDto> codeItemDtos) {
        List<String> codes = new ArrayList<String>();
        for (CodeItemDto codeItemDto : codeItemDtos) {
            codes.add(codeItemDto.getCode());
        }
        return codes;

    }
}
