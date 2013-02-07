package org.siemac.metamac.statistical.resources.core.query.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.criteria.enums.QueryCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.query.criteria.enums.QueryCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryProperties;
import org.springframework.stereotype.Component;

@Component
public class QueryMetamacCriteria2SculptorCriteriaMapperImpl implements QueryMetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<Query> queryCriteriaMapper = null;

    /**************************************************************************
     * Constructor
     **************************************************************************/

    public QueryMetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        queryCriteriaMapper = new MetamacCriteria2SculptorCriteria<Query>(Query.class, QueryCriteriaOrderEnum.class, QueryCriteriaPropertyEnum.class, new QueryCriteriaCallback());
    }

    /**************************************************************************
     * Mappings
     **************************************************************************/

    @Override
    public MetamacCriteria2SculptorCriteria<Query> getQueryCriteriaMapper() {
        return queryCriteriaMapper;
    }

    /**************************************************************************
     * CallBacks classes
     *************************************************************************/

    private class QueryCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteria retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            QueryCriteriaPropertyEnum propertyEnum = QueryCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                case CODE:
                    return new SculptorPropertyCriteria(QueryProperties.lifeCycleStatisticalResource().code(), propertyRestriction.getStringValue());
                case URN:
                    return new SculptorPropertyCriteria(QueryProperties.lifeCycleStatisticalResource().urn(), propertyRestriction.getStringValue());
                case TITLE:
                    return new SculptorPropertyCriteria(QueryProperties.lifeCycleStatisticalResource().title().texts().label(), propertyRestriction.getStringValue());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(QueryProperties.lifeCycleStatisticalResource().description().texts().label(), propertyRestriction.getStringValue());
                case STATUS:
                    return new SculptorPropertyCriteria(QueryProperties.status(), propertyRestriction.getEnumValue());
                case PROC_STATUS:
                    return new SculptorPropertyCriteria(QueryProperties.lifeCycleStatisticalResource().procStatus(), propertyRestriction.getEnumValue());
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property<Query> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            QueryCriteriaOrderEnum propertyOrderEnum = QueryCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyOrderEnum) {
                case CODE:
                    return QueryProperties.lifeCycleStatisticalResource().code();
                case URN:
                    return QueryProperties.lifeCycleStatisticalResource().urn();
                case TITLE:
                    return QueryProperties.lifeCycleStatisticalResource().title().texts().label();
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @Override
        public Property<Query> retrievePropertyOrderDefault() throws MetamacException {
            return QueryProperties.id();
        }
    }
}
