package org.siemac.metamac.statistical.resources.core.common.utils;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.Publication;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;


public class RelatedResourceUtils {

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
    
    public static RelatedResource createRelatedResourceForHasLifecycleResource(HasLifecycle resource) throws MetamacException {
        RelatedResource relatedResource = new RelatedResource();
        
        if (resource instanceof DatasetVersion) {
            relatedResource.setType(TypeRelatedResourceEnum.DATASET_VERSION);
            relatedResource.setDatasetVersion((DatasetVersion)resource);
        } else if (resource instanceof Dataset) {
            relatedResource.setType(TypeRelatedResourceEnum.DATASET);
            relatedResource.setDataset((Dataset)resource);
        } else if (resource instanceof PublicationVersion) {
            relatedResource.setType(TypeRelatedResourceEnum.PUBLICATION_VERSION);
            relatedResource.setPublicationVersion((PublicationVersion)resource);
        } else if (resource instanceof Publication) {
            relatedResource.setType(TypeRelatedResourceEnum.PUBLICATION);
            relatedResource.setPublication((Publication)resource);
        } else if (resource instanceof QueryVersion) {
            relatedResource.setType(TypeRelatedResourceEnum.QUERY_VERSION);
            relatedResource.setQueryVersion((QueryVersion)resource);
        } else if (resource instanceof Query) {
            relatedResource.setType(TypeRelatedResourceEnum.QUERY);
            relatedResource.setQuery((Query)resource);
        } else {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Undefined resource type");
        }
        return relatedResource;
    }
}
