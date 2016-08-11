package org.siemac.metamac.statistical.resources.core.utils.mocks;

import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class DatasetMock extends Dataset {

    private static final long serialVersionUID = -9190715034478060706L;

    private String            maintainerCode;
    private Integer           sequentialId;

    public DatasetMock() {
        maintainerCode = StatisticalResourcesPersistedDoMocks.mockString(10);
    }

    public void setMaintainerCode(String maintainerCode) {
        this.maintainerCode = maintainerCode;
    }

    public String getMaintainerCode() {
        return maintainerCode;
    }

    public void setStatisticalOperationCode(String statisticalOperationCode) {
        getIdentifiableStatisticalResource().setStatisticalOperation(StatisticalResourcesPersistedDoMocks.mockStatisticalOperationExternalItem(statisticalOperationCode));
    }

    public void setSequentialId(Integer sequentialId) {
        this.sequentialId = sequentialId;
    }

    public Integer getSequentialId() {
        return sequentialId;
    }

    public void setCode(String code) {
        getIdentifiableStatisticalResource().setCode(code);
    }

    @Override
    public IdentifiableStatisticalResource getIdentifiableStatisticalResource() {
        IdentifiableStatisticalResource resource = super.getIdentifiableStatisticalResource();
        if (resource == null) {
            setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        }
        return super.getIdentifiableStatisticalResource();
    }
}
