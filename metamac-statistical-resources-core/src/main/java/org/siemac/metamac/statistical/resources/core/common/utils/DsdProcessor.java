package org.siemac.metamac.statistical.resources.core.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attribute;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeQualifierType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeRelationship;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.AttributeUsageStatusType;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Attributes;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Concept;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DataStructureComponents;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.DimensionBase;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Dimensions;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ItemResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.MeasureDimension;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.PrimaryMeasure;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.Representation;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.ResourceInternal;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.TextFormat;
import org.siemac.metamac.rest.structural_resources_internal.v1_0.domain.TimeDimension;
import org.siemac.metamac.statistical.resources.core.invocation.service.SrmRestInternalService;

public class DsdProcessor {

    private static SrmRestInternalService srmRestInternalService;

    public static SrmRestInternalService getSrmRestInternalService() {
        if (srmRestInternalService == null) {
            srmRestInternalService = ApplicationContextProvider.getApplicationContext().getBean(SrmRestInternalService.class);
        }
        return srmRestInternalService;
    }

    public static List<DsdDimension> getDimensions(DataStructure dsd) throws MetamacException {
        List<DsdDimension> dimensions = new ArrayList<DsdProcessor.DsdDimension>();
        DataStructureComponents components = dsd.getDataStructureComponents();
        if (components != null && components.getDimensions() != null) {
            Dimensions dimensionList = components.getDimensions();
            for (DimensionBase dimObj : dimensionList.getDimensions()) {
                if (dimObj instanceof Dimension) {
                    dimensions.add(new DsdDimension((Dimension) dimObj));
                } else if (dimObj instanceof MeasureDimension) {
                    dimensions.add(new DsdDimension((MeasureDimension) dimObj));
                } else if (dimObj instanceof TimeDimension) {
                    dimensions.add(new DsdDimension((TimeDimension) dimObj));
                } else {
                    throw new IllegalArgumentException("Found a dimension of unknown type " + dimObj);
                }
            }
        }
        return dimensions;
    }

    public static List<DsdAttribute> getAttributes(DataStructure dsd) throws MetamacException {
        List<DsdAttribute> attributes = new ArrayList<DsdAttribute>();
        DataStructureComponents components = dsd.getDataStructureComponents();

        if (components != null && components.getAttributes() != null) {
            Attributes attributeList = components.getAttributes();
            for (AttributeBase attrObj : attributeList.getAttributes()) {
                if (attrObj instanceof Attribute) {
                    attributes.add(new DsdAttribute((Attribute) attrObj));
                } else {
                    throw new IllegalArgumentException("Found an attribute of unknown type " + attrObj);
                }
            }
        }
        return attributes;
    }

    public abstract static class DsdComponent {

        protected DsdComponentType     type                           = null;
        protected String               codelistRepresentationUrn      = null;
        protected String               conceptSchemeRepresentationUrn = null;
        protected ItemResourceInternal conceptIdentity                = null;
        protected TextFormat           textFormat                     = null;
        protected TextFormat           textFormatConceptId            = null;

        protected void setConceptIdentity(ItemResourceInternal conceptIdentity) {
            if (conceptIdentity != null) {
                this.conceptIdentity = conceptIdentity;
            } else {
                throw new IllegalArgumentException("Concept identity is required");
            }
        }

        protected void setRepresentationFromLocalRepresentation(Representation localRepresentation) {
            if (localRepresentation.getEnumerationCodelist() != null) {
                extractCodelistUrn(localRepresentation.getEnumerationCodelist());
            } else {
                textFormat = localRepresentation.getTextFormat();
            }
        }

        protected void setRepresentationFromConceptIdentity(ItemResourceInternal conceptIdentity) throws MetamacException {
            Concept concept = null;
            if (conceptIdentity != null) {
                concept = getSrmRestInternalService().retrieveConceptByUrn(conceptIdentity.getUrn());
            } else {
                throw new RuntimeException("Concept identity can not be null");
            }

            if (concept.getCoreRepresentation() != null) {
                if (concept.getCoreRepresentation().getEnumerationCodelist() != null) {
                    extractCodelistUrn(concept.getCoreRepresentation().getEnumerationCodelist());
                } else {
                    textFormatConceptId = concept.getCoreRepresentation().getTextFormat();
                }
            } else {
                throw new IllegalArgumentException("Found a dimension with concept identity with core representation null");
            }
        }

        protected void extractCodelistUrn(ResourceInternal codelist) {
            if (codelist != null) {
                codelistRepresentationUrn = codelist.getUrn();
            } else {
                throw new RuntimeException("Codelist can not be null");
            }
        }

        protected void extractConceptSchemeUrn(ResourceInternal conceptScheme) {
            if (conceptScheme != null) {
                conceptSchemeRepresentationUrn = conceptScheme.getUrn();
            } else {
                throw new RuntimeException("ConceptScheme can not be null");
            }
        }

        public abstract String getComponentId();

        public DsdComponentType getType() {
            return type;
        }

        public String getCodelistRepresentationUrn() {
            return codelistRepresentationUrn;
        }

        public String getConceptSchemeRepresentationUrn() {
            return conceptSchemeRepresentationUrn;
        }

        public TextFormat getTextFormatRepresentation() {
            return textFormat;
        }

        public TextFormat getTextFormatConceptId() {
            return textFormatConceptId;
        }

