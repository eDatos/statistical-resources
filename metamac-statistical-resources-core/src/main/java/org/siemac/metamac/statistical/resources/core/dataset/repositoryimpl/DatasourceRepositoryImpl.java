package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasourceProperties;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Datasource
 */
@Repository("datasourceRepository")
public class DatasourceRepositoryImpl extends DatasourceRepositoryBase {

    public DatasourceRepositoryImpl() {
    }

    @Override
    public Datasource retrieveByUrn(String urn) throws MetamacException {
        List<ConditionalCriteria> condition = criteriaFor(Datasource.class).withProperty(DatasourceProperties.identifiableStatisticalResource().urn()).eq(urn).distinctRoot().build();

        List<Datasource> result = findByCondition(condition);

        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.DATASOURCE_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one datasource with urn " + urn);
        }

        return result.get(0);
    }

    @Override
    public List<Datasource> findByFilename(String filename) {
        List<ConditionalCriteria> condition = criteriaFor(Datasource.class).withProperty(DatasourceProperties.filename()).eq(filename).distinctRoot().build();
        return findByCondition(condition);
    }
}
