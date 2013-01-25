package org.siemac.metamac.statistical.resources.core.base.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionableStatisticalResource;

public class BaseVersioningCopyUtils {

    // --------------------------------------------------------------------------
    // BASE HIERARCHY
    // --------------------------------------------------------------------------

    public static void copySiemacMetadataStatisticalResource(SiemacMetadataStatisticalResource source, SiemacMetadataStatisticalResource target) {
        // target.setType(source.getType());
        // target.setFormat(source.getFormat());
        //
        // copyLifeCycleStatisticalResource(source, target);
    }

    private static void copyLifeCycleStatisticalResource(LifeCycleStatisticalResource source, LifeCycleStatisticalResource target) {

        // // PROC_STATUS is automatically filled
        // // VERSION_RESPONSIBILITY_CREATOR is automatically filled
        // target.setVersionResponsibilityContributor(source.getVersionResponsibilityContributor());
        // // VERSION_RESPONSIBILITY_SUBMITTED is automatically filled
        // // VERSION_RESPONSIBILITY_ACCEPTED is automatically filled
        // // VERSION_RESPONSIBILITY_ISSUED is automatically filled
        // // VERSION_RESPONSIBILITY_OUT_OF_PRINT is automatically filled
        //
        // target.setCreator(copyExternalItem(source.getCreator()));
        // target.getContributor().addAll(copyListExternalItem(source.getContributor()));
        // target.getPublisher().addAll(copyListExternalItem(source.getPublisher()));
        // target.getMediator().addAll(copyListExternalItem(source.getMediator()));
        //
        // copyVersionableStatisticalResource(source, target);
    }

    private static void copyVersionableStatisticalResource(VersionableStatisticalResource source, VersionableStatisticalResource target) {

        // VERSION_LOGIC is automatically filled
        // VERSION_DATE is automatically filled

        // NEXT_VERSION_DATE isn't inheritable

        // VERSION_RATIONALE_TYPE isn't inheritable
        // VERSION_RATIONALE isn't inheritable

        // IS_LAST_VERSION is automatically filled
        // REPLACED_BY is automatically filled
        // REPLACE_TO is automatically filled

        copyNameableStatisticalResource(source, target);
    }

    private static void copyNameableStatisticalResource(NameableStatisticalResource source, NameableStatisticalResource target) {
        // TODO: ¿Se hereda el titulo?
        target.setTitle(copyInternationalString(source.getTitle()));
        target.setDescription(copyInternationalString(source.getDescription()));

        copyIdentifiableStatisticalResource(source, target);
    }

    private static void copyIdentifiableStatisticalResource(IdentifiableStatisticalResource source, IdentifiableStatisticalResource target) {
        // TODO: ¿El code es el mismo?
        target.setCode(source.getCode());

        // URI is automatically filled
        // URN is automatically filled

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
            return null;
        }

        List<ExternalItem> target = new ArrayList<ExternalItem>();
        for (ExternalItem item : source) {
            target.add(copyExternalItem(item));
        }
        return target;
    }
}
