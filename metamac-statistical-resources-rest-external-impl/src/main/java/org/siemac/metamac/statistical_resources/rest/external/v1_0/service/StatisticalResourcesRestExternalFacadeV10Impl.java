package org.siemac.metamac.statistical_resources.rest.external.v1_0.service;

import static org.siemac.metamac.statistical_resources.rest.external.service.utils.StatisticalResourcesRestExternalUtils.hasField;
import static org.siemac.metamac.statistical_resources.rest.external.service.utils.StatisticalResourcesRestExternalUtils.manageException;
import static org.siemac.metamac.statistical_resources.rest.external.service.utils.StatisticalResourcesRestExternalUtils.parseDimensionExpression;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.service.StatisticalResourcesRestExternalCommonService;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset.DatasetsDo2RestMapperV10;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("statisticalResourcesRestExternalFacadeV10")
public class StatisticalResourcesRestExternalFacadeV10Impl implements StatisticalResourcesV1_0 {

    @Autowired
    private StatisticalResourcesRestExternalCommonService commonService;

    @Autowired
    private DatasetsDo2RestMapperV10                      datasetsDo2RestMapper;

    @Override
    public Dataset retrieveDataset(String agencyID, String resourceID, String version, List<String> lang, String fields, String dim) {
        try {

            Map<String, List<String>> dimensions = parseDimensionExpression(dim);
            boolean includeMetadata = !hasField(fields, RestExternalConstants.RETRIEVE_DATASET_EXCLUDE_METADATA);
            boolean includeData = !hasField(fields, RestExternalConstants.RETRIEVE_DATASET_EXCLUDE_DATA);

            DatasetVersion datasetVersion = commonService.retrieveDatasetVersion(agencyID, resourceID, version);
            Dataset dataset = datasetsDo2RestMapper.toDataset(datasetVersion, dimensions, lang, includeMetadata, includeData);
            return dataset;
        } catch (Exception e) {
            throw manageException(e);
        }
    }

}
