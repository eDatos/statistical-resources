package org.siemac.metamac.statistical.resources.core.publication.utils.structure;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum.CHAPTER;
import static org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum.CUBE;
import static org.siemac.metamac.statistical.resources.core.utils.asserts.PublicationsAsserts.assertEqualsPublicationStructure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.MetamacExceptionItemBuilder;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
public class PublicationStructureTSVProcessorTest extends StatisticalResourcesBaseTest {

    @Autowired
    private PublicationStructureTSVProcessor publicationStructureTSVProcessor;

    @Test
    public void testParseEmptyFile() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_FORMAT_NOT_VALID, 1));
        File file = loadFile("publication_structure-empty.tsv");
        publicationStructureTSVProcessor.parse(file);
    }

    @Test
    public void testParseFileWithIncorrectHeader() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_HEADER_NOT_VALID, 1));
        File file = loadFile("publication_structure-header-not-valid.tsv");
        publicationStructureTSVProcessor.parse(file);
    }

    @Test
    public void testParseFileWithOnlyHeader() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_FORMAT_NOT_VALID, 2));
        File file = loadFile("publication_structure-only-header.tsv");
        publicationStructureTSVProcessor.parse(file);
    }

    @Test
    public void testParseFileWithIncorrectPublication() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_FORMAT_NOT_VALID, 2));
        {
            File file = loadFile("publication_structure-publication-not-valid.tsv");
            publicationStructureTSVProcessor.parse(file);
        }
        {
            File file = loadFile("publication_structure-publication-without-title.tsv");
            publicationStructureTSVProcessor.parse(file);
        }
    }

    @Test
    public void testParseFileWithOnlyPublication() throws Exception {
        File file = loadFile("publication_structure-only-publication.tsv");
        PublicationStructure publicationStructure = publicationStructureTSVProcessor.parse(file);
        assertEquals("Publication01", publicationStructure.getPublicationTitle());
        assertTrue(publicationStructure.getElements().isEmpty());
    }

    @Test
    public void testParseFileWithThreeLevels() throws Exception {

        PublicationStructure expected = createPublicationStructureWithThreeLevels();

        File file = loadFile("publication_structure-3-levels.tsv");
        PublicationStructure actual = publicationStructureTSVProcessor.parse(file);

        assertEqualsPublicationStructure(expected, actual);
    }

    @Test
    public void testParseFileWithChaptersWithRelatedResource() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem()
                .withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_CHAPTER_WITH_RELATED_RESOURCE).withMessageParameters(5).build());
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem()
                .withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_CHAPTER_WITH_RELATED_RESOURCE).withMessageParameters(11).build());
        expectedMetamacException(MetamacExceptionBuilder.builder().withExceptionItems(exceptionItems).build());

        File file = loadFile("publication_structure-chapters-with-related-resource.tsv");
        publicationStructureTSVProcessor.parse(file);
    }

    @Test
    public void testParseFileWithCubesWithoutRelatedResource() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem()
                .withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_CUBE_WITHOUT_RELATED_RESOURCE).withMessageParameters(5).build());
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem()
                .withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_CUBE_WITHOUT_RELATED_RESOURCE).withMessageParameters(18).build());
        expectedMetamacException(MetamacExceptionBuilder.builder().withExceptionItems(exceptionItems).build());

        File file = loadFile("publication_structure-cubes-without-related-resource.tsv");
        publicationStructureTSVProcessor.parse(file);
    }

    @Test
    public void testParseFileWithWrongElements() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem().withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_FORMAT_NOT_VALID)
                .withMessageParameters(3).build());
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem().withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_FORMAT_NOT_VALID)
                .withMessageParameters(4).build());
        expectedMetamacException(MetamacExceptionBuilder.builder().withExceptionItems(exceptionItems).build());

        File file = loadFile("publication_structure-wrong-element.tsv");
        publicationStructureTSVProcessor.parse(file);
    }

    @Test
    public void testParseFileWithElementsWithoutName() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem().withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_ELEMENT_WITH_EMTPY_NAME)
                .withMessageParameters(7).build());
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem().withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_ELEMENT_WITH_EMTPY_NAME)
                .withMessageParameters(15).build());
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem().withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_FORMAT_NOT_VALID)
                .withMessageParameters(15).build());
        expectedMetamacException(MetamacExceptionBuilder.builder().withExceptionItems(exceptionItems).build());

        File file = loadFile("publication_structure-elements-without-names.tsv");
        publicationStructureTSVProcessor.parse(file);
    }

    @Test
    public void testParseFileWithWrongRelatedResources() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem()
                .withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_CUBE_WITH_WRONG_RELATED_RESOURCE).withMessageParameters(6).build());
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem()
                .withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_CUBE_WITH_WRONG_RELATED_RESOURCE).withMessageParameters(9).build());
        expectedMetamacException(MetamacExceptionBuilder.builder().withExceptionItems(exceptionItems).build());

        File file = loadFile("publication_structure-wrong-related-resources.tsv");
        publicationStructureTSVProcessor.parse(file);
    }

    @Test
    public void testParseFileWithEmptyLine() throws Exception {
        File file = loadFile("publication_structure-empty-lines.tsv");
        PublicationStructure publicationStructure = publicationStructureTSVProcessor.parse(file);
        Assert.assertNotNull(publicationStructure);
    }

    @Test
    public void testParseFileWithCubeWithSubElements() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_CUBE_WITH_SUBELEMENTS, 19));
        File file = loadFile("publication_structure-cubes-with-subelements.tsv");
        publicationStructureTSVProcessor.parse(file);
    }

    @Test
    public void testParseFileWithMultipleErrors() throws Exception {
        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem().withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_FORMAT_NOT_VALID)
                .withMessageParameters(5).build());
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem()
                .withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_CHAPTER_WITH_RELATED_RESOURCE).withMessageParameters(7).build());
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem().withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_FORMAT_NOT_VALID)
                .withMessageParameters(9).build());
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem()
                .withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_CUBE_WITHOUT_RELATED_RESOURCE).withMessageParameters(11).build());
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem().withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_FORMAT_NOT_VALID)
                .withMessageParameters(16).build());
        exceptionItems.add(MetamacExceptionItemBuilder.metamacExceptionItem().withCommonServiceExceptionType(ServiceExceptionType.PUBLICATION_VERSION_STRUCTURE_IMPORTATION_ELEMENT_WITH_EMTPY_NAME)
                .withMessageParameters(16).build());
        expectedMetamacException(MetamacExceptionBuilder.builder().withExceptionItems(exceptionItems).build());

        File file = loadFile("publication_structure-multiple-errors.tsv");
        publicationStructureTSVProcessor.parse(file);
    }

    //
    // UTILITY METHODS
    //

    private PublicationStructure createPublicationStructureWithThreeLevels() {
        PublicationStructure publicationStructure = new PublicationStructure();
        publicationStructure.setPublicationTitle("Publication title");

        Element elementA = createChapter("Chapter level 01 - a");
        Element elementAA = createChapter("Chapter level 02 - a.a");
        elementAA.addElement(createCube("Table level 03 - a.a-a", "C00031A_000001", StatisticalResourceTypeEnum.QUERY));
        elementAA.addElement(createCube("Table level 03 - a.a-b", "C00031A_000002", StatisticalResourceTypeEnum.DATASET));
        elementAA.addElement(createCube("Table level 03 - a.a-c", "C00031A_000003", StatisticalResourceTypeEnum.QUERY));
        Element elementAB = createChapter("Chapter level 02 - a.b");
        elementAB.addElement(createCube("Table level 03 - a.b-a", "C00031A_000004", StatisticalResourceTypeEnum.DATASET));
        Element elementAC = createChapter("Chapter level 02 - a.c");
        elementAC.addElement(createCube("Table level 03 - a.c-a", "C00031A_000005", StatisticalResourceTypeEnum.DATASET));
        elementA.addElement(elementAA);
        elementA.addElement(elementAB);
        elementA.addElement(elementAC);

        Element elementB = createChapter("Chapter level 01 - b");
        elementB.addElement(createChapter("Chapter level 02 - b.a"));
        elementB.addElement(createChapter("Chapter level 02 - b.b"));
        elementB.addElement(createChapter("Chapter level 02 - b.c"));

        Element elementC = createChapter("Chapter level 01 - c");
        elementC.addElement(createCube("Table level 2", "C00031A_000006", StatisticalResourceTypeEnum.DATASET));

        Element elementD = createCube("Table level 1", "C00031A_000007", StatisticalResourceTypeEnum.QUERY);

        publicationStructure.addElement(elementA);
        publicationStructure.addElement(elementB);
        publicationStructure.addElement(elementC);
        publicationStructure.addElement(elementD);

        return publicationStructure;
    }

    private Element createChapter(String name) {
        return createElement(CHAPTER, name, null, null);
    }

    private Element createCube(String name, String relatedResourceCode, StatisticalResourceTypeEnum relatedResourceType) {
        return createElement(CUBE, name, relatedResourceCode, relatedResourceType);
    }

    private Element createElement(TypeRelatedResourceEnum type, String name, String relatedResourceCode, StatisticalResourceTypeEnum relatedResourceType) {
        Element element = new Element();
        element.setType(type);
        element.setTitle(name);
        element.setRelatedResourCode(relatedResourceCode);
        element.setRelatedResourceType(relatedResourceType);
        return element;
    }

    private File loadFile(String filename) throws Exception {
        return new File(this.getClass().getResource("/tsv/" + filename).getFile());
    }
}
