package org.siemac.metamac.statistical.resources.core.utils.mocks;

import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.StatisticalResourcesVersionUtils;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesPersistedDoMocks;

public class DatasetVersionMock extends DatasetVersion {

    private static final long serialVersionUID = -289909094976654068L;

    private Integer           sequentialId;

    public DatasetVersionMock() {
        setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
    }

    public static DatasetVersionMock buildBasicSingleVersionWithSequence(int sequenceId) {
        DatasetVersionMock instance = new DatasetVersionMock();
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

    public void setDataset(DatasetMock dataset) {
        super.setDataset(dataset);
        if (dataset != null && dataset.getMaintainerCode() != null) {
            setMaintainerCode(dataset.getMaintainerCode());
        }
    }

}
