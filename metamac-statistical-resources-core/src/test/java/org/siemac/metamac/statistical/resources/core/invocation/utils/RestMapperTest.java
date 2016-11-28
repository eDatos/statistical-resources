package org.siemac.metamac.statistical.resources.core.invocation.utils;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.notices.v1_0.domain.ResourceInternal;
import org.siemac.metamac.statistical.resources.core.base.domain.HasSiemacMetadata;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.common.domain.ExternalItem;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourceTypeEnum;
import org.siemac.metamac.statistical.resources.core.publication.domain.PublicationVersion;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class RestMapperTest {

    private static final String   EXPECTED_STATISTICAL_OPERATON_CODE = "EXPECTED_STATISTICAL_OPERATON_CODE";
    private static final String   EXPECTED_KIND_DATASET              = "statisticalResources#dataset";
    private static final String   EXPECTED_KIND_COLLECTION           = "statisticalResources#collection";
    private static final String   EXPECTED_RESOURCE_CODE                  = "TRANS_MAR_000001";
    private static final String   EXPECTED_MAINTAINER            = "EXPECTED_MAINTAINER";
    private static final String   URL_SEPARATOR                  = "/";
    private static final String   EXPECTED_URL_TYPE              = "datasets";
    private static final String   EXPECTED_RESOURCE_VERSION          = "123.456";
    private static final String   EXPECTED_INTERNAL_WEB_URL_BASE = "EXPECTED_INTERNAL_WEB_URL_BASE";
    protected static final String EXPECTED_API_INTERNAL_ENDPOINT     = "http://EXPECTED_INTERNAL_API_ENDPOINT/statistical-resources-internal/apis";
    private static final String EXPECTED_API_VERSION = "v1.0";

    private static final String   URN_EXPECTED_001               = "urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=" + EXPECTED_MAINTAINER + ":" + EXPECTED_RESOURCE_CODE + "("
            + EXPECTED_RESOURCE_VERSION + ")";

    @Test
    public void testGenerateResourceInternalListGeneratesSelfLink() throws Exception {
        List<HasSiemacMetadata> affectedResources = new ArrayList<HasSiemacMetadata>();
        affectedResources.add(mockDatasetVersion());
        RestMapper rm = new RestMapper();

        ResourceInternal expected = new ResourceInternal();
        ResourceLink expectedResourceLink = new ResourceLink();
        String expectedHref = createExpectedApiHref("dataset", true);
        expectedResourceLink.setHref(expectedHref);
        expectedResourceLink.setKind(EXPECTED_KIND_DATASET);
        expected.setSelfLink(expectedResourceLink);

        List<ResourceInternal> actualList = rm.generateResourceInternalList(affectedResources, EXPECTED_INTERNAL_WEB_URL_BASE, EXPECTED_API_INTERNAL_ENDPOINT);
        ResourceInternal actual = actualList.get(0);

        assertThat(actual.getSelfLink().getHref(), is(equalTo(expected.getSelfLink().getHref())));
        assertThat(actual.getSelfLink().getKind(), is(equalTo(expected.getSelfLink().getKind())));
    }

    @Test
    public void testGenerateResourceInternalListGeneratesSelfLinkForCollectionsWithoutVersion() throws Exception {
        List<HasSiemacMetadata> affectedResources = new ArrayList<HasSiemacMetadata>();
        affectedResources.add(mockPublicationVersion());
        RestMapper rm = new RestMapper();

        ResourceInternal expected = new ResourceInternal();
        ResourceLink expectedResourceLink = new ResourceLink();
        String expectedHref = createExpectedApiHref("collection", false);
        expectedResourceLink.setHref(expectedHref);
        expectedResourceLink.setKind(EXPECTED_KIND_COLLECTION);
        expected.setSelfLink(expectedResourceLink);

        List<ResourceInternal> actualList = rm.generateResourceInternalList(affectedResources, EXPECTED_INTERNAL_WEB_URL_BASE, EXPECTED_API_INTERNAL_ENDPOINT);
        ResourceInternal actual = actualList.get(0);

        assertThat(actual.getSelfLink().getHref(), is(equalTo(expected.getSelfLink().getHref())));
        assertThat(actual.getSelfLink().getKind(), is(equalTo(expected.getSelfLink().getKind())));
    }

    @Test
    public void testGenerateResourceInternalListGeneratesAppManagementLink() throws Exception {
        List<HasSiemacMetadata> affectedResources = new ArrayList<HasSiemacMetadata>();
        affectedResources.add(mockDatasetVersion());
        RestMapper rm = new RestMapper();

        ResourceInternal expected = new ResourceInternal();
        expected.setManagementAppLink(getExpectedManagementUrl("dataset"));

        List<ResourceInternal> actualList = rm.generateResourceInternalList(affectedResources, EXPECTED_INTERNAL_WEB_URL_BASE, EXPECTED_API_INTERNAL_ENDPOINT);
        ResourceInternal actual = actualList.get(0);

        assertThat(actual.getManagementAppLink(), is(equalTo(expected.getManagementAppLink())));
    }

    private String getExpectedManagementUrl(String type) {
        return new StringBuffer().append(EXPECTED_INTERNAL_WEB_URL_BASE)
               .append("/#operations/operation;id=")
               .append(EXPECTED_STATISTICAL_OPERATON_CODE)
                .append("/" + type + "s" + "/" + type + ";id=")
               .append(EXPECTED_MAINTAINER)
               .append(":")
               .append(EXPECTED_RESOURCE_CODE)
               .append("(").append(EXPECTED_RESOURCE_VERSION).append(")")
               .toString();
    }

    private String createExpectedApiHref(String type, boolean withVersion) {
        StringBuffer href = new StringBuffer().append(EXPECTED_API_INTERNAL_ENDPOINT).append(URL_SEPARATOR)
            .append("statistical-resources-internal").append(URL_SEPARATOR )
            .append(EXPECTED_API_VERSION).append(URL_SEPARATOR)
            .append(type + "s").append(URL_SEPARATOR)
            .append(EXPECTED_MAINTAINER).append(URL_SEPARATOR)
            .append(EXPECTED_RESOURCE_CODE).append(URL_SEPARATOR);
        if (withVersion) {
            href.append(EXPECTED_RESOURCE_VERSION);
        }
        return href.toString();
    }

    protected DatasetVersion mockDatasetVersion() {
        DatasetVersion datasetVersion = new DatasetVersion();
        SiemacMetadataStatisticalResource siemacMetadataStatisticalResource = mockSiemacMetadataStatisticalResource();
        datasetVersion.setSiemacMetadataStatisticalResource(siemacMetadataStatisticalResource);
        datasetVersion.getSiemacMetadataStatisticalResource().setType(StatisticalResourceTypeEnum.DATASET);
        return datasetVersion;
    }

    protected PublicationVersion mockPublicationVersion() {
        PublicationVersion publicationVersion = new PublicationVersion();
        publicationVersion.setSiemacMetadataStatisticalResource(mockSiemacMetadataStatisticalResource());
        publicationVersion.getSiemacMetadataStatisticalResource().setType(StatisticalResourceTypeEnum.COLLECTION);
        return publicationVersion;
    }

    protected SiemacMetadataStatisticalResource mockSiemacMetadataStatisticalResource() {
        SiemacMetadataStatisticalResource siemacMetadataStatisticalResource = new SiemacMetadataStatisticalResource();

        siemacMetadataStatisticalResource.setUrn(URN_EXPECTED_001);
        siemacMetadataStatisticalResource.setVersionLogic(EXPECTED_RESOURCE_VERSION);
        siemacMetadataStatisticalResource.setCode(EXPECTED_RESOURCE_CODE);

        ExternalItem statisticalOperation = new ExternalItem();
        statisticalOperation.setCode(EXPECTED_STATISTICAL_OPERATON_CODE);
        siemacMetadataStatisticalResource.setStatisticalOperation(statisticalOperation);

        ExternalItem maintainer = new ExternalItem();
        maintainer.setCode(EXPECTED_MAINTAINER);
        siemacMetadataStatisticalResource.setMaintainer(maintainer);

        return siemacMetadataStatisticalResource;
    }

}
