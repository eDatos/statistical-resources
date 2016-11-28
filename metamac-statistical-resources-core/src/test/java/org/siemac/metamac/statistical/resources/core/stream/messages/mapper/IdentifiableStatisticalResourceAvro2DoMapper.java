package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;

public class IdentifiableStatisticalResourceAvro2DoMapper {

    protected IdentifiableStatisticalResourceAvro2DoMapper() {
    }

    public static void fillMetadata(IdentifiableStatisticalResourceAvro source, IdentifiableStatisticalResource target) {
        target.setCode(source.getCode());
        target.setUrn(source.getUrn());
        target.setStatisticalOperation(ExternalItemAvro2DoMapper.avro2Do(source.getStatisticalOperation()));
    }

    public static IdentifiableStatisticalResource avro2Do(IdentifiableStatisticalResourceAvro source) {
        IdentifiableStatisticalResource target = new IdentifiableStatisticalResource();
        fillMetadata(source, target);
        return target;
    }

}
