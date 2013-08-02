package org.siemac.metamac.statistical.resources.core.base.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.common.test.mock.ConfigurationServiceMockImpl;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.ExternalItemRepository;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDto2DoMapperTest extends StatisticalResourcesBaseTest {

    private static final String          METADATA_NAME_TEST = "METADATA_TEST";

    @Mock
    private ExternalItemRepository       externalItemRepository;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private ConfigurationServiceMockImpl configurationService;

    @InjectMocks
    private BaseDto2DoMapper             baseDto2DoMapper   = new BaseDto2DoMapperImpl();

    @Before
    public void prepareMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCalculateKeywords() throws Exception {
        SiemacMetadataStatisticalResourceDto dto = new SiemacMetadataStatisticalResourceDto();
        SiemacMetadataStatisticalResource entity = new SiemacMetadataStatisticalResource();

        entity.setUserMofifiedKeywords(false);
        entity.setKeywords(null);

        dto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "keytitulo1 keytitulo2 no"));
        dto.setDescription(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "keydesc1 keydesc2 not"));
        dto.setKeywords(null);

        entity = baseDto2DoMapper.siemacMetadataStatisticalResourceDtoToDo(dto, entity, METADATA_NAME_TEST);

        assertContainsKeywordsInLocale(entity, "es", "keytitulo1", "keytitulo2", "keydesc1", "keydesc2");

        assertFalse(entity.getUserMofifiedKeywords());
    }

    @Test
    public void testKeywordsUserEdition() throws Exception {
        SiemacMetadataStatisticalResourceDto dto = new SiemacMetadataStatisticalResourceDto();
        SiemacMetadataStatisticalResource entity = new SiemacMetadataStatisticalResource();

        entity.setTitle(StatisticalResourcesDoMocks.mockInternationalString("es", "label1 no"));
        entity.setDescription(StatisticalResourcesDoMocks.mockInternationalString("es", "label2 not"));
        entity.setUserMofifiedKeywords(false);
        entity.setKeywords(StatisticalResourcesDoMocks.mockInternationalString("es", "label1 label2"));

        dto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "keytitulo1 keytitulo2 no"));
        dto.setDescription(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "keydesc1 keydesc2 not"));
        dto.setKeywords(null);
        // keywords manually modified
        dto.setKeywords(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "label3 label4"));

        entity = baseDto2DoMapper.siemacMetadataStatisticalResourceDtoToDo(dto, entity, METADATA_NAME_TEST);

        BaseAsserts.assertEqualsInternationalString(entity.getKeywords(), dto.getKeywords());
        assertTrue(entity.getUserMofifiedKeywords());
    }

    @Test
    public void testKeywordsNonUserEditionNull() throws Exception {
        SiemacMetadataStatisticalResourceDto dto = new SiemacMetadataStatisticalResourceDto();
        SiemacMetadataStatisticalResource entity = new SiemacMetadataStatisticalResource();

        entity.setUserMofifiedKeywords(false);
        entity.setKeywords(null);

        dto.setKeywords(null);

        entity = baseDto2DoMapper.siemacMetadataStatisticalResourceDtoToDo(dto, entity, METADATA_NAME_TEST);

        BaseAsserts.assertEqualsInternationalString(entity.getKeywords(), dto.getKeywords());
        assertFalse(entity.getUserMofifiedKeywords());
    }

    @Test
    public void testKeywordsNonUserEdition() throws Exception {
        SiemacMetadataStatisticalResourceDto dto = new SiemacMetadataStatisticalResourceDto();
        SiemacMetadataStatisticalResource entity = new SiemacMetadataStatisticalResource();

        entity.setUserMofifiedKeywords(false);
        entity.setKeywords(StatisticalResourcesDoMocks.mockInternationalString("es", "label1", "en", "label2"));

        dto.setKeywords(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "label1", "en", "label2"));

        entity = baseDto2DoMapper.siemacMetadataStatisticalResourceDtoToDo(dto, entity, METADATA_NAME_TEST);

        BaseAsserts.assertEqualsInternationalString(entity.getKeywords(), dto.getKeywords());
        assertFalse(entity.getUserMofifiedKeywords());
    }

    @Test
    public void testKeywordsNonUserEditionPreviouslyChanged() throws Exception {
        SiemacMetadataStatisticalResourceDto dto = new SiemacMetadataStatisticalResourceDto();
        SiemacMetadataStatisticalResource entity = new SiemacMetadataStatisticalResource();

        entity.setTitle(StatisticalResourcesDoMocks.mockInternationalString("es", "label1 no"));
        entity.setDescription(StatisticalResourcesDoMocks.mockInternationalString("en", "label2 not"));
        entity.setUserMofifiedKeywords(true);
        entity.setKeywords(StatisticalResourcesDoMocks.mockInternationalString("es", "label1", "en", "label2"));

        dto.setTitle(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "label1 no"));
        dto.setDescription(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "label2* not"));
        dto.setKeywords(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "label1 label2*"));

        entity = baseDto2DoMapper.siemacMetadataStatisticalResourceDtoToDo(dto, entity, METADATA_NAME_TEST);

        BaseAsserts.assertEqualsInternationalString(entity.getKeywords(), dto.getKeywords());
        assertTrue(entity.getUserMofifiedKeywords());
    }

    @Test
    public void testMapLanguageOnCreation() throws Exception {
        SiemacMetadataStatisticalResourceDto dto = new SiemacMetadataStatisticalResourceDto();
        SiemacMetadataStatisticalResource entity = new SiemacMetadataStatisticalResource();

        dto.setId(null);
        dto.setLanguage(StatisticalResourcesDtoMocks.mockCodeExternalItemDto("lang_es"));

        entity = baseDto2DoMapper.siemacMetadataStatisticalResourceDtoToDo(dto, entity, METADATA_NAME_TEST);

        assertEquals(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_es").getUrn(), entity.getLanguage().getUrn());
    }

    @Test
    public void testLanguageIgnoreOnEdition() throws Exception {
        SiemacMetadataStatisticalResourceDto dto = new SiemacMetadataStatisticalResourceDto();
        SiemacMetadataStatisticalResource entity = new SiemacMetadataStatisticalResource();

        entity.setLanguage(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_es"));
        entity.getLanguages().clear();
        entity.getLanguages().add(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_es"));

        dto.setId(1L);
//        dto.setLanguage(StatisticalResourcesDtoMocks.mockCodeExternalItemDto("lang_en"));

        entity = baseDto2DoMapper.siemacMetadataStatisticalResourceDtoToDo(dto, entity, METADATA_NAME_TEST);

        BaseAsserts.assertEqualsExternalItem(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_es"), entity.getLanguage());
    }

    @Test
    public void testLanguagesForceIncludeLanguage() throws Exception {
        SiemacMetadataStatisticalResourceDto dto = new SiemacMetadataStatisticalResourceDto();
        SiemacMetadataStatisticalResource entity = new SiemacMetadataStatisticalResource();

        entity.setLanguage(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_es"));
        entity.getLanguages().clear();
        entity.getLanguages().add(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_es"));

        dto.setId(1L);
        dto.getLanguages().clear();
        dto.getLanguages().add(StatisticalResourcesDtoMocks.mockCodeExternalItemDto("lang_en"));

        entity = baseDto2DoMapper.siemacMetadataStatisticalResourceDtoToDo(dto, entity, METADATA_NAME_TEST);

        List<ExternalItem> expectedLanguages = new ArrayList<ExternalItem>();
        expectedLanguages.add(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_es"));
        expectedLanguages.add(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_en"));

        BaseAsserts.assertEqualsExternalItemCollection(expectedLanguages, entity.getLanguages());
    }

    @Test
    public void testLanguagesShouldAlwaysIncludeLanguage() throws Exception {
        SiemacMetadataStatisticalResourceDto dto = new SiemacMetadataStatisticalResourceDto();
        SiemacMetadataStatisticalResource entity = new SiemacMetadataStatisticalResource();

        entity.setLanguage(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_es"));
        entity.getLanguages().clear();
        entity.getLanguages().add(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_es"));

        dto.getLanguages().clear();
        dto.getLanguages().add(StatisticalResourcesDtoMocks.mockCodeExternalItemDto("lang_es"));
        dto.getLanguages().add(StatisticalResourcesDtoMocks.mockCodeExternalItemDto("lang_en"));

        entity = baseDto2DoMapper.siemacMetadataStatisticalResourceDtoToDo(dto, entity, METADATA_NAME_TEST);

        List<ExternalItem> expectedLanguages = new ArrayList<ExternalItem>();
        expectedLanguages.add(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_es"));
        expectedLanguages.add(StatisticalResourcesDoMocks.mockCodeExternalItem("lang_en"));

        BaseAsserts.assertEqualsExternalItemCollection(expectedLanguages, entity.getLanguages());
    }

    private void assertContainsKeywordsInLocale(SiemacMetadataStatisticalResource resource, String locale, String... keywords) {
        assertNotNull(resource.getKeywords());
        String localisedKeywords = resource.getKeywords().getLocalisedLabel(locale);
        assertNotNull(localisedKeywords);
        List<String> actualKeywords = Arrays.asList(localisedKeywords.split("\\s"));
        assertEquals(keywords.length, actualKeywords.size());
        assertTrue(actualKeywords.containsAll(Arrays.asList(keywords)));
    }

}
