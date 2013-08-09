package org.siemac.metamac.sdmx.data.rest.external.v2_1.service.utils;

import org.siemac.metamac.rest.utils.RestUtils;

public class UriCalculator {

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
