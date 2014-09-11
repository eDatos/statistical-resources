package org.siemac.metamac.statistical.resources.web.shared.dtos;

import java.util.Date;

import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceBaseDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

public class ResourceNotificationBaseDto extends BaseResourceNotificationDto<LifeCycleStatisticalResourceBaseDto> {

    private static final long serialVersionUID = 4597719959970093399L;

    public static class Builder {

        private final LifeCycleActionEnum                 lifeCycleAction;
        private final StatisticalResourceTypeEnum         statisticalResourceType;
        private LifeCycleStatisticalResourceBaseDto       updatedResource;
        private final LifeCycleStatisticalResourceBaseDto previousResource;
        private String                                    reasonOfRejection;
        private Date                                      programmedPublicationDate;

        public Builder(LifeCycleStatisticalResourceBaseDto previousResource, StatisticalResourceTypeEnum statisticalResourceType, LifeCycleActionEnum lifeCycleAction) {
            this.previousResource = previousResource;
            this.statisticalResourceType = statisticalResourceType;
            this.lifeCycleAction = lifeCycleAction;
        }

        public Builder updatedResource(LifeCycleStatisticalResourceBaseDto updatedResource) {
            this.updatedResource = updatedResource;
            return this;
        }

        public Builder programmedPublicationDate(Date programmedPublicationDate) {
            this.programmedPublicationDate = programmedPublicationDate;
            return this;
        }

        public Builder reasonOfRejection(String reasonOfRejection) {
            this.reasonOfRejection = reasonOfRejection;
            return this;
        }

        public ResourceNotificationBaseDto build() {
            return new ResourceNotificationBaseDto(this);
        };
    }

    public ResourceNotificationBaseDto(Builder builder) {
        lifeCycleAction = builder.lifeCycleAction;
        statisticalResourceType = builder.statisticalResourceType;
        updatedResource = builder.updatedResource;
        previousResource = builder.previousResource;
        reasonOfRejection = builder.reasonOfRejection;
        programmedPublicationDate = builder.programmedPublicationDate;
    }
}