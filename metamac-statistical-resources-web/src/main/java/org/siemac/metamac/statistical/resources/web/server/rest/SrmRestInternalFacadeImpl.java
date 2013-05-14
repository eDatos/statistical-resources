package org.siemac.metamac.statistical.resources.web.server.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructureCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructures;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SrmRestInternalFacadeImpl implements SrmRestInternalFacade {

    @Autowired
    private SrmRestInternalService srmRestInternalService;
    

    @Override
    public List<ExternalItemDto> findDsds(int firstResult, int maxResult, DsdWebCriteria criteria) {
        String query = buildQuery(criteria);
        
        DataStructures structures = srmRestInternalService.findDsds(firstResult, maxResult, query);
        
        List<ExternalItemDto> dsdsExternalItems = new ArrayList<ExternalItemDto>(); 
        for (Resource resource : structures.getDataStructures()) {
            dsdsExternalItems.add(buildExternalItemDtoFromResource(resource, TypeExternalArtefactsEnum.DATASTRUCTURE));
        }
        return dsdsExternalItems;
    }
    
    private ExternalItemDto buildExternalItemDtoFromResource(Resource resource, TypeExternalArtefactsEnum type) {
        return new ExternalItemDto(resource.getId(), resource.getSelfLink().getHref(), resource.getUrn(), type,
                DtoUtils.getInternationalStringDtoFromInternationalString(resource.getTitle()));
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
    
    private void appendConditionToQuery(StringBuilder queryBuilder, String condition) {
        if (queryBuilder.length() > 0) {
            queryBuilder.append(" ").append(LogicalOperator.AND.name()).append(" ");
        }
        queryBuilder.append(condition);
    }
    
    private String fieldComparison(DataStructureCriteriaPropertyRestriction field, ComparisonOperator operator, Object value) {
        StringBuilder conditionBuilder = new StringBuilder();
        conditionBuilder.append(field)
                        .append(" ").append(operator.name()).append(" ");
        conditionBuilder.append("\"").append(value).append("\"");
        return conditionBuilder.toString();
    }
}