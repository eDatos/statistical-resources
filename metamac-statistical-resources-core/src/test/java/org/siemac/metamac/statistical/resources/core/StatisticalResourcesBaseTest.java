package org.siemac.metamac.statistical.resources.core;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.sso.client.MetamacPrincipal;
import org.siemac.metamac.sso.client.MetamacPrincipalAccess;
import org.siemac.metamac.sso.client.SsoClientConstants;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.enume.domain.StatisticalResourcesRoleEnum;
import org.siemac.metamac.statistical.resources.core.utils.mocks.configuration.MockAnnotationRule;
import org.springframework.beans.factory.annotation.Value;

public abstract class StatisticalResourcesBaseTest {

    protected static String      EMPTY             = StringUtils.EMPTY;

    protected static Long        ID_NOT_EXISTS     = Long.valueOf(-1);
    protected static String      URN_NOT_EXISTS    = "not_exists";

    protected static String      QUERY_1           = "urn:siemac.org.siemac.infomodel.statisticalResources.Query=QUERY1";

    @Value("${metamac.statistical_resources.db.provider}")
    private String               databaseProvider;

    private final ServiceContext serviceContext    = new ServiceContext("junit", "junit", "app");

    @Rule
    public ExpectedException  expectedException = ExpectedException.none();

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

    protected void expectedMetamacException(final MetamacException testExpectedException, final int testExpectedExceptionItems) {
        expectedException.expect(MetamacException.class);
        expectedException.expect(new BaseMatcher<MetamacException>() {

            @Override
            public boolean matches(Object e) {
                MetamacException metamacCoreException = (MetamacException) e;
                
                assertEquals(testExpectedExceptionItems, metamacCoreException.getExceptionItems().size());
                
                for (int i = 0; i < metamacCoreException.getExceptionItems().size(); i++) {
                    assertEqualsMetamacExceptionItem(testExpectedException.getExceptionItems().get(i), metamacCoreException.getExceptionItems().get(i));
                }
                
                return testExpectedException.getExceptionItems().get(0).getCode().equals(metamacCoreException.getExceptionItems().get(0).getCode());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("exception = " + testExpectedException + "");
            }
        });
    }
    
    private static void assertEqualsMetamacExceptionItem(MetamacExceptionItem expected, MetamacExceptionItem actual) {
        assertEquals(expected.getCode(), actual.getCode());
        assertEquals(expected.getMessage(), actual.getMessage());
        assertEquals(expected.getMessageParameters().length, actual.getMessageParameters().length);
        
        for (int i = 0; i < expected.getMessageParameters().length; i++) {
            assertEquals(expected.getMessageParameters()[i], actual.getMessageParameters()[i]);
        }
    }
}
