package org.siemac.metamac.statistical.resources.core.dataset.criteria.mapper;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.domain.LeafProperty;
import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.core.common.criteria.SculptorCriteriaConjunction;
import org.siemac.metamac.core.common.criteria.SculptorCriteriaDisjunction;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesCriteriaUtils;
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
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().statisticalOperation().urn(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case CODE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().code(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case URN:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().urn(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case TITLE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().title().texts().label(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().description().texts().label(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case VERSION_RATIONALE_TYPE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().versionRationaleTypes().value(), propertyRestriction.getEnumValue(),
                            propertyRestriction.getOperationType());
                case NEXT_VERSION:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().nextVersion(), propertyRestriction.getEnumValue(),
                            propertyRestriction.getOperationType());
                case NEXT_VERSION_DATE:
                    return new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.siemacMetadataStatisticalResource().nextVersionDate(),
                            DatasetVersion.class), propertyRestriction.getDateValue(), propertyRestriction.getOperationType());
                case PROC_STATUS:
                    if (ProcStatusEnum.PUBLISHED.equals(propertyRestriction.getEnumValue())) {
                        return StatisticalResourcesCriteriaUtils.buildPublishedVisibleCondition(DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus(), DatasetVersionProperties
                                .siemacMetadataStatisticalResource().validFrom(), DatasetVersion.class);
                    } else if (ProcStatusEnum.PUBLISHED_NOT_VISIBLE.equals(propertyRestriction.getEnumValue())) {
                        return StatisticalResourcesCriteriaUtils.buildPublishedNotVisibleCondition(DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus(), DatasetVersionProperties
                                .siemacMetadataStatisticalResource().validFrom(), DatasetVersion.class);
                    } else {
                        return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().procStatus(), propertyRestriction.getEnumValue(),
                                propertyRestriction.getOperationType());
                    }
                case TITLE_ALTERNATIVE:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().titleAlternative().texts().label(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case KEYWORDS:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().keywords().texts().label(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case NEWNESS_UNTIL_DATE:
                    return new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.siemacMetadataStatisticalResource().newnessUntilDate(),
                            DatasetVersion.class), propertyRestriction.getDateValue(), propertyRestriction.getOperationType());
                case DATASET_GEOGRAPHIC_GRANULARITY_URN:
                    SculptorPropertyCriteria urnGeoCriteria = new SculptorPropertyCriteria(DatasetVersionProperties.geographicGranularities().urn(), propertyRestriction.getStringValue(),
                            OperationType.LIKE);
                    SculptorPropertyCriteria urnProviderGeoCriteria = new SculptorPropertyCriteria(DatasetVersionProperties.geographicGranularities().urnProvider(),
                            propertyRestriction.getStringValue(), OperationType.LIKE);
                    return new SculptorCriteriaDisjunction(urnGeoCriteria, urnProviderGeoCriteria);
                case DATASET_TEMPORAL_GRANULARITY_URN:
                    SculptorPropertyCriteria urnTempCriteria = new SculptorPropertyCriteria(DatasetVersionProperties.temporalGranularities().urn(), propertyRestriction.getStringValue(),
                            OperationType.LIKE);
                    SculptorPropertyCriteria urnTempProviderCriteria = new SculptorPropertyCriteria(DatasetVersionProperties.temporalGranularities().urnProvider(),
                            propertyRestriction.getStringValue(), OperationType.LIKE);
                    return new SculptorCriteriaDisjunction(urnTempCriteria, urnTempProviderCriteria);
                case DATASET_DATE_START:
                    return new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.dateStart(), DatasetVersion.class), propertyRestriction.getDateValue(),
                            propertyRestriction.getOperationType());
                case DATASET_DATE_END:
                    return new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.dateEnd(), DatasetVersion.class), propertyRestriction.getDateValue(),
                            propertyRestriction.getOperationType());
                case DATASET_RELATED_DSD_URN:
                    SculptorPropertyCriteria urnDsdCriteria = new SculptorPropertyCriteria(DatasetVersionProperties.relatedDsd().urn(), propertyRestriction.getStringValue(), OperationType.LIKE);
                    SculptorPropertyCriteria urnDsdProviderCriteria = new SculptorPropertyCriteria(DatasetVersionProperties.relatedDsd().urnProvider(), propertyRestriction.getStringValue(),
                            OperationType.LIKE);
                    return new SculptorCriteriaDisjunction(urnDsdCriteria, urnDsdProviderCriteria);
                case DATASET_DATE_NEXT_UPDATE:
                    return new SculptorPropertyCriteria(CriteriaUtils.getDatetimeLeafPropertyEmbedded(DatasetVersionProperties.dateNextUpdate(), DatasetVersion.class),
                            propertyRestriction.getDateValue(), propertyRestriction.getOperationType());
                case DATASET_STATISTIC_OFFICIALITY_IDENTIFIER:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.statisticOfficiality().identifier(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case DATA:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.datasetRepositoryId(), propertyRestriction.getStringValue(), propertyRestriction.getOperationType());
                case LAST_VERSION:
                    return new SculptorPropertyCriteria(DatasetVersionProperties.siemacMetadataStatisticalResource().lastVersion(), propertyRestriction.getBooleanValue(),
                            propertyRestriction.getOperationType());
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
