package org.siemac.metamac.statistical.resources.core.enume.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;

public class TypeRelatedResourceEnumUtilsTest extends StatisticalResourcesBaseTest {

    @Test
    public void checkIfTypeRelatedResourceIsAnyReturnFalse() throws Exception {
        boolean result = TypeRelatedResourceEnumUtils.checkIfTypeRelatedResourceIsAny(TypeRelatedResourceEnum.DATASET, TypeRelatedResourceEnum.DATASOURCE, TypeRelatedResourceEnum.CUBE,
                TypeRelatedResourceEnum.CHAPTER);
        assertFalse(result);
    }
    
    @Test
    public void checkIfTypeRelatedResourceIsAnyReturnTrue() throws Exception {
        boolean result = TypeRelatedResourceEnumUtils.checkIfTypeRelatedResourceIsAny(TypeRelatedResourceEnum.CHAPTER, TypeRelatedResourceEnum.DATASOURCE, TypeRelatedResourceEnum.CUBE,
                TypeRelatedResourceEnum.CHAPTER);
        assertTrue(result);
    }

}