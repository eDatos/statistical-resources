package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base;

import static org.siemac.metamac.statistical_resources.rest.external.StatisticalResourcesRestExternalConstants.SERVICE_CONTEXT;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.LocalDimensionReferenceType;
import org.sdmx.resources.sdmxml.schemas.v2_1.common.TextType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.SimpleComponentTextFormatType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeTextFormatType;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.Pair;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CodeRepresentation;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CodeRepresentations;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Data;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DataStructureDefinition;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimension;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionRepresentation;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionRepresentations;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionType;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionValues;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionsId;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.EnumeratedDimensionValue;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.EnumeratedDimensionValues;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.NonEnumeratedDimensionValue;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.NonEnumeratedDimensionValues;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.SelectedLanguages;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.StatisticalResource;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.StatisticalResourceType;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleTypes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResource;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DimensionVisualisation;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ShowDecimalPrecision;
import org.siemac.metamac.rest.utils.RestCommonUtil;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponentType;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_resources.rest.external.invocation.SrmRestExternalFacade;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.collection.CollectionsDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset.DatasetsDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.query.QueriesDo2RestMapperV10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.ConditionDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationExtendedDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

@Component
public class CommonDo2RestMapperV10Impl implements CommonDo2RestMapperV10 {

    private static final Logger              logger = LoggerFactory.getLogger(CommonDo2RestMapperV10Impl.class);

    @Autowired
    private ConfigurationService             configurationService;

    @Autowired
    private DatasetService                   datasetService;

    @Autowired
    private DatasetRepositoriesServiceFacade datasetRepositoriesServiceFacade;

    @Autowired
    private SrmRestExternalFacade            srmRestExternalFacade;

    @Autowired
    private DatasetsDo2RestMapperV10         datasetsDo2RestMapper;

    @Autowired
    private CollectionsDo2RestMapperV10      collectionsDo2RestMapper;

    @Autowired
    private QueriesDo2RestMapperV10          queriesDo2RestMapper;

    private String                           statisticalResourcesApiExternalEndpointV10;
    private String                           srmApiExternalEndpoint;
    private String                           statisticalOperationsApiExternalEndpoint;
    private String                           defaultLanguage;

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
        statisticalOperationsApiExternalEndpoint = StringUtils.removeEnd(statisticalOperationsApiExternalEndpoint, "/");

