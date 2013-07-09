package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.springframework.stereotype.Repository;

/**
 * Repository implementation for CodeDimension
 */
@Repository("codeDimensionRepository")
public class CodeDimensionRepositoryImpl extends CodeDimensionRepositoryBase {
    public CodeDimensionRepositoryImpl() {
    }

    public List<CodeDimension> findCodesForDatasetVersionByDimensionId(
        DatasetVersion datasetVersion, String dimensionId) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
            "findCodesForDatasetVersionByDimensionId not implemented");

    }
    
    @Override
    public List<CodeDimension> findCodesForDatasetVersionByDimensionId(long datasetVersionId, String dimensionId) throws MetamacException {
        Map<String,Object> parameters = new HashMap<String, Object>();
        parameters.put("dimensionId", dimensionId);
        parameters.put("datasetVersionId", datasetVersionId);
        return findByQuery(
                "from CodeDimension code "+
                "where code.dsdComponentId = :dimensionId "+
                "and code.datasetVersion.id = :datasetVersionId"
                , 
                parameters);
    }
}
