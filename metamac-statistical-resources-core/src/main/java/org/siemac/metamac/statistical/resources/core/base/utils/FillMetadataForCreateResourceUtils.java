package org.siemac.metamac.statistical.resources.core.base.utils;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceVersionRationaleTypeEnum;

public class FillMetadataForCreateResourceUtils {
    
    public static void fillMetadataForCretateSiemacResource(SiemacMetadataStatisticalResource resource, ExternalItem statisticalOperation, StatisticalResourceTypeEnum type, ServiceContext ctx) {
        fillMetadataForCreateLifeCycleResource(resource, type, ctx);

        resource.setStatisticalOperation(statisticalOperation);
        resource.setType(type);
    }

    public static void fillMetadataForCreateLifeCycleResource(LifeCycleStatisticalResource resource, StatisticalResourceTypeEnum type, ServiceContext ctx) {
        fillMetadataForCreateVersionableResource(resource, type);

        resource.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        resource.setCreatedDate(new DateTime());
        resource.setCreatedBy(ctx.getUserId());
    }

    private static void fillMetadataForCreateVersionableResource(VersionableStatisticalResource resource, StatisticalResourceTypeEnum type) {
        fillMetadataForCreateIdentifiableResource(resource, type);

        resource.setVersionLogic("01.000");
        resource.getVersionRationaleTypes().clear();
        resource.addVersionRationaleType(new VersionRationaleType(StatisticalResourceVersionRationaleTypeEnum.MAJOR_NEW_RESOURCE));
    }

    private static void fillMetadataForCreateIdentifiableResource(IdentifiableStatisticalResource resource, StatisticalResourceTypeEnum type) {
        resource.setUri(null);

        // URN
        if (StatisticalResourceTypeEnum.QUERY.equals(type)) {
            resource.setUrn(GeneratorUrnUtils.generateSiemacStatisticalResourceQueryUrn(resource.getCode()));

        }
        // DATASETS AND PUBLICATIONS: CODE and URN are set just before saving, because the computation for code must be synchronized and this way, we minimize the synchronized block
    }
}
