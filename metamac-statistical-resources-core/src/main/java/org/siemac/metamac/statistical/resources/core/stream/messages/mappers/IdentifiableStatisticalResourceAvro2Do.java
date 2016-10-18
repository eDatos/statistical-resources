package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;

public class IdentifiableStatisticalResourceAvro2Do {

    protected IdentifiableStatisticalResourceAvro2Do() {
    }

    public static void fillMetadata(IdentifiableStatisticalResourceAvro source, IdentifiableStatisticalResource target) {
        target.setCode((String) source.getCode());
        target.setUrn((String) source.getUrn());
    }

    public static IdentifiableStatisticalResource identifiableStatisticalResourceAvro2Do(IdentifiableStatisticalResourceAvro source) {
        IdentifiableStatisticalResource target = new IdentifiableStatisticalResource();
        fillMetadata(source, target);
        return target;
    }
}
