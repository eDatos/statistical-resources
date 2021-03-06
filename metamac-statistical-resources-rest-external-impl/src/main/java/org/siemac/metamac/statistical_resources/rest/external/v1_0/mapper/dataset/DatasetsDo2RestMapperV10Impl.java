package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset;

import static org.siemac.metamac.statistical_resources.rest.external.service.utils.StatisticalResourcesRestExternalUtils.containsField;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.Item;
import org.siemac.metamac.rest.common.v1_0.domain.Items;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.search.criteria.mapper.SculptorCriteria2RestCriteria;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DatasetMetadata;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Datasets;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Categorisation;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.StatisticOfficiality;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.domain.DsdProcessorResult;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.CommonDo2RestMapperV10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatasetsDo2RestMapperV10Impl implements DatasetsDo2RestMapperV10 {

    @Autowired
    private CommonDo2RestMapperV10   commonDo2RestMapper;

    @Autowired
    private DatasetVersionRepository datasetVersionRepository;

    private static final Logger      logger = LoggerFactory.getLogger(DatasetsDo2RestMapperV10.class);

    @Override
    public Datasets toDatasets(PagedResult<DatasetVersion> sources, String agencyID, String resourceID, String query, String orderBy, Integer limit, List<String> selectedLanguages) {

        Datasets targets = new Datasets();
        targets.setKind(StatisticalResourcesRestExternalConstants.KIND_DATASETS);

        // Pagination
        String baseLink = toDatasetsLink(agencyID, resourceID, null);
        SculptorCriteria2RestCriteria.toPagedResult(sources, targets, query, orderBy, limit, baseLink);

        // Values
        for (DatasetVersion source : sources.getValues()) {
            Resource target = toResource(source, selectedLanguages);
            targets.getDatasets().add(target);
        }
        return targets;
    }

    @Override
    public Dataset toDataset(DatasetVersion source, Map<String, List<String>> selectedDimensions, List<String> selectedLanguages, Set<String> fields) throws Exception {
        if (source == null) {
            return null;
        }
        Dataset target = new Dataset();
        target.setKind(StatisticalResourcesRestExternalConstants.KIND_DATASET);
        target.setId(source.getSiemacMetadataStatisticalResource().getCode());
        target.setUrn(source.getSiemacMetadataStatisticalResource().getUrn());
        target.setSelfLink(toDatasetSelfLink(source, false));
        target.setName(commonDo2RestMapper.toInternationalString(source.getSiemacMetadataStatisticalResource().getTitle(), selectedLanguages));
        target.setDescription(commonDo2RestMapper.toInternationalString(source.getSiemacMetadataStatisticalResource().getDescription(), selectedLanguages));
        target.setParentLink(toDatasetParentLink(source));
        target.setChildLinks(toDatasetChildLinks(source));
        target.setSelectedLanguages(commonDo2RestMapper.toLanguages(selectedLanguages));

        DsdProcessorResult dsdProcessorResult = null;

        boolean includeMetadata = !containsField(fields, StatisticalResourcesRestExternalConstants.FIELD_EXCLUDE_METADATA);
        boolean includeData = !containsField(fields, StatisticalResourcesRestExternalConstants.FIELD_EXCLUDE_DATA);
        if (includeMetadata || includeData) {
            dsdProcessorResult = commonDo2RestMapper.processDataStructure(source.getRelatedDsd().getUrn());
        }
        if (includeMetadata) {
            target.setMetadata(toDatasetMetadata(source, dsdProcessorResult, selectedLanguages, fields));
        }
        if (includeData) {
            target.setData(commonDo2RestMapper.toData(source, dsdProcessorResult, selectedDimensions, selectedLanguages));
        }
        return target;
    }

    @Override
    public Resource toResource(DatasetVersion source, List<String> selectedLanguages) {
        return toResource(source, false, selectedLanguages);
    }

    @Override
    public Resource toResourceAsLatest(DatasetVersion source, List<String> selectedLanguages) {
        return toResource(source, true, selectedLanguages);
    }

    @Override
    public Resource toResource(RelatedResourceResult source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        if (!TypeRelatedResourceEnum.DATASET_VERSION.equals(source.getType())) {
            logger.error("RelatedResource unsupported: " + source.getType());
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }

        Resource target = new Resource();
        target.setId(source.getCode());
        target.setUrn(source.getUrn());
        target.setKind(StatisticalResourcesRestExternalConstants.KIND_DATASET);
        target.setSelfLink(toDatasetSelfLink(source));
        target.setName(commonDo2RestMapper.toInternationalString(source.getTitle(), selectedLanguages));
        return target;
    }

    private Resource toResource(DatasetVersion source, boolean asLatest, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        target.setId(source.getSiemacMetadataStatisticalResource().getCode());
        target.setUrn(source.getSiemacMetadataStatisticalResource().getUrn());
        target.setKind(StatisticalResourcesRestExternalConstants.KIND_DATASET);
        target.setSelfLink(toDatasetSelfLink(source, asLatest));
        target.setName(commonDo2RestMapper.toInternationalString(source.getSiemacMetadataStatisticalResource().getTitle(), selectedLanguages));
        return target;
    }

    private DatasetMetadata toDatasetMetadata(DatasetVersion source, DsdProcessorResult dsdProcessorResult, List<String> selectedLanguages, Set<String> fields) throws MetamacException {
        if (source == null) {
            return null;
        }
        DatasetMetadata target = new DatasetMetadata();
        target.setRelatedDsd(commonDo2RestMapper.toDataStructureDefinition(source.getRelatedDsd(), dsdProcessorResult.getDataStructure(), selectedLanguages));
        target.setDimensions(commonDo2RestMapper.toDimensions(source.getSiemacMetadataStatisticalResource().getUrn(), dsdProcessorResult, null, selectedLanguages, fields));
        target.setAttributes(commonDo2RestMapper.toAttributes(source.getSiemacMetadataStatisticalResource().getUrn(), dsdProcessorResult, selectedLanguages));
        target.setGeographicCoverages(commonDo2RestMapper.toResourcesExternalItemsSrm(source.getGeographicCoverage(), selectedLanguages));
        target.setTemporalCoverages(toTemporalCoverages(source.getTemporalCoverage(), selectedLanguages));
        target.setMeasureCoverages(commonDo2RestMapper.toResourcesExternalItemsSrm(source.getMeasureCoverage(), selectedLanguages));
        target.setGeographicGranularities(commonDo2RestMapper.toResourcesExternalItemsSrm(source.getGeographicGranularities(), selectedLanguages));
        target.setTemporalGranularities(commonDo2RestMapper.toResourcesExternalItemsSrm(source.getTemporalGranularities(), selectedLanguages));
        target.setDateStart(commonDo2RestMapper.toDate(source.getDateStart()));
        target.setDateEnd(commonDo2RestMapper.toDate(source.getDateEnd()));
        target.setStatisticalUnit(commonDo2RestMapper.toResourcesExternalItemsSrm(source.getStatisticalUnit(), selectedLanguages));
        target.setSubjectAreas(toDatasetSubjectAreas(source, selectedLanguages));
        target.setFormatExtentObservations(source.getFormatExtentObservations());
        target.setFormatExtentDimensions(source.getFormatExtentDimensions());
        target.setDateNextUpdate(commonDo2RestMapper.toDate(source.getDateNextUpdate()));
        target.setUpdateFrequency(commonDo2RestMapper.toResourceExternalItemSrm(source.getUpdateFrequency(), selectedLanguages));
        target.setStatisticOfficiality(toStatisticOfficiality(source.getStatisticOfficiality(), selectedLanguages));
        target.setBibliographicCitation(toBibliographicCitation(source, source.getBibliographicCitation(), selectedLanguages));
        target.setIsRequiredBy(toDatasetIsRequiredBy(source, selectedLanguages));
        target.setReplacesVersion(toDatasetReplacesVersion(source, selectedLanguages));
        target.setIsReplacedByVersion(toDatasetIsReplacedByVersion(source, selectedLanguages));
        target.setReplaces(toDatasetReplaces(source, selectedLanguages));
        target.setIsReplacedBy(toDatasetIsReplacedBy(source, selectedLanguages));
        target.setIsPartOf(toDatasetIsPartOf(source, selectedLanguages));
        target.setKeepAllData(source.isKeepAllData());

        // StatisticalResource and other
        commonDo2RestMapper.toMetadataStatisticalResource(source.getSiemacMetadataStatisticalResource(), target, selectedLanguages);
        return target;
    }

    private Resources toDatasetIsRequiredBy(DatasetVersion source, List<String> selectedLanguages) throws MetamacException {
        List<RelatedResourceResult> relatedResourceIsRequiredBy = null;

        if (StatisticalResourcesRestExternalConstants.IS_INTERNAL_API) {
            relatedResourceIsRequiredBy = datasetVersionRepository.retrieveIsRequiredBy(source);
        } else {
            relatedResourceIsRequiredBy = datasetVersionRepository.retrieveIsRequiredByOnlyLastPublished(source);
        }

        if (CollectionUtils.isEmpty(relatedResourceIsRequiredBy)) {
            return null;
        }
        Resources targets = new Resources();
        for (RelatedResourceResult relatedResourceResult : relatedResourceIsRequiredBy) {
            targets.getResources().add(commonDo2RestMapper.toResource(relatedResourceResult, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    private Resource toDatasetReplacesVersion(DatasetVersion source, List<String> selectedLanguages) throws MetamacException {
        RelatedResource replacesVersion = source.getSiemacMetadataStatisticalResource().getReplacesVersion();
        return commonDo2RestMapper.toResource(replacesVersion, selectedLanguages);
    }

    private Resource toDatasetIsReplacedByVersion(DatasetVersion source, List<String> selectedLanguages) throws MetamacException {
        RelatedResourceResult relatedResourceReplacesByVersion = null;

        if (StatisticalResourcesRestExternalConstants.IS_INTERNAL_API) {
            relatedResourceReplacesByVersion = datasetVersionRepository.retrieveIsReplacedByVersion(source);
        } else {
            relatedResourceReplacesByVersion = datasetVersionRepository.retrieveIsReplacedByVersionOnlyIfPublished(source);
        }
        return toResource(relatedResourceReplacesByVersion, selectedLanguages);
    }

    private Resource toDatasetReplaces(DatasetVersion source, List<String> selectedLanguages) throws MetamacException {
        // There is no need to check if the replaced resource is published. The "replaces" metadata is always filled with a published dataset.
        RelatedResource replaces = source.getSiemacMetadataStatisticalResource().getReplaces();
        return commonDo2RestMapper.toResource(replaces, selectedLanguages);
    }

    private Resource toDatasetIsReplacedBy(DatasetVersion source, List<String> selectedLanguages) throws MetamacException {
        RelatedResourceResult relatedResourceReplacesBy = null;

        if (StatisticalResourcesRestExternalConstants.IS_INTERNAL_API) {
            relatedResourceReplacesBy = datasetVersionRepository.retrieveIsReplacedBy(source);
        } else {
            relatedResourceReplacesBy = datasetVersionRepository.retrieveIsReplacedByOnlyIfPublished(source);
        }
        return toResource(relatedResourceReplacesBy, selectedLanguages);
    }

    private Resources toDatasetIsPartOf(DatasetVersion source, List<String> selectedLanguages) throws MetamacException {
        List<RelatedResourceResult> relatedResourceIsPartOf = null;

        if (StatisticalResourcesRestExternalConstants.IS_INTERNAL_API) {
            relatedResourceIsPartOf = datasetVersionRepository.retrieveIsPartOf(source);
        } else {
            relatedResourceIsPartOf = datasetVersionRepository.retrieveIsPartOfOnlyLastPublished(source);
        }

        if (CollectionUtils.isEmpty(relatedResourceIsPartOf)) {
            return null;
        }
        Resources targets = new Resources();
        for (RelatedResourceResult relatedResourceResult : relatedResourceIsPartOf) {
            targets.getResources().add(commonDo2RestMapper.toResource(relatedResourceResult, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    private Items toTemporalCoverages(List<TemporalCode> sources, List<String> selectedLanguages) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        Items targets = new Items();
        for (TemporalCode source : sources) {
            targets.getItems().add(toTemporalCoverage(source, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getItems().size()));
        return targets;
    }

    private Item toTemporalCoverage(TemporalCode source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(commonDo2RestMapper.toInternationalString(source.getTitle(), selectedLanguages));
        return target;
    }

    private Resources toDatasetSubjectAreas(DatasetVersion source, List<String> selectedLanguages) throws MetamacException {
        if (CollectionUtils.isEmpty(source.getCategorisations())) {
            return null;
        }
        Resources targets = new Resources();
        for (Categorisation categorisation : source.getCategorisations()) {
            targets.getResources().add(commonDo2RestMapper.toResourceExternalItemSrm(categorisation.getCategory(), selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    private Item toStatisticOfficiality(StatisticOfficiality source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Item target = new Item();
        target.setId(source.getIdentifier());
        target.setName(commonDo2RestMapper.toInternationalString(source.getDescription(), selectedLanguages));
        return target;
    }

    private ResourceLink toDatasetParentLink(DatasetVersion source) {
        return toDatasetsSelfLink(null, null, null);
    }

    private ChildLinks toDatasetChildLinks(DatasetVersion source) {
        // nothing
        return null;
    }

    private ResourceLink toDatasetsSelfLink(String agencyID, String resourceID, String version) {
        return commonDo2RestMapper.toResourceLink(StatisticalResourcesRestExternalConstants.KIND_DATASETS, toDatasetsLink(agencyID, resourceID, version));
    }

    private String toDatasetsLink(String agencyID, String resourceID, String version) {
        String resourceSubpath = StatisticalResourcesRestExternalConstants.LINK_SUBPATH_DATASETS;
        return commonDo2RestMapper.toResourceLink(resourceSubpath, agencyID, resourceID, version);
    }

    private ResourceLink toDatasetSelfLink(DatasetVersion source, boolean asLatest) {
        String agencyID = source.getLifeCycleStatisticalResource().getMaintainer().getCodeNested();
        String resourceID = source.getLifeCycleStatisticalResource().getCode();
        String version = null;
        if (asLatest) {
            version = StatisticalResourcesRestExternalConstants.WILDCARD_LATEST;
        } else {
            version = source.getLifeCycleStatisticalResource().getVersionLogic();
        }
        return toDatasetSelfLink(agencyID, resourceID, version);
    }

    private ResourceLink toDatasetSelfLink(RelatedResourceResult source) {
        String agencyID = source.getMaintainerNestedCode();
        String resourceID = source.getCode();
        String version = source.getVersion();
        return toDatasetSelfLink(agencyID, resourceID, version);
    }

    private ResourceLink toDatasetSelfLink(String agencyID, String resourceID, String version) {
        String link = toDatasetLink(agencyID, resourceID, version);
        return commonDo2RestMapper.toResourceLink(StatisticalResourcesRestExternalConstants.KIND_DATASET, link);
    }

    private String toDatasetLink(String agencyID, String resourceID, String version) {
        String resourceSubpath = StatisticalResourcesRestExternalConstants.LINK_SUBPATH_DATASETS;
        return commonDo2RestMapper.toResourceLink(resourceSubpath, agencyID, resourceID, version);
    }

    private String toDatasetLink(DatasetVersion source) throws MetamacException {
        String agencyID = source.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested();
        String resourceID = source.getSiemacMetadataStatisticalResource().getCode();
        String version = source.getSiemacMetadataStatisticalResource().getVersionLogic();

        return toDatasetLink(agencyID, resourceID, version);
    }

    private InternationalString toBibliographicCitation(DatasetVersion datasetVersion, org.siemac.metamac.statistical.resources.core.common.domain.InternationalString sources,
            List<String> selectedLanguages) throws MetamacException {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString source : sources.getTexts()) {
            if (selectedLanguages.contains(source.getLocale())) {
                LocalisedString target = new LocalisedString();
                target.setLang(source.getLocale());
                target.setValue(source.getLabel().replace(StatisticalResourcesConstants.BIBLIOGRAPHIC_CITATION_URI_TOKEN, toDatasetLink(datasetVersion)));
                targets.getTexts().add(target);
            }
        }
        return targets;
    }

}