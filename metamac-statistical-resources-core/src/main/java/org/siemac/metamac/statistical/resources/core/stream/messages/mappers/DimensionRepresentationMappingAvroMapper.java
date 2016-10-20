package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;
import org.siemac.metamac.statistical.resources.core.stream.messages.DimensionRepresentationMappingAvro;

public class DimensionRepresentationMappingAvroMapper {

    protected DimensionRepresentationMappingAvroMapper() {
    }

    public static DimensionRepresentationMappingAvro do2Avro(DimensionRepresentationMapping source) {
        DimensionRepresentationMappingAvro target = DimensionRepresentationMappingAvro.newBuilder()
                .setDatasetUrn(source.getDataset().getIdentifiableStatisticalResource().getUrn())
                .setDatasourceFilename(source.getDatasourceFilename())
                .setMapping(source.getMapping())
                .setVersion(source.getVersion())
                .build();
        return target;
    }

    public static DimensionRepresentationMapping avro2Do(DimensionRepresentationMappingAvro source) throws MetamacException {
        DimensionRepresentationMapping target = new DimensionRepresentationMapping();
        target.setDataset(AvroMapperUtils.retrieveDataset(source.getDatasetUrn()));
        target.setDatasourceFilename(source.getDatasourceFilename());
        target.setMapping(source.getMapping());
        target.setVersion(source.getVersion());
        return target;
    }

}
