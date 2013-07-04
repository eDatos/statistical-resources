package org.siemac.metamac.statistical.resources.core.common.utils;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;


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
}
