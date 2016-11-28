package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticalResourceTypeEnumAvro;

public class StatisticalResourceTypeEnumAvro2DoMapper {

    protected StatisticalResourceTypeEnumAvro2DoMapper() {
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

}
