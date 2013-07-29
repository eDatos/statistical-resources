package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.constants.StatisticalResourceWebConstants;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.search.SearchSingleCommonConfigurationWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchExternalItemLinkItem;

import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public class StatisticalResourceCommonMetadataEditionForm extends NavigationEnabledDynamicForm {

    private ExternalItemDto                       commonConfigurationDto;
    private SearchSingleCommonConfigurationWindow searchCommonConfigurationWindow;

    private SearchExternalItemLinkItem            commonConfiguration;

    private StatisticalResourceUiHandlers         uiHandlers;

    public StatisticalResourceCommonMetadataEditionForm() {
        super(getConstants().formCommonMetadata());

        commonConfiguration = createCommonMetadataItem(StatisticalResourceDS.COMMON_METADATA, getConstants().commonMetadata());

        setFields(commonConfiguration);
    }

    private void setCommonConfiguration(ExternalItemDto commonConfig) {
        StatisticalResourcesFormUtils.setExternalItemValue(getItem(StatisticalResourceDS.COMMON_METADATA), commonConfig);
        commonConfigurationDto = commonConfig;
    }

    public void setCommonConfigurationsList(List<ExternalItemDto> commonConfigurations) {
        if (searchCommonConfigurationWindow != null) {
            searchCommonConfigurationWindow.setResources(commonConfigurations);
            searchCommonConfigurationWindow.refreshSourcePaginationInfo(0, commonConfigurations.size(), commonConfigurations.size());
        }
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        setCommonConfiguration(siemacMetadataStatisticalResourceDto.getCommonMetadata());
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        siemacMetadataStatisticalResourceDto.setCommonMetadata(commonConfigurationDto);
        return siemacMetadataStatisticalResourceDto;
    }

    private SearchExternalItemLinkItem createCommonMetadataItem(String name, String title) {

        final SearchExternalItemLinkItem dsdItem = new SearchExternalItemLinkItem(name, title);
        dsdItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchCommonConfigurationWindow = new SearchSingleCommonConfigurationWindow(getConstants().resourceSelection(), StatisticalResourceWebConstants.FORM_LIST_MAX_RESULTS,
                        new SearchPaginatedAction<CommonConfigurationWebCriteria>() {

                            @Override
                            public void retrieveResultSet(int firstResult, int maxResults, CommonConfigurationWebCriteria criteria) {
                                uiHandlers.retrieveCommonConfigurations(criteria);
                            }

                        });

                uiHandlers.retrieveCommonConfigurations(searchCommonConfigurationWindow.getWebCriteria());

                searchCommonConfigurationWindow.setSaveAction(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        ExternalItemDto selectedResource = searchCommonConfigurationWindow.getSelectedResource();
                        searchCommonConfigurationWindow.markForDestroy();
                        setCommonConfiguration(selectedResource);
                        validate(false);
                    }
                });
            }
        });
        return dsdItem;
    }

    public void setUiHandlers(StatisticalResourceUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
