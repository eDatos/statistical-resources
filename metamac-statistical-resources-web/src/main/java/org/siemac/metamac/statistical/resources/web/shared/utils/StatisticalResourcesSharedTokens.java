package org.siemac.metamac.statistical.resources.web.shared.utils;

import com.gwtplatform.dispatch.shared.SecurityCookie;

public class StatisticalResourcesSharedTokens extends org.siemac.metamac.web.common.shared.utils.SharedTokens {

    //
    // SECURITY
    //

    @SecurityCookie
    public static final String securityCookieName               = "securityCookieName";

    //
    // FILE UPLOAD PARAMETERS
    //

    public static final String UPLOAD_PARAM_OPERATION_URN       = "operation-urn";
    public static final String UPLOAD_PARAM_DATASET_VERSION_URN = "dataset-version-urn";
    public static final String UPLOAD_PARAM_DIM_PREFIX          = "dim-";
}
