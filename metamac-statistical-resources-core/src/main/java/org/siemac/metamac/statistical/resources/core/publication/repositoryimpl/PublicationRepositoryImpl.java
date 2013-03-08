package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Publication
 */
@Repository("publicationRepository")
public class PublicationRepositoryImpl extends PublicationRepositoryBase {
    
    public PublicationRepositoryImpl() {
    }

    public String findLastPublicationCode(String operationUrn) {
        String subCodeHql = "substring(publicationVersion.siemacMetadataStatisticalResource.code, length(publicationVersion.siemacMetadataStatisticalResource.code)-3, length(publicationVersion.siemacMetadataStatisticalResource.code))";  
        String hql = "select max(" + subCodeHql + ")" +
                     "from PublicationVersion as publicationVersion " +
                     "where publicationVersion.siemacMetadataStatisticalResource.statisticalOperation.urn = :operationUrn ";
        
        
        Query query = getEntityManager().createQuery(hql);
        query.setParameter("operationUrn", operationUrn);
        String result = (String) query.getSingleResult();
       return result;
    }
}
