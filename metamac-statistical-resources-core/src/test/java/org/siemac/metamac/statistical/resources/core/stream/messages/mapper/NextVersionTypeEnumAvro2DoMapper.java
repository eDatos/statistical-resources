package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.NextVersionTypeEnumAvro;

public class NextVersionTypeEnumAvro2DoMapper {

    protected NextVersionTypeEnumAvro2DoMapper() {
    }

    public static NextVersionTypeEnum avro2Do(NextVersionTypeEnumAvro source) {
        NextVersionTypeEnum target = null;
        for (NextVersionTypeEnumAvro current : NextVersionTypeEnumAvro.values()) {
            if (current.name().equals(source.name())) {
                target = NextVersionTypeEnum.valueOf(current.name());
            }
        }
        return target;
    }

}
