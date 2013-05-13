package org.siemac.metamac.statistical.resources.core.common.utils;

import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;


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
    

}
