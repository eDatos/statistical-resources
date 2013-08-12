package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.MainFormLayoutButton;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public abstract class LifecycleMainFormLayout extends InternationalMainFormLayout {

    private MainFormLayoutButton productionValidation;
    private MainFormLayoutButton diffusionValidation;
    private MainFormLayoutButton rejectValidation;
    private MainFormLayoutButton programPublication;
    private MainFormLayoutButton cancelProgrammedPublication;
    private MainFormLayoutButton publish;
    private MainFormLayoutButton versioning;
    private MainFormLayoutButton preview;

    private ProcStatusEnum       status;

    public LifecycleMainFormLayout() {
        super();
        common();
    }

    public LifecycleMainFormLayout(boolean canEdit) {
        super(canEdit);
        common();
    }

    private void common() {
        productionValidation = new MainFormLayoutButton(getConstants().lifeCycleSendToProductionValidation(), GlobalResources.RESOURCE.validateProduction().getURL());
        diffusionValidation = new MainFormLayoutButton(getConstants().lifeCycleSendToDiffusionValidation(), GlobalResources.RESOURCE.validateDiffusion().getURL());
        rejectValidation = new MainFormLayoutButton(getConstants().lifeCycleRejectValidation(), GlobalResources.RESOURCE.reject().getURL());
        programPublication = new MainFormLayoutButton(getConstants().lifeCycleProgramPublication(), GlobalResources.RESOURCE.programPublication().getURL());
        cancelProgrammedPublication = new MainFormLayoutButton(getConstants().lifeCycleCancelProgramedPublication(), GlobalResources.RESOURCE.reject().getURL());
        publish = new MainFormLayoutButton(getConstants().lifeCyclePublish(), GlobalResources.RESOURCE.publish().getURL());
        versioning = new MainFormLayoutButton(getConstants().lifeCycleVersioning(), GlobalResources.RESOURCE.version().getURL());
        preview = new MainFormLayoutButton(getConstants().actionPreviewData(), GlobalResources.RESOURCE.preview().getURL());

        toolStrip.addButton(productionValidation);
        toolStrip.addButton(diffusionValidation);
        toolStrip.addButton(rejectValidation);
        toolStrip.addButton(programPublication);
        toolStrip.addButton(cancelProgrammedPublication);
        toolStrip.addButton(publish);
        toolStrip.addButton(versioning);
        toolStrip.addButton(preview);
    }

    @Override
    public void setViewMode() {
        super.setViewMode();
        updateVisibility();
    }

    @Override
    public void setEditionMode() {
        super.setEditionMode();
        hideAllLifeCycleButtons();
    }

    public void updatePublishSection(ProcStatusEnum status) {
        this.status = status;
    }

    protected void updateVisibility() {
        // Hide all buttons
        hideAllLifeCycleButtons();
        // Show buttons depending on the status
        if (ProcStatusEnum.DRAFT.equals(status)) {
            showProductionValidationButton();
        } else if (ProcStatusEnum.VALIDATION_REJECTED.equals(status)) {
            showProductionValidationButton();
        } else if (ProcStatusEnum.PRODUCTION_VALIDATION.equals(status)) {
            showDiffusionValidationButton();
            showRejectValidationButton();
        } else if (ProcStatusEnum.DIFFUSION_VALIDATION.equals(status)) {
            showRejectValidationButton();
            showProgramPublicationButton();
            showPublishButton();
        } else if (ProcStatusEnum.PUBLISHED.equals(status)) {
            showCancelProgrammedPublication();
            showVersioningButton();
        }
        showPreviewButton();
    }

    protected void hideAllLifeCycleButtons() {
        productionValidation.hide();
        diffusionValidation.hide();
        rejectValidation.hide();
        programPublication.hide();
        cancelProgrammedPublication.hide();
        publish.hide();
        versioning.hide();
        preview.hide();
    }

    private void showProductionValidationButton() {
        if (canSendToProductionValidation()) {
            productionValidation.show();
        }
    }

    private void showDiffusionValidationButton() {
        if (canSendToDiffusionValidation()) {
            diffusionValidation.show();
        }
    }

    private void showRejectValidationButton() {
        if (canRejectValidation()) {
            rejectValidation.show();
        }
    }

    private void showProgramPublicationButton() {
        if (canProgramPublication()) {
            programPublication.show();
        }
    }

    private void showCancelProgrammedPublication() {
        if (canCancelProgrammedPublication()) {
            cancelProgrammedPublication.show();
        }
    }

    private void showPublishButton() {
        if (canPublish()) {
            publish.show();
        }
    }

    private void showVersioningButton() {
        if (canVersioning()) {
            versioning.show();
        }
    }

    private void showPreviewButton() {
        if (canPreviewData()) {
            preview.show();
        }
    }

    public HasClickHandlers getProductionValidationButton() {
        return productionValidation;
    }

    public HasClickHandlers getDiffusionValidationButton() {
        return diffusionValidation;
    }

    public HasClickHandlers getRejectValidationButton() {
        return rejectValidation;
    }

    public HasClickHandlers getProgramPublicationButton() {
        return programPublication;
    }

    public HasClickHandlers getCancelProgrammedPublication() {
        return cancelProgrammedPublication;
    }

    public HasClickHandlers getPublishButton() {
        return publish;
    }

    public HasClickHandlers getVersioningButton() {
        return versioning;
    }

    public HasClickHandlers getPreviewButton() {
        return preview;
    }

    //
    // ABSTRACT METHODS
    //

    protected abstract boolean canSendToProductionValidation();
    protected abstract boolean canSendToDiffusionValidation();
    protected abstract boolean canRejectValidation();
    protected abstract boolean canPublish();
    protected abstract boolean canProgramPublication();
    protected abstract boolean canCancelProgrammedPublication();
    protected abstract boolean canVersioning();
    protected abstract boolean canPreviewData();
}
