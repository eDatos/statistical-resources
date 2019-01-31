package org.siemac.metamac.statistical.resources.core.io.utils;

import java.util.HashSet;
import java.util.Set;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.core.common.dto.LocalisedStringDto;

public class InternationalStringDtoBuilder {

    private Set<LocalisedStringDto> texts = new HashSet<>();
    private Long                    id;
    private String                  uuid;
    private Long                    version;

    private InternationalStringDtoBuilder() {
    }

    public InternationalStringDtoBuilder withText(LocalisedStringDto text) {
        this.texts.add(text);
        return this;
    }

    public InternationalStringDtoBuilder withTexts(Set<LocalisedStringDto> texts) {
        this.texts = texts;
        return this;
    }
    public InternationalStringDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    public InternationalStringDtoBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
    public InternationalStringDtoBuilder withVersion(Long version) {
        this.version = version;
        return this;
    }
    public InternationalStringDto build() {
        InternationalStringDto internationalStringDto = new InternationalStringDto();
        for (LocalisedStringDto text : texts) {
            internationalStringDto.addText(text);
        }
        internationalStringDto.setId(id);
        internationalStringDto.setUuid(uuid);
        internationalStringDto.setVersion(version);

        return internationalStringDto;
    }

    public static InternationalStringDtoBuilder builder() {
        return new InternationalStringDtoBuilder();
    }

}
