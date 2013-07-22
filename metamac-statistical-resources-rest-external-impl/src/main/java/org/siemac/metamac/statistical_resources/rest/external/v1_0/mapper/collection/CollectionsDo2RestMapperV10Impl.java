package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.collection;

import java.util.List;

import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.BaseDo2RestMapperV10Impl;
import org.springframework.stereotype.Component;

@Component
public class CollectionsDo2RestMapperV10Impl extends BaseDo2RestMapperV10Impl implements CollectionsDo2RestMapperV10 {

    @Override
    public Resource toResource(PublicationVersion source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getLifeCycleStatisticalResource().getCode());
        target.setUrn(source.getLifeCycleStatisticalResource().getUrn());
        target.setKind(RestExternalConstants.KIND_COLLECTION);
        target.setSelfLink(toCollectionSelfLink(source));
        target.setName(toInternationalString(source.getLifeCycleStatisticalResource().getTitle(), selectedLanguages));
        return target;
    }

    private ResourceLink toCollectionSelfLink(PublicationVersion source) {
        return toResourceLink(RestExternalConstants.KIND_COLLECTION, toCollectionLink(source));
    }

    private String toCollectionLink(PublicationVersion source) {
        String resourceSubpath = RestExternalConstants.LINK_SUBPATH_COLLECTIONS;
        String agencyID = source.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested();
        String resourceID = source.getSiemacMetadataStatisticalResource().getCode();
        String version = source.getSiemacMetadataStatisticalResource().getVersionLogic(); // TODO devolver versión?
        return toResourceLink(resourceSubpath, agencyID, resourceID, version);
    }
}