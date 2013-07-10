package org.siemac.metamac.statistical_resources.rest.external.v1_0.service;

import static org.siemac.metamac.rest.exception.utils.RestExceptionUtils.checkParameterNotWildcardAll;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.exception.RestCommonServiceExceptionType;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset.DatasetsDo2RestMapperV10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statisticalResourcesRestExternalFacadeV10")
public class StatisticalResourcesRestExternalFacadeV10Impl implements StatisticalResourcesV1_0 {

    private final ServiceContext     ctx                      = new ServiceContext("restExternal", "restExternal", "restExternal");
    private final Logger             logger                   = LoggerFactory.getLogger(StatisticalResourcesRestExternalFacadeV10Impl.class);

    private final PagingParameter    pagingParameterOneResult = PagingParameter.pageAccess(1, 1, false);

    @Autowired
    private DatasetService           datasetService;

    @Autowired
    private DatasetsDo2RestMapperV10 datasetsDo2RestMapper;

    @Override
    public Dataset retrieveDataset(String agencyID, String resourceID, String version) {
        try {
            // Validations
            checkParameterNotWildcardAll(RestExternalConstants.PARAMETER_AGENCY_ID, agencyID);
            checkParameterNotWildcardAll(RestExternalConstants.PARAMETER_RESOURCE_ID, resourceID);
            checkParameterNotWildcardAll(RestExternalConstants.PARAMETER_VERSION, version);

            // Find one
            PagedResult<DatasetVersion> entitiesPagedResult = findDatasetsCore(agencyID, resourceID, version, null, pagingParameterOneResult);
            if (entitiesPagedResult.getValues().size() != 1) {
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.DATASET_NOT_FOUND, resourceID, version, agencyID);
                throw new RestException(exception, Status.NOT_FOUND);
            }

            // TODO DIMENSIONES
            // datasetService.retrieveDatasetVersionDimensionsIds(ctx, datasetVersionUrn);
            // datasetService.retrieveCoverageForDatasetVersionDimension(ctx, datasetVersionUrn, dsdDimensionId);

            // Transform
            Dataset dataset = datasetsDo2RestMapper.toDataset(entitiesPagedResult.getValues().get(0));
            return dataset;
        } catch (Exception e) {
            throw manageException(e);
        }
    }

    private PagedResult<DatasetVersion> findDatasetsCore(String agencyID, String resourceID, String version, List<ConditionalCriteria> conditionalCriteriaQuery, PagingParameter pagingParameter)
            throws MetamacException {

        // Criteria to find by criteria
        // TODO query desde petición
        // TODO query con agencyId (nestedCode), resourceId...
        // TODO PUBLICADOS
        List<ConditionalCriteria> conditionalCriteria = conditionalCriteriaQuery;

        // Find
        PagedResult<DatasetVersion> entitiesPagedResult = datasetService.findDatasetVersionsByCondition(ctx, conditionalCriteria, pagingParameter);
        return entitiesPagedResult;
    }
    /**
     * Throws response error, logging exception
     */
    private RestException manageException(Exception e) {
        logger.error("Error", e);
        if (e instanceof RestException) {
            return (RestException) e;
        } else {
            // do not show information details about exception to user
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestCommonServiceExceptionType.UNKNOWN);
            return new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }
}
