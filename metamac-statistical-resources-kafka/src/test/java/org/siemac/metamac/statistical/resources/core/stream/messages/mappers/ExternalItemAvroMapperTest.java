package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;

public class ExternalItemAvroMapperTest {

    @Test
    public void testExternalItemDo2Avro() {
        ExternalItem source = MappersMockUtils.mockExternalItem();
        ExternalItemAvro expected = MappersMockUtils.mockExternalItemAvro();
        ExternalItemAvro actual = ExternalItemAvroMapper.do2Avro(source);
        assertThat(actual, is(equalTo(expected)));
    }

    @Test
    public void testExternalItemAvro2Do() {
        ExternalItem expected = MappersMockUtils.mockExternalItem();
        ExternalItemAvro source = MappersMockUtils.mockExternalItemAvro();

        ExternalItem actual = ExternalItemAvroMapper.avro2Do(source);

        CommonAsserts.assertEqualsExternalItem(expected, actual);;

    }

}
