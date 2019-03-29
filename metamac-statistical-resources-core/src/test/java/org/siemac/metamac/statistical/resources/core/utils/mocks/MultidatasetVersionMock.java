package org.siemac.metamac.statistical.resources.core.utils.mocks;

import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.Multidataset;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class MultidatasetVersionMock extends MultidatasetVersion {

    private static final long serialVersionUID = 3117920262758220340L;

    private Integer           sequentialId;

    public static MultidatasetVersionMock buildBasicSingleVersionWithSequence(int sequenceId) {
        MultidatasetVersionMock instance = new MultidatasetVersionMock();
        instance.setSequentialId(sequenceId);
        instance.getSiemacMetadataStatisticalResource().setLastVersion(true);
        instance.setVersionLogic(StatisticalResourcesVersionUtils.getInitialVersion());
        return instance;
    }

    public static MultidatasetVersionMock buildSimpleVersion(Multidataset publication, String version) {
        MultidatasetVersionMock instance = new MultidatasetVersionMock();
        instance.setMultidataset(publication);
        instance.setVersionLogic(version);
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
        getSiemacMetadataStatisticalResource().setStatisticalOperation(StatisticalResourcesPersistedDoMocks.mockStatisticalOperationExternalItem(operationCode));
    }

    public void setMaintainerCode(String maintainerCode) {
        getSiemacMetadataStatisticalResource().setMaintainer(StatisticalResourcesPersistedDoMocks.mockAgencyExternalItem(maintainerCode));
    }

    public void setMultidataset(MultidatasetMock publication) {
        super.setMultidataset(publication);
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
