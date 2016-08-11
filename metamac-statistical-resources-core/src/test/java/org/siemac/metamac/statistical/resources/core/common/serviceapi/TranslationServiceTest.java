package org.siemac.metamac.statistical.resources.core.common.serviceapi;

import static org.junit.Assert.assertEquals;
import static org.siemac.metamac.statistical.resources.core.utils.mocks.factories.TranslationMockFactory.TRANSLATIONS;

import java.util.Map;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.siemac.metamac.core.common.test.utils.mocks.configuration.MetamacMock;
import org.siemac.metamac.statistical.resources.core.StatisticalResourcesBaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/statistical-resources/applicationContext-test.xml"})
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class TranslationServiceTest extends StatisticalResourcesBaseTest implements TranslationServiceTestBase {

    @Autowired
    protected TranslationService translationService;

    private final ServiceContext ctx = getServiceContextWithoutPrincipal();

    @Override
    @Test
    @MetamacMock(TRANSLATIONS)
    public void testTranslateTime() throws Exception {

        // REPORTING TIME PERIOD
        {
            Map<String, String> title = translationService.translateTime(ctx, "2000-A1");
            assertEquals("2000", title.get("es"));
            assertEquals("2000", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2001-S1");
            assertEquals("2001 Primer semestre", title.get("es"));
            assertEquals("2001 First semester", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2001-S2");
            assertEquals("2001 Segundo semestre", title.get("es"));
            assertEquals("2001 Second semester", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2002-T3");
            assertEquals("2002 Tercer trimestre", title.get("es"));
            assertEquals("2002 Third trimester", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2003-Q4");
            assertEquals("2003 Cuarto cuatrimestre", title.get("es"));
            assertEquals("2003 Fourth quarter", title.get("en"));
        }
        {
            // Without translate
            Map<String, String> title = translationService.translateTime(ctx, "2003-Q1");
            assertEquals("2003-Q1", title.get("es"));
            assertEquals(null, title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2004-M05");
            assertEquals("05/2004", title.get("es"));
            assertEquals("05/2004", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2005-W52");
            assertEquals("2005 Semana 52", title.get("es"));
            assertEquals("2005 Week 52", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2006-D364");
            assertEquals("30/12/2006", title.get("es"));
            assertEquals("30/12/2006", title.get("en"));
        }

        // DATETIME
        {
            Map<String, String> title = translationService.translateTime(ctx, "2013-07-24T08:21:52.519+01:00");
            assertEquals("24/07/2013 08:21:52", title.get("es"));
            assertEquals("24/07/2013 08:21:52", title.get("en"));
        }

        // GREGORIAN TIME
        {
            Map<String, String> title = translationService.translateTime(ctx, "2013");
            assertEquals("2013", title.get("es"));
            assertEquals("2013", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2004Z");
            assertEquals("2004", title.get("es"));
            assertEquals("2004", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "-0045");
            assertEquals("-0045", title.get("es"));
            assertEquals("-0045", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2004-05:00");
            assertEquals("2004", title.get("es"));
            assertEquals("2004", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2013-10");
            assertEquals("10/2013", title.get("es"));
            assertEquals("10/2013", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2013-08-30");
            assertEquals("30/08/2013", title.get("es"));
            assertEquals("30/08/2013", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2013-08-30+02:00");
            assertEquals("30/08/2013", title.get("es"));
            assertEquals("30/08/2013", title.get("en"));
        }

        // TIME RANGES
        {
            Map<String, String> title = translationService.translateTime(ctx, "2013-07-24T13:21:52.519+01:00/P1M");
            assertEquals("24/07/2013 13:21:52 a 24/08/2013 13:21:52", title.get("es"));
            assertEquals("24/07/2013 13:21:52 to 24/08/2013 13:21:52", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "2013-07-24/P1M");
            assertEquals("24/07/2013 a 24/08/2013", title.get("es"));
            assertEquals("24/07/2013 to 24/08/2013", title.get("en"));
        }
        {
            Map<String, String> title = translationService.translateTime(ctx, "1999-07-01T03:04:05.519+01:00/P1Y1M2D");
            assertEquals("01/07/1999 03:04:05 a 03/08/2000 03:04:05", title.get("es"));
            assertEquals("01/07/1999 03:04:05 to 03/08/2000 03:04:05", title.get("en"));
        }
    }
}
