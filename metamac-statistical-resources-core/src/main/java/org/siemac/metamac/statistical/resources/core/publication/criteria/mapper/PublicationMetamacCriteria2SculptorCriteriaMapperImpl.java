package org.siemac.metamac.statistical.resources.core.publication.criteria.mapper;

import java.util.Date;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction.OperationType;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteriaConjunction;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.springframework.stereotype.Component;

@Component
public class PublicationMetamacCriteria2SculptorCriteriaMapperImpl implements PublicationMetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<PublicationVersion> publicationCriteriaMapper = null;

    /**************************************************************************
     * Constructor
     **************************************************************************/

    public PublicationMetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        publicationCriteriaMapper = new MetamacCriteria2SculptorCriteria<PublicationVersion>(PublicationVersion.class, StatisticalResourcesCriteriaOrderEnum.class,
                StatisticalResourcesCriteriaPropertyEnum.class, new PublicationCriteriaCallback());
    }

    /**************************************************************************
     * Mappings
     **************************************************************************/

    @Override
    public MetamacCriteria2SculptorCriteria<PublicationVersion> getPublicationCriteriaMapper() {
        return publicationCriteriaMapper;
    }

    /**************************************************************************
     * CallBacks classes
     *************************************************************************/

    private class PublicationCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteriaBase retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            StatisticalResourcesCriteriaPropertyEnum propertyEnum = StatisticalResourcesCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                // From Publication
                case CODE:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.publication().identifiableStatisticalResource().code(), propertyRestriction.getStringValue());
                case URN:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.publication().identifiableStatisticalResource().urn(), propertyRestriction.getStringValue());
                case STATISTICAL_OPERATION_URN:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.publication().identifiableStatisticalResource().statisticalOperation().urn(), propertyRestriction.getStringValue());

                    // From Publication Version
                case TITLE:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().title().texts().label(), propertyRestriction.getStringValue());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().description().texts().label(), propertyRestriction.getStringValue());
                case PROC_STATUS:
                    if (ProcStatusEnum.PUBLISHED.equals(propertyRestriction.getEnumValue())) {
                        return new SculptorPropertyCriteriaConjunction(PublicationVersionProperties.siemacMetadataStatisticalResource().procStatus(), ProcStatusEnum.PUBLISHED, OperationType.EQ,
                                CriteriaUtils.getDatetimeLeafPropertyEmbedded(PublicationVersionProperties.siemacMetadataStatisticalResource().validFrom(), QueryVersion.class), new Date(),
                                OperationType.LE);
                    } else if (ProcStatusEnum.PUBLISHED_NOT_VISIBLE.equals(propertyRestriction.getEnumValue())) {
                        return new SculptorPropertyCriteriaConjunction(PublicationVersionProperties.siemacMetadataStatisticalResource().procStatus(), ProcStatusEnum.PUBLISHED, OperationType.EQ,
                                CriteriaUtils.getDatetimeLeafPropertyEmbedded(PublicationVersionProperties.siemacMetadataStatisticalResource().validFrom(), QueryVersion.class), new Date(),
                                OperationType.GT);
                    } else {
                        return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().procStatus(), propertyRestriction.getEnumValue());
                    }
                default:
                    // LAST_VERSION
                    // QUERY_STATUS
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property<PublicationVersion> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            StatisticalResourcesCriteriaOrderEnum propertyOrderEnum = StatisticalResourcesCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyOrderEnum) {
                // From Publication
                case CODE:
                    return PublicationVersionProperties.publication().identifiableStatisticalResource().code();
                case URN:
                    return PublicationVersionProperties.publication().identifiableStatisticalResource().urn();
                case STATISTICAL_OPERATION_URN:
                    return PublicationVersionProperties.publication().identifiableStatisticalResource().statisticalOperation().urn();

                    // From Publication Version
                case TITLE:
                    return PublicationVersionProperties.siemacMetadataStatisticalResource().title().texts().label();
                case PROC_STATUS:
                    return PublicationVersionProperties.siemacMetadataStatisticalResource().procStatus();

                default:
                    // LAST_UPDATED
                    // LAST_VERSION
                    // QUERY_STATUS
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @Override
        public Property<PublicationVersion> retrievePropertyOrderDefault() throws MetamacException {
            return PublicationVersionProperties.publication().id();
        }
    }
}
