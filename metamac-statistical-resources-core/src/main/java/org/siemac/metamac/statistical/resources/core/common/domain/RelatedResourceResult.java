package org.siemac.metamac.statistical.resources.core.common.domain;

import java.util.Map;

import org.siemac.metamac.statistical.resources.core.enume.domain.TypeRelatedResourceEnum;


public class RelatedResourceResult {
    private static final long serialVersionUID = 1L;
    private String statisticalOperationUrn;
    private TypeRelatedResourceEnum type;
    private String code;
    private String urn;
    private String maintainer;
    private String version;
    private Map<String,String> title;
    
    public String getStatisticalOperationUrn() {
        return statisticalOperationUrn;
    }
    
    public void setStatisticalOperationUrn(String statisticalOperationUrn) {
        this.statisticalOperationUrn = statisticalOperationUrn;
    }
    
    
    public String getMaintainer() {
        return maintainer;
    }

    
    public void setMaintainer(String maintainer) {
        this.maintainer = maintainer;
    }

    
    public String getVersion() {
        return version;
    }

    
    public void setVersion(String version) {
        this.version = version;
    }

    public TypeRelatedResourceEnum getType() {
        return type;
    }
    
    public void setType(TypeRelatedResourceEnum type) {
        this.type = type;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getUrn() {
        return urn;
    }
    
    public void setUrn(String urn) {
        this.urn = urn;
    }
    
    public Map<String, String> getTitle() {
        return title;
    }
    
    public void setTitle(Map<String, String> title) {
        this.title = title;
    }
    
}
