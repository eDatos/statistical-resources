package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataRequired;
import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.AttributeType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DataStructureComponentsType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.DimensionListType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.MeasureDimensionType;
import org.sdmx.resources.sdmxml.schemas.v2_1.structure.TimeDimensionType;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeQualifierType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.invocation.SrmRestInternalService;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.SiemacLifecycleFiller;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceapi.LifecycleInvocationValidatorBase;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("datasetLifecycleService")
public class DatasetLifecycleServiceImpl extends LifecycleTemplateService<DatasetVersion> {

    @Autowired
    private LifecycleCommonMetadataChecker             lifecycleCommonMetadataChecker;

    @Autowired
    private SiemacLifecycleChecker                     siemacLifecycleChecker;

    @Autowired
    private SiemacLifecycleFiller                      siemacLifecycleFiller;

    @Autowired
    private DatasetLifecycleServiceInvocationValidator datasetLifecycleServiceInvocationValidator;

    @Autowired
    private DatasetVersionRepository                   datasetVersionRepository;

    
    @Autowired
    private SrmRestInternalService srmRestInternalService;
    

    // ------------------------------------------------------------------------------------------------------
    // >> PRODUCTION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToProductionValidationResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // CHECK all dsd related info 
        //Check geo granularity
        //check time granularity
        checkMetadataRequired(resource.getRelatedDsd(), ServiceExceptionParameters.DATASET_VERSION__RELATED_DSD, exceptions);
    }

    @Override
    protected void checkSendToProductionValidationLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        siemacLifecycleChecker.checkSendToProductionValidation(resource, ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }

    @Override
    protected void applySendToProductionValidationResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        ExternalItem externalDsd = resource.getRelatedDsd();
        DataStructure dataStructure = srmRestInternalService.retrieveDsdByUrn(externalDsd.getUrn());
        
        resource.getGeographicCoverage().clear();
        resource.getGeographicCoverage().addAll(buildGeographicCoverage(resource, dataStructure));
        
        resource.setTemporalCoverageList(buildTemporalCoverage(resource, dataStructure));
        
        resource.getMeasures().clear();
        resource.getMeasures().addAll(buildMeasureCoverage(resource, dataStructure));
        
        // Access dataset repository and compute:
        // FORMAT_EXTENT_OBSERVATIONS
        // FORMAT_EXTENT_DIMENSIONS
    }

    private Collection<ExternalItem> buildGeographicCoverage(DatasetVersion resource, DataStructure dataStructure) {
        DataStructureComponentsType dataStructureComponents = dataStructure.getDataStructureComponents();
        
        Dimension spatialDimension = getGeographicDimensionInDsdComponents(dataStructureComponents);
        
        List<ExternalItem> codes = new ArrayList<ExternalItem>();
        if (spatialDimension != null) {
            //TODO: get codes from data
        } else {
            Attribute spatialAttribute = getAttributeWithTypeInDsdComponents(dataStructureComponents, AttributeQualifierType.SPATIAL);
            if (spatialAttribute != null) {
                //TODO: get attribute value/s
            }
        }
        return codes;
    }
    
    private List<String> buildTemporalCoverage(DatasetVersion resource, DataStructure dataStructure) {
        DataStructureComponentsType dataStructureComponents = dataStructure.getDataStructureComponents();
        
        TimeDimensionType timeDimension = getTemporalDimensionInDsdComponents(dataStructureComponents);
        
        List<String> tempValues = new ArrayList<String>();
        if (timeDimension != null) {
            //TODO: get codes from data in dimension 
        } else {
            Attribute timeAttribute = getAttributeWithTypeInDsdComponents(dataStructureComponents, AttributeQualifierType.TIME);
            if (timeAttribute != null) {
                //TODO: get attribute value/s
            }
        }
        return tempValues;
    }
    
    private Collection<ExternalItem> buildMeasureCoverage(DatasetVersion resource, DataStructure dataStructure) {
        DataStructureComponentsType dataStructureComponents = dataStructure.getDataStructureComponents();
        
        MeasureDimensionType measureDimension = getMeasureDimensionInDsdComponents(dataStructureComponents);
        
        List<ExternalItem> codes = new ArrayList<ExternalItem>();
        if (measureDimension != null) {
            //TODO: get codes from data in dimension 
        } else {
            Attribute measureAttribute = getAttributeWithTypeInDsdComponents(dataStructureComponents, AttributeQualifierType.MEASURE);
            if (measureAttribute != null) {
                //TODO: get attribute value/s
            }
        }
        return codes;
    }
    


    @Override
    protected void applySendToProductionValidationLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        siemacLifecycleFiller.applySendToProductionValidationActions(ctx, resource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> DIFFUSION VALIDATION
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToDiffusionValidationResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void checkSendToDiffusionValidationLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        siemacLifecycleChecker.checkSendToDiffusionValidation(resource, ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }

    @Override
    protected void applySendToDiffusionValidationResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void applySendToDiffusionValidationLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        siemacLifecycleFiller.applySendToDiffusionValidationActions(ctx, resource);
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VALIDATION REJECTED
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkSendToValidationRejectedResource(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        // NOTHING
    }

    @Override
    protected void checkSendToValidationRejectedLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        siemacLifecycleChecker.checkSendToValidationRejected(resource, ServiceExceptionParameters.DATASET_VERSION, exceptionItems);
    }

    @Override
    protected void applySendToValidationRejectedResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // TODO: CLEAR ALL METADATA FILLED IN PREVIOUS VALIDATIONS
        resource.getGeographicCoverage().clear();
    }

    @Override
    protected void applySendToValidationRejectedLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        siemacLifecycleFiller.applySendToValidationRejectedActions(ctx, resource);
    }

    private InternationalString buildBibliographicCitation(DatasetVersion resource) {
        ExternalItem rightsHolder = resource.getSiemacMetadataStatisticalResource().getRightsHolder();
        String version = resource.getSiemacMetadataStatisticalResource().getVersionLogic();
        InternationalString publisherName = resource.getSiemacMetadataStatisticalResource().getPublisher().get(0).getTitle();
        // FIXME: GET PUBLIC URL
        String publicUrl = "http://";
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
    protected void checkSendToPublishedLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    protected void applySendToPublishedLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        /* 
         * FILL:
         * DATE_START
         * DATE_END 
         */
        
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    protected void applySendToPublishedResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }

    // ------------------------------------------------------------------------------------------------------
    // >> VERSIONING
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected void checkVersioningLinkedStatisticalResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
        
    }
    
    @Override
    protected void checkVersioningResource(DatasetVersion resource, List<MetamacExceptionItem> exceptionItems) throws MetamacException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not implemented");
    }
    
    @Override
    protected void applyVersioningLinkedStatisticalResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
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
    protected void checkResourceMetadataAllActions(DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(resource, ServiceExceptionParameters.DATASET_VERSION, exceptions);
    }
    
    // ------------------------------------------------------------------------------------------------------
    // SRM related Utils
    // ------------------------------------------------------------------------------------------------------
    
    private Dimension getGeographicDimensionInDsdComponents(DataStructureComponentsType components) {
        DimensionListType dimensionsList = components.getDimensionList();
        if (dimensionsList != null) {
            List<Object> dimensions = dimensionsList.getDimensionsAndMeasureDimensionsAndTimeDimensions();
            for (Object dimensionObj : dimensions) {
                if (dimensionObj instanceof Dimension) {
                    Dimension dimension = (Dimension)dimensionObj;
                    if (dimension.isIsSpatial()) {
                        return dimension;
                    }
                }
            }
        }
        return null;
    }
    
    private TimeDimensionType getTemporalDimensionInDsdComponents(DataStructureComponentsType components) {
        DimensionListType dimensionsList = components.getDimensionList();
        if (dimensionsList != null) {
            List<Object> dimensions = dimensionsList.getDimensionsAndMeasureDimensionsAndTimeDimensions();
            for (Object dimensionObj : dimensions) {
                if (dimensionObj instanceof TimeDimensionType) {
                    return (TimeDimensionType)dimensionObj;
                }
            }
        }
        return null;
    }
    
    private MeasureDimensionType getMeasureDimensionInDsdComponents(DataStructureComponentsType components) {
        DimensionListType dimensionsList = components.getDimensionList();
        if (dimensionsList != null) {
            List<Object> dimensions = dimensionsList.getDimensionsAndMeasureDimensionsAndTimeDimensions();
            for (Object dimensionObj : dimensions) {
                if (dimensionObj instanceof MeasureDimensionType) {
                    return (MeasureDimensionType)dimensionObj;
                }
            }
        }
        return null;
    }
    
    private Attribute getAttributeWithTypeInDsdComponents(DataStructureComponentsType components, AttributeQualifierType type) {
        AttributeListType attributesList = components.getAttributeList();
        if (attributesList != null) {
            for (Object attrObj : attributesList.getAttributesAndReportingYearStartDaies()) {
                if (attrObj instanceof Attribute) {
                    Attribute attribute = (Attribute) attrObj;
                    if (type.equals(attribute.getType())) {
                        return attribute;
                    }
                }
            }
        }
        return null;
    }
}
