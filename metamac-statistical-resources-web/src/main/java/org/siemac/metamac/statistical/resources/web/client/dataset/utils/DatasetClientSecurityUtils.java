package org.siemac.metamac.statistical.resources.web.client.dataset.utils;

import org.siemac.metamac.core.common.util.shared.BooleanUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.security.shared.SharedDatasetsSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.base.utils.BaseClientSecurityUtils;

// TODO take into account the metadata isTaskInBackground to avoid to execute some actions!
public class DatasetClientSecurityUtils extends BaseClientSecurityUtils {

    // ------------------------------------------------------------------------
    // DATASETS VERSIONS
    // ------------------------------------------------------------------------

    public static boolean canCreateDataset() {
        return SharedDatasetsSecurityUtils.canCreateDataset(getMetamacPrincipal());
    }

    public static boolean canUpdateDatasetVersion(DatasetVersionDto datasetVersionDto) {
        if (BooleanUtils.isTrue(datasetVersionDto.getIsTaskInBackground())) {
            return false;
        }
        return SharedDatasetsSecurityUtils.canUpdateDatasetVersion(getMetamacPrincipal());
    }

    public static boolean canDeleteDatasetVersion(DatasetVersionDto datasetVersionDto) {
        return canDeleteDatasetVersion(datasetVersionDto.getIsTaskInBackground(), datasetVersionDto.getProcStatus());
    }

    public static boolean canDeleteDatasetVersion(boolean isTaskInBackground, ProcStatusEnum procStatus) {
        if (BooleanUtils.isTrue(isTaskInBackground)) {
            return false;
        }
        if (ProcStatusEnum.PUBLISHED.equals(procStatus)) {
            return false;
        }
        return SharedDatasetsSecurityUtils.canDeleteDatasetVersion(getMetamacPrincipal());
    }

    public static boolean canVersionDataset(DatasetVersionDto datasetVersionDto) {
        return canVersionDataset(datasetVersionDto.getIsTaskInBackground());
    }

    public static boolean canVersionDataset(boolean isTaskInBackground) {
        if (BooleanUtils.isTrue(isTaskInBackground)) {
            return false;
        }
        return SharedDatasetsSecurityUtils.canVersionDataset(getMetamacPrincipal());
    }

    public static boolean canSendDatasetVersionToProductionValidation(DatasetVersionDto datasetVersionDto) {
        return canSendDatasetVersionToProductionValidation(datasetVersionDto.getIsTaskInBackground());
    }

    public static boolean canSendDatasetVersionToProductionValidation(boolean isTaskInBackground) {
        if (BooleanUtils.isTrue(isTaskInBackground)) {
            return false;
        }
        return SharedDatasetsSecurityUtils.canSendDatasetVersionToProductionValidation(getMetamacPrincipal());
    }

    public static boolean canSendDatasetVersionToDiffusionValidation(DatasetVersionDto datasetVersionDto) {
        return canSendDatasetVersionToDiffusionValidation(datasetVersionDto.getIsTaskInBackground());
    }

    public static boolean canSendDatasetVersionToDiffusionValidation(boolean isTaskInBackground) {
        if (BooleanUtils.isTrue(isTaskInBackground)) {
            return false;
        }
        return SharedDatasetsSecurityUtils.canSendDatasetVersionToDiffusionValidation(getMetamacPrincipal());
    }

    public static boolean canSendDatasetVersionToValidationRejected(DatasetVersionDto datasetVersionDto) {
        return canSendDatasetVersionToValidationRejected(datasetVersionDto.getIsTaskInBackground());
    }

    public static boolean canSendDatasetVersionToValidationRejected(boolean isTaskInBackground) {
        if (BooleanUtils.isTrue(isTaskInBackground)) {
            return false;
        }
        return SharedDatasetsSecurityUtils.canSendDatasetVersionToValidationRejected(getMetamacPrincipal());
    }

    public static boolean canPublishDatasetVersion(DatasetVersionDto datasetVersionDto) {
        return canPublishDatasetVersion(datasetVersionDto.getIsTaskInBackground());
    }

    public static boolean canPublishDatasetVersion(boolean isTaskInBackground) {
        if (BooleanUtils.isTrue(isTaskInBackground)) {
            return false;
        }
        return true; // TODO
    }

    // ------------------------------------------------------------------------
    // DATASOURCES
    // ------------------------------------------------------------------------

    public static boolean canDeleteDatasource(DatasetVersionDto datasetVersionDto) {
        if (BooleanUtils.isTrue(datasetVersionDto.getIsTaskInBackground())) {
            return false;
        }
        return SharedDatasetsSecurityUtils.canDeleteDatasource(getMetamacPrincipal());
    }

    public static boolean canImportDatasourcesInDatasetVersion(DatasetVersionDto datasetVersionDto) {
        if (BooleanUtils.isTrue(datasetVersionDto.getIsTaskInBackground())) {
            return false;
        }

        // Datasources can only be imported in datasets in DRAFT or in VALIDATION_REJECTED
        if (!ProcStatusEnum.DRAFT.equals(datasetVersionDto.getProcStatus()) && !ProcStatusEnum.VALIDATION_REJECTED.equals(datasetVersionDto.getProcStatus())) {
            return false;
        }
        return SharedDatasetsSecurityUtils.canImportDatasourcesInDatasetVersion(getMetamacPrincipal());
    }

    public static boolean canImportDatasourcesInStatisticalOperation() {
        return SharedDatasetsSecurityUtils.canImportDatasourcesInStatisticalOperation(getMetamacPrincipal());
    }
}
