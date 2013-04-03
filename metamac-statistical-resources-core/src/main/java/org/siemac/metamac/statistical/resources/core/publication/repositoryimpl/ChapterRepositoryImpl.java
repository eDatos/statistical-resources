package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.ChapterProperties;
import org.springframework.stereotype.Repository;
import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

/**
 * Repository implementation for Chapter
 */
@Repository("chapterRepository")
public class ChapterRepositoryImpl extends ChapterRepositoryBase {

    public ChapterRepositoryImpl() {
    }

    public Chapter retrieveChapterByUrn(String urn) throws MetamacException {
        // TODO: Limitar el número de resultados a uno para mejorar la eficiencia
        
         List<ConditionalCriteria> condition = criteriaFor(Chapter.class).withProperty(ChapterProperties.nameableStatisticalResource().urn()).eq(urn).distinctRoot().build();
         List<Chapter> result = findByCondition(condition);
        
        if (result.size() == 0) {
            throw new MetamacException(ServiceExceptionType.CHAPTER_NOT_FOUND, urn);
        } else if (result.size() > 1) {
            // Exists a database constraint that makes URN unique
            throw new MetamacException(ServiceExceptionType.UNKNOWN, "More than one chapter with urn " + urn);
        }

        return result.get(0);
    }
}
