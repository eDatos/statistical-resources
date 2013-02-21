package org.siemac.metamac.statistical.resources.core.query.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.asserts.QueryAsserts.assertEqualsQuery;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_06_FOR_QUERIES_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory.QUERY_01_WITH_SELECTION_NAME;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.enume.query.domain.QueryStatusEnum;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.query.domain.CodeItem;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.siemac.metamac.statistical.resources.core.query.domain.QuerySelectionItem;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.QueryMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesNotPersistedDoMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateSystemException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring based transactional test with DbUnit support.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional
public class QueryRepositoryTest extends StatisticalResourcesBaseTest implements QueryRepositoryTestBase {

    @Autowired
    private QueryRepository                         queryRepository;

    @Autowired
    private QueryMockFactory                        queryMockFactory;

    @Autowired
    private StatisticalResourcesNotPersistedDoMocks statisticalResourcesNotPersistedDoMocks;

    @Autowired
    private DatasetVersionMockFactory               datasetVersionMockFactory;
    

    @Override
    @Test
    @MetamacMock(QUERY_01_WITH_SELECTION_NAME)
    public void testRetrieveByUrn() throws MetamacException {
        Query actual = queryRepository.retrieveByUrn(queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME).getLifeCycleStatisticalResource().getUrn());
        assertEqualsQuery(queryMockFactory.retrieveMock(QUERY_01_WITH_SELECTION_NAME), actual);
    }

    @Test
    public void testRetrieveByUrnNotFound() throws MetamacException {
        expectedMetamacException(new MetamacException(ServiceExceptionType.QUERY_NOT_FOUND, URN_NOT_EXISTS), 1);

        queryRepository.retrieveByUrn(URN_NOT_EXISTS);
    }

    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryErrorQueryDimensionUnique() throws Exception {
        thrown.expect(HibernateSystemException.class);
        
        Query query = statisticalResourcesNotPersistedDoMocks.mockQueryWithDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));

        // Set attributes that normally sets de service and can not be null in database
        query.setStatus(QueryStatusEnum.ACTIVE);
        
        // QuerySelectionItem 01
        QuerySelectionItem querySelectionItem01 = new QuerySelectionItem();
        querySelectionItem01.setQuery(query);

        CodeItem codeItem011 = new CodeItem();
        codeItem011.setCode("CODE_01");
        codeItem011.setQuerySelectionItem(querySelectionItem01);

        CodeItem codeItem012 = new CodeItem();
        codeItem012.setCode("CODE_02");
        codeItem012.setQuerySelectionItem(querySelectionItem01);

        querySelectionItem01.setDimension("DIMESNION_01");
        querySelectionItem01.addCode(codeItem011);
        querySelectionItem01.addCode(codeItem012);

        // QuerySelectionItem 02
        QuerySelectionItem querySelectionItem02 = new QuerySelectionItem();
        querySelectionItem02.setQuery(query);

        CodeItem codeItem021 = new CodeItem();
        codeItem021.setCode("CODE_03");
        codeItem021.setQuerySelectionItem(querySelectionItem02);

        CodeItem codeItem022 = new CodeItem();
        codeItem022.setCode("CODE_04");
        codeItem022.setQuerySelectionItem(querySelectionItem02);

        querySelectionItem02.setDimension("DIMESNION_01");
        querySelectionItem02.addCode(codeItem021);
        querySelectionItem02.addCode(codeItem022);

        // Add selections
        query.addSelection(querySelectionItem01);
        query.addSelection(querySelectionItem02);

        // Save
        queryRepository.save(query);
    }
    
    @Test
    @MetamacMock(DATASET_VERSION_06_FOR_QUERIES_NAME)
    public void testCreateQueryErrorDimensionCodeUnique() throws Exception {
        thrown.expect(HibernateSystemException.class);
        
        Query query = statisticalResourcesNotPersistedDoMocks.mockQueryWithDatasetVersion(datasetVersionMockFactory.retrieveMock(DATASET_VERSION_06_FOR_QUERIES_NAME));

        // Set attributes that normally sets de service and can not be null in database
        query.setStatus(QueryStatusEnum.ACTIVE);
        
        // QuerySelectionItem 01
        QuerySelectionItem querySelectionItem01 = new QuerySelectionItem();
        querySelectionItem01.setQuery(query);

        CodeItem codeItem011 = new CodeItem();
        codeItem011.setCode("CODE_01");
        codeItem011.setQuerySelectionItem(querySelectionItem01);

        CodeItem codeItem012 = new CodeItem();
        codeItem012.setCode("CODE_01");
        codeItem012.setQuerySelectionItem(querySelectionItem01);

        querySelectionItem01.setDimension("DIMESNION_01");
        querySelectionItem01.addCode(codeItem011);
        querySelectionItem01.addCode(codeItem012);

        // QuerySelectionItem 02
        QuerySelectionItem querySelectionItem02 = new QuerySelectionItem();
        querySelectionItem02.setQuery(query);

        CodeItem codeItem021 = new CodeItem();
        codeItem021.setCode("CODE_01");
        codeItem021.setQuerySelectionItem(querySelectionItem02);

        CodeItem codeItem022 = new CodeItem();
        codeItem022.setCode("CODE_02");
        codeItem022.setQuerySelectionItem(querySelectionItem02);

        querySelectionItem02.setDimension("DIMESNION_02");
        querySelectionItem02.addCode(codeItem021);
        querySelectionItem02.addCode(codeItem022);

        // Add selections
        query.addSelection(querySelectionItem01);
        query.addSelection(querySelectionItem02);

        // Save
        queryRepository.save(query);
    }
}
