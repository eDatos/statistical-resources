package org.siemac.metamac.statistical.resources.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.rest.common_metadata.v1_0.domain.ConfigurationCriteriaPropertyRestriction;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configurations;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal;

public class CommonMetadataRestInternalFacadeV10MockUtils extends RestMockUtils {

    public static Configurations mockConfigurationsWithOnlyUrns(List<String> urns) {
        Configurations configurations = new Configurations();
        configurations.getConfigurations().addAll(mockResourcesInternalWithOnlyUrns(urns));
        populateListBaseWithResourcesWithOnlyUrns(configurations, urns);
        return configurations;
    }

    private static List<ResourceInternal> mockResourcesInternalWithOnlyUrns(List<String> urns) {
        List<ResourceInternal> resources = new ArrayList<ResourceInternal>();
        for (String urn : urns) {
            ResourceInternal resource = new ResourceInternal();
            resource.setUrn(urn);
            resources.add(resource);
        }
        return resources;
    }

    public static String mockQueryFindPublishedConfigurationsUrnsAsList(List<String> urns) {
        return mockQueryFindPublishedResourcesUrnsAsList(ConfigurationCriteriaPropertyRestriction.URN, null, null, urns);
    }

}
