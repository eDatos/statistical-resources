package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository implementation for Cube
 */
@Repository("cubeRepository")
public class CubeRepositoryImpl extends CubeRepositoryBase {

    public CubeRepositoryImpl() {
    }

    @Override
    public Cube retrieveCubeByUrn(String urn) {
        // TODO
        throw new UnsupportedOperationException("retrieveCubeByUrn not implemented");
    }

    @Override
    public Cube retrieveCubePublishedByCode(String code) {
        // TODO
        throw new UnsupportedOperationException("retrieveCubePublishedByCode not implemented");
    }

    @Override
    public Boolean existAnyCube(String publicationCode, String publicationVersionNumber) {
        // TODO
        throw new UnsupportedOperationException("existAnyCube not implemented");
    }

    @Override
    public List<String> findDatasetsLinkedWithPublicationVersion(Long publicationVersionId) {
        // TODO
        throw new UnsupportedOperationException("findDatasetsLinkedWithPublicationVersion not implemented");
    }

    @Override
    public Cube retrieveCubeInPublishedPublication(String publicationCode, String cubeCode) {
        // TODO
        throw new UnsupportedOperationException("retrieveCubeInPublishedPublication not implemented");
    }

    @Override
    public List<Cube> findCubesInPublishedPublication(String publicationCode) {
        // TODO
        throw new UnsupportedOperationException("findCubesInPublishedPublication not implemented");
    }
}
