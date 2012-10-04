package org.siemac.metamac.statistical.resources.core.repositoryimpl;

import org.siemac.metamac.statistical.resources.core.domain.DatasetVersion;

import org.springframework.stereotype.Repository;

import java.util.List;

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

    public DatasetVersion findItemSchemeVersionNoFinal(Long itemSchemeId) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "findItemSchemeVersionNoFinal not implemented");

    }

    public List<DatasetVersion> findItemSchemeVersionsFinal(Long itemSchemeId) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "findItemSchemeVersionsFinal not implemented");

    }

    public DatasetVersion findByVersion(Long itemSchemeId, String versionLogic) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("findByVersion not implemented");

    }
}
