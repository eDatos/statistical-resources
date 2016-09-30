package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.web.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.MainFormLayoutButton;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public abstract class LifecycleMainFormLayout extends InternationalMainFormLayout {

    private MainFormLayoutButton productionValidation;
    private MainFormLayoutButton diffusionValidation;
    private MainFormLayoutButton rejectValidation;
    private MainFormLayoutButton programPublication;
    private MainFormLayoutButton publish;
    private MainFormLayoutButton versioning;
    private MainFormLayoutButton preview;

    private boolean              lastVersion;

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
        publish = new MainFormLayoutButton(getConstants().lifeCyclePublish(), GlobalResources.RESOURCE.publish().getURL());
        versioning = new MainFormLayoutButton(getConstants().lifeCycleVersioning(), GlobalResources.RESOURCE.version().getURL());
        preview = new MainFormLayoutButton(getConstants().actionPreviewData(), GlobalResources.RESOURCE.preview().getURL());

        toolStrip.addButton(productionValidation);
        toolStrip.addButton(diffusionValidation);
        toolStrip.addButton(rejectValidation);
        toolStrip.addButton(programPublication);
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

    public void updatePublishSection(boolean lastVersion) {
        this.lastVersion = lastVersion;
    }

    protected void updateVisibility() {
        // Hide all buttons
        hideAllLifeCycleButtons();

        if (canSendToProductionValidation()) {
            showProductionValidationButton();
        }
        if (canSendToDiffusionValidation()) {
            showDiffusionValidationButton();
        }
        if (canRejectValidation()) {
            showRejectValidationButton();
        }
        if (canProgramPublication()) {
            showProgramPublicationButton();
        }
        if (canPublish()) {
            showPublishButton();
        }
        if (canVersion() && lastVersion) {
            showVersioningButton();
        }

        showPreviewButton();
    }

    protected void hideAllLifeCycleButtons() {
        productionValidation.hide();
        diffusionValidation.hide();
        rejectValidation.hide();
        programPublication.hide();
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

    private void showPublishButton() {
        if (canPublish()) {
            publish.show();
        }
    }

    private void showVersioningButton() {
        if (canVersion()) {
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

    protected abstract boolean canVersion();
    protected abstract boolean canPreviewData();
}
