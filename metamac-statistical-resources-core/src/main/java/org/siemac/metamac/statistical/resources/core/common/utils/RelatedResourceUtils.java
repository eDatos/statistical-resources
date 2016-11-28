package org.siemac.metamac.statistical.resources.core.common.utils;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationRepository;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RelatedResourceUtils {

    @Autowired
    private static DatasetVersionRepository     datasetVersionRepository;

    @Autowired
    private static PublicationVersionRepository publicationVersionRepository;

    @Autowired
    private static DatasetRepository            datasetRepository;

    @Autowired
    private static PublicationRepository        publicationRepository;

    @Autowired
    private static QueryRepository              queryRepository;

    @Autowired
    private static QueryVersionRepository       queryVersionRepository;

    public static NameableStatisticalResource retrieveNameableResourceLinkedToRelatedResource(RelatedResource source) throws MetamacException {
        switch (source.getType()) {
            case DATASET_VERSION:
                return source.getDatasetVersion().getSiemacMetadataStatisticalResource();
            case PUBLICATION_VERSION:
                return source.getPublicationVersion().getSiemacMetadataStatisticalResource();
            case QUERY_VERSION:
                return source.getQueryVersion().getLifeCycleStatisticalResource();
            default:
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of related resource not supported: " + source.getType());
        }
    }

    public static IdentifiableStatisticalResource retrieveIdentifiableResourceLinkedToRelatedResource(RelatedResource source) throws MetamacException {
        switch (source.getType()) {
            case DATASET_VERSION:
                return source.getDatasetVersion().getSiemacMetadataStatisticalResource();
            case PUBLICATION_VERSION:
                return source.getPublicationVersion().getSiemacMetadataStatisticalResource();
            case QUERY_VERSION:
                return source.getQueryVersion().getLifeCycleStatisticalResource();
            case DATASET:
                return source.getDataset().getIdentifiableStatisticalResource();
            case QUERY:
                return source.getQuery().getIdentifiableStatisticalResource();
            default:
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of related resource not supported: " + source.getType());
        }
    }

    public static RelatedResource createRelatedResourceForHasLifecycleResource(HasLifecycle resource) throws MetamacException {
        RelatedResource relatedResource = new RelatedResource();

        if (resource instanceof DatasetVersion) {
            relatedResource.setType(TypeRelatedResourceEnum.DATASET_VERSION);
            relatedResource.setDatasetVersion((DatasetVersion) resource);
        } else if (resource instanceof Dataset) {
            relatedResource.setType(TypeRelatedResourceEnum.DATASET);
            relatedResource.setDataset((Dataset) resource);
        } else if (resource instanceof PublicationVersion) {
            relatedResource.setType(TypeRelatedResourceEnum.PUBLICATION_VERSION);
            relatedResource.setPublicationVersion((PublicationVersion) resource);
        } else if (resource instanceof Publication) {
            relatedResource.setType(TypeRelatedResourceEnum.PUBLICATION);
            relatedResource.setPublication((Publication) resource);
        } else if (resource instanceof QueryVersion) {
            relatedResource.setType(TypeRelatedResourceEnum.QUERY_VERSION);
            relatedResource.setQueryVersion((QueryVersion) resource);
        } else if (resource instanceof Query) {
            relatedResource.setType(TypeRelatedResourceEnum.QUERY);
            relatedResource.setQuery((Query) resource);
        } else {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Undefined resource type");
        }
        return relatedResource;
    }

    public static RelatedResource createRelatedResourceFromRelatedResourceResult(RelatedResourceResult source) throws MetamacException {
        RelatedResource target = new RelatedResource();
        switch (source.getType()) {
            case DATASET:
                target.setDataset(datasetRepository.retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.DATASET);
                break;
            case DATASET_VERSION:
                target.setDatasetVersion(datasetVersionRepository.retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.DATASET_VERSION);
                break;
            case PUBLICATION:
                target.setPublication(publicationRepository.retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.PUBLICATION);
                break;
            case PUBLICATION_VERSION:
                target.setPublicationVersion(publicationVersionRepository.retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.PUBLICATION);
                break;
            case QUERY:
                target.setQuery(queryRepository.retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.QUERY);
                break;
            case QUERY_VERSION:
                target.setQueryVersion(queryVersionRepository.retrieveByUrn(source.getUrn()));
                target.setType(TypeRelatedResourceEnum.QUERY_VERSION);
                break;
            default:
                break;
        }
        return target;
    }

}
