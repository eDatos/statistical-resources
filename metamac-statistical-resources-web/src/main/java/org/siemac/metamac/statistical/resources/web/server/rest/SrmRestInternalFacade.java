package org.siemac.metamac.statistical.resources.web.server.rest;

import java.util.List;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;
import org.siemac.metamac.statistical.resources.web.shared.criteria.DsdWebCriteria;
import org.siemac.metamac.statistical.resources.web.shared.criteria.ItemSchemeWebCriteria;
import org.siemac.metamac.web.common.shared.criteria.MetamacWebCriteria;
import org.siemac.metamac.web.common.shared.domain.ExternalItemsResult;
import org.siemac.metamac.web.common.shared.exception.MetamacWebException;

public interface SrmRestInternalFacade {

    public ExternalItemsResult findDsds(int firstResult, int maxResult, DsdWebCriteria condition) throws MetamacWebException;

    public List<String> retrieveDsdDimensionsIds(String dsdUrn) throws MetamacWebException;

    public ExternalItemsResult findCodesInCodelist(String codeListUrn, int firstResult, int maxResult, MetamacWebCriteria condition) throws MetamacWebException;

    public ExternalItemDto retrieveCodeByUrn(String urn) throws MetamacWebException;

    public ExternalItemsResult findConceptSchemes(int firstResult, int maxResult, MetamacWebCriteria condition) throws MetamacWebException;

    public ExternalItemsResult findConcepts(int firstResult, int maxResult, ItemSchemeWebCriteria condition) throws MetamacWebException;

    public ExternalItemsResult findOrganisationSchemes(int firstResult, int maxResult, MetamacWebCriteria criteria, TypeExternalArtefactsEnum type) throws MetamacWebException;

    public ExternalItemsResult findOrganisations(int firstResult, int maxResult, ItemSchemeWebCriteria criteria, TypeExternalArtefactsEnum type) throws MetamacWebException;

    public ExternalItemDto retrieveAgencyByUrn(String agencyUrn) throws MetamacWebException;

}
