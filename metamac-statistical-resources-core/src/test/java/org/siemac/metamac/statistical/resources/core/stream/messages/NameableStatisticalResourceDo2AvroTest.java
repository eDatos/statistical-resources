package org.siemac.metamac.statistical.resources.core.stream.messages;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.mappers.NameableStatisticalResourceDo2Avro;

public class NameableStatisticalResourceDo2AvroTest {

    @Test
    public void testNameableStatisticalResourceDo2Avro() {
        NameableStatisticalResourceAvro expected = MappersMockUtils.mockNameableStatisticalResourceAvro();
        NameableStatisticalResource source = MappersMockUtils.mockNameableStatisticalResource();

        NameableStatisticalResourceAvro actual = NameableStatisticalResourceDo2Avro.nameableStatisticalResourceDo2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

}
