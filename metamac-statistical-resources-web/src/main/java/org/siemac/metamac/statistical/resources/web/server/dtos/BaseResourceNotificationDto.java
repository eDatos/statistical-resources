package org.siemac.metamac.statistical.resources.web.server.dtos;

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

    public StatisticalResourceTypeEnum getStatisticalResourceType() {
        return statisticalResourceType;
    }

    public T getUpdatedResource() {
        return updatedResource;
    }

    public T getPreviousResource() {
        return previousResource;
    }

    public String getReasonOfRejection() {
        return reasonOfRejection;
    }

    public Date getProgrammedPublicationDate() {
        return programmedPublicationDate;
    }
}
