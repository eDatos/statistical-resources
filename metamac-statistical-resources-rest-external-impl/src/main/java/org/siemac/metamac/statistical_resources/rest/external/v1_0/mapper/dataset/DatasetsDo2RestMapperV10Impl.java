package org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.dataset;

import java.math.BigInteger;
import java.util.Map;

import org.siemac.metamac.core.common.ent.domain.ExternalItem;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.rest.common.v1_0.domain.ChildLinks;
import org.siemac.metamac.rest.common.v1_0.domain.Resource;
import org.siemac.metamac.rest.common.v1_0.domain.ResourceLink;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dataset;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimension;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.DimensionType;
import org.siemac.metamac.rest.statistical_resources.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.utils.RestUtils;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetVersion;
import org.siemac.metamac.statistical_resources.rest.external.RestExternalConstants;
import org.siemac.metamac.statistical_resources.rest.external.v1_0.mapper.base.BaseDo2RestMapperV10Impl;
import org.springframework.stereotype.Component;

@Component
public class DatasetsDo2RestMapperV10Impl extends BaseDo2RestMapperV10Impl implements DatasetsDo2RestMapperV10 {

    @Override
    public Dataset toDataset(DatasetVersion source, Map<String, DsdDimension> dimensionsById, DataStructure dataStructure) throws MetamacException {
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

        target.setDataStructureDefinition(toResourceExternalItemSrm(source.getRelatedDsd()));
        target.setDimensions(toDimensions(dimensionsById, dataStructure));
        return target;
    }

    private Resource toResourceExternalItemSrm(ExternalItem source) {
        if (source == null) {
            return null;
        }
        return toResourceExternalItem(source, getSrmApiExternalEndpoint());
    }

    private ResourceInternal toResourceExternalItem(ExternalItem source, String apiExternalItemBase) {
        if (source == null) {
            return null;
        }
        ResourceInternal target = new ResourceInternal();
        target.setId(source.getCode());
        target.setNestedId(source.getCodeNested());
        target.setUrn(source.getUrn());
        target.setKind(source.getType().getValue());
        target.setSelfLink(toResourceLink(target.getKind(), RestUtils.createLink(apiExternalItemBase, source.getUri())));
        target.setName(toInternationalString(source.getTitle()));
        return target;
    }

    private Dimensions toDimensions(Map<String, DsdDimension> dimensionsById, DataStructure dataStructure) {
        if (dimensionsById == null || dimensionsById.size() == 0) {
            return null;
        }
        Dimensions targets = new Dimensions();
        for (String sourceId : dimensionsById.keySet()) {
            targets.getDimensions().add(toDimension(dimensionsById.get(sourceId), dataStructure));
        }
        targets.setTotal(BigInteger.valueOf(targets.getDimensions().size()));
        return targets;
    }

    private Dimension toDimension(DsdDimension source, DataStructure dataStructure) {
        if (source == null) {
            return null;
        }
        Dimension target = new Dimension();
        target.setId(source.getComponentId());
        // target.setName(value); // TODO name
        DimensionType type = null;
        switch (source.getType()) {
            case OTHER:
                type = DimensionType.DIMENSION;
                break;
            case SPATIAL:
                type = DimensionType.GEOGRAPHIC_DIMENSION;
                break;
            case TEMPORAL:
                type = DimensionType.TIME_DIMENSION;
                break;
            case MEASURE:
                type = DimensionType.MEASURE_DIMENSION;
                break;
        }
        target.setType(type);

        // TODO codes
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