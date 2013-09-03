package org.siemac.metamac.statistical.resources.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.common_metadata.rest.external.v1_0.service.CommonMetadataV1_0;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.CommonServiceExceptionType;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.api.constants.RestApiConstants;
import org.siemac.metamac.srm.rest.internal.v1_0.service.SrmRestInternalFacadeV10;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.RelatedResourceUtils;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.MetamacApisLocator;
import org.siemac.metamac.statistical.resources.core.utils.CommonMetadataRestInternalFacadeV10MockUtils;
import org.siemac.metamac.statistical.resources.core.utils.SrmRestInternalFacadeV10MockUtils;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalOperationsRestInternalFacadeV10MockUtils;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesCollectionUtils;
import org.siemac.metamac.statistical.resources.core.utils.transformers.ExternalItemToUrnTransformer;
import org.siemac.metamac.statistical_operations.rest.internal.v1_0.service.StatisticalOperationsRestInternalFacadeV10;
import org.springframework.beans.factory.annotation.Autowired;

public class StatisticalResourcesMockRestBaseTest extends StatisticalResourcesBaseTest {

    @Mock
    private SrmRestInternalFacadeV10                   srmRestInternalFacadeV10;

    @Mock
    private CommonMetadataV1_0                         commonMetadataV10;

    @Mock
    private StatisticalOperationsRestInternalFacadeV10 statisticalOperationsRestInternalFacadeV10;

