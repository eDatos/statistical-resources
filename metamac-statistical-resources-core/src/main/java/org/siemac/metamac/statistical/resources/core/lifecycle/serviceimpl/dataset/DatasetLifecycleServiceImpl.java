package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataRequired;

import java.util.ArrayList;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ApplicationException;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.CodeResource;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concepts;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponent;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdComponentType;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.TemporalCode;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.invocation.utils.RestMapper;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesCollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arte.statistic.dataset.repository.dto.AttributeDto;
import com.arte.statistic.dataset.repository.dto.CodeDimensionDto;
import com.arte.statistic.dataset.repository.dto.ConditionObservationDto;
import com.arte.statistic.dataset.repository.dto.DatasetRepositoryDto;
import com.arte.statistic.dataset.repository.service.DatasetRepositoriesServiceFacade;

@Service("datasetLifecycleService")
public class DatasetLifecycleServiceImpl extends LifecycleTemplateService<DatasetVersion> {

    @Autowired
    private LifecycleCommonMetadataChecker             lifecycleCommonMetadataChecker;

    @Autowired
    private DatasetLifecycleServiceInvocationValidator datasetLifecycleServiceInvocationValidator;

    @Autowired
    private DatasetVersionRepository                   datasetVersionRepository;

    @Autowired
    private SrmRestInternalService                     srmRestInternalService;

    @Autowired
    private DatasetRepositoriesServiceFacade           statisticsDatasetRepositoriesServiceFacade;

    @Autowired
    private RestMapper                                 restMapper;

