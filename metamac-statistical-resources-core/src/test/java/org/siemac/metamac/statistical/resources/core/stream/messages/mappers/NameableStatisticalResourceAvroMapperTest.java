package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;

public class NameableStatisticalResourceAvroMapperTest {

    @Test
    public void testNameableStatisticalResourceDo2Avro() {
        NameableStatisticalResourceAvro expected = MappersMockUtils.mockNameableStatisticalResourceAvro();
        NameableStatisticalResource source = MappersMockUtils.mockNameableStatisticalResource();

        NameableStatisticalResourceAvro actual = NameableStatisticalResourceDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

}
