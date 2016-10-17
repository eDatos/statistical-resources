package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringItemAvro;

public class InternationalStringAvro2Do {

    protected InternationalStringAvro2Do() {
    }

    public static InternationalString internationalStringAvro2Do(InternationalStringAvro source) {
        InternationalString target = new InternationalString();
        for (InternationalStringItemAvro s : source.getLocalisedStrings()) {
            LocalisedString localisedText = new LocalisedString();
            localisedText.setLabel((String) s.getLabel());
            localisedText.setLocale((String) s.getLocale());
            target.addText(localisedText);
        }
        return target;
    }

}
