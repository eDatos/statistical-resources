package org.siemac.metamac.statistical.resources.core.stream.messages;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.mappers.TypeExternalArtefactsEnumMapper;

public class TypeExternalArtefactsEnumMapperTest {

    @Test
    public void testCorrectMatchingDo2Avro() {
        for (TypeExternalArtefactsEnum expected : TypeExternalArtefactsEnum.values()) {
            TypeExternalArtefactsEnumAvro actual = TypeExternalArtefactsEnumMapper.do2Avro(expected);
            assertThat(actual.name(), is(equalTo(expected.name())));
        }
    }

    @Test
    public void testCorrectMatchingAvro2Do() {
        for (TypeExternalArtefactsEnumAvro expected : TypeExternalArtefactsEnumAvro.values()) {
            TypeExternalArtefactsEnum actual = TypeExternalArtefactsEnumMapper.avro2Do(expected);
            assertThat(actual.name(), is(equalTo(expected.name())));
        }
    }

}
