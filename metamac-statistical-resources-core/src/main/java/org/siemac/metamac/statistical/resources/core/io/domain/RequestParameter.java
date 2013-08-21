package org.siemac.metamac.statistical.resources.core.io.domain;

public class RequestParameter {

    public String startPeriod            = null;
    public String endPeriod              = null;
    public String updatedAfter           = null;
    public String firstNObservations     = null;
    public String lastNObservations      = null;
    public String dimensionAtObservation = null;
    public String detail                 = null;

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
