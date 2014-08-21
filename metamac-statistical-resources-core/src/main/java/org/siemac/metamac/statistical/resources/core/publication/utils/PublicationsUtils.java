package org.siemac.metamac.statistical.resources.core.publication.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;

public final class PublicationsUtils {

    private PublicationsUtils() {
    }

    public static List<RelatedResource> computeHasPart(PublicationVersion resource) {
        Map<String, Dataset> datasetsByUrn = new HashMap<String, Dataset>();
        Map<String, Query> queriesByUrn = new HashMap<String, Query>();

        for (ElementLevel elementLevel : resource.getChildrenAllLevels()) {
            if (elementLevel.isCube()) {
                Cube cube = elementLevel.getCube();
                if (cube.getDataset() != null) {
                    datasetsByUrn.put(cube.getDatasetUrn(), cube.getDataset());
                } else if (cube.getQuery() != null) {
                    queriesByUrn.put(cube.getQueryUrn(), cube.getQuery());
                }
            }
        }

        List<RelatedResource> resources = new ArrayList<RelatedResource>();
        resources.addAll(buildRelatedResourcesForDatasets(datasetsByUrn.values()));
        resources.addAll(buildRelatedResourcesForQueries(queriesByUrn.values()));
        return resources;
    }

    public static Collection<RelatedResource> buildRelatedResourcesForQueries(Collection<Query> queries) {
        Set<RelatedResource> resources = new HashSet<RelatedResource>();
        for (Query query : queries) {
            resources.add(buildRelatedResourceForQuery(query));
        }
        return resources;
    }

    public static RelatedResource buildRelatedResourceForQuery(Query query) {
        RelatedResource resource = new RelatedResource(TypeRelatedResourceEnum.QUERY);
        resource.setQuery(query);
        return resource;
    }

    public static Collection<RelatedResource> buildRelatedResourcesForDatasets(Collection<Dataset> datasets) {
        Set<RelatedResource> resources = new HashSet<RelatedResource>();
        for (Dataset dataset : datasets) {
            resources.add(buildRelatedResourceForDataset(dataset));
        }
        return resources;
    }

    public static RelatedResource buildRelatedResourceForDataset(Dataset dataset) {
        RelatedResource resource = new RelatedResource(TypeRelatedResourceEnum.DATASET);
        resource.setDataset(dataset);
        return resource;
    }

}
