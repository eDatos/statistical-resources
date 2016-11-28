package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.NextVersionTypeEnumAvro;

public class NextVersionTypeEnumDo2AvroMapper {

    protected NextVersionTypeEnumDo2AvroMapper() {
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
