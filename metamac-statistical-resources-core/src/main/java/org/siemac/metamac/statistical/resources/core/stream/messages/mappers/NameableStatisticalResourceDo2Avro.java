package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;

public class NameableStatisticalResourceDo2Avro {

    protected NameableStatisticalResourceDo2Avro() {
    }
    
    public static NameableStatisticalResourceAvro nameableStatisticalResourceDo2Avro(NameableStatisticalResource source) {
        NameableStatisticalResourceAvro target = NameableStatisticalResourceAvro.newBuilder()
                .setIdentifiableStatisticalResource(IdentifiableStatisticalResourceDo2Avro.identifiableStatisticalResourceDo2Avro(source))
                .setDescription(InternationalStringDo2Avro.internationalString2Avro(source.getDescription()))
                .setTitle(InternationalStringDo2Avro.internationalString2Avro(source.getTitle()))
                .build();
        
        return target;
    }

}
