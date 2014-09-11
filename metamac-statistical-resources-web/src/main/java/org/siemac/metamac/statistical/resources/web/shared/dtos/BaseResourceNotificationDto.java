package org.siemac.metamac.statistical.resources.web.shared.dtos;

import java.io.Serializable;
import java.util.Date;

import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

public class BaseResourceNotificationDto<T extends IdentifiableStatisticalResourceDto> implements Serializable {

    private static final long     serialVersionUID = 2830084867900779273L;

    protected LifeCycleActionEnum lifeCycleAction;
    protected T                   datasetVersionDto;
    protected T                   previousDatasetVersionDto;
    protected String              reasonOfRejection;
    protected Date                programmedPublicationDate;

    public LifeCycleActionEnum getLifeCycleAction() {
        return lifeCycleAction;
    }

    public void setLifeCycleAction(LifeCycleActionEnum lifeCycleAction) {
        this.lifeCycleAction = lifeCycleAction;
    }

    public T getDatasetVersionDto() {
        return datasetVersionDto;
    }

    public void setDatasetVersionDto(T datasetVersionDto) {
        this.datasetVersionDto = datasetVersionDto;
    }

    public T getPreviousDatasetVersionDto() {
        return previousDatasetVersionDto;
    }

    public void setPreviousDatasetVersionDto(T previousDatasetVersionDto) {
        this.previousDatasetVersionDto = previousDatasetVersionDto;
    }

    public String getReasonOfRejection() {
        return reasonOfRejection;
    }

    public void setReasonOfRejection(String reasonOfRejection) {
        this.reasonOfRejection = reasonOfRejection;
    }

    public Date getProgrammedPublicationDate() {
        return programmedPublicationDate;
    }

    public void setProgrammedPublicationDate(Date programmedPublicationDate) {
        this.programmedPublicationDate = programmedPublicationDate;
    }
}
