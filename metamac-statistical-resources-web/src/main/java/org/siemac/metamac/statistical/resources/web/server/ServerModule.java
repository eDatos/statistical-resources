package org.siemac.metamac.statistical.resources.web.server;

import org.siemac.metamac.statistical.resources.web.server.handlers.ValidateTicketActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.base.GetInitialValuesActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.base.GetLatestResourceVersionActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.DeleteDatasetVersionsActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.DeleteDatasourcesActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasetAttributeInstancesActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasetAttributesActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasetDimensionCoverageActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasetDimensionsActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasetVersionActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasetVersionMainCoveragesActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasetVersionsActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasetsActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasourcesByDatasetActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetVersionsOfDatasetActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.SaveDatasetVersionActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.SaveDatasourceActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.UpdateDatasetVersionProcStatusActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.UpdateDatasetVersionsProcStatusActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetAgenciesPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetAgencySchemesPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetCommonMetadataConfigurationsListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetConceptSchemesPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetConceptsPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetDsdsPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetGeographicalGranularitiesListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetLanguagesCodesActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetOrganisationUnitSchemesPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetOrganisationUnitsPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetStatisticalOperationActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetStatisticalOperationInstancesPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetStatisticalOperationsPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.external.GetTemporalGranularitiesListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.DeletePublicationStructureElementActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.DeletePublicationVersionsActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.GetPublicationStructureActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.GetPublicationVersionActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.GetPublicationVersionsActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.GetVersionsOfPublicationActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.SavePublicationStructureElementActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.SavePublicationVersionActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.UpdatePublicationStructureElementLocationActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.UpdatePublicationVersionProcStatusActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.UpdatePublicationVersionsProcStatusActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.query.DeleteQueryVersionsActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.query.GetQueriesActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.query.GetQueryVersionActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.query.GetQueryVersionsActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.query.SaveQueryVersionActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.query.UpdateQueryVersionProcStatusActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.query.UpdateQueryVersionsProcStatusActionHandler;
import org.siemac.metamac.statistical.resources.web.shared.base.GetInitialValuesAction;
import org.siemac.metamac.statistical.resources.web.shared.base.GetLatestResourceVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasourcesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAttributeInstancesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAttributesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionCoverageAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetDimensionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionMainCoveragesAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetVersionsOfDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasourceAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionsProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgenciesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetAgencySchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCommonMetadataConfigurationsListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptSchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetGeographicalGranularitiesListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetLanguagesCodesAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetOrganisationUnitSchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetOrganisationUnitsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationInstancesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetTemporalGranularitiesListAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationStructureElementAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationStructureAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetVersionsOfPublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationStructureElementAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationStructureElementLocationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationVersionsProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.query.DeleteQueryVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueriesAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.query.GetQueryVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.query.SaveQueryVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.query.UpdateQueryVersionsProcStatusAction;
import org.siemac.metamac.web.common.server.handlers.CloseSessionActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetLoginPageUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetNavigationBarUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.LoadConfigurationPropertiesActionHandler;
import org.siemac.metamac.web.common.server.handlers.MockCASUserActionHandler;
import org.siemac.metamac.web.common.shared.CloseSessionAction;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlAction;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlAction;
import org.siemac.metamac.web.common.shared.LoadConfigurationPropertiesAction;
import org.siemac.metamac.web.common.shared.MockCASUserAction;
import org.siemac.metamac.web.common.shared.ValidateTicketAction;
import org.springframework.stereotype.Component;

import com.gwtplatform.dispatch.server.spring.HandlerModule;

/**
 * Module which binds the handlers and configurations.
 */
@Component
public class ServerModule extends HandlerModule {

    public ServerModule() {
    }

