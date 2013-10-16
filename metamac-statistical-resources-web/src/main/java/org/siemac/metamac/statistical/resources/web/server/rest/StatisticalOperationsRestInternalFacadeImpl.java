package org.siemac.metamac.statistical.resources.web.server.rest;

import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebRestCriteriaUtils.buildQueryStatisticalOperation;
import static org.siemac.metamac.statistical.resources.web.server.utils.MetamacWebRestCriteriaUtils.buildQueryStatisticalOperationInstance;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.invocation.service.StatisticalOperationsRestInternalService;
import org.siemac.metamac.statistical.resources.web.server.utils.ExternalItemWebUtils;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticalOperationsRestInternalFacadeImpl implements StatisticalOperationsRestInternalFacade {

    @Autowired
    private StatisticalOperationsRestInternalService statisticalOperationsRestInternalService;

    @Override
    public ExternalItemDto retrieveOperation(String operationCode) throws MetamacWebException {
        try {
            Operation operation = statisticalOperationsRestInternalService.retrieveOperationById(operationCode);
            return buildExternalItemDtoFromOperation(operation);
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public ExternalItemsResult findOperations(int firstResult, int maxResult, MetamacWebCriteria criteria) throws MetamacWebException {
        try {
            String query = buildQueryStatisticalOperation(criteria);

            Operations findOperationsResult = statisticalOperationsRestInternalService.findOperations(firstResult, maxResult, query);

            List<ExternalItemDto> externalItemDtos = buildExternalItemDtosFromResources(findOperationsResult.getOperations(), TypeExternalArtefactsEnum.STATISTICAL_OPERATION);

            ExternalItemsResult result = ExternalItemWebUtils.createExternalItemsResultFromListBase(findOperationsResult, externalItemDtos);
            return result;
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    @Override
    public ExternalItemsResult findOperationInstances(String operationId, int firstResult, int maxResult, MetamacWebCriteria criteria) throws MetamacWebException {
        try {
            String query = buildQueryStatisticalOperationInstance(criteria);

            Instances instances = statisticalOperationsRestInternalService.findInstances(operationId, firstResult, maxResult, query);

            List<ExternalItemDto> externalItemDtos = buildExternalItemDtosFromResources(instances.getInstances(), TypeExternalArtefactsEnum.STATISTICAL_OPERATION_INSTANCE);

            ExternalItemsResult result = ExternalItemWebUtils.createExternalItemsResultFromListBase(instances, externalItemDtos);
            return result;
        } catch (MetamacException e) {
            throw WebExceptionUtils.createMetamacWebException(e);
        }
    }

    private ExternalItemDto buildExternalItemDtoFromResource(ResourceInternal resource, TypeExternalArtefactsEnum type) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(resource.getId());
        externalItemDto.setCodeNested(resource.getNestedId());
        externalItemDto.setUri(resource.getSelfLink().getHref());
        externalItemDto.setUrn(resource.getUrn());
        externalItemDto.setType(type);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(resource.getName()));
        externalItemDto.setManagementAppUrl(resource.getManagementAppLink());
        return externalItemDto;
    }

    private List<ExternalItemDto> buildExternalItemDtosFromResources(List<ResourceInternal> resources, TypeExternalArtefactsEnum type) {
        List<ExternalItemDto> results = new ArrayList<ExternalItemDto>();
        for (ResourceInternal resource : resources) {
            results.add(buildExternalItemDtoFromResource(resource, type));
        }
        return results;
    }

    private ExternalItemDto buildExternalItemDtoFromOperation(Operation operation) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(operation.getId());
        externalItemDto.setUri(operation.getSelfLink().getHref());
        externalItemDto.setUrn(operation.getUrn());
        externalItemDto.setType(TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(operation.getName()));
        externalItemDto.setManagementAppUrl(operation.getManagementAppLink());
        return externalItemDto;
    }

}
