package org.siemac.metamac.statistical.resources.core.utils;

import static org.junit.Assert.assertTrue;
import static org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesCollectionUtils.mapExternalItemsToUrnsList;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.siemac.metamac.rest.api.utils.RestCriteriaUtils;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class StatisticalResourcesCollectionUtilsTest extends StatisticalResourcesBaseTest {

    @Test
    public void testProcessExternalItemsUrns() throws Exception {
        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());

        List<String> expectedUrns = new ArrayList<String>();
        expectedUrns.add(externalItems.get(0).getUrn());
        expectedUrns.add(externalItems.get(1).getUrn());
        expectedUrns.add(externalItems.get(2).getUrn());
        expectedUrns.add(externalItems.get(3).getUrn());

        List<String> actualUrns = mapExternalItemsToUrnsList(externalItems);

        assertTrue(CollectionUtils.isEqualCollection(expectedUrns, actualUrns));
    }

    @Test
    public void testProcessExternalItemsUrnsErrorNotEqualsMinusItems() throws Exception {
        thrown.expect(AssertionError.class);

        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());

        List<String> expectedUrns = new ArrayList<String>();
        expectedUrns.add(externalItems.get(0).getUrn());
        expectedUrns.add(externalItems.get(1).getUrn());
        expectedUrns.add(externalItems.get(2).getUrn());

        List<String> actualUrns = mapExternalItemsToUrnsList(externalItems);

        assertTrue(CollectionUtils.isEqualCollection(expectedUrns, actualUrns));
    }

    @Test
    public void testProcessExternalItemsUrnsErrorNotEqualsMoreItems() throws Exception {
        thrown.expect(AssertionError.class);

        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());

        List<String> expectedUrns = new ArrayList<String>();
        expectedUrns.add(externalItems.get(0).getUrn());
        expectedUrns.add(externalItems.get(1).getUrn());
        expectedUrns.add(externalItems.get(2).getUrn());
        expectedUrns.add(externalItems.get(3).getUrn());
        expectedUrns.add("element-not-exists");

        List<String> actualUrns = mapExternalItemsToUrnsList(externalItems);

        assertTrue(CollectionUtils.isEqualCollection(expectedUrns, actualUrns));
    }

    @Test
    public void testProcessExternalItemsUrnsErrorNotDifferentItems() throws Exception {
        thrown.expect(AssertionError.class);

        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());
        externalItems.add(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem());

        List<String> expectedUrns = new ArrayList<String>();
        expectedUrns.add(externalItems.get(0).getUrn());
        expectedUrns.add(externalItems.get(1).getUrn());
        expectedUrns.add(externalItems.get(2).getUrn());
        expectedUrns.add("different-element");

        List<String> actualUrns = mapExternalItemsToUrnsList(externalItems);

        assertTrue(CollectionUtils.isEqualCollection(expectedUrns, actualUrns));
    }
}
