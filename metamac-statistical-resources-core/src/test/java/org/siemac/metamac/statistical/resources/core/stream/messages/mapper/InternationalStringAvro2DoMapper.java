package org.siemac.metamac.statistical.resources.core.stream.messages.mapper;

import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringItemAvro;

public class InternationalStringAvro2DoMapper {

    protected InternationalStringAvro2DoMapper() {
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

}
