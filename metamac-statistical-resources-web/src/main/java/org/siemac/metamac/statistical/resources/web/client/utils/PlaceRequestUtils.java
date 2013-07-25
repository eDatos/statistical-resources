package org.siemac.metamac.statistical.resources.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class PlaceRequestUtils {

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

    public static PlaceRequest buildRelativePublicationsPlaceRequest() {
        return new PlaceRequest(NameTokens.publicationsListPage);
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

    // ---------------------------------------------------------------------------
    // GENERIC METHODS
    // ---------------------------------------------------------------------------

    public static boolean isNameTokenInPlaceHierarchy(PlaceManager placeManager, String nameToken) {
        for (PlaceRequest placeReq : placeManager.getCurrentPlaceHierarchy()) {
            if (nameToken.equals(placeReq.getNameToken())) {
                return true;
            }
        }
        return false;
    }

    public static List<PlaceRequest> getHierarchyUntilNameToken(PlaceManager placeManager, String nameToken) {
        List<PlaceRequest> filteredHierarchy = new ArrayList<PlaceRequest>();
        List<PlaceRequest> hierarchy = placeManager.getCurrentPlaceHierarchy();
        boolean found = false;
        for (int i = 0; i < hierarchy.size() && !found; i++) {
            PlaceRequest placeReq = hierarchy.get(i);
            if (placeReq.matchesNameToken(nameToken)) {
                found = true;
            }
            filteredHierarchy.add(placeReq);
        }

        return filteredHierarchy;
    }

    public static List<PlaceRequest> buildAbsoluteResourcePlaceRequest(RelatedResourceDto relatedResourceDto) {
        if (relatedResourceDto != null) {
            switch (relatedResourceDto.getType()) {
                case DATASET_VERSION:
                    break;
                case PUBLICATION_VERSION:
                    break;
            }

        }
        // FIXME: se debe generar la ruta absoluta
        return new ArrayList<PlaceRequest>();
    }
}
