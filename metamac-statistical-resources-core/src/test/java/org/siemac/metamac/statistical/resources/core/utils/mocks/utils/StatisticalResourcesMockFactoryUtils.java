package org.siemac.metamac.statistical.resources.core.utils.mocks.utils;

import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public class StatisticalResourcesMockFactoryUtils {

    public static RelatedResource createRelatedResource(DatasetVersion datasetVersion) {
        RelatedResource relatedResource = new RelatedResource(TypeRelatedResourceEnum.DATASET_VERSION);
        relatedResource.setDatasetVersion(datasetVersion);
        return relatedResource;
    }

    public static RelatedResource createRelatedResource(PublicationVersion pubVersion) {
        RelatedResource relatedResource = new RelatedResource(TypeRelatedResourceEnum.PUBLICATION_VERSION);
        relatedResource.setPublicationVersion(pubVersion);
        return relatedResource;
    }

}
