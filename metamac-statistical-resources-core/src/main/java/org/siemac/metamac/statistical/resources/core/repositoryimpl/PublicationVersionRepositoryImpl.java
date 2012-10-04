package org.siemac.metamac.statistical.resources.core.repositoryimpl;

import org.siemac.metamac.statistical.resources.core.domain.PublicationVersion;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository implementation for PublicationVersion
 */
@Repository("publicationVersionRepository")
public class PublicationVersionRepositoryImpl
    extends PublicationVersionRepositoryBase {
    public PublicationVersionRepositoryImpl() {
    }

    public PublicationVersion findByUrn(String urn) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("findByUrn not implemented");

    }

    public PublicationVersion retrieveLastVersion(Long itemSchemeId) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "retrieveLastVersion not implemented");

    }

    public PublicationVersion findItemSchemeVersionNoFinal(Long itemSchemeId) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "findItemSchemeVersionNoFinal not implemented");

    }

    public List<PublicationVersion> findItemSchemeVersionsFinal(
        Long itemSchemeId) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "findItemSchemeVersionsFinal not implemented");

    }

    public PublicationVersion findByVersion(Long itemSchemeId,
        String versionLogic) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("findByVersion not implemented");

    }
}
