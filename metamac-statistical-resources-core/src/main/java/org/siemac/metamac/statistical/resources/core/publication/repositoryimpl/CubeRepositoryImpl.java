package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import javax.persistence.Query;

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
    public Boolean existAnyCubeInPublication(String publicationCode, String publicationVersionNumber) throws MetamacException {
        List<ConditionalCriteria> conditions = criteriaFor(Cube.class).withProperty(CubeProperties.elementLevel().publicationVersion().publication().identifiableStatisticalResource().code())
        .eq(publicationCode).and().withProperty(CubeProperties.elementLevel().publicationVersion().siemacMetadataStatisticalResource().versionLogic()).eq(publicationVersionNumber).build();

        List<Cube> result = findByCondition(conditions);
        return !result.isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> findDatasetsLinkedWithPublicationVersion(String publicationVersionUrn) throws MetamacException {
        Query query = getEntityManager().createQuery("select distinct(cube.dataset.identifiableStatisticalResource.urn) from Cube cube where cube.elementLevel.publicationVersion.siemacMetadataStatisticalResource.urn = :urn");
        query.setParameter("urn", publicationVersionUrn);
        List<String> result = query.getResultList();
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> findQueriesLinkedWithPublicationVersion(String publicationVersionUrn) throws MetamacException {
        Query query = getEntityManager().createQuery("select distinct(cube.query.identifiableStatisticalResource.urn) from Cube cube where cube.elementLevel.publicationVersion.siemacMetadataStatisticalResource.urn = :urn");
        query.setParameter("urn", publicationVersionUrn);
        List<String> result = query.getResultList();
        return result;
    }
}
