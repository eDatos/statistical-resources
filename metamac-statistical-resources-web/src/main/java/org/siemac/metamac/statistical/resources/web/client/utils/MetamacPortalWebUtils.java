package org.siemac.metamac.statistical.resources.web.client.utils;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;

public class MetamacPortalWebUtils {

    private static final String DATASETS      = "datasets";
    private static final String PUBLICATIONS  = "collections";
    private static final String URL_SEPARATOR = "/";

    public static String buildDatasetVersionUrl(DatasetVersionDto datasetVersionDto) {
        return buildLifecycleResourceUrl(DATASETS, datasetVersionDto);
    }

    public static String buildPublicationVersionUrl(PublicationVersionDto publicationVersionDto) {
        return buildLifecycleResourceUrl(PUBLICATIONS, publicationVersionDto);
    }

    private static String buildLifecycleResourceUrl(String urlToken, LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        StringBuilder builder = new StringBuilder();
        String urlBase = CommonUtils.getMetamacPortalBaseUrl();

        if (!StringUtils.isBlank(urlBase) && lifeCycleStatisticalResourceDto != null) {

            String maintainerCode = lifeCycleStatisticalResourceDto.getMaintainer() != null ? lifeCycleStatisticalResourceDto.getMaintainer().getCode() : null;
            String Code = lifeCycleStatisticalResourceDto.getCode();
            String version = lifeCycleStatisticalResourceDto.getVersionLogic();

            builder.append(urlBase).append(URL_SEPARATOR);
            builder.append(urlToken).append(URL_SEPARATOR);
            builder.append(maintainerCode).append(URL_SEPARATOR);
            builder.append(Code).append(URL_SEPARATOR);
            builder.append(version);
        }

        return builder.toString();
    }
}
