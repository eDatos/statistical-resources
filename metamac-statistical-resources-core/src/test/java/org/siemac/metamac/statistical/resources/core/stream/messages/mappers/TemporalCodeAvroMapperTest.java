package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.stream.messages.TemporalCodeAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.TemporalCodeAvro2DoMapper;

public class TemporalCodeAvroMapperTest {

    @Test
    public void testDo2Avro() {
        TemporalCodeAvro expected = MappersMockUtils.mockTemporalCodeAvro();
        TemporalCode source = MappersMockUtils.mockTemporalCode();

        TemporalCodeAvro actual = TemporalCodeDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() throws MetamacException {
        TemporalCode expected = MappersMockUtils.mockTemporalCode();
        TemporalCodeAvro source = MappersMockUtils.mockTemporalCodeAvro();

        TemporalCode actual = TemporalCodeAvro2DoMapper.avro2Do(source);

        assertThat(actual.getIdentifier(), is(equalTo(expected.getIdentifier())));
        assertThat(actual.getTitle(), is(equalTo(expected.getTitle())));
    }

}
