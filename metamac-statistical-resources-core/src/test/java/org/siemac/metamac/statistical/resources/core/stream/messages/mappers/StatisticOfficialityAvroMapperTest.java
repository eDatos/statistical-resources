package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticOfficialityAvro;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;


public class StatisticOfficialityAvroMapperTest {

    @Test
    public void testDo2Avro() {
        StatisticOfficialityAvro expected = MappersMockUtils.mockStatisticOfficialityAvro();
        StatisticOfficiality source = MappersMockUtils.mockStatisticOfficiality();

        StatisticOfficialityAvro actual = StatisticOfficialityAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() {
        StatisticOfficiality expected = MappersMockUtils.mockStatisticOfficiality();
        StatisticOfficialityAvro source = MappersMockUtils.mockStatisticOfficialityAvro();

        StatisticOfficiality actual = StatisticOfficialityAvroMapper.avro2Do(source);

        assertThat(actual.getIdentifier(), is(equalTo(expected.getIdentifier())));
        assertThat(actual.getVersion(), is(equalTo(expected.getVersion())));
        CommonAsserts.assertEqualsInternationalString(expected.getDescription(), actual.getDescription());
    }

}
