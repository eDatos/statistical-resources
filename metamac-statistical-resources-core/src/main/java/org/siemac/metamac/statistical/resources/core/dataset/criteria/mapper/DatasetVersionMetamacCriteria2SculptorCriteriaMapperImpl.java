package org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.enums.DatasetVersionCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.dataset.criteria.enums.DatasetVersionCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.stereotype.Component;

@Component
public class DatasetVersionMetamacCriteria2SculptorCriteriaMapperImpl implements DatasetVersionMetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<DatasetVersion> datasetVersionCriteriaMapper = null;

    /**************************************************************************
     * Constructor
     **************************************************************************/

    public DatasetVersionMetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        datasetVersionCriteriaMapper = new MetamacCriteria2SculptorCriteria<DatasetVersion>(DatasetVersion.class, DatasetVersionCriteriaOrderEnum.class, DatasetVersionCriteriaPropertyEnum.class, new DatasetVersionCriteriaCallback());
    }

    /**************************************************************************
     * Mappings
     **************************************************************************/

    @Override
    public MetamacCriteria2SculptorCriteria<DatasetVersion> getDatasetVersionCriteriaMapper() {
        return datasetVersionCriteriaMapper;
    }

    /**************************************************************************
     * CallBacks classes
     *************************************************************************/

    private class DatasetVersionCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteria retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            DatasetVersionCriteriaPropertyEnum propertyEnum = DatasetVersionCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                case CODE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().code(), propertyRestriction.getStringValue());
                case URN:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().urn(), propertyRestriction.getStringValue());
                case TITLE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().title().texts().label(), propertyRestriction.getStringValue());
                case PROC_STATUS:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus(), propertyRestriction.getEnumValue());
                case STATISTICAL_OPERATION_URN:    
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().statisticalOperation().urn(), propertyRestriction.getStringValue());
                case LAST_VERSION:    
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().lastVersion(), propertyRestriction.getBooleanValue());
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property<DatasetVersion> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            DatasetVersionCriteriaOrderEnum propertyOrderEnum = DatasetVersionCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyOrderEnum) {
                case CODE:
                    return DatasetVersionProperties.siemacMetadataStatisticalResource().code();
                case URN:
                    return DatasetVersionProperties.siemacMetadataStatisticalResource().urn();
                case TITLE:
                    return DatasetVersionProperties.siemacMetadataStatisticalResource().title().texts().label();
                case STATISTICAL_OPERATION_URN:
                    return DatasetVersionProperties.siemacMetadataStatisticalResource().statisticalOperation().urn();
                case LAST_VERSION:
                    return DatasetVersionProperties.siemacMetadataStatisticalResource().lastVersion();
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @Override
        public Property<DatasetVersion> retrievePropertyOrderDefault() throws MetamacException {
            return DatasetVersionProperties.id();
        }
    }
}
