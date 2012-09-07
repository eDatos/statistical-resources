package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.web.common.client.widgets.AnnounceToolStripButton;
import org.siemac.metamac.web.common.client.widgets.MainFormLayoutButton;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public class DatasetMainFormLayout extends InternationalMainFormLayout {

    private MainFormLayoutButton    productionValidation;
    private MainFormLayoutButton    diffusionValidation;
    private MainFormLayoutButton    rejectValidation;
    private MainFormLayoutButton    publishInternally;
    private MainFormLayoutButton    publishExternally;
    private MainFormLayoutButton    versioning;
    private MainFormLayoutButton    cancelValidity;
    private AnnounceToolStripButton announce;

    public DatasetMainFormLayout() {
        super();
        common();
    }

    public DatasetMainFormLayout(boolean canEdit) {
        super(canEdit);
        common();
    }

    private void common() {
        // Remove handler from edit button
        editHandlerRegistration.removeHandler();

        /*
         * productionValidation = new MainFormLayoutButton(getConstants().lifeCycleSendToProductionValidation(), GlobalResources.RESOURCE.validateProduction().getURL());
         * diffusionValidation = new MainFormLayoutButton(getConstants().lifeCycleSendToDiffusionValidation(), GlobalResources.RESOURCE.validateDiffusion().getURL());
         * publishInternally = new MainFormLayoutButton(getConstants().lifeCyclePublishInternally(), GlobalResources.RESOURCE.internalPublish().getURL());
         * publishExternally = new MainFormLayoutButton(getConstants().lifeCyclePublishExternally(), GlobalResources.RESOURCE.externalPublish().getURL());
         * rejectValidation = new MainFormLayoutButton(getConstants().lifeCycleRejectValidation(), GlobalResources.RESOURCE.reject().getURL());
         * versioning = new MainFormLayoutButton(getConstants().lifeCycleVersioning(), GlobalResources.RESOURCE.version().getURL());
         * cancelValidity = new MainFormLayoutButton(getConstants().lifeCycleCancelValidity(), org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE.disable().getURL());
         * announce = new AnnounceToolStripButton(MetamacWebCommon.getConstants().announce(), org.siemac.metamac.web.common.client.resources.GlobalResources.RESOURCE.announce().getURL());
         * announce.setVisibility(DatasetClientSecurityUtils.canAnnounceConceptScheme() ? Visibility.VISIBLE : Visibility.HIDDEN);
         * toolStrip.addButton(productionValidation);
         * toolStrip.addButton(diffusionValidation);
         * toolStrip.addButton(publishInternally);
         * toolStrip.addButton(publishExternally);
         * toolStrip.addButton(rejectValidation);
         * toolStrip.addButton(versioning);
         * toolStrip.addButton(cancelValidity);
         * toolStrip.addButton(announce);
         */
    }

    public void updatePublishSection(StatisticalResourceProcStatusEnum status) {
        /* this.status = status; */
    }

    private void updateVisibility() {
        // // Hide all buttons
        // hideAllPublishButtons();
        // // Show buttons depending on the status
        // if (StatisticalResourceProcStatusEnum.DRAFT.equals(status)) {
        // showSendToProductionValidation();
        // } else if (StatisticalResourceProcStatusEnum.VALIDATION_REJECTED.equals(status)) {
        // showSendToProductionValidation();
        // } else if (StatisticalResourceProcStatusEnum.PRODUCTION_VALIDATION.equals(status)) {
        // showSendToDiffusionValidation();
        // showRejectValidationButton();
        // } else if (StatisticalResourceProcStatusEnum.DIFFUSION_VALIDATION.equals(status)) {
        // showPublishInternallyButton();
        // showRejectValidationButton();
        // } else if (StatisticalResourceProcStatusEnum.INTERNALLY_PUBLISHED.equals(status)) {
        // showPublishExternallyButton();
        // showVersioningButton();
        // } else if (StatisticalResourceProcStatusEnum.EXTERNALLY_PUBLISHED.equals(status)) {
        // showVersioningButton();
        // showCancelValidityButton();
        // }
    }

    @Override
    public void setViewMode() {
        super.setViewMode();
        updateVisibility();
    }

    @Override
    public void setEditionMode() {
        super.setEditionMode();
        hideAllPublishButtons();
    }

    public HasClickHandlers getSendToProductionValidation() {
        return productionValidation;
    }

    public HasClickHandlers getSendToDiffusionValidation() {
        return diffusionValidation;
    }

    public HasClickHandlers getRejectValidation() {
        return rejectValidation;
    }

    public HasClickHandlers getPublishInternally() {
        return publishInternally;
    }

    public HasClickHandlers getPublishExternally() {
        return publishExternally;
    }

    public HasClickHandlers getVersioning() {
        return versioning;
    }

    public HasClickHandlers getCancelValidity() {
        return cancelValidity;
    }

    public HasClickHandlers getAnnounce() {
        return announce;
    }

    private void hideAllPublishButtons() {
        productionValidation.hide();
        diffusionValidation.hide();
        rejectValidation.hide();
        publishInternally.hide();
        publishExternally.hide();
        versioning.hide();
        cancelValidity.hide();
    }
    //
    // private void showSendToProductionValidation() {
    // if (DatasetClientSecurityUtils.canSendDatasetToProductionValidation()) {
    // productionValidation.show();
    // }
    // }
    //
    // private void showSendToDiffusionValidation() {
    // if (DatasetClientSecurityUtils.canSendConceptSchemeToDiffusionValidation()) {
    // diffusionValidation.show();
    // }
    // }
    //
    // private void showRejectValidationButton() {
    // if (DatasetClientSecurityUtils.canRejectConceptSchemeValidation()) {
    // rejectValidation.show();
    // }
    // }
    //
    // private void showPublishInternallyButton() {
    // if (DatasetClientSecurityUtils.canPublishConceptSchemeInternally()) {
    // publishInternally.show();
    // }
    // }
    //
    // private void showPublishExternallyButton() {
    // if (DatasetClientSecurityUtils.canPublishConceptSchemeExternally()) {
    // publishExternally.show();
    // }
    // }
    //
    // private void showVersioningButton() {
    // if (DatasetClientSecurityUtils.canVersioningConceptScheme()) {
    // versioning.show();
    // }
    // }
    //
    // private void showCancelValidityButton() {
    // if (DatasetClientSecurityUtils.canCancelConceptSchemeValidity()) {
    // cancelValidity.show();
    // }
    // }

}
