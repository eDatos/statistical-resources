package org.siemac.metamac.statistical.resources.web.client.utils;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.web.common.shared.constants.CommonSharedConstants;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public class MetamacPortalWebUtils {

    private static final String PAGE_DATA_RESOURCE                 = "data.html";
    private static final String PAGE_COLLECTION_RESOURCE           = "collection.html";
    private static final String URL_SINGLEPAGE_RESOURCE_DATASETS   = "datasets";
    private static final String URL_SINGLEPAGE_RESOURCE_QUERIES    = "queries";
    private static final String URL_SEPARATOR                      = "/";
    private static final String URL_SINGLEPAGE_SEPARATOR           = "#";
    private static final String URL_QUERY_SEPARATOR                = "?";
    private static final String URL_QUERY_EQUALS                   = "=";
    private static final String URL_QUERY_AND                      = "&";
    private static final String URL_QUERY_PARAMETER_RESOURCEID     = "resourceId";
    private static final String URL_QUERY_PARAMETER_AGENCYID       = "agencyId";
    private static final String URL_QUERY_PARAMETER_VERSION        = "version";
    private static final String URL_QUERY_PARAMETER_RESOURCETYPE   = "resourceType";

    private static final String URL_QUERY_RESOURCE_TYPE_DATASET    = "dataset";
    private static final String URL_QUERY_RESOURCE_TYPE_QUERY      = "query";
    private static final String URL_QUERY_RESOURCE_TYPE_COLLECTION = "collection";

    public static String buildDatasetVersionUrl(DatasetVersionDto datasetVersionDto) throws MetamacWebException {
        StringBuilder builder = new StringBuilder();
        builder.append(buildEndpointUrl());
        builder.append(PAGE_DATA_RESOURCE);
        builder.append(URL_QUERY_SEPARATOR);
        builder.append(buildQueryParametersForVersionableResource(datasetVersionDto, StatisticalResourceTypeEnum.DATASET));
        builder.append(URL_SINGLEPAGE_SEPARATOR);

        return builder.toString();
    }

    public static String buildQueryVersionUrl(QueryVersionDto queryVersionDto) throws MetamacWebException {
        StringBuilder builder = new StringBuilder();
        builder.append(buildEndpointUrl());
        builder.append(PAGE_DATA_RESOURCE);
        builder.append(URL_QUERY_SEPARATOR);
        builder.append(buildQueryParametersForNotVersionableResource(queryVersionDto, StatisticalResourceTypeEnum.QUERY));
        builder.append(URL_SINGLEPAGE_SEPARATOR);

        return builder.toString();
    }

    public static String buildPublicationVersionUrl(PublicationVersionDto publicationVersionDto) throws MetamacWebException {
        StringBuilder builder = new StringBuilder();
        builder.append(buildEndpointUrl());
        builder.append(PAGE_COLLECTION_RESOURCE);
        builder.append(URL_QUERY_SEPARATOR);
        builder.append(buildQueryParametersForNotVersionableResource(publicationVersionDto, StatisticalResourceTypeEnum.COLLECTION));

        return builder.toString();
    }

    public static String buildMultidatasetVersionUrl(MultidatasetVersionDto multidatasetVersionDto) throws MetamacWebException {
        StringBuilder builder = new StringBuilder();
        builder.append(buildEndpointUrl());
        builder.append(PAGE_DATA_RESOURCE);
        builder.append(URL_QUERY_SEPARATOR);
        builder.append(buildQueryParametersForNotVersionableResource(multidatasetVersionDto, StatisticalResourceTypeEnum.MULTIDATASET));

        return builder.toString();
    }

    private static String buildEndpointUrl() {
        String urlBase = CommonUtils.getMetamacPortalBaseUrl();
        StringBuilder builder = new StringBuilder().append(urlBase);

        if (!StringUtils.isBlank(urlBase) && !urlBase.endsWith(URL_SEPARATOR)) {
            builder.append(URL_SEPARATOR);
        }
        return builder.toString();
    }

    private static String buildQueryParametersForVersionableResource(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto, StatisticalResourceTypeEnum type) throws MetamacWebException {
        StringBuilder builder = new StringBuilder();

        if (lifeCycleStatisticalResourceDto != null) {
            String parametersForNotVersionableResource = buildQueryParametersForNotVersionableResource(lifeCycleStatisticalResourceDto, type);
            builder.append(parametersForNotVersionableResource);
            builder.append(URL_QUERY_AND);
            builder.append(URL_QUERY_PARAMETER_VERSION);
            builder.append(URL_QUERY_EQUALS);
            builder.append(lifeCycleStatisticalResourceDto.getVersionLogic());
        }

        return builder.toString();

    }

    private static String buildQueryParametersForNotVersionableResource(LifeCycleStatisticalResourceDto lifeCycleStatisticalResourceDto, StatisticalResourceTypeEnum type) throws MetamacWebException {
        StringBuilder builder = new StringBuilder();

        if (lifeCycleStatisticalResourceDto != null) {

            String maintainerCode = lifeCycleStatisticalResourceDto.getMaintainer() != null ? lifeCycleStatisticalResourceDto.getMaintainer().getCode() : null;
            String code = lifeCycleStatisticalResourceDto.getCode();
            String resourceType = determinateResourceType(type);

            builder.append(URL_QUERY_PARAMETER_RESOURCETYPE);
            builder.append(URL_QUERY_EQUALS);
            builder.append(resourceType);
            builder.append(URL_QUERY_AND);
            builder.append(URL_QUERY_PARAMETER_AGENCYID);
            builder.append(URL_QUERY_EQUALS);
            builder.append(maintainerCode);
            builder.append(URL_QUERY_AND);
            builder.append(URL_QUERY_PARAMETER_RESOURCEID);
            builder.append(URL_QUERY_EQUALS);
            builder.append(code);
        }
        return builder.toString();

    }

    private static String determinateResourceType(StatisticalResourceTypeEnum type) throws MetamacWebException {
        switch (type) {
            case QUERY:
                return URL_QUERY_RESOURCE_TYPE_QUERY;
            case DATASET:
                return URL_QUERY_RESOURCE_TYPE_DATASET;
            case COLLECTION:
                return URL_QUERY_RESOURCE_TYPE_COLLECTION;

            default:
                throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, "StatisticalResourceTypeEnum " + type + " not valid.");
        }
    }
}
