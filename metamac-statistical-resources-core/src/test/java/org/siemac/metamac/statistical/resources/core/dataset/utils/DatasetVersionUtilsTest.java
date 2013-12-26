package org.siemac.metamac.statistical.resources.core.dataset.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;

public class DatasetVersionUtilsTest {

    @Test
    public void testGenerateDatasetRepositoryTableName() throws Exception {
        SiemacMetadataStatisticalResource datasetVersion = mockRequieredDatasetVersionFieldsForGenerateRepositoryTableName("C00025A_000001", "001.000");
        assertEquals("DATA_C00025A_000001_001000", DatasetVersionUtils.generateDatasetRepositoryTableName(datasetVersion));
    }

    private static SiemacMetadataStatisticalResource mockRequieredDatasetVersionFieldsForGenerateRepositoryTableName(String code, String version) {
        SiemacMetadataStatisticalResource resource = new SiemacMetadataStatisticalResource();
        resource.setCode(code);
        resource.setVersionLogic(version);
        return resource;
    }
}
