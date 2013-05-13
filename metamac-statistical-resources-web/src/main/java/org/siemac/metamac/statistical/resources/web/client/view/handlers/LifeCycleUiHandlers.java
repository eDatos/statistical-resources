package org.siemac.metamac.statistical.resources.web.client.view.handlers;

import org.siemac.metamac.core.common.enume.domain.VersionTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;

import com.gwtplatform.mvp.client.UiHandlers;

public interface LifeCycleUiHandlers extends UiHandlers {

    void sendToProductionValidation(String urn, ProcStatusEnum currentProcStatus);
    void sendToDiffusionValidation(String urn, ProcStatusEnum currentProcStatus);
    void rejectValidation(String urn, ProcStatusEnum currentProcStatus);
    // void sendToPendingPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    // void programPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    // void cancelProgrammedPublication(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void publish(String urn, ProcStatusEnum currentProcStatus);
    // void archive(String urn, StatisticalResourceProcStatusEnum currentProcStatus);
    void version(String urn, VersionTypeEnum versionType);

}
