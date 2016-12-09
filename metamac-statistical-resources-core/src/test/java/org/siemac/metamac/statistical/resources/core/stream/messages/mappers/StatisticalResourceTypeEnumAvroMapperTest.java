package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticalResourceTypeEnumAvro;

public class StatisticalResourceTypeEnumAvroMapperTest {

    @Test
    public void testdo2Avro() {
        StatisticalResourceTypeEnumAvro expected = StatisticalResourceTypeEnumAvro.DATASET;
        StatisticalResourceTypeEnum source = StatisticalResourceTypeEnum.DATASET;

        StatisticalResourceTypeEnumAvro actual = StatisticalResourceTypeEnumDo2AvroMapper.do2Avro(source);

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
