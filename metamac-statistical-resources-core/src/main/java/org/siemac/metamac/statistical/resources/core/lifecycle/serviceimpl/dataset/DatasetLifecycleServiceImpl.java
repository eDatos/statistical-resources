package org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.dataset;

import static org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils.checkMetadataRequired;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.LocalisedString;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.util.GeneratorUrnUtils;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.utils.DatasetVersioningCopyUtils;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.lifecycle.LifecycleCommonMetadataChecker;
import org.siemac.metamac.statistical.resources.core.lifecycle.serviceimpl.LifecycleTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("datasetLifecycleService")
public class DatasetLifecycleServiceImpl extends LifecycleTemplateService<DatasetVersion> {

    @Autowired
    private LifecycleCommonMetadataChecker lifecycleCommonMetadataChecker;

    @Autowired
    private DatasetVersionRepository       datasetVersionRepository;

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
        // NOTHING
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
        // NOTHING
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
        // nothing specific to check
    }

    @Override
    protected DatasetVersion copyResourceForVersioning(DatasetVersion previousResource) throws MetamacException {
        return DatasetVersioningCopyUtils.copyDatasetVersion(previousResource);
    }

    @Override
    protected void applyVersioningNewResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // FIXME: Pendiente añadir método de duplicado en DatasetRepositoriesServiceFacade
        // It's necessary to duplicate datasetRepository and set the new id to datasetVersion
        throw new UnsupportedOperationException("Not implemented: needed a DatasetRepositoriesServiceFacade method");
    }

    @Override
    protected void applyVersioningPreviousResource(ServiceContext ctx, DatasetVersion resource) throws MetamacException {
        // nothing specific to apply
    }

    @Override
    protected DatasetVersion updateResourceUrnAfterVersioning(DatasetVersion resource) throws MetamacException {
        String[] creator = new String[]{resource.getSiemacMetadataStatisticalResource().getMaintainer().getCodeNested()};
        resource.getSiemacMetadataStatisticalResource().setUrn(
                GeneratorUrnUtils.generateSiemacStatisticalResourceDatasetVersionUrn(creator, resource.getSiemacMetadataStatisticalResource().getCode(), resource
                        .getSiemacMetadataStatisticalResource().getVersionLogic()));
        return resource;
    }

    // ------------------------------------------------------------------------------------------------------
    // GENERAL ABSTRACT METHODS
    // ------------------------------------------------------------------------------------------------------

    @Override
    protected DatasetVersion saveResource(DatasetVersion resource) {
        return datasetVersionRepository.save(resource);
    }

    @Override
    protected DatasetVersion retrieveResourceByUrn(String urn) throws MetamacException {
        return datasetVersionRepository.retrieveByUrn(urn);
    }

    @Override
    protected DatasetVersion retrieveResourceByResource(DatasetVersion resource) throws MetamacException {
        return datasetVersionRepository.retrieveByUrn(resource.getSiemacMetadataStatisticalResource().getUrn());
    }

    @Override
    protected DatasetVersion retrievePreviousPublishedResourceByResource(DatasetVersion resource) throws MetamacException {
        return datasetVersionRepository.retrieveLastPublishedVersion(resource.getDataset().getIdentifiableStatisticalResource().getUrn());
    }

    @Override
    protected void checkResourceMetadataAllActions(ServiceContext ctx, DatasetVersion resource, List<MetamacExceptionItem> exceptions) throws MetamacException {
        lifecycleCommonMetadataChecker.checkDatasetVersionCommonMetadata(resource, ServiceExceptionParameters.DATASET_VERSION, exceptions);
    }

    @Override
    protected String getResourceUrn(DatasetVersion resource) {
        return resource.getSiemacMetadataStatisticalResource().getUrn();
    }

}
