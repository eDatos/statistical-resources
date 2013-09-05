package org.siemac.metamac.statistical.resources.core.utils.mocks;

import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;


public class PublicationVersionMock extends PublicationVersion {
    
    private Integer sequentialId;
    
    public static PublicationVersionMock buildBasicSingleVersionWithSequence(int sequenceId) {
        PublicationVersionMock instance = new PublicationVersionMock();
        instance.setSequentialId(sequenceId);
        instance.getSiemacMetadataStatisticalResource().setLastVersion(true);
        instance.setVersionLogic(StatisticalResourcesVersionUtils.INITIAL_VERSION);
        return instance;
    }
    
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
    
    public void setMaintainerCode(String maintainerCode) {
        getSiemacMetadataStatisticalResource().setMaintainer(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem(maintainerCode));
    }
    
    public void setPublication(PublicationMock publication) {
        super.setPublication(publication);
        if (publication.getMaintainerCode() != null) {
            setMaintainerCode(publication.getMaintainerCode());
        }
    }
    
    @Override
    public SiemacMetadataStatisticalResource getSiemacMetadataStatisticalResource() {
        if (super.getSiemacMetadataStatisticalResource() == null) {
            setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
        }
        return super.getSiemacMetadataStatisticalResource();
    }
}