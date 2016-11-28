package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;

public class NameableStatisticalResourceDo2AvroMapper {

    protected NameableStatisticalResourceDo2AvroMapper() {
    }

    public static NameableStatisticalResourceAvro do2Avro(NameableStatisticalResource source) {
        NameableStatisticalResourceAvro target = null;
        if (source != null) {
            target = NameableStatisticalResourceAvro.newBuilder().setIdentifiableStatisticalResource(IdentifiableStatisticalResourceDo2AvroMapper.do2Avro(source))
                    .setDescription(InternationalStringDo2AvroMapper.do2Avro(source.getDescription())).setTitle(InternationalStringDo2AvroMapper.do2Avro(source.getTitle())).build();
        }
        return target;
    }

}
