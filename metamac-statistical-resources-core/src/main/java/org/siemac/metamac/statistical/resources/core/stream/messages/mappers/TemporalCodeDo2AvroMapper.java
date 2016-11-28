package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.stream.messages.TemporalCodeAvro;

public class TemporalCodeDo2AvroMapper {

    protected TemporalCodeDo2AvroMapper() {
    }

    public static TemporalCodeAvro do2Avro(TemporalCode source) {
        TemporalCodeAvro target = TemporalCodeAvro.newBuilder().setIdentifier(source.getIdentifier()).setTitle(source.getTitle()).build();
        return target;
    }

}
