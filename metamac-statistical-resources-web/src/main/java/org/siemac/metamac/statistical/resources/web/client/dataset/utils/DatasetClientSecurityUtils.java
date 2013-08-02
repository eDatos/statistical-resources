package org.siemac.metamac.statistical.resources.web.client.dataset.utils;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

// TODO: Add real rules not only "true"
// TODO take into account the metadata isTaskInBackground to avoid to execute some actions!
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

    public static boolean canImportDatasources(DatasetVersionDto datasetVersionDto) {
        if (!ProcStatusEnum.DRAFT.equals(datasetVersionDto.getProcStatus())) {
            // Datasources can only be imported in datasets in DRAFT
            return false;
        }
        return true;
    }

    // this method will be called by the button that import datasources in many datasets
    public static boolean canImportDatasources() {
        return true;
    }

    public static boolean canDeleteDatasource() {
        return true;
    }
}
