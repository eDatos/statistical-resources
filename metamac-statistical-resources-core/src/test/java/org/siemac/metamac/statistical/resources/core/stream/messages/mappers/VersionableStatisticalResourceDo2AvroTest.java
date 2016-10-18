package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.MappersMockUtils;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionableStatisticalResourceAvro;

public class VersionableStatisticalResourceDo2AvroTest {

    @Test
    public void testVersionableStatisticalResourceDo2Avro() {
        VersionableStatisticalResourceAvro expected = MappersMockUtils.mockVersionableStatisticalResourceAvro();
        VersionableStatisticalResource source = MappersMockUtils.mockVersionableStatisticalResource();

        VersionableStatisticalResourceAvro actual = VersionableStatisticalResourceDo2Avro.versionableStatisticalResourceDo2Avro(source);

        assertThat(expected, is(equalTo(actual)));
    }

}
