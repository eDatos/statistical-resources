package org.siemac.metamac.statistical.resources.web.shared.DTO;

import org.siemac.metamac.core.common.dto.ExternalItemDto;
import org.siemac.metamac.core.common.dto.IdentityDto;

public class FacetDto extends IdentityDto {

    private static final long  serialVersionUID = 1L;
    private String             isSequenceFT;
    private String             intervalFT;
    private String             startValueFT;
    private String             endValueFT;
    private String             timeIntervalFT;
    private String             startTimeFT;
    private String             endTimeFT;
    private String             minLengthFT;
    private String             maxLengthFT;
    private String             minValueFT;
    private String             maxValueFT;
    private String             decimalsFT;
    private String             patternFT;
    private String             xhtmlEFT;
    private String             isMultiLingual;
    private FacetValueTypeEnum facetValue;
    private ExternalItemDto    itemSchemeFacet;

    public FacetDto() {
    }

    public FacetDto(FacetValueTypeEnum facetValue) {
        super();
        this.facetValue = facetValue;
    }

    /**
     * The isSequence facet indicates whether the values are intended to be ordered, and it may work in combination with the interval, startValue, and
     * endValue facet or the timeInterval, startTime, and endTime, facets. If this attribute holds a value of true, a start value or time and a numeric or time
     * interval must supplied. If an end value is not given, then the sequence
     * continues indefinitely.
     */
    public String getIsSequenceFT() {
        return isSequenceFT;
    }

    /**
     * The isSequence facet indicates whether the values are intended to be ordered, and it may work in combination with the interval, startValue, and
     * endValue facet or the timeInterval, startTime, and endTime, facets. If this attribute holds a value of true, a start value or time and a numeric or time
     * interval must supplied. If an end value is not given, then the sequence
     * continues indefinitely.
     */
    public void setIsSequenceFT(String isSequenceFT) {
        this.isSequenceFT = isSequenceFT;
    }

    /**
     * The interval attribute specifies the permitted interval (increment) in a sequence. In order for this to be used, the isSequence attribute must
     * have a value of true.
     */
    public String getIntervalFT() {
        return intervalFT;
    }

    /**
     * The interval attribute specifies the permitted interval (increment) in a sequence. In order for this to be used, the isSequence attribute must
     * have a value of true.
     */
    public void setIntervalFT(String intervalFT) {
        this.intervalFT = intervalFT;
    }

    /**
     * The startValue facet is used in conjunction with the isSequence and interval facets (which must be set in order to use this facet). This facet is
     * used for a numeric sequence, and indicates the starting point of the sequence. This value is mandatory for a numeric sequence to be expressed.
     */
    public String getStartValueFT() {
        return startValueFT;
    }

    /**
     * The startValue facet is used in conjunction with the isSequence and interval facets (which must be set in order to use this facet). This facet is
     * used for a numeric sequence, and indicates the starting point of the sequence. This value is mandatory for a numeric sequence to be expressed.
     */
    public void setStartValueFT(String startValueFT) {
        this.startValueFT = startValueFT;
    }

    /**
     * The endValue facet is used in conjunction with the isSequence and interval facets (which must be set in order to use this facet). This facet is
     * used for a numeric sequence, and indicates that ending point (if any) of the sequence.
     */
    public String getEndValueFT() {
        return endValueFT;
    }

    /**
     * The endValue facet is used in conjunction with the isSequence and interval facets (which must be set in order to use this facet). This facet is
     * used for a numeric sequence, and indicates that ending point (if any) of the sequence.
     */
    public void setEndValueFT(String endValueFT) {
        this.endValueFT = endValueFT;
    }

    /**
     * The timeInterval facet indicates the permitted duration in a time sequence. In order for this to be used, the isSequence facet must have a
     * value of true.
     * Specifying the Duration as its string representation, 'PnYnMnDTnHnMnS', as defined in XML Schema 1.0 section 3.2.6.1.
     */
    public String getTimeIntervalFT() {
        return timeIntervalFT;
    }

    /**
     * The timeInterval facet indicates the permitted duration in a time sequence. In order for this to be used, the isSequence facet must have a
     * value of true.
     * Specifying the Duration as its string representation, 'PnYnMnDTnHnMnS', as defined in XML Schema 1.0 section 3.2.6.1.
     */
    public void setTimeIntervalFT(String timeIntervalFT) {
        this.timeIntervalFT = timeIntervalFT;
    }

    /**
     * The startTime facet is used in conjunction with the isSequence and timeInterval facets (which must be set in order to use this facet). This
     * attribute is used for a time sequence, and indicates the start time of the sequence. This value is mandatory for a time sequence to be expressed.
     */
    public String getStartTimeFT() {
        return startTimeFT;
    }

    /**
     * The startTime facet is used in conjunction with the isSequence and timeInterval facets (which must be set in order to use this facet). This
     * attribute is used for a time sequence, and indicates the start time of the sequence. This value is mandatory for a time sequence to be expressed.
     */
    public void setStartTimeFT(String startTimeFT) {
        this.startTimeFT = startTimeFT;
    }

    /**
     * The endTime facet is used in conjunction with the isSequence and timeInterval facets (which must be set in order to use this facet). This
     * facet is used for a time sequence, and indicates that ending point (if any) of the sequence.
     */
    public String getEndTimeFT() {
        return endTimeFT;
    }

