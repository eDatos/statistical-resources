package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

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
        String hql = "select max(" + subCodeHql + ")" + "from DatasetVersion as dsv " + "where dsv.siemacMetadataStatisticalResource.statisticalOperation.urn = :operationUrn ";

        Query query = getEntityManager().createQuery(hql);
        query.setParameter("operationUrn", operationUrn);
        String result = (String) query.getSingleResult();
        return result;
    }
}
