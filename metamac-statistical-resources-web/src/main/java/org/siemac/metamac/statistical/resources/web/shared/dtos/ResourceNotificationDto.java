package org.siemac.metamac.statistical.resources.web.shared.dtos;

import java.util.Date;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

public class ResourceNotificationDto extends BaseResourceNotificationDto<DatasetVersionDto> {

    private static final long serialVersionUID = -6177937293345892429L;

    public static class Builder {

        private final LifeCycleActionEnum lifeCycleAction;
        private DatasetVersionDto         datasetVersionDto;
        private final DatasetVersionDto   previousDatasetVersionDto;
        private String                    reasonOfRejection;
        private Date                      programmedPublicationDate;

        public Builder(DatasetVersionDto previousDatasetVersionDto, LifeCycleActionEnum lifeCycleAction) {
            this.previousDatasetVersionDto = previousDatasetVersionDto;
            this.lifeCycleAction = lifeCycleAction;
        }

        public Builder datasetVersionDto(DatasetVersionDto datasetVersionDto) {
            this.datasetVersionDto = datasetVersionDto;
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

        public ResourceNotificationDto build() {
            return new ResourceNotificationDto(this);
        };
    }

    public ResourceNotificationDto(Builder builder) {
        lifeCycleAction = builder.lifeCycleAction;
        datasetVersionDto = builder.datasetVersionDto;
        previousDatasetVersionDto = builder.previousDatasetVersionDto;
        reasonOfRejection = builder.reasonOfRejection;
        programmedPublicationDate = builder.programmedPublicationDate;
    }
}
