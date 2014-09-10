package org.siemac.metamac.statistical.resources.web.shared.dtos;

import java.io.Serializable;
import java.util.Date;

import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasetVersionDto;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

public class NotificationDto implements Serializable {

    private static final long   serialVersionUID = 2830084867900779273L;

    private LifeCycleActionEnum lifeCycleAction;
    private DatasetVersionDto   datasetVersionDto;
    private DatasetVersionDto   previousDatasetVersionDto;
    private String              reasonOfRejection;
    private Date                programmedPublicationDate;

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

        public NotificationDto build() {
            return new NotificationDto(this);
        }
    }

    public NotificationDto(Builder builder) {
        this.lifeCycleAction = builder.lifeCycleAction;
        this.datasetVersionDto = builder.datasetVersionDto;
        this.previousDatasetVersionDto = builder.previousDatasetVersionDto;
        this.reasonOfRejection = builder.reasonOfRejection;
        this.programmedPublicationDate = builder.programmedPublicationDate;
    }

    public LifeCycleActionEnum getLifeCycleAction() {
        return lifeCycleAction;
    }

    public void setLifeCycleAction(LifeCycleActionEnum lifeCycleAction) {
        this.lifeCycleAction = lifeCycleAction;
    }

    public DatasetVersionDto getDatasetVersionDto() {
        return datasetVersionDto;
    }

    public void setDatasetVersionDto(DatasetVersionDto datasetVersionDto) {
        this.datasetVersionDto = datasetVersionDto;
    }

    public DatasetVersionDto getPreviousDatasetVersionDto() {
        return previousDatasetVersionDto;
    }

    public void setPreviousDatasetVersionDto(DatasetVersionDto previousDatasetVersionDto) {
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