    @Autowired
    private MetamacApisLocator                         metamacApisLocator;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        mockApis();
    }

    private void mockApis() {
        Mockito.when(metamacApisLocator.getStatisticalOperationsRestInternalFacadeV10()).thenReturn(statisticalOperationsRestInternalFacadeV10);
        Mockito.when(metamacApisLocator.getCommonMetadataRestExternalFacadeV10()).thenReturn(commonMetadataV10);
        Mockito.when(metamacApisLocator.getSrmRestInternalFacadeV10()).thenReturn(srmRestInternalFacadeV10);
    }

    protected void mockExternalItemPublished(ExternalItem item) {
        if (item != null) {
            mockExternalItemsPublished(Arrays.asList(item));
        }
    }

    protected void mockExternalItemNotPublished(ExternalItem item) {
        if (item != null) {
            mockExternalItemsNotPublished(Arrays.asList(item));
        }
    }

    protected void mockExternalItemsPublished(Collection<ExternalItem> items) {
        mockExternalItemsPublished(items, items);
    }

    protected void mockExternalItemsNotPublished(Collection<ExternalItem> items) {
        mockExternalItemsPublished(items, new ArrayList<ExternalItem>());
    }

    private void mockExternalItemsPublished(Collection<ExternalItem> allItems, Collection<ExternalItem> publishedItems) {
        if (allItems != null && allItems.size() > 0) {
            TypeExternalArtefactsEnum typeExternalItem = allItems.iterator().next().getType();
            switch (typeExternalItem) {
                case ORGANISATION_UNIT:
                case AGENCY:
                    mockFindPublishedOrganisations(allItems, publishedItems);
                    break;
                case CODE:
                    mockFindPublishedCodes(allItems, publishedItems);
                    break;
                case CONCEPT:
                    mockFindPublishedConcepts(allItems, publishedItems);
                    break;
                case DATASTRUCTURE:
                    mockFindPublishedDsd(allItems, publishedItems);
                    break;
                case CONFIGURATION:
                    mockFindPublishedConfiguration(allItems, publishedItems);
                    break;
                case STATISTICAL_OPERATION:
                    mockFindPublishedStatisticalOperation(allItems, publishedItems);
                    break;
                case STATISTICAL_OPERATION_INSTANCE:
                    mockFindPublishedStatisticalOperationInstances(allItems, publishedItems);
                    break;
                default:
                    throw new RuntimeException("Found unknown external item type " + typeExternalItem);
            }
        }
    }

    private void mockFindPublishedOrganisations(Collection<ExternalItem> allItems, Collection<ExternalItem> publishedItems) {
        List<String> urns = getUrnsFromExternalItems(allItems);
        List<String> publishedUrns = getUrnsFromExternalItems(publishedItems);
        Mockito.when(
                srmRestInternalFacadeV10.findOrganisations(Mockito.eq(RestApiConstants.WILDCARD_ALL), Mockito.eq(RestApiConstants.WILDCARD_ALL), Mockito.eq(RestApiConstants.WILDCARD_ALL),
                        Mockito.eq(SrmRestInternalFacadeV10MockUtils.mockQueryFindPublishedOrganisationsUrnsAsList(urns)), Mockito.isNull(String.class), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(SrmRestInternalFacadeV10MockUtils.mockOrganisationsWithOnlyUrns(publishedUrns));
    }

    protected void mockFindPublishedCodes(Collection<ExternalItem> allItems, Collection<ExternalItem> publishedItems) {
        List<String> urns = getUrnsFromExternalItems(allItems);
        List<String> urnsPublished = getUrnsFromExternalItems(publishedItems);
        Mockito.when(
                srmRestInternalFacadeV10.findCodes(Mockito.eq(RestApiConstants.WILDCARD_ALL), Mockito.eq(RestApiConstants.WILDCARD_ALL), Mockito.eq(RestApiConstants.WILDCARD_ALL),
                        Mockito.eq(SrmRestInternalFacadeV10MockUtils.mockQueryFindPublishedCodesUrnsAsList(urns)), Mockito.isNull(String.class), Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyString())).thenReturn(SrmRestInternalFacadeV10MockUtils.mockCodesWithOnlyUrns(urnsPublished));
    }

    protected void mockFindPublishedConcepts(Collection<ExternalItem> allItems, Collection<ExternalItem> publishedItems) {
        List<String> urns = getUrnsFromExternalItems(allItems);
        List<String> publishedUrns = getUrnsFromExternalItems(publishedItems);
        Mockito.when(
                srmRestInternalFacadeV10.findConcepts(Mockito.eq(RestApiConstants.WILDCARD_ALL), Mockito.eq(RestApiConstants.WILDCARD_ALL), Mockito.eq(RestApiConstants.WILDCARD_ALL),
                        Mockito.eq(SrmRestInternalFacadeV10MockUtils.mockQueryFindPublishedConceptsUrnsAsList(urns)), Mockito.isNull(String.class), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(SrmRestInternalFacadeV10MockUtils.mockConceptsWithOnlyUrns(publishedUrns));
    }

    protected void mockFindPublishedDsd(Collection<ExternalItem> allItems, Collection<ExternalItem> publishedItems) {
        List<String> urns = getUrnsFromExternalItems(allItems);
        List<String> publishedUrns = getUrnsFromExternalItems(publishedItems);
        Mockito.when(
                srmRestInternalFacadeV10.findDataStructures(Mockito.eq(SrmRestInternalFacadeV10MockUtils.mockQueryFindPublishedDsdsUrnsAsList(urns)), Mockito.isNull(String.class),
                        Mockito.anyString(), Mockito.anyString())).thenReturn(SrmRestInternalFacadeV10MockUtils.mockDsdsWithOnlyUrns(publishedUrns));
    }

    protected void mockFindPublishedConfiguration(Collection<ExternalItem> allItems, Collection<ExternalItem> publishedItems) {
        List<String> urns = getUrnsFromExternalItems(allItems);
        List<String> publishedUrns = getUrnsFromExternalItems(publishedItems);
        Mockito.when(commonMetadataV10.findConfigurations(Mockito.eq(CommonMetadataRestInternalFacadeV10MockUtils.mockQueryFindPublishedConfigurationsUrnsAsList(urns)), Mockito.isNull(String.class)))
                .thenReturn(CommonMetadataRestInternalFacadeV10MockUtils.mockConfigurationsWithOnlyUrns(publishedUrns));
    }

    protected void mockFindPublishedStatisticalOperation(Collection<ExternalItem> allItems, Collection<ExternalItem> publishedItems) {
        List<String> urns = getUrnsFromExternalItems(allItems);
        List<String> publishedUrns = getUrnsFromExternalItems(publishedItems);
        Mockito.when(
                statisticalOperationsRestInternalFacadeV10.findOperations(Mockito.eq(StatisticalOperationsRestInternalFacadeV10MockUtils.mockQueryFindPublishedStatisticalOperationsUrnsAsList(urns)),
                        Mockito.isNull(String.class), Mockito.anyString(), Mockito.anyString())).thenReturn(
                StatisticalOperationsRestInternalFacadeV10MockUtils.mockStatisticalOperationsWithOnlyUrns(publishedUrns));
    }

    protected void mockFindPublishedStatisticalOperationInstances(Collection<ExternalItem> allItems, Collection<ExternalItem> publishedItems) {
        List<String> urns = getUrnsFromExternalItems(allItems);
        List<String> publishedUrns = getUrnsFromExternalItems(publishedItems);
        Mockito.when(
                statisticalOperationsRestInternalFacadeV10.findInstances(Mockito.eq(RestApiConstants.WILDCARD_ALL),
                        Mockito.eq(StatisticalOperationsRestInternalFacadeV10MockUtils.mockQueryFindPublishedStatisticalOperationInstancesUrnsAsList(urns)), Mockito.isNull(String.class),
                        Mockito.anyString(), Mockito.anyString())).thenReturn(StatisticalOperationsRestInternalFacadeV10MockUtils.mockStatisticalOperationInstancesWithOnlyUrns(publishedUrns));
    }

    protected List<String> getUrnsFromExternalItems(Collection<ExternalItem> items) {
        List<String> urns = new ArrayList<String>();
        StatisticalResourcesCollectionUtils.mapCollection(items, urns, new ExternalItemToUrnTransformer());
        return urns;
    }

    protected List<MetamacExceptionItem> buildExternalItemNotPublishedExceptionForAllItems(List<ExternalItem> items, String field) {
        return buildExpectedExceptionForAllItems(ServiceExceptionType.EXTERNAL_ITEM_NOT_PUBLISHED, items, field);
    }

    protected List<MetamacExceptionItem> buildExpectedExceptionForAllItems(CommonServiceExceptionType exceptionType, Collection<ExternalItem> items, String messageParameter) {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        for (ExternalItem item : items) {
            exceptionItems.add(new MetamacExceptionItem(exceptionType, messageParameter, item.getUrn()));
        }
        return exceptionItems;
    }

    protected List<MetamacExceptionItem> getExceptionItemsForExternalItemNotPublishedSiemac(SiemacMetadataStatisticalResource siemacResource, String baseField) {
        String prefix = buildField(baseField, "siemacMetadataStatisticalResource");
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        exceptionItems.add(buildExternalItemNotPublishedException(siemacResource.getStatisticalOperation(), prefix, "statisticalOperation"));
        exceptionItems.add(buildExternalItemNotPublishedException(siemacResource.getMaintainer(), prefix, "maintainer"));
        exceptionItems.add(buildExternalItemNotPublishedException(siemacResource.getLanguage(), prefix, "language"));
        exceptionItems.addAll(buildExternalItemsNotPublishedExceptions(siemacResource.getLanguages(), prefix, "languages"));
        exceptionItems.addAll(buildExternalItemsNotPublishedExceptions(siemacResource.getStatisticalOperationInstances(), prefix, "statisticalOperationInstances"));
        exceptionItems.add(buildExternalItemNotPublishedException(siemacResource.getCreator(), prefix, "creator"));
        exceptionItems.addAll(buildExternalItemsNotPublishedExceptions(siemacResource.getContributor(), prefix, "contributor"));
        exceptionItems.addAll(buildExternalItemsNotPublishedExceptions(siemacResource.getPublisher(), prefix, "publisher"));
        exceptionItems.addAll(buildExternalItemsNotPublishedExceptions(siemacResource.getPublisherContributor(), prefix, "publisherContributor"));
        exceptionItems.addAll(buildExternalItemsNotPublishedExceptions(siemacResource.getMediator(), prefix, "mediator"));
        exceptionItems.add(buildExternalItemNotPublishedException(siemacResource.getCommonMetadata(), prefix, "commonMetadata"));

        return exceptionItems;
    }

    protected MetamacExceptionItem buildExternalItemNotPublishedException(ExternalItem item, String entity, String field) {
        return new MetamacExceptionItem(ServiceExceptionType.EXTERNAL_ITEM_NOT_PUBLISHED, buildField(entity, field), item.getUrn());
    }

    protected MetamacExceptionItem buildRelatedResourceNotPublishedException(RelatedResource resource, String entity, String field) {
        NameableStatisticalResource nameable = null;
        try {
            nameable = RelatedResourceUtils.retrieveNameableResourceLinkedToRelatedResource(resource);
        } catch (MetamacException e) {
            Assert.fail("Could not retrieve nameable form related resource " + resource.getId());
        }
        return new MetamacExceptionItem(ServiceExceptionType.RELATED_RESOURCE_NOT_PUBLISHED, buildField(entity, field), nameable.getUrn());
    }

    protected List<MetamacExceptionItem> buildExternalItemsNotPublishedExceptions(Collection<ExternalItem> items, String entity, String field) {
        return buildExpectedExceptionForAllItems(ServiceExceptionType.EXTERNAL_ITEM_NOT_PUBLISHED, items, buildField(entity, field));
    }

    private String buildField(String... fields) {
        return StringUtils.join(fields, ".");
    }

    protected void mockSiemacExternalItemsNotPublished(SiemacMetadataStatisticalResource siemacResource) {
        // Lifecycle
        mockExternalItemNotPublished(siemacResource.getStatisticalOperation());
        mockExternalItemNotPublished(siemacResource.getMaintainer());

        // Siemac
        mockExternalItemNotPublished(siemacResource.getLanguage());
        mockExternalItemsNotPublished(siemacResource.getLanguages());
        mockExternalItemsNotPublished(siemacResource.getStatisticalOperationInstances());
        mockExternalItemNotPublished(siemacResource.getCreator());
        mockExternalItemsNotPublished(siemacResource.getContributor());
        mockExternalItemsNotPublished(siemacResource.getPublisher());
        mockExternalItemsNotPublished(siemacResource.getPublisherContributor());
        mockExternalItemsNotPublished(siemacResource.getMediator());
        mockExternalItemNotPublished(siemacResource.getCommonMetadata());
    }

    protected void mockSiemacExternalItemsPublished(SiemacMetadataStatisticalResource siemacResource) {
        // Lifecycle
        mockExternalItemPublished(siemacResource.getStatisticalOperation());
        mockExternalItemPublished(siemacResource.getMaintainer());

        // Siemac
        mockExternalItemPublished(siemacResource.getLanguage());
        mockExternalItemsPublished(siemacResource.getLanguages());
        mockExternalItemsPublished(siemacResource.getStatisticalOperationInstances());
        mockExternalItemPublished(siemacResource.getCreator());
        mockExternalItemsPublished(siemacResource.getContributor());
        mockExternalItemsPublished(siemacResource.getPublisher());
        mockExternalItemsPublished(siemacResource.getPublisherContributor());
        mockExternalItemsPublished(siemacResource.getMediator());
        mockExternalItemPublished(siemacResource.getCommonMetadata());
    }

}
