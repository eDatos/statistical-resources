package org.siemac.metamac.statistical.resources.web.server;

import org.siemac.metamac.statistical.resources.web.server.handlers.ValidateTicketActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.agency.GetAgenciesPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.collection.DeleteCollectionListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.collection.GetCollectionActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.collection.GetCollectionPaginatedListActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.collection.SaveCollectionActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.collection.UpdateCollectionProcStatusActionHandler;
import org.siemac.metamac.statistical.resources.web.server.handlers.collection.VersionCollectionActionHandler;
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
import org.siemac.metamac.statistical.resources.web.shared.agency.GetAgenciesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.DeleteCollectionListAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.GetCollectionPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.SaveCollectionAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.UpdateCollectionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.collection.VersionCollectionAction;
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
import org.siemac.metamac.web.common.server.handlers.CloseSessionActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetEditionLanguagesActionHandlers;
import org.siemac.metamac.web.common.server.handlers.GetLoginPageUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.GetNavigationBarUrlActionHandler;
import org.siemac.metamac.web.common.server.handlers.MockCASUserActionHandler;
import org.siemac.metamac.web.common.shared.CloseSessionAction;
import org.siemac.metamac.web.common.shared.GetEditionLanguagesAction;
import org.siemac.metamac.web.common.shared.GetLoginPageUrlAction;
import org.siemac.metamac.web.common.shared.GetNavigationBarUrlAction;
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
        bindHandler(GetCollectionPaginatedListAction.class, GetCollectionPaginatedListActionHandler.class);
        bindHandler(GetCollectionAction.class, GetCollectionActionHandler.class);
        bindHandler(SaveCollectionAction.class, SaveCollectionActionHandler.class);
        bindHandler(DeleteCollectionListAction.class, DeleteCollectionListActionHandler.class);
        bindHandler(UpdateCollectionProcStatusAction.class, UpdateCollectionProcStatusActionHandler.class);
        bindHandler(VersionCollectionAction.class, VersionCollectionActionHandler.class);

        bindHandler(ValidateTicketAction.class, ValidateTicketActionHandler.class);
        bindHandler(GetLoginPageUrlAction.class, GetLoginPageUrlActionHandler.class);
        bindHandler(CloseSessionAction.class, CloseSessionActionHandler.class);
        bindHandler(GetNavigationBarUrlAction.class, GetNavigationBarUrlActionHandler.class);

        bindHandler(GetEditionLanguagesAction.class, GetEditionLanguagesActionHandlers.class);

        // This action should be removed to use CAS authentication
        bindHandler(MockCASUserAction.class, MockCASUserActionHandler.class);
    }

}
