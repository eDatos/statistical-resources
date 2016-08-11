package org.siemac.metamac.statistical.resources.core.error.utils;

import java.io.Serializable;
import java.util.Locale;

import org.siemac.metamac.core.common.exception.utils.TranslateExceptions;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("translateExceptions")
public class StatisticalResourcesTranslateExceptions extends TranslateExceptions {

    private final Logger LOG = LoggerFactory.getLogger(StatisticalResourcesTranslateExceptions.class);

    @Override
    protected String getTranslatedParameter(Serializable param, Locale locale) {
        if (param instanceof InternationalString) {
            InternationalString intString = (InternationalString) param;
            return retrieveLocalisedTextFromInternationalString(locale, intString);
        } else {
            return super.getTranslatedParameter(param, locale);
        }
    }

    protected String retrieveLocalisedTextFromInternationalString(Locale locale, InternationalString intString) {
        if (intString.getLocalisedLabel(locale.getLanguage()) != null) {
            return intString.getLocalisedLabel(locale.getLanguage());
        } else {
            String localisedText = null;
            try {
                String localeDefault = configurationService.retrieveLanguageDefault();
                localisedText = intString.getLocalisedLabel(localeDefault);
            } catch (Exception e) {
                LOG.warn("Default edition language could not be retrieved");
            }
            if (localisedText != null) {
                return localisedText;
            }
            return String.valueOf(intString);
        }
    }

}
