package org.siemac.metamac.statistical.resources.web.client.utils;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.utils.shared.StatisticalResourcesUrnParserUtils;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;
import org.siemac.metamac.web.common.client.utils.CommonPlaceRequestUtils;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class PlaceRequestUtils extends CommonPlaceRequestUtils {

    // ---------------------------------------------------------------------------
    // OPERATIONS
    // ---------------------------------------------------------------------------

    public static String getOperationParamFromUrl(PlaceManager placeManager) {
        for (PlaceRequest request : placeManager.getCurrentPlaceHierarchy()) {
            if (NameTokens.operationPage.equals(request.getNameToken())) {
                return request.getParameter(PlaceRequestParams.operationParam, null);
            }
        }
        return null;
    }

    public static PlaceRequest buildRelativeOperationPlaceRequest(String urn) {
        return new PlaceRequest(NameTokens.operationPage).with(PlaceRequestParams.operationParam, UrnUtils.removePrefix(urn));
    }

    public static List<PlaceRequest> buildAbsoluteOperationsPlaceRequest(String urn) {
        List<PlaceRequest> placeRequests = new ArrayList<PlaceRequest>();
        placeRequests.add(new PlaceRequest(NameTokens.operationsListPage));
        return placeRequests;
    }

    public static List<PlaceRequest> buildAbsoluteOperationPlaceRequest(String urn) {
        List<PlaceRequest> placeRequests = buildAbsoluteOperationsPlaceRequest(urn);
        placeRequests.add(buildRelativeOperationPlaceRequest(urn));
        return placeRequests;
    }

    public static String getOperationBreadCrumbTitle(PlaceRequest placeRequest) {
        String operationCode = placeRequest.getParameter(PlaceRequestParams.operationParam, null);
        if (!StringUtils.isBlank(operationCode)) {
            return operationCode;
        }
        return getConstants().breadcrumbOperation();
    }

    // ---------------------------------------------------------------------------
    // DATASETS
    // ---------------------------------------------------------------------------

    public static String getDatasetParamFromUrl(PlaceManager placeManager) {
        for (PlaceRequest request : placeManager.getCurrentPlaceHierarchy()) {
            if (NameTokens.datasetPage.equals(request.getNameToken())) {
                return request.getParameter(PlaceRequestParams.datasetParam, null);
            }
        }
        return null;
    }

    public static PlaceRequest buildRelativeDatasetPlaceRequest(String urn) {
        return new PlaceRequest(NameTokens.datasetPage).with(PlaceRequestParams.datasetParam, UrnUtils.removePrefix(urn));
    }

    public static PlaceRequest buildRelativeDatasetsPlaceRequest() {
        return new PlaceRequest(NameTokens.datasetsListPage);
    }

    public static List<PlaceRequest> buildAbsoluteDatasetsPlaceRequest(String operationUrn) {
        List<PlaceRequest> placeRequests = buildAbsoluteOperationPlaceRequest(operationUrn);
        placeRequests.add(buildRelativeDatasetsPlaceRequest());
        return placeRequests;
    }

    public static List<PlaceRequest> buildAbsoluteDatasetPlaceRequest(String operationUrn, String datasetUrn) {
        List<PlaceRequest> placeRequests = buildAbsoluteDatasetsPlaceRequest(operationUrn);
        placeRequests.add(buildRelativeDatasetPlaceRequest(datasetUrn));
        return placeRequests;
    }

    public static String getDatasetBreadCrumbTitle(PlaceRequest placeRequest) {
        String urnWithoutPrefix = placeRequest.getParameter(PlaceRequestParams.datasetParam, null);
        if (!StringUtils.isBlank(urnWithoutPrefix)) {
            String datasetCode = StatisticalResourcesUrnParserUtils.getDatasetVersionCodeFromUrnWithoutPrefix(urnWithoutPrefix);
            if (!StringUtils.isBlank(datasetCode)) {
                return datasetCode;
            }
        }
        return getConstants().breadcrumbDataset();
    }

    // ---------------------------------------------------------------------------
    // PUBLICATIONS
    // ---------------------------------------------------------------------------

    public static String getPublicationParamFromUrl(PlaceManager placeManager) {
        for (PlaceRequest request : placeManager.getCurrentPlaceHierarchy()) {
            if (NameTokens.publicationPage.equals(request.getNameToken())) {
                return request.getParameter(PlaceRequestParams.publicationParam, null);
            }
        }
        return null;
    }

    public static PlaceRequest buildRelativePublicationPlaceRequest(String urn) {
        return new PlaceRequest(NameTokens.publicationPage).with(PlaceRequestParams.publicationParam, UrnUtils.removePrefix(urn));
    }

    public static PlaceRequest buildRelativePublicationsPlaceRequest() {
        return new PlaceRequest(NameTokens.publicationsListPage);
    }

    public static List<PlaceRequest> buildAbsolutePublicationsPlaceRequest(String operationUrn) {
        List<PlaceRequest> placeRequests = buildAbsoluteOperationPlaceRequest(operationUrn);
        placeRequests.add(buildRelativePublicationsPlaceRequest());
        return placeRequests;
    }

    public static List<PlaceRequest> buildAbsolutePublicationPlaceRequest(String operationUrn, String publicationUrn) {
        List<PlaceRequest> placeRequests = buildAbsolutePublicationsPlaceRequest(operationUrn);
        placeRequests.add(buildRelativePublicationPlaceRequest(publicationUrn));
        return placeRequests;
    }

    public static String getPublicationBreadCrumbTitle(PlaceRequest placeRequest) {
        String urnWithoutPrefix = placeRequest.getParameter(PlaceRequestParams.publicationParam, null);
        if (!StringUtils.isBlank(urnWithoutPrefix)) {
            String publicationCode = StatisticalResourcesUrnParserUtils.getPublicationVersionCodeFromUrnWithoutPrefix(urnWithoutPrefix);
            if (!StringUtils.isBlank(publicationCode)) {
                return publicationCode;
            }
        }
        return getConstants().breadcrumbPublication();
    }

    // ---------------------------------------------------------------------------
    // QUERIES
    // ---------------------------------------------------------------------------

    public static String getQueryParamFromUrl(PlaceManager placeManager) {
        for (PlaceRequest request : placeManager.getCurrentPlaceHierarchy()) {
            if (NameTokens.queryPage.equals(request.getNameToken())) {
                return request.getParameter(PlaceRequestParams.queryParam, null);
            }
        }
        return null;
    }

    public static PlaceRequest buildRelativeQueriesPlaceRequest() {
        return new PlaceRequest(NameTokens.queriesListPage);
    }

    public static PlaceRequest buildRelativeQueryPlaceRequest(String urn) {
        return new PlaceRequest(NameTokens.queryPage).with(PlaceRequestParams.queryParam, UrnUtils.removePrefix(urn));
    }

    public static List<PlaceRequest> buildAbsoluteQueriesPlaceRequest(String operationUrn) {
        List<PlaceRequest> placeRequests = buildAbsoluteOperationPlaceRequest(operationUrn);
        placeRequests.add(buildRelativeQueriesPlaceRequest());
        return placeRequests;
    }

    public static List<PlaceRequest> buildAbsoluteQueryPlaceRequest(String operationUrn, String queryUrn) {
        List<PlaceRequest> placeRequests = buildAbsoluteQueriesPlaceRequest(operationUrn);
        placeRequests.add(buildRelativeQueryPlaceRequest(queryUrn));
        return placeRequests;
    }

    public static String getQueryBreadCrumbTitle(PlaceRequest placeRequest) {
        String urnWithoutPrefix = placeRequest.getParameter(PlaceRequestParams.queryParam, null);
        if (!StringUtils.isBlank(urnWithoutPrefix)) {
            String queryCode = StatisticalResourcesUrnParserUtils.getQueryVersionCodeFromUrnWithoutPrefix(urnWithoutPrefix);
            if (!StringUtils.isBlank(queryCode)) {
                return queryCode;
            }
        }
        return getConstants().breadcrumbQuery();
    }

    // ---------------------------------------------------------------------------
    // GENERIC METHODS
    // ---------------------------------------------------------------------------

    public static List<PlaceRequest> buildAbsoluteResourcePlaceRequest(RelatedResourceDto relatedResourceDto) {
        if (relatedResourceDto != null) {
            String urn = relatedResourceDto.getUrn();
            switch (relatedResourceDto.getType()) {
                case DATASET_VERSION:
                    if (StatisticalResourcesUrnParserUtils.isDatasetUrn(urn)) {
                        return buildAbsoluteDatasetPlaceRequest(relatedResourceDto.getStatisticalOperationUrn(), urn);
                    }
                    break;
                case PUBLICATION_VERSION:
                    if (StatisticalResourcesUrnParserUtils.isPublicationUrn(urn)) {
                        return buildAbsolutePublicationPlaceRequest(relatedResourceDto.getStatisticalOperationUrn(), urn);
                    }
                    break;
                case QUERY_VERSION:
                    if (StatisticalResourcesUrnParserUtils.isQueryUrn(urn)) {
                        return buildAbsoluteQueryPlaceRequest(relatedResourceDto.getStatisticalOperationUrn(), urn);
                    }
                    break;
            }
        }
        return new ArrayList<PlaceRequest>();
    }
}
