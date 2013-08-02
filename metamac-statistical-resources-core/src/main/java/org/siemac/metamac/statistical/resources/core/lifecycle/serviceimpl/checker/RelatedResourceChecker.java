package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker;

import java.util.List;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.HasLifecycle;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelatedResourceChecker {

    @Autowired
    private DatasetVersionRepository datasetVersionRepository;

    public void checkRelatedResourcesExternallyPublished(List<RelatedResource> relatedResources, DateTime referenceValidFrom, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        for (RelatedResource relatedResource : relatedResources) {
            checkRelatedResourceExternallyPublished(relatedResource, referenceValidFrom, metadataName, exceptionItems);
        }
    }
    
    public void checkRelatedResourceExternallyPublished(RelatedResource relatedResource, DateTime referenceValidFrom, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        
        switch (relatedResource.getType()) {
            case DATASET:
                
                break;

            case DATASET_VERSION:
                String urn = relatedResource.getDatasetVersion().getSiemacMetadataStatisticalResource().getUrn();
                HasLifecycle result = datasetVersionRepository.retrieveByUrn(urn);
                checkIfRelatedResourceValidFromBeforeReferenceValidFrom(result, referenceValidFrom, metadataName, exceptionItems);
                break;
                
            case PUBLICATION:
                break;
                
            case PUBLICATION_VERSION:
                break;
            case QUERY: 
                break;
            case QUERY_VERSION:
                break;
            default:
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of relatedResource not supported for relation with other resource");
        }
    }
    
    private void checkIfRelatedResourceValidFromBeforeReferenceValidFrom(HasLifecycle resource, DateTime referenceValidFrom, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        DateTime resourceValidFrom = resource.getLifeCycleStatisticalResource().getValidFrom();
        if (resourceValidFrom == null || resourceValidFrom.isAfter(referenceValidFrom)) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.RELATED_RESOURCE_NOT_PUBLISHED, metadataName, resource.getLifeCycleStatisticalResource().getUrn()));
        }
    }
}
