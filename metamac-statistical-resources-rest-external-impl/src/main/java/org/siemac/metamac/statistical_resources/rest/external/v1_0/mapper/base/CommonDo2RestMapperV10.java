package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Data;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DataStructureDefinition;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.SelectedLanguages;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.StatisticalResource;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;

public interface CommonDo2RestMapperV10 {

    public void toMetadataStatisticalResource(SiemacMetadataStatisticalResource source, StatisticalResource target, List<String> selectedLanguages) throws MetamacException;
    public Data toData(DatasetVersion source, List<String> selectedLanguages, Map<String, List<String>> dimensionValuesSelected) throws Exception;
    public DataStructureDefinition toDataStructureDefinition(ExternalItem source, DataStructure dataStructure, List<String> selectedLanguages);
    public Dimensions toDimensions(DataStructure dataStructure, List<DsdDimension> sources, String datasetVersionUrn, Map<String, List<String>> effectiveDimensionValuesToDataByDimension,
            List<String> selectedLanguages) throws MetamacException;
    // public Attributes toAttributes(DataStructure dataStructure, org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attributes sources, String datasetVersionUrn,
    // List<String> selectedLanguages) throws MetamacException;

    public Resource toResource(Resource source, List<String> selectedLanguages);
    public void toResource(Resource source, Resource target, List<String> selectedLanguages);

    public ResourceLink toResourceLink(String kind, String href);
    public String toResourceLink(String resourceSubpath, String agencyID, String resourceID, String version);

    public InternationalString toInternationalString(org.siemac.metamac.core.common.ent.domain.InternationalString sources, List<String> selectedLanguages);
    public InternationalString toInternationalString(InternationalString sources, List<String> selectedLanguages);
    public InternationalString toInternationalString(String source, List<String> selectedLanguages);

    public Date toDate(DateTime source);

    public SelectedLanguages toLanguages(List<String> selectedLanguages);

    public List<String> codeItemToString(List<CodeItem> sources);
    public List<String> temporalCoverageToString(List<TemporalCode> sources);
    public Map<String, List<String>> calculateEffectiveDimensionValuesToQuery(QueryVersion source);

    public Resources toResourcesExternalItemsSrm(List<ExternalItem> sources, List<String> selectedLanguages);
    public Resource toResourceExternalItemSrm(ExternalItem source, List<String> selectedLanguages);
    public void toResourceExternalItemSrm(ExternalItem source, Resource target, List<String> selectedLanguages);
    public Resource toResourceExternalItemStatisticalOperations(ExternalItem source, List<String> selectedLanguages);
    public Resources toResourcesExternalItemsStatisticalOperations(List<ExternalItem> sources, List<String> selectedLanguages);

}
