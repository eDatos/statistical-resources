package org.siemac.metamac.statistical.resources.core.stream.messages.mappers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.siemac.metamac.core.common.conf.ConfigurationService;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersionRepository;
import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationVersionAvro;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.DatasetVersionMockFactory;
import org.siemac.metamac.statistical.resources.core.utils.mocks.factories.PublicationVersionMockFactory;

public class PublicationVersionAvroMapperTest {

    @Mock
    PublicationVersionRepository            publicationVersionRepository;

    @Mock
    DatasetVersionRepository                datasetVersionRepository;

    @Mock
    ConfigurationService                    configurationService;

    protected DatasetVersionMockFactory     datasetVersionMockFactory     = DatasetVersionMockFactory.getInstance();
    protected PublicationVersionMockFactory publicationVersionMockFactory = PublicationVersionMockFactory.getInstance();

    @Before
    public void setUp() throws MetamacException {
        MockitoAnnotations.initMocks(this);
        AvroMapperUtils.publicationVersionRepository = publicationVersionRepository;
        AvroMapperUtils.configurationService = configurationService;
        // DatasetVersion datasetVersion = datasetVersionMockFactory.retrieveMock(DATASET_VERSION_29_WITHOUT_DATASOURCES_NAME);
        // PublicationVersion publication01 = publicationVersionMockFactory.retrieveMock(PUBLICATION_VERSION_47_PUBLISHED_V02_FOR_PUBLICATION_07_NAME);
        try {
            when(configurationService.retrieveStatisticalResourcesInternalApiUrlBase()).thenReturn(MappersMockUtils.EXPECTED_API_BASE);
            // Mockito.when(datasetVersionRepository.retrieveLastVersion(Mockito.eq(datasetUrn))).thenReturn(datasetVersion);
            // Mockito.when(publicationVersionRepository.retrieveLastVersion(Mockito.eq("datasetUrn"))).thenReturn(publication01);
            // .getPublicationVersionRepository().retrieveLastVersion
        } catch (MetamacException e) {
        }
    }

    @Test
    public void testDo2Avro() throws Exception {
        PublicationVersionAvro expected = MappersMockUtils.mockPublicationVersionAvro();
        PublicationVersion source = MappersMockUtils.mockPublicationVersion();

        when(publicationVersionRepository.retrieveByUrn(Mockito.any())).thenReturn(source);

        assertThat(source.getPublication().getIdentifiableStatisticalResource().getUrn(), is(equalTo(expected.getPublication().getIdentifiableStatisticalResource().getUrn())));

        PublicationVersionAvro actual = PublicationVersionDo2AvroMapper.do2Avro(source);

        assertThat(actual, is(equalTo(expected)));
    }

}
