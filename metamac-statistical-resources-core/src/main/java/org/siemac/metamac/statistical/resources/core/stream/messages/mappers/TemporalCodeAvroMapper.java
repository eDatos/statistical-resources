package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.stream.messages.TemporalCodeAvro;

public class TemporalCodeAvroMapper {

    protected TemporalCodeAvroMapper() {
    }
    
    public static TemporalCodeAvro do2Avro(TemporalCode source) {
        TemporalCodeAvro target = TemporalCodeAvro.newBuilder()
                .setIdentifier(source.getIdentifier())
                .setTitle(source.getTitle())
                .setVersion(source.getVersion())
                .build(); 
        return target;
    }
    
    public static TemporalCode avro2Do(TemporalCodeAvro source) {
        TemporalCode target = new TemporalCode();
        target.setIdentifier(source.getIdentifier());
        target.setTitle(source.getTitle());
        target.setVersion(source.getVersion());
        return target;
    }

}
