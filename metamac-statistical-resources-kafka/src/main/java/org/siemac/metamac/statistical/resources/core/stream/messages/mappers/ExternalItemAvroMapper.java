package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;

public class ExternalItemAvroMapper {

    protected ExternalItemAvroMapper() {
    }

    public static ExternalItem avro2Do(ExternalItemAvro source) {
        ExternalItem target = null;
        if (source != null) {
            target = new ExternalItem();
            target.setCode(source.getCode());
            target.setCodeNested(source.getCodeNested());
            target.setManagementAppUrl(source.getManagementAppUrl());
            target.setTitle(InternationalStringAvroMapper.avro2Do(source.getTitle()));
            target.setType(TypeExternalArtefactsEnumAvroMapper.avro2Do(source.getType()));
            target.setUrn(source.getUrn());
            target.setUrnProvider(source.getUrnProvider());
            target.setVersion(source.getVersion());
        }
        return target;
    }

    public static ExternalItemAvro do2Avro(ExternalItem ei) {
        ExternalItemAvro target = null;
        if (ei != null) {
            target = ExternalItemAvro.newBuilder()
                .setCode(ei.getCode())
                .setCodeNested(ei.getCodeNested())
                .setManagementAppUrl(ei.getManagementAppUrl())
                .setTitle(InternationalStringAvroMapper.do2Avro(ei.getTitle()))
                .setType(TypeExternalArtefactsEnumAvroMapper.do2Avro(ei.getType()))
                .setUrn(ei.getUrn())
                .setUrnProvider(ei.getUrnProvider())
                .setVersion(ei.getVersion())
                .build();
        }
        return target;

    }
}
