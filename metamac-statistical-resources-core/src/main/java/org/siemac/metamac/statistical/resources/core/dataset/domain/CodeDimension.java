package org.siemac.metamac.statistical.resources.core.dataset.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity representing CodeDimension.
 * <p>
 * This class is responsible for the domain object related
 * business logic for CodeDimension. Properties and associations are
 * implemented in the generated base class {@link org.siemac.metamac.statistical.resources.core.dataset.domain.CodeDimensionBase}.
 */
@Entity
@Table(name = "TB_CODE_DIMENSIONS")
public class CodeDimension extends CodeDimensionBase {
    private static final long serialVersionUID = 1L;

    public CodeDimension() {
    }
    
    public CodeDimension(String componentId, String identifier) {
        setDsdComponentId(componentId);
        setIdentifier(identifier);
        setTitle(identifier);
    }
    
    public CodeDimension(String componentId, String identifier, String title) {
        setDsdComponentId(componentId);
        setIdentifier(identifier);
        setTitle(title);
    }
}
