package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.stream.messages.CodeDimensionAvro;

public class CodeDimensionAvroMapper {

    protected CodeDimensionAvroMapper() {
    }

    public static CodeDimensionAvro do2Avro(CodeDimension source) {
        CodeDimensionAvro target = CodeDimensionAvro.newBuilder()
                .setDatasetVersionUrn(source.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn())
                .setDsdComponentId(source.getDsdComponentId())
                .setIdentifier(source.getIdentifier())
                .setTitle(source.getTitle())
                .setVersion(source.getVersion())
                .build();
        return target;
    }

    public static CodeDimension avro2Do(CodeDimensionAvro source) throws MetamacException {
        CodeDimension target = new CodeDimension();
        target.setDatasetVersion(AvroMapperUtils.retrieveDatasetVersion(source.getDatasetVersionUrn()));
        target.setDsdComponentId(source.getDsdComponentId());
        target.setIdentifier(source.getIdentifier());
        target.setTitle(source.getTitle());
        target.setVersion(source.getVersion());
        return target;
    }

}
