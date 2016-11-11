package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.domain.LifeCycleStatisticalResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.utils.asserts.CommonAsserts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class LifecycleStatisticalResourceAvroMapperTest {

    @Mock
    protected DatasetRepository        datasetRepository;

    @Mock
    protected DatasetVersionRepository datasetVersionRepository;

    @Mock
    private static ConfigurationService configurationService;

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.setDatasetRepository(datasetRepository);
        AvroMapperUtils.setDatasetVersionRepository(datasetVersionRepository);
        AvroMapperUtils.setConfiguratinService(configurationService);
        try {
            when(configurationService.retrieveStatisticalResourcesInternalApiUrlBase()).thenReturn(MappersMockUtils.EXPECTED_API_BASE);
        } catch (MetamacException e) {
        }
    }

    @Test
    public void testLifeCycleStatisticalResourceAvro2Do() throws MetamacException {
        when(datasetVersionRepository.retrieveByUrn(any())).thenReturn(MappersMockUtils.mockDatasetVersion());

        LifeCycleStatisticalResource expected = MappersMockUtils.mockLifeCycleStatisticalResource(TypeRelatedResourceEnum.DATASET_VERSION);
        LifecycleStatisticalResourceAvro source = MappersMockUtils.mockLifeCycleStatisticalResourceAvro(TypeRelatedResourceEnum.DATASET_VERSION);

        LifeCycleStatisticalResource actual = LifecycleStatisticalResourceAvroMapper.avro2Do(source);

        assertThat(actual.getCreationDate(), is(equalTo(expected.getCreationDate())));
        assertThat(actual.getCreationUser(), is(equalTo(expected.getCreationUser())));
        assertThat(actual.getProductionValidationDate(), is(equalTo(expected.getProductionValidationDate())));
        assertThat(actual.getProductionValidationUser(), is(equalTo(expected.getProductionValidationUser())));
        assertThat(actual.getDiffusionValidationDate(), is(equalTo(expected.getDiffusionValidationDate())));
        assertThat(actual.getDiffusionValidationUser(), is(equalTo(expected.getDiffusionValidationUser())));
        assertThat(actual.getRejectValidationDate(), is(equalTo(expected.getRejectValidationDate())));
        assertThat(actual.getRejectValidationUser(), is(equalTo(expected.getRejectValidationUser())));
        assertThat(actual.getPublicationDate(), is(equalTo(expected.getPublicationDate())));
        assertThat(actual.getPublicationUser(), is(equalTo(expected.getPublicationUser())));
        assertThat(actual.getLastVersion(), is(equalTo(expected.getLastVersion())));
        assertThat(actual.getProcStatus(), is(equalTo(expected.getProcStatus())));
        CommonAsserts.assertEqualsRelatedResource(expected.getReplacesVersion(), actual.getReplacesVersion());
        CommonAsserts.assertEqualsExternalItem(expected.getMaintainer(), actual.getMaintainer());
    }

    @Test
    public void testLifecycleStatisticalResourceDo2Avro() throws MetamacException {

        when(datasetVersionRepository.retrieveByUrn(any())).thenReturn(MappersMockUtils.mockDatasetVersion());

        LifecycleStatisticalResourceAvro expected = MappersMockUtils.mockLifeCycleStatisticalResourceAvro(TypeRelatedResourceEnum.DATASET);
        LifeCycleStatisticalResource source = MappersMockUtils.mockLifeCycleStatisticalResource(TypeRelatedResourceEnum.DATASET);

        LifecycleStatisticalResourceAvro actual = LifecycleStatisticalResourceAvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

}
