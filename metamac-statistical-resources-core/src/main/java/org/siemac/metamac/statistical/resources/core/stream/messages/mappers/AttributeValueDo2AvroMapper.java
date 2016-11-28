package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValue;
import org.siemac.metamac.statistical.resources.core.stream.messages.AttributeValueAvro;

public class AttributeValueDo2AvroMapper {

    protected AttributeValueDo2AvroMapper() {
    }

    public static AttributeValueAvro do2Avro(AttributeValue source) {
        AttributeValueAvro target = AttributeValueAvro.newBuilder().setDatasetVersionUrn(source.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn())
                .setDsdComponentId(source.getDsdComponentId()).setIdentifier(source.getIdentifier()).setTitle(source.getTitle()).build();
        return target;
    }

}
