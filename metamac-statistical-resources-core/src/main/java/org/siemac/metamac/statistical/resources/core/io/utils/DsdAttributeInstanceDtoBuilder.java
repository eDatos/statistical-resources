package org.siemac.metamac.statistical.resources.core.io.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dto.datasets.AttributeValueDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DsdAttributeInstanceDto;
import org.siemac.metamac.statistical.resources.core.dto.query.CodeItemDto;

public class DsdAttributeInstanceDtoBuilder {

    private String                         attributeId;
    private Map<String, List<CodeItemDto>> codeDimensions = new HashMap<>();
    private AttributeValueDto              value;
    private Long                           id;
    private String                         uuid;
    private Long                           version;

    private DsdAttributeInstanceDtoBuilder() {
    }
    public DsdAttributeInstanceDtoBuilder withAttributeId(String attributeId) {
        this.attributeId = attributeId;
        return this;
    }
    public DsdAttributeInstanceDtoBuilder withCodeDimensions(Map<String, List<CodeItemDto>> codeDimensions) {
        this.codeDimensions = codeDimensions;
        return this;
    }
    public DsdAttributeInstanceDtoBuilder withValue(AttributeValueDto value) {
        this.value = value;
        return this;
    }
    public DsdAttributeInstanceDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    public DsdAttributeInstanceDtoBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
    public DsdAttributeInstanceDtoBuilder withVersion(Long version) {
        this.version = version;
        return this;
    }
    public DsdAttributeInstanceDto build() {
        DsdAttributeInstanceDto dsdAttributeInstanceDto = new DsdAttributeInstanceDto();
        dsdAttributeInstanceDto.setAttributeId(attributeId);
        dsdAttributeInstanceDto.setCodeDimensions(codeDimensions);
        dsdAttributeInstanceDto.setValue(value);
        dsdAttributeInstanceDto.setId(id);
        dsdAttributeInstanceDto.setUuid(uuid);
        dsdAttributeInstanceDto.setVersion(version);

        return dsdAttributeInstanceDto;
    }
    public static DsdAttributeInstanceDtoBuilder builder() {
        return new DsdAttributeInstanceDtoBuilder();
    }
}
