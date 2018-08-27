package org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.base;

import static org.siemac.metamac.statistical_resources.rest.internal.StatisticalResourcesRestInternalConstants.KEY_DIMENSIONS_SEPARATOR;
import static org.siemac.metamac.statistical_resources.rest.internal.StatisticalResourcesRestInternalConstants.SERVICE_CONTEXT;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.enume.domain.IstacTimeGranularityEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.time.IstacTimeUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.api.constants.RestApiConstants;
import org.siemac.metamac.rest.common.v1_0.domain.InternationalString;
import org.siemac.metamac.rest.common.v1_0.domain.LocalisedString;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.common_metadata.v1_0.domain.Configuration;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Instance;
import org.siemac.metamac.rest.statistical_operations_internal.v1_0.domain.Operation;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.AttributeAttachmentLevelType;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.AttributeDimension;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.AttributeDimensions;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.AttributeValues;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Attributes;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.CodeRepresentation;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.CodeRepresentations;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.ComponentType;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Data;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.DataAttribute;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.DataAttributes;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.DataStructureDefinition;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.DimensionRepresentation;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.DimensionRepresentations;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.DimensionType;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.DimensionValues;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.DimensionsId;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.EnumeratedAttributeValue;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.EnumeratedAttributeValues;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.EnumeratedDimensionValue;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.EnumeratedDimensionValues;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.MeasureQuantity;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.NextVersionType;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.NonEnumeratedAttributeValue;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.NonEnumeratedAttributeValues;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.NonEnumeratedDimensionValue;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.NonEnumeratedDimensionValues;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.ProcStatusType;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.ResourcesInternal;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.SelectedLanguages;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.StatisticalResource;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.StatisticalResourceType;
import org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleTypes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DimensionVisualisation;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Quantity;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ShowDecimalPrecision;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.TextFormat;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.VariableElementResourceInternal;
import org.siemac.metamac.rest.utils.RestCommonUtil;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.srm.rest.common.SrmRestConstants;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleType;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.common.serviceapi.TranslationService;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponentType;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValue;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.dataset.serviceapi.DatasetService;
import org.siemac.metamac.statistical.resources.core.dataset.utils.DatasetVersionUtils;
import org.siemac.metamac.statistical.resources.core.enume.domain.NextVersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.VersionRationaleTypeEnum;
import org.siemac.metamac.statistical.resources.core.invocation.utils.InternalWebApplicationNavigation;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.serviceapi.QueryService;
import org.siemac.metamac.statistical_resources.rest.internal.StatisticalResourcesRestInternalConstants;
import org.siemac.metamac.statistical_resources.rest.internal.exception.RestServiceExceptionType;
import org.siemac.metamac.statistical_resources.rest.internal.invocation.CommonMetadataRestExternalFacade;
import org.siemac.metamac.statistical_resources.rest.internal.invocation.SrmRestInternalFacade;
import org.siemac.metamac.statistical_resources.rest.internal.invocation.StatisticalOperationsRestInternalFacade;
import org.siemac.metamac.statistical_resources.rest.internal.service.utils.LookupUtil;
import org.siemac.metamac.statistical_resources.rest.internal.service.utils.StatisticalResourcesRestInternalUtils;
import org.siemac.metamac.statistical_resources.rest.internal.v1_0.domain.DsdProcessorResult;
import org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.collection.CollectionsDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.dataset.DatasetsDo2RestMapperV10;
import org.siemac.metamac.statistical_resources.rest.internal.v1_0.mapper.query.QueriesDo2RestMapperV10;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.gobcan.istac.edatos.dataset.repository.dto.AttributeInstanceBasicDto;
import es.gobcan.istac.edatos.dataset.repository.dto.AttributeInstanceDto;
import es.gobcan.istac.edatos.dataset.repository.dto.AttributeInstanceObservationDto;
import es.gobcan.istac.edatos.dataset.repository.dto.ConditionDimensionDto;
import es.gobcan.istac.edatos.dataset.repository.dto.ObservationExtendedDto;
import es.gobcan.istac.edatos.dataset.repository.service.DatasetRepositoriesServiceFacade;

@Component
public class CommonDo2RestMapperV10Impl implements CommonDo2RestMapperV10 {

    private static final Logger                     logger             = LoggerFactory.getLogger(CommonDo2RestMapperV10.class);

    private String                                  INCLUDE_ALL_FIELDS = SrmRestConstants.FIELD_INCLUDE_OPENNES + RestApiConstants.COMMA + SrmRestConstants.FIELD_INCLUDE_ORDER + RestApiConstants.COMMA
            + SrmRestConstants.FIELD_INCLUDE_VARIABLE_ELEMENT;

    @Autowired
    private StatisticalResourcesConfiguration       configurationService;

    @Autowired
    private StatisticalOperationsRestInternalFacade statisticalOperationsRestInternalFacade;

    @Autowired
    private DatasetService                          datasetService;

    @Autowired
    private QueryService                            queryService;

    @Autowired
    private TranslationService                      translationService;

    @Autowired
    private DatasetRepositoriesServiceFacade        datasetRepositoriesServiceFacade;

    @Autowired
    private SrmRestInternalFacade                   srmRestExternalFacade;

    @Autowired
    private CommonMetadataRestExternalFacade        commonMetadataRestExternalFacade;

    @Autowired
    private DatasetsDo2RestMapperV10                datasetsDo2RestMapper;

    @Autowired
    private CollectionsDo2RestMapperV10             collectionsDo2RestMapper;

    @Autowired
    private QueriesDo2RestMapperV10                 queriesDo2RestMapper;

