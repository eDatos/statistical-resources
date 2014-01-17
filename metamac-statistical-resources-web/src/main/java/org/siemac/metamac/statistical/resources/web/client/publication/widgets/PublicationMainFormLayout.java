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
        setCanDelete(PublicationClientSecurityUtils.canDeletePublicationVersion(publicationVersionDto.getProcStatus()));
        updatePublishSection(publicationVersionDto.getProcStatus(), publicationVersionDto.getLastVersion());
    }

    @Override
    protected boolean canSendToProductionValidation() {
        return PublicationClientSecurityUtils.canSendPublicationVersionToProductionValidation();
    }

    @Override
    protected boolean canSendToDiffusionValidation() {
        return PublicationClientSecurityUtils.canSendPublicationVersionToDiffusionValidation();
    }

    @Override
    protected boolean canRejectValidation() {
        return PublicationClientSecurityUtils.canSendPublicationVersionToValidationRejected();
    }

    @Override
    protected boolean canPublish() {
        return PublicationClientSecurityUtils.canPublishPublicationVersion();
    }

    @Override
    protected boolean canProgramPublication() {
        return PublicationClientSecurityUtils.canProgramPublicationPublicationVersion();
    }

    @Override
    protected boolean canCancelProgrammedPublication() {
        return PublicationClientSecurityUtils.canCancelPublicationPublicationVersion();
    }

    @Override
    protected boolean canVersioning() {
        return PublicationClientSecurityUtils.canVersionPublication();
    }

    @Override
    protected boolean canPreviewData() {
        return PublicationClientSecurityUtils.canPreviewDataPublicationVersion();
    }
}
