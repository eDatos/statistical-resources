package org.siemac.metamac.statistical.resources.core.multidataset.criteria.mapper;

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
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesCriteriaUtils;
import org.springframework.stereotype.Component;

@Component
public class MultidatasetMetamacCriteria2SculptorCriteriaMapperImpl implements MultidatasetMetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<MultidatasetVersion> multidatasetCriteriaMapper = null;

    public MultidatasetMetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        multidatasetCriteriaMapper = new MetamacCriteria2SculptorCriteria<>(MultidatasetVersion.class, StatisticalResourcesCriteriaOrderEnum.class, StatisticalResourcesCriteriaPropertyEnum.class,
                new MultidatasetCriteriaCallback());
    }

    @Override
    public MetamacCriteria2SculptorCriteria<MultidatasetVersion> getMultidatasetCriteriaMapper() {
        return multidatasetCriteriaMapper;
    }

    private class MultidatasetCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteriaBase retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            StatisticalResourcesCriteriaPropertyEnum propertyEnum = StatisticalResourcesCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                // From multidataset
                case CODE:
                    return new SculptorPropertyCriteria(MultidatasetVersionProperties.multidataset().identifiableStatisticalResource().code(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case URN:
                    return new SculptorPropertyCriteria(MultidatasetVersionProperties.multidataset().identifiableStatisticalResource().urn(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case STATISTICAL_OPERATION_URN:
                    return new SculptorPropertyCriteria(MultidatasetVersionProperties.multidataset().identifiableStatisticalResource().statisticalOperation().urn(),
                            propertyRestriction.getStringValue(), propertyRestriction.getOperationType());

                // From multidatasetVersion
                case TITLE:
                    return new SculptorPropertyCriteria(MultidatasetVersionProperties.siemacMetadataStatisticalResource().title().texts().label(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(MultidatasetVersionProperties.siemacMetadataStatisticalResource().description().texts().label(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case PROC_STATUS:
                    if (ProcStatusEnum.PUBLISHED.equals(propertyRestriction.getEnumValue())) {
                        return StatisticalResourcesCriteriaUtils.buildPublishedVisibleCondition(MultidatasetVersionProperties.siemacMetadataStatisticalResource().procStatus(),
                                MultidatasetVersionProperties.siemacMetadataStatisticalResource().validFrom(), MultidatasetVersion.class);
                    } else {
                        return new SculptorPropertyCriteria(MultidatasetVersionProperties.siemacMetadataStatisticalResource().procStatus(), propertyRestriction.getEnumValue(),
                                propertyRestriction.getOperationType());
                    }
                default:
                    // LAST_VERSION
                    // QUERY_STATUS
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            StatisticalResourcesCriteriaOrderEnum propertyOrderEnum = StatisticalResourcesCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyOrderEnum) {
                // From multidataset
                case CODE:
                    return MultidatasetVersionProperties.multidataset().identifiableStatisticalResource().code();
                case URN:
                    return MultidatasetVersionProperties.multidataset().identifiableStatisticalResource().urn();
                case STATISTICAL_OPERATION_URN:
                    return MultidatasetVersionProperties.multidataset().identifiableStatisticalResource().statisticalOperation().urn();

                // From multidatasetVersion
                case TITLE:
                    return MultidatasetVersionProperties.siemacMetadataStatisticalResource().title().texts().label();
                case PROC_STATUS:
                    return MultidatasetVersionProperties.siemacMetadataStatisticalResource().procStatus();

                default:
                    // LAST_VERSION
                    // QUERY_STATUS
                    // LAST_UPDATED
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @Override
        public Property retrievePropertyOrderDefault() throws MetamacException {
            return MultidatasetVersionProperties.multidataset().id();
        }

    }

}
