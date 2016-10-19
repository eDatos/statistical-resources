package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;

public class NameableStatisticalResourceAvroMapper {

    protected NameableStatisticalResourceAvroMapper() {
    }

    public static void fillMetadata(NameableStatisticalResourceAvro source, NameableStatisticalResource target) {
        IdentifiableStatisticalResourceAvroMapper.fillMetadata(source.getIdentifiableStatisticalResource(), target);
        target.setTitle(InternationalStringAvroMapper.avro2Do(source.getTitle()));
        target.setDescription(InternationalStringAvroMapper.avro2Do(source.getDescription()));
    }

    public static NameableStatisticalResource avro2Do(NameableStatisticalResourceAvro source) {
        NameableStatisticalResource target = new NameableStatisticalResource();
        fillMetadata(source, target);
        return target;
    }
    
    public static NameableStatisticalResourceAvro do2Avro(NameableStatisticalResource source) {
        NameableStatisticalResourceAvro target = NameableStatisticalResourceAvro.newBuilder()
                .setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvroMapper.do2Avro(source))
                .setDescription(InternationalStringAvroMapper.do2Avro(source.getDescription()))
                .setTitle(InternationalStringAvroMapper.do2Avro(source.getTitle()))
                .build();
        
        return target;
    }


}
