package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;

import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Datasource
 */
@Repository("datasourceRepository")
public class DatasourceRepositoryImpl extends DatasourceRepositoryBase {
    public DatasourceRepositoryImpl() {
    }

    public Datasource findByUrn(String urn) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("findByUrn not implemented");

    }
}
