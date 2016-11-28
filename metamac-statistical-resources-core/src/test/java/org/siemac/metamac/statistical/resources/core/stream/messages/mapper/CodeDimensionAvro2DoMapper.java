package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.stream.messages.CodeDimensionAvro;

public class CodeDimensionAvro2DoMapper {

    protected CodeDimensionAvro2DoMapper() {
    }

    public static CodeDimension avro2Do(CodeDimensionAvro source) throws MetamacException {
        CodeDimension target = new CodeDimension();
        target.setDatasetVersion(AvroMapperUtils.retrieveDatasetVersion(source.getDatasetVersionUrn()));
        target.setDsdComponentId(source.getDsdComponentId());
        target.setIdentifier(source.getIdentifier());
        target.setTitle(source.getTitle());
        return target;
    }

}
