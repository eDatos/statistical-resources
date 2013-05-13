package org.siemac.metamac.statistical.resources.web.shared.criteria;


public class DsdWebCriteria extends StatisticalResourceWebCriteria {

    
    //This filter is used if only last version can be specified
    private String fixedDsdCode;
    private boolean onlyLastVersion;
    
    /**
     * 
     */
    private static final long serialVersionUID = 5661810672873720214L;

    public DsdWebCriteria() {
        fixedDsdCode = null;
        onlyLastVersion = false;
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
    
    public void setOnlyLastVersion(boolean onlyLastVersion) {
        this.onlyLastVersion = onlyLastVersion;
    }
    
    public boolean isOnlyLastVersion() {
        return onlyLastVersion;
    }
    
}
