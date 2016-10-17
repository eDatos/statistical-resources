package org.siemac.metamac.statistical.resources.core.stream.messages;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.stream.messages.mappers.NameableStatisticalResourceAvro2Do;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;

public class NameableStatisticalResourceAvro2DoTest {

    @Test
    public void testNameableStatisticalResourceAvro2Do() {
        NameableStatisticalResource expected = MappersMockUtils.mockNameableStatisticalResource();

        NameableStatisticalResourceAvro source = MappersMockUtils.mockNameableStatisticalResourceAvro();

        NameableStatisticalResource actual = NameableStatisticalResourceAvro2Do.nameableStatisticalResourceAvro2Do(source);

        assertThat(expected.getCode(), is(equalTo(actual.getCode())));
        assertThat(expected.getUrn(), is(equalTo(actual.getUrn())));
        CommonAsserts.assertEqualsInternationalString(expected.getTitle(), actual.getTitle());
        CommonAsserts.assertEqualsInternationalString(expected.getDescription(), actual.getDescription());
    }

}
