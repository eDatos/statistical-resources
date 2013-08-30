package org.siemac.metamac.statistical.resources.core.utils.mocks;

import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;


public class DatasetVersionMock extends DatasetVersion {
    
    private Integer sequentialId;
    
    public Integer getSequentialId() {
        return sequentialId;
    }
    
    public void setSequentialId(Integer sequentialId) {
        this.sequentialId = sequentialId;
    }
    
    public void setVersionLogic(String version) {
        getSiemacMetadataStatisticalResource().setVersionLogic(version);
    }
    
    public void setStatisticalOperationCode(String operationCode) {
        getSiemacMetadataStatisticalResource().setStatisticalOperation(StatisticalResourcesPersistedDoMocks.mockStatisticalOperationInstanceExternalItem(operationCode));
    }
    
    @Override
    public SiemacMetadataStatisticalResource getSiemacMetadataStatisticalResource() {
        if (super.getSiemacMetadataStatisticalResource() == null) {
            setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
        }
        return super.getSiemacMetadataStatisticalResource();
    }
}
