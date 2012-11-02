package org.siemac.metamac.statistical.resources.core;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Rule;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourcesRoleEnum;
import org.siemac.metamac.statistical.resources.core.mocks.MockAnnotationRule;
import org.springframework.beans.factory.annotation.Value;

public abstract class StatisticalResourcesBaseTest{

    protected static String EMPTY = StringUtils.EMPTY;
    
    protected static Long   ID_NOT_EXISTS  = Long.valueOf(-1);
    protected static String URN_NOT_EXISTS = "not_exists";

    protected static String QUERY_1        = "urn:siemac.org.siemac.infomodel.statisticalResources.Query=QUERY1";

    @Value("${metamac.statistical_resources.db.provider}")
    private String databaseProvider;
    
    private final ServiceContext     serviceContext   = new ServiceContext("junit", "junit", "app");
    
    public ServiceContext getServiceContextWithoutPrincipal() {
        return serviceContext;
    }
    
    
    @Rule
    public MockAnnotationRule mockRule = new MockAnnotationRule();
    
    protected ServiceContext getServiceContextAdministrador() {
        ServiceContext serviceContext = getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalResourcesRoleEnum.ADMINISTRADOR);
        return serviceContext;
    }
    
    protected ServiceContext getServiceContextTecnicoProduccion() {
        ServiceContext serviceContext = getServiceContextWithoutPrincipal();
        putMetamacPrincipalInServiceContext(serviceContext, StatisticalResourcesRoleEnum.TECNICO_PRODUCCION);
        return serviceContext;
    }

    private void putMetamacPrincipalInServiceContext(ServiceContext serviceContext, StatisticalResourcesRoleEnum role) {
        MetamacPrincipal metamacPrincipal = new MetamacPrincipal();
        metamacPrincipal.setUserId(serviceContext.getUserId());
        metamacPrincipal.getAccesses().add(new MetamacPrincipalAccess(role.getName(), StatisticalResourcesConstants.SECURITY_APPLICATION_ID, null));
        serviceContext.setProperty(SsoClientConstants.PRINCIPAL_ATTRIBUTE, metamacPrincipal);
    }

}
