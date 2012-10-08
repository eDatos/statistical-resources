package org.siemac.metamac.core.common.ent.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The International String is a collection of Localised Strings and supports the representation of text in multiple locales.
 */
@Entity
@Table(name = "TB_INTERNATIONAL_STRINGS")
public class InternationalString extends InternationalStringBase {
    private static final long serialVersionUID = 1L;

    public InternationalString() {
    }

    public InternationalString(String[] locales, String[] texts) {
        super();
        for (int i = 0; i < locales.length; i++) {
            this.addText(new LocalisedString(locales[i], texts[i]));
        }
    }
    
    public InternationalString(String locale, String text) {
        new InternationalString(new String[]{locale}, new String[]{text});
    }
    
    public LocalisedString getLocalisedLabelEntity(String locale) {
        if (locale == null) {
            return null;
        }
        for (LocalisedString localstr : getTexts()) {
            if (locale.equalsIgnoreCase(localstr.getLocale())) {
                return localstr;
            }
        }
        return null;
    }

    public String getLocalisedLabel(String locale) {
        LocalisedString localisedString = getLocalisedLabelEntity(locale);
        if (localisedString != null) {
            return localisedString.getLabel();
        }
        return null;
    }
}
