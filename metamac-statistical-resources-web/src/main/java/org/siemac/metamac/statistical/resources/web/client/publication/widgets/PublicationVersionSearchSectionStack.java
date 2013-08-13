package org.siemac.metamac.statistical.resources.web.client.publication.widgets;

import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.publication.view.handlers.PublicationListUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.SiemacMetadataResourceSearchSectionStack;
import org.siemac.metamac.statistical.resources.web.shared.criteria.PublicationVersionWebCriteria;

public class PublicationVersionSearchSectionStack extends SiemacMetadataResourceSearchSectionStack {

    private PublicationListUiHandlers uiHandlers;

    public PublicationVersionSearchSectionStack() {
    }

    public PublicationVersionWebCriteria getPublicationVersionWebCriteria() {
        return (PublicationVersionWebCriteria) getSiemacMetadataStatisticalResourceWebCriteria(new PublicationVersionWebCriteria());
    }

    @Override
    public void retrieveResources() {
        getUiHandlers().retrievePublications(0, StatisticalResourceWebConstants.MAIN_LIST_MAX_RESULTS, getPublicationVersionWebCriteria());
    }

    public void setUiHandlers(PublicationListUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public PublicationListUiHandlers getUiHandlers() {
        return uiHandlers;
    }
}
