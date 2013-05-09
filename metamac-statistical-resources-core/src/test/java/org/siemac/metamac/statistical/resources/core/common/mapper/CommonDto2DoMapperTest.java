package org.siemac.metamac.statistical.resources.core.common.mapper;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.siemac.metamac.common.test.utils.MetamacMocks.mockExternalItemDtoComplete;
import static org.siemac.metamac.common.test.utils.MetamacMocks.mockInternationalStringDto;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks.mockAgencyExternalItem;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks.mockInternationalString;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.ExternalItemRepository;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.core.common.ent.domain.InternationalStringRepository;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class CommonDto2DoMapperTest extends StatisticalResourcesBaseTest {

    private static final String           URN_01                        = "lorem:ipsum:externalItem:mock:01";
    private static final String           URN_02                        = "lorem:ipsum:externalItem:mock:02";
    private static final String           METADATA_NAME                 = "LOREM_IPSUM";

    private ExternalItemRepository        externalItemRepository        = Mockito.mock(ExternalItemRepository.class);
    private InternationalStringRepository internationalStringRepository = Mockito.mock(InternationalStringRepository.class);
    private RelatedResourceRepository     relatedResourceRepository     = Mockito.mock(RelatedResourceRepository.class);
    private DatasetVersionRepository      datasetVersionRepository      = Mockito.mock(DatasetVersionRepository.class);
    private PublicationVersionRepository  publicationVersionRepository  = Mockito.mock(PublicationVersionRepository.class);

    @Autowired
    private CommonDto2DoMapper            commonDto2DoMapper;

    @Before
    public void setRepositoriesToMapper() throws Exception {
        setRepositoryToMapper(externalItemRepository, "externalItemRepository");
        setRepositoryToMapper(internationalStringRepository, "internationalStringRepository");
        setRepositoryToMapper(relatedResourceRepository, "relatedResourceRepository");
        setRepositoryToMapper(datasetVersionRepository, "datasetVersionRepository");
        setRepositoryToMapper(publicationVersionRepository, "publicationVersionRepository");
    }

    private void setRepositoryToMapper(Object repository, String fieldName) throws Exception {
        Field externalItemRepositoryField = commonDto2DoMapper.getClass().getDeclaredField(fieldName);
        externalItemRepositoryField.setAccessible(true);
        externalItemRepositoryField.set(commonDto2DoMapper, repository);
    }

    @After
    public void validateMockitoUsage() {
        Mockito.validateMockitoUsage();
    }

    // ------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------

    @Test
    public void testInternationalStringDto2DoWithDtoNullAndDoNull() throws Exception {
        // NULL, NULL
        testInternationalStringDtoToDo(null, null);
        Mockito.verify(internationalStringRepository, never()).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoNullAndDoExists() throws Exception {
        // NULL, EXISTS
        testInternationalStringDtoToDo(null, mockInternationalString());
        Mockito.verify(internationalStringRepository).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoExistsAndDoNull() throws Exception {
        // EXISTS, NULL
        testInternationalStringDtoToDo(mockInternationalStringDto(), null);
        Mockito.verify(internationalStringRepository, never()).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoExistsAndDoExists() throws Exception {
        // EXISTS, EXISTS
        testInternationalStringDtoToDo(mockInternationalStringDto(), mockInternationalString());
        Mockito.verify(internationalStringRepository, never()).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithDtoExistsAndDoNullAndDtoWithoutLocalisedStrings() throws Exception {
        // EXISTS, EXISTS
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, METADATA_NAME));

        InternationalStringDto internationalStringDto = mockInternationalStringDto();
        internationalStringDto.getTexts().clear();

        testInternationalStringDtoToDo(internationalStringDto, null);
        Mockito.verify(internationalStringRepository, never()).delete(Mockito.any(InternationalString.class));
    }

    private void testInternationalStringDtoToDo(InternationalStringDto internationalStringDto, InternationalString internationalString) throws Exception {
        InternationalString result = commonDto2DoMapper.internationalStringDtoToDo(internationalStringDto, internationalString, METADATA_NAME);
        BaseAsserts.assertEqualsInternationalString(result, internationalStringDto);
    }

    // ------------------------------------------------------------
    // EXTERNAL ITEMS
    // ------------------------------------------------------------

    @Test
    public void testExternalItemDtoToDoWithNullDtoAndNullDo() throws Exception {
        // NULL, NULL
        testExternalItemDtoToDo(null, null);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoToDoWithExistsDtoAndNullDo() throws Exception {
        // EXISTS, NULL
        ExternalItemDto externalItemDto = mockExternalItemDtoComplete(URN_01, TypeExternalArtefactsEnum.AGENCY);

        testExternalItemDtoToDo(externalItemDto, null);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoToDoWithNullDtoAndExistsDo() throws Exception {
        // NULL, EXISTS
        ExternalItem externalItem = mockAgencyExternalItem();

        testExternalItemDtoToDo(null, externalItem);
        Mockito.verify(externalItemRepository).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoToDoWithExistsDtoAndExistsDo() throws Exception {
        // EXISTS, EXISTS
        ExternalItemDto externalItemDto = MetamacMocks.mockExternalItemDtoComplete(URN_01, TypeExternalArtefactsEnum.AGENCY);
        ExternalItem externalItem = mockAgencyExternalItem();

        testExternalItemDtoToDo(externalItemDto, externalItem);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithNullDtoListAndNullDoList() throws Exception {
        // NULL, NULL
        testExternalItemDtoListToDoList(null, null);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithNullDtoListAndEmptyDoList() throws Exception {
        // NULL, EMPTY
        List<ExternalItem> entities = new ArrayList<ExternalItem>();

        testExternalItemDtoListToDoList(null, entities);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithEmptyDtoListAndNullDoList() throws Exception {
        // EMPTY, NULL
        List<ExternalItemDto> dtos = new ArrayList<ExternalItemDto>();

        testExternalItemDtoListToDoList(dtos, null);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithEmptyDtoListAndEmptyDoList() throws Exception {
        // EMPTY, EMPTY
        List<ExternalItemDto> dtos = new ArrayList<ExternalItemDto>();
        List<ExternalItem> entities = new ArrayList<ExternalItem>();

        testExternalItemDtoListToDoList(dtos, entities);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithEmptyDtoListAndExistsDoList() throws Exception {
        // EMPTY, EXISTS
        List<ExternalItemDto> dtos = new ArrayList<ExternalItemDto>();
        List<ExternalItem> entities = new ArrayList<ExternalItem>();
        entities.add(mockAgencyExternalItem());
        entities.add(mockAgencyExternalItem());

        testExternalItemDtoListToDoList(dtos, entities);
        Mockito.verify(externalItemRepository, times(2)).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithExistsDtoListAndEmptyDoList() throws Exception {
        // EXISTS, EMPTY
        List<ExternalItemDto> dtos = new ArrayList<ExternalItemDto>();
        dtos.add(mockExternalItemDtoComplete(URN_01, TypeExternalArtefactsEnum.AGENCY));
        dtos.add(mockExternalItemDtoComplete(URN_02, TypeExternalArtefactsEnum.AGENCY));
        List<ExternalItem> entities = new ArrayList<ExternalItem>();

        testExternalItemDtoListToDoList(dtos, entities);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithSameElements() throws Exception {
        // EXISTS, EXISTS: Same elements
        List<ExternalItemDto> dtos = new ArrayList<ExternalItemDto>();
        dtos.add(new ExternalItemDto("CODE_01", "URI_01", "URN_01", TypeExternalArtefactsEnum.AGENCY));
        dtos.add(new ExternalItemDto("CODE_02", "URI_02", "URN_02", TypeExternalArtefactsEnum.CATEGORY));
        List<ExternalItem> entities = new ArrayList<ExternalItem>();
        entities.add(new ExternalItem("CODE_01", "URI_01", "URN_01", TypeExternalArtefactsEnum.AGENCY));
        entities.add(new ExternalItem("CODE_02", "URI_02", "URN_02", TypeExternalArtefactsEnum.CATEGORY));

        testExternalItemDtoListToDoList(dtos, entities);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithMoreElementsInDtoList() throws Exception {
        // EXISTS, EXISTS: More elements
        List<ExternalItemDto> dtos = new ArrayList<ExternalItemDto>();
        dtos.add(new ExternalItemDto("CODE_01", "URI_01", "URN_01", TypeExternalArtefactsEnum.AGENCY));
        dtos.add(new ExternalItemDto("CODE_02", "URI_02", "URN_02", TypeExternalArtefactsEnum.CATEGORY));
        dtos.add(new ExternalItemDto("CODE_03", "URI_03", "URN_03", TypeExternalArtefactsEnum.CATEGORY));
        List<ExternalItem> entities = new ArrayList<ExternalItem>();
        entities.add(new ExternalItem("CODE_01", "URI_01", "URN_01", TypeExternalArtefactsEnum.AGENCY));
        entities.add(new ExternalItem("CODE_02", "URI_02", "URN_02", TypeExternalArtefactsEnum.CATEGORY));

        testExternalItemDtoListToDoList(dtos, entities);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithLessElementsInDtoList() throws Exception {
        // EXISTS, EXISTS: Less elements
        List<ExternalItemDto> dtos = new ArrayList<ExternalItemDto>();
        dtos.add(new ExternalItemDto("CODE_01", "URI_01", "URN_01", TypeExternalArtefactsEnum.AGENCY));
        dtos.add(new ExternalItemDto("CODE_02", "URI_02", "URN_02", TypeExternalArtefactsEnum.CATEGORY));
        List<ExternalItem> entities = new ArrayList<ExternalItem>();
        entities.add(new ExternalItem("CODE_01", "URI_01", "URN_01", TypeExternalArtefactsEnum.AGENCY));
        entities.add(new ExternalItem("CODE_02", "URI_02", "URN_02", TypeExternalArtefactsEnum.CATEGORY));
        entities.add(new ExternalItem("CODE_03", "URI_03", "URN_03", TypeExternalArtefactsEnum.CATEGORY));

        testExternalItemDtoListToDoList(dtos, entities);
        Mockito.verify(externalItemRepository).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithSameListSizeAndDifferentElements() throws Exception {
        // EXISTS, EXISTS: Different elements
        List<ExternalItemDto> dtos = new ArrayList<ExternalItemDto>();
        dtos.add(mockExternalItemDtoComplete(URN_01, TypeExternalArtefactsEnum.AGENCY));
        dtos.add(mockExternalItemDtoComplete(URN_02, TypeExternalArtefactsEnum.AGENCY));
        List<ExternalItem> entities = new ArrayList<ExternalItem>();
        entities.add(mockAgencyExternalItem());
        entities.add(mockAgencyExternalItem());

        testExternalItemDtoListToDoList(dtos, entities);
        Mockito.verify(externalItemRepository, times(2)).delete(Mockito.any(ExternalItem.class));
    }

    private void testExternalItemDtoToDo(ExternalItemDto externalItemDto, ExternalItem externalItem) throws Exception {
        ExternalItem result = commonDto2DoMapper.externalItemDtoToDo(externalItemDto, externalItem, METADATA_NAME);
        BaseAsserts.assertEqualsExternalItem(result, externalItemDto);
    }

    private void testExternalItemDtoListToDoList(List<ExternalItemDto> dtos, List<ExternalItem> entities) throws Exception {
        List<ExternalItem> result = commonDto2DoMapper.externalItemDtoListToDoList(dtos, entities, METADATA_NAME);
        BaseAsserts.assertEqualsExternalItemCollectionMapper(result, dtos);

    }

    // ------------------------------------------------------------
    // RELATED RESOURCES
    // ------------------------------------------------------------

    @Test
    public void testRelatedResourceDtoToDoWithNullDtoAndNullDo() throws Exception {
        // NULL, NULL
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoToDoWithExistsDtoAndNullDo() throws Exception {
        // EXISTS, NULL
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoToDoWithNullDtoAndExistsDo() throws Exception {
        // NULL, EXISTS
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoToDoWithExistsDtoAndExistsDo() throws Exception {
        // EXISTS, EXISTS
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithNullDtoListAndNullDoList() throws Exception {
        // NULL, NULL
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithNullDtoListAndEmptyDoList() throws Exception {
        // NULL, EMPTY
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithEmptyDtoListAndNullDoList() throws Exception {
        // EMPTY, NULL
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithEmptyDtoListAndEmptyDoList() throws Exception {
        // EMPTY, EMPTY
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithEmptyDtoListAndExistsDoList() throws Exception {
        // EMPTY, EXISTS
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithExistsDtoListAndEmptyDoList() throws Exception {
        // EXISTS, EMPTY
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithSameElements() throws Exception {
        // EXISTS, EXISTS: Same elements
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithMoreElementsInDtoList() throws Exception {
        // EXISTS, EXISTS: More elements
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithLessElementsInDtoList() throws Exception {
        // EXISTS, EXISTS: Less elements
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithSameListSizeAndDifferentElements() throws Exception {
        // EXISTS, EXISTS: Different elements
        fail("TODO: Pendiente Robert");
    }

    private void testRelatedResourceDtoToDo(RelatedResourceDto relatedResourceDto, RelatedResource relatedResource) throws Exception {
        RelatedResource result = commonDto2DoMapper.relatedResourceDtoToDo(relatedResourceDto, relatedResource, METADATA_NAME);
        BaseAsserts.assertEqualsRelatedResource(result, relatedResourceDto);
    }

    private void testRelatedResourceDtoListToDoList(List<RelatedResourceDto> dtos, List<RelatedResource> entities) throws Exception {
        List<RelatedResource> result = commonDto2DoMapper.relatedResourceDtoListToDoList(dtos, entities, METADATA_NAME);
        BaseAsserts.assertEqualsRelatedResourceCollectionMapper(result, dtos);

    }

    // ------------------------------------------------------------
    // DATE
    // ------------------------------------------------------------

    @Test
    public void testDateDtoToDoWithNullParameter() throws Exception {
        testDateDtoToDo(null);
    }

    @Test
    public void testDateDtoToDoWithExistsDate() throws Exception {
        testDateDtoToDo(new Date());
    }

    private void testDateDtoToDo(Date date) throws Exception {
        DateTime result = commonDto2DoMapper.dateDtoToDo(date);
        BaseAsserts.assertEqualsDate(result, date);
    }
}
