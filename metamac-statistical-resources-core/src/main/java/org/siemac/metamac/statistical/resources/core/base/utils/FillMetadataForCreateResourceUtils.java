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
        fillMetadataForCreateLifeCycleResource(resource, ctx);

        resource.setStatisticalOperation(statisticalOperation);
        resource.setType(type);
    }

    public static void fillMetadataForCreateLifeCycleResource(LifeCycleStatisticalResource resource, ServiceContext ctx) {
        fillMetadataForCreateVersionableResource(resource);

        resource.setProcStatus(StatisticalResourceProcStatusEnum.DRAFT);
        resource.setCreatedDate(new DateTime());
        resource.setCreatedBy(ctx.getUserId());
    }

    private static void fillMetadataForCreateVersionableResource(VersionableStatisticalResource resource) {
        fillMetadataForCreateIdentifiableResource(resource);

        resource.setVersionLogic("01.000");
        resource.setIsLastVersion(true);
        resource.getVersionRationaleTypes().clear();
        resource.addVersionRationaleType(new VersionRationaleType(StatisticalResourceVersionRationaleTypeEnum.MAJOR_NEW_RESOURCE));
    }

    private static void fillMetadataForCreateIdentifiableResource(IdentifiableStatisticalResource resource) {
        resource.setUri(null);
        // QUERY VERSIONS: URN is set in fillMetadataForCreateQuery
        // DATASETS AND PUBLICATIONS: CODE and URN are set just before saving, because the computation for code must be synchronized and this way, we minimize the synchronized block
    }
}
