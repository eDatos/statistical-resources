package org.siemac.metamac.statistical.resources.core.base.utils;

import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;

public class FillMetadataForUpdateResourceUtils {
    
    public static void fillMetadataForUpdateSiemacResource(SiemacMetadataStatisticalResource resource, StatisticalResourceTypeEnum type) {
        fillMetadataForUpdateLifeCycleResource(resource, type);
        resource.setType(type);
    }

    public static void fillMetadataForUpdateLifeCycleResource(LifeCycleStatisticalResource resource, StatisticalResourceTypeEnum type) {
        String[] maintainerCodes = new String[]{resource.getMaintainer().getCode()};
        fillMetadataForUpdateVersionableResource(maintainerCodes, resource, type);
    }

    private static void fillMetadataForUpdateVersionableResource(String[] maintainerCodes, VersionableStatisticalResource resource, StatisticalResourceTypeEnum type) {
        fillMetadataForUpdateIdentifiableResource(maintainerCodes, resource, type);
    }

    private static void fillMetadataForUpdateIdentifiableResource(String[] maintainerCodes, IdentifiableStatisticalResource resource, StatisticalResourceTypeEnum type) {
        resource.setUri(null);

        // URN
        if (StatisticalResourceTypeEnum.QUERY.equals(type)) {
            resource.setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn(maintainerCodes, resource.getCode()));

        }
        // DATASETS AND PUBLICATIONS: CODE and URN are set just before saving, because the computation for code must be synchronized and this way, we minimize the synchronized block
    }
}
