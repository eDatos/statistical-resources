package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;

public class ExternalItemAvroMapper {

    protected ExternalItemAvroMapper() {
    }

    public static ExternalItem avro2Do(ExternalItemAvro source) {
        ExternalItem target = new ExternalItem();
        target.setCode(source.getCode());
        target.setCodeNested(source.getCodeNested());
        target.setManagementAppUrl(source.getManagementAppUrl());
        target.setTitle(InternationalStringAvroMapper.avro2Do(source.getTitle()));
        target.setType(source.getType());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setVersion(source.getVersion());
        return target;
    }
    
    public static ExternalItemAvro do2Avro(ExternalItem ei) {
        ExternalItemAvro target = ExternalItemAvro.newBuilder()
                .setCode(ei.getCode())
                .setCodeNested(ei.getCodeNested())
                .setManagementAppUrl(ei.getManagementAppUrl())
                .setTitle(InternationalStringAvroMapper.do2Avro(ei.getTitle()))
                .setType(ei.getType())
                .setUrn(ei.getUrn())
                .setUrnProvider(ei.getUrnProvider())
                .setVersion(ei.getVersion())
                .build();
        return target;
        
    }
}
