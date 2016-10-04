package org.siemac.metamac.statistical.resources.web.server.dtos;

import java.io.Serializable;
import java.util.Date;

import org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.notices.v1_0.domain.enume.MetamacRolesEnum;
import org.siemac.metamac.statistical.resources.web.client.enums.LifeCycleActionEnum;

public class GroupedNotificationDto implements Serializable {

    private static final long   serialVersionUID = -481835894846765464L;

    private String              statisticalOperationUrn;
    private ResourceInternal[]  resources;
    private MetamacRolesEnum[]  roles;
    private String[]            receiversUsernames;
    private String              reasonOfRejection;
    private LifeCycleActionEnum lifeCycleAction;

    public static class Builder {

        private String                    statisticalOperationUrn;
        private final ResourceInternal[]  resources;
        private final LifeCycleActionEnum lifeCycleAction;
        private MetamacRolesEnum[]        roles;
        private String[]                  receiversUsernames;
        private String                    reasonOfRejection;
        private Date                      programmedPublicationDate;

        public Builder(ResourceInternal[] resources, LifeCycleActionEnum lifeCycleAction) {
            this.resources = resources;
            this.lifeCycleAction = lifeCycleAction;
        }
        
        public Builder statisticalOperationUrn(String statisticalOperationUrn) {
            this.statisticalOperationUrn = statisticalOperationUrn;
            return this;
        }

        public Builder roles(MetamacRolesEnum[] roles) {
            this.roles = roles;
            return this;
        }

        public Builder receiversUsernames(String[] receiversUsernames) {
            this.receiversUsernames = receiversUsernames;
            return this;
        }

        public Builder reasonOfRejection(String reasonOfRejection) {
            this.reasonOfRejection = reasonOfRejection;
            return this;
        }


        public GroupedNotificationDto build() {
            return new GroupedNotificationDto(this);
        }
    }

    public GroupedNotificationDto(Builder builder) {
        this.statisticalOperationUrn = builder.statisticalOperationUrn;
        this.resources = builder.resources;
        this.lifeCycleAction = builder.lifeCycleAction;
        this.roles = builder.roles;
        this.receiversUsernames = builder.receiversUsernames;
        this.reasonOfRejection = builder.reasonOfRejection;
    }

    public String getStatisticalOperationUrn() {
        return statisticalOperationUrn;
    }

    public void setStatisticalOperationUrn(String statisticalOperationUrn) {
        this.statisticalOperationUrn = statisticalOperationUrn;
    }

    public ResourceInternal[] getResources() {
        return resources;
    }

    public void setResources(ResourceInternal[] resources) {
        this.resources = resources;
    }

    public MetamacRolesEnum[] getRoles() {
        return roles;
    }

    public void setRoles(MetamacRolesEnum[] roles) {
        this.roles = roles;
    }

    public String[] getReceiversUsernames() {
        return receiversUsernames;
    }

    public void setReceiversUsernames(String[] receiversUsernames) {
        this.receiversUsernames = receiversUsernames;
    }

    public String getReasonOfRejection() {
        return reasonOfRejection;
    }

    public void setReasonOfRejection(String reasonOfRejection) {
        this.reasonOfRejection = reasonOfRejection;
    }

    public LifeCycleActionEnum getLifeCycleAction() {
        return lifeCycleAction;
    }

    public void setLifeCycleAction(LifeCycleActionEnum lifeCycleAction) {
        this.lifeCycleAction = lifeCycleAction;
    }
}
