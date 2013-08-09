package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.LifecycleMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.MainFormLayoutButton;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public class DatasetMainFormLayout extends LifecycleMainFormLayout {

    private MainFormLayoutButton preview;

    private DatasetVersionDto    datasetVersionDto;

    public DatasetMainFormLayout() {
        super();
        common();
    }

    public DatasetMainFormLayout(boolean canEdit) {
        super(canEdit);
        common();
    }

    private void common() {
        preview = new MainFormLayoutButton(getConstants().actionPreviewData(), GlobalResources.RESOURCE.preview().getURL());
        toolStrip.addButton(preview);
    }

    public void setDatasetVersion(DatasetVersionDto datasetVersionDto) {
        this.datasetVersionDto = datasetVersionDto;
        setCanEdit(DatasetClientSecurityUtils.canUpdateDatasetVersion(datasetVersionDto));
        setCanDelete(DatasetClientSecurityUtils.canDeleteDatasetVersion(datasetVersionDto));
        updatePublishSection(datasetVersionDto.getProcStatus());
    }

    @Override
    protected void updateVisibility() {
        super.updateVisibility();
        showPreviewButton();
    }

    @Override
    protected void hideAllLifeCycleButtons() {
        super.hideAllLifeCycleButtons();
        preview.hide();
    }

    private void showPreviewButton() {
        if (canPreviewData()) {
            preview.show();
        }
    }

    public HasClickHandlers getPreviewButton() {
        return preview;
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

    protected boolean canPreviewData() {
        // TODO Security
        return true;
    }
}
