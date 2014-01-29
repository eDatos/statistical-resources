package org.siemac.metamac.statistical.resources.core.utils;

import org.siemac.metamac.core.common.util.predicates.MetamacPredicate;
import org.siemac.metamac.statistical.resources.core.task.domain.TaskInfoDataset;


public class TaskInfoPredicateByDatasetId extends MetamacPredicate<TaskInfoDataset> {

    
    private final String datasetId;

    public TaskInfoPredicateByDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }
    
    @Override
    protected boolean eval(TaskInfoDataset obj) {
        return obj != null && obj.getDatasetVersionId().equals(datasetId);
    }

}
