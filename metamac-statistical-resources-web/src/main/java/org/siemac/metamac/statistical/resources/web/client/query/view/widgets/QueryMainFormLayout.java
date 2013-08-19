package org.siemac.metamac.statistical.resources.web.client.query.view.widgets;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.LifecycleMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.query.utils.QueryClientSecurityUtils;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;

public class QueryMainFormLayout extends LifecycleMainFormLayout {

    private QueryVersionDto queryVersionDto;

    public QueryMainFormLayout() {
        super();
    }

    public QueryMainFormLayout(boolean canEdit) {
        super(canEdit);
    }

    public void setQueryVersion(QueryVersionDto queryVersionDto) {
        this.queryVersionDto = queryVersionDto;

        setTitleLabelContents(InternationalStringUtils.getLocalisedString(queryVersionDto.getTitle()));

        setCanEdit(QueryClientSecurityUtils.canUpdatePublicationVersion(queryVersionDto));
        setCanDelete(QueryClientSecurityUtils.canDeletePublicationVersion(queryVersionDto));
        updatePublishSection(queryVersionDto.getProcStatus());
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
