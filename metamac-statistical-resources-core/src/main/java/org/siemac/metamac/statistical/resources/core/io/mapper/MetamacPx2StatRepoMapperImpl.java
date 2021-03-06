package org.siemac.metamac.statistical.resources.core.io.mapper;

import static org.siemac.metamac.core.common.constants.shared.RegularExpressionConstants.END;
import static org.siemac.metamac.core.common.constants.shared.RegularExpressionConstants.START;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration;
import org.siemac.metamac.statistical.resources.core.conf.StatisticalResourcesConfiguration.KeyDotEnum;
import org.siemac.metamac.statistical.resources.core.constants.StatisticalResourcesConstants;
import org.siemac.metamac.statistical.resources.core.io.utils.ManipulateDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.arte.statistic.parser.domain.InternationalString;
import com.arte.statistic.parser.px.domain.PxAttribute;
import com.arte.statistic.parser.px.domain.PxAttributeCodes;
import com.arte.statistic.parser.px.domain.PxAttributeDimension;
import com.arte.statistic.parser.px.domain.PxDimension;
import com.arte.statistic.parser.px.domain.PxModel;
import com.arte.statistic.parser.px.domain.PxObservation;
import com.arte.statistic.parser.px.domain.PxObservationCodeDimension;
import com.arte.statistic.parser.sdmx.v2_1.domain.ComponentInfo;

import es.gobcan.istac.edatos.dataset.repository.dto.AttributeInstanceBasicDto;
import es.gobcan.istac.edatos.dataset.repository.dto.AttributeInstanceObservationDto;
import es.gobcan.istac.edatos.dataset.repository.dto.CodeDimensionDto;
import es.gobcan.istac.edatos.dataset.repository.dto.InternationalStringDto;
import es.gobcan.istac.edatos.dataset.repository.dto.LocalisedStringDto;
import es.gobcan.istac.edatos.dataset.repository.dto.ObservationExtendedDto;
import es.gobcan.istac.edatos.dataset.repository.util.DtoUtils;

@Component(MetamacPx2StatRepoMapper.BEAN_ID)
public class MetamacPx2StatRepoMapperImpl implements MetamacPx2StatRepoMapper {

    public static final String                ATTR_OBS_NOTE = "OBS_NOTE";
    public static final String                ATTR_OBS_CONF = "OBS_CONF";

    protected final Pattern                   PATTERN_DOT   = Pattern.compile(START + "(\\.{1,6})" + END);

    @Autowired
    private StatisticalResourcesConfiguration statisticalResourcesConfiguration;

    @Override
    public ObservationExtendedDto toObservation(PxObservation observation, String datasourceId, Map<String, AttributeInstanceObservationDto> attributesObservations) throws MetamacException {

        ObservationExtendedDto observationExtendedDto = null;

        if (observation == null) {
            return null; // Reached end of file
        }

        observationExtendedDto = new ObservationExtendedDto();

        // Key
        observationExtendedDto.getCodesDimension().addAll(processKeyOfObservation(observation.getCodesDimensions()));

        // Data
        AttributeInstanceBasicDto dotCode2Attribute = transformDotCode2Attribute(observation.getObservationValue());
        if (dotCode2Attribute == null) {
            observationExtendedDto.setPrimaryMeasure(observation.getObservationValue());
        } else {
            observationExtendedDto.setPrimaryMeasure(null);
        }

        // Attributes: Identification data source attribute
        observationExtendedDto.addAttribute(ManipulateDataUtils.createDataSourceIdentificationAttribute(observationExtendedDto.getCodesDimension(), datasourceId));

        // Attributes: Other attributes at observation level (only OBS_NOTE)
        addValidAttributesForCurrentObservation(observationExtendedDto.getCodesDimension(), attributesObservations, observationExtendedDto, new LinkedList<String>(), 0);

        return observationExtendedDto;
    }

