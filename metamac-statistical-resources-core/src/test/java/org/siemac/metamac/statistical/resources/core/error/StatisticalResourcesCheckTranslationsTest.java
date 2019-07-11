package org.siemac.metamac.statistical.resources.core.error;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.siemac.metamac.common.test.translations.CheckTranslationsTestBase;
import org.siemac.metamac.core.common.lang.LocaleUtil;
import org.siemac.metamac.core.common.util.MetamacReflectionUtils;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeAction;
import org.siemac.metamac.statistical.resources.core.notices.ServiceNoticeMessage;

public class StatisticalResourcesCheckTranslationsTest extends CheckTranslationsTestBase {

    // ----------------------------------------------------------------------------------------
    // MORE TESTS
    // ----------------------------------------------------------------------------------------

    @Test
    public void testCheckExistsAllTranslationsForServiceNoticeActions() throws Exception {
        checkExistsAllTranslationsForStrings(getServiceNoticeActionClasses(), getLocalesToTranslate());
    }

    @SuppressWarnings("rawtypes")
    public Class[] getServiceNoticeActionClasses() {
        return new Class[]{ServiceNoticeAction.class};
    }

    @Test
    public void testCheckExistsAllTranslationsForServiceNoticeMessages() throws Exception {
        checkExistsAllTranslationsForStrings(getServiceNoticeMessagesClasses(), getLocalesToTranslate());
    }

    @SuppressWarnings("rawtypes")
    public Class[] getServiceNoticeMessagesClasses() {
        return new Class[]{ServiceNoticeMessage.class};
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testGenerateAutomaticParameterTranslationsThatAreMissing() throws Exception {
        HashSet<String> missingTranslations = new HashSet<String>();

        for (Class serviceExceptionTypeClass : getServiceExceptionParameterClasses()) {
            Set<String> codes = MetamacReflectionUtils.getFieldsValueWithType(serviceExceptionTypeClass, String.class);
            for (String code : codes) {
                for (Locale locale : getLocalesToTranslate()) {
                    try {
                        String translation = LocaleUtil.getMessageForCode(code, locale);

                        if (StringUtils.isBlank(translation) || StringUtils.equals(code, translation)) {
                            missingTranslations.add(createMissingTranslation(code));
                        }
                    } catch (Exception e) {
                        missingTranslations.add(createMissingTranslation(code));
                    }
                }
            }
        }
        printInConsoleMissingTranslations(missingTranslations);
    }

    private void printInConsoleMissingTranslations(HashSet<String> missingTranslations) {
        if (!missingTranslations.isEmpty()) {
            System.out.println("------------------------------- Missing translations -------------------------------");
            for (String missingTranslation : missingTranslations) {
                System.out.println(missingTranslation);
            }
            System.out.println("------------------------------------------------------------------------------------");
        }
    }

    private String createMissingTranslation(String parameter) {
        return parameter + " = " + parameter.replace("parameter.resources.", "");
    }

    // ----------------------------------------------------------------------------------------
    // OVERRIDE METHODS
    // ----------------------------------------------------------------------------------------

    @Override
    @SuppressWarnings("rawtypes")
    public Class[] getServiceExceptionTypeClasses() {
        return new Class[]{ServiceExceptionType.class};
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Class[] getServiceExceptionParameterClasses() {
        return new Class[]{ServiceExceptionParameters.class};
    }

    @Override
    public Locale[] getLocalesToTranslate() {
        return LOCALES_TO_TRANSLATE;
    }

}