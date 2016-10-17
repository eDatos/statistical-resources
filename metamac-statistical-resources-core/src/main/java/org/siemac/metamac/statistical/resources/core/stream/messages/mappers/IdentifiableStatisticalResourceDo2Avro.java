package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;

public class IdentifiableStatisticalResourceDo2Avro {

    protected IdentifiableStatisticalResourceDo2Avro() {
    }
    
    public static IdentifiableStatisticalResourceAvro identifiableStatisticalResourceDo2Avro(IdentifiableStatisticalResource source) {
        IdentifiableStatisticalResourceAvro target = IdentifiableStatisticalResourceAvro.newBuilder()
                .setCode(source.getCode())
                .setUrn(source.getUrn())
                .build();
        return target;
    }

}
