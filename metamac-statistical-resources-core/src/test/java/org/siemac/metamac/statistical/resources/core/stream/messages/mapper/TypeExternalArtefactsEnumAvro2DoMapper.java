package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.TypeExternalArtefactsEnumAvro;

public class TypeExternalArtefactsEnumAvro2DoMapper {

    protected TypeExternalArtefactsEnumAvro2DoMapper() {
    }

    public static TypeExternalArtefactsEnum avro2Do(TypeExternalArtefactsEnumAvro source) {
        TypeExternalArtefactsEnum target = null;
        for (TypeExternalArtefactsEnumAvro current : TypeExternalArtefactsEnumAvro.values()) {
            if (current.name().equals(source.name())) {
                target = TypeExternalArtefactsEnum.valueOf(current.name());
            }
        }
        return target;
    }

}
