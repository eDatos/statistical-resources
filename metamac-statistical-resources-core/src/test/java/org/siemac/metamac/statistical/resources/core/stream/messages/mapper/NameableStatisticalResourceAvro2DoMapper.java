package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;

public class NameableStatisticalResourceAvro2DoMapper {

    protected NameableStatisticalResourceAvro2DoMapper() {
    }

    public static void fillMetadata(NameableStatisticalResourceAvro source, NameableStatisticalResource target) {
        IdentifiableStatisticalResourceAvro2DoMapper.fillMetadata(source.getIdentifiableStatisticalResource(), target);
        target.setTitle(InternationalStringAvro2DoMapper.avro2Do(source.getTitle()));
        target.setDescription(InternationalStringAvro2DoMapper.avro2Do(source.getDescription()));
    }

    public static NameableStatisticalResource avro2Do(NameableStatisticalResourceAvro source) {
        NameableStatisticalResource target = new NameableStatisticalResource();
        fillMetadata(source, target);
        return target;
    }

}
