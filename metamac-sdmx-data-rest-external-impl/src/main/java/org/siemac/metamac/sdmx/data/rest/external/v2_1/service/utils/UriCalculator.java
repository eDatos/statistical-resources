package org.siemac.metamac.sdmx.data.rest.external.v2_1.service.utils;

import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

public class UriCalculator {

    public static String calculateUriForDataset(DatasetVersion datasetVersion, String apiEndpointV21, String schemesSubPath) {
        String agencyId = datasetVersion.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested();
        String code = datasetVersion.getSiemacMetadataStatisticalResource().getCode();
        String versionLogic = datasetVersion.getSiemacMetadataStatisticalResource().getVersionLogic();
        return toMaintainableArtefactLink(apiEndpointV21, schemesSubPath, agencyId, code, versionLogic);
    }

    public static String calculateUriForCategorisation(Categorisation categorisationVersion, String apiEndpointV21, String schemesSubPath) {
        String agencyId = categorisationVersion.getMaintainer().getCodeNested();
        String code = categorisationVersion.getVersionableStatisticalResource().getCode();
        String versionLogic = categorisationVersion.getVersionableStatisticalResource().getVersionLogic();
        return toMaintainableArtefactLink(apiEndpointV21, schemesSubPath, agencyId, code, versionLogic);
    }

    // API/[ARTEFACT_TYPE]
    // API/[ARTEFACT_TYPE]/{agencyID}
    // API/[ARTEFACT_TYPE]/{agencyID}/{resourceID}
    // API/[ARTEFACT_TYPE]/{agencyID}/{resourceID}/{version}
    private static String toMaintainableArtefactLink(String apiEndpointV21, String schemesSubPath, String agencyID, String resourceID, String version) {
        String link = RestUtils.createLink(apiEndpointV21, schemesSubPath);
        if (agencyID != null) {
            link = RestUtils.createLink(link, agencyID);
            if (resourceID != null) {
                link = RestUtils.createLink(link, resourceID);
                if (version != null) {
                    link = RestUtils.createLink(link, version);
                }
            }
        }
        return link;
    }

}
