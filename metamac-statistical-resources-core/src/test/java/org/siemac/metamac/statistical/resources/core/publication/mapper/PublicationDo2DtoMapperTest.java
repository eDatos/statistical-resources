package org.siemac.metamac.statistical.resources.core.publication.mapper;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsChapter;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsCube;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublication;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersion;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersionDoAndDtoCollection;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationVersionStructure;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertRelaxedEqualsElementLevelCollection;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory.CHAPTER_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory.CHAPTER_04_WITH_PARENT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_04_DATASET_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_05_QUERY_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_06_DATASET_WITH_PARENT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory.CUBE_07_QUERY_WITH_PARENT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_01_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_02_BASIC_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_27_V1_PUBLISHED_FOR_PUBLICATION_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory.PUBLICATION_VERSION_31_V2_PUBLISHED_NO_VISIBLE_FOR_PUBLICATION_06_NAME;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ChapterDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.CubeDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.ElementLevelDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationStructureDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.publication.PublicationVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.Chapter;
import org.siemac.metamac.statistical.resources.core.publication.domain.Cube;
import org.siemac.metamac.statistical.resources.core.publication.domain.ElementLevel;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.ChapterMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.CubeMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class PublicationDo2DtoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private PublicationDo2DtoMapper       publicationDo2DtoMapper;

    @Autowired
    private PublicationVersionMockFactory publicationVersionMockFactory;

    @Autowired
    private ChapterMockFactory            chapterMockFactory;

    @Autowired
    private CubeMockFactory               cubeMockFactory;

    @Test
    @MetamacMock({PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME})
    public void testPublicationDoToDto() throws MetamacException {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME);
        PublicationVersionDto actual = publicationDo2DtoMapper.publicationVersionDoToDto(expected);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_31_V2_PUBLISHED_NO_VISIBLE_FOR_PUBLICATION_06_NAME})
    public void testPublicationDoToDtoProcStatusPublishedNotVisible() throws MetamacException {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_31_V2_PUBLISHED_NO_VISIBLE_FOR_PUBLICATION_06_NAME);
        PublicationVersionDto actual = publicationDo2DtoMapper.publicationVersionDoToDto(expected);
        assertEquals(ProcStatusEnum.PUBLISHED_NOT_VISIBLE, actual.getProcStatus());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_27_V1_PUBLISHED_FOR_PUBLICATION_05_NAME})
    public void testPublicationDoToDtoProcStatusPublishedVisible() throws MetamacException {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_27_V1_PUBLISHED_FOR_PUBLICATION_05_NAME);
        PublicationVersionDto actual = publicationDo2DtoMapper.publicationVersionDoToDto(expected);
        assertEquals(ProcStatusEnum.PUBLISHED, actual.getProcStatus());
    }

    @Test
    @MetamacMock({PUBLICATION_VERSION_01_BASIC_NAME, PUBLICATION_VERSION_02_BASIC_NAME, PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME, PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME})
    public void testPublicationDoListToDtoList() throws MetamacException {
        List<PublicationVersion> expected = new ArrayList<PublicationVersion>();
        expected.add(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_01_BASIC_NAME));
        expected.add(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_02_BASIC_NAME));
        expected.add(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_03_FOR_PUBLICATION_03_NAME));
        expected.add(publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME));

        List<PublicationVersionBaseDto> actual = publicationDo2DtoMapper.publicationVersionDoListToDtoList(expected);

        assertEquals(expected.size(), actual.size());
        assertEqualsPublicationVersionDoAndDtoCollection(expected, actual);
    }

    @Test
    @MetamacMock(CHAPTER_01_BASIC_NAME)
    public void chapterDoToDto() throws MetamacException {
        Chapter expected = chapterMockFactory.retrieveMock(CHAPTER_01_BASIC_NAME);
        ChapterDto actual = publicationDo2DtoMapper.chapterDoToDto(expected);
        assertEqualsChapter(expected, actual);
    }

    @Test
    @MetamacMock(CHAPTER_04_WITH_PARENT_NAME)
    public void chapterDoToDtoWithParent() throws MetamacException {
        Chapter expected = chapterMockFactory.retrieveMock(CHAPTER_04_WITH_PARENT_NAME);
        ChapterDto actual = publicationDo2DtoMapper.chapterDoToDto(expected);
        assertEqualsChapter(expected, actual);
    }

    @Test
    @MetamacMock(CUBE_04_DATASET_NAME)
    public void datasetCubeDoToDto() throws MetamacException {
        Cube expected = cubeMockFactory.retrieveMock(CUBE_04_DATASET_NAME);
        CubeDto actual = publicationDo2DtoMapper.cubeDoToDto(expected);
        assertEqualsCube(expected, actual);
    }

    @Test
    @MetamacMock(CUBE_05_QUERY_NAME)
    public void queryCubeDoToDto() throws MetamacException {
        Cube expected = cubeMockFactory.retrieveMock(CUBE_05_QUERY_NAME);
        CubeDto actual = publicationDo2DtoMapper.cubeDoToDto(expected);
        assertEqualsCube(expected, actual);
    }

    @Test
    @MetamacMock(CUBE_06_DATASET_WITH_PARENT_NAME)
    public void datasetCubeDoToDtoWithParent() throws MetamacException {
        Cube expected = cubeMockFactory.retrieveMock(CUBE_06_DATASET_WITH_PARENT_NAME);
        CubeDto actual = publicationDo2DtoMapper.cubeDoToDto(expected);
        assertEqualsCube(expected, actual);
    }

    @Test
    @MetamacMock(CUBE_07_QUERY_WITH_PARENT_NAME)
    public void queryCubeDoToDtoWithParent() throws MetamacException {
        Cube expected = cubeMockFactory.retrieveMock(CUBE_07_QUERY_WITH_PARENT_NAME);
        CubeDto actual = publicationDo2DtoMapper.cubeDoToDto(expected);
        assertEqualsCube(expected, actual);
    }

    @Test
    @MetamacMock({CUBE_04_DATASET_NAME, CUBE_05_QUERY_NAME, CUBE_06_DATASET_WITH_PARENT_NAME, CUBE_07_QUERY_WITH_PARENT_NAME, CHAPTER_01_BASIC_NAME, CHAPTER_04_WITH_PARENT_NAME})
    public void elementsLevelDoListToDtoList() throws MetamacException {
        List<ElementLevel> expected = new ArrayList<ElementLevel>();
        expected.add(cubeMockFactory.retrieveMock(CUBE_04_DATASET_NAME).getElementLevel());
        expected.add(cubeMockFactory.retrieveMock(CUBE_05_QUERY_NAME).getElementLevel());
        expected.add(cubeMockFactory.retrieveMock(CUBE_06_DATASET_WITH_PARENT_NAME).getElementLevel());
        expected.add(cubeMockFactory.retrieveMock(CUBE_07_QUERY_WITH_PARENT_NAME).getElementLevel());
        expected.add(chapterMockFactory.retrieveMock(CHAPTER_01_BASIC_NAME).getElementLevel());
        expected.add(chapterMockFactory.retrieveMock(CHAPTER_04_WITH_PARENT_NAME).getElementLevel());

        List<ElementLevelDto> actual = publicationDo2DtoMapper.elementsLevelDoListToDtoList(expected);

        assertRelaxedEqualsElementLevelCollection(expected, actual);
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME)
    public void testPublicationVersionDoToPublicationRelatedResourceDto() throws MetamacException {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME);
        RelatedResourceDto actual = publicationDo2DtoMapper.publicationVersionDoToPublicationRelatedResourceDto(expected);
        assertEqualsPublication(expected, actual);
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME)
    public void testPublicationVersionDoToPublicationVersionRelatedResourceDto() throws MetamacException {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_04_FOR_PUBLICATION_03_AND_LAST_VERSION_NAME);
        RelatedResourceDto actual = publicationDo2DtoMapper.publicationVersionDoToPublicationVersionRelatedResourceDto(expected);
        assertEqualsPublicationVersion(expected, actual);
    }

    @Test
    @MetamacMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME)
    public void testPublicationVersionStructureDoToDto() throws MetamacException {
        PublicationVersion expected = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_22_WITH_COMPLEX_STRUCTURE_DRAFT_NAME);
        PublicationStructureDto actual = publicationDo2DtoMapper.publicationVersionStructureDoToDto(expected);
        assertEqualsPublicationVersionStructure(expected, actual);
    }

}
