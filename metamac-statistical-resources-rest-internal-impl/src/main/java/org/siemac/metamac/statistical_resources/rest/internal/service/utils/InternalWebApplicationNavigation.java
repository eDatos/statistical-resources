package org.siemac.metamac.statistical_resources.rest.internal.service.utils;

import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.navigation.shared.NameTokens;
import org.siemac.metamac.statistical.resources.navigation.shared.PlaceRequestParams;
import org.springframework.web.util.UriTemplate;

public class InternalWebApplicationNavigation {

    private final String      SEPARATOR                  = "/";
    private final String      PATH_STATISTICAL_RESOURCES = "#" + NameTokens.operationsListPage;
    private final String      OPERATION_ID_PARAMETER     = "operationParam";
    private final String      PUBLICATION_ID_PARAMETER   = "publicationParam";
    private final String      DATASET_ID_PARAMETER       = "datasetParam";
    private final String      QUERY_ID_PARAMETER         = "queryParam";

    private final UriTemplate publicationVersionTemplate;
    private final UriTemplate datasetVersionTemplate;
    private final UriTemplate queryTemplate;

    public InternalWebApplicationNavigation(String webApplicationPath) {
        // Publication
        // @formatter:off
        publicationVersionTemplate = new UriTemplate(webApplicationPath + SEPARATOR + PATH_STATISTICAL_RESOURCES 
                + SEPARATOR + NameTokens.operationPage + ";" + PlaceRequestParams.operationParam + "=" + "{" + OPERATION_ID_PARAMETER + "}" 
                + SEPARATOR + NameTokens.publicationsListPage + SEPARATOR + NameTokens.publicationPage + ";" + PlaceRequestParams.publicationParam + "=" + "{" + PUBLICATION_ID_PARAMETER + "}" 
                );
        
        datasetVersionTemplate = new UriTemplate(webApplicationPath + SEPARATOR + PATH_STATISTICAL_RESOURCES 
                + SEPARATOR + NameTokens.operationPage + ";" + PlaceRequestParams.operationParam + "=" + "{" + OPERATION_ID_PARAMETER + "}" 
                + SEPARATOR + NameTokens.datasetsListPage + SEPARATOR + NameTokens.datasetPage + ";" + PlaceRequestParams.datasetParam + "=" + "{" + DATASET_ID_PARAMETER + "}" 
                );
        
        queryTemplate = new UriTemplate(webApplicationPath + SEPARATOR + PATH_STATISTICAL_RESOURCES 
                + SEPARATOR + NameTokens.operationPage + ";" + PlaceRequestParams.operationParam + "=" + "{" + OPERATION_ID_PARAMETER + "}" 
                + SEPARATOR + NameTokens.queriesListPage + SEPARATOR + NameTokens.queryPage + ";" + PlaceRequestParams.queryParam + "=" + "{" + QUERY_ID_PARAMETER + "}" 
                );
        // @formatter:on
    }

    // PUBLICATIONS
    public String buildPublicationVersionUrl(PublicationVersion source) {
        Map<String, String> parameters = new HashMap<String, String>(1);
        parameters.put(OPERATION_ID_PARAMETER, source.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode());
        parameters.put(PUBLICATION_ID_PARAMETER, UrnUtils.removePrefix(source.getLifeCycleStatisticalResource().getUrn()));
        return publicationVersionTemplate.expand(parameters).toString();
    }

    public String buildRelatedResourcePublicationVersionUrl(RelatedResourceResult source) {
        Map<String, String> parameters = new HashMap<String, String>(1);
        parameters.put(OPERATION_ID_PARAMETER, source.getStatisticalOperationCode());
        parameters.put(PUBLICATION_ID_PARAMETER, UrnUtils.removePrefix(source.getUrn()));
        return publicationVersionTemplate.expand(parameters).toString();
    }

    // DATASETS
    public String buildDatasetVersionUrl(DatasetVersion source) {
        Map<String, String> parameters = new HashMap<String, String>(1);
        parameters.put(OPERATION_ID_PARAMETER, source.getSiemacMetadataStatisticalResource().getStatisticalOperation().getCode());
        parameters.put(DATASET_ID_PARAMETER, UrnUtils.removePrefix(source.getLifeCycleStatisticalResource().getUrn()));
        return datasetVersionTemplate.expand(parameters).toString();
    }

    public String buildDatasetVersionUrl(RelatedResourceResult source) {
        Map<String, String> parameters = new HashMap<String, String>(1);
        parameters.put(OPERATION_ID_PARAMETER, source.getStatisticalOperationCode());
        parameters.put(DATASET_ID_PARAMETER, UrnUtils.removePrefix(source.getUrn()));
        return datasetVersionTemplate.expand(parameters).toString();
    }

    // QUERY
    public String buildQueryVersionUrl(QueryVersion source) {
        Map<String, String> parameters = new HashMap<String, String>(1);
        parameters.put(OPERATION_ID_PARAMETER, source.getLifeCycleStatisticalResource().getStatisticalOperation().getCode());
        parameters.put(QUERY_ID_PARAMETER, UrnUtils.removePrefix(source.getLifeCycleStatisticalResource().getUrn()));
        return queryTemplate.expand(parameters).toString();
    }

    public String buildQueryVersionUrl(RelatedResourceResult source) {
        Map<String, String> parameters = new HashMap<String, String>(1);
        parameters.put(OPERATION_ID_PARAMETER, source.getCode());
        parameters.put(QUERY_ID_PARAMETER, UrnUtils.removePrefix(source.getUrn()));
        return queryTemplate.expand(parameters).toString();
    }

}