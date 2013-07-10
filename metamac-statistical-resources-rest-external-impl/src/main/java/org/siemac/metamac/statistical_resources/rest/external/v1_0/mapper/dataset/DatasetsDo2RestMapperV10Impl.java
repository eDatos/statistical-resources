package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.BaseDo2RestMapperV10Impl;
import org.springframework.stereotype.Component;

@Component
public class DatasetsDo2RestMapperV10Impl extends BaseDo2RestMapperV10Impl implements DatasetsDo2RestMapperV10 {

    @Override
    public Dataset toDataset(DatasetVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }
        Dataset target = new Dataset();
        target.setKind(RestExternalConstants.KIND_DATASET);
        target.setId(source.getSiemacMetadataStatisticalResource().getCode());
        target.setUrn(source.getSiemacMetadataStatisticalResource().getUrn());
        target.setKind(RestExternalConstants.KIND_DATASET);
        target.setSelfLink(toDatasetSelfLink(source));
        target.setName(toInternationalString(source.getSiemacMetadataStatisticalResource().getTitle()));
        target.setParentLink(toDatasetParentLink(source));
        target.setChildLinks(toDatasetChildLinks(source));
        // TODO resto de metadatos públicos

        return target;
    }

    private ResourceLink toDatasetParentLink(DatasetVersion source) {
        return toDatasetsSelfLink(null, null, null);
    }

    private ChildLinks toDatasetChildLinks(DatasetVersion source) {
        // nothing
        return null;
    }

    private ResourceLink toDatasetsSelfLink(String agencyID, String resourceID, String version) {
        return toResourceLink(RestExternalConstants.KIND_DATASETS, toDatasetsLink(agencyID, resourceID, version));
    }

    private String toDatasetsLink(String agencyID, String resourceID, String version) {
        String resourceSubpath = RestExternalConstants.LINK_SUBPATH_DATASETS;
        return toStatisticalResourceLink(resourceSubpath, agencyID, resourceID, version);
    }

    private ResourceLink toDatasetSelfLink(DatasetVersion source) {
        return toResourceLink(RestExternalConstants.KIND_DATASET, toDatasetLink(source));
    }

    private String toDatasetLink(DatasetVersion source) {
        String resourceSubpath = RestExternalConstants.LINK_SUBPATH_DATASETS;
        return toStatisticalResourceLink(resourceSubpath, source.getSiemacMetadataStatisticalResource());
    }
}