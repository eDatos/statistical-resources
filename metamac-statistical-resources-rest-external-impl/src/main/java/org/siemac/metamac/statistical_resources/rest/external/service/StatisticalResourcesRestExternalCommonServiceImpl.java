package org.siemac.metamac.statistical_resources.rest.external.service;

import static org.siemac.metamac.rest.exception.utils.RestExceptionUtils.checkParameterNotWildcardAll;
import static org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants.SERVICE_CONTEXT;
import static org.siemac.metamac.statistical_resources.rest.external.service.utils.StatisticalResourcesRestExternalUtils.manageException;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.srm.rest.internal.RestInternalConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionProperties;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statisticalResourcesRestExternalCommonService")
public class StatisticalResourcesRestExternalCommonServiceImpl implements StatisticalResourcesRestExternalCommonService {

    private final PagingParameter pagingParameterOneResult = PagingParameter.pageAccess(1, 1, false);

    @Autowired
    private DatasetService        datasetService;

    @Override
    public DatasetVersion retrieveDatasetVersion(String agencyID, String resourceID, String version) {
        try {
            // Validations
            checkParameterNotWildcardAll(RestExternalConstants.PARAMETER_AGENCY_ID, agencyID);
            checkParameterNotWildcardAll(RestExternalConstants.PARAMETER_RESOURCE_ID, resourceID);
            checkParameterNotWildcardAll(RestExternalConstants.PARAMETER_VERSION, version);

            // Retrieve dataset
            PagedResult<DatasetVersion> entitiesPagedResult = findDatasetsCore(agencyID, resourceID, version, null, pagingParameterOneResult);
            if (entitiesPagedResult.getValues().size() != 1) {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.DATASET_NOT_FOUND, resourceID, version, agencyID);
                throw new RestException(exception, Status.NOT_FOUND);
            }
            DatasetVersion datasetVersion = entitiesPagedResult.getValues().get(0);
            return datasetVersion;
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    private PagedResult<DatasetVersion> findDatasetsCore(String agencyID, String resourceID, String version, List<ConditionalCriteria> conditionalCriteriaQuery, PagingParameter pagingParameter)
            throws MetamacException {

        // Criteria to find by criteria
        // TODO PUBLICADOS

        List<ConditionalCriteria> conditionalCriteria = new ArrayList<ConditionalCriteria>();
        if (CollectionUtils.isNotEmpty(conditionalCriteriaQuery)) {
            conditionalCriteria.addAll(conditionalCriteriaQuery);
        } else {
            conditionalCriteria.addAll(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).distinctRoot().build());
        }
        if (agencyID != null && !RestInternalConstants.WILDCARD_ALL.equals(agencyID)) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().maintainer().codeNested())
                    .eq(agencyID).buildSingle());
        }
        if (resourceID != null && !RestInternalConstants.WILDCARD_ALL.equals(agencyID)) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().code()).eq(resourceID)
                    .buildSingle());
        }
        if (RestInternalConstants.WILDCARD_LATEST.equals(version)) {
            // TODO Latest
        } else if (version != null && !RestInternalConstants.WILDCARD_ALL.equals(agencyID)) {
            conditionalCriteria.add(ConditionalCriteriaBuilder.criteriaFor(DatasetVersion.class).withProperty(DatasetVersionProperties.siemacMetadataStatisticalResource().versionLogic()).eq(version)
                    .buildSingle());
        }

        // Find
        PagedResult<DatasetVersion> entitiesPagedResult = datasetService.findDatasetVersionsByCondition(SERVICE_CONTEXT, conditionalCriteria, pagingParameter);
        return entitiesPagedResult;
    }
}
