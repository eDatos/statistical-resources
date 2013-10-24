package org.siemac.metamac.statistical.resources.web.client.query.view.widgets;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.query.QueryVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.LifecycleMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.query.utils.QueryClientSecurityUtils;
import org.siemac.metamac.statistical.resources.web.client.resources.GlobalResources;
import org.siemac.metamac.web.common.client.utils.InternationalStringUtils;
import org.siemac.metamac.web.common.client.widgets.MainFormLayoutButton;

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
        updatePublishSection(queryVersionDto.getProcStatus(), queryVersionDto.getLastVersion());
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
        // Queries can not be version
        return false;
    }

    @Override
    protected boolean canPreviewData() {
        // Do not show the query preview button
        return true;
    }
}
