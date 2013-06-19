package org.siemac.metamac.statistical.resources.web.server.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.OperationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.server.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.constants.CommonSharedConstants;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticalOperationsRestInternalFacadeImpl implements StatisticalOperationsRestInternalFacade {

    @Autowired
    private RestApiLocator restApiLocator;

    @Override
    public ExternalItemDto retrieveOperation(String operationCode) throws MetamacWebException {
        try {
            return buildExternalItemDtoFromOperation(restApiLocator.getStatisticalOperationsRestFacadeV10().retrieveOperationById(operationCode)); // OPERATION ID in the rest API is what we call CODE
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = e.toErrorObject(WebClient.client(restApiLocator.getStatisticalOperationsRestFacadeV10()),
                    org.siemac.metamac.rest.common.v1_0.domain.Exception.class);
            throw WebExceptionUtils.createMetamacWebException(exception);
        } catch (Exception e) {
            throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, StatisticalResourcesWeb.getCoreMessages().exception_common_unknown());
        }
    }

    @Override
    public ExternalItemsResult findOperations(int firstResult, int maxResult, String operation) throws MetamacWebException {
        try {
            String query = null;
            if (!StringUtils.isBlank(operation)) {
                query = OperationCriteriaPropertyRestriction.TITLE + " " + ComparisonOperator.ILIKE.name() + " \"" + operation + "\"";
                query += " " + LogicalOperator.OR.name() + " " + OperationCriteriaPropertyRestriction.ID + " " + ComparisonOperator.ILIKE.name() + " \"" + operation + "\"";
            }

            // Pagination
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);

            Operations findOperationsResult = restApiLocator.getStatisticalOperationsRestFacadeV10().findOperations(query, null, limit, offset);
            List<ExternalItemDto> externalItemDtos = new ArrayList<ExternalItemDto>();
            for (ResourceInternal resource : findOperationsResult.getOperations()) {
                ExternalItemDto externalItemDto = buildExternalItemDtoFromResource(resource);
                externalItemDtos.add(externalItemDto);
            }
            
            ExternalItemsResult result = ExternalItemUtils.createExternalItemsResultFromListBase(findOperationsResult, externalItemDtos);

            return result;
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = e.toErrorObject(WebClient.client(restApiLocator.getStatisticalOperationsRestFacadeV10()),
                    org.siemac.metamac.rest.common.v1_0.domain.Exception.class);
            throw WebExceptionUtils.createMetamacWebException(exception);
        } catch (Exception e) {
            throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, StatisticalResourcesWeb.getCoreMessages().exception_common_unknown());
        }
    }

    private ExternalItemDto buildExternalItemDtoFromResource(ResourceInternal resource) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(resource.getId());
        externalItemDto.setUri(resource.getSelfLink().getHref());
        externalItemDto.setUrn(resource.getUrn());
        externalItemDto.setType(TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(resource.getName()));
        return externalItemDto;
    }
    
    private ExternalItemDto buildExternalItemDtoFromOperation(Operation operation) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(operation.getId());
        externalItemDto.setUri(operation.getSelfLink().getHref());
        externalItemDto.setUrn(operation.getUrn());
        externalItemDto.setType(TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(operation.getName()));
        return externalItemDto;
    }
}
