package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.TypeRelatedResourceEnumAvro;

public class TypeRelatedResourceEnumAvroMapper {

    protected TypeRelatedResourceEnumAvroMapper() {
    }


    public static TypeRelatedResourceEnum avro2Do(TypeRelatedResourceEnumAvro source) {
        TypeRelatedResourceEnum target = null;
        for (TypeRelatedResourceEnumAvro current : TypeRelatedResourceEnumAvro.values()) {
            if (current.name().equals(source.name())) {
                target = TypeRelatedResourceEnum.valueOf(current.name());
            }
        }
        return target;
    }

    public static TypeRelatedResourceEnumAvro do2Avro(TypeRelatedResourceEnum source) {
        TypeRelatedResourceEnumAvro target = null;
        for (TypeRelatedResourceEnum current : TypeRelatedResourceEnum.values()) {
            if (current != null && source != null && source.name().equals(current.name())) {
                target = TypeRelatedResourceEnumAvro.valueOf(current.name());
            }
        }
        return target;
    }

}
