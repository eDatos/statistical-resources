package org.siemac.metamac.statistical.resources.core.publication.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertRelaxedEqualsChapter;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory.CHAPTER_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory.CHAPTER_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory.CHAPTER_03_BASIC_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.ChapterRepository;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class ChapterRepositoryTest extends StatisticalResourcesBaseTest implements ChapterRepositoryTestBase {

    @Autowired
    private ChapterMockFactory chapterMockFactory;
    
    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    @Test
    @MetamacMock({CHAPTER_01_BASIC_NAME, CHAPTER_02_BASIC_NAME, CHAPTER_03_BASIC_NAME})
    public void testRetrieveChapterByUrn() throws Exception {
        Chapter expected = chapterMockFactory.retrieveMock(CHAPTER_01_BASIC_NAME);
        Chapter actual = chapterRepository.retrieveChapterByUrn(expected.getNameableStatisticalResource().getUrn());
        assertRelaxedEqualsChapter(expected, actual);
    }
    
    @Test
    @MetamacMock({CHAPTER_01_BASIC_NAME, CHAPTER_02_BASIC_NAME, CHAPTER_03_BASIC_NAME})
    public void testRetrieveChapterByUrnErrorNotFound() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.CHAPTER_NOT_FOUND, URN_NOT_EXISTS));
        chapterRepository.retrieveChapterByUrn(URN_NOT_EXISTS);
    }
}
