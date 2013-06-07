package org.siemac.metamac.statistical.resources.core.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;

public class CommonVersioningCopyUtils {

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
        ExternalItem target = new ExternalItem(source.getCode(), source.getUri(), source.getUrn(), source.getUrnInternal(), source.getType(), copyInternationalString(source.getTitle()), source.getManagementAppUrl());
                
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
        RelatedResource target = new RelatedResource(source.getType());
        target.setDatasetVersion(source.getDatasetVersion());
        target.setPublicationVersion(source.getPublicationVersion());
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
