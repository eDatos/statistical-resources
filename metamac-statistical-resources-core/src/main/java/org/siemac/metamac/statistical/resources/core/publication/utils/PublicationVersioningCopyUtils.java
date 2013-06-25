package org.siemac.metamac.statistical.resources.core.publication.utils;

import static org.siemac.metamac.statistical.resources.core.base.utils.BaseVersioningCopyUtils.copySiemacMetadataStatisticalResource;

import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

public class PublicationVersioningCopyUtils {

    /**
     * Create a new {@link PublicationVersion} copying values from a source.
     */
    public static PublicationVersion copyPublicationVersion(PublicationVersion source) {
        PublicationVersion target = new PublicationVersion();
        target.setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
        copyPublicationVersion(source, target);
        return target;
    }

    /**
     * Copy values from a {@link PublicationVersion}
     */
    public static void copyPublicationVersion(PublicationVersion source, PublicationVersion target) {
        // Metadata
        copySiemacMetadataStatisticalResource(source.getSiemacMetadataStatisticalResource(), target.getSiemacMetadataStatisticalResource());
        target.setFormatExtentResources(source.getFormatExtentResources());
        target.setPublication(source.getPublication());

        // Structure
        //TODO: añadir jerarquia
    }
}