package org.siemac.metamac.statistical.resources.core.multidataset.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import javax.persistence.Query;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCubeProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for MultidatasetCube
 */
@Repository("multidatasetCubeRepository")
public class MultidatasetCubeRepositoryImpl extends MultidatasetCubeRepositoryBase {

    public MultidatasetCubeRepositoryImpl() {
    }

    @Override
    public MultidatasetCube retrieveCubeByUrn(String urn) throws MetamacException {
        List<ConditionalCriteria> condition = criteriaFor(MultidatasetCube.class).withProperty(MultidatasetCubeProperties.nameableStatisticalResource().urn()).eq(urn).distinctRoot().build();
        List<MultidatasetCube> result = findByCondition(condition);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.MULTIDATASET_CUBE_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one multidataset cube with urn " + urn);
        }

        return result.get(0);
    }

    @Override
    public Boolean existAnyCubeInMultidataset(String multidatasetCode, String multidatasetVersionNumber) {

        //@formatter:off
        List<ConditionalCriteria> conditions = criteriaFor(MultidatasetCube.class)
                .withProperty(MultidatasetCubeProperties.multidatasetVersion().multidataset().identifiableStatisticalResource().code())
                    .eq(multidatasetCode)
            .and()
                .withProperty(MultidatasetCubeProperties.multidatasetVersion().siemacMetadataStatisticalResource().versionLogic())
                    .eq(multidatasetVersionNumber)
                .build();
        //@formatter:on

        List<MultidatasetCube> result = findByCondition(conditions);
        return !result.isEmpty();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> findDatasetsLinkedWithMultidatasetVersion(String multidatasetVersionUrn) {
        Query query = getEntityManager().createQuery(
                "select distinct(multidatasetcube.dataset.identifiableStatisticalResource.urn) from MultidatasetCube multidatasetcube where multidatasetcube.multidatasetVersion.siemacMetadataStatisticalResource.urn = :urn");
        query.setParameter("urn", multidatasetVersionUrn);
        List<String> result = query.getResultList();
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> findQueriesLinkedWithMultidatasetVersion(String multidatasetVersionUrn) {
        Query query = getEntityManager().createQuery(
                "select distinct(multidatasetcube.query.identifiableStatisticalResource.urn) from MultidatasetCube multidatasetcube where multidatasetcube.multidatasetVersion.siemacMetadataStatisticalResource.urn = :urn");
        query.setParameter("urn", multidatasetVersionUrn);
        List<String> result = query.getResultList();
        return result;
    }
}
