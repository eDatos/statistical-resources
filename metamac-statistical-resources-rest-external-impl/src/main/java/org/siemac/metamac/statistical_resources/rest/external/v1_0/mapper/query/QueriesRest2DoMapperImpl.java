package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.query;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.rest.common.query.domain.MetamacRestOrder;
import org.siemac.metamac.rest.common.query.domain.MetamacRestQueryPropertyRestriction;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.search.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryCriteriaPropertyOrder;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.QueryCriteriaPropertyRestriction;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.BaseRest2DoMapperV10Impl;
import org.springframework.stereotype.Component;

@Component
public class QueriesRest2DoMapperImpl extends BaseRest2DoMapperV10Impl implements QueriesRest2DoMapper {

    private RestCriteria2SculptorCriteria<QueryVersion> datasetCriteriaMapper = null;

    public QueriesRest2DoMapperImpl() {
        datasetCriteriaMapper = new RestCriteria2SculptorCriteria<QueryVersion>(QueryVersion.class, QueryCriteriaPropertyOrder.class, QueryCriteriaPropertyRestriction.class,
                new QueryCriteriaCallback());
    }

    @Override
    public RestCriteria2SculptorCriteria<QueryVersion> getQueryCriteriaMapper() {
        return datasetCriteriaMapper;
    }

    private class QueryCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteriaBase retrieveProperty(MetamacRestQueryPropertyRestriction propertyRestriction) throws RestException {
            QueryCriteriaPropertyRestriction propertyNameCriteria = QueryCriteriaPropertyRestriction.fromValue(propertyRestriction.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return buildSculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().code(), PropertyTypeEnum.STRING, propertyRestriction);
                case NAME:
                    return buildSculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().title().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case DESCRIPTION:
                    return buildSculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().description().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case RELATED_DATASET_URN:
                    return buildSculptorPropertyCriteria(QueryVersionProperties.datasetVersion().siemacMetadataStatisticalResource().urn(), PropertyTypeEnum.STRING, propertyRestriction);
                case TYPE:
                    return buildSculptorPropertyCriteria(QueryVersionProperties.type(), PropertyTypeEnum.QUERY_TYPE, propertyRestriction);
                case STATUS:
                    return buildSculptorPropertyCriteria(QueryVersionProperties.status(), PropertyTypeEnum.QUERY_STATUS, propertyRestriction);
                case VALID_FROM:
                    return buildSculptorPropertyCriteriaForDateProperty(propertyRestriction, QueryVersionProperties.lifeCycleStatisticalResource().validTo(), QueryVersion.class, false);
                case VALID_TO:
                    return buildSculptorPropertyCriteriaForDateProperty(propertyRestriction, QueryVersionProperties.lifeCycleStatisticalResource().validTo(), QueryVersion.class, false);
                case STATISTIC_OPERATION_URN:
                    return buildSculptorPropertyCriteria(QueryVersionProperties.lifeCycleStatisticalResource().statisticalOperation().urn(), PropertyTypeEnum.STRING, propertyRestriction);
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrder(MetamacRestOrder order) throws RestException {
            QueryCriteriaPropertyOrder propertyNameCriteria = QueryCriteriaPropertyOrder.fromValue(order.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return QueryVersionProperties.lifeCycleStatisticalResource().code();
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrderDefault() throws RestException {
            return QueryVersionProperties.lifeCycleStatisticalResource().code();
        }
    }
}