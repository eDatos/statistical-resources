package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.stream.messages.TemporalCodeAvro;

public class TemporalCodeAvroMapperTest {

    @Test
    public void testDo2Avro() {
        TemporalCodeAvro expected = MappersMockUtils.mockTemporalCodeAvro();
        TemporalCode source = MappersMockUtils.mockTemporalCode();

        TemporalCodeAvro actual = TemporalCodeDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

}
