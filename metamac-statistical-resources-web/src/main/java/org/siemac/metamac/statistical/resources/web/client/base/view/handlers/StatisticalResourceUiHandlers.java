package org.siemac.metamac.statistical.resources.web.client.base.view.handlers;

import org.siemac.metamac.statistical.resources.web.client.base.utils.SiemacMetadataExternalField;
import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface StatisticalResourceUiHandlers extends BaseUiHandlers {

    // COMMON METADATA
    void retrieveCommonConfigurations(CommonConfigurationWebCriteria criteria);

    // STATISTICAL OPERATIONS INSTANCES
    void retrieveStatisticalOperationInstances(String statisticalOperationCode, int firstResult, int maxResults, MetamacWebCriteria webCriteria);

    // languages
    void retrieveLanguagesCodes(int firstResult, int maxResults, MetamacWebCriteria criteria);

    // ORGANIZATION UNITS and SCHEMES
    void retrieveOrganisationUnitSchemes(int firstResult, int maxResults, MetamacWebCriteria webCriteria, SiemacMetadataExternalField field);
    void retrieveOrganisationUnits(int firstResult, int maxResults, ItemSchemeWebCriteria webCriteria, SiemacMetadataExternalField field);
}