    private AttributeInstanceObservationDto transformDotCode2Attribute(String observationValue) throws MetamacException {

        AttributeInstanceObservationDto obsConfAttr = null;

        Matcher matching = PATTERN_DOT.matcher(observationValue);

        if (matching.matches()) {

            String group = matching.group(1);
            String value = StringUtils.EMPTY;
            switch (group.length()) {
                case 1:
                    value = statisticalResourcesConfiguration.retrieveDotCodeMapping().get(KeyDotEnum.ONE_DOT);
                case 2:
                    value = statisticalResourcesConfiguration.retrieveDotCodeMapping().get(KeyDotEnum.TWO_DOT);
                case 3:
                    value = statisticalResourcesConfiguration.retrieveDotCodeMapping().get(KeyDotEnum.THREE_DOT);
                case 4:
                    value = statisticalResourcesConfiguration.retrieveDotCodeMapping().get(KeyDotEnum.FOUR_DOT);
                case 5:
                    value = statisticalResourcesConfiguration.retrieveDotCodeMapping().get(KeyDotEnum.FIVE_DOT);
                case 6:
                    value = statisticalResourcesConfiguration.retrieveDotCodeMapping().get(KeyDotEnum.SIX_DOT);
            }

            InternationalStringDto internationalStringDto = new InternationalStringDto();
            LocalisedStringDto localisedStringDto = new LocalisedStringDto();
            localisedStringDto.setLabel(value);
            // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
            // In this case, in the repo exists the code of enumerated representation, never the i18n of code.
            localisedStringDto.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);
            internationalStringDto.addText(localisedStringDto);

            obsConfAttr = new AttributeInstanceObservationDto(ATTR_OBS_CONF, internationalStringDto);
        }

