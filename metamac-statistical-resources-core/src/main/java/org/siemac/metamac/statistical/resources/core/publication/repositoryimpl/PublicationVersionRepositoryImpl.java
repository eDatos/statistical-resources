package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.joda.time.DateTime;
import org.siemac.metamac.core.common.criteria.utils.CriteriaUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResourceRepository;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceResult;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for PublicationVersion
 */
@Repository("publicationVersionRepository")
public class PublicationVersionRepositoryImpl extends PublicationVersionRepositoryBase {

    @Autowired
    private LifeCycleStatisticalResourceRepository lifeCycleStatisticalResourceRepository;
    
    public PublicationVersionRepositoryImpl() {
    }

    @Override
    public PublicationVersion retrieveByUrn(String urn) throws MetamacException {

        // Prepare criteria
        List<ConditionalCriteria> condition = criteriaFor(PublicationVersion.class)
                .withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().urn()).eq(urn).distinctRoot().build();

        // Find
        List<PublicationVersion> result = findByCondition(condition);

        // Check for unique result and return
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one publication with urn " + urn);
        }

        return result.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PublicationVersion retrieveLastVersion(String publicationUrn) throws MetamacException {

        // Prepare criteria
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class)
                .withProperty(PublicationVersionProperties.publication().identifiableStatisticalResource().urn()).eq(publicationUrn)
                .orderBy(CriteriaUtils.getDatetimedLeafProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().creationDate(), PublicationVersion.class)).descending()
                .distinctRoot().build();

        // Find
        PagingParameter paging = PagingParameter.rowAccess(0, 1);
        PagedResult<PublicationVersion> result = findByCondition(conditions, paging);

        // Check for unique result and return. We have at least one publicationVersion
        if (result.getRowCount() == 0) {
            throw new MetamacException(ServiceExceptionType.PUBLICATION_LAST_VERSION_NOT_FOUND, publicationUrn);
        }
        
        return result.getValues().get(0);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public PublicationVersion retrieveLastPublishedVersion(String publicationUrn) throws MetamacException {
        // Prepare criteria
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class).withProperty(PublicationVersionProperties.publication().identifiableStatisticalResource().urn())
                .eq(publicationUrn).and().withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().procStatus()).eq(ProcStatusEnum.PUBLISHED).and()
                .withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().validFrom()).lessThanOrEqual(new DateTime())
                .orderBy(CriteriaUtils.getDatetimedLeafProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().creationDate(), PublicationVersion.class)).descending().distinctRoot().build();

        PagingParameter paging = PagingParameter.rowAccess(0, 1);
        // Find
        PagedResult<PublicationVersion> result = findByCondition(conditions, paging);

        // Check for unique result and return
        if (result.getRowCount() != 0) {
            return result.getValues().get(0);
        } else {
            return null;
        }
    }

    @Override
    public PublicationVersion retrieveByVersion(Long statisticalResourceId, String versionLogic) throws MetamacException {

        // Prepare criteria
        List<ConditionalCriteria> conditions = ConditionalCriteriaBuilder.criteriaFor(PublicationVersion.class).withProperty(PublicationVersionProperties.publication().id()).eq(statisticalResourceId)
                .withProperty(PublicationVersionProperties.siemacMetadataStatisticalResource().versionLogic()).eq(versionLogic).distinctRoot().build();

        // Find
        List<PublicationVersion> result = findByCondition(conditions);

        // Check for unique result and return
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_NOT_FOUND, statisticalResourceId, versionLogic);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one publication with id " + statisticalResourceId + " and versionLogic " + versionLogic + " found");
        }
        return result.get(0);
    }
    
    @Override
    public RelatedResourceResult retrieveResourceThatReplacesPublicationVersion(PublicationVersion publicationVersion) throws MetamacException {
        return lifeCycleStatisticalResourceRepository.retrieveResourceThatReplacesThisResourceVersion(publicationVersion.getId(), TypeRelatedResourceEnum.PUBLICATION_VERSION);
    }
}
