package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DimensionRepresentationMappingAvro;

public class DatasetAvro2DoMapper {

    protected DatasetAvro2DoMapper() {
    }

    public static Dataset avro2Do(DatasetAvro source) throws MetamacException {
        Dataset target = new Dataset();
        target.setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvro2DoMapper.avro2Do(source.getIdentifiableStatisticalResource()));
        addDimensionRepresentatations(source, target);
        addDatasetVersions(source, target);
        return target;
    }

    protected static void addDatasetVersions(DatasetAvro source, Dataset target) throws MetamacException {
        for (String version : source.getDatasetVersionsUrns()) {
            DatasetVersion versionElement = AvroMapperUtils.retrieveDatasetVersion(version);
            target.addVersion(versionElement);
        }
    }

    protected static void addDimensionRepresentatations(DatasetAvro source, Dataset target) throws MetamacException {
        for (DimensionRepresentationMappingAvro dimension : source.getDimensionRepresentationMappings()) {
            target.addDimensionRepresentationMapping(DimensionRepresentationMappingAvro2DoMapper.avro2Do(dimension));
        }
    }

}
