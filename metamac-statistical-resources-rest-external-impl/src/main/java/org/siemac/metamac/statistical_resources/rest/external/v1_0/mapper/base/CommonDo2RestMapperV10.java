package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.SelectedLanguages;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.StatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;

public interface CommonDo2RestMapperV10 {

    public void toMetadataStatisticalResource(SiemacMetadataStatisticalResource source, StatisticalResource target, List<String> selectedLanguages) throws MetamacException;

    public ResourceLink toResourceLink(String kind, String href);
    public String toResourceLink(String resourceSubpath, String agencyID, String resourceID, String version);

    public InternationalString toInternationalString(org.siemac.metamac.core.common.ent.domain.InternationalString sources, List<String> selectedLanguages);
    public InternationalString toInternationalString(List<TextType> sources, List<String> selectedLanguages);
    public InternationalString toInternationalString(InternationalString sources, List<String> selectedLanguages);
    public InternationalString toInternationalString(String source, List<String> selectedLanguages);

    public Date toDate(DateTime source);

    public SelectedLanguages toLanguages(List<String> selectedLanguages);

    public Resources toResourcesExternalItemsSrm(List<ExternalItem> sources, List<String> selectedLanguages);
    public Resource toResourceExternalItemSrm(ExternalItem source, List<String> selectedLanguages);
    public void toResourceExternalItemSrm(ExternalItem source, Resource target, List<String> selectedLanguages);
    public Resource toResourceExternalItemStatisticalOperations(ExternalItem source, List<String> selectedLanguages);
    public Resources toResourcesExternalItemsStatisticalOperations(List<ExternalItem> sources, List<String> selectedLanguages);

}
