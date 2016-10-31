package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.TypeRelatedResourceEnumAvro;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class TypeRelatedResourceEnumAvroMapperTest {

    @Test
    public void testdo2Avro() {
        TypeRelatedResourceEnumAvro expected = TypeRelatedResourceEnumAvro.DATASET_VERSION;
        TypeRelatedResourceEnum source = TypeRelatedResourceEnum.DATASET_VERSION;

        TypeRelatedResourceEnumAvro actual = TypeRelatedResourceEnumAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() {
        TypeRelatedResourceEnum expected = TypeRelatedResourceEnum.DATASET_VERSION;
        TypeRelatedResourceEnumAvro source = TypeRelatedResourceEnumAvro.DATASET_VERSION;

        TypeRelatedResourceEnum actual = TypeRelatedResourceEnumAvroMapper.avro2Do(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAllDoValuesAreDefinedInAvro() {
        TypeRelatedResourceEnum[] expected = TypeRelatedResourceEnum.values();
        for (TypeRelatedResourceEnum currentExpected : expected) {
            TypeRelatedResourceEnumAvro actual = TypeRelatedResourceEnumAvro.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

    @Test
    public void testAllAvroValuesAreDefinedInDo() {
        TypeRelatedResourceEnumAvro[] expected = TypeRelatedResourceEnumAvro.values();
        for (TypeRelatedResourceEnumAvro currentExpected : expected) {
            TypeRelatedResourceEnum actual = TypeRelatedResourceEnum.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

}
