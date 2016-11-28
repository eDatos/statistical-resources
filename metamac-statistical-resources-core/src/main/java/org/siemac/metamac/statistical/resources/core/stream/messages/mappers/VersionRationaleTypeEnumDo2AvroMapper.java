package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionRationaleTypeEnumAvro;

public class VersionRationaleTypeEnumDo2AvroMapper {

    protected VersionRationaleTypeEnumDo2AvroMapper() {
    }

    public static VersionRationaleTypeEnumAvro do2Avro(VersionRationaleTypeEnum source) {
        VersionRationaleTypeEnumAvro target = null;
        for (VersionRationaleTypeEnum current : VersionRationaleTypeEnum.values()) {
            if (current != null && source != null && source.name().equals(current.name())) {
                target = VersionRationaleTypeEnumAvro.valueOf(current.name());
                break;
            }
        }
        return target;
    }

    public static List<VersionRationaleTypeEnumAvro> do2Avro(List<VersionRationaleType> source) {
        List<VersionRationaleTypeEnumAvro> target = new ArrayList<VersionRationaleTypeEnumAvro>();
        for (VersionRationaleType item : source) {
            target.add(do2Avro(item.getValue()));
        }
        return target;
    }

}
