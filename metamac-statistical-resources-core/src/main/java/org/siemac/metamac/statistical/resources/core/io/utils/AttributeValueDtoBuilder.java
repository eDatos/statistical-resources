package org.siemac.metamac.statistical.resources.core.io.utils;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.AttributeValueDto;

public class AttributeValueDtoBuilder {

    private String          stringValue;
    private ExternalItemDto externalItemValue;
    private Long            id;
    private String          uuid;
    private Long            version;

    private AttributeValueDtoBuilder() {
    }
    public AttributeValueDtoBuilder withStringValue(String stringValue) {
        this.stringValue = stringValue;
        return this;
    }
    public AttributeValueDtoBuilder withExternalItemValue(ExternalItemDto externalItemValue) {
        this.externalItemValue = externalItemValue;
        return this;
    }
    public AttributeValueDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    public AttributeValueDtoBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
    public AttributeValueDtoBuilder withVersion(Long version) {
        this.version = version;
        return this;
    }
    public AttributeValueDto build() {
        AttributeValueDto attributeValueDto = new AttributeValueDto();
        attributeValueDto.setStringValue(stringValue);
        attributeValueDto.setExternalItemValue(externalItemValue);
        attributeValueDto.setId(id);
        attributeValueDto.setUuid(uuid);
        attributeValueDto.setVersion(version);

        return attributeValueDto;
    }

    public static AttributeValueDtoBuilder builder() {
        return new AttributeValueDtoBuilder();
    }

}
