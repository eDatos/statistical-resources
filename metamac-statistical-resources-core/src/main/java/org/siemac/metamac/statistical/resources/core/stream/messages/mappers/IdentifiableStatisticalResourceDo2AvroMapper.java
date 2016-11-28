package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;

public class IdentifiableStatisticalResourceDo2AvroMapper {

    protected IdentifiableStatisticalResourceDo2AvroMapper() {
    }

    public static IdentifiableStatisticalResourceAvro do2Avro(IdentifiableStatisticalResource source) {
        IdentifiableStatisticalResourceAvro target = null;
        if (source != null) {
            target = IdentifiableStatisticalResourceAvro.newBuilder().setCode(source.getCode()).setUrn(source.getUrn())
                    .setStatisticalOperation(ExternalItemDo2AvroMapper.do2Avro(source.getStatisticalOperation())).build();
        }
        return target;
    }

}
