package org.siemac.metamac.statistical.resources.web.client.base.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.MainFormLayoutButton;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;

import com.smartgwt.client.widgets.events.HasClickHandlers;


public class LifecycleMainFormLayout extends InternationalMainFormLayout {

    private MainFormLayoutButton productionValidation;
    private MainFormLayoutButton diffusionValidation;
    private MainFormLayoutButton rejectValidation;
    private MainFormLayoutButton pendingPublication;
    private MainFormLayoutButton programPublication;
    private MainFormLayoutButton cancelProgrammedPublication;
    private MainFormLayoutButton publish;
    private MainFormLayoutButton versioning;

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
        // Remove handler from edit button
        // editHandlerRegistration.removeHandler();

        productionValidation = new MainFormLayoutButton(getConstants().lifeCycleSendToProductionValidation(), GlobalResources.RESOURCE.validateProduction().getURL());
        diffusionValidation = new MainFormLayoutButton(getConstants().lifeCycleSendToDiffusionValidation(), GlobalResources.RESOURCE.validateDiffusion().getURL());
        rejectValidation = new MainFormLayoutButton(getConstants().lifeCycleRejectValidation(), GlobalResources.RESOURCE.reject().getURL());
        pendingPublication = new MainFormLayoutButton(getConstants().lifeCycleSendToPendingPublication(), GlobalResources.RESOURCE.pendingPublication().getURL());
        programPublication = new MainFormLayoutButton(getConstants().lifeCycleProgramPublication(), GlobalResources.RESOURCE.programPublication().getURL());
        cancelProgrammedPublication = new MainFormLayoutButton(getConstants().lifeCycleCancelProgramedPublication(), GlobalResources.RESOURCE.reject().getURL());
        publish = new MainFormLayoutButton(getConstants().lifeCyclePublish(), GlobalResources.RESOURCE.publish().getURL());
        versioning = new MainFormLayoutButton(getConstants().lifeCycleVersioning(), GlobalResources.RESOURCE.version().getURL());

        toolStrip.addButton(productionValidation);
        toolStrip.addButton(diffusionValidation);
        toolStrip.addButton(pendingPublication);
        toolStrip.addButton(rejectValidation);
        toolStrip.addButton(programPublication);
        toolStrip.addButton(cancelProgrammedPublication);
        toolStrip.addButton(publish);
        toolStrip.addButton(versioning);
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

    private void updateVisibility() {
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
            showPendingPublicationButton();
            showRejectValidationButton();
            // FIXME add cases for pub pending and publication programmed
            // } else if (StatisticalResourceProcStatusEnum.PUBLICATION_PENDING.equals(status)) {
            // showProgramPublicationButton();
            // showPublishButton();
            // } else if (StatisticalResourceProcStatusEnum.PUBLICATION_PROGRAMMED.equals(status)) {
            // showCancelProgrammedPublication();
            // // showPublishButton();
        } else if (ProcStatusEnum.PUBLISHED.equals(status)) {
            showVersioningButton();
        }
    }

    private void hideAllLifeCycleButtons() {
        productionValidation.hide();
        diffusionValidation.hide();
        rejectValidation.hide();
        pendingPublication.hide();
        programPublication.hide();
        cancelProgrammedPublication.hide();
        publish.hide();
        versioning.hide();
    }

    private void showProductionValidationButton() {
        productionValidation.show();
    }

    private void showDiffusionValidationButton() {
        diffusionValidation.show();
    }

    private void showRejectValidationButton() {
        rejectValidation.show();
    }

    private void showPendingPublicationButton() {
        pendingPublication.show();
    }

    private void showProgramPublicationButton() {
        programPublication.show();
    }

    private void showCancelProgrammedPublication() {
        cancelProgrammedPublication.show();
    }

    private void showPublishButton() {
        publish.show();
    }

    private void showVersioningButton() {
        versioning.show();
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

    public HasClickHandlers getPendingPublicationButton() {
        return pendingPublication;
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

}
