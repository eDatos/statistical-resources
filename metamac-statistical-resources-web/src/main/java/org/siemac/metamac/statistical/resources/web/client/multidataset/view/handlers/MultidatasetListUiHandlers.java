package org.siemac.metamac.statistical.resources.web.client.multidataset.view.handlers;

import java.util.List;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.NewStatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.shared.criteria.MultidatasetVersionWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

public interface MultidatasetListUiHandlers extends NewStatisticalResourceUiHandlers {

    void createMultidataset(MultidatasetVersionDto multidatasetDto);
    void deleteMultidataset(List<String> urns);
    void retrieveMultidatasets(int firstResult, int maxResults, MultidatasetVersionWebCriteria criteria);
    void goToMultidataset(String urn);

    // LifeCycle

    void sendToProductionValidation(List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos);
    void sendToDiffusionValidation(List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos);
    void rejectValidation(List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos, String reasonOfRejection);
    void publish(List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos);

    void version(List<MultidatasetVersionBaseDto> multidatasetVersionBaseDtos, VersionTypeEnum versionType);

    // Related resources
    void retrieveStatisticalOperationsForSearchSection(int firstResult, int maxResults, MetamacWebCriteria criteria);
}
