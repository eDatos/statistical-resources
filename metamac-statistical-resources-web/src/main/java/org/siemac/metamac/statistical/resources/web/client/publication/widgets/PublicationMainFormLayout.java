package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.widgets.LifecycleMainFormLayout;
import org.siemac.metamac.statistical.resources.web.client.publication.utils.PublicationClientSecurityUtils;

public class PublicationMainFormLayout extends LifecycleMainFormLayout {

    private PublicationVersionDto publicationVersionDto;

    public PublicationMainFormLayout() {
        super();
    }

    public PublicationMainFormLayout(boolean canEdit) {
        super(canEdit);
    }

    public void setPublicationVersion(PublicationVersionDto publicationVersionDto) {
        this.publicationVersionDto = publicationVersionDto;
        setCanEdit(PublicationClientSecurityUtils.canUpdatePublicationVersion(publicationVersionDto));
        setCanDelete(PublicationClientSecurityUtils.canDeletePublicationVersion(publicationVersionDto));
        updatePublishSection(publicationVersionDto.getProcStatus(), publicationVersionDto.getLastVersion());
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
        // TODO Auto-generated method stub
        return true;
    }
}
