package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.MappersMockUtils;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;

public class VersionableStatisticalResourceAvro2DoTest {

    @Test
    public void testVersionableStatisticalResourceAvro2Do() {
        VersionableStatisticalResource expected = MappersMockUtils.mockVersionableStatisticalResource();
        VersionableStatisticalResourceAvro source = MappersMockUtils.mockVersionableStatisticalResourceAvro();

        VersionableStatisticalResource actual = VersionableStatisticalResourceAvro2Do.versionableStatisticalResourceAvro2Do(source);

        assertThat(expected.getCode(), is(equalTo(actual.getCode())));
        assertThat(expected.getUrn(), is(equalTo(actual.getUrn())));
        CommonAsserts.assertEqualsInternationalString(expected.getTitle(), actual.getTitle());
        CommonAsserts.assertEqualsInternationalString(expected.getDescription(), actual.getDescription());
        assertThat(expected.getNextVersionDate(), is(equalTo(actual.getNextVersionDate())));
        assertThat(expected.getValidFrom(), is(equalTo(actual.getValidFrom())));
        assertThat(expected.getValidTo(), is(equalTo(actual.getValidTo())));
        CommonAsserts.assertEqualsInternationalString(expected.getVersionRationale(), actual.getVersionRationale());
        assertThat(expected.getNextVersion(), is(equalTo(actual.getNextVersion())));
        assertThat(expected.getVersionLogic(), is(equalTo(actual.getVersionLogic())));
    }

}