    private String                                  statisticalResourcesApiInternalEndpointV10;
    private String                                  srmApiInternalEndpoint;
    private String                                  statisticalOperationsApiInternalEndpoint;
    private String                                  statisticalResourcesInternalWebApplication;
    private String                                  srmInternalWebApplication;
    private String                                  commonMetadataInternalWebApplication;
    private String                                  statisticalOperationsWebApplication;
    private String                                  defaultLanguage;

    private InternalWebApplicationNavigation        internalWebApplicationNavigation;

    @PostConstruct
    public void init() throws Exception {

        // APPLICATIONS
        statisticalResourcesInternalWebApplication = configurationService.retrieveStatisticalResourcesInternalWebApplicationUrlBase();
        srmInternalWebApplication = configurationService.retrieveSrmInternalWebApplicationUrlBase();
        commonMetadataInternalWebApplication = configurationService.retrieveCommonMetadataInternalWebApplicationUrlBase();
        statisticalOperationsWebApplication = configurationService.retrieveStatisticalOperationsInternalWebApplicationUrlBase();

        // ENDPOINTS
        // Statistical resources internal Api V1.0
        String statisticalResourcesApiInternalEndpoint = configurationService.retrieveStatisticalResourcesInternalApiUrlBase();
        statisticalResourcesApiInternalEndpointV10 = RestUtils.createLink(statisticalResourcesApiInternalEndpoint, StatisticalResourcesRestInternalConstants.API_VERSION_1_0);

        // SRM external Api (do not add api version! it is already stored in database (~latest))
        srmApiInternalEndpoint = configurationService.retrieveSrmInternalApiUrlBase();
        srmApiInternalEndpoint = StringUtils.removeEnd(srmApiInternalEndpoint, "/");

        // Statistical operations external Api (do not add api version! it is already stored in database (~latest))
        statisticalOperationsApiInternalEndpoint = configurationService.retrieveStatisticalOperationsInternalApiUrlBase();
        statisticalOperationsApiInternalEndpoint = StringUtils.removeEnd(statisticalOperationsApiInternalEndpoint, "/");

        // MISC
        defaultLanguage = configurationService.retrieveLanguageDefault();

        internalWebApplicationNavigation = new InternalWebApplicationNavigation(statisticalResourcesInternalWebApplication);
    }

