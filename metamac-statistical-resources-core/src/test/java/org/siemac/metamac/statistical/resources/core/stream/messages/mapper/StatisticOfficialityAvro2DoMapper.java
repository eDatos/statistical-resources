package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticOfficialityAvro;

public class StatisticOfficialityAvro2DoMapper {

    protected StatisticOfficialityAvro2DoMapper() {
    }

    public static StatisticOfficiality avro2Do(StatisticOfficialityAvro source) {
        StatisticOfficiality target = new StatisticOfficiality();
        target.setDescription(InternationalStringAvro2DoMapper.avro2Do(source.getDescription()));
        target.setIdentifier(source.getIdentifier());
        return target;
    }

}