        public String getEnumeratedRepresentationUrn() {
            if (StringUtils.isNotEmpty(this.codelistRepresentationUrn) && StringUtils.isNotEmpty(this.conceptSchemeRepresentationUrn)) {
                throw new RuntimeException("Two enumerated representation aren't allowed.");
            }
            if (StringUtils.isNotEmpty(this.codelistRepresentationUrn)) {
                return this.codelistRepresentationUrn;
            }
            if (StringUtils.isNotEmpty(this.conceptSchemeRepresentationUrn)) {
                return this.conceptSchemeRepresentationUrn;
            }
            return null;
        }

        public ItemResourceInternal getConceptIdentity() {
            return conceptIdentity;
        }
    }

    public static class DsdDimension extends DsdComponent {

        private final String dimensionId;
        private TextFormat   timeTextFormatType;

        public DsdDimension(Dimension dim) throws MetamacException {
            dimensionId = dim.getId();
            if (BooleanUtils.isTrue(dim.isIsSpatial())) {
                type = DsdComponentType.SPATIAL;
            } else {
                type = DsdComponentType.OTHER;
            }

            if (dim.getLocalRepresentation() != null) {
                setRepresentationFromLocalRepresentation(dim.getLocalRepresentation());
            } else if (dim.getConceptIdentity() != null) {
                setRepresentationFromConceptIdentity(dim.getConceptIdentity());
            } else {
                throw new IllegalArgumentException("Found a dimension with no representation info " + dim.getId());
            }

            setConceptIdentity(dim.getConceptIdentity());

        }

        public DsdDimension(MeasureDimension dim) {
            dimensionId = dim.getId();
            type = DsdComponentType.MEASURE;
            if (dim.getLocalRepresentation() != null) {
                if (dim.getLocalRepresentation().getEnumerationConceptScheme() != null) {
                    extractConceptSchemeUrn(dim.getLocalRepresentation().getEnumerationConceptScheme());
                } else {
                    throw new IllegalArgumentException("Found a dimension with local representation but no Concept Scheme");
                }
            } else {
                throw new IllegalArgumentException("Found a dimension with no representation info " + dim.getId());
            }

            setConceptIdentity(dim.getConceptIdentity());
        }

        public DsdDimension(TimeDimension dim) {
            dimensionId = dim.getId();
            type = DsdComponentType.TEMPORAL;
            if (dim.getLocalRepresentation() != null) {
                timeTextFormatType = dim.getLocalRepresentation().getTextFormat();
            } else {
                throw new IllegalArgumentException("Found a dimension with no representation info " + dim.getId());
            }

            setConceptIdentity(dim.getConceptIdentity());
        }

        public TextFormat getTimeTextFormatRepresentation() {
            return timeTextFormatType;
        }

        @Override
        public String getComponentId() {
            return dimensionId;
        }
    }

    public static class DsdAttribute extends DsdComponent {

        private final String                attributeId;
        private final boolean               isAttributeAtObservationLevel;
        private final AttributeRelationship attributeRelationship;
        private final boolean               isMandatory;

        public DsdAttribute(Attribute attr) throws MetamacException {
            attributeId = attr.getId();
            if (AttributeQualifierType.SPATIAL.equals(attr.getType())) {
                type = DsdComponentType.SPATIAL;
            } else if (AttributeQualifierType.MEASURE.equals(attr.getType())) {
                type = DsdComponentType.MEASURE;
            } else {
                type = DsdComponentType.OTHER;
            }

            if (attr.getLocalRepresentation() != null) {
                setRepresentationFromLocalRepresentation(attr.getLocalRepresentation());
            } else {
                setRepresentationFromConceptIdentity(attr.getConceptIdentity());
            }

            setConceptIdentity(attr.getConceptIdentity());

            isAttributeAtObservationLevel = (attr.getAttributeRelationship().getPrimaryMeasure() != null);
            attributeRelationship = attr.getAttributeRelationship();
            isMandatory = AttributeUsageStatusType.MANDATORY.equals(attr.getAssignmentStatus());
        }

        @Override
        public String getComponentId() {
            return attributeId;
        }

        public boolean isAttributeAtObservationLevel() {
            return isAttributeAtObservationLevel;
        }

        public AttributeRelationship getAttributeRelationship() {
            return attributeRelationship;
        }

        public boolean isMandatory() {
            return isMandatory;
        }
    }

    public static class DsdPrimaryMeasure extends DsdComponent {

        private final String primaryMeasueId = "OBS_VALUE"; // Fixed

        public DsdPrimaryMeasure(PrimaryMeasure primaryMeasure) throws MetamacException {

            if (primaryMeasure.getLocalRepresentation() != null) {
                setRepresentationFromLocalRepresentation(primaryMeasure.getLocalRepresentation());
            } else if (primaryMeasure.getConceptIdentity() != null) {
                setRepresentationFromConceptIdentity(primaryMeasure.getConceptIdentity());
            } else {
                throw new IllegalArgumentException("Found a primary measure with no representation info ");
            }
            setConceptIdentity(primaryMeasure.getConceptIdentity());
        }

        @Override
        public String getComponentId() {
            return primaryMeasueId;
        }
    }

    public static enum DsdComponentType {
        OTHER, SPATIAL, TEMPORAL, MEASURE
    }

}
