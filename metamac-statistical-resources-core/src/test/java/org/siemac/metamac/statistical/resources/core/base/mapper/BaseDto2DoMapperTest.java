package org.siemac.metamac.statistical.resources.core.base.mapper;

import static org.junit.Assert.*;

import org.apache.commons.lang.BooleanUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.MockUtil;
import org.siemac.metamac.common.test.mock.ConfigurationServiceMockImpl;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.VersionRationaleTypeRepository;
import org.siemac.metamac.statistical.resources.core.dto.SiemacMetadataStatisticalResourceDto;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDtoMocks;

public class BaseDto2DoMapperTest extends StatisticalResourcesBaseTest {

    private static final String METADATA_NAME_TEST = "METADATA_TEST";

    @Mock
    private ConfigurationService configurationService = new ConfigurationServiceMockImpl();
    
    @InjectMocks
    private BaseDto2DoMapper    baseDto2DoMapper = new BaseDto2DoMapperImpl();

    @Before
    public void prepareMocks() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testKeywordsUserEditionBeingEmpty() throws Exception {
        SiemacMetadataStatisticalResourceDto dto = new SiemacMetadataStatisticalResourceDto();
        SiemacMetadataStatisticalResource entity = new SiemacMetadataStatisticalResource();

        entity.setUserMofifiedKeywords(false);
        entity.setKeywords(null);
        
        dto.setKeywords(StatisticalResourcesDtoMocks.mockInternationalStringDto());
        
        entity = baseDto2DoMapper.siemacMetadataStatisticalResourceDtoToDo(dto, entity, METADATA_NAME_TEST);

        BaseAsserts.assertEqualsInternationalString(entity.getKeywords(), dto.getKeywords());
        assertTrue(entity.getUserMofifiedKeywords());
    }
    
    @Test
    public void testKeywordsUserEditionNotBeingEmpty() throws Exception {
        SiemacMetadataStatisticalResourceDto dto = new SiemacMetadataStatisticalResourceDto();
        SiemacMetadataStatisticalResource entity = new SiemacMetadataStatisticalResource();
        
        entity.setUserMofifiedKeywords(false);
        entity.setKeywords(StatisticalResourcesDoMocks.mockInternationalString("es","label1"));
        
        dto.setKeywords(StatisticalResourcesDtoMocks.mockInternationalStringDto("en","different label"));
        
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
        
        entity.setUserMofifiedKeywords(true);
        entity.setKeywords(StatisticalResourcesDoMocks.mockInternationalString("es", "label1", "en", "label2"));
        
        dto.setKeywords(StatisticalResourcesDtoMocks.mockInternationalStringDto("es", "label1", "en", "label2"));
        
        entity = baseDto2DoMapper.siemacMetadataStatisticalResourceDtoToDo(dto, entity, METADATA_NAME_TEST);
        
        BaseAsserts.assertEqualsInternationalString(entity.getKeywords(), dto.getKeywords());
        assertTrue(entity.getUserMofifiedKeywords());
    }
}
