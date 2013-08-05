package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker;

import java.util.List;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelatedResourceChecker {

    @Autowired
    private DatasetVersionRepository     datasetVersionRepository;

    @Autowired
    private PublicationVersionRepository publicationVersionRepository;

    @Autowired
    private QueryVersionRepository       queryVersionRepository;

    public void checkRelatedResourcesExternallyPublished(List<RelatedResource> relatedResources, DateTime referenceValidFrom, String metadataName, List<MetamacExceptionItem> exceptionItems)
            throws MetamacException {
        for (RelatedResource relatedResource : relatedResources) {
            checkRelatedResourceExternallyPublished(relatedResource, referenceValidFrom, metadataName, exceptionItems);
        }
    }

    public void checkRelatedResourceExternallyPublished(RelatedResource relatedResource, DateTime referenceValidFrom, String metadataName, List<MetamacExceptionItem> exceptionItems)
            throws MetamacException {
        String urn = null;
        HasLifecycle actualRelatedResource;
        HasLifecycle expectedRelatedResource;
        switch (relatedResource.getType()) {
            case DATASET:
                urn = relatedResource.getDataset().getIdentifiableStatisticalResource().getUrn();
                actualRelatedResource = datasetVersionRepository.retrieveLastPublishedVersion(urn);
                expectedRelatedResource = datasetVersionRepository.retrieveLastVersion(urn);
                checkIfRelatedResourceHasAPublishedVersion(actualRelatedResource, expectedRelatedResource, referenceValidFrom, urn, metadataName, exceptionItems);
                break;
            case DATASET_VERSION:
                urn = relatedResource.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn();
                actualRelatedResource = datasetVersionRepository.retrieveByUrn(urn);
                checkIfRelatedResourceValidFromBeforeReferenceValidFrom(actualRelatedResource, referenceValidFrom, metadataName, exceptionItems);
                break;
            case PUBLICATION:
                urn = relatedResource.getPublication().getIdentifiableStatisticalResource().getUrn();
                actualRelatedResource = publicationVersionRepository.retrieveLastPublishedVersion(urn);
                expectedRelatedResource = publicationVersionRepository.retrieveLastVersion(urn);
                checkIfRelatedResourceHasAPublishedVersion(actualRelatedResource, expectedRelatedResource, referenceValidFrom, urn, metadataName, exceptionItems);
                break;
            case PUBLICATION_VERSION:
                urn = relatedResource.getPublicationVersion().getSiemacMetadataStatisticalResource().getUrn();
                actualRelatedResource = publicationVersionRepository.retrieveByUrn(urn);
                checkIfRelatedResourceValidFromBeforeReferenceValidFrom(actualRelatedResource, referenceValidFrom, metadataName, exceptionItems);
                break;
            case QUERY:
                urn = relatedResource.getQuery().getIdentifiableStatisticalResource().getUrn();
                actualRelatedResource = queryVersionRepository.retrieveLastPublishedVersion(urn);
                expectedRelatedResource = queryVersionRepository.retrieveLastVersion(urn);
                checkIfRelatedResourceHasAPublishedVersion(actualRelatedResource, expectedRelatedResource, referenceValidFrom, urn, metadataName, exceptionItems);
                break;
            case QUERY_VERSION:
                urn = relatedResource.getQueryVersion().getLifeCycleStatisticalResource().getUrn();
                actualRelatedResource = queryVersionRepository.retrieveByUrn(urn);
                checkIfRelatedResourceValidFromBeforeReferenceValidFrom(actualRelatedResource, referenceValidFrom, metadataName, exceptionItems);
                break;
            default:
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of relatedResource not supported for relation with other resource");
        }
    }

    private void checkIfRelatedResourceHasAPublishedVersion(HasLifecycle actualRelatedResource, HasLifecycle expectedRelatedResource, DateTime referenceValidFrom, String urn, String metadataName,
            List<MetamacExceptionItem> exceptionItems) {
        if (actualRelatedResource == null) {
            DateTime resourceValidFrom = expectedRelatedResource.getLifeCycleStatisticalResource().getValidFrom();
            if (resourceValidFrom == null || resourceValidFrom.isAfter(referenceValidFrom)) {
                exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.RELATED_RESOURCE_WITHOUT_PUBLISHED_VERSION, metadataName, urn));
            }
        }
    }

    private void checkIfRelatedResourceValidFromBeforeReferenceValidFrom(HasLifecycle resource, DateTime referenceValidFrom, String metadataName, List<MetamacExceptionItem> exceptionItems)
            throws MetamacException {
        DateTime resourceValidFrom = resource.getLifeCycleStatisticalResource().getValidFrom();
        if (resourceValidFrom == null || resourceValidFrom.isAfter(referenceValidFrom)) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.RELATED_RESOURCE_NOT_PUBLISHED, metadataName, resource.getLifeCycleStatisticalResource().getUrn()));
        }
    }
}
