package org.siemac.metamac.statistical.resources.web.server.dtos;

import java.util.Date;

import org.siemac.metamac.statistical.resources.core.dto.LifeCycleStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

public class ResourceNotificationDto extends BaseResourceNotificationDto<LifeCycleStatisticalResourceDto> {

    private static final long serialVersionUID = -6177937293345892429L;

    public static class Builder {

        private final LifeCycleActionEnum             lifeCycleAction;
        private final StatisticalResourceTypeEnum     statisticalResourceType;
        private LifeCycleStatisticalResourceDto       updatedResource;
        private final LifeCycleStatisticalResourceDto previousResource;
        private String                                reasonOfRejection;
        private Date                                  programmedPublicationDate;

        public Builder(LifeCycleStatisticalResourceDto previousResource, StatisticalResourceTypeEnum statisticalResourceType, LifeCycleActionEnum lifeCycleAction) {
            this.previousResource = previousResource;
            this.statisticalResourceType = statisticalResourceType;
            this.lifeCycleAction = lifeCycleAction;
        }

        public Builder updatedResource(LifeCycleStatisticalResourceDto updatedResource) {
            this.updatedResource = updatedResource;
            return this;
        }

        public Builder reasonOfRejection(String reasonOfRejection) {
            this.reasonOfRejection = reasonOfRejection;
            return this;
        }

        public ResourceNotificationDto build() {
            return new ResourceNotificationDto(this);
        };
    }

    public ResourceNotificationDto(Builder builder) {
        lifeCycleAction = builder.lifeCycleAction;
        statisticalResourceType = builder.statisticalResourceType;
        updatedResource = builder.updatedResource;
        previousResource = builder.previousResource;
        reasonOfRejection = builder.reasonOfRejection;
        programmedPublicationDate = builder.programmedPublicationDate;
    }
}
