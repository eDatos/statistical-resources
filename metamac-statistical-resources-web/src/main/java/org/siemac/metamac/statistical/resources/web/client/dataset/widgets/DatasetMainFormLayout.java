package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.LifecycleMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;

public class DatasetMainFormLayout extends LifecycleMainFormLayout {

    private DatasetVersionDto datasetVersionDto;

    public DatasetMainFormLayout() {
        super();
        common();
    }

    public DatasetMainFormLayout(boolean canEdit) {
        super(canEdit);
        common();
    }

    private void common() {

    }

    public void setDatasetVersion(DatasetVersionDto datasetVersionDto) {
        this.datasetVersionDto = datasetVersionDto;
        setCanEdit(DatasetClientSecurityUtils.canUpdateDatasetVersion(datasetVersionDto));
        setCanDelete(DatasetClientSecurityUtils.canDeleteDatasetVersion(datasetVersionDto));
        updatePublishSection(datasetVersionDto.getProcStatus());
    }

    //
    // SECURITY
    //

    @Override
    protected boolean canSendToProductionValidation() {
        return DatasetClientSecurityUtils.canSendDatasetVersionToProductionValidation(datasetVersionDto);
    }

    @Override
    protected boolean canSendToDiffusionValidation() {
        return DatasetClientSecurityUtils.canSendDatasetVersionToDiffusionValidation(datasetVersionDto);
    }

    @Override
    protected boolean canRejectValidation() {
        return DatasetClientSecurityUtils.canSendDatasetVersionToValidationRejected(datasetVersionDto);
    }

    @Override
    protected boolean canPublish() {
        return DatasetClientSecurityUtils.canPublishDatasetVersion(datasetVersionDto);
    }

    @Override
    protected boolean canProgramPublication() {
        return DatasetClientSecurityUtils.canPublishDatasetVersion(datasetVersionDto);
    }

    @Override
    protected boolean canCancelProgrammedPublication() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected boolean canVersioning() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected boolean canPreviewData() {
        // TODO Security
        return true;
    }
}
