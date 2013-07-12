package org.siemac.metamac.statistical_resources.rest.external.invocation;

import org.apache.cxf.jaxrs.client.WebClient;
import org.siemac.metamac.core.common.util.shared.UrnUtils;
import org.siemac.metamac.rest.exception.RestException;
import org.siemac.metamac.rest.exception.utils.RestExceptionUtils;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Codelist;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ConceptScheme;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

// TODO SRM: cambiar a API externa
@Component("srmRestExternalFacade")
public class SrmRestExternalFacadeImpl implements SrmRestExternalFacade {

    private final Logger       logger = LoggerFactory.getLogger(SrmRestExternalFacadeImpl.class);

    @Autowired
    @Qualifier("metamacApisLocatorRestExternal")
    private MetamacApisLocator restApiLocator;

    @Override
    public DataStructure retrieveDataStructureByUrn(String urn) {
        try {
            String[] urnSplited = UrnUtils.splitUrnStructure(urn);
            String agencyID = urnSplited[0];
            String resourceID = urnSplited[1];
            String version = urnSplited[2];
            return restApiLocator.getSrmRestExternalFacadeV10().retrieveDataStructure(agencyID, resourceID, version);
        } catch (Exception e) {
            throw toRestException(e);
        }
    }

    @Override
    public Codelist retrieveCodelistByUrn(String urn) {
        try {
            String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
            String agencyID = urnSplited[0];
            String resourceID = urnSplited[1];
            String version = urnSplited[2];
            return restApiLocator.getSrmRestExternalFacadeV10().retrieveCodelist(agencyID, resourceID, version);
        } catch (Exception e) {
            throw toRestException(e);
        }
    }

    @Override
    public ConceptScheme retrieveConceptSchemeByUrn(String urn) {
        try {
            String[] urnSplited = UrnUtils.splitUrnItemScheme(urn);
            String agencyID = urnSplited[0];
            String resourceID = urnSplited[1];
            String version = urnSplited[2];
            return restApiLocator.getSrmRestExternalFacadeV10().retrieveConceptScheme(agencyID, resourceID, version);
        } catch (Exception e) {
            throw toRestException(e);
        }
    }

    private RestException toRestException(Exception e) {
        logger.error("Error", e);
        return RestExceptionUtils.toRestException(e, WebClient.client(restApiLocator.getSrmRestExternalFacadeV10()));
    }
}
