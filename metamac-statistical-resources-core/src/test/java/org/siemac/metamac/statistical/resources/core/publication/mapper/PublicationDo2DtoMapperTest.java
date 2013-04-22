package org.siemac.metamac.statistical.resources.core.publication.mapper;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersionDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class PublicationDo2DtoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private PublicationDo2DtoMapper       publicationDo2DtoMapper;

    @Autowired
    private PublicationVersionMockFactory publicationVersionMockFactory;
    

    @Test
    @MetamacMock({PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME})
    public void testPublicationDoToDto() throws MetamacException {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME);
        PublicationDto actual = publicationDo2DtoMapper.publicationVersionDoToDto(expected);
        assertEqualsPublicationVersion(expected, actual);
    }
    
    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME, PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME, PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME})
    public void testPublicationDoListToDtoList() throws MetamacException {
        List<PublicationVersion> expected = new ArrayList<PublicationVersion>();
        expected.add(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME));
        expected.add(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_02_BASIC_NAME));
        expected.add(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME));
        expected.add(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME));
        
        List<PublicationDto> actual = publicationDo2DtoMapper.publicationVersionDoListToDtoList(expected);

        assertEquals(expected.size(), actual.size());
        assertEqualsPublicationVersionDoAndDtoCollection(expected, actual);
    }
    
    
}
