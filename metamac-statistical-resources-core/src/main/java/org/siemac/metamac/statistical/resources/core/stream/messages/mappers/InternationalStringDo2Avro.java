package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringItemAvro;

public class InternationalStringDo2Avro {

    protected InternationalStringDo2Avro() {
        // TODO Auto-generated constructor stub
    }

    public static InternationalStringAvro internationalString2Avro(InternationalString src) {
        List<InternationalStringItemAvro> localisedStrings = new ArrayList<InternationalStringItemAvro>();
        for (LocalisedString s : src.getTexts()) {
            InternationalStringItemAvro isia = InternationalStringItemAvro
                    .newBuilder()
                    .setLocale(s.getLocale())
                    .setLabel(s.getLabel())
                    .build();
            localisedStrings.add(isia);
        }
        InternationalStringAvro target = InternationalStringAvro.newBuilder().setLocalisedStrings(localisedStrings).build();
        return target;

    }

}
