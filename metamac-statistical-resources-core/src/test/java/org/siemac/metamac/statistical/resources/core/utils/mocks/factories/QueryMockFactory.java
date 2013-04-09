package org.siemac.metamac.statistical.resources.core.utils.mocks.factories;

import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.springframework.stereotype.Component;

@Component
public class QueryMockFactory extends StatisticalResourcesMockFactory<Query> {

    public static final String  QUERY_01_SIMPLE_NAME                                 = "QUERY_01_SIMPLE";
    private static Query QUERY_01_SIMPLE;

  

    protected static Query getQuery01Simple() {
        if (QUERY_01_SIMPLE == null) {
            QUERY_01_SIMPLE = createQuery();
        }
        return QUERY_01_SIMPLE;
    }

  
    private static Query createQuery() {
        return getStatisticalResourcesPersistedDoMocks().mockQueryWithoutGeneratedQueryVersions();
    }
}