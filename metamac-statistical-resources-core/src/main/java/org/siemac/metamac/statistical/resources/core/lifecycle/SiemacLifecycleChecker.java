package org.siemac.metamac.statistical.resources.core.lifecycle;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SiemacLifecycleChecker {

    @Autowired
    private LifecycleChecker lifecycleService;
    
    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;
    
    
    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------
    
    public void checkSendToProductionValidation(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleService.checkSendToProductionValidation(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }
    
    public void applySendToProductionValidationActions(ServiceContext ctx, SiemacMetadataStatisticalResource resource) {
        lifecycleService.applySendToProductionValidationActions(ctx, resource);
        //FIXME: Computed fields based on data
        resource.setKeywords(buildKeywords(resource));
    }
    
    private InternationalString buildKeywords(SiemacMetadataStatisticalResource resource) {
        Set<String> locales = new HashSet<String>();
        if (resource.getTitle() != null) {
            locales.addAll(resource.getTitle().getLocales());
        }
        if (resource.getDescription() != null) {
            locales.addAll(resource.getDescription().getLocales());
        }
        InternationalString keywords = new InternationalString();
        for (String locale : locales) {
            Set<String> words = new HashSet<String>();
            words.addAll(splitLocalisedText(resource.getTitle(), locale));
            words.addAll(splitLocalisedText(resource.getDescription(), locale));
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
    
    public void checkSendToDiffusionValidation(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleService.checkSendToDiffusionValidation(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }
    
    public void applySendToDiffusionValidationActions(ServiceContext ctx, SiemacMetadataStatisticalResource resource) {
        lifecycleService.applySendToDiffusionValidationActions(ctx, resource);
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------
    
    public void checkSendToValidationRejected(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        lifecycleService.checkSendToValidationRejected(resource, metadataName, exceptionItems);
        checkSiemacMetadataAllActions(resource, metadataName, exceptionItems);
    }

    public void applySendToValidationRejectedActions(ServiceContext ctx, SiemacMetadataStatisticalResource resource) {
        lifecycleService.applySendToValidationRejectedActions(ctx, resource);
        //FIXME: clear metadata computed in production validation and diffusion validation
    }
    
    // ------------------------------------------------------------------------------------------------------
    // >> PROTECTED COMMON UTILS
    // ------------------------------------------------------------------------------------------------------
    
    private void checkSiemacMetadataAllActions(SiemacMetadataStatisticalResource resource, String metadataName, List<MetamacExceptionItem> exceptionItems) {
        lifecycleCommonMetadataChecker.checkSiemacCommonMetadata(resource, metadataName, exceptionItems);
    }
}