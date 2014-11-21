package org.siemac.metamac.statistical.resources.core.dataset.utils;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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

    @Test
    public void testDimensionRepresentationMapToString() {
        {
            Map<String, String> map = new HashMap<String, String>();
            map.put("DIMENSION01", "urn:repr01");
            map.put("DIMENSION02", "urn:repr02");

            String expected = "DIMENSION01#urn:repr01,DIMENSION02#urn:repr02";
            String actual = DatasetVersionUtils.dimensionRepresentationMapToString(map);
            assertEquals(expected, actual);
        }
        {
            Map<String, String> map = new HashMap<String, String>();
            map.put("DIMENSION01", "urn:repr01");

            String expected = "DIMENSION01#urn:repr01";
            String actual = DatasetVersionUtils.dimensionRepresentationMapToString(map);
            assertEquals(expected, actual);
        }
        {
            String actual = DatasetVersionUtils.dimensionRepresentationMapToString(new HashMap<String, String>());
            assertEquals(StringUtils.EMPTY, actual);
        }
        {
            String actual = DatasetVersionUtils.dimensionRepresentationMapToString(null);
            assertEquals(null, actual);
        }
    }

    @Test
    public void testDimensionRepresentationMapFromString() {
        {
            String serializedMap = "DIMENSION01#urn:repr01=code1,DIMENSION02#urn:repr02=code2";
            Map<String, String> expected = new HashMap<String, String>();
            expected.put("DIMENSION01", "urn:repr01=code1");
            expected.put("DIMENSION02", "urn:repr02=code2");

            Map<String, String> actual = DatasetVersionUtils.dimensionRepresentationMapFromString(serializedMap);

            assertEquals(expected, actual);
        }
        {
            String serializedMap = "DIMENSION01#urn:repr01=code2";
            Map<String, String> expected = new HashMap<String, String>();
            expected.put("DIMENSION01", "urn:repr01=code2");

            Map<String, String> actual = DatasetVersionUtils.dimensionRepresentationMapFromString(serializedMap);

            assertEquals(expected, actual);
        }
        {
            Map<String, String> actual = DatasetVersionUtils.dimensionRepresentationMapFromString(StringUtils.EMPTY);
            assertEquals(new HashMap<String, String>(), actual);
        }
        {
            Map<String, String> actual = DatasetVersionUtils.dimensionRepresentationMapFromString(null);
            assertEquals(new HashMap<String, String>(), actual);
        }
    }
}
