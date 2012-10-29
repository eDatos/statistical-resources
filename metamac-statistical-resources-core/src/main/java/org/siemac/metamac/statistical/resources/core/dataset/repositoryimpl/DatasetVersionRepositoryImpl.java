package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;

import org.springframework.stereotype.Repository;

/**
 * Repository implementation for DatasetVersion
 */
@Repository("datasetVersionRepository")
public class DatasetVersionRepositoryImpl extends DatasetVersionRepositoryBase {
    public DatasetVersionRepositoryImpl() {
    }

    public DatasetVersion findByUrn(String urn) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("findByUrn not implemented");

    }

    public DatasetVersion retrieveLastVersion(Long itemSchemeId) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveLastVersion not implemented");

    }

    public DatasetVersion findByVersion(Long itemSchemeId, String versionLogic) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("findByVersion not implemented");

    }
}
