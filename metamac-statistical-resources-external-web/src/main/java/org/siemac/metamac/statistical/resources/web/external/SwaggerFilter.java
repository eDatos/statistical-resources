package org.siemac.metamac.statistical.resources.web.external;

import org.siemac.metamac.core.common.util.swagger.AbstractSwaggerFilter;

public class SwaggerFilter extends AbstractSwaggerFilter {

    @Override
    protected String[] getSupportedApiVersions() {
        return new String[]{"/v1.0/"};
    }
}
