package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;

public class ExternalItemAvro2Do {

    protected ExternalItemAvro2Do() {
    }

    public static ExternalItem externalItemAvro2Do(ExternalItemAvro source) {
        ExternalItem target = new ExternalItem();
        target.setCode((String) source.getCode());
        target.setCodeNested((String) source.getCodeNested());
        target.setManagementAppUrl((String) source.getManagementAppUrl());
        target.setTitle(InternationalStringAvro2Do.internationalStringAvro2Do(source.getTitle()));
        target.setType(TypeExternalArtefactsEnumMapper.avro2Do(source.getType()));
        target.setUrn((String) source.getUrn());
        target.setUrnProvider((String) source.getUrnProvider());
        target.setVersion(source.getVersion());
        return target;

    }

}
