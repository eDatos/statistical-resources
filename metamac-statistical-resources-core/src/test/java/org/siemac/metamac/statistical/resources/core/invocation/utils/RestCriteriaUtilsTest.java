package org.siemac.metamac.statistical.resources.core.invocation.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.rest.common.v1_0.domain.ComparisonOperator;
import org.siemac.metamac.rest.common.v1_0.domain.LogicalOperator;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.criteria.enums.StatisticalResourcesCriteriaPropertyEnum;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class RestCriteriaUtilsTest extends StatisticalResourcesBaseTest {
    
    @SuppressWarnings("rawtypes")
    @Test
    public void testFieldComparison() throws Exception {
        Enum field = StatisticalResourcesCriteriaPropertyEnum.CODE;
        ComparisonOperator operator = ComparisonOperator.EQ;
        String value = "code01";
        String expectedResult = "CODE EQ 'code01'";
        String actualResult = RestCriteriaUtils.fieldComparison(field, operator, value);
        assertEquals(expectedResult, actualResult);
    }
    
    @SuppressWarnings("rawtypes")
    @Test
    public void testFieldComparisonUsingInOperator() throws Exception {
        Enum field = StatisticalResourcesCriteriaPropertyEnum.CODE;
        ComparisonOperator operator = ComparisonOperator.IN;
        String value = "'code01','code02','code03'";
        String expectedResult = "CODE IN ('code01','code02','code03')";
        String actualResult = RestCriteriaUtils.fieldComparison(field, operator, value);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testAppendConditionToQueryWithoutLogicalOperator() throws Exception {
        StringBuilder initialString = new StringBuilder();
        String condition = "field01 like '%element01'";
        String expected = "field01 like '%element01'";
        RestCriteriaUtils.appendConditionToQuery(initialString, condition);
        assertEquals(expected, initialString.toString());
    }
    
    @Test
    public void testAppendConditionToQueryWithoutLogicalOperatorWithInitialString() throws Exception {
        StringBuilder initialString = new StringBuilder("field01 like '%element01'");
        String condition = "field02 like '%element02'";
        String expected = "field01 like '%element01' AND field02 like '%element02'";
        RestCriteriaUtils.appendConditionToQuery(initialString, condition);
        assertEquals(expected, initialString.toString());
    }

    @Test
    public void testAppendConditionToQueryWithLogicalOperator() throws Exception {
        StringBuilder initialString = new StringBuilder();
        String condition = "field01 like '%element01'";
        String expected = "field01 like '%element01'";
        RestCriteriaUtils.appendConditionToQuery(initialString, LogicalOperator.OR, condition);
        assertEquals(expected, initialString.toString());
    }
    
    @Test
    public void testAppendConditionToQueryWithLogicalOperatorWithInitialString() throws Exception {
        StringBuilder initialString = new StringBuilder("field01 like '%element01'");
        String condition = "field02 like '%element02'";
        String expected = "field01 like '%element01' OR field02 like '%element02'";
        RestCriteriaUtils.appendConditionToQuery(initialString, LogicalOperator.OR, condition);
        assertEquals(expected, initialString.toString());
    }

    @Test
    public void testAppendCommaSeparatedQuotedElement() throws Exception {
        StringBuilder initialString = new StringBuilder();
        String elementToAppend = "element01";
        String expectedResult = "'element01'";
        RestCriteriaUtils.appendCommaSeparatedQuotedElement(initialString, elementToAppend);
        assertEquals(expectedResult, initialString.toString());
    }
    
    @Test
    public void testAppendCommaSeparatedQuotedElementWithInitialElement() throws Exception {
        StringBuilder initialString = new StringBuilder("'element01'");
        String elementToAppend = "element02";
        String expectedResult = "'element01','element02'";
        RestCriteriaUtils.appendCommaSeparatedQuotedElement(initialString, elementToAppend);
        assertEquals(expectedResult, initialString.toString());
    }

    @Test
    public void testTransformListIntoQuotedCommaSeparatedString() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("element01");
        list.add("element02");
        list.add("element03");
        
        String expectedResult = "'element01','element02','element03'";
        String actualResult = RestCriteriaUtils.transformListIntoQuotedCommaSeparatedString(list);
        assertEquals(expectedResult, actualResult);
    }

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
        
        List<String> actualUrns = RestCriteriaUtils.processExternalItemsUrns(externalItems);
        
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
        
        List<String> actualUrns = RestCriteriaUtils.processExternalItemsUrns(externalItems);
        
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
        
        List<String> actualUrns = RestCriteriaUtils.processExternalItemsUrns(externalItems);
        
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
        
        List<String> actualUrns = RestCriteriaUtils.processExternalItemsUrns(externalItems);
        
        assertTrue(CollectionUtils.isEqualCollection(expectedUrns, actualUrns));
    }
}
