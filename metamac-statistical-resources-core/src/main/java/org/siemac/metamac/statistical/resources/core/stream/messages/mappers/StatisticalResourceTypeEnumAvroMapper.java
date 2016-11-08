package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticalResourceTypeEnumAvro;

public class StatisticalResourceTypeEnumAvroMapper {

    protected StatisticalResourceTypeEnumAvroMapper() {
    }


    public static StatisticalResourceTypeEnum avro2Do(StatisticalResourceTypeEnumAvro source) {
        StatisticalResourceTypeEnum target = null;
        for (StatisticalResourceTypeEnumAvro current : StatisticalResourceTypeEnumAvro.values()) {
            if (current.name().equals(source.name())) {
                target = StatisticalResourceTypeEnum.valueOf(current.name());
            }
        }
        return target;
    }

    public static StatisticalResourceTypeEnumAvro do2Avro(StatisticalResourceTypeEnum source) {
        StatisticalResourceTypeEnumAvro target = null;
        for (StatisticalResourceTypeEnum current : StatisticalResourceTypeEnum.values()) {
            if (current != null && source != null && source.name().equals(current.name())) {
                target = StatisticalResourceTypeEnumAvro.valueOf(current.name());
            }
        }
        return target;
    }

}
