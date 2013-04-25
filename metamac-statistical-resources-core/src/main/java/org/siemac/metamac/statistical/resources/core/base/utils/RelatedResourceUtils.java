package org.siemac.metamac.statistical.resources.core.base.utils;

import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;


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
