package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.collection;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Chapter;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Collection;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CollectionNode;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CollectionNodes;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CollectionStructure;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Table;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.BaseDo2RestMapperV10Impl;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset.DatasetsDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.query.QueriesDo2RestMapperV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectionsDo2RestMapperV10Impl extends BaseDo2RestMapperV10Impl implements CollectionsDo2RestMapperV10 {

    @Autowired
    private DatasetsDo2RestMapperV10 datasetsDo2RestMapper;

    @Autowired
    private DatasetVersionRepository datasetVersionRepository;

    @Autowired
    private QueryVersionRepository   queryVersionRepository;

    @Autowired
    private QueriesDo2RestMapperV10  queriesDo2RestMapper;

    @Override
    public Collection toCollection(PublicationVersion source, List<String> selectedLanguages) throws Exception {
        if (source == null) {
            return null;
        }
        selectedLanguages = languagesRequestedToEffectiveLanguages(selectedLanguages);

        Collection target = new Collection();
        target.setKind(RestExternalConstants.KIND_COLLECTION);
        target.setId(source.getSiemacMetadataStatisticalResource().getCode());
        target.setUrn(source.getSiemacMetadataStatisticalResource().getUrn());
        target.setSelfLink(toCollectionSelfLink(source));
        target.setName(toInternationalString(source.getSiemacMetadataStatisticalResource().getTitle(), selectedLanguages));
        target.setDescription(toInternationalString(source.getSiemacMetadataStatisticalResource().getDescription(), selectedLanguages));
        target.setParentLink(toCollectionParentLink(source));
        target.setChildLinks(toCollectionChildLinks(source));
        target.setSelectedLanguages(toLanguages(selectedLanguages));
        target.setFormatExtentResources(source.getFormatExtentResources());
        target.setStructure(toCollectionStructure(source, selectedLanguages));
        return target;
    }

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

    private CollectionStructure toCollectionStructure(PublicationVersion source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        CollectionStructure target = new CollectionStructure();
        target.setNodes(toCollectionNodes(source.getChildrenFirstLevel(), selectedLanguages));
        return target;
    }

    private CollectionNode toCollectionNode(ElementLevel source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        CollectionNode target = null;
        if (source.isChapter()) {
            target = toCollectionNode(source.getChapter(), selectedLanguages);
        } else if (source.isCube()) {
            target = toCollectionNode(source.getCube(), selectedLanguages);
        }
        return target;
    }

    private Chapter toCollectionNode(org.siemac.metamac.statistical.resources.core.publication.domain.Chapter source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        Chapter target = new Chapter();
        target.setName(toInternationalString(source.getNameableStatisticalResource().getTitle(), selectedLanguages));
        target.setDescription(toInternationalString(source.getNameableStatisticalResource().getDescription(), selectedLanguages));

        target.setNodes(toCollectionNodes(source.getElementLevel().getChildren(), selectedLanguages));
        return target;
    }

    private Table toCollectionNode(Cube source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        Table target = new Table();
        target.setName(toInternationalString(source.getNameableStatisticalResource().getTitle(), selectedLanguages));
        target.setDescription(toInternationalString(source.getNameableStatisticalResource().getDescription(), selectedLanguages));
        if (source.getDatasetUrn() != null) {
            DatasetVersion dataset = datasetVersionRepository.retrieveLastPublishedVersion(source.getDatasetUrn());
            target.setDataset(datasetsDo2RestMapper.toResource(dataset, selectedLanguages)); // TODO devolver latest en selfLink
        } else if (source.getQuery() != null) {
            QueryVersion query = queryVersionRepository.retrieveLastPublishedVersion(source.getQueryUrn());
            target.setQuery(queriesDo2RestMapper.toResource(query, selectedLanguages)); // TODO devolver latest en selfLink si se devuelve versión
        }
        return target;
    }

    private CollectionNodes toCollectionNodes(List<ElementLevel> sources, List<String> selectedLanguages) throws MetamacException {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        CollectionNodes target = new CollectionNodes();
        for (ElementLevel source : sources) {
            target.getNodes().add(toCollectionNode(source, selectedLanguages));
        }
        return target;
    }

    private ResourceLink toCollectionParentLink(PublicationVersion source) {
        return toCollectionsSelfLink(null, null, null);
    }

    private ChildLinks toCollectionChildLinks(PublicationVersion source) {
        // nothing
        return null;
    }

    private ResourceLink toCollectionsSelfLink(String agencyID, String resourceID, String version) {
        return toResourceLink(RestExternalConstants.KIND_COLLECTIONS, toCollectionsLink(agencyID, resourceID, version));
    }

    private String toCollectionsLink(String agencyID, String resourceID, String version) {
        String resourceSubpath = RestExternalConstants.LINK_SUBPATH_COLLECTIONS;
        return toResourceLink(resourceSubpath, agencyID, resourceID, version);
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