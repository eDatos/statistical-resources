package org.siemac.metamac.statistical.resources.core.multidataset.utils;

import static org.siemac.metamac.statistical.resources.core.base.utils.BaseVersioningCopyUtils.copyNameableStatisticalResource;
import static org.siemac.metamac.statistical.resources.core.base.utils.BaseVersioningCopyUtils.copySiemacMetadataStatisticalResource;
import static org.siemac.metamac.statistical.resources.core.common.utils.CommonVersioningCopyUtils.copyInternationalString;

import java.util.List;

import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;

public class MultidatasetVersioningCopyUtils {

    /**
     * Create a new {@link MultidatasetVersion} copying values from a source.
     */
    public static MultidatasetVersion copyMultidatasetVersion(MultidatasetVersion source) {
        MultidatasetVersion target = new MultidatasetVersion();
        target.setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
        copyMultidatasetVersion(source, target);
        return target;
    }

    /**
     * Copy values from a {@link MultidatasetVersion}
     */
    public static void copyMultidatasetVersion(MultidatasetVersion source, MultidatasetVersion target) {
        // Metadata
        target.setSiemacMetadataStatisticalResource(copySiemacMetadataStatisticalResource(source.getSiemacMetadataStatisticalResource(), target.getSiemacMetadataStatisticalResource()));
        target.setFormatExtentResources(null);
        target.setMultidataset(source.getMultidataset());

        target.setFilteringDimension(copyInternationalString(source.getFilteringDimension()));

        // Structure
        copyMultidatasetCubes(source, target);
    }

    private static void copyMultidatasetCubes(MultidatasetVersion multidatasetVersionSource, MultidatasetVersion multidatasetVersionTarget) {
        List<MultidatasetCube> sources = multidatasetVersionSource.getCubes();
        for (MultidatasetCube source : sources) {
            MultidatasetCube target = copyMultidatasetCube(source);
            target.setMultidatasetVersion(multidatasetVersionTarget);
            multidatasetVersionTarget.getCubes().add(target);
        }
    }

    private static MultidatasetCube copyMultidatasetCube(MultidatasetCube source) {
        if (source == null) {
            return null;
        }
        MultidatasetCube target = new MultidatasetCube();
        target.setIdentifier(source.getIdentifier());

        target.setNameableStatisticalResource(copyNameableStatisticalResource(source.getNameableStatisticalResource(), target.getNameableStatisticalResource()));
        target.fillCodeAndUrn();

        target.setOrderInMultidataset(source.getOrderInMultidataset());

        target.setQuery(source.getQuery());
        target.setDataset(source.getDataset());

        return target;
    }
}