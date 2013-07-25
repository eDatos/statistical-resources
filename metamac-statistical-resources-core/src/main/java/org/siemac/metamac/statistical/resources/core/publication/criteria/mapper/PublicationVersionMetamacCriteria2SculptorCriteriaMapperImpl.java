package org.siemac.metamac.statistical.resources.core.publication.criteria.mapper;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaOrder;
import org.siemac.metamac.core.common.criteria.MetamacCriteriaPropertyRestriction;
import org.siemac.metamac.core.common.criteria.SculptorPropertyCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria;
import org.siemac.metamac.core.common.criteria.mapper.MetamacCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaOrderEnum;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionProperties;
import org.springframework.stereotype.Component;

@Component
public class PublicationVersionMetamacCriteria2SculptorCriteriaMapperImpl implements PublicationVersionMetamacCriteria2SculptorCriteriaMapper {

    private MetamacCriteria2SculptorCriteria<PublicationVersion> publicationVersionCriteriaMapper = null;

    /**************************************************************************
     * Constructor
     **************************************************************************/

    public PublicationVersionMetamacCriteria2SculptorCriteriaMapperImpl() throws MetamacException {
        publicationVersionCriteriaMapper = new MetamacCriteria2SculptorCriteria<PublicationVersion>(PublicationVersion.class, StatisticalResourcesCriteriaOrderEnum.class, StatisticalResourcesCriteriaPropertyEnum.class, new PublicationVersionCriteriaCallback());
    }

    /**************************************************************************
     * Mappings
     **************************************************************************/

    @Override
    public MetamacCriteria2SculptorCriteria<PublicationVersion> getPublicationVersionCriteriaMapper() {
        return publicationVersionCriteriaMapper;
    }

    /**************************************************************************
     * CallBacks classes
     *************************************************************************/

    private class PublicationVersionCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteria retrieveProperty(MetamacCriteriaPropertyRestriction propertyRestriction) throws MetamacException {
            StatisticalResourcesCriteriaPropertyEnum propertyEnum = StatisticalResourcesCriteriaPropertyEnum.fromValue(propertyRestriction.getPropertyName());
            switch (propertyEnum) {
                case CODE:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().code(), propertyRestriction.getStringValue());
                case URN:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().urn(), propertyRestriction.getStringValue());
                case TITLE:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().title().texts().label(), propertyRestriction.getStringValue());
                case DESCRIPTION:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().description().texts().label(), propertyRestriction.getStringValue());
                case PROC_STATUS:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().procStatus(), propertyRestriction.getEnumValue());
                case STATISTICAL_OPERATION_URN:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().statisticalOperation().urn(), propertyRestriction.getStringValue());
                case LAST_VERSION:
                    return new SculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().lastVersion(), propertyRestriction.getBooleanValue());
                default:
                    // QUERY_STATUS
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, propertyRestriction.getPropertyName());
            }
        }

        @Override
        public Property<PublicationVersion> retrievePropertyOrder(MetamacCriteriaOrder order) throws MetamacException {
            StatisticalResourcesCriteriaOrderEnum propertyOrderEnum = StatisticalResourcesCriteriaOrderEnum.fromValue(order.getPropertyName());
            switch (propertyOrderEnum) {
                case CODE:
                    return PublicationVersionProperties.siemacMetadataStatisticalResource().code();
                case URN:
                    return PublicationVersionProperties.siemacMetadataStatisticalResource().urn();
                case TITLE:
                    return PublicationVersionProperties.siemacMetadataStatisticalResource().title().texts().label();
                case PROC_STATUS:
                    return PublicationVersionProperties.siemacMetadataStatisticalResource().procStatus();
                case STATISTICAL_OPERATION_URN:
                    return PublicationVersionProperties.siemacMetadataStatisticalResource().statisticalOperation().urn();
                case LAST_VERSION:
                    return PublicationVersionProperties.siemacMetadataStatisticalResource().lastVersion();
                default:
                    // QUERY_STATUS
                    throw new MetamacException(ServiceExceptionType.PARAMETER_INCORRECT, order.getPropertyName());
            }
        }

        @Override
        public Property<PublicationVersion> retrievePropertyOrderDefault() throws MetamacException {
            return PublicationVersionProperties.id();
        }
    }
}
