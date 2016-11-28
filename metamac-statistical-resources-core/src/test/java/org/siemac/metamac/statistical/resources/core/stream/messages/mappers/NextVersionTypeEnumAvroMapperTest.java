package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.NextVersionTypeEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.NextVersionTypeEnumAvro2DoMapper;

public class NextVersionTypeEnumAvroMapperTest {

    @Test
    public void testdo2Avro() {
        NextVersionTypeEnumAvro expected = NextVersionTypeEnumAvro.NON_SCHEDULED_UPDATE;
        NextVersionTypeEnum source = NextVersionTypeEnum.NON_SCHEDULED_UPDATE;

        NextVersionTypeEnumAvro actual = NextVersionTypeEnumDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() {
        NextVersionTypeEnum expected = NextVersionTypeEnum.NON_SCHEDULED_UPDATE;
        NextVersionTypeEnumAvro source = NextVersionTypeEnumAvro.NON_SCHEDULED_UPDATE;

        NextVersionTypeEnum actual = NextVersionTypeEnumAvro2DoMapper.avro2Do(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAllDoValuesAreDefinedInAvro() {
        NextVersionTypeEnum[] expected = NextVersionTypeEnum.values();
        for (NextVersionTypeEnum currentExpected : expected) {
            NextVersionTypeEnumAvro actual = NextVersionTypeEnumAvro.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

    @Test
    public void testAllAvroValuesAreDefinedInDo() {
        NextVersionTypeEnumAvro[] expected = NextVersionTypeEnumAvro.values();
        for (NextVersionTypeEnumAvro currentExpected : expected) {
            NextVersionTypeEnum actual = NextVersionTypeEnum.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

}
