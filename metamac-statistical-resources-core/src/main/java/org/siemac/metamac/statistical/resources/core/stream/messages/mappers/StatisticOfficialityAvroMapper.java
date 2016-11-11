package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticOfficialityAvro;

public class StatisticOfficialityAvroMapper {

    protected StatisticOfficialityAvroMapper() {
    }
    
    public static StatisticOfficialityAvro do2Avro(StatisticOfficiality source) {
        StatisticOfficialityAvro target = StatisticOfficialityAvro.newBuilder()
                .setDescription(InternationalStringAvroMapper.do2Avro(source.getDescription()))
                .setIdentifier(source.getIdentifier())
                .build();
        return target;
    }
    
    public static StatisticOfficiality avro2Do(StatisticOfficialityAvro source) {
        StatisticOfficiality target = new StatisticOfficiality();
        target.setDescription(InternationalStringAvroMapper.avro2Do(source.getDescription()));
        target.setIdentifier(source.getIdentifier());
        return target;
    }

}
