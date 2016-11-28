package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionRationaleTypeEnumAvro;

public class VersionRationaleTypeEnumAvro2DoMapper {

    protected VersionRationaleTypeEnumAvro2DoMapper() {
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

    public static List<VersionRationaleType> avro2Do(List<VersionRationaleTypeEnumAvro> source) {
        List<VersionRationaleType> targetList = new ArrayList<VersionRationaleType>();
        for (VersionRationaleTypeEnumAvro item : source) {
            VersionRationaleType target = new VersionRationaleType(avro2Do(item));
            targetList.add(target);
        }
        return targetList;
    }

}