        return obsConfAttr;
    }
    private List<CodeDimensionDto> processKeyOfObservation(List<PxObservationCodeDimension> observations) {
        List<CodeDimensionDto> codeDimensionDtos = new ArrayList<CodeDimensionDto>(observations.size());

        for (PxObservationCodeDimension pxObservationCodeDimension : observations) {
            codeDimensionDtos.add(new CodeDimensionDto(pxObservationCodeDimension.getDimensionId(), pxObservationCodeDimension.getCodeDimensionId()));
        }

        return codeDimensionDtos;
    }

    /**
     * Transform observations attributes (only attributes for one observation)
     * Only are valid CELLNOTE y CELNOTEX attributes.
     *
     * @return Map with key = unique key of observations; value = list of attributes of observation
     */
    @Override
    public Map<String, AttributeInstanceObservationDto> toAttributesObservations(PxModel pxModel, String preferredLanguage, List<ComponentInfo> dimensionsInfos,
            Map<String, Integer> dimensionsOrderPxMap) throws MetamacException {

        Map<String, AttributeInstanceObservationDto> attributes = new HashMap<String, AttributeInstanceObservationDto>();

        for (PxAttribute pxAttributeDto : pxModel.getAttributesObservations()) {
            if (PxAttributeCodes.CELLNOTE.equals(pxAttributeDto.getIdentifier()) || PxAttributeCodes.CELLNOTEX.equals(pxAttributeDto.getIdentifier())) {

                InternationalStringDto internationalString = toInternationalStringStatisticRepository(pxAttributeDto.getValue(), preferredLanguage, pxModel.getLanguage());
                if (internationalString == null) {
                    continue;
                }

                AttributeInstanceObservationDto attributeObservationDto = new AttributeInstanceObservationDto(ATTR_OBS_NOTE, internationalString);

                // key
                List<String> codesPxAttribute = new ArrayList<String>(dimensionsInfos.size());
                for (ComponentInfo componentInfo : dimensionsInfos) {
                    Integer order = dimensionsOrderPxMap.get(componentInfo.getCode());
                    if (order != null) {
                        PxAttributeDimension pxAttributeDimension = pxAttributeDto.getDimensions().get(order);
                        codesPxAttribute.add(pxAttributeDimension.getDimensionCode());
                    }
                }
                String uniqueKey = DtoUtils.generateUniqueKeyWithCodes(codesPxAttribute);
                if (!attributes.containsKey(uniqueKey)) {
                    attributes.put(uniqueKey, attributeObservationDto);
                } else {
                    AttributeInstanceObservationDto previousObsNotes = attributes.get(uniqueKey);
                    previousObsNotes.setValue(concatInternationalStrings(previousObsNotes.getValue(), attributeObservationDto.getValue()));
                }
            }
        }

        return attributes;
    }

    public void addValidAttributesForCurrentObservation(List<CodeDimensionDto> keys, Map<String, AttributeInstanceObservationDto> attributesObservations, ObservationExtendedDto observationExtendedDto,
            final List<String> codesDimension, int index) {

        if (attributesObservations == null || attributesObservations.isEmpty()) {
            return;
        }

        if (index == keys.size()) {
            String generateUniqueKeyWithCodes = DtoUtils.generateUniqueKeyWithCodes(codesDimension);
            if (attributesObservations.get(generateUniqueKeyWithCodes) != null) {
                observationExtendedDto.addAttribute(attributesObservations.get(generateUniqueKeyWithCodes));
            }
            return;
        }

        // Generate Keys, only two codes by dimension, the '*' and the specific value

        // One code with '*'
        List<String> candidateCodesDimension = new LinkedList<String>(codesDimension);
        candidateCodesDimension.add("*");
        addValidAttributesForCurrentObservation(keys, attributesObservations, observationExtendedDto, candidateCodesDimension, index + 1);

        // One code with the specific value
        candidateCodesDimension = new LinkedList<String>(codesDimension);
        candidateCodesDimension.add(keys.get(index).getCodeDimensionId());
        addValidAttributesForCurrentObservation(keys, attributesObservations, observationExtendedDto, candidateCodesDimension, index + 1);
    }

    @Override
    public Map<String, Integer> generateDimensionsOrderPxMap(PxModel pxModel) throws MetamacException {
        Map<String, Integer> pxDimensionOrder = new HashMap<String, Integer>();
        int i = 0;
        for (PxDimension stubDimension : pxModel.getStub()) {
            pxDimensionOrder.put(stubDimension.getIdentifier(), i++);
        }
        for (PxDimension headingDimension : pxModel.getHeading()) {
            pxDimensionOrder.put(headingDimension.getIdentifier(), i++);
        }
        return pxDimensionOrder;
    }

    private InternationalStringDto toInternationalStringStatisticRepository(InternationalString source, String preferredLanguage, String defaultLanguage) throws MetamacException {
        if (source == null) {
            return null;
        }

        InternationalStringDto target = new InternationalStringDto();

        // In SDMX the attributes aren't localized. For use localised in SDMX must be use a enumerated representation.
        // In this case, in the repository exists the code of enumerated representation, never the i18n of code.
        String localisedLabel = source.getLocalisedLabel(preferredLanguage);
        if (StringUtils.isEmpty(localisedLabel)) {
            // Get in default language
            localisedLabel = source.getLocalisedLabel(defaultLanguage);
            if (StringUtils.isEmpty(localisedLabel)) {
                return null;
            }
        }

        LocalisedStringDto targetLocalisedString = new LocalisedStringDto();
        targetLocalisedString.setLabel(localisedLabel);
        targetLocalisedString.setLocale(StatisticalResourcesConstants.DEFAULT_DATA_REPOSITORY_LOCALE);

        target.getTexts().add(targetLocalisedString);

        return target;
    }

    private InternationalStringDto concatInternationalStrings(InternationalStringDto iStringA, InternationalStringDto iStringB) throws MetamacException {
        if (iStringA == null) {
            return iStringB;
        }
        if (iStringB == null) {
            return iStringA;
        }

        List<LocalisedStringDto> textsA = iStringA.getTexts();
        List<LocalisedStringDto> textsB = iStringB.getTexts();
        if (!textsA.isEmpty() && !textsB.isEmpty()) {
            textsA.get(0).setLabel(textsA.get(0).getLabel() + " | " + textsB.get(0).getLabel());
        }

        return iStringA;
    }
}
