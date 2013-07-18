package org.siemac.metamac.statistical.resources.core.base.utils;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.utils.CommonVersioningCopyUtils;

public class BaseVersioningCopyUtils extends CommonVersioningCopyUtils {

    // --------------------------------------------------------------------------
    // SIEMAC METADATA STATISTICAL RESOURCE
    // --------------------------------------------------------------------------

    public static void copySiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource source, SiemacMetadataStatisticalResource target) {
        copyLifeCycleStatisticalResource(source, target);

        // Languages
        target.setLanguage(copyExternalItem(source.getLanguage()));
        target.getLanguages().clear();
        target.getLanguages().addAll(copyListExternalItem(source.getLanguages()));

        // Theme content classifiers
        target.setStatisticalOperation(copyExternalItem(source.getStatisticalOperation()));
        target.getStatisticalOperationInstances().clear();
        target.getStatisticalOperationInstances().addAll(copyListExternalItem(source.getStatisticalOperationInstances()));

        // Content descriptors
        target.setSubtitle(copyInternationalString(source.getSubtitle()));
        target.setTitleAlternative(copyInternationalString(source.getTitleAlternative()));
        target.setAbstractLogic(copyInternationalString(source.getAbstractLogic()));
        // TODO: KEYWORDS?

        // Class descriptor
        target.setType(source.getType());

        // Production descriptors
        target.setCreator(copyExternalItem(source.getCreator()));
        target.getContributor().clear();
        target.getContributor().addAll(copyListExternalItem(source.getContributor()));
        target.setCreatedDate(source.getCreatedDate());
        target.setConformsTo(copyInternationalString(source.getConformsTo()));
        target.setConformsToInternal(copyInternationalString(source.getConformsToInternal()));

        // Publishing descriptors
        target.getPublisher().clear();
        target.getPublisher().addAll(copyListExternalItem(source.getPublisher()));
        target.getPublisherContributor().clear();
        target.getPublisherContributor().addAll(copyListExternalItem(source.getPublisherContributor()));
        target.getMediator().clear();
        target.getMediator().addAll(copyListExternalItem(source.getMediator()));

        // Resources relation descriptors
        // TODO: requires and is required are inherited, but must be changed if a query is discontinued
        target.getRequires().clear();
        target.getRequires().addAll(copyListRelatedResource(source.getRequires()));
        target.getIsRequiredBy().clear();
        target.getIsRequiredBy().addAll(copyListRelatedResource(source.getIsRequiredBy()));

        // Intellectual ownership descriptors
        target.setRightsHolder(copyExternalItem(source.getRightsHolder()));
        target.setLicense(copyInternationalString(source.getLicense()));
        target.setAccessRights(copyInternationalString(source.getAccessRights()));
    }

    // --------------------------------------------------------------------------
    // LIFE CYCLE STATISTICAL RESOURCE
    // --------------------------------------------------------------------------

    private static void copyLifeCycleStatisticalResource(LifeCycleStatisticalResource source, LifeCycleStatisticalResource target) {
        copyVersionableStatisticalResource(source, target);
        target.setMaintainer(copyExternalItem(source.getMaintainer()));
        // Other lifecycle metadata are filled automatically
    }

    // --------------------------------------------------------------------------
    // VERSIONABLE STATISTICAL RESOURCE
    // --------------------------------------------------------------------------

    private static void copyVersionableStatisticalResource(VersionableStatisticalResource source, VersionableStatisticalResource target) {
        copyNameableStatisticalResource(source, target);

        target.setNextVersion(source.getNextVersion());
    }

    // --------------------------------------------------------------------------
    // NAMEABLE STATISTICAL RESOURCE
    // --------------------------------------------------------------------------

    private static void copyNameableStatisticalResource(NameableStatisticalResource source, NameableStatisticalResource target) {
        // TODO: ¿Se hereda el titulo?
        target.setTitle(copyInternationalString(source.getTitle()));
        target.setDescription(copyInternationalString(source.getDescription()));

        copyIdentifiableStatisticalResource(source, target);
    }

    // --------------------------------------------------------------------------
    // IDENTIFIABLE METADATA STATISTICAL RESOURCE
    // --------------------------------------------------------------------------

    public static void copyIdentifiableStatisticalResource(IdentifiableStatisticalResource source, IdentifiableStatisticalResource target) {
        // TODO: Is the code always the same?
        target.setCode(source.getCode());

        copyStatisticalResource(source, target);
    }

    // --------------------------------------------------------------------------
    // STATISTICAL RESOURCE
    // --------------------------------------------------------------------------

    private static void copyStatisticalResource(StatisticalResource source, StatisticalResource target) {
    }
}
