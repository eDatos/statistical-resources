package org.siemac.metamac.statistical.resources.web.client.utils;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;

public class MetamacPortalWebUtils {

    private static final String PAGE_SINGLEPAGE_RESOURCES      = "view.html";
    private static final String PAGE_COLLECTION_RESOURCE       = "index.html";
    private static final String DATASETS                       = "datasets";
    private static final String QUERIES                        = "queries";
    private static final String URL_SEPARATOR                  = "/";
    private static final String URL_SINGLEPAGE_SEPARATOR       = "#";
    private static final String URL_QUERY_SEPARATOR            = "?";
    private static final String URL_QUERY_EQUALS               = "=";
    private static final String URL_QUERY_AND                  = "&";
    private static final String URL_QUERY_PARAMETER_RESOURCEID = "resourceId";
    private static final String URL_QUERY_PARAMETER_AGENCYID   = "agencyId";

    public static String buildDatasetVersionUrl(DatasetVersionDto datasetVersionDto) {
        return buildSinglepageResourceUrl(DATASETS, datasetVersionDto);
    }

    public static String buildQueryVersionUrl(QueryVersionDto queryVersionDto) {
        return buildSinglepageResourceUrlWithoutVersion(QUERIES, queryVersionDto);
    }

    public static String buildPublicationVersionUrl(PublicationVersionDto publicationVersionDto) {
        String urlWithoutVersion = buildPublicationUrlWithoutVersion(publicationVersionDto);
        return urlWithoutVersion;
    }

    private static String buildSinglepageResourceUrl(String urlToken, LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        StringBuilder builder = new StringBuilder();

        String urlWithoutVersion = buildSinglepageResourceUrlWithoutVersion(urlToken, lifeCycleStatisticalResourceDto);
        if (!StringUtils.isEmpty(urlWithoutVersion)) {
            String version = lifeCycleStatisticalResourceDto.getVersionLogic();
            builder.append(urlWithoutVersion).append(URL_SEPARATOR).append(version);
        }

        return builder.toString();
    }

    private static String buildSinglepageResourceUrlWithoutVersion(String urlToken, LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        String urlBase = CommonUtils.getMetamacPortalBaseUrl();
        StringBuilder builder = new StringBuilder().append(urlBase);

        if (!StringUtils.isBlank(urlBase) && lifeCycleStatisticalResourceDto != null) {

            String maintainerCode = lifeCycleStatisticalResourceDto.getMaintainer() != null ? lifeCycleStatisticalResourceDto.getMaintainer().getCode() : null;
            String Code = lifeCycleStatisticalResourceDto.getCode();

            if (!urlBase.endsWith(URL_SEPARATOR)) {
                builder.append(URL_SEPARATOR);
            }

            builder.append(PAGE_SINGLEPAGE_RESOURCES);
            builder.append(URL_SINGLEPAGE_SEPARATOR);
            builder.append(urlToken);
            builder.append(URL_SEPARATOR);
            builder.append(maintainerCode);
            builder.append(URL_SEPARATOR);
            builder.append(Code);
        }

        return builder.toString();
    }

    private static String buildPublicationUrlWithoutVersion(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto) {
        String urlBase = CommonUtils.getMetamacPortalBaseUrl();
        StringBuilder builder = new StringBuilder().append(urlBase);

        if (!StringUtils.isBlank(urlBase) && lifeCycleStatisticalResourceDto != null) {

            String maintainerCode = lifeCycleStatisticalResourceDto.getMaintainer() != null ? lifeCycleStatisticalResourceDto.getMaintainer().getCode() : null;
            String Code = lifeCycleStatisticalResourceDto.getCode();

            if (!urlBase.endsWith(URL_SEPARATOR)) {
                builder.append(URL_SEPARATOR);
            }

            builder.append(PAGE_COLLECTION_RESOURCE);
            builder.append(URL_QUERY_SEPARATOR);
            builder.append(URL_QUERY_PARAMETER_AGENCYID);
            builder.append(URL_QUERY_EQUALS);
            builder.append(maintainerCode);
            builder.append(URL_QUERY_AND);
            builder.append(URL_QUERY_PARAMETER_RESOURCEID);
            builder.append(URL_QUERY_EQUALS);
            builder.append(Code);
        }

        return builder.toString();
    }
}
