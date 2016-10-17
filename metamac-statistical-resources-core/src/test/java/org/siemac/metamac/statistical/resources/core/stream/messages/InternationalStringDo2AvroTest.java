package org.siemac.metamac.statistical.resources.core.stream.messages;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.stream.messages.mappers.InternationalStringDo2Avro;

public class InternationalStringDo2AvroTest {

    protected static final String EXPECTED_LABEL  = "My String label";
    protected static final String EXPECTED_LOCALE = "ES";

    @Test
    public void testAnInternationalStringIsRepresentedInAvro() {

        InternationalStringItemAvro itemMock = InternationalStringItemAvro.newBuilder().setLabel(EXPECTED_LABEL).setLocale(EXPECTED_LOCALE).build();
        InternationalStringItemAvro item2Mock = InternationalStringItemAvro.newBuilder().setLabel(EXPECTED_LABEL + "2").setLocale(EXPECTED_LOCALE + "2").build();
        List<InternationalStringItemAvro> list = new ArrayList<InternationalStringItemAvro>();
        list.add(itemMock);
        list.add(item2Mock);

        InternationalStringAvro expected = InternationalStringAvro.newBuilder().setLocalisedStrings(list).build();

        InternationalString source = new InternationalString();
        LocalisedString source1 = new LocalisedString();
        source1.setLabel(EXPECTED_LABEL);
        source1.setLocale(EXPECTED_LOCALE);
        LocalisedString source2 = new LocalisedString();
        source2.setLabel(EXPECTED_LABEL + "2");
        source2.setLocale(EXPECTED_LOCALE + "2");
        source.addText(source2);
        source.addText(source1);

        InternationalStringAvro actual = InternationalStringDo2Avro.internationalString2Avro(source);
        assertThat(actual.getLocalisedStrings(), is(equalTo(expected.getLocalisedStrings())));
    }
}
