package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.ProcStatusEnumAvro;

public class ProcStatusEnumDo2AvroMapper {

    protected ProcStatusEnumDo2AvroMapper() {
    }

    public static ProcStatusEnumAvro do2Avro(ProcStatusEnum source) {
        ProcStatusEnumAvro target = null;
        for (ProcStatusEnum current : ProcStatusEnum.values()) {
            if (current != null && source != null && source.name().equals(current.name())) {
                target = ProcStatusEnumAvro.valueOf(current.name());
            }
        }
        return target;
    }

}
