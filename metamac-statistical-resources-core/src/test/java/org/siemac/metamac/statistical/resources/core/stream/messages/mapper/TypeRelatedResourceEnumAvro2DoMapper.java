package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.TypeRelatedResourceEnumAvro;

public class TypeRelatedResourceEnumAvro2DoMapper {

    protected TypeRelatedResourceEnumAvro2DoMapper() {
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

}
