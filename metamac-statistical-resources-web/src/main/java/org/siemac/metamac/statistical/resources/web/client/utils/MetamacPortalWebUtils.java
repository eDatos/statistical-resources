package org.siemac.metamac.statistical.resources.web.client.utils;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;

public class MetamacPortalWebUtils {

    private static final String DATASETS      = "datasets";
    private static final String URL_SEPARATOR = "/";

    public static String buildDatasetUrl(DatasetVersionDto datasetVersionDto) {
        StringBuilder builder = new StringBuilder();
        String urlBase = CommonUtils.getMetamacPortalBaseUrl();

        if (!StringUtils.isBlank(urlBase) && datasetVersionDto != null) {

            String maintainerCode = datasetVersionDto.getMaintainer() != null ? datasetVersionDto.getMaintainer().getCode() : null;
            String datasetCode = datasetVersionDto.getCode();
            String version = datasetVersionDto.getVersionLogic();

            builder.append(urlBase).append(URL_SEPARATOR);
            builder.append(DATASETS).append(URL_SEPARATOR);
            builder.append(maintainerCode).append(URL_SEPARATOR);
            builder.append(datasetCode).append(URL_SEPARATOR);
            builder.append(version);
        }

        return builder.toString();
    }
}