    /**
     * The endTime facet is used in conjunction with the isSequence and timeInterval facets (which must be set in order to use this facet). This
     * facet is used for a time sequence, and indicates that ending point (if any) of the sequence.
     */
    public void setEndTimeFT(String endTimeFT) {
        this.endTimeFT = endTimeFT;
    }

    /**
     * The minLength facet specifies the minimum and length of the value in characters.
     */
    public String getMinLengthFT() {
        return minLengthFT;
    }

    /**
     * The minLength facet specifies the minimum and length of the value in characters.
     */
    public void setMinLengthFT(String minLengthFT) {
        this.minLengthFT = minLengthFT;
    }

    /**
     * The maxLength facet specifies the maximum length of the value in characters.
     */
    public String getMaxLengthFT() {
        return maxLengthFT;
    }

    /**
     * The maxLength facet specifies the maximum length of the value in characters.
     */
    public void setMaxLengthFT(String maxLengthFT) {
        this.maxLengthFT = maxLengthFT;
    }

    /**
     * The minValue facet is used for inclusive and exclusive ranges, indicating what the lower bound of the range is. If this is used with an inclusive
     * range, a valid value will be greater than or equal to the value specified here. If the inclusive and exclusive data type is not specified (e.g. this
     * facet is used with an integer data type), the value is assumed to be inclusive.
     */
    public String getMinValueFT() {
        return minValueFT;
    }

    /**
     * The minValue facet is used for inclusive and exclusive ranges, indicating what the lower bound of the range is. If this is used with an inclusive
     * range, a valid value will be greater than or equal to the value specified here. If the inclusive and exclusive data type is not specified (e.g. this
     * facet is used with an integer data type), the value is assumed to be inclusive.
     */
    public void setMinValueFT(String minValueFT) {
        this.minValueFT = minValueFT;
    }

    /**
     * The maxValue facet is used for inclusive and exclusive ranges, indicating what the upper bound of the range is. If this is used with an inclusive
     * range, a valid value will be less than or equal to the value specified here. If the inclusive and exclusive data type is not specified (e.g. this facet is
     * used with an integer data type), the value is assumed to be inclusive.
     */
    public String getMaxValueFT() {
        return maxValueFT;
    }

    /**
     * The maxValue facet is used for inclusive and exclusive ranges, indicating what the upper bound of the range is. If this is used with an inclusive
     * range, a valid value will be less than or equal to the value specified here. If the inclusive and exclusive data type is not specified (e.g. this facet is
     * used with an integer data type), the value is assumed to be inclusive.
     */
    public void setMaxValueFT(String maxValueFT) {
        this.maxValueFT = maxValueFT;
    }

    /**
     * The decimals facet indicates the number of characters allowed after the decimal separator.
     */
    public String getDecimalsFT() {
        return decimalsFT;
    }

    /**
     * The decimals facet indicates the number of characters allowed after the decimal separator.
     */
    public void setDecimalsFT(String decimalsFT) {
        this.decimalsFT = decimalsFT;
    }

    /**
     * The pattern attribute holds any regular expression permitted in the implementation syntax (e.g. W3C XML Schema).
     */
    public String getPatternFT() {
        return patternFT;
    }

    /**
     * The pattern attribute holds any regular expression permitted in the implementation syntax (e.g. W3C XML Schema).
     */
    public void setPatternFT(String patternFT) {
        this.patternFT = patternFT;
    }

    /**
     * Only valid for a MetadataAttribute. PAY ATTENTION (See SDMX INFORMATIONMODEL, ExtendedFacetType)
     */
    public String getXhtmlEFT() {
        return xhtmlEFT;
    }

    /**
     * Only valid for a MetadataAttribute. PAY ATTENTION (See SDMX INFORMATIONMODEL, ExtendedFacetType)
     */
    public void setXhtmlEFT(String xhtmlEFT) {
        this.xhtmlEFT = xhtmlEFT;
    }

    /**
     * The isMultiLingual attribute indicates for a text format of type 'string', whether the value should allow for multiple values in different languages.
     */
    public String getIsMultiLingual() {
        return isMultiLingual;
    }

    /**
     * The isMultiLingual attribute indicates for a text format of type 'string', whether the value should allow for multiple values in different languages.
     */
    public void setIsMultiLingual(String isMultiLingual) {
        this.isMultiLingual = isMultiLingual;
    }

    /**
     * The format of the value of a Component when reported in a data or metadata set. This is contrained by the FacetValueType enumeration.
     */
    public FacetValueTypeEnum getFacetValue() {
        return facetValue;
    }

    /**
     * The format of the value of a Component when reported in a data or metadata set. This is contrained by the FacetValueType enumeration.
     */
    public void setFacetValue(FacetValueTypeEnum facetValue) {
        this.facetValue = facetValue;
    }

    /**
     * Defines the format of the identifiers in an Item Scheme used by a Component. Typically this would define the number of characters (length) of the identifier.
     */
    public ExternalItemDto getItemSchemeFacet() {
        return itemSchemeFacet;
    }

    /**
     * Defines the format of the identifiers in an Item Scheme used by a Component. Typically this would define the number of characters (length) of the identifier.
     */
    public void setItemSchemeFacet(ExternalItemDto itemSchemeFacet) {
        this.itemSchemeFacet = itemSchemeFacet;
    }
}
