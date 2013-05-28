package org.siemac.metamac.statistical.resources.web.server.rest;

import static org.siemac.metamac.statistical.resources.web.server.rest.utils.RestCriteriaUtils.appendConditionToQuery;
import static org.siemac.metamac.statistical.resources.web.server.rest.utils.RestCriteriaUtils.fieldComparison;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.ServerWebApplicationException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.CommonMetadataStatus;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ConfigurationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.web.common.server.utils.DtoUtils;
import org.siemac.metamac.web.common.server.utils.WebExceptionUtils;
import org.siemac.metamac.web.common.shared.constants.CommonSharedConstants;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonMetadataRestExternalFacadeImpl implements CommonMetadataRestExternalFacade {

    @Autowired
    private RestApiLocator restApiLocator;
    
    @Override
    public List<ExternalItemDto> findConfigurations(CommonConfigurationWebCriteria criteria) throws MetamacWebException {
        String query = buildQuery(criteria);
        try {
           Configurations configurations = restApiLocator.getCommonMetadataRestExternalFacadeV10().findConfigurations(query, null);
           List<ExternalItemDto> items = new ArrayList<ExternalItemDto>();
           for (ResourceInternal resource : configurations.getConfigurations()) {
               items.add(getExternalItemDtoFromConfiguration(resource));
           }
           return items;
        } catch (ServerWebApplicationException e) {
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = e.toErrorObject(WebClient.client(restApiLocator.getCommonMetadataRestExternalFacadeV10()),
                    org.siemac.metamac.rest.common.v1_0.domain.Exception.class);
            throw WebExceptionUtils.createMetamacWebException(exception);
        } catch (Exception e) {
            throw new MetamacWebException(CommonSharedConstants.EXCEPTION_UNKNOWN, StatisticalResourcesWeb.getCoreMessages().exception_common_unknown());
        }
    }
    
    private String buildQuery(CommonConfigurationWebCriteria webCriteria) {
        StringBuilder queryBuilder = new StringBuilder();
        if (webCriteria != null) {
            String simpleCriteria = webCriteria.getCriteria();
            boolean onlyEnabled = webCriteria.isOnlyEnabled();
            if (StringUtils.isNotBlank(simpleCriteria)) {
                StringBuilder conditionBuilder = new StringBuilder();

                String urnCondition = fieldComparison(ConfigurationCriteriaPropertyRestriction.URN, ComparisonOperator.ILIKE, simpleCriteria);
                String idCondition = fieldComparison(ConfigurationCriteriaPropertyRestriction.ID, ComparisonOperator.ILIKE, simpleCriteria);
                
                conditionBuilder.append("(")
                                .append(idCondition)
                                .append(" ").append(LogicalOperator.OR).append(" ")
                                .append(urnCondition)
                                .append(")");
                appendConditionToQuery(queryBuilder, conditionBuilder.toString());
            }
            
            if (onlyEnabled) {
                String lastVersionCondition = fieldComparison(ConfigurationCriteriaPropertyRestriction.STATUS, ComparisonOperator.EQ, CommonMetadataStatus.ENABLED);
                appendConditionToQuery(queryBuilder, lastVersionCondition);
            }
        }
        return queryBuilder.toString();
    }
    
    
    private ExternalItemDto getExternalItemDtoFromConfiguration(Resource configuration) {
        ExternalItemDto item = new ExternalItemDto();
        item.setCode(configuration.getId());
        item.setUri(configuration.getSelfLink().getHref());
        item.setUrn(configuration.getUrn());
        item.setType(TypeExternalArtefactsEnum.CONFIGURATION);
        item.setTitle(DtoUtils.getInternationalStringDtoFromInternationalString(configuration.getTitle()));
        return item;
    }

}
