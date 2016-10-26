package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticalResourceTypeEnumAvro;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class StatisticalResourceTypeEnumAvroMapperTest {

    @Test
    public void testdo2Avro() {
        StatisticalResourceTypeEnumAvro expected = StatisticalResourceTypeEnumAvro.DATASET;
        StatisticalResourceTypeEnum source = StatisticalResourceTypeEnum.DATASET;

        StatisticalResourceTypeEnumAvro actual = StatisticalResourceTypeEnumAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() {
        StatisticalResourceTypeEnum expected = StatisticalResourceTypeEnum.DATASET;
        StatisticalResourceTypeEnumAvro source = StatisticalResourceTypeEnumAvro.DATASET;

        StatisticalResourceTypeEnum actual = StatisticalResourceTypeEnumAvroMapper.avro2Do(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAllDoValuesAreDefinedInAvro() {
        StatisticalResourceTypeEnum[] expected = StatisticalResourceTypeEnum.values();
        for (StatisticalResourceTypeEnum currentExpected : expected) {
            StatisticalResourceTypeEnumAvro actual = StatisticalResourceTypeEnumAvro.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

    @Test
    public void testAllAvroValuesAreDefinedInDo() {
        StatisticalResourceTypeEnumAvro[] expected = StatisticalResourceTypeEnumAvro.values();
        for (StatisticalResourceTypeEnumAvro currentExpected : expected) {
            StatisticalResourceTypeEnum actual = StatisticalResourceTypeEnum.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

}
