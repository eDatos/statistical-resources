package org.siemac.metamac.statistical.resources.web.client.gin;

import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetAttributesTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetDatasourcesTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetListPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetMetadataTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.presenter.DatasetPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationListPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationPresenter;
import org.siemac.metamac.statistical.resources.web.client.operation.presenter.OperationResourcesPresenter;
import org.siemac.metamac.statistical.resources.web.client.presenter.ErrorPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.presenter.MainPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.presenter.UnauthorizedPagePresenter;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationListPresenter;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationMetadataTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationPresenter;
import org.siemac.metamac.statistical.resources.web.client.publication.presenter.PublicationStructureTabPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.presenter.QueryListPresenter;
import org.siemac.metamac.statistical.resources.web.client.query.presenter.QueryPresenter;
import org.siemac.metamac.web.common.client.gin.MetamacWebGinjector;

import com.google.gwt.inject.client.AsyncProvider;
import com.google.gwt.inject.client.GinModules;
import com.google.inject.Provider;
import com.gwtplatform.dispatch.client.gin.DispatchAsyncModule;

@GinModules({DispatchAsyncModule.class, ClientModule.class})
public interface StatisticalResourcesWebGinjector extends MetamacWebGinjector {

    LoggedInGatekeeper getLoggedInGatekeeper();

    Provider<MainPagePresenter> getMainPagePresenter();

    AsyncProvider<OperationListPresenter> getOperationListPresenter();
    AsyncProvider<OperationResourcesPresenter> getOperationResourcesPresenter();
    AsyncProvider<OperationPresenter> getOperationPresenter();
    AsyncProvider<DatasetListPresenter> getDatasetListPresenter();
    AsyncProvider<DatasetPresenter> getDatasetPresenter();
    AsyncProvider<DatasetMetadataTabPresenter> getDatasetMetadataTabPresenter();
    AsyncProvider<DatasetDatasourcesTabPresenter> getDatasetDatasourcesTabPresenter();
    AsyncProvider<DatasetAttributesTabPresenter> getDatasetAttributesTabPresenter();
    AsyncProvider<PublicationListPresenter> getPublicationListPresenter();
    AsyncProvider<PublicationPresenter> getPublicationPresenter();
    AsyncProvider<PublicationMetadataTabPresenter> getPublicationMetadataTabPresenter();
    AsyncProvider<PublicationStructureTabPresenter> getPublicationStructureTabPresenter();
    AsyncProvider<QueryListPresenter> getQueryListPresenter();
    AsyncProvider<QueryPresenter> getQueryPresenter();

    AsyncProvider<ErrorPagePresenter> getErrorPagePresenter();
    AsyncProvider<UnauthorizedPagePresenter> getUnauthorizedPagePresenter();
}