    @Override
    protected void configureHandlers() {

        // STATISTICAL OPERATIONS
        bindHandler(GetStatisticalOperationAction.class, GetStatisticalOperationActionHandler.class);
        bindHandler(GetStatisticalOperationsPaginatedListAction.class, GetStatisticalOperationsPaginatedListActionHandler.class);
        bindHandler(GetStatisticalOperationInstancesPaginatedListAction.class, GetStatisticalOperationInstancesPaginatedListActionHandler.class);

        // COMMON METADATA
        bindHandler(GetCommonMetadataConfigurationsListAction.class, GetCommonMetadataConfigurationsListActionHandler.class);

        // SRM
        bindHandler(GetDsdsPaginatedListAction.class, GetDsdsPaginatedListActionHandler.class);
        bindHandler(GetGeographicalGranularitiesListAction.class, GetGeographicalGranularitiesListActionHandler.class);
        bindHandler(GetTemporalGranularitiesListAction.class, GetTemporalGranularitiesListActionHandler.class);
        bindHandler(GetLanguagesCodesAction.class, GetLanguagesCodesActionHandler.class);
        bindHandler(GetConceptSchemesPaginatedListAction.class, GetConceptSchemesPaginatedListActionHandler.class);
        bindHandler(GetConceptsPaginatedListAction.class, GetConceptsPaginatedListActionHandler.class);
        bindHandler(GetOrganisationUnitSchemesPaginatedListAction.class, GetOrganisationUnitSchemesPaginatedListActionHandler.class);
        bindHandler(GetOrganisationUnitsPaginatedListAction.class, GetOrganisationUnitsPaginatedListActionHandler.class);
        bindHandler(GetAgencySchemesPaginatedListAction.class, GetAgencySchemesPaginatedListActionHandler.class);
        bindHandler(GetAgenciesPaginatedListAction.class, GetAgenciesPaginatedListActionHandler.class);

        // DATASETS
        bindHandler(GetDatasetVersionAction.class, GetDatasetVersionActionHandler.class);
        bindHandler(SaveDatasetVersionAction.class, SaveDatasetVersionActionHandler.class);
        bindHandler(DeleteDatasetVersionsAction.class, DeleteDatasetVersionsActionHandler.class);
        bindHandler(GetDatasetVersionsAction.class, GetDatasetVersionsActionHandler.class);
        bindHandler(GetDatasetsAction.class, GetDatasetsActionHandler.class);
        bindHandler(UpdateDatasetVersionsProcStatusAction.class, UpdateDatasetVersionsProcStatusActionHandler.class);
        bindHandler(UpdateDatasetVersionProcStatusAction.class, UpdateDatasetVersionProcStatusActionHandler.class);
        bindHandler(GetDatasourcesByDatasetAction.class, GetDatasourcesByDatasetActionHandler.class);
        bindHandler(SaveDatasourceAction.class, SaveDatasourceActionHandler.class);
        bindHandler(DeleteDatasourcesAction.class, DeleteDatasourcesActionHandler.class);
        bindHandler(GetDatasetDimensionsAction.class, GetDatasetDimensionsActionHandler.class);
        bindHandler(GetDatasetDimensionCoverageAction.class, GetDatasetDimensionCoverageActionHandler.class);
        bindHandler(GetDatasetVersionMainCoveragesAction.class, GetDatasetVersionMainCoveragesActionHandler.class);
        bindHandler(GetVersionsOfDatasetAction.class, GetVersionsOfDatasetActionHandler.class);
        bindHandler(GetDatasetAttributesAction.class, GetDatasetAttributesActionHandler.class);
        bindHandler(GetDatasetAttributeInstancesAction.class, GetDatasetAttributeInstancesActionHandler.class);

        // PUBLICATIONS
        bindHandler(GetPublicationVersionsAction.class, GetPublicationVersionsActionHandler.class);
        bindHandler(GetPublicationVersionAction.class, GetPublicationVersionActionHandler.class);
        bindHandler(SavePublicationVersionAction.class, SavePublicationVersionActionHandler.class);
        bindHandler(DeletePublicationVersionsAction.class, DeletePublicationVersionsActionHandler.class);
        bindHandler(UpdatePublicationVersionsProcStatusAction.class, UpdatePublicationVersionsProcStatusActionHandler.class);
        bindHandler(UpdatePublicationVersionProcStatusAction.class, UpdatePublicationVersionProcStatusActionHandler.class);
        bindHandler(GetPublicationStructureAction.class, GetPublicationStructureActionHandler.class);
        bindHandler(SavePublicationStructureElementAction.class, SavePublicationStructureElementActionHandler.class);
        bindHandler(UpdatePublicationStructureElementLocationAction.class, UpdatePublicationStructureElementLocationActionHandler.class);
        bindHandler(DeletePublicationStructureElementAction.class, DeletePublicationStructureElementActionHandler.class);
        bindHandler(GetVersionsOfPublicationAction.class, GetVersionsOfPublicationActionHandler.class);

        // QUERIES
        bindHandler(GetQueriesAction.class, GetQueriesActionHandler.class);
        bindHandler(GetQueryVersionsAction.class, GetQueryVersionsActionHandler.class);
        bindHandler(SaveQueryVersionAction.class, SaveQueryVersionActionHandler.class);
        bindHandler(GetQueryVersionAction.class, GetQueryVersionActionHandler.class);
        bindHandler(DeleteQueryVersionsAction.class, DeleteQueryVersionsActionHandler.class);
        bindHandler(UpdateQueryVersionsProcStatusAction.class, UpdateQueryVersionsProcStatusActionHandler.class);
        bindHandler(UpdateQueryVersionProcStatusAction.class, UpdateQueryVersionProcStatusActionHandler.class);

        // COMMON
        bindHandler(GetLatestResourceVersionAction.class, GetLatestResourceVersionActionHandler.class);
        bindHandler(GetInitialValuesAction.class, GetInitialValuesActionHandler.class);

        bindHandler(ValidateTicketAction.class, ValidateTicketActionHandler.class);
        bindHandler(GetLoginPageUrlAction.class, GetLoginPageUrlActionHandler.class);
        bindHandler(CloseSessionAction.class, CloseSessionActionHandler.class);
        bindHandler(GetNavigationBarUrlAction.class, GetNavigationBarUrlActionHandler.class);

        bindHandler(LoadConfigurationPropertiesAction.class, LoadConfigurationPropertiesActionHandler.class);

        // This action should be removed to use CAS authentication
        bindHandler(MockCASUserAction.class, MockCASUserActionHandler.class);
    }
}
