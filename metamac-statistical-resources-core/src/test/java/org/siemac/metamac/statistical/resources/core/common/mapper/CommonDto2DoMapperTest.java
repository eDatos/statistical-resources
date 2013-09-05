package org.siemac.metamac.statistical.resources.core.common.mapper;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.siemac.metamac.common.test.utils.MetamacMocks.mockExternalItemDtoComplete;
import static org.siemac.metamac.common.test.utils.MetamacMocks.mockInternationalStringDto;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks.mockAgencyExternalItem;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks.mockExternalItem;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks.mockInternationalString;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks.mockExternalItemDto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.siemac.metamac.common.test.constants.ConfigurationMockConstants;
import org.siemac.metamac.common.test.mock.ConfigurationServiceMockImpl;
import org.siemac.metamac.common.test.utils.MetamacAsserts.MapperEnum;
import org.siemac.metamac.common.test.utils.MetamacMocks;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.constants.CoreCommonConstants;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItemRepository;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.common.domain.InternationalStringRepository;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResourceRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class CommonDto2DoMapperTest extends StatisticalResourcesBaseTest {

    private static final String                     CODE_01                       = "mock01";
    private static final String                     CODE_02                       = "mock02";
    private static final String                     URN_01                        = "lorem:ipsum:externalItem:mock01:01";
    private static final String                     URN_02                        = "lorem:ipsum:externalItem:mock02:02";
    private static final String                     METADATA_NAME                 = "LOREM_IPSUM";

    protected ConfigurationService                  configurationService          = new ConfigurationServiceMockImpl();
    private final ExternalItemRepository            externalItemRepository        = Mockito.mock(ExternalItemRepository.class);
    private final InternationalStringRepository     internationalStringRepository = Mockito.mock(InternationalStringRepository.class);
    private final RelatedResourceRepository         relatedResourceRepository     = Mockito.mock(RelatedResourceRepository.class);
    private final DatasetVersionRepository          datasetVersionRepository      = Mockito.mock(DatasetVersionRepository.class);
    private final PublicationVersionRepository      publicationVersionRepository  = Mockito.mock(PublicationVersionRepository.class);

    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks = StatisticalResourcesNotPersistedDoMocks.getInstance();

    @Autowired
    private CommonDto2DoMapper                      commonDto2DoMapper;

    @Before
    public void setRepositoriesToMapper() throws Exception {
        setFieldToBaseMapper("configurationService", configurationService);
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

    private void setFieldToBaseMapper(String fieldName, ConfigurationService fieldValue) throws Exception {
        Field field = commonDto2DoMapper.getClass().getSuperclass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(commonDto2DoMapper, fieldValue);
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

    @Test
    public void testInternationalStringDto2DoWithDtoNullAndWithoutLocaleInDefaultLanguage() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_WITHOUT_DEFAULT_LANGUAGE, METADATA_NAME));
        testInternationalStringDtoToDo(mockInternationalStringDto("rs", "text"), null);
        Mockito.verify(internationalStringRepository, never()).delete(Mockito.any(InternationalString.class));
    }

    @Test
    public void testInternationalStringDto2DoWithoutLocaleInDefaultLanguage() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_WITHOUT_DEFAULT_LANGUAGE, METADATA_NAME));
        testInternationalStringDtoToDo(mockInternationalStringDto("rs", "text"), mockInternationalString(configurationService.retrieveLanguageDefault(), "texto"));
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
        ExternalItemDto externalItemDto = mockExternalItemDtoComplete(CODE_01, URN_01, TypeExternalArtefactsEnum.AGENCY);

        testExternalItemDtoToDo(externalItemDto, null);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoToDoWithExistsDtoAndNullDoAndDtoUrnNull() throws Exception {
        // EXISTS, NULL
        ExternalItemDto externalItemDto = mockExternalItemDtoComplete(null, null, TypeExternalArtefactsEnum.AGENCY);
        testExternalItemDtoToDo(externalItemDto, null);
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
        ExternalItemDto externalItemDto = MetamacMocks.mockExternalItemDtoComplete(CODE_01, URN_01, TypeExternalArtefactsEnum.AGENCY);
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
        dtos.add(mockExternalItemDtoComplete(CODE_01, URN_01, TypeExternalArtefactsEnum.AGENCY));
        dtos.add(mockExternalItemDtoComplete(CODE_02, URN_02, TypeExternalArtefactsEnum.AGENCY));
        List<ExternalItem> entities = new ArrayList<ExternalItem>();

        testExternalItemDtoListToDoList(dtos, entities);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithSameElements() throws Exception {
        // EXISTS, EXISTS: Same elements
        List<ExternalItemDto> dtos = new ArrayList<ExternalItemDto>();
        dtos.add(mockExternalItemDto("CODE_01", null, ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_01", "URN_01_provider", "URN_01",
                TypeExternalArtefactsEnum.AGENCY));
        dtos.add(mockExternalItemDto("CODE_02", null, ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_02", "URN_02_provider", "URN_02",
                TypeExternalArtefactsEnum.CATEGORY));
        List<ExternalItem> entities = new ArrayList<ExternalItem>();
        entities.add(mockExternalItem("CODE_01", null, ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_01", "URN_01_provider", "URN_01",
                TypeExternalArtefactsEnum.AGENCY));
        entities.add(mockExternalItem("CODE_02", null, ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_02", "URN_02_provider", "URN_02",
                TypeExternalArtefactsEnum.CATEGORY));

        testExternalItemDtoListToDoList(dtos, entities);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithMoreElementsInDtoList() throws Exception {
        // EXISTS, EXISTS: More elements
        List<ExternalItemDto> dtos = new ArrayList<ExternalItemDto>();
        dtos.add(mockExternalItemDto("CODE_01", null, ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_01", "URN_01_provider", "URN_01",
                TypeExternalArtefactsEnum.AGENCY));
        dtos.add(mockExternalItemDto("CODE_02", null, ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_02", "URN_02_provider", "URN_02",
                TypeExternalArtefactsEnum.CATEGORY));
        dtos.add(mockExternalItemDto("CODE_03", null, ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_03", "URN_03_provider", "URN_03",
                TypeExternalArtefactsEnum.CATEGORY));
        List<ExternalItem> entities = new ArrayList<ExternalItem>();
        entities.add(mockExternalItem("CODE_01", null, ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_01", "URN_01_provider", "URN_01",
                TypeExternalArtefactsEnum.AGENCY));
        entities.add(mockExternalItem("CODE_02", null, ConfigurationMockConstants.SRM_INTERNAL_API_URL_BASE + CoreCommonConstants.URL_SEPARATOR + "URI_02", "URN_02_provider", "URN_02",
                TypeExternalArtefactsEnum.CATEGORY));

        testExternalItemDtoListToDoList(dtos, entities);
        Mockito.verify(externalItemRepository, never()).delete(Mockito.any(ExternalItem.class));
    }

    @Test
    public void testExternalItemDtoListToDoListWithLessElementsInDtoList() throws Exception {
        // EXISTS, EXISTS: Less elements

        // Can not execute this test, because can not set id in entity
    }

    @Test
    public void testExternalItemDtoListToDoListWithSameListSizeAndDifferentElements() throws Exception {
        // EXISTS, EXISTS: Different elements
        List<ExternalItemDto> dtos = new ArrayList<ExternalItemDto>();
        dtos.add(mockExternalItemDtoComplete(CODE_01, URN_01, TypeExternalArtefactsEnum.AGENCY));
        dtos.add(mockExternalItemDtoComplete(CODE_02, URN_02, TypeExternalArtefactsEnum.AGENCY));
        List<ExternalItem> entities = new ArrayList<ExternalItem>();
        entities.add(mockAgencyExternalItem());
        entities.add(mockAgencyExternalItem());

        testExternalItemDtoListToDoList(dtos, entities);
        Mockito.verify(externalItemRepository, times(2)).delete(Mockito.any(ExternalItem.class));
    }

    private void testExternalItemDtoToDo(ExternalItemDto externalItemDto, ExternalItem externalItem) throws Exception {
        ExternalItem result = commonDto2DoMapper.externalItemDtoToDo(externalItemDto, externalItem, METADATA_NAME);
        BaseAsserts.assertEqualsExternalItem(result, externalItemDto, MapperEnum.DTO2DO);
    }

    private void testExternalItemDtoListToDoList(List<ExternalItemDto> dtos, List<ExternalItem> entities) throws Exception {
        Collection<ExternalItem> result = commonDto2DoMapper.externalItemDtoCollectionToDoList(dtos, entities, METADATA_NAME);
        BaseAsserts.assertEqualsExternalItemCollectionMapper(result, dtos);

    }

    // ------------------------------------------------------------
    // RELATED RESOURCES
    // ------------------------------------------------------------

    @Test
    public void testRelatedResourceDtoToDoWithNullDtoAndNullDo() throws Exception {
        testRelatedResourceDtoToDo(null, null);
        Mockito.verify(relatedResourceRepository, never()).delete(Mockito.any(RelatedResource.class));
    }

    @Test
    public void testRelatedResourceDtoToDoWithExistsDtoAndNullDo() throws Exception {
        RelatedResource expected = createRelatedResourceLinkedToMockedDatasetVersion(URN_01);
        DatasetVersion datasetVersion = expected.getDatasetVersion();

        Mockito.when(datasetVersionRepository.retrieveByUrn(datasetVersion.getSiemacMetadataStatisticalResource().getUrn())).thenReturn(datasetVersion);

        RelatedResourceDto relatedResourceDto = StatisticalResourcesDtoMocks.mockNotPersistedRelatedResourceDatasetVersionDto(datasetVersion);

        testRelatedResourceDtoToDo(expected, relatedResourceDto, null);
    }

    @Test
    public void testRelatedResourceDtoToDoWithExistsUrnNullDtoAndNullDo() throws Exception {
        expectedMetamacException(new MetamacException(ServiceExceptionType.METADATA_REQUIRED, METADATA_NAME));

        RelatedResource expected = createRelatedResourceLinkedToMockedDatasetVersion(URN_01);
        DatasetVersion datasetVersion = expected.getDatasetVersion();

        Mockito.when(datasetVersionRepository.retrieveByUrn(datasetVersion.getSiemacMetadataStatisticalResource().getUrn())).thenReturn(datasetVersion);

        RelatedResourceDto relatedResourceDto = StatisticalResourcesDtoMocks.mockNotPersistedRelatedResourceDatasetVersionDto(datasetVersion);
        relatedResourceDto.setUrn(null);

        testRelatedResourceDtoToDo(expected, relatedResourceDto, null);
    }

    @Test
    public void testRelatedResourceDtoToDoWithNullDtoAndExistsDo() throws Exception {
        RelatedResource target = createRelatedResourceLinkedToMockedDatasetVersion(URN_01);

        testRelatedResourceDtoToDo(null, target);

        Mockito.verify(relatedResourceRepository).delete(target);
    }

    @Test
    public void testRelatedResourceDtoToDoWithExistsDtoAndExistsDo() throws Exception {
        RelatedResource target = createRelatedResourceLinkedToMockedDatasetVersion(URN_02);
        RelatedResource expected = createRelatedResourceLinkedToMockedDatasetVersion(URN_01);

        RelatedResourceDto relatedResourceDto = StatisticalResourcesDtoMocks.mockNotPersistedRelatedResourceDatasetVersionDto(expected.getDatasetVersion());

        testRelatedResourceDtoToDo(expected, relatedResourceDto, target);
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithNullDtoListAndNullDoList() throws Exception {
        // null, null
        testRelatedResourceDtoListToDoList(null, null);
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithNullDtoListAndEmptyDoList() throws Exception {
        // null, EMPTY
        testRelatedResourceDtoListToDoList(null, new ArrayList<RelatedResource>());
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithEmptyDtoListAndNullDoList() throws Exception {
        // EMPTY, null
        testRelatedResourceDtoListToDoList(new ArrayList<RelatedResourceDto>(), null);
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithEmptyDtoListAndEmptyDoList() throws Exception {
        // EMPTY, EMPTY
        testRelatedResourceDtoListToDoList(new ArrayList<RelatedResourceDto>(), new ArrayList<RelatedResource>());
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithEmptyDtoListAndExistsDoList() throws Exception {
        // EMPTY, EXISTS
        List<RelatedResource> targets = new ArrayList<RelatedResource>();
        targets.add(createRelatedResourceLinkedToMockedDatasetVersion(URN_01));
        targets.add(createRelatedResourceLinkedToMockedDatasetVersion(URN_02));

        testRelatedResourceDtoListToDoList(new ArrayList<RelatedResourceDto>(), targets);

        for (RelatedResource resource : targets) {
            Mockito.verify(relatedResourceRepository).delete(resource);
        }
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithExistsDtoListAndEmptyDoList() throws Exception {
        // EXISTS, EMPTY
        List<RelatedResource> expected = new ArrayList<RelatedResource>();
        expected.add(createRelatedResourceLinkedToMockedDatasetVersion(URN_01));
        expected.add(createRelatedResourceLinkedToMockedDatasetVersion(URN_02));

        List<RelatedResourceDto> sources = new ArrayList<RelatedResourceDto>();
        sources.add(createSimpleRelatedResourceLinkedToMockedDatasetVersion(URN_01));
        sources.add(createSimpleRelatedResourceLinkedToMockedDatasetVersion(URN_02));

        testRelatedResourceDtoListToDoList(expected, sources, new ArrayList<RelatedResource>());
        Mockito.verify(relatedResourceRepository, never()).delete(Mockito.any(RelatedResource.class));
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithSameElements() throws Exception {
        // EXISTS, EXISTS: Same elements
        List<RelatedResource> target = new ArrayList<RelatedResource>();
        target.add(createRelatedResourceLinkedToMockedDatasetVersion(URN_01));
        target.add(createRelatedResourceLinkedToMockedDatasetVersion(URN_02));

        List<RelatedResourceDto> sources = new ArrayList<RelatedResourceDto>();
        sources.add(createSimpleRelatedResourceLinkedToMockedDatasetVersion(URN_01));
        sources.add(createSimpleRelatedResourceLinkedToMockedDatasetVersion(URN_02));

        testRelatedResourceDtoListToDoList(target, sources, target);
        Mockito.verify(relatedResourceRepository, never()).delete(Mockito.any(RelatedResource.class));
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithLessElementsInDtoList() throws Exception {
        // EXISTS, EXISTS: Less elements

        List<RelatedResource> targets = new ArrayList<RelatedResource>();
        targets.add(createRelatedResourceLinkedToMockedDatasetVersion(URN_01));
        targets.add(createRelatedResourceLinkedToMockedDatasetVersion(URN_02));

        RelatedResource deletedResource = targets.get(1);

        List<RelatedResource> expected = new ArrayList<RelatedResource>();
        expected.add(targets.get(0));

        List<RelatedResourceDto> sources = new ArrayList<RelatedResourceDto>();
        sources.add(createSimpleRelatedResourceLinkedToMockedDatasetVersion(URN_01));

        testRelatedResourceDtoListToDoList(expected, sources, targets);

        Mockito.verify(relatedResourceRepository).delete(deletedResource);
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithMoreElementsInDtoList() throws Exception {
        // EXISTS, EXISTS: More elements
        RelatedResource newResource = createRelatedResourceLinkedToMockedDatasetVersion(URN_02);

        List<RelatedResource> targets = new ArrayList<RelatedResource>();
        targets.add(createRelatedResourceLinkedToMockedDatasetVersion(URN_01));

        List<RelatedResource> expected = new ArrayList<RelatedResource>();
        expected.add(targets.get(0));
        expected.add(newResource);

        List<RelatedResourceDto> sources = new ArrayList<RelatedResourceDto>();
        sources.add(createSimpleRelatedResourceLinkedToMockedDatasetVersion(URN_01));
        sources.add(createSimpleRelatedResourceLinkedToMockedDatasetVersion(URN_02));

        testRelatedResourceDtoListToDoList(expected, sources, targets);

        Mockito.verify(relatedResourceRepository, never()).delete(Mockito.any(RelatedResource.class));
    }

    @Test
    public void testRelatedResourceDtoListToDoListWithSameListSizeAndDifferentElements() throws Exception {
        // EXISTS, EXISTS: Different elements
        RelatedResource deletedResource = createRelatedResourceLinkedToMockedDatasetVersion(URN_01);
        RelatedResource newResource = createRelatedResourceLinkedToMockedDatasetVersion(URN_02);

        List<RelatedResource> targets = new ArrayList<RelatedResource>();
        targets.add(deletedResource);

        List<RelatedResource> expected = new ArrayList<RelatedResource>();
        expected.add(newResource);

        List<RelatedResourceDto> sources = new ArrayList<RelatedResourceDto>();
        sources.add(createSimpleRelatedResourceLinkedToMockedDatasetVersion(URN_02));

        testRelatedResourceDtoListToDoList(expected, sources, targets);

        Mockito.verify(relatedResourceRepository).delete(deletedResource);
    }

    private void testRelatedResourceDtoToDo(RelatedResourceDto relatedResourceDto, RelatedResource relatedResource) throws Exception {
        RelatedResource result = commonDto2DoMapper.relatedResourceDtoToDo(relatedResourceDto, relatedResource, METADATA_NAME);
        BaseAsserts.assertEqualsRelatedResource(result, relatedResourceDto);
    }

    private void testRelatedResourceDtoToDo(RelatedResource expected, RelatedResourceDto relatedResourceDto, RelatedResource relatedResource) throws Exception {
        RelatedResource result = commonDto2DoMapper.relatedResourceDtoToDo(relatedResourceDto, relatedResource, METADATA_NAME);
        BaseAsserts.assertEqualsRelatedResource(expected, result);
    }

    private void testRelatedResourceDtoListToDoList(List<RelatedResourceDto> dtos, List<RelatedResource> entities) throws Exception {
        List<RelatedResource> result = commonDto2DoMapper.relatedResourceDtoListToDoList(dtos, entities, METADATA_NAME);
        BaseAsserts.assertEqualsRelatedResourceCollectionMapper(result, dtos);
    }

    private void testRelatedResourceDtoListToDoList(List<RelatedResource> expected, List<RelatedResourceDto> dtos, List<RelatedResource> entities) throws Exception {
        List<RelatedResource> result = commonDto2DoMapper.relatedResourceDtoListToDoList(dtos, entities, METADATA_NAME);
        BaseAsserts.assertEqualsRelatedResourceCollection(result, expected);
    }

    private RelatedResource createRelatedResourceLinkedToMockedDatasetVersion(String datasetVersionUrn) throws MetamacException {
        RelatedResource resource = statisticalResourcesNotPersistedDoMocks.mockRelatedResourceLinkedToMockedDatasetVersion(datasetVersionUrn);

        DatasetVersion datasetVersion = resource.getDatasetVersion();
        Mockito.when(datasetVersionRepository.retrieveByUrn(datasetVersionUrn)).thenReturn(datasetVersion);

        return resource;
    }

    private RelatedResourceDto createSimpleRelatedResourceLinkedToMockedDatasetVersion(String datasetVersionUrn) {
        RelatedResourceDto target = new RelatedResourceDto();
        target.setUrn(datasetVersionUrn);
        target.setType(TypeRelatedResourceEnum.DATASET_VERSION);
        return target;
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
