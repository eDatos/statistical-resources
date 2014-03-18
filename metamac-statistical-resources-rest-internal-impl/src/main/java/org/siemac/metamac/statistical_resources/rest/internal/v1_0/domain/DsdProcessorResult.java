package org.siemac.metamac.statistical_resources.rest.internal.v1_0.domain;

import java.util.List;
import java.util.Map;

import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdAttribute;
import org.siemac.metamac.statistical.resources.core.common.utils.DsdProcessor.DsdDimension;

public class DsdProcessorResult {

    private DataStructure             dataStructure;
    private List<DsdDimension>        dimensions;
    private List<DsdAttribute>        attributes;
    private Map<String, List<String>> groups;

    public DataStructure getDataStructure() {
        return dataStructure;
    }

    public void setDataStructure(DataStructure dataStructure) {
        this.dataStructure = dataStructure;
    }

    public List<DsdDimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<DsdDimension> dimensions) {
        this.dimensions = dimensions;
    }

    public List<DsdAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<DsdAttribute> attributes) {
        this.attributes = attributes;
    }

    public Map<String, List<String>> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, List<String>> groups) {
        this.groups = groups;
    }
}
