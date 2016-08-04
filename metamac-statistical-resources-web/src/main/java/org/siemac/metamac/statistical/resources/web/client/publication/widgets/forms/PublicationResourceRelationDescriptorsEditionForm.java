package org.siemac.metamac.statistical.resources.web.client.publication.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcesFormUtils.setRelatedResourcesValue;

import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.web.client.publication.model.ds.PublicationDS;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.SiemacMetadataResourceRelationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceListItem;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;

import com.gwtplatform.mvp.client.UiHandlers;

public class PublicationResourceRelationDescriptorsEditionForm extends SiemacMetadataResourceRelationDescriptorsEditionForm {

    private PublicationMetadataTabUiHandlers uiHandlers;

    public PublicationResourceRelationDescriptorsEditionForm() {
        super();

        RelatedResourceListItem hasPart = new RelatedResourceListItem(PublicationDS.HAS_PART, getConstants().siemacMetadataStatisticalResourceHasPart(), false, getRecordNavigationHandler());

        addFields(hasPart);
    }

    public void setPublicationVersionDto(PublicationVersionDto dto) {
        setSiemacMetadataStatisticalResourceDto(dto);

        setRelatedResourcesValue(getItem(PublicationDS.HAS_PART), dto.getHasPart());
    }

    @Override
    public void setUiHandlers(UiHandlers uiHandlers) {
        this.uiHandlers = (PublicationMetadataTabUiHandlers) uiHandlers;
    }

    @Override
    public void retrieveResourcesForReplaces(int firstResult, int maxResults, VersionableStatisticalResourceWebCriteria criteria) {
        uiHandlers.retrievePublicationsForReplaces(firstResult, maxResults, criteria);
    }

    @Override
    public void retrieveStatisticalOperationsForReplacesSelection() {
        uiHandlers.retrieveStatisticalOperationsForReplacesSelection();
    }

    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
