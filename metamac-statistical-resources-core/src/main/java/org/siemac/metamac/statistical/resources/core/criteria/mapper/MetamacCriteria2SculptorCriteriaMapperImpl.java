package org.siemac.metamac.statistical.resources.core.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.criteria.enums.QueryCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.criteria.enums.QueryCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryProperties;
import org.springframework.stereotype.Component;

@Component
public class MetamacCriteria2SculptorCriteriaMapperImpl implements MetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<Query> queryCriteriaMapper = null;

    /**************************************************************************
     * Constructor
     **************************************************************************/

    public MetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
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
                    return new SculptorPropertyCriteria(QueryProperties.nameableStatisticalResource().code(), propertyRestriction.getStringValue());
                case URN:
                    return new SculptorPropertyCriteria(QueryProperties.nameableStatisticalResource().urn(), propertyRestriction.getStringValue());
                case TITLE:
                    return new SculptorPropertyCriteria(QueryProperties.nameableStatisticalResource().title().texts().label(), propertyRestriction.getStringValue());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(QueryProperties.nameableStatisticalResource().description().texts().label(), propertyRestriction.getStringValue());
                case OPERATION_CODE:
                    return new SculptorPropertyCriteria(QueryProperties.nameableStatisticalResource().operation().code(), propertyRestriction.getStringValue());
                case OPERATION_ID:
                    return new SculptorPropertyCriteria(QueryProperties.nameableStatisticalResource().operation().id(), propertyRestriction.getLongValue());
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property<Query> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            QueryCriteriaOrderEnum propertyOrderEnum = QueryCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyOrderEnum) {
                case CODE:
                    return QueryProperties.nameableStatisticalResource().code();
                case URN:
                    return QueryProperties.nameableStatisticalResource().urn();
                case TITLE:
                    return QueryProperties.nameableStatisticalResource().title().texts().label();
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
