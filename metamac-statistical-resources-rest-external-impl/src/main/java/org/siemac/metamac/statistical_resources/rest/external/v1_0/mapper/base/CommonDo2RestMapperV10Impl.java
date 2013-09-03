package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base;

import static org.siemac.metamac.statistical_resources.rest.external.RestExternalConstantsPrivate.KEY_DIMENSIONS_SEPARATOR;
import static org.siemac.metamac.statistical_resources.rest.external.RestExternalConstantsPrivate.SERVICE_CONTEXT;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
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
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common.v1_0.domain.Resources;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Attribute;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.AttributeAttachmentLevelType;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.AttributeDimension;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.AttributeDimensions;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Attributes;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CodeRepresentation;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.CodeRepresentations;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Data;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DataAttribute;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DataAttributes;
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
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DimensionVisualisation;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ShowDecimalPrecision;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.TextFormat;
import org.siemac.metamac.rest.utils.RestCommonUtil;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
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
import org.siemac.metamac.statistical_resources.rest.external.invocation.CommonMetadataRestExternalFacade;
import org.siemac.metamac.statistical_resources.rest.external.invocation.SrmRestExternalFacade;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.domain.DsdProcessorResult;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.collection.CollectionsDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset.DatasetsDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.query.QueriesDo2RestMapperV10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.ConditionDimensionDto;
import com.arte.statistic.dataset.repository.dto.ObservationDto;
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
    private CommonMetadataRestExternalFacade commonMetadataRestExternalFacade;

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
    public DsdProcessorResult processDataStructure(String urn) throws MetamacException {
        DsdProcessorResult dsdProcessorResult = new DsdProcessorResult();
        DataStructure dataStructure = srmRestExternalFacade.retrieveDataStructureByUrn(urn);
        dsdProcessorResult.setDataStructure(dataStructure);
        dsdProcessorResult.setDimensions(DsdProcessor.getDimensions(dataStructure));
        dsdProcessorResult.setAttributes(DsdProcessor.getAttributes(dataStructure));
        dsdProcessorResult.setGroups(DsdProcessor.getGroups(dataStructure));
        return dsdProcessorResult;
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
        target.setIsRequiredBy(toResources(source.getIsRequiredBy(), selectedLanguages));
        target.setHasPart(toResources(source.getHasPart(), selectedLanguages));
        target.setIsPartOf(toResources(source.getIsPartOf(), selectedLanguages));

        toCommonMetadata(source, target, selectedLanguages);

        target.setCopyrightDate(source.getCopyrightedDate());
        target.setAccessRights(toInternationalString(source.getAccessRights(), selectedLanguages));

        // Lifecycle
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

    @Override
    public Data toData(DatasetVersion source, DsdProcessorResult dsdProcessorResult, Map<String, List<String>> dimensionValuesSelected, List<String> selectedLanguages) throws Exception {
        if (source == null) {
            return null;
        }
        Data target = new Data();

        // Filter codes
        List<String> datasetDimensions = datasetService.retrieveDatasetVersionDimensionsIds(SERVICE_CONTEXT, source.getSiemacMetadataStatisticalResource().getUrn());
        Map<String, List<String>> dimensionsCodesSelectedEffective = buildDimensionsSelectedWithValues(source, dimensionValuesSelected, datasetDimensions);

        // Transform data
        target.setDimensions(toDataDimensionRepresentations(datasetDimensions, dimensionsCodesSelectedEffective));
        target.setObservations(toDataObservations(source, datasetDimensions, dimensionValuesSelected, dimensionsCodesSelectedEffective));
        target.setAttributes(toDataAttributes(dsdProcessorResult, source.getDatasetRepositoryId(), datasetDimensions, dimensionsCodesSelectedEffective));
        return target;
    }

    @Override
    public DataStructureDefinition toDataStructureDefinition(ExternalItem source, DataStructure dataStructure, List<String> selectedLanguages) {
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

    /**
     * @param effectiveDimensionValuesToDataByDimension It is necessary when query is retrieved, to filter dimension values. It can be null; in this case, returns all
     */
    @Override
    public Dimensions toDimensions(String datasetVersionUrn, DsdProcessorResult dsdProcessorResult, Map<String, List<String>> effectiveDimensionValuesToDataByDimension, List<String> selectedLanguages)
            throws MetamacException {

        List<DsdDimension> sources = dsdProcessorResult.getDimensions();
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        List<String> dimensionsId = datasetService.retrieveDatasetVersionDimensionsIds(SERVICE_CONTEXT, datasetVersionUrn);
        if (CollectionUtils.isEmpty(dimensionsId)) {
            return null;
        }

        // Visualisations
        DataStructure dataStructure = dsdProcessorResult.getDataStructure();
        Map<String, DimensionVisualisation> dimensionVisualisationByDimensionId = null;
        if (dataStructure.getDimensionVisualisations() != null && !CollectionUtils.isEmpty(dataStructure.getDimensionVisualisations().getDimensionVisualisations())) {
            dimensionVisualisationByDimensionId = new HashMap<String, DimensionVisualisation>(dataStructure.getDimensionVisualisations().getDimensionVisualisations().size());
            for (DimensionVisualisation dimensionVisualisation : dataStructure.getDimensionVisualisations().getDimensionVisualisations()) {
                dimensionVisualisationByDimensionId.put(dimensionVisualisation.getDimension(), dimensionVisualisation);
            }
        }

        // Transform dimensions
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

    /**
     * @param effectiveDimensionValuesToDataByDimension It is necessary when query is retrieved, to filter dimension values. It can be null; in this case, returns all
     */
    @Override
    public Attributes toAttributes(String datasetVersionUrn, DsdProcessorResult dsdProcessorResult, Map<String, List<String>> effectiveDimensionValuesToDataByDimension, List<String> selectedLanguages)
            throws MetamacException {

        List<DsdAttribute> sources = dsdProcessorResult.getAttributes();
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }

        List<String> datasetDimensionsOrdered = datasetService.retrieveDatasetVersionDimensionsIds(SERVICE_CONTEXT, datasetVersionUrn);

        Attributes targets = new Attributes();
        for (DsdAttribute source : sources) {
            // TODO effectiveDimensionValuesToDataByDimension
            Attribute target = toAttribute(source, dsdProcessorResult, datasetDimensionsOrdered, selectedLanguages);
            targets.getAttributes().add(target);
        }
        targets.setTotal(BigInteger.valueOf(targets.getAttributes().size()));
        return targets;
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
    public Resources toResourcesExternalItemsSrm(Collection<ExternalItem> sources, List<String> selectedLanguages) {
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
    public InternationalString toInternationalString(org.siemac.metamac.statistical.resources.core.common.domain.InternationalString sources, List<String> selectedLanguages) {
        if (sources == null) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (org.siemac.metamac.statistical.resources.core.common.domain.LocalisedString source : sources.getTexts()) {
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
    public void toResource(Resource source, Resource target, List<String> selectedLanguages) {
        if (source == null) {
            return;
        }
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setKind(source.getKind());
        target.setName(toInternationalString(source.getName(), selectedLanguages));
        target.setNestedId(source.getNestedId());
        target.setSelfLink(source.getSelfLink());
    }

    @Override
    public Resource toResource(Resource source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        Resource target = new Resource();
        toResource(source, target, selectedLanguages);
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

    private Dimension toDimension(String datasetVersionUrn, DataStructure dataStructure, DsdDimension source, DimensionVisualisation dimensionVisualisation,
            List<String> effectiveDimensionValuesToData, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        Dimension target = new Dimension();
        target.setId(source.getComponentId());
        target.setType(toDimensionType(source.getType()));
        target.setName(toInternationalString(source.getConceptIdentity().getName(), selectedLanguages));
        if (source.getCodelistRepresentationUrn() != null) {
            Codelist codelistRepresentation = srmRestExternalFacade.retrieveCodelistByUrn(source.getCodelistRepresentationUrn());
            target.setVariable(toResource(codelistRepresentation.getVariable(), selectedLanguages));
        }

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
        } else if (dimension.getTextFormatRepresentation() != null) {
            targets = toNonEnumeratedDimensionValuesFromTextFormatType(coverages, dimension.getTextFormatRepresentation(), effectiveDimensionValuesToData, selectedLanguages);
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
        for (CodeResourceInternal code : codes.getCodes()) {
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

    private Attribute toAttribute(DsdAttribute source, DsdProcessorResult dsdProcessorResult, List<String> datasetDimensionsOrdered, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        Attribute target = new Attribute();
        target.setId(source.getComponentId());
        target.setName(toInternationalString(source.getConceptIdentity().getName(), selectedLanguages));

        if (source.getAttributeRelationship().getNone() != null) {
            target.setAttachmentLevel(AttributeAttachmentLevelType.DATASET);
        } else if (!CollectionUtils.isEmpty(source.getAttributeRelationship().getDimensions())) {
            target.setAttachmentLevel(AttributeAttachmentLevelType.DIMENSION);
            List<String> attributeDimensions = source.getAttributeRelationship().getDimensions();
            target.setDimensions(toAttributeDimensions(attributeDimensions, datasetDimensionsOrdered));
        } else if (source.getAttributeRelationship().getGroup() != null) {
            target.setAttachmentLevel(AttributeAttachmentLevelType.DIMENSION);
            List<String> attributeDimensions = dsdProcessorResult.getGroups().get(source.getAttributeRelationship().getGroup());
            target.setDimensions(toAttributeDimensions(attributeDimensions, datasetDimensionsOrdered));
        } else if (source.getAttributeRelationship().getPrimaryMeasure() != null) {
            target.setAttachmentLevel(AttributeAttachmentLevelType.PRIMARY_MEASURE);
        } else {
            logger.error("AttributeRelationship unsupported to attributeId " + source.getComponentId());
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
        return target;
    }

    private AttributeDimensions toAttributeDimensions(List<String> attributeDimensions, List<String> datasetDimensionsOrdered) throws MetamacException {
        if (CollectionUtils.isEmpty(attributeDimensions)) {
            return null;
        }
        List<String> attributeDimensionsOrdered = toAttributeDimensionsOrdered(datasetDimensionsOrdered, attributeDimensions);
        AttributeDimensions targets = new AttributeDimensions();
        for (String attributeDimensionOrdered : attributeDimensionsOrdered) {
            AttributeDimension target = new AttributeDimension();
            target.setDimensionId(attributeDimensionOrdered);
            targets.getDimensions().add(target);
            // TODO toAttributeValues
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensions().size()));
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

    private EnumeratedDimensionValues toEnumeratedDimensionValuesFromConceptScheme(Map<String, CodeDimension> coveragesById, DataStructure dataStructure, DsdComponentType dimensionType,
            String conceptSchemeUrn, List<String> effectiveDimensionValuesToData, List<String> selectedLanguages) throws MetamacException {
        if (conceptSchemeUrn == null) {
            return null;
        }
        EnumeratedDimensionValues targets = new EnumeratedDimensionValues();

        Map<String, Integer> showDecimalPrecisionsByUrn = null;
        if (DsdComponentType.MEASURE.equals(dimensionType)) {
            if (dataStructure.getShowDecimalsPrecisions() != null && !CollectionUtils.isEmpty(dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions())) {
                showDecimalPrecisionsByUrn = new HashMap<String, Integer>(dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions().size());
                for (ShowDecimalPrecision showDecimalPrecision : dataStructure.getShowDecimalsPrecisions().getShowDecimalPrecisions()) {
                    showDecimalPrecisionsByUrn.put(showDecimalPrecision.getConcept().getUrn(), showDecimalPrecision.getShowDecimals());
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
            targets.getValues().add(toEnumeratedDimensionValue(concept, showDecimalPrecisionsByUrn, parentsReplacedToVisualisation, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private NonEnumeratedDimensionValues toNonEnumeratedDimensionValuesFromTextFormatType(List<CodeDimension> coverages, TextFormat timeTextFormatType, List<String> effectiveDimensionValuesToData,
            List<String> selectedLanguages) throws MetamacException {
        if (timeTextFormatType == null) {
            return null;
        }
        // note: timeTextFormatType definition is not necessary to define dimension values
        NonEnumeratedDimensionValues targets = new NonEnumeratedDimensionValues();
        for (CodeDimension coverage : coverages) {
            if (effectiveDimensionValuesToData != null && !effectiveDimensionValuesToData.contains(coverage.getIdentifier())) {
                // skip to include only values in query
                continue;
            }
            targets.getValues().add(toNonEnumeratedDimensionValue(coverage, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private EnumeratedDimensionValue toEnumeratedDimensionValue(CodeResourceInternal source, Map<String, String> parentsReplacedToVisualisation, List<String> selectedLanguages)
            throws MetamacException {
        if (source == null) {
            return null;
        }
        EnumeratedDimensionValue target = new EnumeratedDimensionValue();
        toResource(source, target, selectedLanguages);
        if (source.getParent() != null) {
            if (!parentsReplacedToVisualisation.containsKey(source.getParent())) {
                target.setVisualisationParent(source.getParent());
            } else {
                target.setVisualisationParent(parentsReplacedToVisualisation.get(source.getParent()));
            }
        }
        target.setVariableElement(toResource(source.getVariableElement(), selectedLanguages));
        target.setOpen(source.isOpen());
        return target;
    }

    private EnumeratedDimensionValue toEnumeratedDimensionValue(ItemResourceInternal source, Map<String, Integer> showDecimalPrecisionsByUrn, Map<String, String> effectiveParentVisualisation,
            List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        EnumeratedDimensionValue target = new EnumeratedDimensionValue();
        toResource(source, target, selectedLanguages);
        if (source.getParent() != null) {
            if (!effectiveParentVisualisation.containsKey(source.getParent())) {
                target.setVisualisationParent(source.getParent());
            } else {
                target.setVisualisationParent(effectiveParentVisualisation.get(source.getParent()));
            }
        }
        if (showDecimalPrecisionsByUrn != null) {
            target.setShowDecimalsPrecision(showDecimalPrecisionsByUrn.get(source.getUrn()));
        }
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

    private DimensionRepresentations toDataDimensionRepresentations(List<String> datasetDimensions, Map<String, List<String>> dimensionsCodesSelectedEffective) {
        DimensionRepresentations targets = new DimensionRepresentations();
        for (String dimension : datasetDimensions) {
            DimensionRepresentation representation = new DimensionRepresentation();
            representation.setDimensionId(dimension);
            representation.setRepresentations(new CodeRepresentations());

            int valuesSize = 0;
            for (String dimensionValue : dimensionsCodesSelectedEffective.get(dimension)) {
                CodeRepresentation codeRepresentation = new CodeRepresentation();
                codeRepresentation.setCode(dimensionValue);
                codeRepresentation.setIndex(valuesSize);
                representation.getRepresentations().getRepresentations().add(codeRepresentation);
                valuesSize++;
            }
            representation.getRepresentations().setTotal(BigInteger.valueOf(representation.getRepresentations().getRepresentations().size()));
            targets.getDimensions().add(representation);
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensions().size()));
        return targets;
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
        Map<String, ObservationExtendedDto> observations = datasetRepositoriesServiceFacade.findObservationsExtendedByDimensions(source.getDatasetRepositoryId(), conditions);

        // Build data
        DataValueExtraction dataValueExtraction = new DataValueExtractionForObservation(observations);
        return toDataCommon(dimensions, dimensionsCodesSelectedEffective, dataValueExtraction);
    }

    private DataAttributes toDataAttributes(DsdProcessorResult dsdProcessorResult, String datasetRepositoryId, List<String> datasetDimensionsOrdered,
            Map<String, List<String>> dimensionsCodesSelectedEffective) throws Exception {

        List<DsdAttribute> sources = dsdProcessorResult.getAttributes();
        if (CollectionUtils.isEmpty(sources) || MapUtils.isEmpty(dimensionsCodesSelectedEffective)) {
            return null;
        }

        DataAttributes targets = new DataAttributes();
        for (DsdAttribute source : sources) {
            String attributeId = source.getComponentId();

            String value = null;
            if (source.getAttributeRelationship().getNone() != null) {
                value = toDataAttributeForDatasetAttachmentLevel(datasetRepositoryId, attributeId);
            } else if (!CollectionUtils.isEmpty(source.getAttributeRelationship().getDimensions())) {
                List<String> attributeDimensions = source.getAttributeRelationship().getDimensions();
                value = toDataAttributeForDimensionAttachmentLevel(attributeId, attributeDimensions, datasetDimensionsOrdered, dimensionsCodesSelectedEffective, datasetRepositoryId);
            } else if (source.getAttributeRelationship().getGroup() != null) {
                List<String> attributeDimensions = dsdProcessorResult.getGroups().get(source.getAttributeRelationship().getGroup());
                value = toDataAttributeForDimensionAttachmentLevel(attributeId, attributeDimensions, datasetDimensionsOrdered, dimensionsCodesSelectedEffective, datasetRepositoryId);
            } else if (source.getAttributeRelationship().getPrimaryMeasure() != null) {
                // These attributes are retrieved with observations
            } else {
                logger.error("AttributeRelationship unsupported to attributeId " + source.getComponentId());
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
            }
            if (value != null) {
                DataAttribute target = new DataAttribute();
                target.setId(attributeId);
                target.setValue(value);
                targets.getAttributes().add(target);
            }
        }
        targets.setTotal(BigInteger.valueOf(targets.getAttributes().size()));
        return targets;
    }

    private String toDataAttributeForDatasetAttachmentLevel(String datasetId, String attributeId) throws Exception {

        // Find attributes
        List<AttributeDto> sources = datasetRepositoriesServiceFacade.findAttributesWithDatasetAttachmentLevel(datasetId, attributeId);
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        AttributeDto source = sources.get(0); // must be only one
        return getAttributeValue(source);
    }

    private String toDataAttributeForDimensionAttachmentLevel(String attributeId, List<String> attributeDimensions, List<String> datasetDimensionsOrdered,
            Map<String, List<String>> dimensionsCodesSelectedEffective, String datasetId) throws Exception {

        // Find attributes
        List<AttributeDto> sources = datasetRepositoriesServiceFacade.findAttributesWithDimensionAttachmentLevelDenormalized(datasetId, attributeId, dimensionsCodesSelectedEffective);
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }

        // Build data
        List<String> attributeDimensionsOrdered = toAttributeDimensionsOrdered(datasetDimensionsOrdered, attributeDimensions);
        Map<String, AttributeDto> attributesByCodeDimensions = buildMapToAttributesWithDimensionAttachmentLevelDenormalizedByCodeDimensions(attributeDimensionsOrdered, sources);

        DataValueExtraction dataValueExtraction = new DataValueExtractionForAttributeWithDimensionAttachmentLevel(attributesByCodeDimensions);
        return toDataCommon(attributeDimensionsOrdered, dimensionsCodesSelectedEffective, dataValueExtraction);
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

    private DimensionsId toDimensionsId(org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DimensionReferences sources) {
        if (sources == null) {
            return null;
        }
        DimensionsId targets = new DimensionsId();
        for (String source : sources.getDimensions()) {
            targets.getDimensionIds().add(source);
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensionIds().size()));
        return targets;
    }

    private void toCommonMetadata(SiemacMetadataStatisticalResource source, StatisticalResource target, List<String> selectedLanguages) {
        if (source == null) {
            return;
        }
        if (source.getCommonMetadata() == null) {
            return;
        }
        String configurationId = source.getCommonMetadata().getCode();
        Configuration configuration = commonMetadataRestExternalFacade.retrieveConfiguration(configurationId);
        target.setRightsHolder(toResource(configuration.getContact(), selectedLanguages));
        target.setLicense(toInternationalString(configuration.getLicense(), selectedLanguages));
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

    private List<String> toAttributeDimensionsOrdered(List<String> datasetDimensionsOrdered, List<String> attributeDimensions) {
        List<String> attributeDimensionsOrdered = new ArrayList<String>(attributeDimensions.size());
        for (String datasetDimension : datasetDimensionsOrdered) {
            if (attributeDimensions.contains(datasetDimension)) {
                attributeDimensionsOrdered.add(datasetDimension);
            }
        }
        return attributeDimensionsOrdered;
    }

    private String getAttributeValue(AttributeDto attributeDto) {
        return attributeDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE); // all attributes has only one locale
    }

    private Map<String, AttributeDto> buildMapToAttributesWithDimensionAttachmentLevelDenormalizedByCodeDimensions(List<String> attributeDimensionsOrdered, List<AttributeDto> attributeInstances) {
        Map<String, AttributeDto> attributesByCodeDimensions = new HashMap<String, AttributeDto>(attributeInstances.size());
        for (AttributeDto attributeDto : attributeInstances) {
            StringBuilder key = new StringBuilder();
            for (int i = 0; i < attributeDimensionsOrdered.size(); i++) {
                String dimensionId = attributeDimensionsOrdered.get(i);
                String codeDimension = attributeDto.getCodesByDimension().get(dimensionId).get(0); // only to denormalized!!
                key.append(codeDimension);
                if (i != attributeDimensionsOrdered.size() - 1) {
                    key.append(KEY_DIMENSIONS_SEPARATOR);
                }
            }
            attributesByCodeDimensions.put(key.toString(), attributeDto);
        }
        return attributesByCodeDimensions;
    }

    private String toDataCommon(List<String> dimensions, Map<String, List<String>> dimensionsCodesSelectedEffective, DataValueExtraction dataValueExtraction) throws Exception {

        // Data size
        int dataSize = 1;
        for (String dimension : dimensions) {
            dataSize = dataSize * dimensionsCodesSelectedEffective.get(dimension).size();
        }

        // Build data
        List<String> dataResult = new ArrayList<String>(dataSize);
        Stack<OrderingStackElement> stack = new Stack<OrderingStackElement>();
        stack.push(new OrderingStackElement(StringUtils.EMPTY, -1));
        ArrayList<String> entryId = new ArrayList<String>(dimensions.size());
        for (int i = 0; i < dimensions.size(); i++) {
            entryId.add(i, StringUtils.EMPTY);
        }

        int lastDimension = dimensions.size() - 1;
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
                String id = StringUtils.join(entryId, KEY_DIMENSIONS_SEPARATOR);

                // We have the full entry here
                String value = dataValueExtraction.getValue(id);
                dataResult.add(value); // value can be null
                entryId.set(elemDimension, StringUtils.EMPTY);
            } else {
                String dimension = dimensions.get(elemDimension + 1);
                List<String> dimensionValues = dimensionsCodesSelectedEffective.get(dimension);
                for (int i = dimensionValues.size() - 1; i >= 0; i--) {
                    OrderingStackElement temp = new OrderingStackElement(dimensionValues.get(i), elemDimension + 1);
                    stack.push(temp);
                }
            }
        }
        return StringUtils.join(dataResult.iterator(), RestExternalConstants.DATA_SEPARATOR);
    }

    private interface DataValueExtraction {

        public String getValue(String key);
    }

    private class DataValueExtractionForAttributeWithDimensionAttachmentLevel implements DataValueExtraction {

        private final Map<String, AttributeDto> attributesByCodeDimensions;

        public DataValueExtractionForAttributeWithDimensionAttachmentLevel(Map<String, AttributeDto> attributesByCodeDimensions) {
            this.attributesByCodeDimensions = attributesByCodeDimensions;
        }

        @Override
        public String getValue(String key) {
            AttributeDto attributeDto = attributesByCodeDimensions.get(key);
            if (attributeDto == null) {
                return null;
            }
            return getAttributeValue(attributeDto);
        }
    }

    private class DataValueExtractionForObservation implements DataValueExtraction {

        private final Map<String, ObservationExtendedDto> observations;

        public DataValueExtractionForObservation(Map<String, ObservationExtendedDto> observations) {
            this.observations = observations;
        }

        @Override
        public String getValue(String key) {
            ObservationDto observationDto = observations.get(key);
            if (observationDto == null) {
                return null;
            }
            return observationDto.getPrimaryMeasure();
        }
    }

    private List<String> calculateEffectiveDimensionValuesToQuery(QueryVersion source, QuerySelectionItem selection) {
        DatasetVersion datasetVersion = source.getDatasetVersion();
        QueryTypeEnum type = source.getType();
        String dimensionId = selection.getDimension();
        List<String> selectionCodes = codeItemToString(selection.getCodes());

        if (QueryTypeEnum.FIXED.equals(type)) {
            // return exactly
            return selectionCodes;
        } else if (QueryTypeEnum.AUTOINCREMENTAL.equals(type)) {
            if (isTemporalDimension(dimensionId)) {
                List<String> effectiveDimensionValues = new ArrayList<String>();
                List<String> temporalCoverageCodes = temporalCoverageToString(datasetVersion.getTemporalCoverage());
                int indexLatestTemporalCodeInCreation = temporalCoverageCodes.indexOf(source.getLatestTemporalCodeInCreation());
                if (indexLatestTemporalCodeInCreation != 0) {
                    // add codes added after query creation
                    List<TemporalCode> temporalCodesAddedAfterQueryCreation = datasetVersion.getTemporalCoverage().subList(0, indexLatestTemporalCodeInCreation);
                    List<String> temporalCodesAddedAfterQueryCreationString = temporalCoverageToString(temporalCodesAddedAfterQueryCreation);
                    for (String code : temporalCodesAddedAfterQueryCreationString) {
                        effectiveDimensionValues.add(code);
                    }
                }
                effectiveDimensionValues.addAll(selectionCodes);
                return effectiveDimensionValues;
            } else {
                // return exactly
                return selectionCodes;
            }
        } else if (QueryTypeEnum.LATEST_DATA.equals(type)) {
            if (isTemporalDimension(dimensionId)) {
                // return N data
                int codeLastIndexToReturn = -1;
                if (datasetVersion.getTemporalCoverage().size() < source.getLatestDataNumber()) {
                    codeLastIndexToReturn = datasetVersion.getTemporalCoverage().size(); // there is not N data, so return all
                } else {
                    codeLastIndexToReturn = source.getLatestDataNumber();
                }
                List<TemporalCode> temporalCodesLatestDataNumber = datasetVersion.getTemporalCoverage().subList(0, codeLastIndexToReturn);
                return temporalCoverageToString(temporalCodesLatestDataNumber);
            } else {
                // return exactly
                return selectionCodes;
            }
        } else {
            logger.error("QueryTypeEnum unsupported: " + source);
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }
}