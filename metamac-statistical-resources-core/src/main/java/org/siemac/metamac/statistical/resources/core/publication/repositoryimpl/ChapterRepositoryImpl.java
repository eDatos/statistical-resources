package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.fornax.cartridges.sculptor.framework.domain.PagedResult;
import org.fornax.cartridges.sculptor.framework.domain.PagingParameter;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.ChapterProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Chapter
 */
@Repository("chapterRepository")
public class ChapterRepositoryImpl extends ChapterRepositoryBase {

    public ChapterRepositoryImpl() {
    }

    public Chapter retrieveChapterByUrn(String urn) throws MetamacException {
         List<ConditionalCriteria> condition = criteriaFor(Chapter.class).withProperty(ChapterProperties.nameableStatisticalResource().urn()).eq(urn).distinctRoot().build();
         PagedResult<Chapter> result = findByCondition(condition, PagingParameter.pageAccess(1));
        
        if (result.getValues().size() == 0) {
            throw new MetamacException(ServiceExceptionType.CHAPTER_NOT_FOUND, urn);
        } else if (result.getValues().size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one chapter with urn " + urn);
        }

        return result.getValues().get(0);
    }
}
