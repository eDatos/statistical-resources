package org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.LeafProperty;
import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaDisjunction;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaPropertyEnum;
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
        datasetVersionCriteriaMapper = new MetamacCriteria2SculptorCriteria<DatasetVersion>(DatasetVersion.class, StatisticalResourcesCriteriaOrderEnum.class,
                StatisticalResourcesCriteriaPropertyEnum.class, new DatasetVersionCriteriaCallback());
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
        public SculptorPropertyCriteriaBase retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            StatisticalResourcesCriteriaPropertyEnum propertyEnum = StatisticalResourcesCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                case STATISTICAL_OPERATION_URN:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().statisticalOperation().urn(), propertyRestriction.getStringValue());
                case CODE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().code(), propertyRestriction.getStringValue());
                case URN:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().urn(), propertyRestriction.getStringValue());
                case TITLE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().title().texts().label(), propertyRestriction.getStringValue());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().description().texts().label(), propertyRestriction.getStringValue());
                case VERSION_RATIONALE_TYPE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().versionRationaleTypes().value(), propertyRestriction.getEnumValue());
                case NEXT_VERSION:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().nextVersion(), propertyRestriction.getEnumValue());
                case NEXT_VERSION_DATE:
                    return new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.siemacMetadataStatisticalResource().nextVersionDate(),
                            DatasetVersion.class), propertyRestriction.getDateValue());
                case PROC_STATUS:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus(), propertyRestriction.getEnumValue());
                case TITLE_ALTERNATIVE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().titleAlternative().texts().label(), propertyRestriction.getStringValue());
                case KEYWORDS:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().keywords().texts().label(), propertyRestriction.getStringValue());
                case NEWNESS_UNTIL_DATE:
                    return new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.siemacMetadataStatisticalResource().newnessUntilDate(),
                            DatasetVersion.class), propertyRestriction.getDateValue());
                case DATASET_GEOGRAPHIC_GRANULARITY_URN:
                    return new SculptorPropertyCriteriaDisjunction(DatasetVersionProperties.geographicGranularities().urn(), propertyRestriction.getStringValue(), OperationType.LIKE,
                            DatasetVersionProperties.geographicGranularities().urnProvider(), propertyRestriction.getStringValue(), OperationType.LIKE);
                case DATASET_TEMPORAL_GRANULARITY_URN:
                    return new SculptorPropertyCriteriaDisjunction(DatasetVersionProperties.temporalGranularities().urn(), propertyRestriction.getStringValue(), OperationType.LIKE,
                            DatasetVersionProperties.temporalGranularities().urnProvider(), propertyRestriction.getStringValue(), OperationType.LIKE);
                case DATASET_DATE_START:
                    return new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.dateStart(), DatasetVersion.class), propertyRestriction.getDateValue());
                case DATASET_DATE_END:
                    return new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.dateEnd(), DatasetVersion.class), propertyRestriction.getDateValue());
                case DATASET_RELATED_DSD_URN:
                    return new SculptorPropertyCriteriaDisjunction(DatasetVersionProperties.relatedDsd().urn(), propertyRestriction.getStringValue(), OperationType.LIKE,
                            DatasetVersionProperties.relatedDsd().urnProvider(), propertyRestriction.getStringValue(), OperationType.LIKE);
                case DATASET_DATE_NEXT_UPDATE:
                    return new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.dateNextUpdate(), DatasetVersion.class), propertyRestriction.getDateValue());
                case DATASET_STATISTIC_OFFICIALITY_IDENTIFIER:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.statisticOfficiality().identifier(), propertyRestriction.getStringValue());
                case DATA:    
                    return new SculptorPropertyCriteria(DatasetVersionProperties.datasetRepositoryId(), propertyRestriction.getStringValue());
                case LAST_VERSION:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().lastVersion(), propertyRestriction.getBooleanValue());
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property<DatasetVersion> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            StatisticalResourcesCriteriaOrderEnum propertyOrderEnum = StatisticalResourcesCriteriaOrderEnum.fromValue(order.getPropertyName());
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
                case PROC_STATUS:
                    return DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus();
                case LAST_UPDATED:
                    return new LeafProperty<DatasetVersion>(DatasetVersionProperties.siemacMetadataStatisticalResource().lastUpdated().getName(),
                            CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true, DatasetVersion.class);
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @Override
        public Property<DatasetVersion> retrievePropertyOrderDefault() throws MetamacException {
            return new LeafProperty<DatasetVersion>(DatasetVersionProperties.siemacMetadataStatisticalResource().lastUpdated().getName(), CoreCommonConstants.CRITERIA_DATETIME_COLUMN_DATETIME, true,
                    DatasetVersion.class);
        }
    }
}
