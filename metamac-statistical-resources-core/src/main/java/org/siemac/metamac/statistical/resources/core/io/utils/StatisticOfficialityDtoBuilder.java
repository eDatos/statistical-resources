package org.siemac.metamac.statistical.resources.core.io.utils;

import org.siemac.metamac.core.common.dto.InternationalStringDto;
import org.siemac.metamac.statistical.resources.core.dto.datasets.StatisticOfficialityDto;

public class StatisticOfficialityDtoBuilder {

    private String                 identifier;
    private InternationalStringDto description;
    private Long                   id;
    private String                 uuid;
    private Long                   version;

    private StatisticOfficialityDtoBuilder() {
    }
    public StatisticOfficialityDtoBuilder withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }
    public StatisticOfficialityDtoBuilder withDescription(InternationalStringDto description) {
        this.description = description;
        return this;
    }
    public StatisticOfficialityDtoBuilder withId(Long id) {
        this.id = id;
        return this;
    }
    public StatisticOfficialityDtoBuilder withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }
    public StatisticOfficialityDtoBuilder withVersion(Long version) {
        this.version = version;
        return this;
    }
    public StatisticOfficialityDto build() {
        StatisticOfficialityDto statisticOfficialityDto = new StatisticOfficialityDto();
        statisticOfficialityDto.setIdentifier(identifier);
        statisticOfficialityDto.setDescription(description);
        statisticOfficialityDto.setId(id);
        statisticOfficialityDto.setUuid(uuid);
        statisticOfficialityDto.setVersion(version);

        return statisticOfficialityDto;
    }

    public static StatisticOfficialityDtoBuilder builder() {
        return new StatisticOfficialityDtoBuilder();
    }
}
