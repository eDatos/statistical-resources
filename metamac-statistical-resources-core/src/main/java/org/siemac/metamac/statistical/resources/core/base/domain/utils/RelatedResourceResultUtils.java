package org.siemac.metamac.statistical.resources.core.base.domain.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
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

    public static List<String> getUrnsFromRelatedResourceResults(List<RelatedResourceResult> resources) {
        List<String> urns = new ArrayList<String>();
        for (RelatedResourceResult relatedResourceResult : resources) {
            urns.add(relatedResourceResult.getUrn());
        }
        return urns;
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

    public static RelatedResourceResult from(HasSiemacMetadata siemacMetadata) {
        if (siemacMetadata == null) {
            return null;
        }

        RelatedResourceResult resource = new RelatedResourceResult();
        resource.setCode(siemacMetadata.getSiemacMetadataStatisticalResource().getCode());
        resource.setUrn(siemacMetadata.getSiemacMetadataStatisticalResource().getUrn());
        resource.setStatisticalOperationCode(siemacMetadata.getLifeCycleStatisticalResource().getStatisticalOperation().getCode());
        resource.setStatisticalOperationUrn(siemacMetadata.getSiemacMetadataStatisticalResource().getStatisticalOperation().getUrn());
        resource.setMaintainerNestedCode(siemacMetadata.getSiemacMetadataStatisticalResource().getMaintainer().getCode());
        resource.setVersion(siemacMetadata.getSiemacMetadataStatisticalResource().getVersionLogic());
        if (resource.getTitle() == null) {
            resource.setTitle(new HashMap<String, String>());
        }
        for (String locale : siemacMetadata.getSiemacMetadataStatisticalResource().getTitle().getLocales()) {
            resource.getTitle().put(locale, siemacMetadata.getSiemacMetadataStatisticalResource().getTitle().getLocalisedLabel(locale));
        }
        return resource;
    }

}
