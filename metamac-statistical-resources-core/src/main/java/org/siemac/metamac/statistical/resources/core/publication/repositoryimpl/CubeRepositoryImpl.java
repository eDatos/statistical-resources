package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.CubeProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Cube
 */
@Repository("cubeRepository")
public class CubeRepositoryImpl extends CubeRepositoryBase {

    public CubeRepositoryImpl() {
    }

    @Override
    public Cube retrieveCubeByUrn(String urn) throws MetamacException {
        // TODO: Limitar el número de resultados a uno para mejorar la eficiencia

        List<ConditionalCriteria> condition = criteriaFor(Cube.class).withProperty(CubeProperties.nameableStatisticalResource().urn()).eq(urn).distinctRoot().build();
        List<Cube> result = findByCondition(condition);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.CUBE_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one cube with urn " + urn);
        }

        return result.get(0);
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
