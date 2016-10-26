package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.NextVersionTypeEnumAvro;

public class NextVersionTypeEnumAvroMapper {

    protected NextVersionTypeEnumAvroMapper() {
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

    public static NextVersionTypeEnumAvro do2Avro(NextVersionTypeEnum source) {
        NextVersionTypeEnumAvro target = null;
        for (NextVersionTypeEnum current : NextVersionTypeEnum.values()) {
            if (current != null && source != null && source.name().equals(current.name())) {
                target = NextVersionTypeEnumAvro.valueOf(current.name());
            }
        }
        return target;
    }

}
