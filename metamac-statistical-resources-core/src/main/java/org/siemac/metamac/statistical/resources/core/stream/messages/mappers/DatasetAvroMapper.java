package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DimensionRepresentationMappingAvro;

public class DatasetAvroMapper {

    protected DatasetAvroMapper() {
    }

    public static DatasetAvro do2Avro(Dataset source) {
        List<DimensionRepresentationMappingAvro> dimensions = dimensionsDo2Avro(source);
        List<String> versions = datasetVersionsDo2Avro(source);
        DatasetAvro target = DatasetAvro.newBuilder()
                .setDimensionRepresentationMappings(dimensions)
                .setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvroMapper.do2Avro(source.getIdentifiableStatisticalResource()))
                .setVersionsUrns(versions)
                .setVersion(source.getVersion())
                .build();
        return target;
    }

    public static Dataset avro2Do(DatasetAvro source) throws MetamacException {
        Dataset target = new Dataset();
        target.setIdentifiableStatisticalResource(IdentifiableStatisticalResourceAvroMapper.avro2Do(source.getIdentifiableStatisticalResource()));
        target.setVersion(source.getVersion());
        addDimensionRepresentatations(source, target);
        addDatasetVersions(source, target);
        return target;
    }

    protected static void addDatasetVersions(DatasetAvro source, Dataset target) throws MetamacException {
        for (String version : source.getVersionsUrns()) {
            DatasetVersion versionElement = AvroMapperUtils.retrieveDatasetVersion(version);
            target.addVersion(versionElement);
        }
    }

    protected static void addDimensionRepresentatations(DatasetAvro source, Dataset target) throws MetamacException {
        for(DimensionRepresentationMappingAvro dimension : source.getDimensionRepresentationMappings()) {
                target.addDimensionRepresentationMapping(DimensionRepresentationMappingAvroMapper.avro2Do(dimension));
        }
    }

    protected static List<DimensionRepresentationMappingAvro> dimensionsDo2Avro(Dataset source) {
        List<DimensionRepresentationMappingAvro> dimensions = new ArrayList<DimensionRepresentationMappingAvro>();
        source.getDimensionRepresentationMappings().forEach(dimension -> {
            dimensions.add(DimensionRepresentationMappingAvroMapper.do2Avro(dimension));
        });
        return dimensions;
    }

    protected static List<String> datasetVersionsDo2Avro(Dataset source) {
        List<String> versions = new ArrayList<String>();
        source.getVersions().forEach(datasetVersion -> {
            versions.add(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
        });
        return versions;
    }

}
