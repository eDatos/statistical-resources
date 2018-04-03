package org.siemac.metamac.statistical.resources.core.multidataset.repositoryimpl;

import org.siemac.metamac.statistical.resources.core.multidataset.domain.Multidataset;

import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Multidataset
 */
@Repository("multidatasetRepository")
public class MultidatasetRepositoryImpl extends MultidatasetRepositoryBase {
    public MultidatasetRepositoryImpl() {
    }

    public Multidataset retrieveByUrn(String urn) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("retrieveByUrn not implemented");

    }
}
