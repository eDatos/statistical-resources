package org.siemac.metamac.statistical.resources.web.server.rest;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.statistical.resources.web.shared.criteria.CommonConfigurationWebCriteria;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface CommonMetadataRestExternalFacade {

    List<ExternalItemDto> findConfigurations(CommonConfigurationWebCriteria criteria) throws MetamacWebException;
}
