package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.TypeExternalArtefactsEnumAvro;

public class TypeExternalArtefactsEnumMapper {

    protected TypeExternalArtefactsEnumMapper() {
    }

    public static TypeExternalArtefactsEnum avro2Do(TypeExternalArtefactsEnumAvro source) {
        return TypeExternalArtefactsEnum.values()[source.ordinal()];
    }

    public static TypeExternalArtefactsEnumAvro do2Avro(TypeExternalArtefactsEnum source) {
        return TypeExternalArtefactsEnumAvro.values()[source.ordinal()];
    }

}