    @Override
    public InternalWebApplicationNavigation getInternalWebApplicationNavigation() {
        return internalWebApplicationNavigation;
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
        target.setConformsToInternal(toInternationalString(source.getConformsToInternal(), selectedLanguages));
        // note: hasPart, isPartOf: in concrete mappers

        toCommonMetadata(source, target, selectedLanguages);

        target.setCopyrightDate(source.getCopyrightedDate());
        target.setAccessRights(toInternationalString(source.getAccessRights(), selectedLanguages));

        // Lifecycle
        // note: replacesVersion and isReplacedByVersion are only valid to datasets. So, they are mapped in Dataset mapper
        target.setMaintainer(toResourceExternalItemSrm(source.getMaintainer(), selectedLanguages));
        target.setProcStatus(toProcStatusType(source.getProcStatus(), selectedLanguages));
        target.setCreationDate(toDate(source.getCreationDate()));
        target.setCreationUser(source.getCreationUser());
        target.setProductionValidationDate(toDate(source.getCreationDate()));
        target.setProductionValidationUser(source.getPublicationUser());
        target.setDiffusionValidationDate(toDate(source.getDiffusionValidationDate()));
        target.setDiffusionValidationUser(source.getDiffusionValidationUser());
        target.setRejectValidationDate(toDate(source.getRejectValidationDate()));
        target.setRejectValidationUser(source.getRejectValidationUser());
        target.setPublicationDate(toDate(source.getPublicationDate()));
        target.setPublicationUser(source.getPublicationUser());

        // Versionable
        target.setVersion(source.getVersionLogic());
        target.setVersionRationaleTypes(toVersionRationaleTypes(source.getVersionRationaleTypes(), selectedLanguages));
        target.setVersionRationale(toInternationalString(source.getVersionRationale(), selectedLanguages));
        target.setValidFrom(toDate(source.getValidFrom()));
        target.setValidTo(toDate(StatisticalResourcesRestInternalUtils.isDateAfterNowSetNull(source.getValidTo())));
        target.setNextVersion(toNextVersionType(source.getNextVersion(), selectedLanguages));
        target.setNextVersionDate(toDate(source.getNextVersionDate()));
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
        // Dimensions
        toDataDimensionRepresentations(datasetDimensions, dimensionsCodesSelectedEffective, target);
        // Observations and attributes
        target.setAttributes(new DataAttributes());
        toDataAttributesWithDatasetAndDimensionAttachmenteLevel(dsdProcessorResult, source.getDatasetRepositoryId(), datasetDimensions, dimensionsCodesSelectedEffective, target.getAttributes());
        toDataObservationsAndAttributeWithObservationAttachmentLevel(source, dsdProcessorResult, datasetDimensions, dimensionValuesSelected, dimensionsCodesSelectedEffective, target);
        if (CollectionUtils.isEmpty(target.getAttributes().getAttributes())) {
            target.setAttributes(null);
        } else {
            target.getAttributes().setTotal(BigInteger.valueOf(target.getAttributes().getAttributes().size()));
        }
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

        List<String> dimensionsId = new LinkedList<>();
        for (DsdDimension dsdDimension : dsdProcessorResult.getDimensions()) {
            dimensionsId.add(dsdDimension.getComponentId());
        }

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

    @Override
    public Attributes toAttributes(String datasetVersionUrn, DsdProcessorResult dsdProcessorResult, List<String> selectedLanguages) throws MetamacException {

        List<DsdAttribute> sources = dsdProcessorResult.getAttributes();
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }

        List<String> datasetDimensionsOrdered = datasetService.retrieveDatasetVersionDimensionsIds(SERVICE_CONTEXT, datasetVersionUrn);

        Attributes targets = new Attributes();
        for (DsdAttribute source : sources) {
            Attribute target = toAttribute(datasetVersionUrn, source, dsdProcessorResult, datasetDimensionsOrdered, selectedLanguages);
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
    public ResourceInternal toResourceExternalItemStatisticalOperations(ExternalItem source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        toResourceExternalItem(source, statisticalOperationsApiInternalEndpoint, statisticalOperationsWebApplication, target, selectedLanguages);
        updateNameForNonVersionableResource(target, source);
        return target;
    }

    private void updateNameForNonVersionableResource(Resource target, ExternalItem source) {
        switch (source.getType()) {
            case STATISTICAL_OPERATION:
                target.setName(getUpdatedStatisticalOperationName(source.getCode()));
                break;
            case STATISTICAL_OPERATION_INSTANCE:
                String identifier = UrnUtils.removePrefix(source.getUrn());
                String[] parts = UrnUtils.splitUrnByDots(identifier);
                target.setName(getUpdatedStatisticalOperationInstanceName(parts[0], parts[1]));
                break;
            default:
                break;
        }
    }

    @Override
    public ResourcesInternal toResourcesExternalItemsStatisticalOperations(List<ExternalItem> sources, List<String> selectedLanguages) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        ResourcesInternal targets = new ResourcesInternal();
        for (ExternalItem source : sources) {
            ResourceInternal target = toResourceExternalItemStatisticalOperations(source, selectedLanguages);
            targets.getResources().add(target);
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    @Override
    public ResourceInternal toResourceExternalItemSrm(ExternalItem source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        toResourceExternalItemSrm(source, target, selectedLanguages);
        return target;
    }

    @Override
    public void toResourceExternalItemSrm(ExternalItem source, ResourceInternal target, List<String> selectedLanguages) {
        if (source == null) {
            return;
        }
        toResourceExternalItem(source, srmApiInternalEndpoint, srmInternalWebApplication, target, selectedLanguages);
    }

    @Override
    public ResourcesInternal toResourcesExternalItemsSrm(Collection<ExternalItem> sources, List<String> selectedLanguages) {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        ResourcesInternal targets = new ResourcesInternal();
        for (ExternalItem source : sources) {
            ResourceInternal target = toResourceExternalItemSrm(source, selectedLanguages);
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
    public InternationalString toInternationalString(Map<String, String> sources, List<String> selectedLanguages) {
        if (MapUtils.isEmpty(sources)) {
            return null;
        }
        InternationalString targets = new InternationalString();
        for (String locale : sources.keySet()) {
            if (selectedLanguages.contains(locale)) {
                LocalisedString target = new LocalisedString();
                target.setLang(locale);
                target.setValue(sources.get(locale));
                targets.getTexts().add(target);
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
    public void toResource(ResourceInternal source, ResourceInternal target, List<String> selectedLanguages) {
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

    private void toResource(org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal source, ResourceInternal target, List<String> selectedLanguages) {
        if (source == null) {
            return;
        }
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setKind(source.getKind());
        target.setName(toInternationalString(source.getName(), selectedLanguages));
        target.setNestedId(source.getNestedId());
        target.setSelfLink(source.getSelfLink());
        if (source.getManagementAppLink() != null) {
            target.setManagementAppLink(RestUtils.createLink(srmInternalWebApplication, source.getManagementAppLink()));
        }
        target.setUrnProvider(source.getUrnProvider());
    }

    private void toResource(org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal source, ResourceInternal target, List<String> selectedLanguages) {
        if (source == null) {
            return;
        }
        target.setId(source.getId());
        target.setUrn(source.getUrn());
        target.setKind(source.getKind());
        target.setName(toInternationalString(source.getName(), selectedLanguages));
        target.setNestedId(source.getNestedId());
        target.setSelfLink(source.getSelfLink());
        if (source.getManagementAppLink() != null) {
            target.setManagementAppLink(RestUtils.createLink(commonMetadataInternalWebApplication, source.getManagementAppLink()));
        }
    }

    @Override
    public ResourceInternal toResource(ResourceInternal source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        toResource(source, target, selectedLanguages);
        return target;
    }

    private ResourceInternal toResource(org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }

        ResourceInternal target = new ResourceInternal();
        toResource(source, target, selectedLanguages);
        return target;
    }

    private ResourceInternal toResource(org.siemac.metamac.rest.common_metadata.v1_0.domain.ResourceInternal source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }

        ResourceInternal target = new ResourceInternal();
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
        String link = RestUtils.createLink(statisticalResourcesApiInternalEndpointV10, resourceSubpath);
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

    @Override
    public ResourcesInternal toResources(List<RelatedResource> sources, List<String> selectedLanguages) throws MetamacException {
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        ResourcesInternal targets = new ResourcesInternal();
        for (RelatedResource source : sources) {
            targets.getResources().add(toResource(source, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getResources().size()));
        return targets;
    }

    @Override
    public ResourceInternal toResource(RelatedResource source, List<String> selectedLanguages) throws MetamacException {
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
            case DATASET:
                DatasetVersion datasetVersion = datasetService.retrieveLatestDatasetVersionByDatasetUrn(SERVICE_CONTEXT, source.getDataset().getIdentifiableStatisticalResource().getUrn());
                return datasetsDo2RestMapper.toResourceAsLatest(datasetVersion, selectedLanguages);
            case QUERY:
                QueryVersion queryVersion = queryService.retrieveLatestPublishedQueryVersionByQueryUrn(SERVICE_CONTEXT, source.getQuery().getIdentifiableStatisticalResource().getUrn());
                return queriesDo2RestMapper.toResource(queryVersion, selectedLanguages);
            default:
                logger.error("RelatedResource unsupported: " + source.getType());
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResourceInternal toResource(RelatedResourceResult source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        switch (source.getType()) {
            case DATASET_VERSION:
                return datasetsDo2RestMapper.toResource(source, selectedLanguages);
            case QUERY_VERSION:
                return queriesDo2RestMapper.toResource(source, selectedLanguages);
            case PUBLICATION_VERSION:
                return collectionsDo2RestMapper.toResource(source, selectedLanguages);
            case DATASET:
                DatasetVersion datasetVersion = datasetService.retrieveLatestDatasetVersionByDatasetUrn(SERVICE_CONTEXT, source.getUrn());
                return datasetsDo2RestMapper.toResourceAsLatest(datasetVersion, selectedLanguages);
            case QUERY:
                QueryVersion queryVersion = queryService.retrieveLatestPublishedQueryVersionByQueryUrn(SERVICE_CONTEXT, source.getUrn());
                return queriesDo2RestMapper.toResource(queryVersion, selectedLanguages);
            default:
                logger.error("RelatedResource unsupported: " + source.getType());
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ProcStatusType toProcStatusType(ProcStatusEnum source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }

        ProcStatusType result;
        try {
            result = LookupUtil.lookup(ProcStatusType.class, source.getName());
        } catch (Exception e) {
            logger.error("ProcStatusEnum unsupported: " + source);
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @Override
    public NextVersionType toNextVersionType(NextVersionTypeEnum source, List<String> selectedLanguages) {
        if (source == null) {
            return null;
        }

        NextVersionType result;
        try {
            result = LookupUtil.lookup(NextVersionType.class, source.getName());
        } catch (Exception e) {
            logger.error("NextVersionTypeEnum unsupported: " + source);
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    private void toResourceExternalItem(ExternalItem source, String apiExternalItemBase, String webApplicationExternalItemBase, ResourceInternal target, List<String> selectedLanguages) {
        if (source == null) {
            return;
        }
        target.setId(source.getCode());
        target.setNestedId(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setKind(source.getType().getValue());
        target.setSelfLink(toResourceLink(target.getKind(), RestUtils.createLink(apiExternalItemBase, source.getUri())));
        target.setManagementAppLink(RestUtils.createLink(webApplicationExternalItemBase, source.getManagementAppUrl()));
        target.setName(toInternationalString(source.getTitle(), selectedLanguages));
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
            targets = toEnumeratedDimensionValuesFromCodelist(coveragesById, dimension.getCodelistRepresentationUrn(), dimensionVisualisation, effectiveDimensionValuesToData, selectedLanguages,
                    dimension);
        } else if (dimension.getConceptSchemeRepresentationUrn() != null) {
            targets = toEnumeratedDimensionValuesFromConceptScheme(coveragesById, dataStructure, dimension.getType(), dimension.getConceptSchemeRepresentationUrn(), effectiveDimensionValuesToData,
                    selectedLanguages);
        } else if (dimension.getTextFormatRepresentation() != null) {
            targets = toNonEnumeratedDimensionValuesFromTextFormatType(coverages, dimension.getTextFormatRepresentation(), dimension.getType(), effectiveDimensionValuesToData, selectedLanguages);
        } else {
            logger.error("Dimension definition unsupported for dimension: " + dimension.getComponentId());
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }

        return targets;
    }

    private EnumeratedDimensionValues toEnumeratedDimensionValuesFromCodelist(Map<String, CodeDimension> coveragesById, String codelistUrn, DimensionVisualisation dimensionVisualisation,
            List<String> effectiveDimensionValuesToData, List<String> selectedLanguages, DsdDimension dimension) throws MetamacException {
        if (codelistUrn == null) {
            return null;
        }
        EnumeratedDimensionValues targets = new EnumeratedDimensionValues();

        // This map contains nodes that are not in the result. If a child of this nodes is in the result, we use this map to put it inside the nearest parent node in result
        Map<String, String> parentsReplacedToVisualisation = new HashMap<String, String>();

        String order = dimensionVisualisation != null ? dimensionVisualisation.getOrder() : null;
        String openness = dimensionVisualisation != null ? dimensionVisualisation.getOpenness() : null;
        Codes codes = null;
        if (DsdComponentType.SPATIAL.equals(dimension.getType())) {
            codes = srmRestExternalFacade.retrieveCodesByCodelistUrn(codelistUrn, order, openness, INCLUDE_ALL_FIELDS); // note: srm api returns codes in order
        } else {
            codes = srmRestExternalFacade.retrieveCodesByCodelistUrn(codelistUrn, order, openness,
                    SrmRestConstants.FIELD_INCLUDE_OPENNES + RestApiConstants.COMMA + SrmRestConstants.FIELD_INCLUDE_ORDER); // note: srm api returns codes in order
        }
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
            targets.getValues().add(toEnumeratedDimensionValue(concept, showDecimalPrecisionsByUrn, parentsReplacedToVisualisation, selectedLanguages, dimensionType));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private NonEnumeratedDimensionValues toNonEnumeratedDimensionValuesFromTextFormatType(List<CodeDimension> coverages, TextFormat textFormatType, DsdComponentType dimensionType,
            List<String> effectiveDimensionValuesToData, List<String> selectedLanguages) throws MetamacException {
        if (textFormatType == null) {
            return null;
        }
        NonEnumeratedDimensionValues targets = new NonEnumeratedDimensionValues();

        // Sort
        if (DsdComponentType.TEMPORAL.equals(dimensionType)) {
            DatasetVersionUtils.sortTemporalCodeDimensions(coverages);
        }

        for (CodeDimension coverage : coverages) {
            if (effectiveDimensionValuesToData != null && !effectiveDimensionValuesToData.contains(coverage.getIdentifier())) {
                // skip to include only values in query
                continue;
            }
            targets.getValues().add(toNonEnumeratedDimensionValue(coverage, dimensionType, selectedLanguages));
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
        if (source.getVariableElement() != null) {
            if (source.getVariableElement() instanceof VariableElementResourceInternal) {
                ItemResourceInternal geographicalGranularity = ((VariableElementResourceInternal) source.getVariableElement()).getGeographicalGranularity();
                target.setGeographicGranularity(toResource(geographicalGranularity, selectedLanguages));
            }
        }
        target.setOpen(source.isOpen());
        return target;
    }

    private EnumeratedDimensionValue toEnumeratedDimensionValue(ItemResourceInternal source, Map<String, Integer> showDecimalPrecisionsByUrn, Map<String, String> effectiveParentVisualisation,
            List<String> selectedLanguages, DsdComponentType dimensionType) throws MetamacException {
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

        // Measure Dimension Type
        if (DsdComponentType.MEASURE.equals(dimensionType)) {
            Concept conceptDetail = srmRestExternalFacade.retrieveConceptByUrn(source.getUrn());
            if (conceptDetail.getQuantity() != null) {
                target.setMeasureQuantity(toQuantity(conceptDetail.getQuantity(), selectedLanguages));
            }
        }
        return target;
    }

    private MeasureQuantity toQuantity(Quantity source, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }

        MeasureQuantity target = new MeasureQuantity();

        org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.ItemResourceInternal targetItemResourceInternal = new org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.ItemResourceInternal();
        toResource(source.getUnitCode(), targetItemResourceInternal, selectedLanguages);
        target.setUnitCode(targetItemResourceInternal);

        return target;
    }

    private NonEnumeratedDimensionValue toNonEnumeratedDimensionValue(CodeDimension source, DsdComponentType dimensionType, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        NonEnumeratedDimensionValue target = new NonEnumeratedDimensionValue();
        target.setId(source.getIdentifier());
        if (DsdComponentType.TEMPORAL.equals(dimensionType)) {
            Map<String, String> title = translationService.translateTime(SERVICE_CONTEXT, source.getIdentifier());
            target.setName(toInternationalString(title, selectedLanguages));
            IstacTimeGranularityEnum timeGranularity = IstacTimeUtils.guessTimeGranularity(source.getIdentifier());
            target.setTemporalGranularity(timeGranularity == null ? null : timeGranularity.name());
        } else {
            target.setName(toInternationalString(source.getTitle(), selectedLanguages));
        }
        return target;
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

    private Attribute toAttribute(String datasetVersionUrn, DsdAttribute source, DsdProcessorResult dsdProcessorResult, List<String> datasetDimensionsOrdered, List<String> selectedLanguages)
            throws MetamacException {
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

        // Specific type
        target.setType(ComponentType.valueOf(source.getType().name()));

        // Attributes values
        target.setAttributeValues(toAttributeValues(datasetVersionUrn, source, selectedLanguages));
        return target;
    }

    private AttributeValues toAttributeValues(String datasetVersionUrn, DsdAttribute attribute, List<String> selectedLanguages) throws MetamacException {
        if (attribute == null) {
            return null;
        }
        if (attribute.getEnumeratedRepresentationUrn() == null && !DsdComponentType.TEMPORAL.equals(attribute.getType())) {
            // Translate only representations or time attributes
            return null;
        }
        List<AttributeValue> coverages = datasetService.retrieveCoverageForDatasetVersionAttribute(SERVICE_CONTEXT, datasetVersionUrn, attribute.getComponentId());
        if (CollectionUtils.isEmpty(coverages)) {
            return null;
        }
        Map<String, AttributeValue> coveragesById = new HashMap<String, AttributeValue>(coverages.size());
        for (AttributeValue coverage : coverages) {
            coveragesById.put(coverage.getIdentifier(), coverage);
        }

        AttributeValues targets = null;
        if (attribute.getCodelistRepresentationUrn() != null) {
            targets = toEnumeratedAttributeValuesFromCodelist(coveragesById, attribute.getCodelistRepresentationUrn(), attribute.getType(), selectedLanguages);
        } else if (attribute.getConceptSchemeRepresentationUrn() != null) {
            targets = toEnumeratedAttributeValuesFromConceptScheme(coveragesById, attribute.getConceptSchemeRepresentationUrn(), attribute.getType(), selectedLanguages);
        } else if (DsdComponentType.TEMPORAL.equals(attribute.getType())) {
            targets = toNonEnumeratedAttributeValuesFromTextFormatType(coverages, attribute.getTextFormatRepresentation(), attribute.getType(), selectedLanguages);
        } else {
            logger.error("Attribute definition unsupported for attribute: " + attribute.getComponentId());
            org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
            throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
        return targets;
    }

    private EnumeratedAttributeValues toEnumeratedAttributeValuesFromCodelist(Map<String, AttributeValue> coveragesById, String codelistUrn, DsdComponentType attributeType,
            List<String> selectedLanguages) throws MetamacException {
        if (codelistUrn == null) {
            return null;
        }
        EnumeratedAttributeValues targets = new EnumeratedAttributeValues();
        Codes codes = srmRestExternalFacade.retrieveCodesByCodelistUrn(codelistUrn, null, null, null);
        for (CodeResourceInternal code : codes.getCodes()) {
            String id = code.getId();
            if (!coveragesById.containsKey(id)) {
                // skip to include only values in coverage
                continue;
            }
            targets.getValues().add(toEnumeratedAttributeValue(code, attributeType, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private EnumeratedAttributeValues toEnumeratedAttributeValuesFromConceptScheme(Map<String, AttributeValue> coveragesById, String conceptSchemeUrn, DsdComponentType attributeType,
            List<String> selectedLanguages) throws MetamacException {
        if (conceptSchemeUrn == null) {
            return null;
        }
        EnumeratedAttributeValues targets = new EnumeratedAttributeValues();
        Concepts concepts = srmRestExternalFacade.retrieveConceptsByConceptSchemeByUrn(conceptSchemeUrn);
        for (ItemResourceInternal concept : concepts.getConcepts()) {
            String id = concept.getId();
            if (!coveragesById.containsKey(id)) {
                // skip to include only values in coverage
                continue;
            }
            targets.getValues().add(toEnumeratedAttributeValue(concept, attributeType, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private EnumeratedAttributeValue toEnumeratedAttributeValue(ItemResourceInternal source, DsdComponentType attributeType, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        EnumeratedAttributeValue target = new EnumeratedAttributeValue();
        toResource(source, target, selectedLanguages);

        // Measure Attribute Type
        if (DsdComponentType.MEASURE.equals(attributeType)) {
            Concept conceptDetail = srmRestExternalFacade.retrieveConceptByUrn(source.getUrn());
            if (conceptDetail.getQuantity() != null) {
                target.setMeasureQuantity(toQuantity(conceptDetail.getQuantity(), selectedLanguages));
            }
        }

        return target;
    }

    private NonEnumeratedAttributeValues toNonEnumeratedAttributeValuesFromTextFormatType(List<AttributeValue> coverages, TextFormat textFormatType, DsdComponentType attributeType,
            List<String> selectedLanguages) throws MetamacException {
        if (textFormatType == null) {
            return null;
        }
        NonEnumeratedAttributeValues targets = new NonEnumeratedAttributeValues();
        for (AttributeValue coverage : coverages) {
            targets.getValues().add(toNonEnumeratedAttributeValue(coverage, attributeType, selectedLanguages));
        }
        targets.setTotal(BigInteger.valueOf(targets.getValues().size()));
        return targets;
    }

    private NonEnumeratedAttributeValue toNonEnumeratedAttributeValue(AttributeValue source, DsdComponentType attributeType, List<String> selectedLanguages) throws MetamacException {
        if (source == null) {
            return null;
        }
        NonEnumeratedAttributeValue target = new NonEnumeratedAttributeValue();
        target.setId(source.getIdentifier());
        if (DsdComponentType.TEMPORAL.equals(attributeType)) {
            Map<String, String> title = translationService.translateTime(SERVICE_CONTEXT, source.getIdentifier());
            target.setName(toInternationalString(title, selectedLanguages));
        } else {
            logger.error("Attribute must be time to add values in metadata: " + source.getDsdComponentId());
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
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensions().size()));
        return targets;
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
            case MULTIDATASET:
                return StatisticalResourceType.MULTIDATASET;
            default:
                logger.error("StatisticalResourceTypeEnum unsupported: " + source);
                org.siemac.metamac.rest.common.v1_0.domain.Exception exception = RestExceptionUtils.getException(RestServiceExceptionType.UNKNOWN);
                throw new RestException(exception, Status.INTERNAL_SERVER_ERROR);
        }
    }

    private org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleType toVersionRationaleType(VersionRationaleTypeEnum source) {
        if (source == null) {
            return null;
        }
        switch (source) {
            case MAJOR_NEW_RESOURCE:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleType.MAJOR_NEW_RESOURCE;
            case MAJOR_ESTIMATORS:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleType.MAJOR_ESTIMATORS;
            case MAJOR_CATEGORIES:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleType.MAJOR_CATEGORIES;
            case MAJOR_VARIABLES:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleType.MAJOR_VARIABLES;
            case MAJOR_OTHER:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleType.MAJOR_OTHER;
            case MINOR_ERRATA:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleType.MINOR_ERRATA;
            case MINOR_METADATA:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleType.MINOR_METADATA;
            case MINOR_DATA_UPDATE:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleType.MINOR_DATA_UPDATE;
            case MINOR_SERIES_UPDATE:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleType.MINOR_SERIES_UPDATE;
            case MINOR_OTHER:
                return org.siemac.metamac.rest.statistical_resources_internal.v1_0.domain.VersionRationaleType.MINOR_OTHER;
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

    private void toDataDimensionRepresentations(List<String> datasetDimensions, Map<String, List<String>> dimensionsCodesSelectedEffective, Data target) {
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
        target.setDimensions(targets);
    }

    /**
     * Retrieve observations of selected codes
     */
    private void toDataObservationsAndAttributeWithObservationAttachmentLevel(DatasetVersion source, DsdProcessorResult dsdProcessorResult, List<String> dimensions,
            Map<String, List<String>> dimensionsSelected, Map<String, List<String>> dimensionsCodesSelectedEffective, Data target) throws Exception {

        if (MapUtils.isEmpty(dimensionsCodesSelectedEffective)) {
            return;
        }

        // Search observations and attributes in repository
        List<ConditionDimensionDto> conditions = generateConditions(dimensionsSelected);
        Map<String, ObservationExtendedDto> observations = datasetRepositoriesServiceFacade.findObservationsExtendedByDimensions(source.getDatasetRepositoryId(), conditions);

        // Build data (observations and attribute in observation attachment level)
        int dataSize = calculateDataSize(dimensions, dimensionsCodesSelectedEffective);
        DataProcessorForObservation dataProcessor = new DataProcessorForObservation(observations, dataSize);
        toDataCommon(dimensions, dimensionsCodesSelectedEffective, dataProcessor);

        // Observations
        target.setObservations(dataProcessor.getDataObservationsForResponse());

        // Attributes
        for (DsdAttribute dsdAttribute : dsdProcessorResult.getAttributes()) {
            if (dsdAttribute.getAttributeRelationship().getPrimaryMeasure() != null) {
                String attributeId = dsdAttribute.getComponentId();
                String dataAttributeValue = dataProcessor.getDataAttributeForResponse(attributeId);
                if (dataAttributeValue != null) {
                    DataAttribute dataAttribute = new DataAttribute();
                    dataAttribute.setId(attributeId);
                    dataAttribute.setValue(dataAttributeValue);
                    target.getAttributes().getAttributes().add(dataAttribute);
                }
            }
        }
    }

    private void toDataAttributesWithDatasetAndDimensionAttachmenteLevel(DsdProcessorResult dsdProcessorResult, String datasetRepositoryId, List<String> datasetDimensionsOrdered,
            Map<String, List<String>> dimensionsCodesSelectedEffective, DataAttributes targets) throws Exception {

        List<DsdAttribute> sources = dsdProcessorResult.getAttributes();
        if (CollectionUtils.isEmpty(sources) || MapUtils.isEmpty(dimensionsCodesSelectedEffective)) {
            return;
        }

        for (DsdAttribute source : sources) {
            String attributeId = source.getComponentId();

            String value = null;
            if (source.getAttributeRelationship().getNone() != null) {
                value = toDataAttributeWithDatasetAttachmentLevel(datasetRepositoryId, attributeId);
            } else if (!CollectionUtils.isEmpty(source.getAttributeRelationship().getDimensions())) {
                List<String> attributeDimensions = source.getAttributeRelationship().getDimensions();
                value = toDataAttributeWithDimensionAttachmentLevel(attributeId, attributeDimensions, datasetDimensionsOrdered, dimensionsCodesSelectedEffective, datasetRepositoryId);
            } else if (source.getAttributeRelationship().getGroup() != null) {
                List<String> attributeDimensions = dsdProcessorResult.getGroups().get(source.getAttributeRelationship().getGroup());
                value = toDataAttributeWithDimensionAttachmentLevel(attributeId, attributeDimensions, datasetDimensionsOrdered, dimensionsCodesSelectedEffective, datasetRepositoryId);
            } else if (source.getAttributeRelationship().getPrimaryMeasure() != null) {
                // These attributes are transformed with observations
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
    }

    private String toDataAttributeWithDatasetAttachmentLevel(String datasetId, String attributeId) throws Exception {

        // Find attributes
        List<AttributeInstanceDto> sources = datasetRepositoriesServiceFacade.findAttributesInstancesWithDatasetAttachmentLevel(datasetId, attributeId);
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }
        AttributeInstanceDto source = sources.get(0); // must be only one
        return toAttributeInstanceValueToData(source);
    }

    private String toDataAttributeWithDimensionAttachmentLevel(String attributeId, List<String> attributeDimensions, List<String> datasetDimensionsOrdered,
            Map<String, List<String>> dimensionsCodesSelectedEffective, String datasetId) throws Exception {

        // Find attributes
        List<AttributeInstanceDto> sources = datasetRepositoriesServiceFacade.findAttributesInstancesWithDimensionAttachmentLevelDenormalized(datasetId, attributeId, dimensionsCodesSelectedEffective);
        if (CollectionUtils.isEmpty(sources)) {
            return null;
        }

        List<String> attributeDimensionsOrdered = toAttributeDimensionsOrdered(datasetDimensionsOrdered, attributeDimensions);
        Map<String, AttributeInstanceDto> attributesByCodeDimensions = buildMapToAttributesWithDimensionAttachmentLevelDenormalizedByCodeDimensions(attributeDimensionsOrdered, sources);

        // Build data
        int dataSize = calculateDataSize(attributeDimensions, dimensionsCodesSelectedEffective);
        DataProcessorForAttributeWithDimensionAttachmentLevel dataProcessor = new DataProcessorForAttributeWithDimensionAttachmentLevel(attributesByCodeDimensions, dataSize);
        toDataCommon(attributeDimensionsOrdered, dimensionsCodesSelectedEffective, dataProcessor);

        return dataProcessor.getDataAttributeForResponse();
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

    /**
     * Retrieves attribute instance value in locale of dataset-repository. NOTE: This value will be escaped to not contain separator in DATA
     */
    private String toAttributeInstanceValueToData(AttributeInstanceBasicDto attributeDto) {
        String attributeValue = attributeDto.getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE); // all attributes has only one locale
        return StatisticalResourcesRestInternalUtils.escapeValueToData(attributeValue);
    }

    private Map<String, AttributeInstanceDto> buildMapToAttributesWithDimensionAttachmentLevelDenormalizedByCodeDimensions(List<String> attributeDimensionsOrdered,
            List<AttributeInstanceDto> attributeInstances) {
        Map<String, AttributeInstanceDto> attributesByCodeDimensions = new HashMap<String, AttributeInstanceDto>(attributeInstances.size());
        for (AttributeInstanceDto attributeInstanceDto : attributeInstances) {
            StringBuilder key = new StringBuilder();
            for (int i = 0; i < attributeDimensionsOrdered.size(); i++) {
                String dimensionId = attributeDimensionsOrdered.get(i);
                String codeDimension = attributeInstanceDto.getCodesByDimension().get(dimensionId).get(0); // only to denormalized!!
                key.append(codeDimension);
                if (i != attributeDimensionsOrdered.size() - 1) {
                    key.append(KEY_DIMENSIONS_SEPARATOR);
                }
            }
            attributesByCodeDimensions.put(key.toString(), attributeInstanceDto);
        }
        return attributesByCodeDimensions;
    }

    private void toDataCommon(List<String> dimensions, Map<String, List<String>> dimensionsCodesSelectedEffective, DataProcessor dataProcessor) throws Exception {

        // Build data
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
                dataProcessor.processFullEntry(id);
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
    }

    private abstract class DataProcessor {

        @SuppressWarnings("rawtypes")
        public String transformDataListToDataResponse(Iterator iterator) {
            return StringUtils.join(iterator, StatisticalResourcesRestInternalConstants.DATA_SEPARATOR);
        }

        protected abstract void processFullEntry(String key);
    }

    private class DataProcessorForAttributeWithDimensionAttachmentLevel extends DataProcessor {

        private final Map<String, AttributeInstanceDto> attributesByCodeDimensions;
        private final List<AttributeInstanceDto>        targets;

        public DataProcessorForAttributeWithDimensionAttachmentLevel(Map<String, AttributeInstanceDto> attributesByCodeDimensions, int dataSize) {
            this.attributesByCodeDimensions = attributesByCodeDimensions;
            targets = new ArrayList<AttributeInstanceDto>(dataSize);
        }

        @Override
        protected void processFullEntry(String key) {
            AttributeInstanceDto attributeInstanceDto = attributesByCodeDimensions.get(key);
            targets.add(attributeInstanceDto);
        }

        public String getDataAttributeForResponse() {
            Iterator<String> iterator = new DataAttributeWithDimensionAttachmentLevelIterator(targets);
            return transformDataListToDataResponse(iterator);
        }
    }

    private class DataProcessorForObservation extends DataProcessor {

        private final Map<String, ObservationExtendedDto> sources;
        private final List<ObservationExtendedDto>        targets;

        public DataProcessorForObservation(Map<String, ObservationExtendedDto> observations, int dataSize) {
            sources = observations;
            targets = new ArrayList<ObservationExtendedDto>(dataSize);
        }

        @Override
        public void processFullEntry(String key) {
            ObservationExtendedDto observationDto = sources.get(key);
            targets.add(observationDto);
        }

        public String getDataObservationsForResponse() {
            Iterator<String> iterator = new DataObservationIterator(targets);
            return transformDataListToDataResponse(iterator);
        }

        public String getDataAttributeForResponse(String attributeId) {
            DataAttributeObservationIterator iterator = new DataAttributeObservationIterator(targets, attributeId);
            String value = transformDataListToDataResponse(iterator);
            if (!iterator.isAnyObservationHasAttribute()) {
                value = null;
            }
            return value;
        }
    }

    private abstract class DataIterator implements Iterator<String> {

        private int       pos = 0;
        private int       max = -1;
        protected List<?> list;

        public DataIterator(List<?> list) {
            this.list = list;
            max = list.size();
        }

        @Override
        public boolean hasNext() {
            return pos < max;
        }

        @Override
        public String next() {
            String value = getValue(pos);
            pos++;
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove unsupported");
        }

        protected abstract String getValue(int position);
    }

    private class DataAttributeWithDimensionAttachmentLevelIterator extends DataIterator {

        public DataAttributeWithDimensionAttachmentLevelIterator(List<AttributeInstanceDto> list) {
            super(list);
        }

        @Override
        protected String getValue(int position) {
            String value = null;
            AttributeInstanceDto attributeInstanceDto = (AttributeInstanceDto) list.get(position);
            if (attributeInstanceDto != null) {
                value = toAttributeInstanceValueToData(attributeInstanceDto);
            }
            return value;
        }
    }

    private class DataObservationIterator extends DataIterator {

        public DataObservationIterator(List<ObservationExtendedDto> list) {
            super(list);
        }

        @Override
        protected String getValue(int position) {
            String value = null;
            ObservationExtendedDto observationExtendedDto = (ObservationExtendedDto) list.get(position);
            if (observationExtendedDto != null) {
                value = observationExtendedDto.getPrimaryMeasure();
            }
            return value;
        }
    }

    private class DataAttributeObservationIterator extends DataIterator {

        private final String attributeId;
        private boolean      anyObservationHasAttribute = false;

        public DataAttributeObservationIterator(List<ObservationExtendedDto> list, String attributeId) {
            super(list);
            this.attributeId = attributeId;
        }

        @Override
        protected String getValue(int position) {
            String value = null;
            ObservationExtendedDto observationExtendedDto = (ObservationExtendedDto) list.get(position);
            if (observationExtendedDto != null) {
                AttributeInstanceObservationDto attributeInstanceObservationDto = observationExtendedDto.getAttributesAsMap().get(attributeId);
                if (attributeInstanceObservationDto != null) {
                    anyObservationHasAttribute = true;
                    value = toAttributeInstanceValueToData(attributeInstanceObservationDto);
                }
            }
            return value;
        }

        public boolean isAnyObservationHasAttribute() {
            return anyObservationHasAttribute;
        }
    }

    private int calculateDataSize(List<String> dimensions, Map<String, List<String>> dimensionsCodesSelectedEffective) {
        int dataSize = 1;
        for (String dimension : dimensions) {
            dataSize = dataSize * dimensionsCodesSelectedEffective.get(dimension).size();
        }
        return dataSize;
    }

    private InternationalString getUpdatedStatisticalOperationName(String operationCode) {
        Operation operation = statisticalOperationsRestInternalFacade.retrieveOperation(operationCode);
        return operation != null ? operation.getName() : null;
    }

    private InternationalString getUpdatedStatisticalOperationInstanceName(String operationId, String instanceId) {
        Instance instance = statisticalOperationsRestInternalFacade.retrieveInstanceById(operationId, instanceId);
        return instance != null ? instance.getName() : null;
    }
}