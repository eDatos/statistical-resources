package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticOfficialityAvro;

public class StatisticOfficialityAvroMapperTest {

    @Test
    public void testDo2Avro() {
        StatisticOfficialityAvro expected = MappersMockUtils.mockStatisticOfficialityAvro();
        StatisticOfficiality source = MappersMockUtils.mockStatisticOfficiality();

        StatisticOfficialityAvro actual = StatisticOfficialityDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

}
