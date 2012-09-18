package org.siemac.metamac.statistical.resources.web.client.dataset.utils;

// TODO: Add real rules not only "true"
public class DatasetClientSecurityUtils {

    public static boolean canCreateDataset() {
        return true;
    }

    public static boolean canUpdateDataset() {
        return true;
    }

    public static boolean canDeleteDataset() {
        return true;
    }
    
    public static boolean canCreateDatasource() {
        return true;
    }
    public static boolean canDeleteDatasource() {
        return true;
    }

}
