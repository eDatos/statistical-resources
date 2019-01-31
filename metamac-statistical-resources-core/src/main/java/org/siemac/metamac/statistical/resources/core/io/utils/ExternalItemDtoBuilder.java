package org.siemac.metamac.statistical.resources.core.io.utils;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.enume.domain.TypeExternalArtefactsEnum;

public class ExternalItemDtoBuilder {

    private String                    urnProvider;
    private String                    uri;
    private String                    managementAppUrl;
    private TypeExternalArtefactsEnum type;
    private String                    code;
    private String                    codeNested;
    private String                    urn;
    private InternationalStringDto    title;
    private Long                      id;
    private String                    uuid;
    private Long                      version;

    private ExternalItemDtoBuilder() {
    }
    public ExternalItemDtoBuilder withUrnProvider(String urnProvider) {
        this.urnProvider = urnProvider;
        return this;
    }
    public ExternalItemDtoBuilder withUri(String uri) {
        this.uri = uri;
        return this;
    }
    public ExternalItemDtoBuilder withManagementAppUrl(String managementAppUrl) {
        this.managementAppUrl = managementAppUrl;
        return this;
    }
    public ExternalItemDtoBuilder withType(TypeExternalArtefactsEnum type) {
        this.type = type;
        return this;
    }
    public ExternalItemDtoBuilder withCode(String code) {
        this.code = code;
        return this;
    }
    public ExternalItemDtoBuilder withCodeNested(String codeNested) {
        this.codeNested = codeNested;
        return this;
    }
    public ExternalItemDtoBuilder withUrn(String urn) {
        this.urn = urn;
        return this;
    }
    public ExternalItemDtoBuilder withTitle(InternationalStringDto title) {
        this.title = title;
        return this;
    }
    public ExternalItemDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    public ExternalItemDtoBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
    public ExternalItemDtoBuilder withVersion(Long version) {
        this.version = version;
        return this;
    }
    public ExternalItemDto build() {
        ExternalItemDto externalItemDto = new ExternalItemDto();

        externalItemDto.setUrnProvider(urnProvider);
        externalItemDto.setUri(uri);
        externalItemDto.setManagementAppUrl(managementAppUrl);
        externalItemDto.setType(type);
        externalItemDto.setCode(code);
        externalItemDto.setCodeNested(codeNested);
        externalItemDto.setUrn(urn);
        externalItemDto.setTitle(title);
        externalItemDto.setId(id);
        externalItemDto.setUuid(uuid);
        externalItemDto.setVersion(version);

        return externalItemDto;

    }
    public static ExternalItemDtoBuilder builder() {
        return new ExternalItemDtoBuilder();
    }
}