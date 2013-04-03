package org.siemac.metamac.statistical.resources.core.utils.mocks.utils;

import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.utils.BaseVersioningCopyUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.dbunit.DBUnitMockPersister;

public class StatisticalResourcesMockFactoryUtils {

    public static RelatedResource createRelatedResource(SiemacMetadataStatisticalResource resource, TypeRelatedResourceEnum type) {
        populateUriAndUrnIfEmpty(resource);
        RelatedResource relatedResource = new RelatedResource(resource.getCode(), resource.getUrn(), type);
        relatedResource.setTitle(BaseVersioningCopyUtils.copyInternationalString(resource.getTitle()));
        return relatedResource;
    }

    public static RelatedResource createRelatedResource(DatasetVersion datasetVersion) {
        return createRelatedResource(datasetVersion.getSiemacMetadataStatisticalResource(), TypeRelatedResourceEnum.DATASET_VERSION);
    }

    public static RelatedResource createRelatedResource(PublicationVersion pubVersion) {
        return createRelatedResource(pubVersion.getSiemacMetadataStatisticalResource(), TypeRelatedResourceEnum.PUBLICATION_VERSION);
    }

    private static void populateUriAndUrnIfEmpty(SiemacMetadataStatisticalResource resource) {
        if (resource.getUri() == null) {
            resource.setUri(DBUnitMockPersister.getUriMock());
        }
        if (resource.getUrn() == null) {
            resource.setUrn(DBUnitMockPersister.getUrnMock());
        }
    }
}
