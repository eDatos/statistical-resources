package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.collection;

import org.fornax.cartridges.sculptor.framework.domain.Property;
import org.siemac.metamac.rest.common.query.domain.MetamacRestOrder;
import org.siemac.metamac.rest.common.query.domain.MetamacRestQueryPropertyRestriction;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.search.criteria.SculptorPropertyCriteriaBase;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria;
import org.siemac.metamac.rest.search.criteria.mapper.RestCriteria2SculptorCriteria.CriteriaCallback;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CollectionCriteriaPropertyOrder;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CollectionCriteriaPropertyRestriction;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.BaseRest2DoMapperV10Impl;
import org.springframework.stereotype.Component;

@Component
public class CollectionsRest2DoMapperImpl extends BaseRest2DoMapperV10Impl implements CollectionsRest2DoMapper {

    private RestCriteria2SculptorCriteria<PublicationVersion> collectionCriteriaMapper = null;

    public CollectionsRest2DoMapperImpl() {
        collectionCriteriaMapper = new RestCriteria2SculptorCriteria<PublicationVersion>(PublicationVersion.class, CollectionCriteriaPropertyOrder.class, CollectionCriteriaPropertyRestriction.class,
                new CollectionCriteriaCallback());
    }

    @Override
    public RestCriteria2SculptorCriteria<PublicationVersion> getCollectionCriteriaMapper() {
        return collectionCriteriaMapper;
    }

    private class CollectionCriteriaCallback implements CriteriaCallback {

        @Override
        public SculptorPropertyCriteriaBase retrieveProperty(MetamacRestQueryPropertyRestriction propertyRestriction) throws RestException {
            CollectionCriteriaPropertyRestriction propertyNameCriteria = CollectionCriteriaPropertyRestriction.fromValue(propertyRestriction.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return buildSculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().code(), PropertyTypeEnum.STRING, propertyRestriction);
                case NAME:
                    return buildSculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().title().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                case DESCRIPTION:
                    return buildSculptorPropertyCriteria(PublicationVersionProperties.siemacMetadataStatisticalResource().description().texts().label(), PropertyTypeEnum.STRING, propertyRestriction);
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrder(MetamacRestOrder order) throws RestException {
            CollectionCriteriaPropertyOrder propertyNameCriteria = CollectionCriteriaPropertyOrder.fromValue(order.getPropertyName());
            switch (propertyNameCriteria) {
                case ID:
                    return PublicationVersionProperties.siemacMetadataStatisticalResource().code();
                default:
                    throw toRestExceptionParameterIncorrect(propertyNameCriteria.name());
            }
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Property retrievePropertyOrderDefault() throws RestException {
            return PublicationVersionProperties.siemacMetadataStatisticalResource().code();
        }
    }
}