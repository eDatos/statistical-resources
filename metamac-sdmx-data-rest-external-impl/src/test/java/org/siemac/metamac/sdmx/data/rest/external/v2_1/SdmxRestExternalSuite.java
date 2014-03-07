package org.siemac.metamac.sdmx.data.rest.external.v2_1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.service.SdmxRestExternalFacadeV10DataTest;
import org.siemac.metamac.sdmx.data.rest.external.v2_1.service.SdmxRestExternalFacadeV21BaseTest;

//@formatter:off
@RunWith(Suite.class)
@Suite.SuiteClasses({SdmxRestExternalFacadeV10DataTest.class,
                     SdmxRestExternalFacadeV21BaseTest.class})
// @formatter:on
public class SdmxRestExternalSuite {
}
