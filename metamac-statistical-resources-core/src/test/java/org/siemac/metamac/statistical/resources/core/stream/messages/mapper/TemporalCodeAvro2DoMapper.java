package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.stream.messages.TemporalCodeAvro;

public class TemporalCodeAvro2DoMapper {

    protected TemporalCodeAvro2DoMapper() {
    }

    public static TemporalCode avro2Do(TemporalCodeAvro source) {
        TemporalCode target = new TemporalCode();
        target.setIdentifier(source.getIdentifier());
        target.setTitle(source.getTitle());
        return target;
    }

}
