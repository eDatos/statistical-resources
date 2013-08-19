package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import java.io.InputStream;

import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.siemac.metamac.core.common.exception.MetamacException;

public class SdmxRestExternalFacadeV10DataTest extends SdmxRestExternalFacadeV21BaseTest {

    @Test
    @Ignore
    public void testData() throws Exception {

        Response findData = getSdmxDataRestExternalFacadeClientXml().findData("TEST_DATA_STR_ECB_EXR_RG", null);

        System.out.println("_____________");
        System.out.println(IOUtils.toString((InputStream) findData.getEntity(), "UTF-8"));

    }

    @Override
    protected void resetMocks() throws MetamacException {
        // TODO Auto-generated method stub

    }

    @Override
    protected String getSupathMaintainableArtefacts() {
        // TODO Auto-generated method stub
        return null;
    }

}
