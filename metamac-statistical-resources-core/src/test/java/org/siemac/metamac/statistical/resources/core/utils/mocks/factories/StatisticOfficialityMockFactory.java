package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.springframework.stereotype.Component;

@Component
public class StatisticOfficialityMockFactory extends StatisticalResourcesMockFactory<StatisticOfficiality> {

    public static final String STATISTIC_OFFICIALITY_01_BASIC_NAME                               = "STATISTIC_OFFICIALITY_01_BASIC";
    private static StatisticOfficiality STATISTIC_OFFICIALITY_01_BASIC;
    
    public static final String STATISTIC_OFFICIALITY_02_BASIC_NAME                               = "STATISTIC_OFFICIALITY_02_BASIC";
    private static StatisticOfficiality STATISTIC_OFFICIALITY_02_BASIC;


    protected static StatisticOfficiality getStatisticOfficiality01Basic() {
        if (STATISTIC_OFFICIALITY_01_BASIC == null) {
            STATISTIC_OFFICIALITY_01_BASIC = createStatisticOfficiality();
        }
        return STATISTIC_OFFICIALITY_01_BASIC;
    }
    
    protected static StatisticOfficiality getStatisticOfficiality02Basic() {
        if (STATISTIC_OFFICIALITY_02_BASIC == null) {
            STATISTIC_OFFICIALITY_02_BASIC = createStatisticOfficiality();
        }
        return STATISTIC_OFFICIALITY_02_BASIC;
    }

    private static StatisticOfficiality createStatisticOfficiality() {
        return getStatisticalResourcesPersistedDoMocks().mockStatisticOfficiality(StatisticalResourcesDoMocks.mockString(10));
    }

}
