package org.siemac.metamac.statistical.resources.core.io.utils;

import org.siemac.metamac.core.common.dto.LocalisedStringDto;

public class LocalisedStringDtoBuilder {

    private String  label;
    private String  locale;
    private Boolean isUnmodifiable;
    private Long    id;
    private String  uuid;
    private Long    version;

    private LocalisedStringDtoBuilder() {
    }
    public LocalisedStringDtoBuilder withLabel(String label) {
        this.label = label;
        return this;
    }
    public LocalisedStringDtoBuilder withLocale(String locale) {
        this.locale = locale;
        return this;
    }
    public LocalisedStringDtoBuilder withIsUnmodifiable(Boolean isUnmodifiable) {
        this.isUnmodifiable = isUnmodifiable;
        return this;
    }
    public LocalisedStringDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    public LocalisedStringDtoBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
    public LocalisedStringDtoBuilder withVersion(Long version) {
        this.version = version;
        return this;
    }
    public LocalisedStringDto build() {
        LocalisedStringDto localisedStringDto = new LocalisedStringDto();

        localisedStringDto.setLabel(label);
        localisedStringDto.setLocale(locale);
        localisedStringDto.setIsUnmodifiable(isUnmodifiable);
        localisedStringDto.setId(id);
        localisedStringDto.setUuid(uuid);
        localisedStringDto.setVersion(version);

        return localisedStringDto;
    }

    public static LocalisedStringDtoBuilder builder() {
        return new LocalisedStringDtoBuilder();
    }
}
