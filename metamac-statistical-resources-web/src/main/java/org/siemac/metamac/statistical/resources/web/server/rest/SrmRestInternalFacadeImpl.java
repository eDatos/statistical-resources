package org.siemac.metamac.statistical.resources.web.server.rest;

import static org.siemac.metamac.statistical.resources.web.server.rest.utils.RestCriteriaUtils.appendConditionToQuery;
import static org.siemac.metamac.statistical.resources.web.server.rest.utils.RestCriteriaUtils.fieldComparison;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructureCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructures;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.web.server.utils.ExternalItemUtils;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SrmRestInternalFacadeImpl implements SrmRestInternalFacade {

    @Autowired
    private SrmRestInternalService srmRestInternalService;
    

    @Override
    public ExternalItemsResult findDsds(int firstResult, int maxResult, DsdWebCriteria criteria) {
        String query = buildQuery(criteria);
        
        DataStructures structures = srmRestInternalService.findDsds(firstResult, maxResult, query);
        
        List<ExternalItemDto> dsdsExternalItems = new ArrayList<ExternalItemDto>(); 
        for (ResourceInternal resource : structures.getDataStructures()) {
            dsdsExternalItems.add(buildExternalItemDtoFromResource(resource, TypeExternalArtefactsEnum.DATASTRUCTURE));
        }
        return ExternalItemUtils.createExternalItemsResultFromListBase(structures, dsdsExternalItems);
    }
    
    private ExternalItemDto buildExternalItemDtoFromResource(ResourceInternal resource, TypeExternalArtefactsEnum type) {
        ExternalItemDto externalItemDto = new ExternalItemDto();
        externalItemDto.setCode(resource.getId());
        externalItemDto.setUri(resource.getSelfLink().getHref());
        externalItemDto.setUrn(resource.getUrn());
        externalItemDto.setUrnInternal(resource.getUrnInternal());
        externalItemDto.setType(TypeExternalArtefactsEnum.STATISTICAL_OPERATION);
        externalItemDto.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(resource.getName()));
        return externalItemDto;
    }

    private String buildQuery(DsdWebCriteria webCriteria) {
        StringBuilder queryBuilder = new StringBuilder();
        if (webCriteria != null) {
            String simpleCriteria = webCriteria.getCriteria();
            String operationUrn = webCriteria.getStatisticalOperationUrn();
            String dsdCode = webCriteria.getFixedDsdCode();
            boolean onlyLastVersion = webCriteria.isOnlyLastVersion();
            if (StringUtils.isNotBlank(simpleCriteria)) {
                StringBuilder conditionBuilder = new StringBuilder();

                String nameCondition = fieldComparison(DataStructureCriteriaPropertyRestriction.NAME, ComparisonOperator.ILIKE, simpleCriteria);
                String idCondition = fieldComparison(DataStructureCriteriaPropertyRestriction.ID, ComparisonOperator.ILIKE, simpleCriteria);
                String urnCondition = fieldComparison(DataStructureCriteriaPropertyRestriction.URN, ComparisonOperator.ILIKE, simpleCriteria);
                
                conditionBuilder.append("(")
                                .append(nameCondition)
                                .append(" ").append(LogicalOperator.OR).append(" ")
                                .append(idCondition)
                                .append(" ").append(LogicalOperator.OR).append(" ")
                                .append(urnCondition)
                                .append(")");
                appendConditionToQuery(queryBuilder, conditionBuilder.toString());
            }
            
            if (StringUtils.isNotBlank(dsdCode)) {
                String dsdCodeCondition = fieldComparison(DataStructureCriteriaPropertyRestriction.ID, ComparisonOperator.EQ, dsdCode);
                appendConditionToQuery(queryBuilder, dsdCodeCondition);
            }
            
            if (StringUtils.isNotBlank(operationUrn)) {
                String statOperCondition = fieldComparison(DataStructureCriteriaPropertyRestriction.STATISTICAL_OPERATION_URN, ComparisonOperator.ILIKE, operationUrn);
                appendConditionToQuery(queryBuilder, statOperCondition);
            }

            if (onlyLastVersion) {
                String lastVersionCondition = fieldComparison(DataStructureCriteriaPropertyRestriction.LATEST, ComparisonOperator.EQ, onlyLastVersion);
                appendConditionToQuery(queryBuilder, lastVersionCondition);
            }
        }
        return queryBuilder.toString();
    }

   
}