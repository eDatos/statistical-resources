package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValue;
import org.siemac.metamac.statistical.resources.core.stream.messages.AttributeValueAvro;

public class AttributeValueAvroMapper {

    protected AttributeValueAvroMapper() {
    }

    public static AttributeValueAvro do2Avro(AttributeValue source) {
        AttributeValueAvro target = AttributeValueAvro.newBuilder()
                .setDatasetVersionUrn(source.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn())
                .setDsdComponentId(source.getDsdComponentId())
                .setIdentifier(source.getIdentifier())
                .setTitle(source.getTitle())
                .build();
        return target;
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
