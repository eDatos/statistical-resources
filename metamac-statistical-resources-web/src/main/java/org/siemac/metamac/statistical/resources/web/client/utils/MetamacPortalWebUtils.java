package org.siemac.metamac.statistical.resources.web.client.utils;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;

public class MetamacPortalWebUtils {

    private static final String DATASETS      = "datasets";
    private static final String PUBLICATIONS  = "collections";
    private static final String QUERIES       = "queries";
    private static final String URL_SEPARATOR = "/";

    public static String buildDatasetVersionUrl(DatasetVersionDto datasetVersionDto) {
        return buildLifecycleResourceUrl(DATASETS, datasetVersionDto);
    }

    public static String buildPublicationVersionUrl(PublicationVersionDto publicationVersionDto) {
        return buildLifecycleResourceUrl(PUBLICATIONS, publicationVersionDto);
    }

    public static String buildQueryVersionUrl(QueryVersionDto queryVersionDto) {
        return buildLifecycleResourceUrlWithoutVersion(QUERIES, queryVersionDto);
    }

    private static String buildLifecycleResourceUrl(String urlToken, LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        StringBuilder builder = new StringBuilder();

        String urlWithoutVersion = buildLifecycleResourceUrlWithoutVersion(urlToken, lifeCycleStatisticalResourceDto);
        if (!StringUtils.isEmpty(urlWithoutVersion)) {
            String version = lifeCycleStatisticalResourceDto.getVersionLogic();
            builder.append(urlWithoutVersion).append(URL_SEPARATOR).append(version);
        }

        return builder.toString();
    }

    private static String buildLifecycleResourceUrlWithoutVersion(String urlToken, LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        StringBuilder builder = new StringBuilder();
        String urlBase = CommonUtils.getMetamacPortalBaseUrl();

        if (!StringUtils.isBlank(urlBase) && lifeCycleStatisticalResourceDto != null) {

            String maintainerCode = lifeCycleStatisticalResourceDto.getMaintainer() != null ? lifeCycleStatisticalResourceDto.getMaintainer().getCode() : null;
            String Code = lifeCycleStatisticalResourceDto.getCode();

            builder.append(urlBase).append(URL_SEPARATOR);
            builder.append(urlToken).append(URL_SEPARATOR);
            builder.append(maintainerCode).append(URL_SEPARATOR);
            builder.append(Code);
        }

        return builder.toString();
    }
}
