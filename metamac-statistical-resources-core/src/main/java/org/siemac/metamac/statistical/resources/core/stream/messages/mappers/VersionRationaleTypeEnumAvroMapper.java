package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionRationaleTypeEnumAvro;

public class VersionRationaleTypeEnumAvroMapper {

    protected VersionRationaleTypeEnumAvroMapper() {
    }


    public static VersionRationaleTypeEnum avro2Do(VersionRationaleTypeEnumAvro source) {
        VersionRationaleTypeEnum target = null;
        for (VersionRationaleTypeEnumAvro current : VersionRationaleTypeEnumAvro.values()) {
            if (current.name().equals(source.name())) {
                target = VersionRationaleTypeEnum.valueOf(current.name());
                break;
            }
        }
        return target;
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

    public static List<VersionRationaleType> avro2Do(List<VersionRationaleTypeEnumAvro> source) {
        List<VersionRationaleType> targetList = new ArrayList<VersionRationaleType>();
        for (VersionRationaleTypeEnumAvro item : source) {
            VersionRationaleType target = new VersionRationaleType(avro2Do(item));
            targetList.add(target);
        }
        return targetList;
    }

}
