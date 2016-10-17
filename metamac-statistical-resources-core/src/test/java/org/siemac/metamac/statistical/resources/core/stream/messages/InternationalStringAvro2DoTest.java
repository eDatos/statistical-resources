package org.siemac.metamac.statistical.resources.core.stream.messages;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.stream.messages.mappers.InternationalStringAvro2Do;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;

public class InternationalStringAvro2DoTest {

    protected static final String EXPECTED_LABEL  = "My String label";
    protected static final String EXPECTED_LOCALE = "ES";

    @Test
    public void testIntStringAvro2Do() {

        InternationalString expected = new InternationalString();
        LocalisedString expected1 = new LocalisedString();
        expected1.setLabel(EXPECTED_LABEL);
        expected1.setLocale(EXPECTED_LOCALE);
        expected.addText(expected1);

        InternationalStringItemAvro itemMock = InternationalStringItemAvro.newBuilder().setLabel(EXPECTED_LABEL).setLocale(EXPECTED_LOCALE).build();
        List<InternationalStringItemAvro> list = new ArrayList<InternationalStringItemAvro>();
        list.add(itemMock);

        InternationalStringAvro source = InternationalStringAvro.newBuilder().setLocalisedStrings(list).build();

        InternationalString actual = InternationalStringAvro2Do.internationalStringAvro2Do(source);

        BaseAsserts.assertEqualsInternationalString(actual, expected);

    }
}
