package org.siemac.metamac.statistical.resources.core.dataset.mapper;

import org.siemac.metamac.core.common.exception.ExceptionLevelEnum;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;
import org.siemac.metamac.core.common.util.OptimisticLockingUtils;
import org.siemac.metamac.statistical.resources.core.base.domain.IdentifiableStatisticalResource;
import org.siemac.metamac.statistical.resources.core.base.mapper.BaseDto2DoMapperImpl;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasetRepository;
import org.siemac.metamac.statistical.resources.core.dataset.domain.Datasource;
import org.siemac.metamac.statistical.resources.core.dataset.domain.DatasourceRepository;
import org.siemac.metamac.statistical.resources.core.dataset.exception.DatasourceNotFoundException;
import org.siemac.metamac.statistical.resources.core.dto.datasets.DatasourceDto;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionParameters;
import org.siemac.metamac.statistical.resources.core.error.ServiceExceptionType;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component("datasetDto2DoMapper")
public class DatasetDto2DoMapperImpl extends BaseDto2DoMapperImpl implements DatasetDto2DoMapper {

    @Autowired
    private DatasetRepository    datasetRepository;

    @Autowired
    private DatasourceRepository datasourceRepository;

    @Override
    public Datasource datasourceDtoToDo(DatasourceDto source) throws MetamacException {
        if (source == null) {
            return null;
        }

        // If exists, retrieves existing entity. Otherwise, creates new entity.
        Datasource target = null;
        if (source.getId() == null) {
            target = new Datasource();
            target.setIdentifiableStatisticalResource(new IdentifiableStatisticalResource());
        } else {
            try {
                target = datasourceRepository.findById(source.getId());
            } catch (DatasourceNotFoundException e) {
                throw MetamacExceptionBuilder.builder().withCause(e).withExceptionItems(ServiceExceptionType.DATASOURCE_NOT_FOUND).withMessageParameters(ServiceExceptionParameters.DATASOURCE)
                        .withLoggedLevel(ExceptionLevelEnum.ERROR).build();
            }
            OptimisticLockingUtils.checkVersion(target.getVersion(), source.getVersion());
        }

        datasourceDtoToDo(source, target);

        return target;
    }

    private Datasource datasourceDtoToDo(DatasourceDto source, Datasource target) throws MetamacException {
        if (target == null) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.PARAMETER_REQUIRED).withMessageParameters(ServiceExceptionParameters.NAMEABLE_RESOURCE).build();
        }

        // Hierarchy
        identifiableStatisticalResourceDtoToDo(source, target.getIdentifiableStatisticalResource());

        // Non modifiable after creation

        // Optimistic locking: Update "update date" attribute to force update of the root entity in order to increase attribute "version"
        // TODO: Add update date
        // target.setUpdateDate(new DateTime());

        return target;
    }
}
