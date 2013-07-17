package org.siemac.metamac.statistical.resources.core.invocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.api.constants.RestApiConstants;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatisticalOperationsRestInternalServiceImpl implements StatisticalOperationsRestInternalService {

    @Autowired
    private MetamacApisLocator restApiLocator;

    // ---------------------------------------------------------------------------------
    // OPERATIONS
    // ---------------------------------------------------------------------------------

    @Override
    public Operation retrieveOperationById(String operationCode) throws MetamacException {
        try {
            return restApiLocator.getStatisticalOperationsRestInternalFacadeV10().retrieveOperationById(operationCode);
        } catch (Exception e) {
            throw manageStatisticalOperationsInternalRestException(e);
        }
    }

    @Override
    public Operations findOperations(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            String limit = String.valueOf(maxResult);
            String offset = String.valueOf(firstResult);
            String orderBy = null;
            return restApiLocator.getStatisticalOperationsRestInternalFacadeV10().findOperations(query, orderBy, limit, offset);
        } catch (Exception e) {
            throw manageStatisticalOperationsInternalRestException(e);
        }
    }

    @Override
    public List<String> findOperationsUrns(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            Operations operations = findOperations(firstResult, maxResult, query);
            List<String> urns = new ArrayList<String>();
            for (ResourceInternal resource : operations.getOperations()) {
                urns.add(resource.getUrn());
            }
            return urns;
        } catch (Exception e) {
            throw manageStatisticalOperationsInternalRestException(e);
        }
    }

    @Override
    public List<ExternalItem> findOperationsAsExternalItems(int firstResult, int maxResult, String query) throws MetamacException {
        try {
            Operations operations = findOperations(firstResult, maxResult, query);
            List<ExternalItem> externalItems = new ArrayList<ExternalItem>();
            for (ResourceInternal resource : operations.getOperations()) {
                ExternalItem item = new ExternalItem();
                item.setCode(resource.getId());
                item.setManagementAppUrl(resource.getManagementAppLink());
                item.setTitle(apiInternationalStringToappInternationalString(resource.getName()));
                item.setType(TypeExternalArtefactsEnum.valueOf(resource.getKind()));
                item.setUri(resource.getSelfLink().toString());
                item.setUrn(resource.getUrn());
                item.setCodeNested(resource.getNestedId());
            }
            return externalItems;
        } catch (Exception e) {
            throw manageStatisticalOperationsInternalRestException(e);
        }
    }

    private InternationalString apiInternationalStringToappInternationalString(org.siemac.metamac.rest.common.v1_0.domain.InternationalString apiInternationalString) {
        InternationalString internationalString = new InternationalString();
        internationalString.getTexts().addAll(apiLocalisedStringsToAppLocalisedStrings(apiInternationalString.getTexts()));
        return internationalString;
    }

    private Collection<LocalisedString> apiLocalisedStringsToAppLocalisedStrings(List<org.siemac.metamac.rest.common.v1_0.domain.LocalisedString> texts) {
        Set<LocalisedString> appLocalisedStrings = new HashSet<LocalisedString>();
        for (org.siemac.metamac.rest.common.v1_0.domain.LocalisedString apiLocalisedString : texts) {
            LocalisedString item = new LocalisedString();
            item.setLabel(apiLocalisedString.getValue());
            item.setLocale(apiLocalisedString.getValue());
            appLocalisedStrings.add(item);
        }
        return appLocalisedStrings;
    }

    // ---------------------------------------------------------------------------------
    // INSTANCES
    // ---------------------------------------------------------------------------------

    @Override
    public Instances findInstances(String operationId, int firstResult, int maxResult, String query) {
        String limit = String.valueOf(maxResult);
        String offset = String.valueOf(firstResult);
        String orderBy = null;

        if (StringUtils.isBlank(operationId)) {
            operationId = RestApiConstants.WILDCARD_ALL;
        }
        return restApiLocator.getStatisticalOperationsRestInternalFacadeV10().findInstances(operationId, query, orderBy, limit, offset);
    }

    @Override
    public List<String> findInstancesUrns(String operationId, int firstResult, int maxResult, String query) {
        Instances instances = findInstances(operationId, firstResult, maxResult, query);
        List<String> ids = new ArrayList<String>();
        for (ResourceInternal resource : instances.getInstances()) {
            ids.add(resource.getUrn());
        }
        return ids;
    }

    private MetamacException manageStatisticalOperationsInternalRestException(Exception e) throws MetamacException {
        return ServiceExceptionUtils.manageMetamacRestException(e, ServiceExceptionParameters.API_STATISTICAL_OPERATIONS_INTERNAL, restApiLocator.getStatisticalOperationsRestInternalFacadeV10());
    }
}
