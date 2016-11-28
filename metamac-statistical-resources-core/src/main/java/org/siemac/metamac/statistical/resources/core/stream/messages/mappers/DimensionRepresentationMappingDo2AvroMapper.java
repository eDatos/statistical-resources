package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;
import org.siemac.metamac.statistical.resources.core.stream.messages.DimensionRepresentationMappingAvro;

public class DimensionRepresentationMappingDo2AvroMapper {

    protected DimensionRepresentationMappingDo2AvroMapper() {
    }

    public static DimensionRepresentationMappingAvro do2Avro(DimensionRepresentationMapping source) {
        DimensionRepresentationMappingAvro target = DimensionRepresentationMappingAvro.newBuilder().setDatasetUrn(source.getDataset().getIdentifiableStatisticalResource().getUrn())
                .setDatasourceFilename(source.getDatasourceFilename()).setMapping(source.getMapping()).build();
        return target;
    }

}
