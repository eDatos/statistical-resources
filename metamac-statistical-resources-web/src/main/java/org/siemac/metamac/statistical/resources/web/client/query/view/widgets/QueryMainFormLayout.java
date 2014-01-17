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
        setCanEdit(QueryClientSecurityUtils.canUpdateQueryVersion(queryVersionDto));
        setCanDelete(QueryClientSecurityUtils.canDeleteQueryVersion(queryVersionDto));
        updatePublishSection(queryVersionDto.getProcStatus(), queryVersionDto.getLastVersion());
    }

    @Override
    protected boolean canSendToProductionValidation() {
        return QueryClientSecurityUtils.canSendQueryVersionToProductionValidation();
    }

    @Override
    protected boolean canSendToDiffusionValidation() {
        return QueryClientSecurityUtils.canSendQueryVersionToDiffusionValidation();
    }

    @Override
    protected boolean canRejectValidation() {
        return QueryClientSecurityUtils.canSendQueryVersionToValidationRejected();
    }

    @Override
    protected boolean canPublish() {
        return QueryClientSecurityUtils.canPublishQueryVersion();
    }

    @Override
    protected boolean canProgramPublication() {
        return QueryClientSecurityUtils.canPublishQueryVersion();
    }

    @Override
    protected boolean canCancelProgrammedPublication() {
        return QueryClientSecurityUtils.canCancelProgrammedPublication();
    }

    @Override
    protected boolean canVersioning() {
        // Queries can not be versioned
        return false;
    }

    @Override
    protected boolean canPreviewData() {
        // Do not show the query preview button
        return true;
    }
}
