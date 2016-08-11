package org.siemac.metamac.statistical.resources.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.InstanceCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Instances;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.OperationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operations;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ProcStatus;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.ResourceInternal;

public class StatisticalOperationsRestInternalFacadeV10MockUtils extends RestMockUtils {

    public static Operations mockStatisticalOperationsWithOnlyUrns(List<String> urns) {
        Operations operations = new Operations();
        operations.getOperations().addAll(mockResourceInternalsWithOnlyUrns(urns));
        populateListBaseWithResourcesWithOnlyUrns(operations, urns);
        return operations;
    }

    public static Instances mockStatisticalOperationInstancesWithOnlyUrns(List<String> urns) {
        Instances instances = new Instances();
        instances.getInstances().addAll(mockResourceInternalsWithOnlyUrns(urns));
        populateListBaseWithResourcesWithOnlyUrns(instances, urns);
        return instances;
    }

    private static List<ResourceInternal> mockResourceInternalsWithOnlyUrns(List<String> urns) {
        List<ResourceInternal> resources = new ArrayList<ResourceInternal>();
        for (String urn : urns) {
            ResourceInternal resource = new ResourceInternal();
            resource.setUrn(urn);
            resources.add(resource);
        }
        return resources;
    }

    public static String mockQueryFindPublishedStatisticalOperationsUrnsAsList(List<String> urns) {
        return mockQueryFindPublishedResourcesUrnsAsList(OperationCriteriaPropertyRestriction.URN, OperationCriteriaPropertyRestriction.PROC_STATUS, ProcStatus.EXTERNALLY_PUBLISHED.toString(), urns);
    }

    public static String mockQueryFindPublishedStatisticalOperationInstancesUrnsAsList(List<String> urns) {
        return mockQueryFindPublishedResourcesUrnsAsList(InstanceCriteriaPropertyRestriction.URN, InstanceCriteriaPropertyRestriction.PROC_STATUS, ProcStatus.EXTERNALLY_PUBLISHED.toString(), urns);
    }

}
