package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
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
            try {
                target.setUri(AvroMapperUtils.selfLink2Uri(source.getSelfLink()));
            } catch (MetamacException e) {
            }
        }
        return target;
    }

    public static ExternalItemAvro do2Avro(ExternalItem source)  {
        ExternalItemAvro target = null;
        if (source != null) {
            try {
                target = ExternalItemAvro.newBuilder()
                    .setCode(source.getCode())
                    .setCodeNested(source.getCodeNested())
                    .setManagementAppUrl(source.getManagementAppUrl())
                    .setTitle(InternationalStringAvroMapper.do2Avro(source.getTitle()))
                    .setType(TypeExternalArtefactsEnumAvroMapper.do2Avro(source.getType()))
                    .setUrn(source.getUrn())
                    .setUrnProvider(source.getUrnProvider())
                    .setSelfLink(AvroMapperUtils.getSelfLink(source))
                    .build();
            } catch (MetamacException e) {
            }
        }
        return target;

    }

}
