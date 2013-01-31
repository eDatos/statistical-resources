package org.siemac.metamac.statistical.resources.core.mocks;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.utils.mocks.StatisticalResourcesPersistedDoMocks;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryMockFactory extends MockFactory<Query> implements InitializingBean {

    @Autowired
    private StatisticalResourcesPersistedDoMocks statisticalResourcesPersistedDoMocks;

    public static final String                   QUERY_01_BASIC_NAME            = "QUERY_01_BASIC";
    public Query                                 QUERY_01_BASIC;

    public static final String                   QUERY_02_BASIC_ORDERED_01_NAME = "QUERY_02_BASIC_ORDERED_01";
    public Query                                 QUERY_02_BASIC_ORDERED_01;

    public static final String                   QUERY_03_BASIC_ORDERED_02_NAME = "QUERY_03_BASIC_ORDERED_02";
    public Query                                 QUERY_03_BASIC_ORDERED_02;

    public static final String                   QUERY_04_BASIC_ORDERED_03_NAME = "QUERY_04_BASIC_ORDERED_03";
    public Query                                 QUERY_04_BASIC_ORDERED_03;

    private static Map<String, Query>            mocks;

    @Override
    public void afterPropertiesSet() throws Exception {
        QUERY_01_BASIC = createQuery();
        QUERY_02_BASIC_ORDERED_01 = createPersistedQueryWithCode("a");
        QUERY_03_BASIC_ORDERED_02 = createPersistedQueryWithCode("b");
        QUERY_04_BASIC_ORDERED_03 = createPersistedQueryWithCode("c");

        mocks = new HashMap<String, Query>();
        registerMocks(this, Query.class, mocks);
    }

    @Override
    public Query getMock(String id) {
        return mocks.get(id);
    }

    private Query createPersistedQueryWithCode(String code) {
        Query query = createQuery();
        query.getLifeCycleStatisticalResource().setCode(code);
        return query;
    }

    private Query createQuery() {
        return statisticalResourcesPersistedDoMocks.mockQuery();
    }

}
