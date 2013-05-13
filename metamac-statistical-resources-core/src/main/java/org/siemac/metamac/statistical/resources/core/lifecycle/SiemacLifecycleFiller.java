package org.siemac.metamac.statistical.resources.core.lifecycle;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiemacLifecycleFiller {

    @Autowired
    private LifecycleFiller               lifecycleFiller;
    
    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------
    
    public void applySendToProductionValidationActions(ServiceContext ctx, HasSiemacMetadata resource) {
        lifecycleFiller.applySendToProductionValidationActions(ctx, resource);
        //FIXME: Computed fields based on data
        resource.getSiemacMetadataStatisticalResource().setKeywords(buildKeywords(resource));
    }
    
    private InternationalString buildKeywords(HasSiemacMetadata resource) {
        Set<String> locales = new HashSet<String>();
        if (resource.getSiemacMetadataStatisticalResource().getTitle() != null) {
            locales.addAll(resource.getSiemacMetadataStatisticalResource().getTitle().getLocales());
        }
        if (resource.getSiemacMetadataStatisticalResource().getDescription() != null) {
            locales.addAll(resource.getSiemacMetadataStatisticalResource().getDescription().getLocales());
        }
        InternationalString keywords = new InternationalString();
        for (String locale : locales) {
            Set<String> words = new HashSet<String>();
            words.addAll(splitLocalisedText(resource.getSiemacMetadataStatisticalResource().getTitle(), locale));
            words.addAll(splitLocalisedText(resource.getSiemacMetadataStatisticalResource().getDescription(), locale));
            words = filterKeywords(words);
            if (words.size() > 0) {
                LocalisedString localisedText = new LocalisedString(locale,StringUtils.join(words, " "));
                keywords.addText(localisedText);
            }
        }
        if (keywords.getTexts().size() > 0) {
            return keywords;
        }
        return null;
    }
    
    private Collection<String> splitLocalisedText(InternationalString intString, String locale) {
        String text = intString.getLocalisedLabel(locale);
        Set<String> words = new HashSet<String>();
        if (text != null) {
            words.addAll(Arrays.asList(text.trim().split("\\s+")));
        }
        return words;
    }
    
    private Set<String> filterKeywords(Set<String> words) {
        Set<String> filteredWords = new HashSet<String>();
        for (String word: words) {
            if (word.length() > 3) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    public void applySendToDiffusionValidationActions(ServiceContext ctx, HasSiemacMetadata resource) {
        lifecycleFiller.applySendToDiffusionValidationActions(ctx, resource);
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------
    
    public void applySendToValidationRejectedActions(ServiceContext ctx, HasSiemacMetadata resource) {
        lifecycleFiller.applySendToValidationRejectedActions(ctx, resource);
        //FIXME: clear metadata computed in production validation and diffusion validation
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------
    
    public void applySendToPublished(ServiceContext ctx, HasSiemacMetadata resource, HasSiemacMetadata previousResource) throws MetamacException {
        lifecycleFiller.applySendToPublishedActions(ctx, resource, previousResource);
        resource.getSiemacMetadataStatisticalResource().setCopyrightedDate(resource.getLifeCycleStatisticalResource().getValidFrom());
    }
}