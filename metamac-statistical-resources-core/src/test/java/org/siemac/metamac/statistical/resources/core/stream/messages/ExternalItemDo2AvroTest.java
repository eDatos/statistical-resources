package org.siemac.metamac.statistical.resources.core.stream.messages;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.stream.messages.mappers.ExternalItemDo2Avro;
import org.siemac.metamac.statistical.resources.core.stream.messages.mappers.InternationalStringDo2Avro;

public class ExternalItemDo2AvroTest {


    @Test
    public void testExternalItemDo2Avro() {
       ExternalItem source = MappersMockUtils.mockExternalItem();
       ExternalItemAvro expected = MappersMockUtils.mockExternalItemAvro();
       ExternalItemAvro actual = ExternalItemDo2Avro.externalItemDo2Avro(source);
        assertThat(actual, is(equalTo(expected)));
    }

}
