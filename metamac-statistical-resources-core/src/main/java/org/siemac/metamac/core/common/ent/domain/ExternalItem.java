package org.siemac.metamac.core.common.ent.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;

/**
 * Entity for store information about ExternalItems.
 */
@Entity
@Table(name = "TB_EXTERNAL_ITEMS")
public class ExternalItem extends ExternalItemBase {

    private static final long serialVersionUID = 1L;

    protected ExternalItem() {
    }

    public ExternalItem(String code, String uri, String urn, String urnInternal, TypeExternalArtefactsEnum type) {
        super(code, uri, type);
        this.setUrn(urn);
        this.setUrnInternal(urnInternal);
    }

    public ExternalItem(String code, String uri, String urn, String urnInternal, TypeExternalArtefactsEnum type, InternationalString title) {
        super(code, uri, type);
        this.setUrn(urn);
        this.setUrnInternal(urnInternal);
        this.setTitle(title);
    }

    public ExternalItem(String code, String uri, String urn, String urnInternal, TypeExternalArtefactsEnum type, InternationalString title, String managementAppUrl) {
        super(code, uri, type);
        this.setUrn(urn);
        this.setUrnInternal(urnInternal);
        this.setTitle(title);
        this.setManagementAppUrl(managementAppUrl);
    }
}
