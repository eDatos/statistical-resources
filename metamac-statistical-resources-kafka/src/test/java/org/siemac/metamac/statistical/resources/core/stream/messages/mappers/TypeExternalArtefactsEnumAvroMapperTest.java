package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.TypeExternalArtefactsEnumAvro;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class TypeExternalArtefactsEnumAvroMapperTest {

    @Test
    public void testdo2Avro() {
        TypeExternalArtefactsEnumAvro expected = TypeExternalArtefactsEnumAvro.DATA_CONSUMER_SCHEME;
        TypeExternalArtefactsEnum source = TypeExternalArtefactsEnum.DATA_CONSUMER_SCHEME;

        TypeExternalArtefactsEnumAvro actual = TypeExternalArtefactsEnumAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() {
        TypeExternalArtefactsEnum expected = TypeExternalArtefactsEnum.DATA_CONSUMER_SCHEME;
        TypeExternalArtefactsEnumAvro source = TypeExternalArtefactsEnumAvro.DATA_CONSUMER_SCHEME;

        TypeExternalArtefactsEnum actual = TypeExternalArtefactsEnumAvroMapper.avro2Do(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAllDoValuesAreDefinedInAvro() {
        TypeExternalArtefactsEnum[] expected = TypeExternalArtefactsEnum.values();
        for (TypeExternalArtefactsEnum currentExpected : expected) {
            TypeExternalArtefactsEnumAvro actual = TypeExternalArtefactsEnumAvro.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

    @Test
    public void testAllAvroValuesAreDefinedInDo() {
        TypeExternalArtefactsEnumAvro[] expected = TypeExternalArtefactsEnumAvro.values();
        for (TypeExternalArtefactsEnumAvro currentExpected : expected) {
            TypeExternalArtefactsEnum actual = TypeExternalArtefactsEnum.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

}
