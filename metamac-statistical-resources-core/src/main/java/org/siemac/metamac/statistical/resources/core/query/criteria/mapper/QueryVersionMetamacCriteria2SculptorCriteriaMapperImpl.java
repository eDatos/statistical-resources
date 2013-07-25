package org.siemac.metamac.statistical.resources.core.query.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.springframework.stereotype.Component;

@Component
public class QueryVersionMetamacCriteria2SculptorCriteriaMapperImpl implements QueryVersionMetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<QueryVersion> queryCriteriaMapper = null;

    /**************************************************************************
     * Constructor
     **************************************************************************/

    public QueryVersionMetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        queryCriteriaMapper = new MetamacCriteria2SculptorCriteria<QueryVersion>(QueryVersion.class, StatisticalResourcesCriteriaOrderEnum.class, StatisticalResourcesCriteriaPropertyEnum.class, new QueryCriteriaCallback());
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
            StatisticalResourcesCriteriaPropertyEnum propertyEnum = StatisticalResourcesCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                case CODE:
                    return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().code(), propertyRestriction.getStringValue());
                case URN:
                    return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().urn(), propertyRestriction.getStringValue());
                case TITLE:
                    return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().title().texts().label(), propertyRestriction.getStringValue());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().description().texts().label(), propertyRestriction.getStringValue());
                case QUERY_STATUS:
                    return new SculptorPropertyCriteria(QueryVersionProperties.status(), propertyRestriction.getEnumValue());
                case PROC_STATUS:
                    return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().procStatus(), propertyRestriction.getEnumValue());
                case STATISTICAL_OPERATION_URN:
                    return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().statisticalOperation().urn(), propertyRestriction.getStringValue());
                case LAST_VERSION:
                    return new SculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().lastVersion(), propertyRestriction.getBooleanValue());
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property<QueryVersion> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            StatisticalResourcesCriteriaOrderEnum propertyOrderEnum = StatisticalResourcesCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyOrderEnum) {
                case CODE:
                    return QueryVersionProperties.lifeCycleStatisticalResource().code();
                case URN:
                    return QueryVersionProperties.lifeCycleStatisticalResource().urn();
                case TITLE:
                    return QueryVersionProperties.lifeCycleStatisticalResource().title().texts().label();
                case LAST_VERSION:
                    return QueryVersionProperties.lifeCycleStatisticalResource().lastVersion();
                case PROC_STATUS:
                    return QueryVersionProperties.lifeCycleStatisticalResource().procStatus();
                case QUERY_STATUS:
                    QueryVersionProperties.status();
                case STATISTICAL_OPERATION_URN:
                    QueryVersionProperties.lifeCycleStatisticalResource().statisticalOperation().urn();
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @Override
        public Property<QueryVersion> retrievePropertyOrderDefault() throws MetamacException {
            return QueryVersionProperties.id();
        }
    }
}
