package org.siemac.metamac.statistical.resources.core.query.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQueryVersion;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_01_SIMPLE_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_02_BASIC_WITH_GENERATED_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.*;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_11_DRAFT_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_21_FOR_QUERY_03_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory.QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06_NAME;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryTypeEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersion;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryVersionRepository;
import org.siemac.metamac.statistical.resources.core.utils.asserts.BaseAsserts;
import org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class QueryVersionRepositoryTest extends StatisticalResourcesBaseTest implements QueryVersionRepositoryTestBase {

    @Autowired
    protected QueryVersionRepository                queryVersionRepository;

    @Autowired
    private QueryVersionMockFactory                 queryVersionMockFactory;

    @Autowired
    private QueryMockFactory                        queryMockFactory;

    @Autowired
    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

    @Autowired
    private DatasetVersionMockFactory               datasetVersionMockFactory;

    @Override
    @Test
    @MetamacMock(QUERY_VERSION_01_WITH_SELECTION_NAME)
    public void testRetrieveByUrn() throws MetamacException {
        QueryVersion actual = queryVersionRepository.retrieveByUrn(queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEqualsQueryVersion(queryVersionMockFactory.retrieveMock(QUERY_VERSION_01_WITH_SELECTION_NAME), actual);
    }

    @Test
    public void testRetrieveByUrnNotFound() throws MetamacException {
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, URN_NOT_EXISTS));

        queryVersionRepository.retrieveByUrn(URN_NOT_EXISTS);
    }

    @Test
    @MetamacMock({DATASET_VERSION_06_FOR_QUERIES_NAME, QUERY_01_SIMPLE_NAME})
    public void testCreateQueryErrorQuerySelectionMustHaveDimensionUnique() throws Exception {
        thrown.expect(HibernateSystemException.class);

        Query query = queryMockFactory.retrieveMock(QUERY_01_SIMPLE_NAME);
        QueryVersion queryVersion = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME), true);

        // Set attributes that normally sets THE service and can not be null in database
        queryVersion.setStatus(QueryStatusEnum.ACTIVE);
        queryVersion.setType(QueryTypeEnum.FIXED);
        queryVersion.setQuery(query);

        // QuerySelectionItem 01
        QuerySelectionItem querySelectionItem01 = new QuerySelectionItem();
        querySelectionItem01.setQuery(queryVersion);

        CodeItem codeItem011 = new CodeItem();
        codeItem011.setCode("CODE_01");
        codeItem011.setTitle("Code 01");
        codeItem011.setQuerySelectionItem(querySelectionItem01);

        CodeItem codeItem012 = new CodeItem();
        codeItem012.setCode("CODE_02");
        codeItem012.setTitle("Code 02");
        codeItem012.setQuerySelectionItem(querySelectionItem01);

        querySelectionItem01.setDimension("DIMENSION_01");
        querySelectionItem01.addCode(codeItem011);
        querySelectionItem01.addCode(codeItem012);

        // QuerySelectionItem 02
        QuerySelectionItem querySelectionItem02 = new QuerySelectionItem();
        querySelectionItem02.setQuery(queryVersion);

        CodeItem codeItem021 = new CodeItem();
        codeItem021.setCode("CODE_03");
        codeItem021.setTitle("Code 03");
        codeItem021.setQuerySelectionItem(querySelectionItem02);

        CodeItem codeItem022 = new CodeItem();
        codeItem022.setCode("CODE_04");
        codeItem022.setTitle("Code 04");
        codeItem022.setQuerySelectionItem(querySelectionItem02);

        querySelectionItem02.setDimension("DIMENSION_01");
        querySelectionItem02.addCode(codeItem021);
        querySelectionItem02.addCode(codeItem022);

        // Add selections
        queryVersion.addSelection(querySelectionItem01);
        queryVersion.addSelection(querySelectionItem02);

        // Save
        queryVersionRepository.save(queryVersion);
    }

    @Test
    @MetamacMock({DATASET_VERSION_06_FOR_QUERIES_NAME, QUERY_01_SIMPLE_NAME})
    public void testCreateQueryErrorQuerySelectionItemMustHaveDimensionCodeUnique() throws Exception {
        thrown.expect(HibernateSystemException.class);

        Query query = queryMockFactory.retrieveMock(QUERY_01_SIMPLE_NAME);
        QueryVersion queryVersion = statisticalResourcesNotPersistedDoMocks.mockQueryVersionWithDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME), true);

        // Set attributes that normally sets de service and can not be null in database
        queryVersion.setStatus(QueryStatusEnum.ACTIVE);
        queryVersion.setType(QueryTypeEnum.FIXED);
        queryVersion.setQuery(query);

        // QuerySelectionItem 01
        QuerySelectionItem querySelectionItem01 = new QuerySelectionItem();
        querySelectionItem01.setQuery(queryVersion);

        CodeItem codeItem011 = new CodeItem();
        codeItem011.setCode("CODE_01");
        codeItem011.setTitle("Code 01");
        codeItem011.setQuerySelectionItem(querySelectionItem01);

        CodeItem codeItem012 = new CodeItem();
        codeItem012.setCode("CODE_01");
        codeItem012.setTitle("Code 01 B");
        codeItem012.setQuerySelectionItem(querySelectionItem01);

        querySelectionItem01.setDimension("DIMESNION_01");
        querySelectionItem01.addCode(codeItem011);
        querySelectionItem01.addCode(codeItem012);

        // QuerySelectionItem 02
        QuerySelectionItem querySelectionItem02 = new QuerySelectionItem();
        querySelectionItem02.setQuery(queryVersion);

        CodeItem codeItem021 = new CodeItem();
        codeItem021.setCode("CODE_01");
        codeItem021.setTitle("Code 01");
        codeItem021.setQuerySelectionItem(querySelectionItem02);

        CodeItem codeItem022 = new CodeItem();
        codeItem022.setCode("CODE_02");
        codeItem022.setTitle("Code 02");
        codeItem022.setQuerySelectionItem(querySelectionItem02);

        querySelectionItem02.setDimension("DIMESNION_02");
        querySelectionItem02.addCode(codeItem021);
        querySelectionItem02.addCode(codeItem022);

        // Add selections
        queryVersion.addSelection(querySelectionItem01);
        queryVersion.addSelection(querySelectionItem02);

        // Save
        queryVersionRepository.save(queryVersion);
    }

    @Override
    @Test
    @MetamacMock({QUERY_02_BASIC_WITH_GENERATED_VERSION_NAME, QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testRetrieveLastVersion() throws Exception {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_22_FOR_QUERY_03_AND_LAST_VERSION_NAME);
        QueryVersion actual = queryVersionRepository.retrieveLastVersion(queryUrn);
        assertEqualsQueryVersion(expected, actual);
    }

    @Test
    @MetamacMock({QUERY_02_BASIC_WITH_GENERATED_VERSION_NAME, QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME, QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME})
    public void testRetrieveLastVersionWithAllVersionsPublished() throws Exception {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05_NAME);
        QueryVersion actual = queryVersionRepository.retrieveLastVersion(queryUrn);
        assertEqualsQueryVersion(expected, actual);
    }

    @Test
    @MetamacMock({QUERY_02_BASIC_WITH_GENERATED_VERSION_NAME, QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME, QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME,
            QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME})
    public void testRetrieveLastVersionWithLatestVersionNoVisible() throws Exception {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_28_V2_PUBLISHED_NO_VISIBLE_FOR_QUERY_06_NAME);
        QueryVersion actual = queryVersionRepository.retrieveLastVersion(queryUrn);
        assertEqualsQueryVersion(expected, actual);
    }

    @Override
    @Test
    @MetamacMock({QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testRetrieveLastPublishedVersion() throws Exception {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_21_FOR_QUERY_03_NAME);
        QueryVersion actual = queryVersionRepository.retrieveLastPublishedVersion(queryUrn);
        assertEqualsQueryVersion(expected, actual);
    }

    @Test
    @MetamacMock({QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME})
    public void testRetrieveLastPublishedVersionWithAllVersionsPublished() throws Exception {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_26_V3_PUBLISHED_FOR_QUERY_05_NAME);
        QueryVersion actual = queryVersionRepository.retrieveLastPublishedVersion(queryUrn);
        assertEqualsQueryVersion(expected, actual);
    }

    @Test
    @MetamacMock({QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME, QUERY_VERSION_11_DRAFT_NAME})
    public void testRetrieveLastPublishedVersionWithoutVersionsPublished() throws Exception {
        String queryUrn = queryVersionMockFactory.retrieveMock(QUERY_VERSION_11_DRAFT_NAME).getQuery().getIdentifiableStatisticalResource().getUrn();
        QueryVersion expected = null;
        QueryVersion actual = queryVersionRepository.retrieveLastPublishedVersion(queryUrn);
        assertEqualsQueryVersion(expected, actual);
    }

    @Test
    @MetamacMock({QUERY_05_WITH_MULTIPLE_PUBLISHED_VERSIONS_NAME, QUERY_03_BASIC_WITH_2_QUERY_VERSIONS_NAME, QUERY_VERSION_11_DRAFT_NAME,
            QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME})
    public void testRetrieveLastPublishedVersionWithLatestVersionNoVisible() throws Exception {
        String queryUrn = queryMockFactory.retrieveMock(QUERY_06_WITH_MULTIPLE_PUBLISHED_VERSIONS_AND_LATEST_NO_VISIBLE_NAME).getIdentifiableStatisticalResource().getUrn();
        QueryVersion expected = queryVersionMockFactory.retrieveMock(QUERY_VERSION_27_V1_PUBLISHED_FOR_QUERY_06_NAME);
        QueryVersion actual = queryVersionRepository.retrieveLastPublishedVersion(queryUrn);
        assertEqualsQueryVersion(expected, actual);
    }
    
    
    @Test
    @Override
    @MetamacMock({QUERY_VERSION_08_BASIC_DISCONTINUED_NAME, QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME, DATASET_VERSION_03_FOR_DATASET_03_NAME, QUERY_VERSION_02_BASIC_ORDERED_01_NAME, QUERY_VERSION_03_BASIC_ORDERED_02_NAME})
    public void testFindLinkedToDatasetVersion() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_03_FOR_DATASET_03_NAME);
        
        QueryVersion query01 = queryVersionMockFactory.retrieveMock(QUERY_VERSION_08_BASIC_DISCONTINUED_NAME);
        QueryVersion query02 = queryVersionMockFactory.retrieveMock(QUERY_VERSION_09_BASIC_PENDING_REVIEW_NAME);
        
        List<QueryVersion> queryVersions = queryVersionRepository.findLinkedToDatasetVersion(datasetVersion.getId());
        
        QueryAsserts.assertEqualsQueryVersionCollection(Arrays.asList(query01, query02), queryVersions);
    }

}
