package org.siemac.metamac.statistical.resources.core.base.domain.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;


public class RelatedResourceResultUtils {

    public static List<RelatedResourceResult> getRelatedResourceResultsFromRows(List<Object> rows, TypeRelatedResourceEnum type) {
        Map<String, RelatedResourceResult> resourcesByUrn = new HashMap<String, RelatedResourceResult>();
        for (Object row : rows) {
            Object[] cols = (Object[]) row;
            String queryUrn = (String) cols[1];
            RelatedResourceResult resource = resourcesByUrn.get(queryUrn);
            if (resource == null) {
                resource = new RelatedResourceResult();
                resourcesByUrn.put(queryUrn, resource);
            }
            populateResultWithRow(resource, cols, type);
        }
        List<RelatedResourceResult> resources = new ArrayList<RelatedResourceResult>(resourcesByUrn.values());
        return resources;
    }

    private static void populateResultWithRow(RelatedResourceResult resource, Object[] cols, TypeRelatedResourceEnum type) {
        resource.setCode((String) cols[0]);
        resource.setUrn((String) cols[1]);
        resource.setStatisticalOperationCode((String) cols[2]);
        resource.setStatisticalOperationUrn((String) cols[3]);
        resource.setMaintainerNestedCode((String) cols[4]);
        resource.setVersion((String) cols[5]);
        if (resource.getTitle() == null) {
            resource.setTitle(new HashMap<String, String>());
        }
        resource.getTitle().put((String) cols[6], (String) cols[7]);
        resource.setType(type);
    }

}
