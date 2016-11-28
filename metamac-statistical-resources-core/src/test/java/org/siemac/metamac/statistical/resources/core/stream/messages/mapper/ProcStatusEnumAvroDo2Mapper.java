package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.ProcStatusEnumAvro;

public class ProcStatusEnumAvroDo2Mapper {

    protected ProcStatusEnumAvroDo2Mapper() {
    }

    public static ProcStatusEnum avro2Do(ProcStatusEnumAvro source) {
        ProcStatusEnum target = null;
        for (ProcStatusEnumAvro current : ProcStatusEnumAvro.values()) {
            if (current.name().equals(source.name())) {
                target = ProcStatusEnum.valueOf(current.name());
            }
        }
        return target;
    }

}
