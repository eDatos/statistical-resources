package org.siemac.metamac.statistical.resources.web.server;

import org.siemac.metamac.statistical.resources.web.server.handlers.ValidateTicketActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.agency.GetAgenciesPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.DeleteDatasetListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasetActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasetsByStatisticalOperationPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.GetDatasourcesByDatasetPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.SaveDatasetActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.SaveDatasourceActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.UpdateDatasetProcStatusActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.dataset.VersionDatasetActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.operation.GetStatisticalOperationActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.operation.GetStatisticalOperationsPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.DeletePublicationListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.GetPublicationActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.GetPublicationPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.SavePublicationActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.UpdatePublicationProcStatusActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.publication.VersionPublicationActionHandler;
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.DeleteDatasetListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetsByStatisticalOperationPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasourcesByDatasetPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasourceAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.VersionDatasetAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.operation.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.DeletePublicationListAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.GetPublicationPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.SavePublicationAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.UpdatePublicationProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.publication.VersionPublicationAction;
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

        // AGENCIES
        bindHandler(GetAgenciesPaginatedListAction.class, GetAgenciesPaginatedListActionHandler.class);

        // DATASETS
        bindHandler(GetDatasetAction.class, GetDatasetActionHandler.class);
        bindHandler(SaveDatasetAction.class, SaveDatasetActionHandler.class);
        bindHandler(DeleteDatasetListAction.class, DeleteDatasetListActionHandler.class);
        bindHandler(GetDatasetsByStatisticalOperationPaginatedListAction.class, GetDatasetsByStatisticalOperationPaginatedListActionHandler.class);
        bindHandler(UpdateDatasetProcStatusAction.class, UpdateDatasetProcStatusActionHandler.class);
        bindHandler(VersionDatasetAction.class, VersionDatasetActionHandler.class);
        bindHandler(GetDatasourcesByDatasetPaginatedListAction.class, GetDatasourcesByDatasetPaginatedListActionHandler.class);
        bindHandler(SaveDatasourceAction.class, SaveDatasourceActionHandler.class);

        // COLLECTIONS
        bindHandler(GetPublicationPaginatedListAction.class, GetPublicationPaginatedListActionHandler.class);
        bindHandler(GetPublicationAction.class, GetPublicationActionHandler.class);
        bindHandler(SavePublicationAction.class, SavePublicationActionHandler.class);
        bindHandler(DeletePublicationListAction.class, DeletePublicationListActionHandler.class);
        bindHandler(UpdatePublicationProcStatusAction.class, UpdatePublicationProcStatusActionHandler.class);
        bindHandler(VersionPublicationAction.class, VersionPublicationActionHandler.class);

        bindHandler(ValidateTicketAction.class, ValidateTicketActionHandler.class);
        bindHandler(GetLoginPageUrlAction.class, GetLoginPageUrlActionHandler.class);
        bindHandler(CloseSessionAction.class, CloseSessionActionHandler.class);
        bindHandler(GetNavigationBarUrlAction.class, GetNavigationBarUrlActionHandler.class);

        bindHandler(LoadConfigurationPropertiesAction.class, LoadConfigurationPropertiesActionHandler.class);

        // This action should be removed to use CAS authentication
        bindHandler(MockCASUserAction.class, MockCASUserActionHandler.class);
    }
}
