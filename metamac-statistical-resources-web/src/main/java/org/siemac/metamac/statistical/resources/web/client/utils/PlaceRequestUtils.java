package org.siemac.metamac.statistical.resources.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.PlaceRequestParams;

import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class PlaceRequestUtils {

    public static String getOperationParamFromUrl(PlaceManager placeManager) {
        for (PlaceRequest request : placeManager.getCurrentPlaceHierarchy()) {
            if (NameTokens.operationPage.equals(request.getNameToken())) {
                return request.getParameter(PlaceRequestParams.operationParam, null);
            }
        }
        return null;
    }

    public static String getDatasetParamFromUrl(PlaceManager placeManager) {
        for (PlaceRequest request : placeManager.getCurrentPlaceHierarchy()) {
            if (NameTokens.datasetPage.equals(request.getNameToken())) {
                return request.getParameter(PlaceRequestParams.datasetParam, null);
            }
        }
        return null;
    }

    public static String getPublicationParamFromUrl(PlaceManager placeManager) {
        for (PlaceRequest request : placeManager.getCurrentPlaceHierarchy()) {
            if (NameTokens.publicationPage.equals(request.getNameToken())) {
                return request.getParameter(PlaceRequestParams.collectionParam, null);
            }
        }
        return null;
    }

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

}
