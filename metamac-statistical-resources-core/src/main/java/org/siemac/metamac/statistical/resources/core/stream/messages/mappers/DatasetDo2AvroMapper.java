package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DimensionRepresentationMapping;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DimensionRepresentationMappingAvro;

public class DatasetDo2AvroMapper {

    protected DatasetDo2AvroMapper() {
    }

    public static DatasetAvro do2Avro(Dataset source) {
        List<DimensionRepresentationMappingAvro> dimensions = dimensionsDo2Avro(source);
        List<String> versions = datasetVersionsDo2Avro(source);
        DatasetAvro target = DatasetAvro.newBuilder().setDimensionRepresentationMappings(dimensions)
                .setIdentifiableStatisticalResource(IdentifiableStatisticalResourceDo2AvroMapper.do2Avro(source.getIdentifiableStatisticalResource())).setDatasetVersionsUrns(versions).build();
        return target;
    }

    protected static void addDatasetVersions(DatasetAvro source, Dataset target) throws MetamacException {
        for (String version : source.getDatasetVersionsUrns()) {
            DatasetVersion versionElement = AvroMapperUtils.retrieveDatasetVersion(version);
            target.addVersion(versionElement);
        }
    }

    protected static List<DimensionRepresentationMappingAvro> dimensionsDo2Avro(Dataset source) {
        List<DimensionRepresentationMappingAvro> dimensions = new ArrayList<DimensionRepresentationMappingAvro>();
        if (null != source) {
            for (DimensionRepresentationMapping dimension : source.getDimensionRepresentationMappings()) {
                dimensions.add(DimensionRepresentationMappingDo2AvroMapper.do2Avro(dimension));
            }
        }
        return dimensions;
    }

    protected static List<String> datasetVersionsDo2Avro(Dataset source) {
        List<String> versions = new ArrayList<String>();
        if (null != source) {
            for (DatasetVersion datasetVersion : source.getVersions()) {
                versions.add(datasetVersion.getSiemacMetadataStatisticalResource().getUrn());
            }
        }
        return versions;
    }

}
