package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;
import org.siemac.metamac.statistical.resources.core.stream.messages.DimensionRepresentationMappingAvro;

public class DimensionRepresentationMappingAvro2DoMapper {

    protected DimensionRepresentationMappingAvro2DoMapper() {
    }

    public static DimensionRepresentationMapping avro2Do(DimensionRepresentationMappingAvro source) throws MetamacException {
        DimensionRepresentationMapping target = new DimensionRepresentationMapping();
        target.setDataset(AvroMapperUtils.retrieveDataset(source.getDatasetUrn()));
        target.setDatasourceFilename(source.getDatasourceFilename());
        target.setMapping(source.getMapping());
        return target;
    }

}
