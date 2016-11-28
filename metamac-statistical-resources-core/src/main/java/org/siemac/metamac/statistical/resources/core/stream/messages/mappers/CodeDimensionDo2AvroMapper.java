package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.stream.messages.CodeDimensionAvro;

public class CodeDimensionDo2AvroMapper {

    protected CodeDimensionDo2AvroMapper() {
    }

    public static CodeDimensionAvro do2Avro(CodeDimension source) {
        CodeDimensionAvro target = CodeDimensionAvro.newBuilder().setDatasetVersionUrn(source.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn())
                .setDsdComponentId(source.getDsdComponentId()).setIdentifier(source.getIdentifier()).setTitle(source.getTitle()).build();
        return target;
    }

}
