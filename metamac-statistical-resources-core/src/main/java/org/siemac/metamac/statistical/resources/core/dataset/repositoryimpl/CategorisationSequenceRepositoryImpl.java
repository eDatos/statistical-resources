package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.statistical.resources.core.dataset.domain.CategorisationSequence;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for CategorisationSequence
 */
@Repository("categorisationSequenceRepository")
public class CategorisationSequenceRepositoryImpl extends CategorisationSequenceRepositoryBase {

    public CategorisationSequenceRepositoryImpl() {
    }

    @Override
    public synchronized Long generateNextSequence(String maintainerUrn) {

        // Retrieve actual
        CategorisationSequence categorisationSequence = findCategorisationSequenceByMaintainerUrn(maintainerUrn);

        // Set sequence
        if (categorisationSequence != null) {
            categorisationSequence.setActualSequence(categorisationSequence.getActualSequence() + 1);
        } else {
            categorisationSequence = new CategorisationSequence();
            categorisationSequence.setMaintainerUrn(maintainerUrn);
            categorisationSequence.setActualSequence(Long.valueOf(1));
        }
        categorisationSequence = save(categorisationSequence);

        return categorisationSequence.getActualSequence();
    }

    private CategorisationSequence findCategorisationSequenceByMaintainerUrn(String maintainerUrn) {
        Map<String, Object> parameters = new HashMap<String, Object>(1);
        parameters.put("maintainerUrn", maintainerUrn);
        List<CategorisationSequence> result = findByQuery("from CategorisationSequence where maintainerUrn = :maintainerUrn", parameters, 1);
        if (result != null && result.size() == 1) {
            return result.get(0);
        } else {
            return null;
        }
    }
}
