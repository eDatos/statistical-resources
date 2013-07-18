package org.siemac.metamac.statistical.resources.web.client.base.view;

import org.siemac.metamac.statistical.resources.web.client.base.presenter.StatisticalResourceMetadataBasePresenter;
import org.siemac.metamac.statistical.resources.web.client.base.utils.SiemacMetadataExternalField;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceCommonMetadataEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceLanguageEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceProductionDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourcePublicationDescriptorsEditionForm;
import org.siemac.metamac.statistical.resources.web.client.widgets.forms.StatisticalResourceThematicContentClassifiersEditionForm;
import org.siemac.metamac.statistical.resources.web.shared.external.GetCommonMetadataConfigurationsListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetLanguagesCodesResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetOrganisationUnitSchemesPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetOrganisationUnitsPaginatedListResult;
import org.siemac.metamac.statistical.resources.web.shared.external.GetStatisticalOperationInstancesPaginatedListResult;

import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public abstract class StatisticalResourceMetadataBaseViewImpl<H extends StatisticalResourceUiHandlers> extends ViewWithUiHandlers<H>
        implements
            StatisticalResourceMetadataBasePresenter.StatisticalResourceMetadataBaseView {

    @Override
    public void setCommonConfigurations(GetCommonMetadataConfigurationsListResult result) {
        getCommonMetadataEditionForm().setCommonConfigurationsList(result.getResults());
    }

    @Override
    public void setStatisticalOperationInstances(GetStatisticalOperationInstancesPaginatedListResult result) {
        getThematicContentClassifiersEditionForm().setStatisticalOperationInstances(result.getOperationInstancesList(), result.getFirstResultOut(), result.getTotalResults());
    }

    @Override
    public void setOrganisationUnitSchemesForField(GetOrganisationUnitSchemesPaginatedListResult result, SiemacMetadataExternalField field) {
        switch (field) {
            case CREATOR:
                getProductionDescriptorsEditionForm().setOrganisationUnitSchemesForCreator(result.getOrganisationUnitSchemes(), result.getFirstResultOut(), result.getTotalResults());
                break;
            case CONTRIBUTOR:
                getProductionDescriptorsEditionForm().setOrganisationUnitSchemesForContributor(result.getOrganisationUnitSchemes(), result.getFirstResultOut(), result.getTotalResults());
                break;
            case PUBLISHER:
                getPublicationDescriptorsEditionForm().setOrganisationUnitSchemesForPublisher(result.getOrganisationUnitSchemes(), result.getFirstResultOut(), result.getTotalResults());
                break;
            case PUBLISHER_CONTRIBUTOR:
                getPublicationDescriptorsEditionForm().setOrganisationUnitSchemesForPublisherContributor(result.getOrganisationUnitSchemes(), result.getFirstResultOut(), result.getTotalResults());
                break;
            case MEDIATOR:
                getPublicationDescriptorsEditionForm().setOrganisationUnitSchemesForMediator(result.getOrganisationUnitSchemes(), result.getFirstResultOut(), result.getTotalResults());
                break;
        }
    }

    @Override
    public void setOrganisationUnitsForField(GetOrganisationUnitsPaginatedListResult result, SiemacMetadataExternalField field) {
        switch (field) {
            case CREATOR:
                getProductionDescriptorsEditionForm().setOrganisationUnitsForCreator(result.getOrganisationUnits(), result.getFirstResultOut(), result.getTotalResults());
                break;
            case CONTRIBUTOR:
                getProductionDescriptorsEditionForm().setOrganisationUnitsForContributor(result.getOrganisationUnits(), result.getFirstResultOut(), result.getTotalResults());
                break;
            case PUBLISHER:
                getPublicationDescriptorsEditionForm().setOrganisationUnitsForPublisher(result.getOrganisationUnits(), result.getFirstResultOut(), result.getTotalResults());
                break;
            case PUBLISHER_CONTRIBUTOR:
                getPublicationDescriptorsEditionForm().setOrganisationUnitsForPublisherContributor(result.getOrganisationUnits(), result.getFirstResultOut(), result.getTotalResults());
                break;
            case MEDIATOR:
                getPublicationDescriptorsEditionForm().setOrganisationUnitsForMediator(result.getOrganisationUnits(), result.getFirstResultOut(), result.getTotalResults());
                break;
        }
    }

    @Override
    public void setLanguagesCodes(GetLanguagesCodesResult result) {
        getLanguageEditionForm().setCodesForLanguages(result.getLanguagesCodes(), result.getFirstResultOut(), result.getTotalResults());
    }

    protected abstract StatisticalResourceProductionDescriptorsEditionForm getProductionDescriptorsEditionForm();
    protected abstract StatisticalResourcePublicationDescriptorsEditionForm getPublicationDescriptorsEditionForm();
    protected abstract StatisticalResourceCommonMetadataEditionForm getCommonMetadataEditionForm();
    protected abstract StatisticalResourceThematicContentClassifiersEditionForm getThematicContentClassifiersEditionForm();
    protected abstract StatisticalResourceLanguageEditionForm getLanguageEditionForm();
}
