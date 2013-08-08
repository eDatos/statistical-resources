package org.siemac.metamac.statistical.resources.web.client.dataset.presenter;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;
import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getMessages;

import java.util.List;

import org.siemac.metamac.core.common.constants.shared.UrnConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.core.common.util.shared.StringUtils;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.LoggedInGatekeeper;
import org.siemac.metamac.statistical.resources.web.client.NameTokens;
import org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb;
import org.siemac.metamac.statistical.resources.web.client.base.presenter.StatisticalResourceMetadataBasePresenter;
import org.siemac.metamac.statistical.resources.web.client.dataset.utils.DatasetMetadataExternalField;
import org.siemac.metamac.statistical.resources.web.client.dataset.view.handlers.DatasetMetadataTabUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;
import org.siemac.metamac.statistical.resources.web.client.event.SetOperationEvent;
import org.siemac.metamac.statistical.resources.web.client.utils.MetamacPortalWebUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.PlaceRequestUtils;
import org.siemac.metamac.statistical.resources.web.client.utils.WaitingAsyncCallbackHandlingError;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DatasetVersionWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.GetDatasetVersionsResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetVersionAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.SaveDatasetVersionResult;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionProcStatusAction;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionProcStatusAction.Builder;
import org.siemac.metamac.statistical.resources.web.shared.dataset.UpdateDatasetVersionProcStatusResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptSchemesPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptSchemesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetConceptsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetDsdsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetGeographicalGranularitiesListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetGeographicalGranularitiesListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetTemporalGranularitiesListAction;
import org.siemac.metamac.statistical.resources.web.shared.external.GetTemporalGranularitiesListResult;
import org.siemac.metamac.web.common.client.events.ShowMessageEvent;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.shared.DispatchAsync;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.TitleFunction;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.Place;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class DatasetMetadataTabPresenter extends StatisticalResourceMetadataBasePresenter<DatasetMetadataTabPresenter.DatasetMetadataTabView, DatasetMetadataTabPresenter.DatasetMetadataTabProxy>
        implements
            DatasetMetadataTabUiHandlers {

    private ExternalItemDto operation;

    public interface DatasetMetadataTabView extends StatisticalResourceMetadataBasePresenter.StatisticalResourceMetadataBaseView, HasUiHandlers<DatasetMetadataTabUiHandlers> {

        void setDataset(DatasetVersionDto datasetDto);

        // metadata fill methods
        void setDatasetsForReplaces(GetDatasetVersionsResult result);
        void setDatasetsForIsReplacedBy(GetDatasetVersionsResult result);

        void setStatisticalOperationsForDsdSelection(List<ExternalItemDto> results, ExternalItemDto defaultSelected);
        void setDsdsForRelatedDsd(GetDsdsPaginatedListResult result);

        void setCodesForGeographicalGranularities(GetGeographicalGranularitiesListResult result);
        void setTemporalCodesForField(GetTemporalGranularitiesListResult result, DatasetMetadataExternalField field);

        void setConceptSchemesForStatisticalUnit(GetConceptSchemesPaginatedListResult result);
        void setConceptsForStatisticalUnit(GetConceptsPaginatedListResult result);
    }

    @ProxyCodeSplit
    @NameToken(NameTokens.datasetMetadataPage)
    @UseGatekeeper(LoggedInGatekeeper.class)
    public interface DatasetMetadataTabProxy extends Proxy<DatasetMetadataTabPresenter>, Place {
    }

    @Inject
    public DatasetMetadataTabPresenter(EventBus eventBus, DatasetMetadataTabView view, DatasetMetadataTabProxy proxy, DispatchAsync dispatcher, PlaceManager placeManager) {
        super(eventBus, view, proxy, dispatcher, placeManager);
        getView().setUiHandlers(this);
    }

    @TitleFunction
    public String title() {
        return getConstants().breadcrumbMetadata();
    }

    @Override
    public void prepareFromRequest(PlaceRequest request) {
        super.prepareFromRequest(request);

        String operationCode = PlaceRequestUtils.getOperationParamFromUrl(placeManager);
        String datasetCode = PlaceRequestUtils.getDatasetParamFromUrl(placeManager);
        if (!StringUtils.isBlank(operationCode) && !StringUtils.isBlank(datasetCode)) {
            String operationUrn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_OPERATION_PREFIX, operationCode);
            retrieveOperation(operationUrn);
            retrieveDataset(datasetCode);
        } else {
            StatisticalResourcesWeb.showErrorPage();
        }
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, DatasetPresenter.TYPE_SetContextAreaMetadata, this);
    }

    private void retrieveOperation(String urn) {
        if (operation == null || !StringUtils.equals(operation.getUrn(), urn)) {
            dispatcher.execute(new GetStatisticalOperationAction(urn), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationResult>(this) {

                @Override
                public void onWaitSuccess(GetStatisticalOperationResult result) {
                    DatasetMetadataTabPresenter.this.operation = result.getOperation();
                    SetOperationEvent.fire(DatasetMetadataTabPresenter.this, result.getOperation());
                }
            });
        }
    }

    @Override
    public void retrieveDataset(String datasetIdentifier) {
        String urn = UrnUtils.generateUrn(UrnConstants.URN_SIEMAC_CLASS_DATASET_PREFIX, datasetIdentifier);
        dispatcher.execute(new GetDatasetVersionAction(urn), new WaitingAsyncCallbackHandlingError<GetDatasetVersionResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetVersionResult result) {
                getView().setDataset(result.getDatasetVersionDto());
            }
        });
    }

    @Override
    public void saveDataset(DatasetVersionDto datasetDto) {
        dispatcher.execute(new SaveDatasetVersionAction(datasetDto, datasetDto.getStatisticalOperation().getCode()), new WaitingAsyncCallbackHandlingError<SaveDatasetVersionResult>(this) {

            @Override
            public void onWaitSuccess(SaveDatasetVersionResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().datasetSaved());
                getView().setDataset(result.getSavedDatasetVersion());
            }
        });
    }

    //
    // LIFE CYCLE
    //

    @Override
    public void sendToProductionValidation(DatasetVersionDto dataset) {
        dispatcher.execute(new UpdateDatasetVersionProcStatusAction(dataset, LifeCycleActionEnum.SEND_TO_PRODUCTION_VALIDATION),
                new WaitingAsyncCallbackHandlingError<UpdateDatasetVersionProcStatusResult>(this) {

                    @Override
                    public void onWaitSuccess(UpdateDatasetVersionProcStatusResult result) {
                        ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().lifeCycleResourceSentToProductionValidation());
                        getView().setDataset(result.getDatasetVersionDto());
                    }
                });
    }

    @Override
    public void sendToDiffusionValidation(DatasetVersionDto dataset) {
        dispatcher.execute(new UpdateDatasetVersionProcStatusAction(dataset, LifeCycleActionEnum.SEND_TO_DIFFUSION_VALIDATION),
                new WaitingAsyncCallbackHandlingError<UpdateDatasetVersionProcStatusResult>(this) {

                    @Override
                    public void onWaitSuccess(UpdateDatasetVersionProcStatusResult result) {
                        ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().lifeCycleResourceSentToDiffusionValidation());
                        getView().setDataset(result.getDatasetVersionDto());
                    }
                });
    }

    @Override
    public void rejectValidation(DatasetVersionDto dataset) {
        dispatcher.execute(new UpdateDatasetVersionProcStatusAction(dataset, LifeCycleActionEnum.REJECT_VALIDATION), new WaitingAsyncCallbackHandlingError<UpdateDatasetVersionProcStatusResult>(this) {

            @Override
            public void onWaitSuccess(UpdateDatasetVersionProcStatusResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().lifeCycleResourceRejectValidation());
                getView().setDataset(result.getDatasetVersionDto());
            }
        });
    }

    @Override
    public void programPublication(DatasetVersionDto dataset) {
        dispatcher.execute(new UpdateDatasetVersionProcStatusAction(dataset, LifeCycleActionEnum.PUBLISH), new WaitingAsyncCallbackHandlingError<UpdateDatasetVersionProcStatusResult>(this) {

            @Override
            public void onWaitSuccess(UpdateDatasetVersionProcStatusResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().lifeCycleResourcePublish());
                getView().setDataset(result.getDatasetVersionDto());
            }
        });
    }

    @Override
    public void publish(DatasetVersionDto dataset) {
        dispatcher.execute(new UpdateDatasetVersionProcStatusAction(dataset, LifeCycleActionEnum.PUBLISH), new WaitingAsyncCallbackHandlingError<UpdateDatasetVersionProcStatusResult>(this) {

            @Override
            public void onWaitSuccess(UpdateDatasetVersionProcStatusResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().lifeCycleResourcePublish());
                getView().setDataset(result.getDatasetVersionDto());
            }
        });
    }

    @Override
    public void cancelProgrammedPublication(DatasetVersionDto dataset) {
        // TODO Auto-generated method stub

    }

    @Override
    public void version(DatasetVersionDto dataset, VersionTypeEnum versionType) {
        Builder builder = new Builder(dataset, LifeCycleActionEnum.VERSION);
        builder.versionType(versionType);
        dispatcher.execute(builder.build(), new WaitingAsyncCallbackHandlingError<UpdateDatasetVersionProcStatusResult>(this) {

            @Override
            public void onWaitSuccess(UpdateDatasetVersionProcStatusResult result) {
                ShowMessageEvent.fireSuccessMessage(DatasetMetadataTabPresenter.this, getMessages().lifeCycleResourceVersion());
                getView().setDataset(result.getDatasetVersionDto());
            }
        });
    }

    @Override
    public void previewData(DatasetVersionDto datasetVersionDto) {
        String url = MetamacPortalWebUtils.buildDatasetUrl(datasetVersionDto);
        Window.open(url, "_blank", "");
    }

    //
    // RELATED RESOURCES
    //

    @Override
    public void retrieveDatasetsForReplaces(int firstResult, int maxResults, MetamacWebCriteria criteria) {

        DatasetVersionWebCriteria versionableCriteria = new DatasetVersionWebCriteria(criteria.getCriteria());
        versionableCriteria.setOnlyLastVersion(false);

        dispatcher.execute(new GetDatasetVersionsAction(firstResult, maxResults, versionableCriteria), new WaitingAsyncCallbackHandlingError<GetDatasetVersionsResult>(this) {

            @Override
            public void onWaitSuccess(GetDatasetVersionsResult result) {
                getView().setDatasetsForReplaces(result);
            }
        });
    }

    @Override
    public void retrieveDsdsForRelatedDsd(int firstResult, int maxResults, DsdWebCriteria criteria) {
        dispatcher.execute(new GetDsdsPaginatedListAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetDsdsPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetDsdsPaginatedListResult result) {
                getView().setDsdsForRelatedDsd(result);
            }
        });
    }

    @Override
    public void retrieveCodesForGeographicalGranularities(int firstResult, int maxResults, MetamacWebCriteria criteria) {
        dispatcher.execute(new GetGeographicalGranularitiesListAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetGeographicalGranularitiesListResult>(this) {

            @Override
            public void onWaitSuccess(GetGeographicalGranularitiesListResult result) {
                getView().setCodesForGeographicalGranularities(result);
            }
        });
    }

    @Override
    public void retrieveTemporalCodesForField(int firstResult, int maxResults, MetamacWebCriteria webCriteria, final DatasetMetadataExternalField field) {
        dispatcher.execute(new GetTemporalGranularitiesListAction(firstResult, maxResults, webCriteria), new WaitingAsyncCallbackHandlingError<GetTemporalGranularitiesListResult>(this) {

            @Override
            public void onWaitSuccess(GetTemporalGranularitiesListResult result) {
                getView().setTemporalCodesForField(result, field);
            }
        });
    }

    @Override
    public void retrieveConceptSchemesForStatisticalUnit(int firstResult, int maxResults, MetamacWebCriteria criteria) {
        dispatcher.execute(new GetConceptSchemesPaginatedListAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetConceptSchemesPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetConceptSchemesPaginatedListResult result) {
                getView().setConceptSchemesForStatisticalUnit(result);
            }
        });;
    }

    @Override
    public void retrieveConceptsForStatisticalUnit(int firstResult, int maxResults, ItemSchemeWebCriteria criteria) {
        dispatcher.execute(new GetConceptsPaginatedListAction(firstResult, maxResults, criteria), new WaitingAsyncCallbackHandlingError<GetConceptsPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetConceptsPaginatedListResult result) {
                getView().setConceptsForStatisticalUnit(result);
            }
        });;
    }

    @Override
    public void retrieveStatisticalOperationsForDsdSelection() {
        dispatcher.execute(new GetStatisticalOperationsPaginatedListAction(0, Integer.MAX_VALUE, null), new WaitingAsyncCallbackHandlingError<GetStatisticalOperationsPaginatedListResult>(this) {

            @Override
            public void onWaitSuccess(GetStatisticalOperationsPaginatedListResult result) {
                getView().setStatisticalOperationsForDsdSelection(result.getOperationsList(), operation);
            }
        });
    }
}