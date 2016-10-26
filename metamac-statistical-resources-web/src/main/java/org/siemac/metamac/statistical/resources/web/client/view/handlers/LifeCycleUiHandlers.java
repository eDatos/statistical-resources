package org.siemac.metamac.statistical.resources.web.client.view.handlers;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;

import com.gwtplatform.mvp.client.UiHandlers;

public interface LifeCycleUiHandlers extends UiHandlers {

    void sendToProductionValidation(String urn);
    void sendToDiffusionValidation(String urn);
    void rejectValidation(String urn);
    void publish(String urn);
    // void archive(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void version(String urn, VersionTypeEnum versionType);

}
