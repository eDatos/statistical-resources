package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringItemAvro;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;

public class InternationalStringAvroMapperTest {

    protected static final String EXPECTED_LABEL  = "My String label";
    protected static final String EXPECTED_LOCALE = "ES";

    @Test
    public void testAnInternationalStringIsRepresentedInAvro() {

        List<InternationalStringItemAvro> list = new ArrayList<InternationalStringItemAvro>();
        InternationalString source = new InternationalString();
        for (int i = 0; i < 5; i++) {
            InternationalStringItemAvro itemMock = InternationalStringItemAvro.newBuilder().setLabel(EXPECTED_LABEL).setLocale(EXPECTED_LOCALE).build();
            list.add(itemMock);

            LocalisedString source1 = new LocalisedString();
            source1.setLabel(EXPECTED_LABEL);
            source1.setLocale(EXPECTED_LOCALE);
            source.addText(source1);
        }
        InternationalStringAvro expected = InternationalStringAvro.newBuilder().setLocalisedStrings(list).build();

        InternationalStringAvro actual = InternationalStringAvroMapper.do2Avro(source);


        assertEquals(expected.getLocalisedStrings().size(), actual.getLocalisedStrings().size());

        for (InternationalStringItemAvro itemExpected : expected.getLocalisedStrings()) {
            InternationalStringItemAvro equivalentLocale = actual.getLocalisedStrings().get(0);
            assertEquals(itemExpected.getLabel(), equivalentLocale.getLabel());
        }
    }


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

        InternationalString actual = InternationalStringAvroMapper.avro2Do(source);

        BaseAsserts.assertEqualsInternationalString(actual, expected);

    }
}
