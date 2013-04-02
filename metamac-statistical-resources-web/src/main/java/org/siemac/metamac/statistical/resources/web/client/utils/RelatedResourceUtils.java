package org.siemac.metamac.statistical.resources.web.client.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.record.RelatedResourceRecord;
import org.siemac.metamac.web.common.client.utils.CommonWebUtils;

public class RelatedResourceUtils {

    public static String getRelatedResourceName(RelatedResourceDto relatedResourceDto) {
        if (relatedResourceDto != null) {
            return CommonWebUtils.getElementName(relatedResourceDto.getCode(), relatedResourceDto.getTitle());
        } else {
            return StringUtils.EMPTY;
        }
    }

    public static String getRelatedResourcesNames(List<RelatedResourceDto> relatedResourceDtos) {
        List<String> names = new ArrayList<String>(relatedResourceDtos.size());
        for (RelatedResourceDto relatedResourceDto : relatedResourceDtos) {
            names.add(getRelatedResourceName(relatedResourceDto));
        }
        return CommonWebUtils.getStringListToString(names);
    }

    public static RelatedResourceRecord getRelatedResourceRecord(RelatedResourceDto relatedResourceDto) {
        RelatedResourceRecord record = new RelatedResourceRecord(relatedResourceDto.getCode(), relatedResourceDto.getUrn(), relatedResourceDto.getTitle(), relatedResourceDto);
        return record;
    }
}
