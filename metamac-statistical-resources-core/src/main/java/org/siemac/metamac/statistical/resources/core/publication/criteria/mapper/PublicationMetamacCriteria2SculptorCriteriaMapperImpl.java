package org.siemac.metamac.statistical.resources.core.publication.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.criteria.enums.PublicationCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.publication.criteria.enums.PublicationCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.springframework.stereotype.Component;

@Component
public class PublicationMetamacCriteria2SculptorCriteriaMapperImpl implements PublicationMetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<PublicationVersion> publicationCriteriaMapper = null;

    /**************************************************************************
     * Constructor
     **************************************************************************/

    public PublicationMetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        publicationCriteriaMapper = new MetamacCriteria2SculptorCriteria<PublicationVersion>(PublicationVersion.class, PublicationCriteriaOrderEnum.class, PublicationCriteriaPropertyEnum.class, new PublicationCriteriaCallback());
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
        public SculptorPropertyCriteria retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            PublicationCriteriaPropertyEnum propertyEnum = PublicationCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                case CODE:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.publication().identifiableStatisticalResource().code(), propertyRestriction.getStringValue());
                case URN:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.publication().identifiableStatisticalResource().urn(), propertyRestriction.getStringValue());
                case PUBLICATION_VERSION_TITLE:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().title().texts().label(), propertyRestriction.getStringValue());
                case STATISTICAL_OPERATION_URN:    
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().statisticalOperation().urn(), propertyRestriction.getStringValue());
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property<PublicationVersion> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            PublicationCriteriaOrderEnum propertyOrderEnum = PublicationCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyOrderEnum) {
                case CODE:
                    return PublicationVersionProperties.publication().identifiableStatisticalResource().code();
                case URN:
                    return PublicationVersionProperties.publication().identifiableStatisticalResource().urn();
                case PUBLICATION_VERSION_TITLE:
                    return PublicationVersionProperties.siemacMetadataStatisticalResource().title().texts().label();
                case STATISTICAL_OPERATION_URN:
                    return PublicationVersionProperties.siemacMetadataStatisticalResource().statisticalOperation().urn();
                default:
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @Override
        public Property<PublicationVersion> retrievePropertyOrderDefault() throws MetamacException {
            return PublicationVersionProperties.publication().id();
        }
    }
}
