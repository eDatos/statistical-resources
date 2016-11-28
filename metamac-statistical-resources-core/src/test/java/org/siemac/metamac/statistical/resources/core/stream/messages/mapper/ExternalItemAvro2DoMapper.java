package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;

public class ExternalItemAvro2DoMapper {

    protected ExternalItemAvro2DoMapper() {
    }

    public static ExternalItem avro2Do(ExternalItemAvro source) {
        ExternalItem target = null;
        if (source != null) {
            target = new ExternalItem();
            target.setCode(source.getCode());
            target.setCodeNested(source.getCodeNested());
            target.setManagementAppUrl(source.getManagementAppUrl());
            target.setTitle(InternationalStringAvro2DoMapper.avro2Do(source.getTitle()));
            target.setType(TypeExternalArtefactsEnumAvro2DoMapper.avro2Do(source.getType()));
            target.setUrn(source.getUrn());
            target.setUrnProvider(source.getUrnProvider());
            try {
                target.setUri(AvroMapperUtils.selfLink2Uri(source.getSelfLink()));
            } catch (MetamacException e) {
            }
        }
        return target;
    }

}
