package org.siemac.metamac.statistical.resources.web.client.view.handlers;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceProcStatusEnum;

import com.gwtplatform.mvp.client.UiHandlers;

public interface LifeCycleUiHandlers extends UiHandlers {

    void sendToProductionValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void sendToDiffusionValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void rejectValidation(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    //void sendToPendingPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    //void programPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    //void cancelProgrammedPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void publish(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    //void archive(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void version(String urn, VersionTypeEnum versionType);

}
