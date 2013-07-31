package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;
import org.siemac.metamac.rest.utils.RestCommonUtil;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDo2RestMapperV10Impl {

    protected static final Logger logger = LoggerFactory.getLogger(BaseDo2RestMapperV10Impl.class);

    @Autowired
    private ConfigurationService  configurationService;

    private String                statisticalResourcesApiExternalEndpointV10;
    private String                srmApiExternalEndpoint;
    private String                statisticalOperationsApiExternalEndpoint;
    private String                defaultLanguage;

    @PostConstruct
    public void init() throws Exception {

        // ENDPOINTS
        // Statistical resources external Api V1.0
        String statisticalResourcesApiExternalEndpoint = configurationService.retrieveStatisticalResourcesExternalApiUrlBase();
        statisticalResourcesApiExternalEndpointV10 = RestUtils.createLink(statisticalResourcesApiExternalEndpoint, RestExternalConstants.API_VERSION_1_0);

        // SRM external Api (do not add api version! it is already stored in database (~latest))
        srmApiExternalEndpoint = configurationService.retrieveSrmExternalApiUrlBase();
        srmApiExternalEndpoint = StringUtils.removeEnd(srmApiExternalEndpoint, "/");

        // Statistical operations external Api (do not add api version! it is already stored in database (~latest))
        statisticalOperationsApiExternalEndpoint = configurationService.retrieveStatisticalOperationsExternalApiUrlBase();
        statisticalOperationsApiExternalEndpoint = StringUtils.removeEnd(srmApiExternalEndpoint, "/");

        // MISC
        defaultLanguage = configurationService.retrieveLanguageDefault();
    }

    public String getStatisticalResourcesApiExternalEndpointV10() {
        return statisticalResourcesApiExternalEndpointV10;
    }

    public String getSrmApiExternalEndpoint() {
        return srmApiExternalEndpoint;
    }

    public String getStatisticalOperationsApiExternalEndpoint() {
        return statisticalOperationsApiExternalEndpoint;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    protected Resource toResourceExternalItemStatisticalOperations(ExternalItem source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        toResourceExternalItem(source, getStatisticalOperationsApiExternalEndpoint(), target, selectedLanguages);
        return target;
    }

    protected Resources toResourcesExternalItemsStatisticalOperations(List<ExternalItem> sources, List<String> selectedLanguages) throws MetamacException {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        Resources targets = new Resources();
        for (ExternalItem source : sources) {
            Resource target = toResourceExternalItemStatisticalOperations(source, selectedLanguages);
            targets.getResources().add(target);
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    protected Resource toResourceExternalItemSrm(ExternalItem source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        toResourceExternalItem(source, getSrmApiExternalEndpoint(), target, selectedLanguages);
        return target;
    }

    protected void toResourceExternalItemSrm(ExternalItem source, Resource target, List<String> selectedLanguages) {
        if (source == null) {
            return;
        }
        toResourceExternalItem(source, getSrmApiExternalEndpoint(), target, selectedLanguages);
    }

    protected Resources toResourcesExternalItemsSrm(List<ExternalItem> sources, List<String> selectedLanguages) throws MetamacException {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        Resources targets = new Resources();
        for (ExternalItem source : sources) {
            Resource target = toResourceExternalItemSrm(source, selectedLanguages);
            targets.getResources().add(target);
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    protected InternationalString toInternationalString(org.siemac.metamac.core.common.ent.domain.InternationalString sources, List<String> selectedLanguages) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.core.common.ent.domain.LocalisedString source : sources.getTexts()) {
            if (selectedLanguages.contains(source.getLocale())) {
                LocalisedString target = new LocalisedString();
                target.setLang(source.getLocale());
                target.setValue(source.getLabel());
                targets.getTexts().add(target);
            }
        }
        return targets;
    }

    protected InternationalString toInternationalString(List<TextType> sources, List<String> selectedLanguages) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (TextType source : sources) {
            if (selectedLanguages.contains(source.getLang())) {
                LocalisedString target = new LocalisedString();
                target.setLang(source.getLang());
                target.setValue(source.getValue());
                targets.getTexts().add(target);
            }
        }
        return targets;
    }

    protected InternationalString toInternationalString(InternationalString sources, List<String> selectedLanguages) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (LocalisedString source : sources.getTexts()) {
            if (selectedLanguages.contains(source.getLang())) {
                targets.getTexts().add(source);
            }
        }
        return targets;
    }

    protected InternationalString toInternationalString(String source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        LocalisedString target = new LocalisedString();
        target.setLang(getDefaultLanguage());
        target.setValue(source);
        targets.getTexts().add(target);
        return targets;
    }

    protected Date toDate(DateTime source) {
        return RestCommonUtil.transformDateTimeToDate(source);
    }

    /**
     * Create list with requested languages and default language in service
     */
    protected List<String> toSelectedLanguages(List<String> sources) throws MetamacException {
        List<String> targets = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(sources)) {
            targets.addAll(sources);
        }
        String languageDefault = getDefaultLanguage();
        if (!targets.contains(languageDefault)) {
            targets.add(languageDefault);
        }
        return targets;
    }

    protected ResourceLink toResourceLink(String kind, String href) {
        ResourceLink target = new ResourceLink();
        target.setKind(kind);
        target.setHref(href);
        return target;
    }

    // API/[ARTEFACT_TYPE]
    // API/[ARTEFACT_TYPE]/{agencyID}
    // API/[ARTEFACT_TYPE]/{agencyID}/{resourceID}
    // API/[ARTEFACT_TYPE]/{agencyID}/{resourceID}/{version}
    protected String toResourceLink(String resourceSubpath, String agencyID, String resourceID, String version) {
        String link = RestUtils.createLink(getStatisticalResourcesApiExternalEndpointV10(), resourceSubpath);
        if (agencyID != null) {
            link = RestUtils.createLink(link, agencyID);
            if (resourceID != null) {
                link = RestUtils.createLink(link, resourceID);
                if (version != null) {
                    link = RestUtils.createLink(link, version);
                }
            }
        }
        return link;
    }

    private void toResourceExternalItem(ExternalItem source, String apiExternalItemBase, Resource target, List<String> selectedLanguages) {
        if (source == null) {
            return;
        }
        target.setId(source.getCode());
        target.setNestedId(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setKind(source.getType().getValue());
        target.setSelfLink(toResourceLink(target.getKind(), RestUtils.createLink(apiExternalItemBase, source.getUri())));
        target.setName(toInternationalString(source.getTitle(), selectedLanguages));
    }

}