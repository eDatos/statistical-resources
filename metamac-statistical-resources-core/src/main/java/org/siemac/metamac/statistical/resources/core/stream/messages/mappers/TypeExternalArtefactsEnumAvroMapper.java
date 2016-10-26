package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.TypeExternalArtefactsEnumAvro;

public class TypeExternalArtefactsEnumAvroMapper {

    protected TypeExternalArtefactsEnumAvroMapper() {
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

    public static TypeExternalArtefactsEnumAvro do2Avro(TypeExternalArtefactsEnum source) {
        TypeExternalArtefactsEnumAvro target = null;
        for (TypeExternalArtefactsEnum current : TypeExternalArtefactsEnum.values()) {
            if (current != null && source != null && source.name().equals(current.name())) {
                target = TypeExternalArtefactsEnumAvro.valueOf(current.name());
            }
        }
        return target;
    }

}
