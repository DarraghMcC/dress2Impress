package com.dress2Impress.dto;

public class TemperatureDTO {

    private Double maxTemp;
    private Double minTemp;

    public Double getMaxTemp() {
        return maxTemp;
    }

    public TemperatureDTO setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
        return this;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public TemperatureDTO setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
        return this;
    }

}
