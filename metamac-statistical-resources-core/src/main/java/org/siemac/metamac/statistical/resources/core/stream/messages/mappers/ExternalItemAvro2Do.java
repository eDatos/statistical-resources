package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;

public class ExternalItemAvro2Do {

    protected ExternalItemAvro2Do() {
    }

    public static ExternalItem externalItemAvro2Do(ExternalItemAvro source) {
        ExternalItem target = new ExternalItem();
        target.setCode(source.getCode());
        target.setCodeNested(source.getCodeNested());
        target.setManagementAppUrl(source.getManagementAppUrl());
        target.setTitle(InternationalStringAvro2Do.internationalStringAvro2Do(source.getTitle()));
        target.setType(source.getType());
        target.setUrn(source.getUrn());
        target.setUrnProvider(source.getUrnProvider());
        target.setVersion(source.getVersion());
        return target;

    }

}
