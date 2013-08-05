package org.siemac.metamac.statistical.resources.web.client.dataset.widgets;

import org.siemac.metamac.statistical.resources.web.client.base.widgets.LifecycleMainFormLayout;

public class DatasetMainFormLayout extends LifecycleMainFormLayout {

    public DatasetMainFormLayout() {
        super();
    }

    public DatasetMainFormLayout(boolean canEdit) {
        super(canEdit);
    }

    @Override
    protected boolean canSendToProductionValidation() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean canSendToDiffusionValidation() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean canRejectValidation() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean canPublish() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean canProgramPublication() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean canCancelProgrammedPublication() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected boolean canVersioning() {
        // TODO Auto-generated method stub
        return false;
    }
}
