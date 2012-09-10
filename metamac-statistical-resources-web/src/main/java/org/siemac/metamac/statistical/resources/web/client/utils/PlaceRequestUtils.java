package org.siemac.metamac.statistical.resources.web.client.utils;

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

    public static boolean isNameTokenInPlaceHierarchy(PlaceManager placeManager, String nameToken) {
        for (PlaceRequest placeReq : placeManager.getCurrentPlaceHierarchy()) {
            if (nameToken.equals(placeReq.getNameToken())) {
                return true;
            }
        }
        return false;
    }

}
