package org.siemac.metamac.statistical.resources.core.publication.criteria.mapper;

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
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesCriteriaUtils;
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
                    return new SculptorPropertyCriteria(PublicationVersionProperties.publication().identifiableStatisticalResource().code(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case URN:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.publication().identifiableStatisticalResource().urn(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case STATISTICAL_OPERATION_URN:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.publication().identifiableStatisticalResource().statisticalOperation().urn(),
                            propertyRestriction.getStringValue(), propertyRestriction.getOperationType());

                    // From Publication Version
                case TITLE:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().title().texts().label(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().description().texts().label(), propertyRestriction.getStringValue(),
                            propertyRestriction.getOperationType());
                case PROC_STATUS:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().procStatus(), propertyRestriction.getEnumValue(),
                            propertyRestriction.getOperationType());
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
