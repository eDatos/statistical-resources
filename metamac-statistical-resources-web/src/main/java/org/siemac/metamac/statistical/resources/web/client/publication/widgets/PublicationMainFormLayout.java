package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.widgets.MainFormLayoutButton;
import org.siemac.metamac.web.common.client.widgets.form.InternationalMainFormLayout;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public class PublicationMainFormLayout extends InternationalMainFormLayout {

    private MainFormLayoutButton              productionValidation;
    private MainFormLayoutButton              diffusionValidation;
    private MainFormLayoutButton              rejectValidation;
    private MainFormLayoutButton              publish;
    private MainFormLayoutButton              versioning;

    private ProcStatusEnum status;

    public PublicationMainFormLayout() {
        super();
        common();
    }

    public PublicationMainFormLayout(boolean canEdit) {
        super(canEdit);
        common();
    }

    private void common() {
        // Remove handler from edit button
        // editHandlerRegistration.removeHandler();

        productionValidation = new MainFormLayoutButton(getConstants().lifeCycleSendToProductionValidation(), GlobalResources.RESOURCE.validateProduction().getURL());
        diffusionValidation = new MainFormLayoutButton(getConstants().lifeCycleSendToDiffusionValidation(), GlobalResources.RESOURCE.validateDiffusion().getURL());
        rejectValidation = new MainFormLayoutButton(getConstants().lifeCycleRejectValidation(), GlobalResources.RESOURCE.reject().getURL());
        publish = new MainFormLayoutButton(getConstants().lifeCyclePublish(), GlobalResources.RESOURCE.publish().getURL());
        versioning = new MainFormLayoutButton(getConstants().lifeCycleVersioning(), GlobalResources.RESOURCE.version().getURL());

        /*toolStrip.addButton(productionValidation);
        toolStrip.addButton(diffusionValidation);
        toolStrip.addButton(rejectValidation);
        toolStrip.addButton(publish);
        toolStrip.addButton(versioning);*/
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
       /* if (ProcStatusEnum.DRAFT.equals(status)) {
            showProductionValidationButton();
        } else if (ProcStatusEnum.VALIDATION_REJECTED.equals(status)) {
            showProductionValidationButton();
        } else if (ProcStatusEnum.PRODUCTION_VALIDATION.equals(status)) {
            showDiffusionValidationButton();
            showRejectValidationButton();
        } else if (ProcStatusEnum.DIFFUSION_VALIDATION.equals(status)) {
            showRejectValidationButton();
            showPublishButton();
        } else if (ProcStatusEnum.PUBLISHED.equals(status)) {
            showVersioningButton();
        }*/
    }

    private void hideAllLifeCycleButtons() {
        productionValidation.hide();
        diffusionValidation.hide();
        rejectValidation.hide();
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

    public HasClickHandlers getPublishButton() {
        return publish;
    }

    public HasClickHandlers getVersioningButton() {
        return versioning;
    }
}
