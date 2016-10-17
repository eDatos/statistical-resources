package org.siemac.metamac.statistical.resources.core.stream.messages;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.stream.messages.mappers.ExternalItemAvro2Do;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;

public class ExternalItemAvro2DoTest {

    @Test
    public void testExternalItemAvro2Do() {
        ExternalItem expected = MappersMockUtils.mockExternalItem();
        ExternalItemAvro source = MappersMockUtils.mockExternalItemAvro();

        ExternalItem actual = ExternalItemAvro2Do.externalItemAvro2Do(source);

        CommonAsserts.assertEqualsExternalItem(expected, actual);;

    }

}