    @Override
    protected String getResourceMetadataName() throws MetamacException {
        return ServiceExceptionParameters.DATASET_VERSION;
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToProductionValidationResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // CHECK all dsd related info
        checkMetadataRequired(resource.getRelatedDsd(), ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD, exceptions);

        checkMetadataRequired(resource.getGeographicGranularities(), ServiceExceptionParameters.DATASET_VERSION__GEOGRAPHIC_GRANULARITIES, exceptions);
        checkMetadataRequired(resource.getTemporalGranularities(), ServiceExceptionParameters.DATASET_VERSION__TEMPORAL_GRANULARITIES, exceptions);

        checkMetadataRequired(resource.getDateNextUpdate(), ServiceExceptionParameters.DATASET_VERSION__DATE_NEXT_UPDATE, exceptions);
        checkMetadataRequired(resource.getUpdateFrequency(), ServiceExceptionParameters.DATASET_VERSION__UPDATE_FREQUENCY, exceptions);
        checkMetadataRequired(resource.getStatisticOfficiality(), ServiceExceptionParameters.DATASET_VERSION__STATISTIC_OFFICIALITY, exceptions);

        if (resource.getDatasources() == null || resource.getDatasources().isEmpty()) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.DATASET_EMPTY_DATASOURCES, resource.getSiemacMetadataStatisticalResource().getUrn()));
        } else {
            if (!hasAnyDatasourceDateNextUpdate(resource)) {
                checkMetadataRequired(resource.getDateNextUpdate(), ServiceExceptionParameters.DATASET_VERSION__DATE_NEXT_UPDATE, exceptions);
            }
        }
    }

    private boolean hasAnyDatasourceDateNextUpdate(DatasetVersion resource) {
        for (Datasource datasource : resource.getDatasources()) {
            if (datasource.getDateNextUpdate() != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void applySendToProductionValidationResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        ExternalItem externalDsd = resource.getRelatedDsd();
        DataStructure dataStructure = srmRestInternalService.retrieveDsdByUrn(externalDsd.getUrn());

        resource.getCoverages().clear();

        processCoverages(resource, dataStructure);

        processDataRelatedMetadata(resource);

        // processStartEndDates();
        // TODO: DATE_START, DATE_END

        // TODO: DATE_NEXT_UPDATE
        // calculateDateNextUpdate(resource);
    }

    private void processDataRelatedMetadata(DatasetVersion resource) throws MetamacException {
        try {
            DatasetRepositoryDto datasetRepository = statisticsDatasetRepositoriesServiceFacade.retrieveDatasetRepository(resource.getDatasetRepositoryId());
            resource.setFormatExtentDimensions(datasetRepository.getDimensions().size());
            // TODO: FORMAT_EXTENT_OBSERVATIONS
        } catch (ApplicationException e) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Error retrieving datasetRepository " + resource.getDatasetRepositoryId());
        }
    }

    private void processCoverages(DatasetVersion resource, DataStructure dataStructure) throws MetamacException {
        List<DsdDimension> dimensions = DsdProcessor.getDimensions(dataStructure);

        resource.getCoverages().clear();
        resource.getGeographicCoverage().clear();
        resource.getTemporalCoverage().clear();
        resource.getMeasureCoverage().clear();

        for (DsdDimension dimension : dimensions) {
            List<CodeDimension> codes = getCodesFromDsdComponent(resource, dimension);
            List<ExternalItem> items = buildExternalItemsBasedOnCodeDimensions(codes, dimension);
            if (items != null) {
                addTranslationsToCodesFromExternalItems(codes, items);
            }
            resource.getCoverages().addAll(codes);
            switch (dimension.getType()) {
                case SPATIAL:
                    for (ExternalItem item : items) {
                        if (!StatisticalResourcesCollectionUtils.isExternalItemInCollection(resource.getGeographicCoverage(), item)) {
                            resource.getGeographicCoverage().add(item);
                        }
                    }
                    break;
                case MEASURE:
                    resource.getMeasureCoverage().addAll(items);
                    break;
                case TEMPORAL:
                    List<TemporalCode> temporalCodes = buildTemporalCodeFromCodeDimensions(codes);
                    resource.getTemporalCoverage().addAll(temporalCodes);
                    break;
            }
        }

        // Try to fill specific coverages from attributes
        if (resource.getGeographicCoverage().isEmpty()) {
            List<ExternalItem> codeItems = processExternalItemsCodeFromAttributeByType(resource, dataStructure, DsdComponentType.SPATIAL);
            resource.getGeographicCoverage().addAll(codeItems);
        }
        if (resource.getTemporalCoverage().isEmpty()) {
            List<CodeDimension> codeItems = processCodeFromAttributeByType(resource, dataStructure, DsdComponentType.TEMPORAL);
            resource.getTemporalCoverage().addAll(buildTemporalCodeFromCodeDimensions(codeItems));
        }
        if (resource.getMeasureCoverage().isEmpty()) {
            List<ExternalItem> codeItems = processExternalItemsCodeFromAttributeByType(resource, dataStructure, DsdComponentType.MEASURE);
            resource.getMeasureCoverage().addAll(codeItems);
        }
    }

    private List<ExternalItem> processExternalItemsCodeFromAttributeByType(DatasetVersion resource, DataStructure dataStructure, DsdComponentType type) throws MetamacException {
        DsdAttribute attribute = getDsdAttributeByType(dataStructure, type);
        List<ExternalItem> items = new ArrayList<ExternalItem>();
        if (attribute != null) {
            List<CodeDimension> codes = filterCodesFromAttribute(resource, resource.getDatasetRepositoryId(), attribute.getComponentId());
            items = buildExternalItemsBasedOnCodeDimensions(codes, attribute);
        }
        return items;
    }

    private List<CodeDimension> processCodeFromAttributeByType(DatasetVersion resource, DataStructure dataStructure, DsdComponentType type) throws MetamacException {
        DsdAttribute attribute = getDsdAttributeByType(dataStructure, type);
        List<CodeDimension> codes = new ArrayList<CodeDimension>();
        if (attribute != null) {
            codes = filterCodesFromAttribute(resource, resource.getDatasetRepositoryId(), attribute.getComponentId());
            List<ExternalItem> items = buildExternalItemsBasedOnCodeDimensions(codes, attribute);
            addTranslationsToCodesFromExternalItems(codes, items);
        }
        return codes;
    }

    private List<TemporalCode> buildTemporalCodeFromCodeDimensions(List<CodeDimension> codes) {
        List<TemporalCode> temporalCodes = new ArrayList<TemporalCode>();
        for (CodeDimension codeDim : codes) {
            TemporalCode tempCode = new TemporalCode();
            tempCode.setIdentifier(codeDim.getIdentifier());
            tempCode.setTitle(codeDim.getTitle());
            temporalCodes.add(tempCode);
        }
        return temporalCodes;
    }

    private List<ExternalItem> buildExternalItemsBasedOnCodeDimensions(List<CodeDimension> codes, DsdComponent component) throws MetamacException {
        if (component.getCodelistRepresentationUrn() != null) {
            return buildExternalItemsBasedOnCodeDimensionsInCodelist(codes, component.getCodelistRepresentationUrn());
        } else if (component.getConceptSchemeRepresentationUrn() != null) {
            return buildExternalItemsBasedOnCodeDimensionsInConceptScheme(codes, component.getConceptSchemeRepresentationUrn());
        } else {
            return null;
        }
    }

    private List<ExternalItem> buildExternalItemsBasedOnCodeDimensionsInCodelist(List<CodeDimension> codeDimensions, String codelistRepresentationUrn) throws MetamacException {
        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();

        Codes codes = srmRestInternalService.retrieveCodesOfCodelistEfficiently(codelistRepresentationUrn);

        for (CodeResource code : codes.getCodes()) {
            for (CodeDimension codeDim : codeDimensions) {
                if (codeDim.getIdentifier().equals(code.getId())) {
                    externalItems.add(restMapper.buildExternalItemFromCode(code));
                }
            }
        }

        if (externalItems.size() < codeDimensions.size()) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Some codes in dimension were not found in codelist " + codelistRepresentationUrn);
        }

        return externalItems;
    }

    private List<ExternalItem> buildExternalItemsBasedOnCodeDimensionsInConceptScheme(List<CodeDimension> codeDimensions, String conceptSchemeRepresentationUrn) throws MetamacException {
        List<ExternalItem> externalItems = new ArrayList<ExternalItem>();

        Concepts concepts = srmRestInternalService.retrieveConceptsOfConceptSchemeEfficiently(conceptSchemeRepresentationUrn);

        for (ItemResourceInternal concept : concepts.getConcepts()) {
            for (CodeDimension codeDim : codeDimensions) {
                if (codeDim.getIdentifier().equals(concept.getId())) {
                    externalItems.add(restMapper.buildExternalItemFromSrmItemResourceInternal(concept));
                }
            }
        }

        if (externalItems.size() < codeDimensions.size()) {
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "Some codes in dimension were not found in conceptScheme " + conceptSchemeRepresentationUrn);
        }

        return externalItems;
    }

    private List<CodeDimension> getCodesFromDsdComponent(DatasetVersion resource, DsdComponent dsdComponent) throws MetamacException {
        List<CodeDimension> codes = new ArrayList<CodeDimension>();
        if (dsdComponent != null) {
            if (dsdComponent instanceof DsdDimension) {
                codes = filterCodesFromDimension(resource, resource.getDatasetRepositoryId(), dsdComponent.getComponentId());
            } else if (dsdComponent instanceof DsdAttribute) {
                codes = filterCodesFromAttribute(resource, resource.getDatasetRepositoryId(), dsdComponent.getComponentId());
            }
        }
        return codes;
    }

    private DsdAttribute getDsdAttributeByType(DataStructure dataStructure, DsdComponentType type) throws MetamacException {
        List<DsdAttribute> attributes = DsdProcessor.getAttributes(dataStructure);
        DsdAttribute foundAttribute = filterDsdAttributeWithType(attributes, type);
        return foundAttribute;
    }

    private List<CodeDimension> filterCodesFromDimension(DatasetVersion resource, String datasetRepositoryId, String dimensionId) throws MetamacException {
        try {
            List<ConditionObservationDto> conditions = statisticsDatasetRepositoriesServiceFacade.findCodeDimensions(datasetRepositoryId);

            List<CodeDimensionDto> dimCodes = filterCodeDimensionsForDimension(dimensionId, conditions);

            List<CodeDimension> codes = new ArrayList<CodeDimension>();
            for (CodeDimensionDto code : dimCodes) {
                CodeDimension codeDimension = new CodeDimension();
                codeDimension.setIdentifier(code.getCodeDimensionId());
                codeDimension.setTitle(code.getCodeDimensionId());
                codeDimension.setDsdComponentId(dimensionId);
                codeDimension.setDatasetVersion(resource);
                codes.add(codeDimension);
            }
            return codes;
        } catch (ApplicationException e) {
            throw new MetamacException(e, ServiceExceptionType.UNKNOWN, "An error has ocurred retrieving codes from dataset repository " + datasetRepositoryId + " for dimension " + dimensionId);
        }
    }

    private List<CodeDimension> filterCodesFromAttribute(DatasetVersion resource, String datasetRepositoryId, String attributeId) throws MetamacException {
        try {
            List<AttributeDto> attributes = statisticsDatasetRepositoriesServiceFacade.findAttributes(datasetRepositoryId, attributeId);

            List<CodeDimension> codes = new ArrayList<CodeDimension>();
            if (attributes.size() > 0) {
                String value = attributes.get(0).getValue().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
                CodeDimension codeDimension = new CodeDimension();
                codeDimension.setIdentifier(value);
                codeDimension.setTitle(value);
                codeDimension.setDsdComponentId(attributeId);
                codeDimension.setDatasetVersion(resource);
                codes.add(codeDimension);
            }
            return codes;
        } catch (ApplicationException e) {
            throw new MetamacException(e, ServiceExceptionType.UNKNOWN, "An error has ocurred retrieving values from dataset repository " + datasetRepositoryId + " for attribute " + attributeId);
        }
    }

    private void addTranslationsToCodesFromExternalItems(List<CodeDimension> codeDimensions, List<ExternalItem> externalItems) {
        for (ExternalItem externalItem : externalItems) {
            for (CodeDimension codeDimension : codeDimensions) {
                if (codeDimension.getIdentifier().equals(externalItem.getCode())) {
                    String title = externalItem.getTitle().getLocalisedLabel(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
                    if (title != null) {
                        codeDimension.setTitle(title);
                    } else {
                        codeDimension.setTitle(codeDimension.getIdentifier());
                    }
                }
            }
        }
    }

    private List<CodeDimensionDto> filterCodeDimensionsForDimension(String dimensionId, List<ConditionObservationDto> conditions) {
        for (ConditionObservationDto condition : conditions) {
            if (condition.getCodesDimension().size() > 0 && condition.getCodesDimension().get(0).getDimensionId().equals(dimensionId)) {
                return condition.getCodesDimension();
            }
        }
        return null;
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToDiffusionValidationResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void applySendToDiffusionValidationResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // NOTHING
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToValidationRejectedResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void applySendToValidationRejectedResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // TODO: CLEAR ALL METADATA FILLED IN PREVIOUS VALIDATIONS
        // resource.getGeographicCoverage().clear();
    }

    private InternationalString buildBibliographicCitation(DatasetVersion resource) {
        ExternalItem rightsHolder = resource.getSiemacMetadataStatisticalResource().getCreator();
        String version = resource.getSiemacMetadataStatisticalResource().getVersionLogic();
        InternationalString publisherName = resource.getSiemacMetadataStatisticalResource().getPublisher().get(0).getTitle();
        String publicUrl = StatisticalResourcesConstants.BIBLIOGRAPHIC_CITATION_URI_TOKEN; // This will be replaced by the API
        InternationalString bibliographicInternational = new InternationalString();
        for (LocalisedString localisedTitle : resource.getSiemacMetadataStatisticalResource().getTitle().getTexts()) {
            String locale = localisedTitle.getLocale();
            StringBuilder bibliographicCitation = new StringBuilder();
            bibliographicCitation.append(rightsHolder.getCode()).append(" (").append("").append(") ");
            bibliographicCitation.append(localisedTitle.getLabel()).append(" (v").append(version).append(") [dataset].");
            bibliographicCitation.append(getLocalisedTextInLocaleOrAppDefault(publisherName, locale));
            bibliographicCitation.append(" ").append(publicUrl);

            LocalisedString localised = new LocalisedString(locale, bibliographicCitation.toString());
            bibliographicInternational.addText(localised);
        }
        return bibliographicInternational;
    }

    private String getLocalisedTextInLocaleOrAppDefault(InternationalString internationaString, String locale) {
        if (internationaString.getLocalisedLabel(locale) != null) {
            return internationaString.getLocalisedLabel(locale);
        } else {
            // FIXME: Choose other locale
            return internationaString.getLocalisedLabel(locale);
        }
    }

    // ------------------------------------------------------------------------------------------------------
    // >> PUBLISHED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToPublishedResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    protected void applySendToPublishedResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {

        // FIXME
        /*
         * FILL:
         * DATE_START
         * DATE_END
         */

        buildBibliographicCitation(resource);

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkVersioningResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected void applyVersioningResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    // ------------------------------------------------------------------------------------------------------
    // GENERAL ABSTRACT METHODS
    // ------------------------------------------------------------------------------------------------------
    @Override
    protected LifecycleInvocationValidatorBase<DatasetVersion> getInvocationValidator() {
        return datasetLifecycleServiceInvocationValidator;
    }

    @Override
    protected DatasetVersion saveResource(DatasetVersion resource) {
        return datasetVersionRepository.save(resource);
    }

    @Override
    protected DatasetVersion retrieveResourceByResource(DatasetVersion resource) throws MetamacException {
        return datasetVersionRepository.retrieveByUrn(resource.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Override
    protected DatasetVersion retrievePreviousResourceByResource(DatasetVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected void checkResourceMetadataAllActions(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(resource, ServiceExceptionParameters.DATASET_VERSION, exceptions);
    }

    // ------------------------------------------------------------------------------------------------------
    // SRM related Utils
    // ------------------------------------------------------------------------------------------------------

    private DsdAttribute filterDsdAttributeWithType(List<DsdAttribute> attributes, DsdComponentType type) {
        for (DsdAttribute attr : attributes) {
            if (type.equals(attr.getType())) {
                return attr;
            }
        }
        return null;
    }

}
