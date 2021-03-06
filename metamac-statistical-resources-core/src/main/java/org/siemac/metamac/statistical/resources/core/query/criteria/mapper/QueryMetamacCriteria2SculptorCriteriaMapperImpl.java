package org.siemac.metamac.statistical.resources.core.query.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.criteria.shared.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
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
        queryCriteriaMapper = new MetamacCriteria2SculptorCriteria<QueryVersion>(QueryVersion.class, StatisticalResourcesCriteriaOrderEnum.class, StatisticalResourcesCriteriaPropertyEnum.class,
                new QueryCriteriaCallback());
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
        public SculptorPropertyCriteriaBase retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            StatisticalResourcesCriteriaPropertyEnum propertyEnum = StatisticalResourcesCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                // From Query
                case CODE:
                    return new SculptorPropertyCriteria(QueryVersionProperties.query().identifiableStatisticalResource().code(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case URN:
                    return new SculptorPropertyCriteria(QueryVersionProperties.query().identifiableStatisticalResource().urn(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case STATISTICAL_OPERATION_URN:
                    return new SculptorPropertyCriteria(QueryVersionProperties.query().identifiableStatisticalResource().statisticalOperation().urn(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                // From Query Version
                case TITLE:
                    return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().title().texts().label(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().description().texts().label(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case PROC_STATUS:
                    return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().procStatus(), propertyRestriction.getEnumValue(), propertyRestriction.getOperationType());
                case QUERY_STATUS:
                    return new SculptorPropertyCriteria(QueryVersionProperties.status(), propertyRestriction.getEnumValue(), propertyRestriction.getOperationType());
                default:
                    // LAST_VERSION
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property<QueryVersion> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            StatisticalResourcesCriteriaOrderEnum propertyOrderEnum = StatisticalResourcesCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyOrderEnum) {
                // From Query
                case CODE:
                    return QueryVersionProperties.query().identifiableStatisticalResource().code();
                case URN:
                    return QueryVersionProperties.query().identifiableStatisticalResource().urn();
                case STATISTICAL_OPERATION_URN:
                    return QueryVersionProperties.lifeCycleStatisticalResource().statisticalOperation().urn();

                // From Query Version
                case TITLE:
                    return QueryVersionProperties.lifeCycleStatisticalResource().title().texts().label();
                case PROC_STATUS:
                    return QueryVersionProperties.lifeCycleStatisticalResource().procStatus();
                case QUERY_STATUS:
                    return QueryVersionProperties.status();

                default:
                    // LAST_UPDATED
                    // LAST_VERSION
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @Override
        public Property<QueryVersion> retrievePropertyOrderDefault() throws MetamacException {
            return QueryVersionProperties.query().id();
        }
    }
}
