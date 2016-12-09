package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.ProcStatusEnumAvro;

public class ProcStatusEnumAvroMapperTest {

    @Test
    public void testdo2Avro() {
        ProcStatusEnumAvro expected = ProcStatusEnumAvro.DIFFUSION_VALIDATION;
        ProcStatusEnum source = ProcStatusEnum.DIFFUSION_VALIDATION;

        ProcStatusEnumAvro actual = ProcStatusEnumDo2AvroMapper.do2Avro(source);

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
