package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValue;
import org.siemac.metamac.statistical.resources.core.stream.messages.AttributeValueAvro;

public class AttributeValueAvro2DoMapper {

    protected AttributeValueAvro2DoMapper() {
    }

    public static AttributeValue avro2Do(AttributeValueAvro source) throws MetamacException {
        AttributeValue target = new AttributeValue();
        target.setDatasetVersion(AvroMapperUtils.retrieveDatasetVersion(source.getDatasetVersionUrn()));
        target.setDsdComponentId(source.getDsdComponentId());
        target.setIdentifier(source.getIdentifier());
        target.setTitle(source.getTitle());
        return target;
    }

}
