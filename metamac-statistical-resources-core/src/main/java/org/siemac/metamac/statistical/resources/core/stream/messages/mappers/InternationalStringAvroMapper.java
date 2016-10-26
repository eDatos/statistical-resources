package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringItemAvro;

public class InternationalStringAvroMapper {

    protected InternationalStringAvroMapper() {
    }

    public static InternationalString avro2Do(InternationalStringAvro source) {
        InternationalString target = new InternationalString();
        if (source != null) {
            for (InternationalStringItemAvro s : source.getLocalisedStrings()) {
                LocalisedString localisedText = new LocalisedString();
                localisedText.setLabel(s.getLabel());
                localisedText.setLocale(s.getLocale());
                target.addText(localisedText);
            }
        }
        return target;
    }


    public static InternationalStringAvro do2Avro(InternationalString src) {
        InternationalStringAvro target = null;
        if (null != src) {
            List<InternationalStringItemAvro> localisedStrings = new ArrayList<InternationalStringItemAvro>();
            for (LocalisedString s : src.getTexts()) {
                InternationalStringItemAvro isia = InternationalStringItemAvro.newBuilder().setLocale(s.getLocale()).setLabel(s.getLabel()).build();
                localisedStrings.add(isia);
            }
            target = InternationalStringAvro.newBuilder().setLocalisedStrings(localisedStrings).build();
        }
        return target;
    }

}
