package org.siemac.metamac.statistical.resources.core.base.utils;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.exception.DatasetVersionNotFoundException;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.exception.PublicationVersionNotFoundException;


public class RelatedResourceUtils {

    public static NameableStatisticalResource retrieveNameableResourceLinkedToRelatedResource(RelatedResource source) {
        switch (source.getType()) {
            case DATASET_VERSION:
                return source.getDatasetVersion().getSiemacMetadataStatisticalResource();
            case PUBLICATION_VERSION:
                return source.getPublicationVersion().getSiemacMetadataStatisticalResource();
        }
        return null;
    }
    
    public static Long retrieveResourceIdLinkedToRelatedResource(RelatedResource source) {
        switch (source.getType()) {
            case DATASET_VERSION:
                return source.getDatasetVersion() != null? source.getDatasetVersion().getId() : null;
            case PUBLICATION_VERSION:
                return source.getPublicationVersion() != null? source.getPublicationVersion().getId() : null;
        }
        return null;
    }
    

}
