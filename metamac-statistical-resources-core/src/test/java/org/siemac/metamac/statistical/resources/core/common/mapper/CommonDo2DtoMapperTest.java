package org.siemac.metamac.statistical.resources.core.common.mapper;

import static org.junit.Assert.fail;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks.mockAgencyExternalItem;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks.mockInternationalString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.ent.domain.InternationalString;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
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
public class CommonDo2DtoMapperTest extends StatisticalResourcesBaseTest {

    @Autowired
    private CommonDo2DtoMapper  commonDo2DtoMapper;

    // ------------------------------------------------------------
    // INTERNATIONAL STRINGS
    // ------------------------------------------------------------

    @Test
    public void testInternationalStringDo2DtoWithNullParameter() throws Exception {
        testInternationalStringDoToDto(null);
    }

    @Test
    public void testInternationalStringDo2DtoWithExistsParameter() throws Exception {
        testInternationalStringDoToDto(mockInternationalString());
    }

    private void testInternationalStringDoToDto(InternationalString internationalString) throws Exception {
        InternationalStringDto result = commonDo2DtoMapper.internationalStringDoToDto(internationalString);
        BaseAsserts.assertEqualsInternationalString(internationalString, result);
    }

    // ------------------------------------------------------------
    // EXTERNAL ITEMS
    // ------------------------------------------------------------

    @Test
    public void testExternalItemDoToDtoWithNullParameter() throws Exception {
        testExternalItemDtoToDo(null);
    }

    @Test
    public void testExternalItemDoToDtoWithExistsParameter() throws Exception {
        testExternalItemDtoToDo(mockAgencyExternalItem());
    }

    @Test
    public void testExternalItemDoCollectionToDtoCollectionWithNullParameter() throws Exception {
        testExternalItemDoCollectionToDtoCollection(null);
    }

    @Test
    public void testExternalItemDoCollectionToDtoCollectionWithEmptyParameter() throws Exception {
        testExternalItemDoCollectionToDtoCollection(new ArrayList<ExternalItem>());
    }

    @Test
    public void testExternalItemDoCollectionToDtoCollectionWithExistsParameter() throws Exception {
        List<ExternalItem> entities = new ArrayList<ExternalItem>();
        entities.add(mockAgencyExternalItem());
        entities.add(mockAgencyExternalItem());

        testExternalItemDoCollectionToDtoCollection(entities);
    }

    private void testExternalItemDtoToDo(ExternalItem externalItem) throws Exception {
        ExternalItemDto result = commonDo2DtoMapper.externalItemDoToDto(externalItem);
        BaseAsserts.assertEqualsExternalItem(externalItem, result);
    }

    private void testExternalItemDoCollectionToDtoCollection(List<ExternalItem> entities) throws Exception {
        Collection<ExternalItemDto> result = commonDo2DtoMapper.externalItemDoCollectionToDtoCollection(entities);
        BaseAsserts.assertEqualsExternalItemCollectionMapper(entities, result);

    }

    // ------------------------------------------------------------
    // RELATED RESOURCES
    // ------------------------------------------------------------

    @Test
    public void testRelatedResourceDoToDtoWithNullParameter() throws Exception {
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDoToDtoWithExistsParameter() throws Exception {
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDoCollectionToDtoCollectionWithNullParameter() throws Exception {
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDoCollectionToDtoCollectionWithEmptyParameter() throws Exception {
        fail("TODO: Pendiente Robert");
    }

    @Test
    public void testRelatedResourceDoCollectionToDtoCollectionWithExistsParameter() throws Exception {
        fail("TODO: Pendiente Robert");
    }

    private void testRelatedResourceDoToDto(RelatedResource relatedResource) throws Exception {
        RelatedResourceDto result = commonDo2DtoMapper.relatedResourceDoToDto(relatedResource);
        BaseAsserts.assertEqualsRelatedResource(relatedResource, result);
    }

    private void testRelatedResourceDoCollectionToDtoCollection(List<RelatedResource> entities) throws Exception {
        Collection<RelatedResourceDto> result = commonDo2DtoMapper.relatedResourceDoCollectionToDtoCollection(entities);
        BaseAsserts.assertEqualsRelatedResourceCollectionMapper(entities, result);

    }

    // ------------------------------------------------------------
    // DATE
    // ------------------------------------------------------------

    @Test
    public void testDateDoToDtoWithNullParameter() throws Exception {
        testDateDoToDto(null);
    }

    @Test
    public void testDateDoToDtoWithExistsDate() throws Exception {
        testDateDoToDto(new DateTime());
    }

    private void testDateDoToDto(DateTime date) throws Exception {
        Date result = commonDo2DtoMapper.dateDoToDto(date);
        BaseAsserts.assertEqualsDate(date, result);
    }
}
