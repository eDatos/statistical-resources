package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;

public class StatRepoDto2StatisticalResourcesDtoMapperTest extends StatisticalResourcesBaseTest {

    @InjectMocks
    private StatRepoDto2StatisticalResourcesDtoMapper dto2StatisticalResourcesDtoMapper = new StatRepoDto2StatisticalResourcesDtoMapperImpl();

    @Test
    public void testAttributeDtoToDsdAttributeInstaceDto() throws MetamacException {
        // TODO
    }
}
