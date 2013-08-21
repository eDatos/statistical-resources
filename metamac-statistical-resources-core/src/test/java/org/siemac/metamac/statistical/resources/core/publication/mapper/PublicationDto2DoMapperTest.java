package org.siemac.metamac.statistical.resources.core.publication.mapper;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsChapter;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsCube;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory.CHAPTER_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory.DATASET_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_01_SIMPLE_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class PublicationDto2DoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private PublicationDto2DoMapper publicationDto2DoMapper;

    @Autowired
    private DatasetMockFactory      datasetMockFactory;

    @Autowired
    private QueryMockFactory        queryMockFactory;

    @Autowired
    private ChapterMockFactory      chapterMockFactory;

    @Test
    public void testPublicationDtoToDo() throws MetamacException {
        PublicationVersionDto dto = StatisticalResourcesDtoMocks.mockPublicationVersionDto();
        PublicationVersion entity = publicationDto2DoMapper.publicationVersionDtoToDo(dto);
        assertEqualsPublicationVersion(dto, entity);
    }

    @Test
    public void testChapterDtoToDo() throws MetamacException {
        ChapterDto dto = StatisticalResourcesDtoMocks.mockChapterDto();
        Chapter entity = publicationDto2DoMapper.chapterDtoToDo(dto);
        assertEqualsChapter(dto, entity);
    }

    @Test
    @MetamacMock(CHAPTER_01_BASIC_NAME)
    public void testChapterDtoToDoWithParent() throws MetamacException {
        String parentChapterUrn = chapterMockFactory.retrieveMock(CHAPTER_01_BASIC_NAME).getNameableStatisticalResource().getUrn();
        ChapterDto dto = StatisticalResourcesDtoMocks.mockChapterDtoWithParent(parentChapterUrn);
        Chapter entity = publicationDto2DoMapper.chapterDtoToDo(dto);
        assertEqualsChapter(dto, entity);
    }

    @Test
    @MetamacMock(DATASET_01_BASIC_NAME)
    public void datasetCubeDtoToDo() throws MetamacException {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_01_BASIC_NAME).getIdentifiableStatisticalResource().getUrn();
        CubeDto dto = StatisticalResourcesDtoMocks.mockDatasetCubeDto(datasetUrn);
        Cube entity = publicationDto2DoMapper.cubeDtoToDo(dto);
        assertEqualsCube(dto, entity);
    }
    
    @Test
    @MetamacMock({DATASET_01_BASIC_NAME, CHAPTER_01_BASIC_NAME})
    public void datasetCubeDtoToDoWithParent() throws MetamacException {
        String datasetUrn = datasetMockFactory.retrieveMock(DATASET_01_BASIC_NAME).getIdentifiableStatisticalResource().getUrn();
        String parentChapterUrn = chapterMockFactory.retrieveMock(CHAPTER_01_BASIC_NAME).getNameableStatisticalResource().getUrn();
        CubeDto dto = StatisticalResourcesDtoMocks.mockDatasetCubeDtoWithParent(parentChapterUrn, datasetUrn);
        Cube entity = publicationDto2DoMapper.cubeDtoToDo(dto);
        assertEqualsCube(dto, entity);
    }
    
    @Test
    @MetamacMock(QUERY_01_SIMPLE_NAME)
    public void queryCubeDtoToDo() throws MetamacException {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_01_SIMPLE_NAME).getIdentifiableStatisticalResource().getUrn();
        CubeDto dto = StatisticalResourcesDtoMocks.mockQueryCubeDto(queryUrn);
        Cube entity = publicationDto2DoMapper.cubeDtoToDo(dto);
        assertEqualsCube(dto, entity);
    }
    
    @Test
    @MetamacMock({QUERY_01_SIMPLE_NAME, CHAPTER_01_BASIC_NAME})
    public void queryCubeDtoToDoWithParent() throws MetamacException {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_01_SIMPLE_NAME).getIdentifiableStatisticalResource().getUrn();
        String parentChapterUrn = chapterMockFactory.retrieveMock(CHAPTER_01_BASIC_NAME).getNameableStatisticalResource().getUrn();
        CubeDto dto = StatisticalResourcesDtoMocks.mockQueryCubeDtoWithParent(parentChapterUrn, queryUrn);
        Cube entity = publicationDto2DoMapper.cubeDtoToDo(dto);
        assertEqualsCube(dto, entity);
    }
}
