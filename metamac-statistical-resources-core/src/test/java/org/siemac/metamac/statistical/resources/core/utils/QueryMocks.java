//package org.siemac.metamac.statistical.resources.core.utils;
//
//import org.siemac.metamac.statistical.resources.core.query.domain.Query;
//
//public class QueryMocks {
//
//    public Query query01 = createQuery01();
//    public Query query02 = createQuery02();
//    public Query query03 = createQuery03();
//    
//    
//    private static final String           QUERY_1_CODE = "query01";
//    private static final String           QUERY_1_URN  = "urn:mock:query01";
//    private static final String           QUERY_2_CODE = "query02";
//    private static final String           QUERY_2_URN  = "urn:mock:query02";
//    private static final String           QUERY_3_CODE = "query03";
//    private static final String           QUERY_3_URN  = "urn:mock:query03";
//   
//    
//    private Query createQuery01() {
//        if (query01 == null) {
//            query01 = StatisticalResourcesDoMocks.mockQuery();
//            query01.getNameableStatisticalResource().setCode(QUERY_1_CODE);
//            query01.getNameableStatisticalResource().setUrn(QUERY_1_URN);
//        }
//        return query01;
//    }
//    
//    private Query createQuery02() {
//        query02 = StatisticalResourcesDoMocks.mockQuery();
//        query02.getNameableStatisticalResource().setCode(QUERY_2_CODE);
//        query02.getNameableStatisticalResource().setUrn(QUERY_2_URN);
//        return query02;
//    }
//    
//    private Query createQuery03() {
//        query03 = StatisticalResourcesDoMocks.mockQuery();
//        query03.getNameableStatisticalResource().setCode(QUERY_3_CODE);
//        query03.getNameableStatisticalResource().setUrn(QUERY_3_URN);
//        return query03;
//    }   
//}
