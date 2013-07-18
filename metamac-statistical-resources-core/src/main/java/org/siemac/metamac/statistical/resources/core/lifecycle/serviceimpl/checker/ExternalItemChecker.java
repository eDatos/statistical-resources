package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.checker;

import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;
import static org.siemac.metamac.statistical.resources.core.invocation.utils.RestCriteriaUtils.appendConditionToQuery;
import static org.siemac.metamac.statistical.resources.core.invocation.utils.RestCriteriaUtils.fieldComparison;
import static org.siemac.metamac.statistical.resources.core.invocation.utils.RestCriteriaUtils.processExternalItemsUrns;
import static org.siemac.metamac.statistical.resources.core.invocation.utils.RestCriteriaUtils.processExternalItemsUrnsInternal;
import static org.siemac.metamac.statistical.resources.core.invocation.utils.RestCriteriaUtils.transformListIntoQuotedCommaSeparatedString;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ConfigurationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.InstanceCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.OperationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategoryCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CategorySchemeCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodelistCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptSchemeCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructureCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.OrganisationSchemeCriteriaPropertyRestriction;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.CommonMetadataRestExternalService;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.invocation.StatisticalOperationsRestInternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExternalItemChecker {
    
    @Autowired
    private SrmRestInternalService                   srmRestInternalService;

    @Autowired
    private StatisticalOperationsRestInternalService statisticalOperationsRestInternalService;

    @Autowired
    private CommonMetadataRestExternalService        commonMetadataRestExternalService;

    private enum ResultType {
        URNS, URNS_INTENRAL
    }

    public void checkExternalItemsExternallyPublished(ExternalItem externalItem, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        checkExternalItemsExternallyPublished(Arrays.asList(externalItem), addParameter(metadataName, ServiceExceptionSingleParameters.STATISTICAL_OPERATION), exceptionItems);
    }

    public void checkExternalItemsExternallyPublished(List<ExternalItem> externalItems, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        TypeExternalArtefactsEnum externalItemType = externalItems.get(0).getType(); // we know that all the externalItems of a collection have same type
        int firstResult = 0;
        int maxResult = externalItems.size();
        String query = null;
        Collection<String> result = null;

        List<String> expectedUrns = processExternalItemsUrns(externalItems);
        List<String> expectedUrnsInternal = processExternalItemsUrnsInternal(externalItems);

        switch (externalItemType) {
            case AGENCY:
                query = createQueryForPublishedResourcesSearchingByUrn(OrganisationCriteriaPropertyRestriction.URN, OrganisationCriteriaPropertyRestriction.ORGANISATION_SCHEME_PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationsUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case AGENCY_SCHEME:
                query = createQueryForPublishedResourcesSearchingByUrn(OrganisationSchemeCriteriaPropertyRestriction.URN, OrganisationSchemeCriteriaPropertyRestriction.PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationSchemesUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case CATEGORY:
                query = createQueryForPublishedResourcesSearchingByUrn(CategoryCriteriaPropertyRestriction.URN, CategoryCriteriaPropertyRestriction.CATEGORY_SCHEME_PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findCategoriesUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case CATEGORY_SCHEME:
                query = createQueryForPublishedResourcesSearchingByUrn(CategorySchemeCriteriaPropertyRestriction.URN, CategorySchemeCriteriaPropertyRestriction.PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationSchemesUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case CODE:
                query = createQueryForPublishedResourcesSearchingByUrn(CodeCriteriaPropertyRestriction.URN, CodeCriteriaPropertyRestriction.CODELIST_PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findCodesUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case CODELIST:
                query = createQueryForPublishedResourcesSearchingByUrn(CodelistCriteriaPropertyRestriction.URN, CodelistCriteriaPropertyRestriction.PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findCodelistsAsUrnsList(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case CONCEPT:
                query = createQueryForPublishedResourcesSearchingByUrn(ConceptCriteriaPropertyRestriction.URN, ConceptCriteriaPropertyRestriction.CONCEPT_SCHEME_PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findConceptsUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case CONCEPT_SCHEME:
                query = createQueryForPublishedResourcesSearchingByUrn(ConceptSchemeCriteriaPropertyRestriction.URN, ConceptSchemeCriteriaPropertyRestriction.PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationSchemesUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case CONFIGURATION:
                query = createQueryForPublishedResourcesSearchingByUrn(ConfigurationCriteriaPropertyRestriction.URN, null, null, expectedUrns);

                result = commonMetadataRestExternalService.findConfigurationsUrns(query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS, metadataName, exceptionItems);
                break;
            case DATA_CONSUMER:
                query = createQueryForPublishedResourcesSearchingByUrn(OrganisationCriteriaPropertyRestriction.URN, OrganisationCriteriaPropertyRestriction.ORGANISATION_SCHEME_PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationsUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case DATA_CONSUMER_SCHEME:
                query = createQueryForPublishedResourcesSearchingByUrn(OrganisationSchemeCriteriaPropertyRestriction.URN, OrganisationSchemeCriteriaPropertyRestriction.PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationSchemesUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case DATA_PROVIDER:
                query = createQueryForPublishedResourcesSearchingByUrn(OrganisationCriteriaPropertyRestriction.URN, OrganisationCriteriaPropertyRestriction.ORGANISATION_SCHEME_PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationsUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case DATA_PROVIDER_SCHEME:
                query = createQueryForPublishedResourcesSearchingByUrn(OrganisationSchemeCriteriaPropertyRestriction.URN, OrganisationSchemeCriteriaPropertyRestriction.PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationSchemesUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case DATASTRUCTURE:
                query = createQueryForPublishedResourcesSearchingByUrn(DataStructureCriteriaPropertyRestriction.URN, DataStructureCriteriaPropertyRestriction.PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findDsdsUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case ORGANISATION:
                query = createQueryForPublishedResourcesSearchingByUrn(OrganisationCriteriaPropertyRestriction.URN, OrganisationCriteriaPropertyRestriction.ORGANISATION_SCHEME_PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationsUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case ORGANISATION_SCHEME:
                query = createQueryForPublishedResourcesSearchingByUrn(OrganisationSchemeCriteriaPropertyRestriction.URN, OrganisationSchemeCriteriaPropertyRestriction.PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationSchemesUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case ORGANISATION_UNIT:
                query = createQueryForPublishedResourcesSearchingByUrn(OrganisationCriteriaPropertyRestriction.URN, OrganisationCriteriaPropertyRestriction.ORGANISATION_SCHEME_PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationsUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case ORGANISATION_UNIT_SCHEME:
                query = createQueryForPublishedResourcesSearchingByUrn(OrganisationSchemeCriteriaPropertyRestriction.URN, OrganisationSchemeCriteriaPropertyRestriction.PROC_STATUS,
                        org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrnsInternal);

                result = srmRestInternalService.findOrganisationSchemesUrns(firstResult, maxResult, query);
                checkResult(expectedUrnsInternal, result, ResultType.URNS_INTENRAL, metadataName, exceptionItems);
                break;
            case STATISTICAL_OPERATION:
                query = createQueryForPublishedResourcesSearchingByUrn(OperationCriteriaPropertyRestriction.URN, OperationCriteriaPropertyRestriction.PROC_STATUS,
                        ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrns);
                result = statisticalOperationsRestInternalService.findOperationsUrns(firstResult, maxResult, query);
                checkResult(expectedUrns, result, ResultType.URNS, metadataName, exceptionItems);
                break;
            case STATISTICAL_OPERATION_INSTANCE:
                query = createQueryForPublishedResourcesSearchingByUrn(InstanceCriteriaPropertyRestriction.URN, InstanceCriteriaPropertyRestriction.PROC_STATUS,
                        ProcStatus.EXTERNALLY_PUBLISHED.toString(), expectedUrns);
                result = statisticalOperationsRestInternalService.findInstancesUrns(null, firstResult, maxResult, query);
                checkResult(expectedUrns, result, ResultType.URNS, metadataName, exceptionItems);
                break;
            default:
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "Type of externalItem not supported: " + externalItemType);
        }

    }

    @SuppressWarnings("unchecked")
    private void checkResult(List<String> expectedList, Collection<String> actualList, ResultType resultType, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        Collection<String> result = CollectionUtils.disjunction(expectedList, actualList);
        if (!result.isEmpty()) {
            for (String notFoundElement : result) {
                exceptionItems.add(createSpecificExceptionItem(notFoundElement, metadataName, resultType));
            }
        }
    }

    private MetamacExceptionItem createSpecificExceptionItem(String notFoundElement, String metadataName, ResultType resultType) throws MetamacException {
        switch (resultType) {
            case URNS:
                return new MetamacExceptionItem(ServiceExceptionType.EXTERNAL_ITEM_URN_NOT_PUBLISHED, metadataName, notFoundElement);
            case URNS_INTENRAL:
                return new MetamacExceptionItem(ServiceExceptionType.EXTERNAL_ITEM_URN_INTERNAL_NOT_PUBLISHED, metadataName, notFoundElement);
            default:
                throw new MetamacException(ServiceExceptionType.UNKNOWN, "unknown type of resource id for create specific exception item: " + resultType);
        }
    }

    @SuppressWarnings("rawtypes")
    private String createQueryForPublishedResourcesSearchingByUrn(Enum urnPropertyName, Enum procStatusPropertyName, String procStatusValue, List<String> ids) {
        StringBuilder query = new StringBuilder();
        appendConditionToQuery(query, fieldComparison(urnPropertyName, ComparisonOperator.IN, transformListIntoQuotedCommaSeparatedString(ids)));
        if (procStatusPropertyName != null) {
            appendConditionToQuery(query, fieldComparison(procStatusPropertyName, ComparisonOperator.EQ, procStatusValue));
        }
        return query.toString();
    }

}