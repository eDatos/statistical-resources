package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.common.domain.Translation;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockDescriptor;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockProvider;

@MockProvider
public class TranslationMockFactory extends StatisticalResourcesMockFactory<Translation> {

    public static final String            TRANSLATIONS = "TRANSLATIONS";

    private static TranslationMockFactory instance;

    public static TranslationMockFactory getInstance() {
        if (instance == null) {
            instance = new TranslationMockFactory();
        }
        return instance;
    }

    public static MockDescriptor getTranslations() {
        List<Translation> translations = new ArrayList<Translation>();
        addTranslationMock(translations, StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_YEAR, "{yyyy}", "{yyyy}");
        addTranslationMock(translations, StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_MONTH, "{MM}/{yyyy}", "{MM}/{yyyy}");
        addTranslationMock(translations, StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_DATE, "{dd}/{MM}/{yyyy}", "{dd}/{MM}/{yyyy}");
        addTranslationMock(translations, StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_DATETIME, "{dd}/{MM}/{yyyy} {hh}:{mm}:{ss}", "{dd}/{MM}/{yyyy} {hh}:{mm}:{ss}");
        addTranslationMock(translations, StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_SEMESTER_PREFIX + ".S1", "{yyyy} Primer semestre", "{yyyy} First semester");
        addTranslationMock(translations, StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_SEMESTER_PREFIX + ".S2", "{yyyy} Segundo semestre", "{yyyy} Second semester");
        addTranslationMock(translations, StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_TRIMESTER_PREFIX + ".T3", "{yyyy} Tercer trimestre", "{yyyy} Third trimester");
        addTranslationMock(translations, StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_QUARTER_PREFIX + ".Q4", "{yyyy} Cuarto cuatrimestre", "{yyyy} Fourth quarter");
        addTranslationMock(translations, StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_WEEK, "{yyyy} Semana {ww}", "{yyyy} Week {ww}");
        addTranslationMock(translations, StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_DATE_RANGE, "{dd}/{MM}/{yyyy} a {dd_END}/{MM_END}/{yyyy_END}",
                "{dd}/{MM}/{yyyy} to {dd_END}/{MM_END}/{yyyy_END}");
        addTranslationMock(translations, StatisticalResourcesConstants.TRANSLATION_TIME_SDMX_DATETIME_RANGE,
                "{dd}/{MM}/{yyyy} {hh}:{mm}:{ss} a {dd_END}/{MM_END}/{yyyy_END} {hh_END}:{mm_END}:{ss_END}",
                "{dd}/{MM}/{yyyy} {hh}:{mm}:{ss} to {dd_END}/{MM_END}/{yyyy_END} {hh_END}:{mm_END}:{ss_END}");
        return new MockDescriptor(translations.get(0), translations.subList(1, translations.size()).toArray());
    }

    private static void addTranslationMock(List<Translation> translations, String code, String labelEs, String labelEn) {
        Translation translation = createTranslation(code, labelEs, labelEn);
        registerTranslationMock(code, translation);
        translations.add(translation);
    }

    private static Translation createTranslation(String code, String labelEs, String labelEn) {
        Translation translation = new Translation();
        translation.setCode(code);
        translation.setTitle(new InternationalString());
        if (labelEs != null) {
            translation.getTitle().addText(new LocalisedString("es", labelEs));
        }
        if (labelEn != null) {
            translation.getTitle().addText(new LocalisedString("en", labelEn));
        }
        return translation;
    }
}