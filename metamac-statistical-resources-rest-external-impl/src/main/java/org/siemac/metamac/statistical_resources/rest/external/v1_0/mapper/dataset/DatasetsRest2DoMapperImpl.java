package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.rest.common.query.domain.MetamacRestOrder;
import org.siemac.metamac.rest.common.query.domain.MetamacRestQueryPropertyRestriction;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.search.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DatasetCriteriaPropertyOrder;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DatasetCriteriaPropertyRestriction;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.BaseRest2DoMapperV10Impl;
import org.springframework.stereotype.Component;

@Component
public class DatasetsRest2DoMapperImpl extends BaseRest2DoMapperV10Impl implements DatasetsRest2DoMapper {

    private RestCriteria2SculptorCriteria<DatasetVersion> datasetCriteriaMapper = null;

    public DatasetsRest2DoMapperImpl() {
        datasetCriteriaMapper = new RestCriteria2SculptorCriteria<DatasetVersion>(DatasetVersion.class, DatasetCriteriaPropertyOrder.class, DatasetCriteriaPropertyRestriction.class,
                new DatasetCriteriaCallback());
    }

    @Override
    public RestCriteria2SculptorCriteria<DatasetVersion> getDatasetCriteriaMapper() {
        return datasetCriteriaMapper;
    }

    private class DatasetCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteriaBase retrieveProperty(MetamacRestQueryPropertyRestriction propertyRestriction) throws RestException {
            DatasetCriteriaPropertyRestriction propertyNameCriteria = DatasetCriteriaPropertyRestriction.fromValue(propertyRestriction.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return buildSculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().code(), PropertyTypeEnum.STRING, propertyRestriction);
                case NAME:
                    return buildSculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().title().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case DESCRIPTION:
                    return buildSculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().description().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case VALID_FROM:
                    return buildSculptorPropertyCriteriaForDateProperty(propertyRestriction, DatasetVersionProperties.siemacMetadataStatisticalResource().validFrom(), DatasetVersion.class, false);
                case VALID_TO:
                    return buildSculptorPropertyCriteriaForDateProperty(propertyRestriction, DatasetVersionProperties.siemacMetadataStatisticalResource().validTo(), DatasetVersion.class, false);
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrder(MetamacRestOrder order) throws RestException {
            DatasetCriteriaPropertyOrder propertyNameCriteria = DatasetCriteriaPropertyOrder.fromValue(order.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return DatasetVersionProperties.siemacMetadataStatisticalResource().code();
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrderDefault() throws RestException {
            return DatasetVersionProperties.siemacMetadataStatisticalResource().code();
        }
    }
}