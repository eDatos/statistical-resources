package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mapper.IdentifiableStatisticalResourceAvro2DoMapper;

public class IdentifiableStatisticalResourceAvroMapperTest {

    @Test
    public void testAvro2Do() {
        IdentifiableStatisticalResource expected = MappersMockUtils.mockIdentifiableStatisticalResource();
        IdentifiableStatisticalResourceAvro source = MappersMockUtils.mockIdentifiableStatisticalResourceAvro();

        IdentifiableStatisticalResource actual = IdentifiableStatisticalResourceAvro2DoMapper.avro2Do(source);

        assertThat(actual.getCode(), is(equalTo(expected.getCode())));
        assertThat(actual.getUrn(), is(equalTo(expected.getUrn())));
    }

    @Test
    public void testDo2Avro() {
        IdentifiableStatisticalResourceAvro expected = MappersMockUtils.mockIdentifiableStatisticalResourceAvro();
        IdentifiableStatisticalResource source = MappersMockUtils.mockIdentifiableStatisticalResource();

        IdentifiableStatisticalResourceAvro actual = IdentifiableStatisticalResourceDo2AvroMapper.do2Avro(source);

        assertThat(actual.getCode(), is(equalTo(expected.getCode())));
        assertThat(actual.getUrn(), is(equalTo(expected.getUrn())));
    }

}
