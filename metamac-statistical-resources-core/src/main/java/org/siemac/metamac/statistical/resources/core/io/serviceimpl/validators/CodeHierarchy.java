package org.siemac.metamac.statistical.resources.core.io.serviceimpl.validators;

public class CodeHierarchy {

    private String        code;
    private String        urn;
    private CodeHierarchy parent;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CodeHierarchy getParent() {
        return parent;
    }

    public void setParent(CodeHierarchy parent) {
        this.parent = parent;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }
}
