package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.ProcStatusEnumAvro;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class ProcStatusEnumAvroMapperTest {

    @Test
    public void testdo2Avro() {
        ProcStatusEnumAvro expected = ProcStatusEnumAvro.DIFFUSION_VALIDATION;
        ProcStatusEnum source = ProcStatusEnum.DIFFUSION_VALIDATION;

        ProcStatusEnumAvro actual = ProcStatusEnumAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() {
        ProcStatusEnum expected = ProcStatusEnum.DIFFUSION_VALIDATION;
        ProcStatusEnumAvro source = ProcStatusEnumAvro.DIFFUSION_VALIDATION;

        ProcStatusEnum actual = ProcStatusEnumAvroMapper.avro2Do(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAllDoValuesAreDefinedInAvro() {
        ProcStatusEnum[] expected = ProcStatusEnum.values();
        for (ProcStatusEnum currentExpected : expected) {
            ProcStatusEnumAvro actual = ProcStatusEnumAvro.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

    @Test
    public void testAllAvroValuesAreDefinedInDo() {
        ProcStatusEnumAvro[] expected = ProcStatusEnumAvro.values();
        for (ProcStatusEnumAvro currentExpected : expected) {
            ProcStatusEnum actual = ProcStatusEnum.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

}
