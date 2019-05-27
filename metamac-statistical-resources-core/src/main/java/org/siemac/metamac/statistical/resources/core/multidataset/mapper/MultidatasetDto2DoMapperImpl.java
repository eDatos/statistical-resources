package org.siemac.metamac.statistical.resources.core.multidataset.mapper;

import static org.siemac.metamac.statistical.resources.core.error.utils.ServiceExceptionParametersUtils.addParameter;

import java.util.ArrayList;
import java.util.List;

import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.exception.utils.ExceptionUtils;
import org.siemac.metamac.core.common.serviceimpl.utils.ValidationUtils;
import org.siemac.metamac.core.common.util.OptimisticLockingUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.NameableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.domain.SiemacMetadataStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.statistical.resources.core.common.domain.RelatedResource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Dataset;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetCubeDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionBaseDto;
import org.siemac.metamac.statistical.resources.core.dto.multidataset.MultidatasetVersionDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionSingleParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCube;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetCubeRepository;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersion;
import org.siemac.metamac.statistical.resources.core.multidataset.domain.MultidatasetVersionRepository;
import org.siemac.metamac.statistical.resources.core.multidataset.exception.MultidatasetVersionNotFoundException;
import org.siemac.metamac.statistical.resources.core.query.domain.Query;
import org.siemac.metamac.statistical.resources.core.query.domain.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("multidatasetDto2DoMapper")
public class MultidatasetDto2DoMapperImpl extends BaseDto2DoMapperImpl implements MultidatasetDto2DoMapper {

    @Autowired
    private MultidatasetVersionRepository multidatasetVersionRepository;

    @Autowired
    private MultidatasetCubeRepository    multidatasetCubeRepository;

    @Autowired
    private QueryRepository               queryRepository;

    @Autowired
    private DatasetRepository             datasetRepository;

    // --------------------------------------------------------------------------------------
    // MULTIDATASET VERSION
    // --------------------------------------------------------------------------------------

    @Override
    public void checkOptimisticLocking(MultidatasetVersionBaseDto source) throws MetamacException {
        if (source != null && source.getId() != null) {
            try {
                MultidatasetVersion target = multidatasetVersionRepository.findById(source.getId());
                if (target.getId() != null) {
                    OptimisticLockingUtils.checkVersion(target.getSiemacMetadataStatisticalResource().getVersion(), source.getOptimisticLockingVersion());
                }
            } catch (MultidatasetVersionNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.MULTIDATASET_VERSION_NOT_FOUND).withMessageParameters(source.getUrn())
                        .withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
        }
    }

    @Override
    public MultidatasetVersion multidatasetVersionDtoToDo(MultidatasetVersionDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity.
        MultidatasetVersion target = null;
        if (source.getId() == null) {
            target = new MultidatasetVersion();
            target.setSiemacMetadataStatisticalResource(new SiemacMetadataStatisticalResource());
        } else {
            try {
                target = multidatasetVersionRepository.findById(source.getId());
            } catch (MultidatasetVersionNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.MULTIDATASET_VERSION_NOT_FOUND).withMessageParameters(source.getUrn())
                        .withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
        }

        multidatasetVersionDtoToDo(source, target);

        return target;
    }

