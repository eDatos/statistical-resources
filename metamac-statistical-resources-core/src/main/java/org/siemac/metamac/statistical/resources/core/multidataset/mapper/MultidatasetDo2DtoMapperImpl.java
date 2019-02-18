package org.siemac.metamac.statistical.resources.core.multidataset.mapper;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDo2DtoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dto.RelatedResourceDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetCubeDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.enume.domain.ProcStatusEnum;
import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;
import org.siemac.metamac.statistical.resources.core.enume.utils.ProcStatusEnumUtils;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("multidatasetDo2DtoMapper")
public class MultidatasetDo2DtoMapperImpl extends BaseDo2DtoMapperImpl implements MultidatasetDo2DtoMapper {

    @Autowired
    private MultidatasetVersionRepository multidatasetVersionRepository;

    // ---------------------------------------------------------------------------------------------------------
    // MULTIDATASETS
    // ---------------------------------------------------------------------------------------------------------

    @Override
    public RelatedResourceDto multidatasetVersionDoToMultidatasetRelatedResourceDto(MultidatasetVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }
        RelatedResourceDto target = new RelatedResourceDto();
        multidatasetVersionDoToMultidatasetRelatedResourceDto(source, target);
        return target;
    }

    private RelatedResourceDto multidatasetVersionDoToMultidatasetRelatedResourceDto(MultidatasetVersion source, RelatedResourceDto target) {
        if (source == null) {
            return null;
        }

        // Identity
        target.setId(source.getMultidataset().getId());
        target.setVersion(source.getMultidataset().getVersion());

        // Type
        target.setType(TypeRelatedResourceEnum.MULTIDATASET);

        // Identifiable Fields
        target.setCode(source.getMultidataset().getIdentifiableStatisticalResource().getCode());
        target.setCodeNested(null);
        target.setUrn(source.getMultidataset().getIdentifiableStatisticalResource().getUrn());

        // Nameable Fields
        target.setTitle(internationalStringDoToDto(source.getSiemacMetadataStatisticalResource().getTitle()));

        return target;
    }

    // --------------------------------------------------------------------------------------
    // MULTIDATASET VERSION
    // --------------------------------------------------------------------------------------

    @Override
    public MultidatasetVersionBaseDto multidatasetVersionDoToBaseDto(MultidatasetVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }

        MultidatasetVersionBaseDto target = new MultidatasetVersionBaseDto();
        multidatasetVersionDoToBaseDto(source, target);
        return target;
    }

    private MultidatasetVersionBaseDto multidatasetVersionDoToBaseDto(MultidatasetVersion source, MultidatasetVersionBaseDto target) throws MetamacException {
        if (source == null) {
            return null;
        }

        // Hierarchy
        siemacMetadataStatisticalResourceDoToBaseDto(source.getSiemacMetadataStatisticalResource(), target);

        // Identity
        target.setId(source.getId());
        target.setVersion(source.getVersion());

        return target;
    }

    @Override
    public MultidatasetVersionDto multidatasetVersionDoToDto(MultidatasetVersion source) throws MetamacException {
        if (source == null) {
            return null;
        }

        MultidatasetVersionDto target = new MultidatasetVersionDto();
        multidatasetVersionDoToDto(source, target);
        return target;
    }

    @Override
    public List<MultidatasetVersionBaseDto> multidatasetVersionDoListToDtoList(List<MultidatasetVersion> sources) throws MetamacException {
        List<MultidatasetVersionBaseDto> targets = new ArrayList<MultidatasetVersionBaseDto>();
        for (MultidatasetVersion source : sources) {
            targets.add(multidatasetVersionDoToBaseDto(source));
        }
        return targets;
    }

    private MultidatasetVersionDto multidatasetVersionDoToDto(MultidatasetVersion source, MultidatasetVersionDto target) throws MetamacException {
        if (source == null) {
            return null;
        }

        // Hierarchy
        siemacMetadataStatisticalResourceDoToDto(source.getSiemacMetadataStatisticalResource(), target);

        // Siemac metadata that needs to be filled
        target.setIsReplacedByVersion(relatedResourceDoToDto(source.getLifeCycleStatisticalResource().getIsReplacedByVersion()));
        target.setIsReplacedBy(relatedResourceDoToDto(source.getSiemacMetadataStatisticalResource().getIsReplacedBy()));

        target.getCubes().clear();
        target.getCubes().addAll(multidatasetCubeDoListToDtoList(source.getCubes()));

        // Identity
        target.setId(source.getId());
        target.setVersion(source.getVersion());

        // Other multidataset version attributes
        if (ProcStatusEnumUtils.isInAnyProcStatus(source, ProcStatusEnum.PUBLISHED)) {
            target.setFormatExtentResources(source.getFormatExtentResources());
        } else {
            target.setFormatExtentResources(source.getCubes().size());
        }

        target.setFilteringDimension(internationalStringDoToDto(source.getFilteringDimension()));

        return target;
    }

    @Override
    public RelatedResourceDto multidatasetVersionDoToMultidatasetVersionRelatedResourceDto(MultidatasetVersion source) {
        if (source == null) {
            return null;
        }
        RelatedResourceDto target = new RelatedResourceDto();
        multidatasetVersionDoToMultidatasetVersionRelatedResourceDto(source, target);
        return target;
    }

    private RelatedResourceDto multidatasetVersionDoToMultidatasetVersionRelatedResourceDto(MultidatasetVersion source, RelatedResourceDto target) {
        if (source == null) {
            return null;
        }

        // Identity
        target.setId(source.getId());
        target.setVersion(source.getVersion());

        // Type
        target.setType(TypeRelatedResourceEnum.MULTIDATASET_VERSION);

        // Identifiable Fields
        target.setCode(source.getSiemacMetadataStatisticalResource().getCode());
        target.setCodeNested(null);
        target.setUrn(source.getSiemacMetadataStatisticalResource().getUrn());

        // Nameable Fields
        target.setTitle(internationalStringDoToDto(source.getSiemacMetadataStatisticalResource().getTitle()));

        return target;
    }

    // --------------------------------------------------------------------------------------
    // CUBE
    // --------------------------------------------------------------------------------------
    @Override
    public MultidatasetCubeDto multidatasetCubeDoToDto(MultidatasetCube source) throws MetamacException {
        if (source == null) {
            return null;
        }

        MultidatasetCubeDto target = new MultidatasetCubeDto();
        multidatasetCubeDoToDto(source, target);
        return target;
    }

    private MultidatasetCubeDto multidatasetCubeDoToDto(MultidatasetCube source, MultidatasetCubeDto target) throws MetamacException {
        if (source == null) {
            return null;
        }

        // Hierarchy
        nameableStatisticalResourceDoToDto(source.getNameableStatisticalResource(), target);

        // Identity
        target.setId(source.getId());
        target.setVersion(source.getVersion());

        // Other
        target.setParentMultidatasetUrn(source.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getUrn());
        target.setOrderInMultidataset(source.getOrderInMultidataset());
        target.setQueryUrn(source.getQueryUrn());
        target.setDatasetUrn(source.getDatasetUrn());
        return target;
    }

    @Override
    public List<MultidatasetCubeDto> multidatasetCubeDoListToDtoList(List<MultidatasetCube> sources) throws MetamacException {
        List<MultidatasetCubeDto> targets = new ArrayList<MultidatasetCubeDto>();
        for (MultidatasetCube source : sources) {
            targets.add(multidatasetCubeDoToDto(source));
        }
        return targets;
    }

}
