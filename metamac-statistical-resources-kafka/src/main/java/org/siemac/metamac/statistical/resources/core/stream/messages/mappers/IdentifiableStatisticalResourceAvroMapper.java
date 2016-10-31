package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;

public class IdentifiableStatisticalResourceAvroMapper {

    protected IdentifiableStatisticalResourceAvroMapper() {
    }

    public static void fillMetadata(IdentifiableStatisticalResourceAvro source, IdentifiableStatisticalResource target) {
        target.setCode(source.getCode());
        target.setUrn(source.getUrn());
        target.setStatisticalOperation(ExternalItemAvroMapper.avro2Do(source.getStatisticalOperation()));
    }

    public static IdentifiableStatisticalResource avro2Do(IdentifiableStatisticalResourceAvro source) {
        IdentifiableStatisticalResource target = new IdentifiableStatisticalResource();
        fillMetadata(source, target);
        return target;
    }

    public static IdentifiableStatisticalResourceAvro do2Avro(IdentifiableStatisticalResource source) {
        IdentifiableStatisticalResourceAvro target = IdentifiableStatisticalResourceAvro.newBuilder()
                .setCode(source.getCode())
                .setUrn(source.getUrn())
                .setStatisticalOperation(ExternalItemAvroMapper.do2Avro(source.getStatisticalOperation()))
                .build();
        return target;
    }

}
