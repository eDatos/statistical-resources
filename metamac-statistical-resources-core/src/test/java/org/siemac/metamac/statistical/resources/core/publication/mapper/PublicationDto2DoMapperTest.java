package org.siemac.metamac.statistical.resources.core.publication.mapper;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class PublicationDto2DoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private PublicationDto2DoMapper publicationDto2DoMapper;

    @Test
    public void testPublicationDtoToDo() throws MetamacException {
        PublicationDto dto = StatisticalResourcesDtoMocks.mockPublicationDto();
        PublicationVersion entity = publicationDto2DoMapper.publicationVersionDtoToDo(dto);
        assertEqualsPublicationVersion(dto, entity);
    }

}
