package org.siemac.metamac.statistical.resources.web.client.query.view.widgets;

import org.siemac.metamac.statistical.resources.web.client.base.widgets.LifecycleMainFormLayout;

public class QueryMainFormLayout extends LifecycleMainFormLayout {

    public QueryMainFormLayout() {
        super();
    }

    public QueryMainFormLayout(boolean canEdit) {
        super(canEdit);
    }

    @Override
    protected boolean canSendToProductionValidation() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected boolean canSendToDiffusionValidation() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected boolean canRejectValidation() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected boolean canPublish() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    protected boolean canProgramPublication() {
        // TODO Auto-generated method stub
        return true;
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
        // Do not show the query preview button
        return false;
    }
}
