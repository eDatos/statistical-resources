package org.siemac.metamac.sdmx.data.rest.external.v2_1.service;

import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.io.domain.DsdSdmxInfo;
import org.siemac.metamac.statistical.resources.core.io.domain.DsdSdmxInfo.DsdSdmxExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dsdSdmxExtractor")
public class DsdSdmxExtractorImpl implements DsdSdmxExtractor {

    @Autowired
    private ApisLocator apisLocator;

    @Override
    public DsdSdmxInfo extractDsdInfo(String dsdUrn) throws MetamacException {
        return new DsdSdmxInfo(apisLocator.retrieveDsdByUrn(dsdUrn));
    }

}
