package org.siemac.metamac.statistical.resources.web.shared.dtos;

import java.util.Date;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

public class ResourceNotificationBaseDto extends BaseResourceNotificationDto<DatasetVersionBaseDto> {

    private static final long serialVersionUID = 4597719959970093399L;

    public static class Builder {

        private final LifeCycleActionEnum   lifeCycleAction;
        private DatasetVersionBaseDto       datasetVersionBaseDto;
        private final DatasetVersionBaseDto previousDatasetVersionDto;
        private String                      reasonOfRejection;
        private Date                        programmedPublicationDate;

        public Builder(DatasetVersionBaseDto previousDatasetVersionBaseDto, LifeCycleActionEnum lifeCycleAction) {
            this.previousDatasetVersionDto = previousDatasetVersionBaseDto;
            this.lifeCycleAction = lifeCycleAction;
        }

        public Builder datasetVersionDto(DatasetVersionBaseDto datasetVersionBaseDto) {
            this.datasetVersionBaseDto = datasetVersionBaseDto;
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
        datasetVersionDto = builder.datasetVersionBaseDto;
        previousDatasetVersionDto = builder.previousDatasetVersionDto;
        reasonOfRejection = builder.reasonOfRejection;
        programmedPublicationDate = builder.programmedPublicationDate;
    }
}