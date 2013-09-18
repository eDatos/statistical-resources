package org.siemac.metamac.statistical.resources.core.io.domain;

import org.sdmx.resources.sdmxml.rest.schemas.v2_1.types.DataParameterDetailEnum;

public class RequestParameter {

    public String                  startPeriod              = null;
    public String                  endPeriod                = null;
    public String                  updatedAfter             = null;
    public String                  firstNObservations       = null;
    public String                  lastNObservations        = null;
    public String                  dimensionAtObservation   = null;
    public DataParameterDetailEnum detail                   = null;

    public String                  proposeContentTypeString = null;

    public String getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getUpdatedAfter() {
        return updatedAfter;
    }

    public void setUpdatedAfter(String updatedAfter) {
        this.updatedAfter = updatedAfter;
    }

    public String getFirstNObservations() {
        return firstNObservations;
    }

    public void setFirstNObservations(String firstNObservations) {
        this.firstNObservations = firstNObservations;
    }

    public String getLastNObservations() {
        return lastNObservations;
    }

    public void setLastNObservations(String lastNObservations) {
        this.lastNObservations = lastNObservations;
    }

    public String getDimensionAtObservation() {
        return dimensionAtObservation;
    }

    public void setDimensionAtObservation(String dimensionAtObservation) {
        this.dimensionAtObservation = dimensionAtObservation;
    }

    public DataParameterDetailEnum getDetail() {
        return detail;
    }

    public void setDetail(DataParameterDetailEnum detail) {
        this.detail = detail;
    }

    public String getProposeContentTypeString() {
        return proposeContentTypeString;
    }

    public void setProposeContentTypeString(String proposeContentTypeString) {
        this.proposeContentTypeString = proposeContentTypeString;
    }
}
