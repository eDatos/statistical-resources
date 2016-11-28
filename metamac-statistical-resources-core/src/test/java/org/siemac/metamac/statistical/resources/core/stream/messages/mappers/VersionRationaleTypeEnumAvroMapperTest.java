package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionRationaleTypeEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.VersionRationaleTypeEnumAvro2DoMapper;

public class VersionRationaleTypeEnumAvroMapperTest {

    @Test
    public void testdo2Avro() {
        VersionRationaleTypeEnumAvro expected = VersionRationaleTypeEnumAvro.MAJOR_CATEGORIES;
        VersionRationaleTypeEnum source = VersionRationaleTypeEnum.MAJOR_CATEGORIES;

        VersionRationaleTypeEnumAvro actual = VersionRationaleTypeEnumDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAvro2Do() {
        VersionRationaleTypeEnum expected = VersionRationaleTypeEnum.MAJOR_CATEGORIES;
        VersionRationaleTypeEnumAvro source = VersionRationaleTypeEnumAvro.MAJOR_CATEGORIES;

        VersionRationaleTypeEnum actual = VersionRationaleTypeEnumAvro2DoMapper.avro2Do(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testAllDoValuesAreDefinedInAvro() {
        VersionRationaleTypeEnum[] expected = VersionRationaleTypeEnum.values();
        for (VersionRationaleTypeEnum currentExpected : expected) {
            VersionRationaleTypeEnumAvro actual = VersionRationaleTypeEnumAvro.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

    @Test
    public void testAllAvroValuesAreDefinedInDo() {
        VersionRationaleTypeEnumAvro[] expected = VersionRationaleTypeEnumAvro.values();
        for (VersionRationaleTypeEnumAvro currentExpected : expected) {
            VersionRationaleTypeEnum actual = VersionRationaleTypeEnum.valueOf(currentExpected.name());
            assertThat(actual.name(), is(equalTo(currentExpected.name())));
        }
    }

}
