package org.siemac.metamac.rest.statistical_resources.v1_0.collection.utils;

import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class CollectionsRestDoMocks {

    public StatisticalResourcesPersistedDoMocks coreDoMocks;

    public CollectionsRestDoMocks(StatisticalResourcesPersistedDoMocks coreDoMocks) {
        this.coreDoMocks = coreDoMocks;
    }

    public PublicationVersion mockPublicationVersionBasic(String agencyID, String resourceID, String version) {
        PublicationVersion target = coreDoMocks.mockPublicationVersion();
        target.getSiemacMetadataStatisticalResource().setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Collection=" + agencyID + ":" + resourceID + "(" + version + ")");
        target.getSiemacMetadataStatisticalResource().getMaintainer().setCodeNested(agencyID);
        target.getSiemacMetadataStatisticalResource().setCode(resourceID);
        target.getSiemacMetadataStatisticalResource().setVersionLogic(version);
        return target;
    }

    public PublicationVersion mockPublicationVersion(String agencyID, String resourceID, String version) {
        PublicationVersion target = mockPublicationVersionBasic(agencyID, resourceID, version);
        return target;
    }

}