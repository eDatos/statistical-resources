package org.siemac.metamac.statistical.resources.core.base.utils;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.enume.domain.VersionPatternEnum;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.VersionUtil;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;


public class FillMetadataForVersioningResourceUtils {

    public static void fillMetadataForVersioningSiemacResource(ServiceContext ctx, SiemacMetadataStatisticalResource previous, SiemacMetadataStatisticalResource next, VersionTypeEnum versionType) {
        fillMetadataForVersioningLifecycleResource(ctx, previous, next, versionType);
        
    }
    
    private static void fillMetadataForVersioningLifecycleResource(ServiceContext ctx, LifeCycleStatisticalResource previous, LifeCycleStatisticalResource next, VersionTypeEnum versionType) {
        fillMetadataForVersioningVersionableResource(ctx, previous, next, versionType);
        
        next.setProcStatus(ProcStatusEnum.DRAFT);
        next.setCreationDate(new DateTime());
        next.setCreationUser(ctx.getUserId());
    }
    

    private static void fillMetadataForVersioningVersionableResource(ServiceContext ctx,VersionableStatisticalResource previous, VersionableStatisticalResource next, VersionTypeEnum versionType) {
        String newVersion = VersionUtil.createNextVersion(previous.getVersionLogic(), VersionPatternEnum.XX_YYY, versionType);
        next.setVersionLogic(newVersion);
        
    }
    
}
