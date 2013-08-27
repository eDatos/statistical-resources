package org.siemac.metamac.statistical.resources.core.dataset.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity representing AttributeValue.
 * <p>
 * This class is responsible for the domain object related
 * business logic for AttributeValue. Properties and associations are
 * implemented in the generated base class {@link org.siemac.metamac.statistical.resources.core.dataset.domain.AttributeValueBase}.
 */
@Entity
@Table(name = "TB_ATTRIBUTE_VALUES")
public class AttributeValue extends AttributeValueBase {
    private static final long serialVersionUID = 1L;

    public AttributeValue() {
    }
    
    public AttributeValue(String componentId, String identifier) {
        setDsdComponentId(componentId);
        setIdentifier(identifier);
        setTitle(identifier);
    }
    
    public AttributeValue(String componentId, String identifier, String title) {
        setDsdComponentId(componentId);
        setIdentifier(identifier);
        setTitle(title);
    }
}