        // MISC
        defaultLanguage = configurationService.retrieveLanguageDefault();
    }

    @Override
    public void toMetadataStatisticalResource(SiemacMetadataStatisticalResource source, StatisticalResource target, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return;
        }
        target.setLanguage(toResourceExternalItemSrm(source.getLanguage(), selectedLanguages));
        target.setLanguages(toResourcesExternalItemsSrm(source.getLanguages(), selectedLanguages));
        target.setStatisticalOperation(toResourceExternalItemStatisticalOperations(source.getStatisticalOperation(), selectedLanguages));
        target.setStatisticalOperationInstances(toResourcesExternalItemsStatisticalOperations(source.getStatisticalOperationInstances(), selectedLanguages));
        target.setSubtitle(toInternationalString(source.getSubtitle(), selectedLanguages));
        target.setTitleAlternative(toInternationalString(source.getTitleAlternative(), selectedLanguages));
        target.setAbstract(toInternationalString(source.getAbstractLogic(), selectedLanguages));
        target.setKeywords(toInternationalString(source.getKeywords(), selectedLanguages));
        target.setType(toStatisticalResourceType(source.getType()));
        target.setCreator(toResourceExternalItemSrm(source.getCreator(), selectedLanguages));
        target.setContributors(toResourcesExternalItemsSrm(source.getContributor(), selectedLanguages));
        target.setCreatedDate(toDate(source.getResourceCreatedDate()));
        target.setLastUpdate(toDate(source.getLastUpdate()));
        target.setConformsTo(toInternationalString(source.getConformsTo(), selectedLanguages));
        target.setPublishers(toResourcesExternalItemsSrm(source.getPublisher(), selectedLanguages));
        target.setPublisherContributors(toResourcesExternalItemsSrm(source.getPublisherContributor(), selectedLanguages));
        target.setMediators(toResourcesExternalItemsSrm(source.getMediator(), selectedLanguages));
        target.setNewnessUntilDate(toDate(source.getNewnessUntilDate()));

        target.setReplaces(toResource(source.getReplaces(), selectedLanguages));
        target.setIsReplacedBy(toResource(source.getIsReplacedBy(), selectedLanguages));
        target.setIsReplacedBy(toResource(source.getIsReplacedBy(), selectedLanguages));
        target.setRequires(toResources(source.getRequires(), selectedLanguages));
        target.setIsRequiredBy(toResources(source.getIsRequiredBy(), selectedLanguages));
        target.setHasPart(toResources(source.getHasPart(), selectedLanguages));
        target.setIsPartOf(toResources(source.getIsPartOf(), selectedLanguages));

        // TODO common-metadata
        // target.setRightsHolder(toResourceExternalItemSrm(source.getRightsHolder(), selectedLanguages));
        // target.setLicense(toInternationalString(source.getLicense(), selectedLanguages));
        target.setCopyrightDate(toDate(source.getCopyrightedDate()));
        target.setAccessRights(toInternationalString(source.getAccessRights(), selectedLanguages));

        // Lifecycle
        target.setPublicationDate(toDate(source.getPublicationDate()));
        target.setReplacesVersion(toResource(source.getReplacesVersion(), selectedLanguages));
        target.setIsReplacedByVersion(toResource(source.getIsReplacedByVersion(), selectedLanguages));
        target.setMaintainer(toResourceExternalItemSrm(source.getMaintainer(), selectedLanguages));

        // Versionable
        target.setVersion(source.getVersionLogic());
        target.setVersionRationaleTypes(toVersionRationaleTypes(source.getVersionRationaleTypes(), selectedLanguages));
        target.setVersionRationale(toInternationalString(source.getVersionRationale(), selectedLanguages));
        target.setValidFrom(toDate(source.getValidFrom()));
        target.setValidTo(toDate(source.getValidTo()));
    }

    /**
     * @param effectiveDimensionValuesToDataByDimension It is necessary when query is retrieved, to filter dimension values. It can be null; in this case, returns all
     */
    @Override
    public Pair<DataStructureDefinition, Dimensions> toDataStructureDefinitionAndDimensions(DatasetVersion datasetVersion, Map<String, List<String>> effectiveDimensionValuesToDataByDimension,
            List<String> selectedLanguages) throws MetamacException {
        DataStructure dataStructure = srmRestExternalFacade.retrieveDataStructureByUrn(datasetVersion.getRelatedDsd().getUrn());
        List<DsdDimension> dimensionsType = DsdProcessor.getDimensions(dataStructure);

        DataStructureDefinition relatedDsd = toDataStructureDefinition(datasetVersion.getRelatedDsd(), dataStructure, selectedLanguages);
        Dimensions dimensions = toDimensions(dataStructure, dimensionsType, datasetVersion.getSiemacMetadataStatisticalResource().getUrn(), effectiveDimensionValuesToDataByDimension,
                selectedLanguages);

        return new Pair<DataStructureDefinition, Dimensions>(relatedDsd, dimensions);
    }

    @Override
    public Data toData(DatasetVersion source, List<String> selectedLanguages, Map<String, List<String>> dimensionValuesSelected) throws Exception {
        if (source == null) {
            return null;
        }
        Data target = new Data();

        // Filter values // TODO validate values in coverage?
        List<String> dimensions = datasetService.retrieveDatasetVersionDimensionsIds(SERVICE_CONTEXT, source.getSiemacMetadataStatisticalResource().getUrn());
        Map<String, List<String>> dimensionsValuesSelectedEffective = buildDimensionsSelectedWithValues(source, dimensionValuesSelected, dimensions);

        // Observations
        String observations = toDataObservations(source, dimensions, dimensionValuesSelected, dimensionsValuesSelectedEffective);
        target.setObservations(observations);

        // Dimensions
        target.setDimensions(new DimensionRepresentations());

        for (String dimension : dimensions) {
            DimensionRepresentation representation = new DimensionRepresentation();
            representation.setDimensionId(dimension);
            representation.setRepresentations(new CodeRepresentations());

            int valuesSize = 0;
            for (String dimensionValue : dimensionsValuesSelectedEffective.get(dimension)) {
                CodeRepresentation codeRepresentation = new CodeRepresentation();
                codeRepresentation.setCode(dimensionValue);
                codeRepresentation.setIndex(valuesSize);
                representation.getRepresentations().getRepresentations().add(codeRepresentation);
                valuesSize++;
            }
            representation.getRepresentations().setTotal(BigInteger.valueOf(representation.getRepresentations().getRepresentations().size()));
            target.getDimensions().getDimensions().add(representation);
        }
        target.getDimensions().setTotal(BigInteger.valueOf(target.getDimensions().getDimensions().size()));

        return target;
    }

    @Override
    public List<String> codeItemToString(List<CodeItem> sources) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        List<String> targets = new ArrayList<String>(sources.size());
        for (CodeItem source : sources) {
            targets.add(source.getCode());
        }
        return targets;
    }

    @Override
    public List<String> temporalCoverageToString(List<TemporalCode> sources) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        List<String> targets = new ArrayList<String>(sources.size());
        for (TemporalCode source : sources) {
            targets.add(source.getIdentifier());
        }
        return targets;
    }

    @Override
    public Map<String, List<String>> calculateEffectiveDimensionValuesToQuery(QueryVersion source) {
        Map<String, List<String>> dimensionValuesSelected = new HashMap<String, List<String>>(source.getSelection().size());
        for (QuerySelectionItem selection : source.getSelection()) {
            List<String> dimensionValues = calculateEffectiveDimensionValuesToQuery(source, selection);
            dimensionValuesSelected.put(selection.getDimension(), dimensionValues);
        }
        return dimensionValuesSelected;
    }

    private List<String> calculateEffectiveDimensionValuesToQuery(QueryVersion source, QuerySelectionItem selection) {
        DatasetVersion datasetVersion = source.getDatasetVersion();
        QueryTypeEnum type = source.getType();
        String dimensionId = selection.getDimension();

        List<String> effectiveDimensionValues = codeItemToString(selection.getCodes());
        if (effectiveDimensionValues == null) {
            effectiveDimensionValues = new ArrayList<String>();
        }

        if (QueryTypeEnum.FIXED.equals(type)) {
            // return exactly
            return effectiveDimensionValues;
        } else if (QueryTypeEnum.AUTOINCREMENTAL.equals(type)) {
            if (isTemporalDimension(dimensionId)) {
                List<String> temporalCoverageCodes = temporalCoverageToString(datasetVersion.getTemporalCoverage());
                int indexLatestTemporalCodeInCreation = temporalCoverageCodes.indexOf(source.getLatestTemporalCodeInCreation());
                if (datasetVersion.getTemporalCoverage().size() > indexLatestTemporalCodeInCreation + 1) {
                    // add codes added after query creation
                    List<TemporalCode> temporalCodesAddedAfterQueryCreation = datasetVersion.getTemporalCoverage().subList(indexLatestTemporalCodeInCreation + 1,
                            datasetVersion.getTemporalCoverage().size());
                    effectiveDimensionValues.addAll(temporalCoverageToString(temporalCodesAddedAfterQueryCreation));
                }
                return effectiveDimensionValues;
            } else {
                // return exactly
                return effectiveDimensionValues;
            }
        } else if (QueryTypeEnum.LATEST_DATA.equals(type)) {
            if (isTemporalDimension(dimensionId)) {
                // return N data
                int codeIndexToReturn = -1;
                if (datasetVersion.getTemporalCoverage().size() < source.getLatestDataNumber()) {
                    codeIndexToReturn = 0; // there is not N data, so return all
                } else {
                    codeIndexToReturn = datasetVersion.getTemporalCoverage().size() - source.getLatestDataNumber();
                }
                List<TemporalCode> temporalCodesLatestDataNumber = datasetVersion.getTemporalCoverage().subList(codeIndexToReturn, datasetVersion.getTemporalCoverage().size());
                return temporalCoverageToString(temporalCodesLatestDataNumber);
            } else {
                // return exactly
                return effectiveDimensionValues;
            }
        } else {
            logger.error("QueryTypeEnum unsupported: " + source);
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Resource toResourceExternalItemStatisticalOperations(ExternalItem source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        toResourceExternalItem(source, statisticalOperationsApiExternalEndpoint, target, selectedLanguages);
        return target;
    }

    @Override
    public Resources toResourcesExternalItemsStatisticalOperations(List<ExternalItem> sources, List<String> selectedLanguages) {
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

    @Override
    public Resource toResourceExternalItemSrm(ExternalItem source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        toResourceExternalItem(source, srmApiExternalEndpoint, target, selectedLanguages);
        return target;
    }

    @Override
    public void toResourceExternalItemSrm(ExternalItem source, Resource target, List<String> selectedLanguages) {
        if (source == null) {
            return;
        }
        toResourceExternalItem(source, srmApiExternalEndpoint, target, selectedLanguages);
    }

    @Override
    public Resources toResourcesExternalItemsSrm(List<ExternalItem> sources, List<String> selectedLanguages) {
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

    @Override
    public InternationalString toInternationalString(org.siemac.metamac.core.common.ent.domain.InternationalString sources, List<String> selectedLanguages) {
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

    @Override
    public InternationalString toInternationalString(List<TextType> sources, List<String> selectedLanguages) {
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

    @Override
    public InternationalString toInternationalString(InternationalString sources, List<String> selectedLanguages) {
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

    @Override
    public InternationalString toInternationalString(String source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        LocalisedString target = new LocalisedString();
        target.setLang(defaultLanguage);
        target.setValue(source);
        targets.getTexts().add(target);
        return targets;
    }

    @Override
    public Date toDate(DateTime source) {
        return RestCommonUtil.transformDateTimeToDate(source);
    }

    @Override
    public SelectedLanguages toLanguages(List<String> selectedLanguages) {
        SelectedLanguages target = new SelectedLanguages();
        target.getLanguages().addAll(selectedLanguages);
        target.setTotal(BigInteger.valueOf(target.getLanguages().size()));
        return target;
    }

    @Override
    public ResourceLink toResourceLink(String kind, String href) {
        ResourceLink target = new ResourceLink();
        target.setKind(kind);
        target.setHref(href);
        return target;
    }

    // API/[ARTEFACT_TYPE]
    // API/[ARTEFACT_TYPE]/{agencyID}
    // API/[ARTEFACT_TYPE]/{agencyID}/{resourceID}
    // API/[ARTEFACT_TYPE]/{agencyID}/{resourceID}/{version}
    @Override
    public String toResourceLink(String resourceSubpath, String agencyID, String resourceID, String version) {
        String link = RestUtils.createLink(statisticalResourcesApiExternalEndpointV10, resourceSubpath);
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

    private Resources toResources(List<RelatedResource> sources, List<String> selectedLanguages) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        Resources targets = new Resources();
        for (RelatedResource source : sources) {
            targets.getResources().add(toResource(source, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    private Resource toResource(RelatedResource source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        switch (source.getType()) {
            case DATASET_VERSION:
                return datasetsDo2RestMapper.toResource(source.getDatasetVersion(), selectedLanguages);
            case QUERY_VERSION:
                return queriesDo2RestMapper.toResource(source.getQueryVersion(), selectedLanguages);
            case PUBLICATION_VERSION:
                return collectionsDo2RestMapper.toResource(source.getPublicationVersion(), selectedLanguages);
            default:
                logger.error("RelatedResource unsupported: " + source.getType());
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private DataStructureDefinition toDataStructureDefinition(ExternalItem source, DataStructure dataStructure, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        DataStructureDefinition target = new DataStructureDefinition();
        toResourceExternalItemSrm(source, target, selectedLanguages);
        target.setHeading(toDimensionsId(dataStructure.getHeading()));
        target.setStub(toDimensionsId(dataStructure.getStub()));
        target.setAutoOpen(dataStructure.isAutoOpen());
        target.setShowDecimals(dataStructure.getShowDecimals());
        return target;
    }

    private Dimensions toDimensions(DataStructure dataStructure, List<DsdDimension> sources, String datasetVersionUrn, Map<String, List<String>> effectiveDimensionValuesToDataByDimension,
            List<String> selectedLanguages) throws MetamacException {

        List<String> dimensionsId = datasetService.retrieveDatasetVersionDimensionsIds(SERVICE_CONTEXT, datasetVersionUrn);
        if (CollectionUtils.isEmpty(dimensionsId)) {
            return null;
        }

        // TODO pasar a DsdProcessor?
        Map<String, DimensionVisualisation> dimensionVisualisationByDimensionId = null;
        if (dataStructure.getDimensionVisualisations() != null && !CollectionUtils.isEmpty(dataStructure.getDimensionVisualisations().getDimensionVisualisations())) {
            dimensionVisualisationByDimensionId = new HashMap<String, DimensionVisualisation>(dataStructure.getDimensionVisualisations().getDimensionVisualisations().size());
            for (DimensionVisualisation dimensionVisualisation : dataStructure.getDimensionVisualisations().getDimensionVisualisations()) {
                dimensionVisualisationByDimensionId.put(dimensionVisualisation.getDimension().getRef().getId(), dimensionVisualisation);
            }
        }

        Dimensions targets = new Dimensions();
        for (DsdDimension source : sources) {
            String dimensionId = source.getComponentId();
            DimensionVisualisation dimensionVisualisation = dimensionVisualisationByDimensionId != null ? dimensionVisualisationByDimensionId.get(dimensionId) : null;
            List<String> effectiveDimensionValuesToData = null;
            if (effectiveDimensionValuesToDataByDimension != null) {
                effectiveDimensionValuesToData = effectiveDimensionValuesToDataByDimension.get(dimensionId);
            }
            Dimension target = toDimension(datasetVersionUrn, dataStructure, source, dimensionVisualisation, effectiveDimensionValuesToData, selectedLanguages);
            targets.getDimensions().add(target);
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensions().size()));
        return targets;
    }

    private Dimension toDimension(String datasetVersionUrn, DataStructure dataStructure, DsdDimension source, DimensionVisualisation dimensionVisualisation,
            List<String> effectiveDimensionValuesToData, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        Dimension target = new Dimension();
        target.setId(source.getComponentId());
        target.setType(toDimensionType(source.getType()));

        Concept conceptIdentity = srmRestExternalFacade.retrieveConceptByUrn(source.getConceptIdentityUrn());
        target.setName(toInternationalString(conceptIdentity.getNames(), selectedLanguages));

        // Dimension values
        target.setDimensionValues(toDimensionValues(datasetVersionUrn, dataStructure, source, dimensionVisualisation, effectiveDimensionValuesToData, selectedLanguages));
        return target;
    }

    private DimensionValues toDimensionValues(String datasetVersionUrn, DataStructure dataStructure, DsdDimension dimension, DimensionVisualisation dimensionVisualisation,
            List<String> effectiveDimensionValuesToData, List<String> selectedLanguages) throws MetamacException {
        if (dimension == null) {
            return null;
        }
        List<CodeDimension> coverages = datasetService.retrieveCoverageForDatasetVersionDimension(SERVICE_CONTEXT, datasetVersionUrn, dimension.getComponentId());
        if (CollectionUtils.isEmpty(coverages)) {
            return null;
        }
        Map<String, CodeDimension> coveragesById = new HashMap<String, CodeDimension>(coverages.size());
        for (CodeDimension coverage : coverages) {
            coveragesById.put(coverage.getIdentifier(), coverage);
        }

        DimensionValues targets = null;
        if (dimension.getCodelistRepresentationUrn() != null) {
            targets = toEnumeratedDimensionValuesFromCodelist(coveragesById, dimension.getCodelistRepresentationUrn(), dimensionVisualisation, effectiveDimensionValuesToData, selectedLanguages);
        } else if (dimension.getConceptSchemeRepresentationUrn() != null) {
            targets = toEnumeratedDimensionValuesFromConceptScheme(coveragesById, dataStructure, dimension.getType(), dimension.getConceptSchemeRepresentationUrn(), effectiveDimensionValuesToData,
                    selectedLanguages);
        } else if (dimension.getTimeTextFormatRepresentation() != null) {
            targets = toNonEnumeratedDimensionValuesFromTimeTextFormatType(coveragesById, dimension.getTimeTextFormatRepresentation(), effectiveDimensionValuesToData, selectedLanguages);
        } else if (dimension.getTextFormatRepresentation() != null) {
            targets = toNonEnumeratedDimensionValuesFromTextFormatType(coveragesById, dimension.getTextFormatRepresentation(), effectiveDimensionValuesToData, selectedLanguages);
        } else {
            logger.error("Dimension definition unsupported for dimension: " + dimension.getComponentId());
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }

        return targets;
    }

    private EnumeratedDimensionValues toEnumeratedDimensionValuesFromCodelist(Map<String, CodeDimension> coveragesById, String codelistUrn, DimensionVisualisation dimensionVisualisation,
            List<String> effectiveDimensionValuesToData, List<String> selectedLanguages) throws MetamacException {
        if (codelistUrn == null) {
            return null;
        }
        EnumeratedDimensionValues targets = new EnumeratedDimensionValues();

        // This map contains nodes that are not in the result. If a child of this nodes is in the result, we use this map to put it inside the nearest parent node in result
        Map<String, String> parentsReplacedToVisualisation = new HashMap<String, String>();

        String order = dimensionVisualisation != null ? dimensionVisualisation.getOrder() : null;
        String openness = dimensionVisualisation != null ? dimensionVisualisation.getOpenness() : null;
        Codes codes = srmRestExternalFacade.retrieveCodesByCodelistUrn(codelistUrn, order, openness); // note: srm api returns codes in order
        for (CodeResource code : codes.getCodes()) {
            String id = code.getId();
            boolean skip = false;
            if (effectiveDimensionValuesToData != null) {
                // note: all values in effectiveDimensionValuesToData are always in coverages
                if (!effectiveDimensionValuesToData.contains(id)) {
                    // skip to include only values in query
                    skip = true;
                }
            } else {
                if (!coveragesById.containsKey(id)) {
                    // skip to include only values in coverage
                    skip = true;
                }
            }
            if (skip) {
                updateParentsReplacedToVisualisationWithNotEffectiveDimensionValue(parentsReplacedToVisualisation, code);
                continue;
            }
            targets.getValues().add(toEnumeratedDimensionValue(code, parentsReplacedToVisualisation, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    /**
     * Update map with parent visualisation.
     * This map contains nodes that are not in the result. If a child of this nodes is in the result, we use this map to put it inside the nearest parent node in result
     * The parent visualisation will be the nearest parent in result
     */
    private void updateParentsReplacedToVisualisationWithNotEffectiveDimensionValue(Map<String, String> parentsReplacedToVisualisation, ItemResourceInternal code) {
        String parentVisualisationEffective = null;
        if (code.getParent() == null) {
            parentVisualisationEffective = null;
        } else {
            if (!parentsReplacedToVisualisation.containsKey(code.getParent())) {
                parentVisualisationEffective = code.getParent();
            } else {
                parentVisualisationEffective = parentsReplacedToVisualisation.get(code.getParent());
            }
        }
        parentsReplacedToVisualisation.put(code.getUrn(), parentVisualisationEffective);
    }

    private EnumeratedDimensionValues toEnumeratedDimensionValuesFromConceptScheme(Map<String, CodeDimension> coveragesById, DataStructure dataStructure, DsdComponentType componentType,
            String conceptSchemeUrn, List<String> effectiveDimensionValuesToData, List<String> selectedLanguages) throws MetamacException {
        if (conceptSchemeUrn == null) {
            return null;
        }
        EnumeratedDimensionValues targets = new EnumeratedDimensionValues();

        Map<String, Integer> showDecimalPrecisionsById = null;
        if (DsdComponentType.MEASURE.equals(componentType)) {
            if (dataStructure.getShowDecimalsPrecisions() != null && !CollectionUtils.isEmpty(dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions())) {
                showDecimalPrecisionsById = new HashMap<String, Integer>(dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions().size());
                for (ShowDecimalPrecision showDecimalPrecision : dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions()) {
                    showDecimalPrecisionsById.put(showDecimalPrecision.getConcept().getRef().getId(), showDecimalPrecision.getShowDecimals());
                }
            }
        }

        // This map contains nodes that are not in the result. If a child of this nodes is in the result, we use this map to put it inside the nearest parent node in result
        Map<String, String> parentsReplacedToVisualisation = new HashMap<String, String>();

        Concepts concepts = srmRestExternalFacade.retrieveConceptsByConceptSchemeByUrn(conceptSchemeUrn);
        for (ItemResourceInternal concept : concepts.getConcepts()) {
            String id = concept.getId();
            boolean skip = false;
            if (effectiveDimensionValuesToData != null) {
                // note: all values in effectiveDimensionValuesToData are always in coverages
                if (!effectiveDimensionValuesToData.contains(id)) {
                    // skip to include only values in query
                    skip = true;
                }
            } else {
                if (!coveragesById.containsKey(id)) {
                    // skip to include only values in coverage
                    skip = true;
                }
            }
            if (skip) {
                updateParentsReplacedToVisualisationWithNotEffectiveDimensionValue(parentsReplacedToVisualisation, concept);
                continue;
            }
            targets.getValues().add(toEnumeratedDimensionValue(concept, showDecimalPrecisionsById, parentsReplacedToVisualisation, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private NonEnumeratedDimensionValues toNonEnumeratedDimensionValuesFromTimeTextFormatType(Map<String, CodeDimension> coveragesById, TimeTextFormatType timeTextFormatType,
            List<String> effectiveDimensionValuesToData, List<String> selectedLanguages) throws MetamacException {
        if (timeTextFormatType == null) {
            return null;
        }
        // note: timeTextFormatType definition is not necessary to define dimension values
        NonEnumeratedDimensionValues targets = new NonEnumeratedDimensionValues();
        for (String coverageId : coveragesById.keySet()) {
            if (effectiveDimensionValuesToData != null) {
                if (!effectiveDimensionValuesToData.contains(coverageId)) {
                    // skip to include only values in query
                    continue;
                }
            }
            CodeDimension codeDimension = coveragesById.get(coverageId);
            targets.getValues().add(toNonEnumeratedDimensionValue(codeDimension, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private NonEnumeratedDimensionValues toNonEnumeratedDimensionValuesFromTextFormatType(Map<String, CodeDimension> coveragesById, SimpleComponentTextFormatType textFormatType,
            List<String> effectiveDimensionValuesToData, List<String> selectedLanguages) throws MetamacException {
        if (textFormatType == null) {
            return null;
        }
        // note: textFormatType definition is not necessary to define dimension values
        NonEnumeratedDimensionValues targets = new NonEnumeratedDimensionValues();
        for (String coverageId : coveragesById.keySet()) {
            if (effectiveDimensionValuesToData != null) {
                if (!effectiveDimensionValuesToData.contains(coverageId)) {
                    // skip to include only values in query
                    continue;
                }
            }
            CodeDimension codeDimension = coveragesById.get(coverageId);
            targets.getValues().add(toNonEnumeratedDimensionValue(codeDimension, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private EnumeratedDimensionValue toEnumeratedDimensionValue(CodeResource source, Map<String, String> parentsReplacedToVisualisation, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        EnumeratedDimensionValue target = new EnumeratedDimensionValue();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setName(toInternationalString(source.getName(), selectedLanguages));
        if (source.getParent() != null) {
            if (!parentsReplacedToVisualisation.containsKey(source.getParent())) {
                target.setVisualisationParent(source.getParent());
            } else {
                target.setVisualisationParent(parentsReplacedToVisualisation.get(source.getParent()));
            }
        }
        target.setKind(source.getKind());
        target.setSelfLink(source.getSelfLink());

        target.setOpen(source.isOpen());
        return target;
    }

    private EnumeratedDimensionValue toEnumeratedDimensionValue(ItemResourceInternal source, Map<String, Integer> showDecimalPrecisionsById, Map<String, String> effectiveParentVisualisation,
            List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        EnumeratedDimensionValue target = new EnumeratedDimensionValue();
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setName(toInternationalString(source.getName(), selectedLanguages));
        if (source.getParent() != null) {
            if (!effectiveParentVisualisation.containsKey(source.getParent())) {
                target.setVisualisationParent(source.getParent());
            } else {
                target.setVisualisationParent(effectiveParentVisualisation.get(source.getParent()));
            }
        }
        if (showDecimalPrecisionsById != null && showDecimalPrecisionsById.containsKey(source.getId())) {
            target.setShowDecimalsPrecision(showDecimalPrecisionsById.get(source.getId()));
        }
        target.setKind(source.getKind());
        target.setSelfLink(source.getSelfLink());
        return target;
    }

    private NonEnumeratedDimensionValue toNonEnumeratedDimensionValue(CodeDimension source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        NonEnumeratedDimensionValue target = new NonEnumeratedDimensionValue();
        target.setId(source.getIdentifier());
        target.setName(toInternationalString(source.getTitle(), selectedLanguages));
        return target;
    }

    private DimensionType toDimensionType(DsdComponentType source) {
        switch (source) {
            case OTHER:
                return DimensionType.DIMENSION;
            case SPATIAL:
                return DimensionType.GEOGRAPHIC_DIMENSION;
            case TEMPORAL:
                return DimensionType.TIME_DIMENSION;
            case MEASURE:
                return DimensionType.MEASURE_DIMENSION;
            default:
                logger.error("DsdComponentType unsupported: " + source);
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private VersionRationaleTypes toVersionRationaleTypes(List<VersionRationaleType> sources, List<String> selectedLanguages) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        VersionRationaleTypes targets = new VersionRationaleTypes();
        for (VersionRationaleType source : sources) {
            targets.getVersionRationaleTypes().add(toVersionRationaleType(source.getValue()));
        }
        targets.setTotal(BigInteger.valueOf(targets.getVersionRationaleTypes().size()));
        return targets;
    }

    private StatisticalResourceType toStatisticalResourceType(StatisticalResourceTypeEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case DATASET:
                return StatisticalResourceType.DATASET;
            case QUERY:
                return StatisticalResourceType.QUERY;
            case COLLECTION:
                return StatisticalResourceType.COLLECTION;
            default:
                logger.error("StatisticalResourceTypeEnum unsupported: " + source);
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType toVersionRationaleType(VersionRationaleTypeEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case MAJOR_NEW_RESOURCE:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MAJOR_NEW_RESOURCE;
            case MAJOR_ESTIMATORS:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MAJOR_ESTIMATORS;
            case MAJOR_CATEGORIES:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MAJOR_CATEGORIES;
            case MAJOR_VARIABLES:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MAJOR_VARIABLES;
            case MAJOR_OTHER:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MAJOR_OTHER;
            case MINOR_ERRATA:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MINOR_ERRATA;
            case MINOR_METADATA:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MINOR_METADATA;
            case MINOR_DATA_UPDATE:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MINOR_DATA_UPDATE;
            case MINOR_SERIES_UPDATE:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MINOR_SERIES_UPDATE;
            case MINOR_OTHER:
                return org.siemac.metamac.rest.statistical_resources.v1_0.domain.VersionRationaleType.MINOR_OTHER;
            default:
                logger.error("VersionRationaleTypeEnum unsupported: " + source);
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Build dimensions selected, with codes selected or all codes if codes are not selected to one dimension
     */
    private Map<String, List<String>> buildDimensionsSelectedWithValues(DatasetVersion source, Map<String, List<String>> dimensionsSelected, List<String> dimensions) throws MetamacException {
        Map<String, List<String>> dimensionsCodesSelected = new HashMap<String, List<String>>();
        for (String dimension : dimensions) {
            List<String> dimensionValues = dimensionsSelected.get(dimension);
            List<String> dimensionValuesSelected = new ArrayList<String>();
            if (CollectionUtils.isEmpty(dimensionValues)) {
                // if dimension is not selected in query, retrieve all codes from coverage
                List<CodeDimension> codeDimensions = datasetService.retrieveCoverageForDatasetVersionDimension(SERVICE_CONTEXT, source.getSiemacMetadataStatisticalResource().getUrn(), dimension);
                for (CodeDimension codeDimension : codeDimensions) {
                    dimensionValuesSelected.add(codeDimension.getIdentifier());
                }
            } else {
                dimensionValuesSelected.addAll(dimensionValues);
            }
            dimensionsCodesSelected.put(dimension, dimensionValuesSelected);
        }
        return dimensionsCodesSelected;
    }

    /**
     * Retrieve observations of selected code
     * FIXME attributes
     */
    private String toDataObservations(DatasetVersion source, List<String> dimensions, Map<String, List<String>> dimensionsSelected, Map<String, List<String>> dimensionsCodesSelectedEffective)
            throws Exception {

        if (MapUtils.isEmpty(dimensionsCodesSelectedEffective)) {
            return null;
        }

        // Search observations in repository
        List<ConditionDimensionDto> conditions = generateConditions(dimensionsSelected);
        Map<String, ObservationExtendedDto> datas = datasetRepositoriesServiceFacade.findObservationsExtendedByDimensions(source.getDatasetRepositoryId(), conditions);

        // Observation Size
        int sizeObservation = 1;
        for (String dimension : dimensionsCodesSelectedEffective.keySet()) {
            sizeObservation = sizeObservation * dimensionsCodesSelectedEffective.get(dimension).size();
        }
        // OBSERVATIONS (return sorted)
        List<String> dataObservations = new ArrayList<String>(sizeObservation);
        Stack<OrderingStackElement> stack = new Stack<OrderingStackElement>();
        stack.push(new OrderingStackElement(StringUtils.EMPTY, -1));
        ArrayList<String> entryId = new ArrayList<String>(dimensions.size());
        for (int i = 0; i < dimensions.size(); i++) {
            entryId.add(i, StringUtils.EMPTY);
        }

        int lastDimension = dimensions.size() - 1;
        int current = 0;
        while (stack.size() > 0) {
            // POP
            OrderingStackElement elem = stack.pop();
            int elemDimension = elem.getDimNum();
            String elemCode = elem.getCodeId();

            // The first time we don't need a hash (#)
            if (elemDimension != -1) {
                entryId.set(elemDimension, elemCode);
            }

            // The entry is complete
            if (elemDimension == lastDimension) {
                String id = StringUtils.join(entryId, "#");

                // We have the full entry here
                ObservationExtendedDto value = datas.get(id);
                if (value != null) {
                    dataObservations.add(value.getPrimaryMeasure());
                } else {
                    dataObservations.add(null); // Return observation null
                }
                entryId.set(elemDimension, StringUtils.EMPTY);
                current++;
            } else {
                String dimension = dimensions.get(elemDimension + 1);
                List<String> dimensionValues = dimensionsCodesSelectedEffective.get(dimension);
                for (int i = dimensionValues.size() - 1; i >= 0; i--) {
                    OrderingStackElement temp = new OrderingStackElement(dimensionValues.get(i), elemDimension + 1);
                    stack.push(temp);
                }
            }
        }
        return StringUtils.join(dataObservations.iterator(), RestExternalConstants.DATA_OBSERVATION_SEPARATOR);
    }

    private List<ConditionDimensionDto> generateConditions(Map<String, List<String>> dimensions) {
        List<ConditionDimensionDto> conditionDimensionDtos = new ArrayList<ConditionDimensionDto>();
        for (Map.Entry<String, List<String>> entry : dimensions.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            ConditionDimensionDto conditionDimensionDto = new ConditionDimensionDto();
            conditionDimensionDto.setDimensionId(entry.getKey());
            for (String value : entry.getValue()) {
                conditionDimensionDto.getCodesDimension().add(value);
            }
            conditionDimensionDtos.add(conditionDimensionDto);
        }
        return conditionDimensionDtos;
    }

    private boolean isTemporalDimension(String dimensionId) {
        return StatisticalResourcesConstants.TEMPORAL_DIMENSION_ID.equals(dimensionId);
    }

    private DimensionsId toDimensionsId(org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimensions sources) {
        if (sources == null) {
            return null;
        }
        DimensionsId targets = new DimensionsId();
        for (LocalDimensionReferenceType source : sources.getDimensions()) {
            targets.getDimensionIds().add(toDimensionId(source));
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensionIds().size()));
        return targets;
    }

    private String toDimensionId(LocalDimensionReferenceType source) {
        if (source == null) {
            return null;
        }
        return source.getRef().getId();
    }

    private class OrderingStackElement {

        private String codeId = null;
        private int    dimNum = -1;

        public OrderingStackElement(String codeId, int dimNum) {
            super();
            this.codeId = codeId;
            this.dimNum = dimNum;
        }

        public String getCodeId() {
            return codeId;
        }

        public int getDimNum() {
            return dimNum;
        }
    }

}