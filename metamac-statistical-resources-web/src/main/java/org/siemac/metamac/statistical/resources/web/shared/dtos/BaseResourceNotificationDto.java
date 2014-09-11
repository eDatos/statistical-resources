package org.siemac.metamac.statistical.resources.web.shared.dtos;

import java.io.Serializable;
import java.util.Date;

import org.siemac.metamac.statistical.resources.core.dto.IdentifiableStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

public class BaseResourceNotificationDto<T extends IdentifiableStatisticalResourceDto> implements Serializable {

    private static final long             serialVersionUID = 2830084867900779273L;

    protected LifeCycleActionEnum         lifeCycleAction;
    protected StatisticalResourceTypeEnum statisticalResourceType;
    protected T                           updatedResource;
    protected T                           previousResource;
    protected String                      reasonOfRejection;
    protected Date                        programmedPublicationDate;

    public LifeCycleActionEnum getLifeCycleAction() {
        return lifeCycleAction;
    }

    public void setLifeCycleAction(LifeCycleActionEnum lifeCycleAction) {
        this.lifeCycleAction = lifeCycleAction;
    }

    public StatisticalResourceTypeEnum getStatisticalResourceType() {
        return statisticalResourceType;
    }

    public void setStatisticalResourceType(StatisticalResourceTypeEnum statisticalResourceType) {
        this.statisticalResourceType = statisticalResourceType;
    }

    public T getUpdatedResource() {
        return updatedResource;
    }

    public void setUpdatedResource(T updatedResource) {
        this.updatedResource = updatedResource;
    }

    public T getPreviousResource() {
        return previousResource;
    }

    public void setPreviousResource(T previousResource) {
        this.previousResource = previousResource;
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
