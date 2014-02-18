package org.siemac.metamac.statistical.resources.web.client.widgets.forms;

import static org.siemac.metamac.statistical.resources.web.client.StatisticalResourcesWeb.getConstants;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.web.client.base.view.handlers.StatisticalResourceUiHandlers;
import org.siemac.metamac.statistical.resources.web.client.model.ds.SiemacMetadataDS;
import org.siemac.metamac.statistical.resources.web.client.utils.CommonUtils;
import org.siemac.metamac.web.common.client.model.ds.ExternalItemDS;
import org.siemac.metamac.web.common.client.utils.CustomRequiredValidator;
import org.siemac.metamac.web.common.client.utils.RecordUtils;
import org.siemac.metamac.web.common.client.view.handlers.BaseUiHandlers;
import org.siemac.metamac.web.common.client.widgets.form.fields.external.SearchSingleCommonConfigurationItem;
import org.siemac.metamac.web.common.client.widgets.form.utils.FormUtils;
import org.siemac.metamac.web.common.shared.criteria.CommonConfigurationRestCriteria;

public class SiemacMetadataCommonMetadataEditionForm extends NavigationEnabledDynamicForm {

    protected ProcStatusEnum                    procStatus;

    private SearchSingleCommonConfigurationItem commonConfiguration;

    private StatisticalResourceUiHandlers       uiHandlers;

    public SiemacMetadataCommonMetadataEditionForm() {
        super(getConstants().formCommonMetadata());

        commonConfiguration = createSearchCommonConfigurationItem(SiemacMetadataDS.COMMON_METADATA, getConstants().commonMetadata());

        commonConfiguration.setValidators(new CustomRequiredValidator() {

            @Override
            protected boolean condition(Object value) {
                ExternalItemDto externalItemValue = FormUtils.getJsObjectAttributeAsTypedObject(value, ExternalItemDS.DTO);
                return CommonUtils.isResourceInProductionValidationOrGreaterProcStatus(procStatus) ? externalItemValue != null : true;
            }
        });

        setFields(commonConfiguration);
    }

    private SearchSingleCommonConfigurationItem createSearchCommonConfigurationItem(final String name, String title) {
        return new SearchSingleCommonConfigurationItem(name, title) {

            @Override
            protected void retrieveCommonConfigurations(CommonConfigurationRestCriteria criteria) {
                uiHandlers.retrieveCommonConfigurations(criteria);
            }
        };
    }

    public void setCommonConfigurationsList(List<ExternalItemDto> commonConfigurations) {
        commonConfiguration.setCommonConfigurationsList(commonConfigurations);
    }

    public void setSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        this.procStatus = siemacMetadataStatisticalResourceDto.getProcStatus();

        setCommonConfiguration(siemacMetadataStatisticalResourceDto.getCommonMetadata());
    }

    private void setCommonConfiguration(ExternalItemDto commonConfig) {
        setValue(SiemacMetadataDS.COMMON_METADATA, commonConfig);
    }

    public SiemacMetadataStatisticalResourceDto getSiemacMetadataStatisticalResourceDto(SiemacMetadataStatisticalResourceDto siemacMetadataStatisticalResourceDto) {
        siemacMetadataStatisticalResourceDto.setCommonMetadata(getValueAsExternalItemDto(SiemacMetadataDS.COMMON_METADATA));
        return siemacMetadataStatisticalResourceDto;
    }

    public void setUiHandlers(StatisticalResourceUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public BaseUiHandlers getBaseUiHandlers() {
        return uiHandlers;
    }
}
