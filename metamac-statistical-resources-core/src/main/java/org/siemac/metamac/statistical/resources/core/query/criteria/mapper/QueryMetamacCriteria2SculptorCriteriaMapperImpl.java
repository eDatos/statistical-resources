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
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.springframework.stereotype.Component;

@Component
public class QueryMetamacCriteria2SculptorCriteriaMapperImpl implements QueryMetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<QueryVersion> queryCriteriaMapper = null;

    /**************************************************************************
     * Constructor
     **************************************************************************/

    public QueryMetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        queryCriteriaMapper = new MetamacCriteria2SculptorCriteria<QueryVersion>(QueryVersion.class, QueryCriteriaOrderEnum.class, QueryCriteriaPropertyEnum.class, new QueryCriteriaCallback());
    }

    /**************************************************************************
     * Mappings
     **************************************************************************/

    @Override
    public MetamacCriteria2SculptorCriteria<QueryVersion> getQueryCriteriaMapper() {
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
                    return new SculptorPropertyCriteria(QueryVersionProperties.query().identifiableStatisticalResource().code(), propertyRestriction.getStringValue());
                case URN:
                    return new SculptorPropertyCriteria(QueryVersionProperties.query().identifiableStatisticalResource().urn(), propertyRestriction.getStringValue());
                case QUERY_VERSION_TITLE:
                    return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().title().texts().label(), propertyRestriction.getStringValue());
                    // case STATISTICAL_OPERATION_URN:
                    // TODO: Pendiente de añadir la operación estadística a las queries
                    // return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().statisticalOperation().urn(), propertyRestriction.getStringValue());
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property<QueryVersion> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            QueryCriteriaOrderEnum propertyOrderEnum = QueryCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyOrderEnum) {
                case CODE:
                    return QueryVersionProperties.query().identifiableStatisticalResource().code();
                case URN:
                    return QueryVersionProperties.query().identifiableStatisticalResource().urn();
                case QUERY_VERSION_TITLE:
                    return QueryVersionProperties.lifeCycleStatisticalResource().title().texts().label();
                    // case STATISTICAL_OPERATION_URN:
                    // TODO: Pendiente de añadir la operación estadística a las queries
                    // return QueryVersionProperties.lifeCycleStatisticalResource().statisticalOperation().urn();
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @Override
        public Property<QueryVersion> retrievePropertyOrderDefault() throws MetamacException {
            return QueryVersionProperties.query().id();
        }
    }
}
