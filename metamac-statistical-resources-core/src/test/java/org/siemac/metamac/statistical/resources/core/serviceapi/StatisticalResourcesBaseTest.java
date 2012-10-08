package org.siemac.metamac.statistical.resources.core.serviceapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.common.test.MetamacBaseTests;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourcesRoleEnum;

public abstract class StatisticalResourcesBaseTest extends MetamacBaseTests {

    protected static Long   ID_NOT_EXISTS  = Long.valueOf(-1);
    protected static String URN_NOT_EXISTS = "not_exists";

    protected static String QUERY_1        = "urn:siemac.org.siemac.infomodel.statisticalResources.Query=QUERY1";

    // --------------------------------------------------------------------------------------------------------------
    // SERVICE CONTEXT
    // --------------------------------------------------------------------------------------------------------------

    @Override
    protected ServiceContext getServiceContextAdministrador() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalResourcesRoleEnum.ADMINISTRADOR);
        return serviceContext;
    }

    protected ServiceContext getServiceContextTecnicoProduccion() {
        ServiceContext serviceContext = super.getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalResourcesRoleEnum.TECNICO_PRODUCCION);
        return serviceContext;
    }

    private void putMetamacPrincipalInServiceContext(ServiceContext serviceContext, StatisticalResourcesRoleEnum role) {
        MetamacPrincipal metamacPrincipal = new MetamacPrincipal();
        metamacPrincipal.setUserId(serviceContext.getUserId());
        metamacPrincipal.getAccesses().add(new MetamacPrincipalAccess(role.getName(), StatisticalResourcesConstants.SECURITY_APPLICATION_ID, null));
        serviceContext.setProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE, metamacPrincipal);
    }

    // --------------------------------------------------------------------------------------------------------------
    // DBUNIT CONFIGURATION
    // --------------------------------------------------------------------------------------------------------------

    @Override
    protected String getDataSetFile() {
        return "dbunit/StatisticalResourcesServiceTest.xml";
    }

    @Override
    protected List<String> getTableNamesOrderedByFKDependency() {
        List<String> tables = new ArrayList<String>();
        
        tables.add("TB_INTERNATIONAL_STRINGS");
        tables.add("TB_LOCALISED_STRINGS");
        tables.add("TB_EXTERNAL_ITEMS");
        tables.add("TB_STATISTICAL_RESOURCES");
        tables.add("TB_DATASETS");
        tables.add("TB_PUBLICATIONS");
        tables.add("TB_PUBLICATIONS_VERSIONS");
        tables.add("TB_DATASETS_VERSIONS");
        tables.add("TB_DATASOURCES");
        tables.add("TB_QUERIES");
        tables.add("TB_EI_MEDIATORS");
        tables.add("TB_EI_PUBLISHERS");
        tables.add("TB_EI_CONTRIBUTORS");

        return tables;
    }

    @Override
    protected List<String> getSequencesToRestart() {
        List<String> sequences = new ArrayList<String>();
        sequences.add("SEQ_L10NSTRS");
        sequences.add("SEQ_I18NSTRS");
        sequences.add("SEQ_LOCALISED_STRINGS");
        sequences.add("SEQ_DATASOURCES");
        sequences.add("SEQ_QUERIES");
        sequences.add("SEQ_PUBLICATIONS_VERSIONS");
        sequences.add("SEQ_PUBLICATIONS");
        sequences.add("SEQ_DATASETS_VERSIONS");
        sequences.add("SEQ_DATASETS");
        sequences.add("SEQ_STATISTICAL_RESOURCES");
        sequences.add("SEQ_EXTERNAL_ITEMS");
        sequences.add("SEQ_INTERNATIONAL_STRINGS");
        return sequences;
    }

    @Override
    protected Map<String, String> getTablePrimaryKeys() {
        Map<String, String> tablePrimaryKeys = new HashMap<String, String>();
        return tablePrimaryKeys;
    }
}
