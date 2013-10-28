package org.siemac.metamac.statistical.resources.core.dataset.repositoryimpl;

import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory.DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.templates.StatisticalResourcesDoMocks.mockAttributeValuesWithIdentifiers;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValue;
import org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValueRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.utils.asserts.DatasetsAsserts;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MetamacMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/include/rest-services-mockito.xml", "classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class AttributeValueRepositoryTest extends StatisticalResourcesBaseTest implements AttributeValueRepositoryTestBase {

    @Autowired
    protected AttributeValueRepository attributeValueRepository;

    @Override
    @Test
    @MetamacMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME)
    public void testFindValuesForDatasetVersionByAttributeId() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME);
        {
            List<AttributeValue> attrValues = attributeValueRepository.findValuesForDatasetVersionByAttributeId(datasetVersion.getId(), "attr-none");
            Assert.assertEquals(0, attrValues.size());
        }
        {
            List<AttributeValue> attrValues = attributeValueRepository.findValuesForDatasetVersionByAttributeId(datasetVersion.getId(), "attr1");
            DatasetsAsserts.assertEqualsAttributeValuesCollection(mockAttributeValuesWithIdentifiers(datasetVersion, "attr1", "value-a1-1", "value-a1-2"), attrValues);
        }
        {
            List<AttributeValue> attrValues = attributeValueRepository.findValuesForDatasetVersionByAttributeId(datasetVersion.getId(), "attr2");
            DatasetsAsserts.assertEqualsAttributeValuesCollection(mockAttributeValuesWithIdentifiers(datasetVersion, "attr2", "value-a2-1", "value-a2-2"), attrValues);
        }
        {
            List<AttributeValue> attrValues = attributeValueRepository.findValuesForDatasetVersionByAttributeId(datasetVersion.getId(), "attr3");
            DatasetsAsserts.assertEqualsAttributeValuesCollection(mockAttributeValuesWithIdentifiers(datasetVersion, "attr3", "value-a3-1"), attrValues);
        }
    }

    @Test
    @MetamacMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME)
    public void testFindValuesForDatasetVersionByAttributeIdNotExist() throws Exception {
        DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_27_WITH_COVERAGE_FILLED_NAME);

        List<AttributeValue> attrCoverage = attributeValueRepository.findValuesForDatasetVersionByAttributeId(datasetVersion.getId(), "attr-none");
        Assert.assertEquals(0, attrCoverage.size());
    }
}
