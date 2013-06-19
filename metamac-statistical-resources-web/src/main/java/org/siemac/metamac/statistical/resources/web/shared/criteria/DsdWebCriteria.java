package org.siemac.metamac.statistical.resources.web.shared.criteria;


public class DsdWebCriteria extends VersionableStatisticalResourceWebCriteria {

    
    private String fixedDsdCode;
    
    /**
     * 
     */
    private static final long serialVersionUID = 5661810672873720214L;

    public DsdWebCriteria() {
        super();
        fixedDsdCode = null;
    }
    
    public DsdWebCriteria(String criteria) {
        super(criteria);
    }
    
    public void setFixedDsdCode(String dsdCode) {
        this.fixedDsdCode = dsdCode;
    }
    
    public String getFixedDsdCode() {
        return fixedDsdCode;
    }
    
}
