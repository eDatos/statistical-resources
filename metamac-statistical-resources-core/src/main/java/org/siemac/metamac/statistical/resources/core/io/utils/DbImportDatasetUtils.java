package org.siemac.metamac.statistical.resources.core.io.utils;

import org.apache.commons.collections.CollectionUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.statistical.resources.core.dto.VersionRationaleTypeDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.io.serviceimpl.CustomImportDatasetJobForDbImport;

public class DbImportDatasetUtils {

    private DbImportDatasetUtils() {
        // NOTHING TO DO HERE
    }

    public static void setDatasetVersionNextVersion(DatasetVersionDto datasetVersionDto) {
        if (datasetVersionDto.getNextVersion() == null) {
            // TODO METAMAC-2866 The value of this metadata should be "SCHEDULED_UPDATE" but it is necessary to establish a translation between the value of the update_frecuency metadata and
            // next_version_date and date_next_update to calculate their values ​​in the proper way
            datasetVersionDto.setNextVersion(NextVersionTypeEnum.NON_SCHEDULED_UPDATE);
        }
    }

    public static void setDatasetVersionNextVersionDate(DatasetVersionDto datasetVersionDto) {
        // TODO METAMAC-2866 it's necessary to resolve TODO in method setDatasetVersionNextVersion to assign proper value in this metadata
    }

    public static void setDatasetVersionDateNextUpdate(DatasetVersionDto datasetVersionDto) {
        // TODO METAMAC-2866 it's necessary to resolve TODO in method setDatasetVersionNextVersion to assign proper value in this metadata
    }

    public static void setDatasetVersionVersionRationaleType(DatasetVersionDto datasetVersionDto) {
        if (CollectionUtils.isEmpty(datasetVersionDto.getVersionRationaleTypes())) {
            // TODO METAMAC-2866 It should be clarified what value to assign to this metadata in case of reaching the maximum number of minor versions
            VersionRationaleTypeDto versionRationaleTypeDto = new VersionRationaleTypeDto(VersionRationaleTypeEnum.MINOR_DATA_UPDATE);
            datasetVersionDto.getVersionRationaleTypes().add(versionRationaleTypeDto);
        }
    }

    public static boolean isDbImportDatasetJob(ServiceContext ctx) {
        return Boolean.TRUE.equals(ctx.getProperty(CustomImportDatasetJobForDbImport.DB_IMPORT_JOB_FLAG));
    }
}
