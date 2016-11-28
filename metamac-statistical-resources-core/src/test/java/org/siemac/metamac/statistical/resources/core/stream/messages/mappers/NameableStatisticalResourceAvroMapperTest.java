package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.NameableStatisticalResourceAvro2DoMapper;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;

public class NameableStatisticalResourceAvroMapperTest {

    @Test
    public void testNameableStatisticalResourceDo2Avro() {
        NameableStatisticalResourceAvro expected = MappersMockUtils.mockNameableStatisticalResourceAvro();
        NameableStatisticalResource source = MappersMockUtils.mockNameableStatisticalResource();

        NameableStatisticalResourceAvro actual = NameableStatisticalResourceDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testNameableStatisticalResourceAvro2Do() {
        NameableStatisticalResource expected = MappersMockUtils.mockNameableStatisticalResource();

        NameableStatisticalResourceAvro source = MappersMockUtils.mockNameableStatisticalResourceAvro();

        NameableStatisticalResource actual = NameableStatisticalResourceAvro2DoMapper.avro2Do(source);

        assertThat(expected.getCode(), is(equalTo(actual.getCode())));
        assertThat(expected.getUrn(), is(equalTo(actual.getUrn())));
        CommonAsserts.assertEqualsInternationalString(expected.getTitle(), actual.getTitle());
        CommonAsserts.assertEqualsInternationalString(expected.getDescription(), actual.getDescription());
    }
}
