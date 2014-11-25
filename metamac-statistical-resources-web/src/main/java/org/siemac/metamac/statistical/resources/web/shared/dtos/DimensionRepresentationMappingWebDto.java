package org.siemac.metamac.statistical.resources.web.shared.dtos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.siemac.metamac.core.common.dto.ExternalItemDto;

public class DimensionRepresentationMappingWebDto implements Serializable {

    private static final long            serialVersionUID = 6431237010971254830L;

    private String                       datasourceFilename;
    private Map<String, ExternalItemDto> mapping          = new HashMap<String, ExternalItemDto>();

    public String getDatasourceFilename() {
        return datasourceFilename;
    }

    public void setDatasourceFilename(String datasourceFilename) {
        this.datasourceFilename = datasourceFilename;
    }

    public Map<String, ExternalItemDto> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, ExternalItemDto> mapping) {
        this.mapping = mapping;
    }
}
