package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockProvider;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;

@MockProvider
public class StatisticOfficialityMockFactory extends StatisticalResourcesMockFactory<StatisticOfficiality> {

    public static final String                     STATISTIC_OFFICIALITY_01_BASIC_NAME = "STATISTIC_OFFICIALITY_01_BASIC";

    public static final String                     STATISTIC_OFFICIALITY_02_BASIC_NAME = "STATISTIC_OFFICIALITY_02_BASIC";

    private static StatisticOfficialityMockFactory instance                            = null;

    private StatisticOfficialityMockFactory() {
    }

    public static StatisticOfficialityMockFactory getInstance() {
        if (instance == null) {
            instance = new StatisticOfficialityMockFactory();
        }
        return instance;
    }

    protected static StatisticOfficiality getStatisticOfficiality01Basic() {
        return createStatisticOfficiality();
    }

    protected static StatisticOfficiality getStatisticOfficiality02Basic() {
        return createStatisticOfficiality();
    }

    private static StatisticOfficiality createStatisticOfficiality() {
        return getStatisticalResourcesPersistedDoMocks().mockStatisticOfficiality(StatisticalResourcesDoMocks.mockString(10));
    }

}
