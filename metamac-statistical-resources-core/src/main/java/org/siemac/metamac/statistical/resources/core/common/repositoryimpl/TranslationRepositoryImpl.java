package org.siemac.metamac.statistical.resources.core.common.repositoryimpl;

import static org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteriaBuilder.criteriaFor;

import java.util.List;

import org.fornax.cartridges.sculptor.framework.accessapi.ConditionalCriteria;
import org.siemac.metamac.statistical.resources.core.common.domain.Translation;
import org.siemac.metamac.statistical.resources.core.common.domain.TranslationProperties;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for Translation
 */
@Repository("translationRepository")
public class TranslationRepositoryImpl extends TranslationRepositoryBase {

    public TranslationRepositoryImpl() {
    }

    @Override
    public Translation findTranslationByCode(String code) {
        List<ConditionalCriteria> condition = criteriaFor(Translation.class).withProperty(TranslationProperties.code()).eq(code).distinctRoot().build();
        List<Translation> result = findByCondition(condition);
        if (result.size() != 0) {
            return result.get(0);
        } else {
            return null;
        }
    }
}
