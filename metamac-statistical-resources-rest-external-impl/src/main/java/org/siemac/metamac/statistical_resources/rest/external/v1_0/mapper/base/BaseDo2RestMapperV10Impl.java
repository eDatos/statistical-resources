package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.utils.RestCommonUtil;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseDo2RestMapperV10Impl {

    @Autowired
    private ConfigurationService configurationService;

    private String               statisticalResourcesApiExternalEndpointV10;
    private String               srmApiExternalEndpoint;

    @PostConstruct
    public void init() throws Exception {
        initEndpoints();
    }

    public String getStatisticalResourcesApiExternalEndpointV10() {
        return statisticalResourcesApiExternalEndpointV10;
    }

    public String getSrmApiExternalEndpoint() {
        return srmApiExternalEndpoint;
    }

    protected InternationalString toInternationalString(org.siemac.metamac.core.common.ent.domain.InternationalString sources) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.core.common.ent.domain.LocalisedString source : sources.getTexts()) {
            LocalisedString target = new LocalisedString();
            target.setValue(source.getLabel());
            target.setLang(source.getLocale());
            targets.getTexts().add(target);
        }
        return targets;
    }

    protected Date toDate(DateTime source) {
        return RestCommonUtil.transformDateTimeToDate(source);
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
    protected String toStatisticalResourceLink(String resourceSubpath, SiemacMetadataStatisticalResource source) {
        String agencyID = getAgencyIdAsMaintainer(source.getMaintainer());
        String resourceID = source.getCode();
        String version = source.getVersionLogic();
        return toStatisticalResourceLink(resourceSubpath, agencyID, resourceID, version);
    }
    protected String toStatisticalResourceLink(String resourceSubpath, String agencyID, String resourceID, String version) {
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

    /**
     * Return id as maintainer of one agency
     * TODO en bbdd estará nestedCode
     */
    private String getAgencyIdAsMaintainer(ExternalItem maintainer) {
        return UrnUtils.splitUrnItemScheme(maintainer.getUrn())[0];
    }

    private void initEndpoints() throws MetamacException {

        // Statistical resources external Api V1.0
        String statisticalResourcesApiExternalEndpoint = configurationService.retrieveStatisticalResourcesExternalApiUrlBase();
        statisticalResourcesApiExternalEndpointV10 = RestUtils.createLink(statisticalResourcesApiExternalEndpoint, RestExternalConstants.API_VERSION_1_0);

        // SRM external Api (do not add api version! it is already stored in database (~latest))
        srmApiExternalEndpoint = configurationService.retrieveSrmExternalApiUrlBase();
        srmApiExternalEndpoint = StringUtils.removeEnd(srmApiExternalEndpoint, "/");
    }
}