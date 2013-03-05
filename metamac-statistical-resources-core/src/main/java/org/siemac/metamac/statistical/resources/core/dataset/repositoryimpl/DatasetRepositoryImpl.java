package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;


import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Dataset
 */
@Repository("datasetRepository")
public class DatasetRepositoryImpl extends DatasetRepositoryBase {
    public DatasetRepositoryImpl() {
    }

    @Override
    public String findLastDatasetCode(String operationUrn) {

        String subCodeHql = "substring(dsv.siemacMetadataStatisticalResource.code, length(dsv.siemacMetadataStatisticalResource.code)-3, length(dsv.siemacMetadataStatisticalResource.code))";  
        String hql = "select distinct(" + subCodeHql + ")" +
                     "from DatasetVersion as dsv " +
                     "where dsv.siemacMetadataStatisticalResource.statisticalOperation.urn = :operationUrn " +
                     "order by " + subCodeHql + " desc";
        
        
        Query query = getEntityManager().createQuery(hql);
        query.setMaxResults(1);
        query.setParameter("operationUrn", operationUrn);
        List results = query.getResultList();
        if (results != null && results.size() > 0) {
            return results.get(0).toString();
        } else {
            return null;
        }
    }
}
