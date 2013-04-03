package org.siemac.metamac.statistical.resources.core.base.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;

public class BaseVersioningCopyUtils {

    // --------------------------------------------------------------------------
    // BASE HIERARCHY
    // --------------------------------------------------------------------------

    public static void copySiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource source, SiemacMetadataStatisticalResource target) {
        copyLifeCycleStatisticalResource(source, target);

        // Languages
        target.setLanguage(copyExternalItem(source.getLanguage()));
        target.getLanguages().clear();
        target.getLanguages().addAll(copyListExternalItem(source.getLanguages()));

        // Theme content classifiers
        target.setStatisticalOperation(copyExternalItem(source.getStatisticalOperation()));
        target.setStatisticalOperationInstance(copyExternalItem(source.getStatisticalOperationInstance()));

        // Content descriptors
        target.setSubtitle(copyInternationalString(source.getSubtitle()));
        target.setTitleAlternative(copyInternationalString(source.getTitleAlternative()));
        target.setAbstractLogic(copyInternationalString(source.getAbstractLogic()));
        // TODO: KEYWORDS?

        // Class descriptor
        target.setType(source.getType());

        // Production descriptors
        target.setMaintainer(copyExternalItem(source.getMaintainer()));
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

    private static void copyLifeCycleStatisticalResource(LifeCycleStatisticalResource source, LifeCycleStatisticalResource target) {
        copyVersionableStatisticalResource(source, target);
        // ALL lifecycle metadata are filled automatically
    }

    private static void copyVersionableStatisticalResource(VersionableStatisticalResource source, VersionableStatisticalResource target) {
        copyNameableStatisticalResource(source, target);

        target.setNextVersion(source.getNextVersion());
    }

    private static void copyNameableStatisticalResource(NameableStatisticalResource source, NameableStatisticalResource target) {
        // TODO: ¿Se hereda el titulo?
        target.setTitle(copyInternationalString(source.getTitle()));
        target.setDescription(copyInternationalString(source.getDescription()));

        copyIdentifiableStatisticalResource(source, target);
    }

    public static void copyIdentifiableStatisticalResource(IdentifiableStatisticalResource source, IdentifiableStatisticalResource target) {
        // TODO: Is the code always the same?
        target.setCode(source.getCode());

        copyStatisticalResource(source, target);
    }

    private static void copyStatisticalResource(StatisticalResource source, StatisticalResource target) {
    }

    // --------------------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // --------------------------------------------------------------------------

    public static InternationalString copyInternationalString(InternationalString source) {
        if (source == null) {
            return null;
        }
        InternationalString target = new InternationalString();
        for (LocalisedString sourceLocalisedString : source.getTexts()) {
            LocalisedString targetLocalisedString = new LocalisedString();
            targetLocalisedString.setLabel(sourceLocalisedString.getLabel());
            targetLocalisedString.setLocale(sourceLocalisedString.getLocale());
            target.addText(targetLocalisedString);
        }
        return target;
    }

    public static List<InternationalString> copyListInternationalString(List<InternationalString> source) {
        if (source.isEmpty()) {
            return null;
        }

        List<InternationalString> target = new ArrayList<InternationalString>();
        for (InternationalString item : source) {
            target.add(copyInternationalString(item));
        }
        return target;
    }

    // --------------------------------------------------------------------------
    // EXTERNAL ITEMS
    // --------------------------------------------------------------------------

    public static ExternalItem copyExternalItem(ExternalItem source) {
        if (source == null) {
            return null;
        }
        ExternalItem target = new ExternalItem(source.getCode(), source.getUri(), source.getUrn(), source.getType());
        return target;
    }

    public static List<ExternalItem> copyListExternalItem(List<ExternalItem> source) {
        if (source.isEmpty()) {
            return new ArrayList<ExternalItem>();
        }

        List<ExternalItem> target = new ArrayList<ExternalItem>();
        for (ExternalItem item : source) {
            target.add(copyExternalItem(item));
        }
        return target;
    }

    // --------------------------------------------------------------------------
    // RELATED RESOURCES
    // --------------------------------------------------------------------------

    public static RelatedResource copyRelatedResource(RelatedResource source) {
        if (source == null) {
            return null;
        }
        RelatedResource target = new RelatedResource(source.getCode(), source.getUrn(), source.getType());
        target.setTitle(copyInternationalString(source.getTitle()));
        return target;
    }

    public static List<RelatedResource> copyListRelatedResource(List<RelatedResource> source) {
        if (source.isEmpty()) {
            return new ArrayList<RelatedResource>();
        }

        List<RelatedResource> target = new ArrayList<RelatedResource>();
        for (RelatedResource item : source) {
            target.add(copyRelatedResource(item));
        }
        return target;
    }
}
