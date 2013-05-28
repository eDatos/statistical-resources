package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.model.ds.StatisticalResourceDS;
import org.siemac.metamac.statistical.resources.web.client.widgets.windows.SearchSingleCommonConfigurationWindow;
import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.web.common.client.utils.ExternalItemUtils;
import org.siemac.metamac.web.common.client.widgets.actions.search.SearchPaginatedAction;
import org.siemac.metamac.web.common.client.widgets.form.fields.SearchViewTextItem;
import org.siemac.metamac.web.common.client.widgets.form.fields.ViewTextItem;

import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;

public abstract class StatisticalResourceCommonMetadataEditionForm extends NavigationEnabledDynamicForm {
    final int FIRST_RESULT = 0;
    final int MAX_RESULTS = 8;
    
    private ExternalItemDto              commonConfigurationDto;
    private SearchSingleCommonConfigurationWindow     searchCommonConfigurationWindow;
    
    private SearchViewTextItem commonConfiguration;
    
    public StatisticalResourceCommonMetadataEditionForm() {
        super(getConstants().formCommonMetadata());
        
        commonConfiguration = createCommonMetadataItem(StatisticalResourceDS.COMMON_METADATA, getConstants().commonMetadata());
        commonConfiguration.setRequired(true);
       
        setFields(commonConfiguration);
    }

    private void setCommonConfiguration(ExternalItemDto commonConfig) {
        setValue(StatisticalResourceDS.COMMON_METADATA, ExternalItemUtils.getExternalItemName(commonConfig));
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
    
    private SearchViewTextItem createCommonMetadataItem(String name, String title) {

        final SearchViewTextItem dsdItem = new SearchViewTextItem(name, title);
        dsdItem.getSearchIcon().addFormItemClickHandler(new FormItemClickHandler() {

            @Override
            public void onFormItemClick(FormItemIconClickEvent event) {

                searchCommonConfigurationWindow = new SearchSingleCommonConfigurationWindow(getConstants().resourceSelection(), MAX_RESULTS, new SearchPaginatedAction<CommonConfigurationWebCriteria>() {

                    @Override
                    public void retrieveResultSet(int firstResult, int maxResults, CommonConfigurationWebCriteria criteria) {
                        retrieveCommonConfigurations(criteria);
                    }

                });

                retrieveCommonConfigurations(searchCommonConfigurationWindow.getWebCriteria());
                
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
    
    protected abstract void retrieveCommonConfigurations(CommonConfigurationWebCriteria criteria);
    
}
