package org.siemac.metamac.statistical.resources.web.client.multidataset.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.multidataset.model.ds.MultidatasetDS;
import org.siemac.metamac.statistical.resources.web.client.multidataset.view.handlers.MultidatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.SiemacMetadataResourceRelationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.fields.RelatedResourceListItem;
import org.siemac.metamac.statistical.resources.web.shared.criteria.VersionableStatisticalResourceWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;

import com.gwtplatform.mvp.client.UiHandlers;

public class MultidatasetResourceRelationDescriptorsEditionForm extends SiemacMetadataResourceRelationDescriptorsEditionForm {

    private MultidatasetMetadataTabUiHandlers uiHandlers;

    public MultidatasetResourceRelationDescriptorsEditionForm() {
        super();

        RelatedResourceListItem hasPart = new RelatedResourceListItem(MultidatasetDS.HAS_PART, getConstants().siemacMetadataStatisticalResourceHasPart(), false, getRecordNavigationHandler());

        addFields(hasPart);
    }

    public void setMultidatasetVersionDto(MultidatasetVersionDto dto) {
        setSiemacMetadataStatisticalResourceDto(dto);

    }

    @Override
    public void setUiHandlers(UiHandlers uiHandlers) {
        this.uiHandlers = (MultidatasetMetadataTabUiHandlers) uiHandlers;
    }

    @Override
    public void retrieveResourcesForReplaces(int firstResult, int maxResults, VersionableStatisticalResourceWebCriteria criteria) {
        uiHandlers.retrieveMultidatasetsForReplaces(firstResult, maxResults, criteria);
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
