package org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.springframework.stereotype.Component;

@Component
public class DatasetMetamacCriteria2SculptorCriteriaMapperImpl implements DatasetMetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<DatasetVersion> datasetCriteriaMapper = null;

    /**************************************************************************
     * Constructor
     **************************************************************************/

    public DatasetMetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        datasetCriteriaMapper = new MetamacCriteria2SculptorCriteria<DatasetVersion>(DatasetVersion.class, StatisticalResourcesCriteriaOrderEnum.class, StatisticalResourcesCriteriaPropertyEnum.class,
                new DatasetCriteriaCallback());
    }

    /**************************************************************************
     * Mappings
     **************************************************************************/

    @Override
    public MetamacCriteria2SculptorCriteria<DatasetVersion> getDatasetCriteriaMapper() {
        return datasetCriteriaMapper;
    }

    /**************************************************************************
     * CallBacks classes
     *************************************************************************/

    private class DatasetCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteria retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            StatisticalResourcesCriteriaPropertyEnum propertyEnum = StatisticalResourcesCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                // From dataset
                case CODE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.dataset().identifiableStatisticalResource().code(), propertyRestriction.getStringValue());
                case URN:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.dataset().identifiableStatisticalResource().urn(), propertyRestriction.getStringValue());
                case STATISTICAL_OPERATION_URN:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.dataset().identifiableStatisticalResource().statisticalOperation().urn(), propertyRestriction.getStringValue());

                // From datasetVersion
                case TITLE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().title().texts().label(), propertyRestriction.getStringValue());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().description().texts().label(), propertyRestriction.getStringValue());
                case PROC_STATUS:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus(), propertyRestriction.getEnumValue());

                default:
                    // LAST_VERSION
                    // QUERY_STATUS
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property<DatasetVersion> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            StatisticalResourcesCriteriaOrderEnum propertyOrderEnum = StatisticalResourcesCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyOrderEnum) {
                // From dataset
                case CODE:
                    return DatasetVersionProperties.dataset().identifiableStatisticalResource().code();
                case URN:
                    return DatasetVersionProperties.dataset().identifiableStatisticalResource().urn();
                case STATISTICAL_OPERATION_URN:
                    return DatasetVersionProperties.dataset().identifiableStatisticalResource().statisticalOperation().urn();

                // From datasetVersion
                case TITLE:
                    return DatasetVersionProperties.siemacMetadataStatisticalResource().title().texts().label();
                case PROC_STATUS:
                    return DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus();
                    
                default:
                    // LAST_VERSION
                    // QUERY_STATUS
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @Override
        public Property<DatasetVersion> retrievePropertyOrderDefault() throws MetamacException {
            return DatasetVersionProperties.dataset().id();
        }
    }
}