    private MultidatasetVersion multidatasetVersionDtoToDo(MultidatasetVersionDto source, MultidatasetVersion target) throws MetamacException {
        if (target == null) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.MULTIDATASET_VERSION).build();
        }

        // Check replaces before setting fields
        checkCanMultidatasetReplacesOtherMultidataset(source);

        // Hierarchy
        siemacMetadataStatisticalResourceDtoToDo(source, target.getSiemacMetadataStatisticalResource(), ServiceExceptionParameters.MULTIDATASET_VERSION);

        // Other
        target.setFilteringDimension(internationalStringDtoToDo(source.getFilteringDimension(), target.getFilteringDimension(),
                addParameter(ServiceExceptionParameters.MULTIDATASET_VERSION, ServiceExceptionSingleParameters.FILTERING_DIMENSION)));

        // We don't copy formatExtentResources because it is a calculated metadata

        return target;
    }

    protected void checkCanMultidatasetReplacesOtherMultidataset(MultidatasetVersionDto source) throws MetamacException {
        if (source.getReplaces() != null) {
            String currentUrn = source.getUrn();
            RelatedResource resourceReplaced = relatedResourceDtoToDo(source.getReplaces(), null, ServiceExceptionParameters.MULTIDATASET_VERSION__REPLACES);
            if (currentUrn.equals(resourceReplaced.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getUrn())) {
                throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.MULTIDATASET_VERSION_CANT_REPLACE_ITSELF).withMessageParameters(currentUrn).build();
            } else {
                RelatedResource resourceAlreadyReplacing = resourceReplaced.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getIsReplacedBy();
                if (resourceAlreadyReplacing != null && !resourceAlreadyReplacing.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getUrn().equals(source.getUrn())) {
                    throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.MULTIDATASET_VERSION_ALREADY_BEEN_REPLACED_BY_OTHER_MULTIDATASET_VERSION)
                            .withMessageParameters(currentUrn, source.getReplaces().getUrn()).build();
                }
            }
        }
    }

    // --------------------------------------------------------------------------------------
    // CUBE
    // --------------------------------------------------------------------------------------

    @Override
    public MultidatasetCube multidatasetCubeDtoToDo(MultidatasetCubeDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity.
        MultidatasetCube target = null;
        if (source.getUrn() == null) {
            target = new MultidatasetCube();
            target.setNameableStatisticalResource(new NameableStatisticalResource());
            target.setOrderInMultidataset(source.getOrderInMultidataset());
        } else {
            target = multidatasetCubeRepository.retrieveCubeByUrn(source.getUrn());

            // Metadata unmodifiable
            List<MetamacExceptionItem> exceptions = new ArrayList<MetamacExceptionItem>();
            // Modified in specific operation
            ValidationUtils.checkMetadataUnmodifiable(source.getParentMultidatasetUrn(), target.getMultidatasetVersion().getSiemacMetadataStatisticalResource().getUrn(),
                    ServiceExceptionParameters.MULTIDATASET_CUBE__MULTIDATASET_VERSION, exceptions);
            ValidationUtils.checkMetadataUnmodifiable(source.getOrderInMultidataset(), target.getOrderInMultidataset(), ServiceExceptionParameters.MULTIDATASET_CUBE__ORDER_IN_MULTIDATASET,
                    exceptions);
            ValidationUtils.checkMetadataUnmodifiable(source.getCode(), target.getNameableStatisticalResource().getCode(), ServiceExceptionParameters.MULTIDATASET_CUBE__CODE, exceptions);
            ValidationUtils.checkMetadataUnmodifiable(source.getUrn(), target.getNameableStatisticalResource().getUrn(), ServiceExceptionParameters.MULTIDATASET_CUBE__URN, exceptions);
            ExceptionUtils.throwIfException(exceptions);
        }

        multidatasetCubeDtoToDo(source, target);

        return target;
    }

    private MultidatasetCube multidatasetCubeDtoToDo(MultidatasetCubeDto source, MultidatasetCube target) throws MetamacException {
        // Hierarchy
        nameableStatisticalResourceDtoToDo(source, target.getNameableStatisticalResource(), ServiceExceptionParameters.CUBE);

        // Identifier
        target.setIdentifier(source.getIdentifier());

        // Related entities
        if (source.getParentMultidatasetUrn() != null) {
            MultidatasetVersion parentMultidatasetVersion = multidatasetVersionRepository.retrieveByUrn(source.getParentMultidatasetUrn());
            target.setMultidatasetVersion(parentMultidatasetVersion);
        } else {
            target.setMultidatasetVersion(null);
        }

        if (source.getDatasetUrn() != null) {
            Dataset dataset = datasetRepository.retrieveByUrn(source.getDatasetUrn());
            target.setDataset(dataset);
        } else {
            target.setDataset(null);
        }

        if (source.getQueryUrn() != null) {
            Query query = queryRepository.retrieveByUrn(source.getQueryUrn());
            target.setQuery(query);
        } else {
            target.setQuery(null);
        }

        return target;

    }
}
